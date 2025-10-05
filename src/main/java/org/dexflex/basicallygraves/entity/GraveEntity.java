package org.dexflex.basicallygraves.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Arm;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import java.util.Collections;

public class GraveEntity extends MobEntity {
    private final SimpleInventory graveInventory = new SimpleInventory(54);
    private String ownerName = "";
    private String ownerSkin = "";
    private int spawnInvulTicks = 10;

    public GraveEntity(EntityType<? extends MobEntity> type, World world) {
        super(type, world);
        this.setHealth(1.0F);
    }

    public void setOwner(ServerPlayerEntity player) {
        this.ownerName = player.getGameProfile().getName();
        this.ownerSkin = player.getGameProfile().getId().toString();
    }

    @Override
    public boolean isPersistent() {
        return true;
    }

    public void fillInventoryFromPlayer(ServerPlayerEntity player) {
        int slot = 0;
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (!stack.isEmpty()) {
                graveInventory.setStack(slot++, stack.copy());
            }
        }
    }

    public static DefaultAttributeContainer.Builder createGraveAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0D);
    }

    private void dropItemWithLowVelocity(ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY() + 0.25, this.getZ(), stack);
        double velocityRange = 0.1;
        itemEntity.setVelocity(
                (this.random.nextDouble() - 0.5) * velocityRange,
                0.1,
                (this.random.nextDouble() - 0.5) * velocityRange
        );
        this.getWorld().spawnEntity(itemEntity);
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (spawnInvulTicks > 0) return false;
        if (source.getAttacker() instanceof PlayerEntity) return super.damage(source, amount);
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (spawnInvulTicks > 0) return true;
        return !(damageSource.getAttacker() instanceof PlayerEntity);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        RegistryWrapper.WrapperLookup registryLookup = this.getRegistryManager();

        NbtList invList = new NbtList();
        for (int i = 0; i < graveInventory.size(); i++) {
            ItemStack stack = graveInventory.getStack(i);
            if (!stack.isEmpty()) {
                NbtCompound slotNbt = new NbtCompound();
                slotNbt.putByte("Slot", (byte) i);
                slotNbt.put("Item", stack.encode(registryLookup));
                invList.add(slotNbt);
            }
        }
        nbt.put("GraveInventory", invList);
        nbt.putString("OwnerName", ownerName);
        nbt.putString("OwnerSkin", ownerSkin);
        nbt.putBoolean("PersistenceRequired", true);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        RegistryWrapper.WrapperLookup registryLookup = this.getRegistryManager();

        NbtList invList = nbt.getList("GraveInventory", 10);
        for (int i = 0; i < invList.size(); i++) {
            NbtCompound slotNbt = invList.getCompound(i);
            int slot = slotNbt.getByte("Slot") & 255;
            ItemStack stack = ItemStack.fromNbt(registryLookup, slotNbt.getCompound("Item")).orElse(ItemStack.EMPTY);
            if (slot >= 0 && slot < graveInventory.size()) {
                graveInventory.setStack(slot, stack);
            }
        }

        ownerName = nbt.getString("OwnerName");
        ownerSkin = nbt.getString("OwnerSkin");

        if (nbt.contains("PersistenceRequired")) {
            this.setPersistent();
        }
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getEquippedStack(net.minecraft.entity.EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(net.minecraft.entity.EquipmentSlot slot, ItemStack stack) {}

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);

        if (!this.getWorld().isClient) {
            for (int i = 0; i < graveInventory.size(); i++) {
                ItemStack stack = graveInventory.getStack(i);
                if (!stack.isEmpty()) {
                    dropItemWithLowVelocity(stack);
                    graveInventory.setStack(i, ItemStack.EMPTY);
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient && this.getWorld() instanceof ServerWorld serverWorld) {
            ChunkPos pos = this.getChunkPos();
            serverWorld.setChunkForced(pos.x, pos.z, true);
        }

        if (spawnInvulTicks > 0) spawnInvulTicks--;
    }

    @Override
    public void remove(RemovalReason reason) {
        if (!this.getWorld().isClient && this.getWorld() instanceof ServerWorld serverWorld) {
            ChunkPos pos = this.getChunkPos();
            serverWorld.setChunkForced(pos.x, pos.z, false);
        }
        super.remove(reason);
    }
}
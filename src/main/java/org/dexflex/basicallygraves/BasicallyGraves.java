package org.dexflex.basicallygraves;

import net.fabricmc.api.ModInitializer;

public class BasicallyGraves implements ModInitializer {
    @Override
    public void onInitialize() {
        ModEntities.register();
        DeathEventHandler.register();
    }
}
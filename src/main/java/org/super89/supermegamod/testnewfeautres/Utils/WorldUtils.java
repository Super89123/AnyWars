package org.super89.supermegamod.testnewfeautres.Utils;

import org.bukkit.World;

public class WorldUtils {
    private final String world_name;
    private final World generatedWorld;
    public WorldUtils(String world_name, World generatedWorld){
        this.world_name = world_name;

        this.generatedWorld = generatedWorld;
        // TODO
    }
    public String getWorldName(){
        return world_name;

    }
}

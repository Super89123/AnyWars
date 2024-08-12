package org.super89.supermegamod.testnewfeautres.Handlers;

import org.bukkit.World;
import org.bukkit.WorldCreator;

public class WorldHandler {
    private final String world_name;
    private final World generatedWorld;
    public WorldHandler(String world_name, World generatedWorld){
        this.world_name = world_name;

        this.generatedWorld = generatedWorld;
        WorldCreator worldCreator = new WorldCreator(world_name);
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.generateStructures(false);
        worldCreator.generator()

        // TODO
    }
    public String getWorldName(){
        return world_name;
    }

    public World getGeneratedWorld() {
        return generatedWorld;
    }
}

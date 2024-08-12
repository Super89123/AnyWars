package org.super89.supermegamod.testnewfeautres.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.super89.supermegamod.testnewfeautres.Generators.VoidGenerator;

public class WorldHandler {
    private final String world_name;
    private final World generatedWorld;
    public WorldHandler(String world_name){
        this.world_name = world_name;


        WorldCreator worldCreator = new WorldCreator(world_name);
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.generateStructures(false);
        worldCreator.generator(new VoidGenerator());


        this.generatedWorld = Bukkit.createWorld(worldCreator);

        // TODO
    }
    public String getWorldName(){
        return world_name;
    }

    public World getGeneratedWorld() {
        return generatedWorld;
    }
}

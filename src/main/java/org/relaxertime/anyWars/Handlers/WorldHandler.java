package org.relaxertime.anyWars.Handlers;

import org.bukkit.*;
import org.relaxertime.anyWars.WorldGenerators.VoidGenerator;


public class WorldHandler {
    private final String world_name;
    private World generatedWorld;
    public WorldHandler(String world_name){
        this.world_name = world_name;


        WorldCreator worldCreator = new WorldCreator(world_name);
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.generateStructures(false);
        worldCreator.generator(new VoidGenerator());
        if(Bukkit.getWorld(world_name) == null) {


            this.generatedWorld = Bukkit.createWorld(worldCreator);
            assert this.generatedWorld != null;
            this.generatedWorld.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            this.generatedWorld.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
            this.generatedWorld.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);

        }
        else {
            this.generatedWorld = Bukkit.getWorld(world_name);
        }

    }
    public String getWorldName(){
        return world_name;
    }

    public World getGeneratedWorld() {
        return generatedWorld;
    }
    public void setSpawnInWorld(Location location){
        generatedWorld.setSpawnLocation(location);
    }
}
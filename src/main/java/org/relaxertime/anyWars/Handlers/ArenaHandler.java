package org.relaxertime.anyWars.Handlers;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.relaxertime.anyWars.AnyWars;


import java.io.File;
import java.io.IOException;

public class ArenaHandler {
    private final AnyWars plugin;
    private final File templateFile;
    private final YamlConfiguration templateConfig;
    private final File arenaDir;
    private final String name;
    private int size;
    private int teamCount;
    private Location spawnLocation;
    private int minSize;
    private int playersInTeam;

    private boolean isExist = true;

    public ArenaHandler(AnyWars plugin, String fileName, int PlayersCount, int teamCount, Location spawnLocation, int minSize) {
        this.plugin = plugin;
        this.arenaDir = new File(plugin.getDataFolder(), "arenas");
        if(!arenaDir.exists()){
            arenaDir.mkdirs();
        }
        this.templateFile = new File(arenaDir, fileName+".arena");
        this.templateConfig = YamlConfiguration.loadConfiguration(templateFile);
        this.name = fileName;

        if(!templateFile.exists()){
            this.spawnLocation = spawnLocation;
            this.teamCount = teamCount;
            this.minSize = minSize;
            this.size = PlayersCount;
            templateConfig.set("name", name);
            templateConfig.set("max-players-count", PlayersCount);
            templateConfig.set("min-players-count", minSize);
            templateConfig.set("teams-count", teamCount);
            templateConfig.set("location", spawnLocation);

            try {
                templateConfig.save(templateFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            this.spawnLocation = (Location) templateConfig.get("location");
            this.size = (int) templateConfig.get("max-players-count");
            this.minSize = (int) templateConfig.get("min-players-count");
            this.teamCount = (int) templateConfig.get("teams-count");
        }


    }
    public ArenaHandler(AnyWars plugin, String name){
        this.plugin = plugin;
        this.arenaDir = new File(plugin.getDataFolder(), "arenas");
        this.name = name;
        if(!arenaDir.exists()){
            arenaDir.mkdirs();
        }
        this.templateFile = new File(arenaDir, name+".arena");
        if(!(templateFile.exists())){
            isExist = false;
        }

        this.templateConfig = YamlConfiguration.loadConfiguration(templateFile);
        this.spawnLocation = (Location) templateConfig.get("location");
        this.size = (int) templateConfig.get("max-players-count");
        this.minSize = (int) templateConfig.get("min-players-count");
        this.teamCount = (int) templateConfig.get("teams-count");



    }

    public int getSize() {
        return size;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public YamlConfiguration getTemplateConfig() {
        return templateConfig;
    }

    public int getMinSize() {
        return minSize;
    }
    public int getTeamCount(){
        return teamCount;
    }
    public boolean isExist(){
        return isExist;
    }
    public String getName(){
        return name;
    }

}
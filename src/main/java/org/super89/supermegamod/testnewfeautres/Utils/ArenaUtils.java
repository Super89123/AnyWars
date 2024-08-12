package org.super89.supermegamod.testnewfeautres.Utils;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.super89.supermegamod.testnewfeautres.TestNewFeautres;

import java.io.File;
import java.io.IOException;

public class ArenaUtils {
    private final TestNewFeautres plugin;
    private final File templateFile;
    private final YamlConfiguration templateConfig;
    private final File arenaDir;
    private int size;
    private int teamCount;
    private Location spawnLocation;
    private int minSize;

    public ArenaUtils(TestNewFeautres plugin, String fileName, int PlayersCount, int teamCount, Location spawnLocation, int minSize) {
        this.plugin = plugin;
        this.arenaDir = new File(plugin.getDataFolder(), "arenas");
        if(!arenaDir.exists()){
            arenaDir.mkdirs();
        }
        this.templateFile = new File(arenaDir, fileName);
        this.templateConfig = YamlConfiguration.loadConfiguration(templateFile);

        if(!templateFile.exists()){
            this.spawnLocation = spawnLocation;
            this.teamCount = teamCount;
            this.minSize = minSize;
            this.size = PlayersCount;
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

}

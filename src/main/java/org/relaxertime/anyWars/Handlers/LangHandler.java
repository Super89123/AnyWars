package org.relaxertime.anyWars.Handlers;

import org.bukkit.configuration.file.YamlConfiguration;
import org.relaxertime.anyWars.AnyWars;


import java.io.File;
import java.io.IOException;

public class LangHandler {
    private final AnyWars plugin;
    private final File langFolder;
    private final File file;
    private final YamlConfiguration config;

    public LangHandler(AnyWars plugin, String filename) {
        this.plugin = plugin;
        this.langFolder = new File(this.plugin.getDataFolder(), "lang");
        if (!langFolder.exists()) {
            langFolder.mkdirs(); // create the lang folder if it doesn't exist
        }
        this.file = new File(langFolder, filename);
        if (!file.exists()) {
            this.config = YamlConfiguration.loadConfiguration(file);
            if (filename.equals("ru.yml")) {
                config.set("respwanend", "Возврождение");
                config.set("generator", "Пожалуйста, проверьте что Citizens работает корректно");
            } else if (filename.equals("en.yml")) {
                config.set("respwanend", "You are Alive");
                config.set("generator", "generator");
                config.set("nocitizens", "Please, Check if Citizens Plugin works correctly");

            }
            else {
                config.set("respwanend", "Возврождение");
                config.set("generator", "Генератор ресурсов");
                config.set("nocitizens", "Please, Check if Citizens Plugin works correctly");

            }
            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            this.config = YamlConfiguration.loadConfiguration(file);
        }

    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
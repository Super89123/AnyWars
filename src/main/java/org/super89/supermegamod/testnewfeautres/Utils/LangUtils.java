package org.super89.supermegamod.testnewfeautres.Utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.super89.supermegamod.testnewfeautres.TestNewFeautres;

import java.io.File;
import java.io.IOException;

public class LangUtils {
    private final TestNewFeautres plugin;
    private final File langFolder;
    private final File file;
    private final YamlConfiguration config;

    public LangUtils(TestNewFeautres plugin, String filename) {
        this.plugin = plugin;
        this.langFolder = new File(this.plugin.getDataFolder(), "lang");
        if (!langFolder.exists()) {
            langFolder.mkdirs(); // create the lang folder if it doesn't exist
        }
        this.file = new File(langFolder, filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
        if (filename.equals("ru.yml")) {
            config.set("respwanend", "Возврождение");
            config.set("generator", "Генератор ресурсов");
        } else if (filename.equals("en.yml")) {
            config.set("respwanend", "You are Alive");
            config.set("generator", "generator");

        }
        else {
            config.set("respwanend", "Возврождение");
            config.set("generator", "Генератор ресурсов");

        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
package org.super89.supermegamod.testnewfeautres;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.profile.PlayerTextures;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.http.WebSocket;
import java.util.List;
import java.util.Objects;

public final class TestNewFeautres extends JavaPlugin implements Listener {
    NamespacedKey smt = new NamespacedKey(this, "smt");

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (getVar(player) == 0) {
                        setVar(player, 0);
                    }
                    if (getVar(player) < 100) {
                        setVar(player, getVar(player) + 1);
                    }
                    player.sendMessage(String.valueOf(getVar(player)));
                }
            }
        }, 20, 20);
    }

    public int getVar(Player player) {
        PersistentDataContainer container = player.getPersistentDataContainer();
        Integer value = container.get(smt, PersistentDataType.INTEGER);
        return value == null ? 0 : value;
    }

    public void setVar(Player player, int a) {
        PersistentDataContainer container = player.getPersistentDataContainer();
        container.set(smt, PersistentDataType.INTEGER, a);
    }

    @EventHandler
    public void join(PlayerJoinEvent E) {
        if (!E.getPlayer().hasPlayedBefore()) {
            setVar(E.getPlayer(), 0);
        }

        Player player = E.getPlayer();
        PlayerProfile profile = player.getPlayerProfile();

        // Укажи ссылку на свой плащ
        String capeUrl = "iVBORw0KGgoAAAANSUhEUgAAABYAAAARCAYAAADZsVyDAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsEAAA7BAbiRa+0AAACcSURBVDhPY2RgYPgPxFQHTFCa6oAqBotU8UFZCEC0wbIbRKEs4gBZLibGEqqHMSxYcBqsfE0SyoKA76d+QlmYgNOMHcpCAKq7GAZobzB6krmr9RzKIg8QHcbI4HHAaygLE8DCG6fBH5d8hbLIA0SHMXrMIwcdthQDN/hN2ycoCxIMyHxyAM7Iw5b/SQFEBwU+gOw7WLDQqDxmYAAA0DEhgzw/zTMAAAAASUVORK5CYII=";

        // Создаём новое свойство для плаща
        ProfileProperty capeProperty = new ProfileProperty("cape", capeUrl);

        // Добавляем свойство в профиль игрока
        profile.setProperties(List.of(new ProfileProperty[]{capeProperty}));

        // Обновляем профиль игрока на сервере
        player.setPlayerProfile(profile);

    }



}
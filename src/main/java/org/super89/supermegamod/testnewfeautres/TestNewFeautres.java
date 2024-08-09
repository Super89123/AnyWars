package org.super89.supermegamod.testnewfeautres;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import org.super89.supermegamod.testnewfeautres.Utils.ArmorStandUtils;


import java.util.*;

public final class TestNewFeautres extends JavaPlugin {
    private boolean isGameStarted = false;
    private Map<Player, Integer> playerResMap = new HashMap<>();



    private int count = 0;
    private int newCount = 0;
    private int entityCount = 0;

    private final NamespacedKey smt = new NamespacedKey(this, "smt");
    private final Events events = new Events(this);


    @Override
    public void onEnable() {
        final Location resourceLocation = new Location(Bukkit.getWorld("world"), 0.5,-61,0.5);
        ArmorStandUtils resArmorStand = new ArmorStandUtils(resourceLocation, ChatColor.YELLOW+"Генератор Ресуров");


        Bukkit.getPluginManager().registerEvents(events, this);
        Objects.requireNonNull(Bukkit.getPluginCommand("game")).setExecutor(new Game(this));
        List<String> tabgame = Arrays.asList("start", "stop");
        Objects.requireNonNull(Bukkit.getPluginCommand("game")).setTabCompleter(new TabCompleter() {
            @Override
            public @NotNull List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {


                return tabgame;
            }
        });
        for(World world : Bukkit.getWorlds()){

            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);

        }

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

                    if(getVar(player) <= 5 && player.isSprinting()){
                        player.setSprinting(false);

                    } else if (getVar(player) >5 && player.isSprinting()) {
                        setVar(player, getVar(player)-1);

                    }
                    player.sendActionBar(ChatColor.AQUA+"Стамина: "+ getVar(player) +"/100");
                }

            }
        }, 2, 2);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for(Player player : events.deathMap.keySet()){
                    if(events.deathMap.get(player) >0){
                    player.sendTitle(ChatColor.YELLOW+"Возрождение через: " + ChatColor.RED + events.deathMap.get(player)+" секунд." , "");
                    events.deathMap.put(player, events.deathMap.get(player)-1);


                    }
                    else {
                        events.deathMap.remove(player);
                        player.sendTitle(ChatColor.YELLOW + "Вы возрождены!", "");
                        player.setGameMode(GameMode.ADVENTURE);
                        if(player.getRespawnLocation() != null) {
                            player.teleport(player.getRespawnLocation());
                        }
                        else {
                            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
                        }
                    }
                }



            }
        }, 20, 20);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (isGameStarted) {
                    for(Entity entity : resArmorStand.getArmorStand().getNearbyEntities(1.5, 1.5, 1.5)){
                        if(entity instanceof Player){
                            entityCount++;
                        }

                    }
                    if (entityCount == 0) {
                        World world = Bukkit.getWorld("world");
                        if (count < 4) {
                            assert world != null;
                            world.dropItemNaturally(resourceLocation, new ItemStack(Material.IRON_INGOT));
                            count++;
                        }
                        if (count == 4) {
                            assert world != null;
                            world.dropItemNaturally(resourceLocation, new ItemStack(Material.GOLD_INGOT));
                            count = 0;

                        }
                    } else {
                        for (Entity entity : resArmorStand.getArmorStand().getNearbyEntities(1, 1, 1)) {
                            if (entity instanceof Player player) {
                                System.out.println("Player detected near armor stand: " + player.getName()); // Debug message
                                if (newCount < 4) {
                                    player.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
                                    newCount++;

                                }
                                if (newCount == 4) {
                                    player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                                    newCount = 0;

                                }
                            }
                        }
                    }
                    entityCount = 0;
                }
            }
        }, 30, 30);
    }
    @Override
    public void onDisable(){
        isGameStarted = false;
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
    public boolean getisGameStarted(){
        return isGameStarted;
    }
    public void setGameStarted(boolean t){
        isGameStarted = t;
    }



}
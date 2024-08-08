package org.super89.supermegamod.testnewfeautres;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TestNewFeautres extends JavaPlugin implements Listener {
    private boolean isGameStarted = false;

    private final Location resourceLocation = new Location(Bukkit.getWorld("world"), 0,5,0);

    private int count = 0;

    private final NamespacedKey smt = new NamespacedKey(this, "smt");
    private final Events events =new Events();


    @Override
    public void onEnable() {

        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(events, this);
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
                    player.sendActionBar(ChatColor.AQUA+String.valueOf(getVar(player))+"/100");
                }

            }
        }, 20, 20);
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
                if(isGameStarted){
                World world = Bukkit.getWorld("world");
                if(count < 4){
                    assert world != null;
                    world.dropItemNaturally(resourceLocation, new ItemStack(Material.IRON_INGOT));

                    count++;
                }
                if(count == 4){
                    assert world != null;
                    world.dropItemNaturally(resourceLocation, new ItemStack(Material.IRON_INGOT));
                    world.dropItemNaturally(resourceLocation, new ItemStack(Material.GOLD_INGOT));
                    count = 0;
                }
                }


            }
        }, 40, 40);
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
package org.relaxertime.anyWars;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.relaxertime.anyWars.Commands.AnyWarsCommand;
import org.relaxertime.anyWars.Commands.Game;
import org.relaxertime.anyWars.Events.BlockEvents;
import org.relaxertime.anyWars.Events.PlayerEvents;
import org.relaxertime.anyWars.Handlers.LangHandler;
import org.relaxertime.anyWars.Utils.ArmorStandUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class AnyWars extends JavaPlugin implements Listener {

    private Map<Player, Integer> playerResMap = new HashMap<>();
    private boolean isGameStarted = false;



    private int count = 0;
    private int newCount = 0;
    private int entityCount = 0;

    private final NamespacedKey smt = new NamespacedKey(this, "smt");
    private final BlockEvents blockEvents = new BlockEvents(this);
    private final PlayerEvents playerEvents = new PlayerEvents(this);
    private LangHandler lang;
    private List<YamlConfiguration> arenas;

    private boolean isCitizens = false;


    private YamlConfiguration config = new YamlConfiguration();




    @Override
    public void onEnable() {
        final File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try {
                configFile.createNewFile();
                config.set("lang", "en");
                config.set("arena_type", "world");
                config.save(configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            config.load(configFile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            getServer().getLogger().severe("Ошибка При загрузки конфигурации! Пожалуйста свяжитесь с нами в офффицальном дискорд сервере");
            getServer().getLogger().severe("https://discord.gg/gWcX5kAHhH");
            throw new RuntimeException(e);

        }
        if(config.get("lang").equals("ru")){
            lang = new LangHandler(this, "ru.yml");
        }
        else {
            lang= new LangHandler(this, "en.yml");
        }
        if(!isCitizens){
            getServer().getLogger().severe(String.valueOf(lang.getConfig().get("nocitizens")));
        }

        final Location resourceLocation = new Location(Bukkit.getWorld("world"), 0.5,-61,0.5);
        ArmorStandUtils resArmorStand = new ArmorStandUtils(resourceLocation, ChatColor.YELLOW+"Генератор Ресуров");
        final File arenaDir = new File(getDataFolder(), "arenas");
        if(!arenaDir.exists()){
            arenaDir.mkdirs();
        }



        Bukkit.getPluginManager().registerEvents(playerEvents, this);
        Bukkit.getPluginManager().registerEvents(blockEvents, this);
        Objects.requireNonNull(Bukkit.getPluginCommand("game")).setExecutor(new Game(this));
        List<String> tabgame = Arrays.asList("start", "stop");
        Objects.requireNonNull(Bukkit.getPluginCommand("game")).setTabCompleter(new TabCompleter() {
            @Override
            public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
                return List.of();
            }
        });

        for(World world : Bukkit.getWorlds()){

            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);

        }
        Objects.requireNonNull(Bukkit.getPluginCommand("anywars")).setExecutor(new AnyWarsCommand(this));


        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if(isGameStarted){
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
                            setVar(player, getVar(player)-3);

                        }
                        player.sendActionBar(ChatColor.AQUA+"Стамина: "+ getVar(player) +"/100");

                    }
                }

            }
        }, 4, 4);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for(Player player : playerEvents.deathMap.keySet()){
                    if(playerEvents.deathMap.get(player) >0){
                        player.sendTitle(ChatColor.YELLOW+"Возрождение через: " + ChatColor.RED + playerEvents.deathMap.get(player)+" секунд." , "");
                        playerEvents.deathMap.put(player, playerEvents.deathMap.get(player)-1);


                    }
                    else {
                        playerEvents.deathMap.remove(player);
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
    public String getArenaType(){
        return String.valueOf(config.get("arena_type"));
    }
    public List<YamlConfiguration> getArenas(){
        return arenas;
    }



}

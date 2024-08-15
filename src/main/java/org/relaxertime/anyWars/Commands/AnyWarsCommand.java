package org.relaxertime.anyWars.Commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.relaxertime.anyWars.AnyWars;
import org.relaxertime.anyWars.Handlers.ArenaHandler;
import org.relaxertime.anyWars.Handlers.WorldHandler;
import org.relaxertime.anyWars.Utils.ItemUtils;
import org.relaxertime.anyWars.Utils.MathUtils;


import java.util.List;

public class AnyWarsCommand implements CommandExecutor {
    private final AnyWars plugin;
    private final List<YamlConfiguration> arenas;
    private ArenaHandler arenaHandler;
    private WorldHandler worldHandler;


    public AnyWarsCommand(AnyWars plugin) {
        this.plugin = plugin;
        this.arenas = plugin.getArenas();
    }
    private ItemUtils itemUtils;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage(ChatColor.RED+"Эту команду могут использовать только игроки!");
            return true;
        }

        Player player = (Player) commandSender;
        if(args[0].equals("create") && args.length == 5){
            if(!(MathUtils.isNumeric(args[2]) || !(MathUtils.isNumeric(args[3])) || !(MathUtils.isNumeric(args[4])))){
                player.sendMessage(ChatColor.RED+"Вы ввели неверные целые числа!");
                return false;

            }
            if(Integer.parseInt(args[2]) % Integer.parseInt(args[3]) != 0){
                player.sendMessage(ChatColor.RED + "Число игроков должно делиться без остатка на количество игроков.");
                return false;
            }
            worldHandler = new WorldHandler(args[1]);
            Location location = new Location(worldHandler.getGeneratedWorld(), 0, 60, 0);
            location.getBlock().setType(Material.STONE);
            location.setY(location.getY()+1);

            arenaHandler = new ArenaHandler(plugin, args[1],  Integer.parseInt(args[2]) ,Integer.parseInt(args[3]), location, Integer.parseInt(args[4]));
            player.teleport(location);
            return true;



        }
        else if(args[0].equals("join") && args.length == 2){
            arenaHandler = new ArenaHandler(plugin, args[1]);
            itemUtils = new ItemUtils(plugin);
            World world = arenaHandler.getSpawnLocation().getWorld();
            if(arenaHandler.isExist()){
                player.teleport(arenaHandler.getSpawnLocation());
                player.setGameMode(GameMode.ADVENTURE);
                player.getInventory().clear();
                player.getInventory().addItem(itemUtils.createUnThrowableItem(Material.COMPASS, ChatColor.YELLOW+"Выбор команды", 1));
                for(Player player1 : world.getPlayers()){
                    player1.sendMessage(ChatColor.GRAY+String.valueOf(world.getPlayers().size())+"/"+ arenaHandler.getSize() +" "+ChatColor.WHITE + player1.getName() + " зашел в игру.");
                }
            }
            else {
                player.sendMessage(ChatColor.RED+"Данной арены не существует!");
            }

        }
        else {
            return false;
        }

        return true;
    }

}
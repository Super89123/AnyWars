package org.super89.supermegamod.testnewfeautres.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.super89.supermegamod.testnewfeautres.Handlers.ArenaHandler;
import org.super89.supermegamod.testnewfeautres.Handlers.WorldHandler;
import org.super89.supermegamod.testnewfeautres.TestNewFeautres;
import org.super89.supermegamod.testnewfeautres.Utils.MathUtils;

import java.util.List;

public class AnyWarsCommand implements CommandExecutor {
    private final TestNewFeautres plugin;
    private final List<YamlConfiguration> arenas;
    private ArenaHandler arenaHandler;
    private WorldHandler worldHandler;

    public AnyWarsCommand(TestNewFeautres plugin) {
        this.plugin = plugin;
        this.arenas = plugin.getArenas();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage(ChatColor.RED+"Эту команду могут использовать только игроки!");
            return true;
        }
        if(args.length < 5){
            return false;
        }
        else {
            Player player = (Player) commandSender;
            if(args[0].equals("create")){
                if(!(MathUtils.isNumeric(args[2]) || !(MathUtils.isNumeric(args[3])) || !(MathUtils.isNumeric(args[4])))){
                    player.sendMessage(ChatColor.RED+"Вы ввели неверные целые числа!");
                    return false;

                }
                worldHandler = new WorldHandler(args[1]);
                Location location = new Location(worldHandler.getGeneratedWorld(), 0, 60, 0);
                location.getBlock().setType(Material.STONE);
                location.setY(location.getY()+1);

                arenaHandler = new ArenaHandler(plugin, args[1],  Integer.parseInt(args[2]) ,Integer.parseInt(args[3]), location, Integer.parseInt(args[4]));
                player.teleport(location);



            }
        }

        return true;
    }

}

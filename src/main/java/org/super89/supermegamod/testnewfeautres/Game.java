package org.super89.supermegamod.testnewfeautres;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Game implements CommandExecutor {
    private final TestNewFeautres plugin;
    private final boolean isgamestarted;

    public Game(TestNewFeautres plugin) {
        this.plugin = plugin;
        this.isgamestarted = plugin.getisGameStarted();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(command.getName().equals("game")){
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage(ChatColor.RED+"Эту команду могут использовать только игроки!");
            return true;

        }
            Player player = (Player) commandSender;
        if(args.length < 1){
            player.sendMessage("Используйте /game start/stop");

            return true;
        }
        if(args[0].equals("start")) {

            if (!player.isOp()) {
                player.sendMessage(ChatColor.RED + "Эту команду могу использовать только администраторы!");
                return true;
            }
            plugin.setGameStarted(true);
            player.sendMessage(ChatColor.RED + "Игра началась!");
            return true;
        }
        if(args[0].equals("stop")) {
                if (!player.isOp()) {
                    player.sendMessage(ChatColor.RED + "Эту команду могу использовать только администраторы!");
                    return true;
                }
                plugin.setGameStarted(false);
                player.sendMessage(ChatColor.RED + "Игра закончилась!");
                return true;
            }





        return true;
    }
        return true;
    }
}

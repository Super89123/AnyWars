package org.super89.supermegamod.testnewfeautres;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Commands implements CommandExecutor {
    private final TestNewFeautres plugin;
    private boolean isgamestarted;

    public Commands(TestNewFeautres plugin) {
        this.plugin = plugin;
        this.isgamestarted = plugin.getisGameStarted();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(command.getName().equals("game-start")){
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage(ChatColor.RED+"Эту команду могут использовать только игроки!");
            return true;

        }
        Player player = (Player) commandSender;
        if(!player.isOp()){
            player.sendMessage(ChatColor.RED+"Эту команду могу использовать только администраторы!");
            return true;
        }
        plugin.setGameStarted(true);




        return true;
    }
        return true;
    }
}

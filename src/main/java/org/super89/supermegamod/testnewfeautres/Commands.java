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
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(command.getName().equals("data-change")){
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage(ChatColor.RED+"Эту команду могут использовать только игроки!");
            return true;

        }
        Player target = Bukkit.getPlayer(strings[0]);
        Player player = (Player) commandSender;




        return true;
    }
        return true;
    }
}

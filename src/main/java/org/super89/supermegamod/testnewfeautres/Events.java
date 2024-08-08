package org.super89.supermegamod.testnewfeautres;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.Map;

public class Events implements Listener {
       public Map<Player, Integer> deathMap = new HashMap<>();
    @EventHandler
    public void deathev(PlayerDeathEvent event){
        Player player = event.getPlayer();
        deathMap.put(player, 5);

    }
    @EventHandler
    public  void  resEv(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        player.setGameMode(GameMode.SPECTATOR);
    }
    @EventHandler
    public void tpspecev(PlayerTeleportEvent event){
        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE) && deathMap.get(event.getPlayer())>0){
            event.setCancelled(true);
        }
    }

}

package org.super89.supermegamod.testnewfeautres;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class Events implements Listener {
    public Map<Player, Integer> deathMap = new HashMap<>();
    private final TestNewFeautres plugin;
    public Events(TestNewFeautres plugin){
        this.plugin = plugin;
    }

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
    @EventHandler
    public void attackEvent(EntityDamageByEntityEvent event){
        NamespacedKey smt = new NamespacedKey(plugin, "smt");
        if(event.getDamager() instanceof Player player){

            PersistentDataContainer con = player.getPersistentDataContainer();
            int a = con.get(smt, PersistentDataType.INTEGER);
            if(a == 0){
                event.setCancelled(true);
                return;
            }
            con.set(smt, PersistentDataType.INTEGER, a-10);
        }
    }
    @EventHandler
    public void jumpEvent(PlayerJumpEvent event){

        NamespacedKey smt = new NamespacedKey(plugin, "smt");

        PersistentDataContainer con = event.getPlayer().getPersistentDataContainer();
        if(con.get(smt, PersistentDataType.INTEGER) == 0){
            event.setCancelled(true);
        }
        else {
            con.set(smt, PersistentDataType.INTEGER, con.get(smt, PersistentDataType.INTEGER)-10);
        }
    }
    @EventHandler
    public void preattack(PrePlayerAttackEntityEvent event){
        NamespacedKey smt = new NamespacedKey(plugin, "smt");
        PersistentDataContainer con = event.getPlayer().getPersistentDataContainer();
        if(con.get(smt, PersistentDataType.INTEGER) == 0){
            event.setCancelled(true);
        }
        else {
            con.set(smt, PersistentDataType.INTEGER, con.get(smt, PersistentDataType.INTEGER)-10);
        }
    }
}




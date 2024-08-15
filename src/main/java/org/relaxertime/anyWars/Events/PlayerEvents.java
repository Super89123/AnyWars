package org.relaxertime.anyWars.Events;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.relaxertime.anyWars.AnyWars;


import java.util.HashMap;
import java.util.Map;

public class PlayerEvents implements Listener {
    public Map<Player, Integer> deathMap = new HashMap<>();
    private final AnyWars plugin;
    private final boolean isGameStarted;
    private Map<Block, Location> blockMap = new HashMap<>();
    public PlayerEvents(AnyWars plugin){
        this.plugin = plugin;
        this.isGameStarted = plugin.getisGameStarted();
    }

    @EventHandler
    public void deathev(PlayerDeathEvent event){

        Player player = event.getPlayer();
        deathMap.put(player, 5);
        NamespacedKey smt = new NamespacedKey(plugin, "smt");
        PersistentDataContainer con = player.getPersistentDataContainer();
        con.set(smt, PersistentDataType.INTEGER, 100);


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
            if(a <= 5){
                event.setCancelled(true);
                return;
            }
            con.set(smt, PersistentDataType.INTEGER, Math.max(con.get(smt, PersistentDataType.INTEGER)-5, 0));
        }
    }
    @EventHandler
    public void jumpEvent(PlayerJumpEvent event){

        NamespacedKey smt = new NamespacedKey(plugin, "smt");

        PersistentDataContainer con = event.getPlayer().getPersistentDataContainer();
        if(con.get(smt, PersistentDataType.INTEGER) <= 5){
            event.setCancelled(true);

        }
        else {
            con.set(smt, PersistentDataType.INTEGER, Math.max(con.get(smt, PersistentDataType.INTEGER)-5, 0));
        }
    }
    @EventHandler
    public void preattack(PrePlayerAttackEntityEvent event){
        NamespacedKey smt = new NamespacedKey(plugin, "smt");
        PersistentDataContainer con = event.getPlayer().getPersistentDataContainer();
        if(con.get(smt, PersistentDataType.INTEGER) <= 5){
            event.setCancelled(true);
        }
        else {
            con.set(smt, PersistentDataType.INTEGER, Math.max(con.get(smt, PersistentDataType.INTEGER)-5, 0));
        }
    }
    @EventHandler
    public void dropEvent(PlayerDropItemEvent event){
        ItemStack itemStack = event.getItemDrop().getItemStack();
        ItemMeta meta = itemStack.getItemMeta();
        NamespacedKey canDrop = new NamespacedKey(plugin, "canDrop");
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if(container.has(canDrop)){
            if(Boolean.TRUE.equals(container.get(canDrop, PersistentDataType.BOOLEAN))){
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void interactcancelevent(InventoryClickEvent event){
        if(event.getCurrentItem().getType() != Material.AIR){
            ItemStack itemStack = event.getCurrentItem();
            ItemMeta meta = itemStack.getItemMeta();
            NamespacedKey canDrop = new NamespacedKey(plugin, "canDrop");
            PersistentDataContainer container = meta.getPersistentDataContainer();
            if(container.has(canDrop)){
                if(Boolean.TRUE.equals(container.get(canDrop, PersistentDataType.BOOLEAN))){
                    event.setCancelled(true);
                }
            }
        }
    }

}

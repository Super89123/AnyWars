package org.relaxertime.anyWars.Events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.relaxertime.anyWars.AnyWars;


import java.util.HashMap;
import java.util.Map;

public class BlockEvents implements Listener {
    private final AnyWars plugin;
    private final boolean isGameStarted;
    private Map<Block, Location> blockMap = new HashMap<>();
    public BlockEvents(AnyWars plugin){
        this.plugin = plugin;
        this.isGameStarted = plugin.getisGameStarted();
    }
    @EventHandler
    public void placeEvent(BlockPlaceEvent event){
        if(isGameStarted){
            blockMap.put(event.getBlockPlaced(), event.getBlockPlaced().getLocation());
        }


    }
    @EventHandler
    public void breakEvent(BlockBreakEvent event){
        if(isGameStarted && blockMap.get(event.getBlock()) != null){
            blockMap.remove(event.getBlock());
            event.setCancelled(true);
        }
    }
}
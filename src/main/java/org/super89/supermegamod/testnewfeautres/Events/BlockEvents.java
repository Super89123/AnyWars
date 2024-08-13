package org.super89.supermegamod.testnewfeautres.Events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.super89.supermegamod.testnewfeautres.TestNewFeautres;

import java.util.HashMap;
import java.util.Map;

public class BlockEvents implements Listener {
    private final TestNewFeautres plugin;
    private final boolean isGameStarted;
    private Map<Block, Location> blockMap = new HashMap<>();
    public BlockEvents(TestNewFeautres plugin){
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

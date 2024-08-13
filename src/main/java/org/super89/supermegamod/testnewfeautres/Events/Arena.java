package org.super89.supermegamod.testnewfeautres.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.super89.supermegamod.testnewfeautres.TestNewFeautres;

public class Arena implements Listener {
    private final TestNewFeautres pluign;

    public Arena(TestNewFeautres pluign) {
        this.pluign = pluign;
    }

    @EventHandler
    public void playerJoin(PlayerChangedWorldEvent event){
        if(pluign.getArenaType().equals("world")){

        }
    }
}

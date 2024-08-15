package org.relaxertime.anyWars.Utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;



public class ArmorStandUtils {
    private ArmorStand armorStand;
    private final Location location;
    private final String name;


    public ArmorStandUtils(Location location, String name){
        this.location = location;
        this.name = name;

        createArmorStand();
    }

    public void createArmorStand(){

        World world = location.getWorld();
        ArmorStand armorStand = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setInvisible(true);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(name);
        armorStand.setBasePlate(false);
        armorStand.setGravity(false);
        this.armorStand = armorStand;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }
}
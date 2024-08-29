package org.relaxertime.anyWars.Handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scoreboard.Team;

public class ColorHandler {
    private int hex;
    private int r;
    private int g;
    private int b;

    public ColorHandler(int hex) {
        this.hex = hex;
        this.r = (hex >> 16) & 0xFF;
        this.g = (hex >> 8) & 0xFF;
        this.b = hex & 0xFF;
    }

    public ColorHandler(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.hex = (r << 16) | (g << 8) | b;
    }

    public int getHex() {
        return hex;
    }

    public TextColor getTextColor() {
        return TextColor.color(r, g, b);
    }

    public void setColorToPlayer(Player player) {
        player.displayName(Component.text(player.getName(), getTextColor()));

    }

    public void giveColoredLeatherArmor(Player player) {
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        ItemStack bots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) chest.getItemMeta();
        meta.setColor(getColor());
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        chest.setItemMeta(meta);
        leg.setItemMeta(meta);
        helm.setItemMeta(meta);
        bots.setItemMeta(meta);
        player.getInventory().setChestplate(chest);
        player.getInventory().setBoots(bots);
        player.getInventory().setLeggings(leg);
        player.getInventory().setHelmet(helm);
    }

    public org.bukkit.Color getColor() {
        return org.bukkit.Color.fromRGB(r, g, b);
    }
}
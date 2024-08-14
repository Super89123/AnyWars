package org.super89.supermegamod.testnewfeautres.Utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.super89.supermegamod.testnewfeautres.TestNewFeautres;

public class ItemUtils {
    private final TestNewFeautres plugin ;

    public ItemUtils(TestNewFeautres plugin) {
        this.plugin = plugin;
    }


    public  ItemStack createUnThrowableItem(Material material, String name, int count){
        NamespacedKey unThrowAbleKey = new NamespacedKey(plugin, "canDrop");
        ItemStack itemStack = new ItemStack(material);
        itemStack.setAmount(count);
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();
        persistentDataContainer.set(unThrowAbleKey, PersistentDataType.BOOLEAN, true);
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}

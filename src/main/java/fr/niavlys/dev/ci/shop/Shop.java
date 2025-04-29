package fr.niavlys.dev.ci.shop;

import fr.niavlys.dev.gm.main.GradientManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;

public class Shop {

    public static String getHomeTitle(){
        return ShopCategorie.getHomeTitle();
    }
    public static Integer getHomeSize(){
        return ShopCategorie.getHomeSize();
    }

    public static String buildCategoryTitle(ShopCategorie categorie){
        return GradientManager.createGradientTitle(categorie.getName(), Color.RED, Color.BLUE);
    }

    public static Inventory buildHome(){
        Inventory inv = Bukkit.createInventory(null, getHomeSize(), getHomeTitle());
        for(ShopCategorie categorie : ShopCategorie.values()){
            ItemStack item = new ItemStack(categorie.getIcon());
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(buildCategoryTitle(categorie));
            itemMeta.setEnchantmentGlintOverride(true);
            item.setItemMeta(itemMeta);
            inv.setItem(categorie.getPlace(), item);
        }
        return inv;
    }

    public static Inventory buildCategory(ShopCategorie categorie){
        Inventory cat = Bukkit.createInventory(null, 9*6, buildCategoryTitle(categorie));
        for(ShopItem item : ShopItem.values()){
            // TODO: J'en suis a la, finir le shop
        }
        return cat;
    }
}

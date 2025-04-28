package fr.niavlys.dev.ci.shop;

import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.traduction.Language;
import fr.niavlys.dev.ci.traduction.Traduction;
import fr.niavlys.dev.gm.main.GradientManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.Locale;

public enum ShopCategories {
    Blocs(Traduction.CategoryBlocs, ChatColor.GRAY, Material.GRASS_BLOCK, 1),
    Nourriture(Traduction.CategoryNourriture, ChatColor.GREEN, Material.COOKED_BEEF, 2),
    Minerais(Traduction.CategoryMinerais, ChatColor.GOLD, Material.IRON_INGOT, 4),
    Mobs(Traduction.CategoryMobs, ChatColor.LIGHT_PURPLE, Material.SPIDER_EYE, 6),
    Autres(Traduction.CategoryAutres, ChatColor.BLUE, Material.SCAFFOLDING, 7);

    private final Traduction name;
    private final ChatColor color;
    private final Material icon;
    private final Integer slot;

    ShopCategories(Traduction name, ChatColor color, Material icon, Integer slot){
        this.name = name;
        this.color = color;
        this.icon = icon;
        this.slot = slot;
    }
    public ChatColor getColor() {
        return color;
    }
    public Traduction getName() {
        return name;
    }
    public Integer getSlot() {
        return slot;
    }
    public Material getIcon() {
        return icon;
    }
    public ItemStack buildIcon(Language lang){
        ItemStack item = new ItemStack(this.getIcon());
        ItemMeta data = item.getItemMeta();
        if(data == null){
            return item;
        }
        data.setDisplayName(this.getColor() + this.getName().getByLang(lang));
        return item;
    }

    public static Inventory buildHome(CIPlayer player){
        Inventory home = Bukkit.createInventory(null, 9, GradientManager.createGradientTitle("Shop", Color.RED, Color.BLUE));
        for(ShopCategories cat : ShopCategories.values()){
            ItemStack icon = new ItemStack(cat.getIcon());
            ItemMeta iconMeta = icon.getItemMeta();
            if(iconMeta == null){
                return home;
            }
            iconMeta.setDisplayName(GradientManager.createGradientTitle(cat.name.getByLang(player.getLang()), Color.RED, Color.BLUE));
            icon.setItemMeta(iconMeta);
            home.setItem(cat.getSlot(), icon);
        }
        return home;
    }

    public static Inventory buildShop(CIPlayer player, ShopCategories category){
        Inventory shop = Bukkit.createInventory(null, 54, GradientManager.createGradientTitle(category.getName().getByLang(player.getLang()), Color.RED, Color.BLUE));
        for(ShopItem item : ShopItem.values()){
            if(item.getCategory() != category){
                continue;
            }
            shop.addItem(item.buildItem(player));
        }
        return shop;
    }
}

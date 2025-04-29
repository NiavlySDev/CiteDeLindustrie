package fr.niavlys.dev.ci.shop;

import fr.niavlys.dev.gm.main.GradientManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;

public enum ShopItem {

    ;

    private final String name;
    private final ShopCategorie categorie;
    private final Material mat;
    private ItemStack icon;

    ShopItem(ShopCategorie categorie, String name, Material mat){
        this.categorie = categorie;
        this.mat = mat;
        this.icon = buildIcon();
        this.name = name;
    }

    public ItemStack getIcon() {
        return icon;
    }
    public ItemStack buildIcon(){
        this.icon = new ItemStack(this.getMat());
        ItemMeta iconMeta = this.icon.getItemMeta();
        iconMeta.setDisplayName(GradientManager.createGradientTitle(this.getName(), Color.RED, Color.BLUE));
        // TODO: J'en suis a la, finir le shop
        return this.icon;
    }
    public Material getMat() {
        return mat;
    }
    public ShopCategorie getCategorie() {
        return categorie;
    }
    public String getName() {
        return name;
    }
}

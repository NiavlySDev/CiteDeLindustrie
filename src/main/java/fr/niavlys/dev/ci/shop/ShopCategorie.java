package fr.niavlys.dev.ci.shop;

import fr.niavlys.dev.gm.main.GradientManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;

public enum ShopCategorie {
    // 0 1 2 3 4 5 6 7 8
    // B R M R C R U R A
    Blocs("Blocs", Material.STONE, 0),
    Minerais("Minerais", Material.COAL_ORE, 2),
    Creatures("Creatures", Material.ROTTEN_FLESH, 4),
    Utilities("Utilitaires", Material.HOPPER,6),
    Autre("Autre", Material.DIAMOND, 8),
    ;

    private static final Integer HOME_SIZE = 9;
    private static final String HOME_TITLE = GradientManager.createGradientTitle("Shop", Color.RED, Color.BLUE);

    private final String name;
    private final Material Icon;
    private final Integer place;

    ShopCategorie(String name, Material Icon, Integer place){
        this.name = name;
        this.Icon = Icon;
        this.place = place;
    }

    public String getName() {
        return name;
    }
    public Integer getPlace() {
        return place;
    }
    public Material getIcon() {
        return Icon;
    }

    public static String getHomeTitle(){
        return HOME_TITLE;
    }
    public static Integer getHomeSize(){
        return HOME_SIZE;
    }

}
package fr.niavlys.dev.ci.inventory.shop;

import org.bukkit.ChatColor;

public enum ShopRarete {
    Commun("Commun", ChatColor.GREEN, 10),
    Rare("Rare", ChatColor.BLUE, 50),
    Epic("Epique", ChatColor.LIGHT_PURPLE, 100),
    Legendary("Légendaire", ChatColor.GOLD, 500),
    Mythical("Mythique", ChatColor.RED, 1000),
    Eternal("Eternel", ChatColor.YELLOW, 5000),
    Exotique("Exotique", ChatColor.AQUA, 10000);

    private final String name;
    private final ChatColor color;
    private final int price;
    ShopRarete(String name, ChatColor color, int price){
        this.name = name;
        this.color = color;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public ChatColor getColor() {
        return color;
    }
    public int getPrice() {
        return price;
    }
}

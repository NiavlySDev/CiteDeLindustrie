package fr.niavlys.dev.ci.shop;

import fr.niavlys.dev.ci.traduction.Traduction;
import org.bukkit.ChatColor;

public enum ShopRarete {

    Commun(Traduction.RareteCommun, ChatColor.GRAY, 1),
    Rare(Traduction.RareteRare, ChatColor.BLUE, 2),
    Epique(Traduction.RareteEpique, ChatColor.LIGHT_PURPLE, 4),
    Legendaire(Traduction.RareteLegendaire, ChatColor.GOLD, 8),
    Mythique(Traduction.RareteMythique, ChatColor.RED, 16),
    Exotique(Traduction.RareteExotique, ChatColor.AQUA, 32),
    Eternel(Traduction.RareteEternel, ChatColor.DARK_PURPLE, 64);

    private final Traduction name;
    private final ChatColor color;
    private final Integer coefficient;
    private ShopRarete(Traduction name, ChatColor color, Integer coefficient){
        this.name = name;
        this.color = color;
        this.coefficient = coefficient;
    }
    public ChatColor getColor() {
        return color;
    }
    public Traduction getName() {
        return name;
    }
    public Integer getCoefficient() {
        return coefficient;
    }
}

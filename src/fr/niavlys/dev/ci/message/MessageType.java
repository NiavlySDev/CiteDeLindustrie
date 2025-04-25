package fr.niavlys.dev.ci.message;

import fr.niavlys.dev.ci.traduction.Traduction;
import org.bukkit.ChatColor;

public enum MessageType {
    Info(Traduction.Info, ChatColor.AQUA),
    Error(Traduction.Erreur, ChatColor.RED),
    Warning(Traduction.Warning, ChatColor.GOLD),
    Success(Traduction.Success, ChatColor.GREEN);

    private final Traduction name;
    private final ChatColor color;
    MessageType(Traduction name, ChatColor color){
        this.name = name;
        this.color = color;
    }
    public ChatColor getColor() {
        return color;
    }
    public Traduction getName() {
        return name;
    }
}

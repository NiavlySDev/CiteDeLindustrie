package fr.niavlys.dev.ci.message;

import org.bukkit.ChatColor;

public enum MessageType {
    Info("Information", ChatColor.AQUA),
    Error("Erreur", ChatColor.RED),
    Warning("Warning", ChatColor.GOLD),
    Success("Succès", ChatColor.GREEN);

    private final String name;
    private final ChatColor color;
    MessageType(String name, ChatColor color){
        this.name = name;
        this.color = color;
    }
    public ChatColor getColor() {
        return color;
    }
    public String getName() {
        return name;
    }
}

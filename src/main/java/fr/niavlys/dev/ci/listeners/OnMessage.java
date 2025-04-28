package fr.niavlys.dev.ci.listeners;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.gm.main.GradientManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.awt.*;

public class OnMessage implements Listener {

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        CIPlayer player = BDD.getPlayer(p.getUniqueId());
        Bukkit.broadcastMessage(player.getGrade().getGrade().getColor() + "["+player.getGrade().getGrade().getDisplayName()+"] " + player.getName() + ": " + GradientManager.createGradientTitle(e.getMessage(), Color.RED, Color.BLUE));
        e.setCancelled(true);
    }
}

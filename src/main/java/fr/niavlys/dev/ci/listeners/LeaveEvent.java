package fr.niavlys.dev.ci.listeners;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.players.CIPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        CIPlayer player = BDD.getPlayer(p.getUniqueId());
        player.savePlayer();
        System.out.println(player.getName() + " a quitte le serveur!");
        e.setQuitMessage(player.getGrade().getGrade().getColor() + "["+player.getGrade().getGrade().getDisplayName()+"] " + player.getName() + " a quitté le serveur.");
    }

}

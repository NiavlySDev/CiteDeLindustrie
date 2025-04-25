package fr.niavlys.dev.ci.players;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        CIPlayer player = new CIPlayer();
        player.loadPlayer();
        System.out.println(player.getName() + " a rejoint le serveur!");
        e.setJoinMessage(player.getGrade().getGrade().getColor() + "["+player.getGrade().getGrade().getDisplayName()+"]" + player.getName() + " a rejoin le serveur.");

    }
}

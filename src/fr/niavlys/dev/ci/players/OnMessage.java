package fr.niavlys.dev.ci.players;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.pdm.main.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnMessage implements Listener {

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        CIPlayer player = BDD.getPlayer(p.getUniqueId());
        e.setMessage(player.getGrade().getGrade().getColor() + "["+player.getGrade().getGrade().getDisplayName()+"]" + player.getName() + ": " + e.getMessage() );
    }
}

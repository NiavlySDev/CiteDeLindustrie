package fr.niavlys.dev.ci.listeners;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.quests.manager.QuestManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class WalkListener implements Listener {

    @EventHandler
    public void onWalk(PlayerMoveEvent e){
        Player p = e.getPlayer();
        CIPlayer player = BDD.getPlayer(p.getUniqueId());

        QuestManager.WalkQuest(e, p, player);
    }
}

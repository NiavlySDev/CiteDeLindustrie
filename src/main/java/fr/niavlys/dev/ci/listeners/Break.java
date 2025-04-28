package fr.niavlys.dev.ci.listeners;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.quests.QuestManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Break implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        CIPlayer player = BDD.getPlayer(p.getUniqueId());

        if(QuestManager.BreakQuest(e, p, player)){
            return;
        }

    }

}

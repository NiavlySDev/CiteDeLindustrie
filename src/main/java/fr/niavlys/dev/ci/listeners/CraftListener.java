package fr.niavlys.dev.ci.listeners;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.quests.manager.QuestManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftListener implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if(!(e.getView().getPlayer() instanceof Player)) return;
        Player p = (Player) e.getWhoClicked();
        CIPlayer player = BDD.getPlayer(p.getUniqueId());

        QuestManager.CraftQuest(e, p, player);
    }

}

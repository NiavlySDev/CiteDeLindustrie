package fr.niavlys.dev.ci.inventory;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.economy.convertisseur.ConvertisseurGUI;
import fr.niavlys.dev.ci.players.CIPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.awt.*;

public class GUIListener implements Listener {

    @EventHandler
    public void onClic(InventoryClickEvent e){
        if(!(e.getWhoClicked() instanceof Player)){
            System.err.println("GUIListener: Player null (GUIListener:10)");
            return;
        }
        Player p = (Player) e.getWhoClicked();
        CIPlayer player = BDD.getPlayer(p.getUniqueId());
        if(player == null){
            System.err.println("GUIListener: CIPlayer null (GUIListener:13)");
            return;
        }

        String invTitle = e.getView().getTitle();

        if(invTitle.equals(ConvertisseurGUI.getTitle())){
            ConvertisseurGUI.Action(e, p, player);
        }

    }
}

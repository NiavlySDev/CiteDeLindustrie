package fr.niavlys.dev.ci.economy.convertisseur;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.players.CIPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ConvertMoneyC implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
        if(!(s instanceof Player)){
            System.err.println("Vous ne pouvez pas executer cette commande ne tant que console.");
            return false;
        }
        Player p = (Player) s;
        CIPlayer player = BDD.getPlayer(p.getUniqueId());
        Inventory inv = ConvertisseurGUI.buildConvertisseur(player);
        p.openInventory(inv);
        return false;
    }
}

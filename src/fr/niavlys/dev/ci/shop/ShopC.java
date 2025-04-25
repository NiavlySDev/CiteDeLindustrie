package fr.niavlys.dev.ci.shop;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.players.CIPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class ShopC implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
        if(!(s instanceof Player)){
            s.sendMessage("Vous ne pouvez pas ouvrir le shop avec la console!");
            return false;
        }
        Player p = (Player) s;
        CIPlayer player = BDD.getPlayer(p.getUniqueId());
        if(player == null){
            System.err.println("ShopC: CIPlayer null (ShopC:23)");
            return false;
        }
        Inventory shop = ShopCategories.buildHome(player);
        p.openInventory(shop);
        return false;
    }
}

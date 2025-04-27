package fr.niavlys.dev.ci.shop;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.main.Main;
import fr.niavlys.dev.ci.message.MessageType;
import fr.niavlys.dev.ci.message.Messages;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.traduction.Traduction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadShopPriceC implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
        Main.loadShopPriceForced();
        if(!(s instanceof Player)){
            System.out.println("ShopPrices reloaded!");
        }
        else{
            Player p = (Player) s;
            CIPlayer player = BDD.getPlayer(p.getUniqueId());
            if(player == null){
                System.err.println("ReloadShopPriceC: CIPlayer null (ReloadShopPriceC:23)");
            }
            Messages.send(Traduction.ShopPriceReloaded, MessageType.Success, p);
        }
        return false;
    }
}

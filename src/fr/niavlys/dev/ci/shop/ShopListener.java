package fr.niavlys.dev.ci.shop;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.message.MessageType;
import fr.niavlys.dev.ci.message.Messages;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.traduction.Traduction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {

    @EventHandler
    public void onClicShop(InventoryClickEvent e){
        if(!(e.getWhoClicked() instanceof Player)){
            System.err.println("ShopListener: Player null (ShopListener:10)");
            return;
        }
        Player p = (Player) e.getWhoClicked();
        CIPlayer player = BDD.getPlayer(p.getUniqueId());
        if(player == null){
            System.err.println("ShopListener: CIPlayer null (ShopListener:13)");
            return;
        }
        e.setCancelled(true);
        if(e.getCurrentItem() == null){
            System.err.println("ShopListener: Item null (ShopListener:16)");
            return;
        }
        ItemStack item = e.getCurrentItem();
        Material mat = item.getType();
        ShopItem shopItem = ShopItem.getByMaterial(mat);
        if(shopItem == null){
            System.err.println("ShopListener: ShopItem null (ShopListener:19)");
            return;
        }

        Integer nbItem = 0;
        for(ItemStack invItem : p.getInventory().getContents()){
            if(invItem == null){
                continue;
            }
            if(invItem.getType() != mat){
                continue;
            }
            nbItem += invItem.getAmount();
        }

        InventoryAction action = e.getAction();
        if(action == InventoryAction.PICKUP_ALL){
            if(nbItem <=1){
                Messages.send(Traduction.ErreurPasAssezShop, MessageType.Error, p);
            }
            p.getInventory().removeItem(new ItemStack(mat, 1));
            player.getBalance().getBronze().add(1); // TODO: Shop Prices
            Messages.send(Traduction.ValidationVente, MessageType.Success, p);
        }
        else if(action == InventoryAction.PICKUP_HALF){
            if(nbItem <=64){
                Messages.send(Traduction.ErreurPasAssezShop, MessageType.Error, p);
            }
            p.getInventory().removeItem(new ItemStack(mat, 64));
            player.getBalance().getBronze().add(1*64);
            Messages.send(Traduction.ValidationVente, MessageType.Success, p);
        }
        else if(action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            p.getInventory().remove(item);
            player.getBalance().getBronze().add(1*nbItem);
            Messages.send(Traduction.ValidationVente, MessageType.Success, p);
        }
    }
}

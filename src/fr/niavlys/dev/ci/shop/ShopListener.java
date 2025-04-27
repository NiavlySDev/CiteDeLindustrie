package fr.niavlys.dev.ci.shop;

import fr.niavlys.dev.bn.main.BigNumbers;
import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.main.Main;
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
import org.bukkit.inventory.Inventory;
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
        String title = e.getView().getTitle();
        if (!title.equals("Shop") && !title.startsWith(ShopCategories.Blocs.getColor().toString())
                && !title.startsWith(ShopCategories.Nourriture.getColor().toString())
                && !title.startsWith(ShopCategories.Minerais.getColor().toString())
                && !title.startsWith(ShopCategories.Mobs.getColor().toString())
                && !title.startsWith(ShopCategories.Autres.getColor().toString())) {
            return;
        }
        e.setCancelled(true);
        if(e.getCurrentItem() == null){
            System.err.println("ShopListener: Item null (ShopListener:16)");
            return;
        }
        if(e.getView().getTitle().equalsIgnoreCase("Shop")){
            Home(e, p, player);
            return;
        }
        ItemStack item = e.getCurrentItem();
        Material mat = item.getType();
        ShopItem shopItem = ShopItem.getByMaterial(mat);
        Integer price = (Integer) Main.shopPrices.get(shopItem.name(), "price");
        BigNumbers price1 = new BigNumbers(price);
        BigNumbers price64 = new BigNumbers(price);
        price64.multiply(64);
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
                return;
            }
            p.getInventory().removeItem(new ItemStack(mat, 1));
            player.getBalance().getBronze().add(price1);
            Messages.send(Messages.build(Traduction.ValidationVente, MessageType.Success, player).replaceAll("%argent%", price1.toString()), p);
            nbItem-=1;
        }
        else if(action == InventoryAction.PICKUP_HALF){
            if(nbItem <=64){
                Messages.send(Traduction.ErreurPasAssezShop, MessageType.Error, p);
                return;
            }
            p.getInventory().removeItem(new ItemStack(mat, 64));
            player.getBalance().getBronze().add(price64);
            Messages.send(Messages.build(Traduction.ValidationVente, MessageType.Success, player).replaceAll("%argent%", price64.toString()), p);
            nbItem-=64;
        } else if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            if (nbItem <= 0) {
                Messages.send(Traduction.ErreurPasAssezShop, MessageType.Error, p);
                return;
            }
            p.getInventory().removeItem(new ItemStack(mat, nbItem));
            BigNumbers price2304 = new BigNumbers(price);
            price2304.multiply(nbItem);
            player.getBalance().getBronze().add(price2304);
            Messages.send(Messages.build(Traduction.ValidationVente, MessageType.Success, player).replaceAll("%argent%", price2304.toString()), p);
            nbItem-=nbItem;
            if(nbItem <=0){
                return;
            }
        }
    }

    private void Home(InventoryClickEvent e, Player p, CIPlayer player) {
        Inventory inv;
        if(e.getCurrentItem().getType().equals(ShopCategories.Blocs.buildIcon(player.getLang()).getType())){
            inv = ShopCategories.buildShop(player, ShopCategories.Blocs);
            System.out.println("Blocs");
        } else if (e.getCurrentItem().getType().equals(ShopCategories.Nourriture.buildIcon(player.getLang()).getType())) {
            inv = ShopCategories.buildShop(player, ShopCategories.Nourriture);
            System.out.println("Nourriture");
        } else if (e.getCurrentItem().getType().equals(ShopCategories.Minerais.buildIcon(player.getLang()).getType())) {
            inv = ShopCategories.buildShop(player, ShopCategories.Minerais);
            System.out.println("Minerais");
        } else if (e.getCurrentItem().getType().equals(ShopCategories.Mobs.buildIcon(player.getLang()).getType())) {
            inv = ShopCategories.buildShop(player, ShopCategories.Mobs);
            System.out.println("Mobs");
        } else if (e.getCurrentItem().getType().equals(ShopCategories.Autres.buildIcon(player.getLang()).getType())) {
            inv = ShopCategories.buildShop(player, ShopCategories.Autres);
            System.out.println("Autres");
        }
        else{
            System.err.println("ShopListener: ShopCategories null (ShopListener:39)");
            return;
        }
        System.out.println("Shop Ouvert");
        p.openInventory(inv);
        e.setCancelled(true);
    }
}

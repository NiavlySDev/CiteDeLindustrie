package fr.niavlys.dev.ci.economy.convertisseur;

import fr.niavlys.dev.bn.main.BigNumbers;
import fr.niavlys.dev.ci.message.MessageType;
import fr.niavlys.dev.ci.message.Messages;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.gm.main.GradientManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.Arrays;

public class ConvertisseurGUI {
    private static String title = GradientManager.createGradientTitle("Convertisseur de Monnaie", Color.RED, Color.BLUE);
    public static ItemStack bronze;
    public static ItemStack argent;
    public static ItemStack or;

    public static String getTitle() {
        return title;
    }
    public static ItemStack getBronze(){
        return bronze;
    }
    public static ItemStack getArgent(){
        return argent;
    }
    public static ItemStack getOr(){
        return or;
    }
    public static void setBronze(ItemStack bronze) {
        ConvertisseurGUI.bronze = bronze;
    }
    public static void setArgent(ItemStack argent) {
        ConvertisseurGUI.argent = argent;
    }
    public static void setOr(ItemStack or) {
        ConvertisseurGUI.or = or;
    }

    public static Inventory buildConvertisseur(){
        String title;
        String str1;
        String str2;

        title = GradientManager.createGradientTitle("Bronze", Color.ORANGE, Color.GREEN);
        str1 = GradientManager.createGradientTitle("Clic Droit: Vendre 1 d'Argent pour 64K de Bronze", Color.WHITE, Color.ORANGE);
        setBronze(buildItem(Material.COPPER_BLOCK, title, str1, null));

        title = GradientManager.createGradientTitle("Argent", Color.WHITE, Color.GRAY);
        str1 = GradientManager.createGradientTitle("Clic Droit: Vendre 1 d'Or pour 64 d'Argent", Color.YELLOW, Color.WHITE);
        str2 = GradientManager.createGradientTitle("Clic Gauche: Acheter 1 d'Argent pour 64K de Bronze", Color.ORANGE, Color.WHITE);
        setArgent(buildItem(Material.IRON_BLOCK, title, str1, str2));

        title = GradientManager.createGradientTitle("Or", Color.YELLOW, Color.RED);
        str2 = GradientManager.createGradientTitle("Clic Gauche: Acheter 1 d'Or pour 64 d'Argent", Color.WHITE, Color.YELLOW);
        setOr(buildItem(Material.GOLD_BLOCK, title, str2, null));

        Inventory inv = Bukkit.createInventory(null, 9, GradientManager.createGradientTitle("Convertisseur de Monnaie", Color.RED, Color.BLUE));
        inv.setItem(0, getBronze());
        inv.setItem(4, getArgent());
        inv.setItem(8, getOr());

        return inv;
    }
    private static ItemStack buildItem(Material mat, String name, String lore1, String lore2){
        ItemStack item = new ItemStack(mat);
        ItemMeta iM = item.getItemMeta();
        iM.setDisplayName(name);
        iM.setEnchantmentGlintOverride(true);
        if(lore2 == null){
            iM.setLore(Arrays.asList(lore1));
        }
        else{
            iM.setLore(Arrays.asList(lore1, lore2));
        }
        item.setItemMeta(iM);
        return item;
    }
    public static void Action(InventoryClickEvent e, Player p, CIPlayer player){
        ItemStack item = e.getCurrentItem();
        e.setCancelled(true);
        if(item == null){
            return;
        }
        InventoryAction action = e.getAction();
        if(item.equals(getBronze())){
            if (action.equals(InventoryAction.PICKUP_HALF)) {
                if(player.getBalance().getArgent().superieur(new BigNumbers(1))){
                    player.getBalance().getArgent().remove(1);
                    player.getBalance().getBronze().add(new BigNumbers(64, "K"));
                    Messages.send("Vous avez vendu 1 d'Argent pour 64K de Bronze", MessageType.Success, p);
                }
            }
            else{
                Messages.send("Vous n'avez pas assez de ce type d'argent dans votre inventaire", MessageType.Error, p);
                return;
            }
        }
        else if (item.equals(getArgent())) {
            if(action.equals(InventoryAction.PICKUP_ALL)){
                if(player.getBalance().getBronze().superieur(new BigNumbers(64, "K"))){
                    player.getBalance().getBronze().remove(new BigNumbers(64, "K"));
                    player.getBalance().getArgent().add(1);
                    Messages.send("Vous avez acheter 1 d'Argent pour 64K de Bronze", MessageType.Success, p);
                }
                else{
                    Messages.send("Vous n'avez pas assez de ce type d'argent dans votre inventaire", MessageType.Error, p);
                    return;
                }
            } else if (action.equals(InventoryAction.PICKUP_ALL)) {
                if(player.getBalance().getOr().superieur(new BigNumbers(1))){
                    player.getBalance().getOr().remove(1);
                    player.getBalance().getArgent().add(64);
                    Messages.send("Vous avez vendu 1 d'Or pour 64 d'Argent", MessageType.Success, p);
                }
                else{
                    Messages.send("Vous n'avez pas assez de ce type d'argent dans votre inventaire", MessageType.Error, p);
                    return;
                }
            }
        }
        else{
            if(action.equals(InventoryAction.PICKUP_ALL)){
                if(player.getBalance().getArgent().superieur(new BigNumbers(64))){
                    player.getBalance().getArgent().remove(64);
                    player.getBalance().getOr().add(1);
                    Messages.send("Vous avez acheter 1 d'Or pour 64 d'Argent", MessageType.Success, p);
                }
                else{
                    Messages.send("Vous n'avez pas assez de ce type d'argent dans votre inventaire", MessageType.Error, p);
                    return;
                }
            }
        }
    }
}

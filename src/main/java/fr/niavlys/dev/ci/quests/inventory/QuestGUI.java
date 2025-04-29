package fr.niavlys.dev.ci.quests.inventory;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.quests.QuestLink;
import fr.niavlys.dev.gm.main.GradientManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class QuestGUI {

    private static String title;
    private static final int size = 9*6;
    private static Inventory inv;

    public static String getTitle(){
        return title;
    }

    public static void init(){
        title = GradientManager.createGradientTitle("Quests", Color.RED, Color.BLUE);
        inv = Bukkit.createInventory(null, size, title);
    }

    public static Inventory build(Player p){
        List<QuestLink> quests = BDD.getQuestsByPlayer(p);
        for(QuestLink link : quests){
            ItemStack questItem = new ItemStack(link.getQuest().getMat());
            ItemMeta questItemMeta = questItem.getItemMeta();

            String questName = link.getQuest().getQuest().getName();
            String questLevel = link.getTier().getLevel().toString();
            String questLevelMax = link.getTier().getLevelMax().toString();
            String questAvancement = link.getTier().getAmount().toString();
            String questAvancementMax = link.getTier().getAmountMaxForLevel(link.getTier().getLevel()).toString();
            String pourcentage = link.getTier().getPercentage().toString();

            questItemMeta.setDisplayName(GradientManager.createGradientTitle(questName, Color.RED, Color.BLUE));
            questItemMeta.setLore(Arrays.asList(
                    "Level: "+questLevel+"/"+questLevelMax,
                    "Pourcentage: ("+pourcentage+"%)",
                    "Avancement: "+questAvancement+"/"+questAvancementMax
            ));
            questItemMeta.setEnchantmentGlintOverride(true);
            questItem.setItemMeta(questItemMeta);
            inv.addItem(questItem);
        }
        return inv;
    }

    public static void Action(InventoryClickEvent e, Player p, CIPlayer player) {
        e.setCancelled(true);
    }
}
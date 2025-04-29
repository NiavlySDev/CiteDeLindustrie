package fr.niavlys.dev.ci.quests.inventory;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.message.MessageType;
import fr.niavlys.dev.ci.message.Messages;
import fr.niavlys.dev.ci.message.commonMessages.ValidationMessage;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.quests.QuestLink;
import fr.niavlys.dev.gm.main.GradientManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
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
    private static final Integer MAX_QUEST_STARTED = 5;

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
            String started = link.isStarted() ? ChatColor.GREEN+"Oui" : ChatColor.RED+"Non";
            String questLevel = link.getTier().getLevel().toString();
            String questLevelMax = link.getTier().getLevelMax().toString();
            String questAvancement = link.getTier().getAmount().toString();
            String questAvancementMax = link.getTier().getAmountMaxForLevel(link.getTier().getLevel()).toString();
            String pourcentage = link.getTier().getPercentage().toString();

            questItemMeta.setDisplayName(GradientManager.createGradientTitle(questName, Color.RED, Color.BLUE));
            questItemMeta.setLore(Arrays.asList(
                    ChatColor.GOLD+"Lancée: "+started,
                    ChatColor.GOLD+"Level: "+ChatColor.WHITE+questLevel+"/"+questLevelMax,
                    ChatColor.GOLD+"Pourcentage: "+ChatColor.WHITE+"("+pourcentage+"%)",
                    ChatColor.GOLD+"Avancement: "+ChatColor.WHITE+questAvancement+"/"+questAvancementMax,
                    ChatColor.GOLD+"Clic Gauche: "+ChatColor.GREEN+"Lancer"+ChatColor.AQUA+" la Quête",
                    ChatColor.GOLD+"Clic Droit: "+ChatColor.RED+"Arrêter "+ChatColor.AQUA+"la Quête",
                    ChatColor.RED+"Attention! "+ChatColor.GOLD+"Si vous arrêtez une quête,",
                    ChatColor.GOLD+"vous perdrez votre progression actuelle",
                    ChatColor.GOLD+"pas votre level, mais votre avancement"
            ));
            questItemMeta.setEnchantmentGlintOverride(true);
            questItem.setItemMeta(questItemMeta);
            inv.addItem(questItem);
        }
        return inv;
    }

    public static void Action(InventoryClickEvent e, Player p, CIPlayer player) {
        e.setCancelled(true);
        ItemStack item = e.getCurrentItem();
        if(item == null){
            return;
        }
        Material itemMat = item.getType();
        List<QuestLink> quests = BDD.getQuestsByPlayer(p);
        QuestLink quest = null;
        for(QuestLink link : quests){
            if(link.getQuest().getMat().equals(itemMat)){
                quest = link;
                break;
            }
        }
        InventoryAction action = e.getAction();
        if(action.equals(InventoryAction.PICKUP_ALL)){
            if(quest.isStarted()){
                return;
            }
            if(player.getQuestStarted() >= MAX_QUEST_STARTED){
                Messages.send("Vous avez atteint le nombre maximal de quêtes démarrées en même temps! (5)", MessageType.Error, p);
                return;
            }
            quest.start();
            Messages.send("Vous avez lancé la quête!", MessageType.Success, p);
            player.addQuestStarted(1);
        }
        else if(action.equals(InventoryAction.PICKUP_HALF)){
            if(!quest.isStarted()){
                return;
            }
            quest.stop();
            Messages.send("Vous avez arrêté la quête!", MessageType.Success, p);
            player.removeQuestStarted(1);
        }
        p.closeInventory();
        p.openInventory(build(p));
    }
}
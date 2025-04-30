package fr.niavlys.dev.ci.quests.manager;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.quests.QuestLink;
import fr.niavlys.dev.ci.quests.QuestList;
import fr.niavlys.dev.ci.quests.QuestType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;

import java.util.ArrayList;
import java.util.List;

public class CraftQuest {
    public static void AvancementQuest(CraftItemEvent e, Player p, CIPlayer player, QuestList questList) {
        List<QuestLink> playerQuests = BDD.getQuestsByPlayer(p);
        List<QuestLink> breakQuest = new ArrayList<>();
        for(QuestLink link : playerQuests){
            if(link.getQuest().getType().equals(QuestType.Mining)){
                breakQuest.add(link);
            }
        }

        Material mat = e.getCurrentItem().getType();
        for(QuestLink link : breakQuest){
            if(link.getQuest().getMat().equals(mat)){
                if(!CommonQuest.Avancement(link, p)) continue;
            }
        }
    }
}

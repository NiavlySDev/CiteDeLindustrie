package fr.niavlys.dev.ci.quests.manager;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.quests.QuestLink;
import fr.niavlys.dev.ci.quests.QuestList;
import fr.niavlys.dev.ci.quests.QuestType;
import fr.niavlys.dev.ci.main.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class BreakQuest {
    public static void AvancementQuest(BlockBreakEvent e, Player p, CIPlayer player, QuestList questList) {
        List<QuestLink> playerQuests = BDD.getQuestsByPlayer(p);
        List<QuestLink> breakQuest = new ArrayList<>();
        for(QuestLink link : playerQuests){
            if(link.getQuest().getQuest().getType().equals(QuestType.Mining)){
                breakQuest.add(link);
            }
        }

        Material mat = e.getBlock().getType();
        for(QuestLink link : breakQuest){
            if(link.getQuest().getMat().equals(mat)){
                if(!link.isStarted()){
                    continue;
                }
                link.getTier().addAmount(1);
                String questName = link.getQuest().getQuest().getName();
                String questLevel = link.getTier().getLevel().toString();
                String questLevelMax = link.getTier().getLevelMax().toString();
                String questAvancement = link.getTier().getAmount().toString();
                String questAvancementMax = link.getTier().getAmountMaxForLevel(link.getTier().getLevel()).toString();
                String pourcentage = link.getTier().getPercentage().toString();
                Utils.sendActionBar(p, questName+"["+questLevel+"/"+questLevelMax+"]: "+questAvancement+"/"+questAvancementMax+" ("+pourcentage+"%)");
            }
        }
    }
}

package fr.niavlys.dev.ci.quests.manager;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.message.MessageType;
import fr.niavlys.dev.ci.message.Messages;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.quests.QuestLink;
import fr.niavlys.dev.ci.quests.QuestList;
import fr.niavlys.dev.ci.quests.QuestType;
import fr.niavlys.dev.ci.main.Utils;
import fr.niavlys.dev.ci.quests.rewards.Reward;
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
            if(link.getQuest().getType().equals(QuestType.Mining)){
                breakQuest.add(link);
            }
        }

        Material mat = e.getBlock().getType();
        for(QuestLink link : breakQuest){
            if(link.getQuest().getMat().equals(mat)){
                if(!CommonQuest.Avancement(link, p)) continue;
            }
        }
    }
}

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
                if(!link.isStarted()){
                    continue;
                }
                link.getTier().addAmount(1);
                boolean leveledup = link.getTier().levelUp();
                String questName = link.getQuest().getName();
                String questLevel = link.getTier().getLevel().toString();
                String questLevelMax = link.getTier().getLevelMax().toString();
                String questAvancement = link.getTier().getAmount().toString();
                String questAvancementMax = link.getTier().getAmountMaxForLevel(link.getTier().getLevel()).toString();
                String pourcentage = link.getTier().getPercentage().toString();
                if(leveledup){
                    Messages.send("Vous avez fini une quête!", MessageType.Quest, p);
                    Messages.send("Quête: "+link.getQuest().getName(), MessageType.Quest, p);
                    Messages.send("Niveau: "+questLevel+"/"+questLevelMax, MessageType.Quest, p);
                    for(Reward reward : link.getQuest().getRewards()){
                        reward.RewardPlayer(p, link.getTier().getLevel());
                    }
                    return;
                }
                Utils.sendActionBar(p, questName+"["+questLevel+"/"+questLevelMax+"]: "+questAvancement+"/"+questAvancementMax+" ("+pourcentage+"%)");
            }
        }
    }
}

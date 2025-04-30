package fr.niavlys.dev.ci.quests.manager;

import fr.niavlys.dev.ci.main.Utils;
import fr.niavlys.dev.ci.message.MessageType;
import fr.niavlys.dev.ci.message.Messages;
import fr.niavlys.dev.ci.quests.QuestLink;
import fr.niavlys.dev.ci.quests.QuestList;
import fr.niavlys.dev.ci.quests.rewards.Reward;
import org.bukkit.entity.Player;

public class CommonQuest {

    public static boolean Avancement(QuestLink link, Player p){
        if(!link.isStarted()){
            return false;
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
            return true;
        }
        Utils.sendActionBar(p, questName+"["+questLevel+"/"+questLevelMax+"]: "+questAvancement+"/"+questAvancementMax+" ("+pourcentage+"%)");
        return true;
    }

}

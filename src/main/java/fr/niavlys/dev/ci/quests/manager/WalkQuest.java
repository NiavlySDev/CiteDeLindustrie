package fr.niavlys.dev.ci.quests.manager;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.main.Utils;
import fr.niavlys.dev.ci.message.MessageType;
import fr.niavlys.dev.ci.message.Messages;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.quests.QuestLink;
import fr.niavlys.dev.ci.quests.QuestList;
import fr.niavlys.dev.ci.quests.QuestType;
import fr.niavlys.dev.ci.quests.rewards.Reward;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class WalkQuest {
    public static void AvancementQuest(PlayerMoveEvent e, Player p, CIPlayer player, QuestList questList) {
        List<QuestLink> playerQuests = BDD.getQuestsByPlayer(p);
        List<QuestLink> walkQuests = new ArrayList<>();
        for(QuestLink link : playerQuests){
            if(link.getQuest().getType().equals(QuestType.Mining)){
                walkQuests.add(link);
            }
        }

        for(QuestLink link : walkQuests){
            if(!CommonQuest.Avancement(link, p)) continue;
        }
    }
}

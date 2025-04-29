package fr.niavlys.dev.ci.quests;

import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.quests.types.BreakQuest;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class QuestManager {
    public static void BreakQuest(BlockBreakEvent e, Player p, CIPlayer player) {
        for(QuestList questList : QuestList.values()){
            if(questList.getType().equals(QuestType.Mining)){
                BreakQuest.AvancementQuest(e, p, player, questList);
            }
        }
    }
}

package fr.niavlys.dev.ci.quests.manager;

import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.quests.QuestList;
import fr.niavlys.dev.ci.quests.QuestType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class QuestManager {
    public static void BreakQuest(BlockBreakEvent e, Player p, CIPlayer player) {
        for(QuestList questList : QuestList.values()){
            if(questList.getType().equals(QuestType.Mining)){
                BreakQuest.AvancementQuest(e, p, player, questList);
            }
        }
    }

    public static void WalkQuest(PlayerMoveEvent e, Player p, CIPlayer player) {
        for(QuestList questList: QuestList.values()){
            if(questList.getType().equals(QuestType.Walking)){
                WalkQuest.AvancementQuest(e, p, player, questList);
            }
        }
    }

    public static void CraftQuest(CraftItemEvent e, Player p, CIPlayer player) {
        for(QuestList questList : QuestList.values()){
            if(questList.getType().equals(QuestType.Crafting)){
                CraftQuest.AvancementQuest(e, p, player, questList);
            }
        }
    }
}

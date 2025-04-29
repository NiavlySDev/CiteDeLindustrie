package fr.niavlys.dev.ci.quests;

import fr.niavlys.dev.ci.players.CIPlayer;
import org.bukkit.entity.Player;

public class QuestLink {

    private final Player p;
    private final CIPlayer player;
    private final Quest quest;
    private final QuestTier tier;

    public QuestLink(Player p, CIPlayer player, Quest quest, QuestTier tier){
        this.p = p;
        this.player = player;
        this.quest = quest;
        this.tier = tier;
    }

    public Player getPlayer(){
        return p;
    }
    public CIPlayer getBDDPlayer(){
        return player;
    }
    public Quest getQuest(){
        return quest;
    }
    public QuestTier getTier(){
        return tier;
    }
}

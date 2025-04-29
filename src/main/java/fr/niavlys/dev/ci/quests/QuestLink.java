package fr.niavlys.dev.ci.quests;

import fr.niavlys.dev.ci.players.CIPlayer;
import org.bukkit.entity.Player;

public class QuestLink {

    private final Player p;
    private final CIPlayer player;
    private final Quest quest;
    private final QuestTier tier;
    private boolean started;

    public QuestLink(Player p, CIPlayer player, Quest quest, QuestTier tier){
        this.p = p;
        this.player = player;
        this.quest = quest;
        this.tier = tier;
        this.started = false;
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

    public void start(){
        this.started = true;
    }
    public boolean isStarted(){
        return started;
    }
    public void stop(){
        this.started = false;
    }
}

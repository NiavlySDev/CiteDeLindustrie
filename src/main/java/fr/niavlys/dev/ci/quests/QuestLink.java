package fr.niavlys.dev.ci.quests;

import fr.niavlys.dev.ci.players.CIPlayer;
import org.bukkit.entity.Player;

public class QuestLink {

    private final Player p;
    private final QuestList list;
    private final QuestTier tier;
    private boolean started;

    public QuestLink(Player p, QuestList list, QuestTier tier){
        this.p = p;
        this.list = list;
        this.tier = tier;
        this.started = false;
    }

    public Player getPlayer(){
        return p;
    }
    public QuestList getQuest(){
        return list;
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

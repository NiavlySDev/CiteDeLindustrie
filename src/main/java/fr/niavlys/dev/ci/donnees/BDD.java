package fr.niavlys.dev.ci.donnees;

import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.quests.QuestLink;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BDD {
    public static HashMap<UUID, CIPlayer> players = new HashMap<>();
    public static List<QuestLink> quests = new ArrayList<>();

    public static CIPlayer getPlayer(UUID uuid){
        return players.get(uuid);
    }
    public static void addPlayer(UUID uuid, CIPlayer player){
        players.put(uuid, player);
    }

    public static List<QuestLink> getQuestsByPlayer(Player p){
        List<QuestLink> playerQuests = new ArrayList<>();
        for(QuestLink link : quests){
            if(link.getPlayer().equals(p)){
                playerQuests.add(link);
            }
        }
        return playerQuests;
    }
}

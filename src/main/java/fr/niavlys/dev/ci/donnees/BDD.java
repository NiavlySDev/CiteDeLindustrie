package fr.niavlys.dev.ci.donnees;

import fr.niavlys.dev.ci.players.CIPlayer;

import java.util.HashMap;
import java.util.UUID;

public class BDD {
    public static HashMap<UUID, CIPlayer> players = new HashMap<>();
    public static CIPlayer getPlayer(UUID uuid){
        return players.get(uuid);
    }
    public static void addPlayer(UUID uuid, CIPlayer player){
        players.put(uuid, player);
    }
}

package fr.niavlys.dev.ci.message;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.players.CIPlayer;
import org.bukkit.entity.Player;

public class Messages {

    public static String build(String msg, MessageType type, CIPlayer player){
        String balise = type.getColor() + "["+type.getName()+"] ";
        if(player == null){
            return buildConsole(msg, type);
        }
        return balise + msg;
    }

    private static String buildConsole(String msg, MessageType type){
        String balise = type.getColor() + "["+type.getName()+"] ";
        return balise + msg;
    }

    public static void send(String msg, MessageType type, Player p){
        CIPlayer player = BDD.getPlayer(p.getUniqueId());
        String msg2 = Messages.build(msg, type, player);
        Messages.send(msg2, p);
    }

    public static void send(String msg, Player p){
        if(p == null){
            System.out.println(msg);
            return;
        }
        p.sendMessage(msg);
    }
}

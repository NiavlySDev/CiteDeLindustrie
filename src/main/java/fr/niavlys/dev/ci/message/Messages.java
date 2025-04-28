package fr.niavlys.dev.ci.message;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.traduction.Traduction;
import org.bukkit.entity.Player;

public class Messages {
    public static String build(Traduction trad, MessageType type, CIPlayer player){
        String balise = type.getColor() + "["+type.getName()+"] ";
        if(player == null){
            return buildConsole(trad, type);
        }
        String msg = trad.getByLang(player.getLang());
        return balise + msg;
    }

    private static String buildConsole(Traduction trad, MessageType type){
        String balise = type.getColor() + "["+type.getName()+"] ";
        String msg = trad.getFr();
        return balise + msg;
    }

    public static void send(Traduction trad, MessageType type, Player p){
        CIPlayer player = BDD.getPlayer(p.getUniqueId());
        String msg = Messages.build(trad, type, player);
        Messages.send(msg, p);
    }

    public static void send(String msg, Player p){
        if(p == null){
            System.out.println(msg);
            return;
        }
        p.sendMessage(msg);
    }
}

package fr.niavlys.dev.cv.main;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.message.MessageType;
import fr.niavlys.dev.ci.message.Messages;
import fr.niavlys.dev.ci.message.commonMessages.ErrorMessage;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.players.grades.GradeType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommonVerif {

    public static boolean isPlayer(CommandSender s){
        return s instanceof Player;
    }
    public static boolean isConsole(CommandSender s){
        if(!isPlayer(s)){
            Messages.send(ErrorMessage.NoConsole.getMessage(), MessageType.Error, null);
            return false;
        }
        return true;
    }

    public static boolean verifNombreArg(String[] args, Integer nombre, Player p){
        if(args.length < nombre){
            Messages.send(ErrorMessage.NotEnoughArg.getMessage(), MessageType.Error, p);
            return false;
        }
        else if(args.length > nombre){
            Messages.send(ErrorMessage.TooManyArg.getMessage(), MessageType.Error, p);
            return false;
        }
        return true;
    }

    public static boolean verifOnline(String name, Player sender){
        Player p = Bukkit.getPlayer(name);
        if(p == null){
            Messages.send(ErrorMessage.PlayerNotConnected.getMessage(), MessageType.Error, sender);
            return false;
        }
        CIPlayer player = BDD.getPlayer(p.getUniqueId());
        if(player == null){
            Messages.send(ErrorMessage.PlayerNotConnected.getMessage(), MessageType.Error, sender);
            return false;
        }
        return true;
    }

    public static boolean verifArg(Player p, String arg, List<String> possibilities){
        String usage = "<"+possibilities.get(0);
        possibilities.remove(0);
        for(String str : possibilities){
            usage += "|" + str;
        }
        usage += ">";

        if(!possibilities.contains(arg)){
            Messages.send(ErrorMessage.BadArg.getMessage().replaceAll("%argument%", usage), MessageType.Error, p);
            return false;
        }

        return true;
    }

    public static boolean verifNombre(Player p, String nombre, boolean negatif, boolean nul){
        try {
            int value = Integer.parseInt(nombre);
            if(!negatif){
                if(!nul){
                    if(value <=0){
                        Messages.send(ErrorMessage.ZeroNegatif.getMessage(), MessageType.Error, p);
                        return false;
                    }
                }
                if(value<0){
                    Messages.send(ErrorMessage.Negatif.getMessage(), MessageType.Error, p);
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            Messages.send(ErrorMessage.NotInteger.getMessage(), MessageType.Error, p);
            return false;
        }
        return true;
    }

    public static boolean hasPerm(CIPlayer player, GradeType perm){
        if(!player.getGrade().hasPerm(perm)){
            Messages.send(ErrorMessage.NoPerm.getMessage(), Bukkit.getPlayer(player.getUuid()));
            return false;
        }
        return true;
    }

}

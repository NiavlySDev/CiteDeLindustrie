package fr.niavlys.dev.ci.players.grades;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.players.grades.GradeType;
import fr.niavlys.dev.ci.message.MessageType;
import fr.niavlys.dev.ci.message.Messages;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.traduction.Traduction;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GradeC implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
        // /grade <player> <set/rankup/derank> <grade>
        if(!(s instanceof Player)){
            return Console((ConsoleCommandSender) s, args);
        }
        else{
            return Joueur((Player) s, args);
        }
    }

    private boolean Joueur(Player p, String[] args) {
        CIPlayer player = BDD.getPlayer(p.getUniqueId());
        if(player == null){
            System.err.println("GradeC: CIPlayer null (GradeC:33)");
            return false;
        }
        if(!player.getGrade().hasPerm(GradeType.ORGANISATEUR)){
            Messages.send(Traduction.NoPerm, MessageType.Error, p);
            return false;
        }

        String targetName = args[0];
        if(Bukkit.getPlayer(targetName) == null){
            Messages.send(Traduction.JoueurNonCo, MessageType.Error, p);
            return false;
        }

        Player t = Bukkit.getPlayer(targetName);

        CIPlayer target = BDD.getPlayer(t.getUniqueId());
        if(target == null){
            Messages.send(Traduction.JoueurNonCo, MessageType.Error, p);
            return false;
        }

        String action = args[1];
        if(action.equalsIgnoreCase("set")){
            String gradeName = args[2];
            if(GradeType.getByName(gradeName) == null){
                Messages.send(Traduction.MauvaisGrade, MessageType.Error, p);
                return false;
            }

            GradeType grade = GradeType.getByName(gradeName);

            target.getGrade().setGrade(grade);
            String msg = Messages.build(Traduction.GradeChangeTarget, MessageType.Info, target).replaceAll("%grade%", gradeName);
            Messages.send(msg, t);
            target.reloadScoreboard();
            msg = Messages.build(Traduction.GradeChangePlayer, MessageType.Success, player).replaceAll("%grade%", gradeName);
            Messages.send(msg, p);
            return true;
        }
        else{
            if(args.length > 2){
                Messages.send(Traduction.TooMuchArg, MessageType.Error, p);
                return false;
            }
            if(action.equalsIgnoreCase("rankup")){
                String gradeName = target.getGrade().getGrade().getDisplayName();
                target.getGrade().rankup();
                String msg = Messages.build(Traduction.GradeChangeTarget, MessageType.Info, target).replaceAll("%grade%", gradeName);
                Messages.send(msg, t);
                target.reloadScoreboard();
                t.setPlayerListName(target.getGrade().getGrade().getColor() + "["+target.getGrade().getGrade().getDisplayName()+"] " + target.getName());
                msg = Messages.build(Traduction.GradeChangePlayer, MessageType.Success, player).replaceAll("%grade%", gradeName);
                Messages.send(msg, p);
                return true;
            }
            else if(action.equalsIgnoreCase("derank")){
                String gradeName = target.getGrade().getGrade().getDisplayName();
                target.getGrade().derank();
                String msg = Messages.build(Traduction.GradeChangeTarget, MessageType.Info, target).replaceAll("%grade%", gradeName);
                Messages.send(msg, t);
                target.reloadScoreboard();
                t.setPlayerListName(target.getGrade().getGrade().getColor() + "["+target.getGrade().getGrade().getDisplayName()+"] " + target.getName());
                msg = Messages.build(Traduction.GradeChangePlayer, MessageType.Success, player).replaceAll("%grade%", gradeName);
                Messages.send(msg, p);
                return true;
            }
            else {
                Messages.send(Traduction.BadArg, MessageType.Error, p);
                return false;
            }
        }
    }

    private boolean Console(ConsoleCommandSender s, String[] args) {
        String targetName = args[0];
        if(Bukkit.getPlayer(targetName) == null){
            Messages.send(Traduction.JoueurNonCo, MessageType.Error, null);
            return false;
        }

        Player t = Bukkit.getPlayer(targetName);

        // Check Target
        CIPlayer target = BDD.getPlayer(t.getUniqueId());
        if(target == null){
            Messages.send(Traduction.JoueurNonCo, MessageType.Error, null);
            return false;
        }

        String action = args[1];
        if(action.equalsIgnoreCase("set")){
            String gradeName = args[2];
            if(GradeType.getByName(gradeName) == null){
                Messages.send(Traduction.MauvaisGrade, MessageType.Error, null);
                return false;
            }

            GradeType grade = GradeType.getByName(gradeName);

            target.getGrade().setGrade(grade);
            String msg = Messages.build(Traduction.GradeChangeTarget, MessageType.Info, target).replaceAll("%grade%", gradeName);
            Messages.send(msg, t);
            target.reloadScoreboard();
            t.setPlayerListName(target.getGrade().getGrade().getColor() + "["+target.getGrade().getGrade().getDisplayName()+"] " + target.getName());
            System.out.println(Messages.build(Traduction.GradeChangePlayer, MessageType.Success, null).replaceAll("%grade%", gradeName));
            return true;
        }
        else{
            if(args.length > 2){
                Messages.send(Traduction.TooMuchArg, MessageType.Error, null);
                return false;
            }
            if(action.equalsIgnoreCase("rankup")){
                String gradeName = target.getGrade().getGrade().getDisplayName();
                target.getGrade().rankup();
                String msg = Messages.build(Traduction.GradeChangeTarget, MessageType.Info, target).replaceAll("%grade%", gradeName);
                Messages.send(msg, t);
                target.reloadScoreboard();
                t.setPlayerListName(target.getGrade().getGrade().getColor() + "["+target.getGrade().getGrade().getDisplayName()+"] " + target.getName());
                System.out.println(Messages.build(Traduction.GradeChangePlayer, MessageType.Success, null).replaceAll("%grade%", gradeName));
                return true;
            }
            else if(action.equalsIgnoreCase("derank")){
                String gradeName = target.getGrade().getGrade().getDisplayName();
                target.getGrade().derank();
                String msg = Messages.build(Traduction.GradeChangeTarget, MessageType.Info, target).replaceAll("%grade%", gradeName);
                Messages.send(msg, t);
                target.reloadScoreboard();
                t.setPlayerListName(target.getGrade().getGrade().getColor() + "["+target.getGrade().getGrade().getDisplayName()+"] " + target.getName());
                System.out.println(Messages.build(Traduction.GradeChangePlayer, MessageType.Success, null).replaceAll("%grade%", gradeName));

                return true;
            }
            else {
                Messages.send(Traduction.BadArg, MessageType.Error, null);
                return false;
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String msg, String[] args) {
        List<String> choice = new ArrayList<>();
        switch (args.length){
            default:
                return choice;
            case 1:
                for(Player p : Bukkit.getOnlinePlayers()){
                    choice.add(p.getName());
                }
                break;
            case 2:
                choice.add("set");
                choice.add("rankup");
                choice.add("derank");
                break;
            case 3:
                if(args[1].equalsIgnoreCase("set")){
                    for(GradeType grade : GradeType.values()){
                        choice.add(grade.getDisplayName());
                    }
                }
                else{
                    return choice;
                }
        }
        return choice;
    }
}

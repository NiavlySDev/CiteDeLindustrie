package fr.niavlys.dev.ci.players.grades;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.message.MessageType;
import fr.niavlys.dev.ci.message.Messages;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.main.CommonVerif;
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
            return Console(args);
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
        if(!CommonVerif.hasPerm(player, GradeType.ORGANISATEUR)) return false;

        String targetName = args[0];
        if(!CommonVerif.verifOnline(targetName, p)) return false;
        Player t = Bukkit.getPlayer(targetName);
        CIPlayer target = BDD.getPlayer(t.getUniqueId());

        String action = args[1];
        List<String> actions = List.of("set", "rankup", "derank");
        if(!CommonVerif.verifArg(p, action, actions)) return false;

        if(action.equalsIgnoreCase("set")){
            String gradeName = args[2];
            if(GradeType.getByName(gradeName) == null){
                Messages.send("Le grade que vous avez choisi n'est pas valide", MessageType.Error, p);
                return false;
            }

            GradeType grade = GradeType.getByName(gradeName);

            target.getGrade().setGrade(grade);
            String msg = Messages.build("Votre grade a été changé en %grade%", MessageType.Info, target).replaceAll("%grade%", gradeName);
            Messages.send(msg, t);
            target.reloadScoreboard();
            msg = Messages.build("Vous avez changé le grade du joueur pour %grade%", MessageType.Success, player).replaceAll("%grade%", gradeName);
            Messages.send(msg, p);
            return true;
        }
        else{
            CommonVerif.verifNombreArg(args, 2, p);
            if(action.equalsIgnoreCase("rankup")){
                target.getGrade().rankup();
                String gradeName = target.getGrade().getGrade().getDisplayName();
                String msg = Messages.build("Votre grade a été changé en %grade%", MessageType.Info, target)
                        .replaceAll("%grade%", gradeName
                        );
                Messages.send(msg, t);
                target.reloadScoreboard();
                t.setPlayerListName(target.getGrade().getGrade().getColor() + "["+target.getGrade().getGrade().getDisplayName()+"] " + target.getName());
                msg = Messages.build("Vous avez changé le grade du joueur pour %grade%", MessageType.Success, player).replaceAll("%grade%", gradeName);
                Messages.send(msg, p);
                return true;
            }
            else if(action.equalsIgnoreCase("derank")){
                target.getGrade().derank();
                String gradeName = target.getGrade().getGrade().getDisplayName();
                String msg = Messages.build("Votre grade a été changé en %grade%", MessageType.Info, target).replaceAll("%grade%", gradeName);
                Messages.send(msg, t);
                target.reloadScoreboard();
                t.setPlayerListName(target.getGrade().getGrade().getColor() + "["+target.getGrade().getGrade().getDisplayName()+"] " + target.getName());
                msg = Messages.build("Vous avez changé le grade du joueur pour %grade%", MessageType.Success, player).replaceAll("%grade%", gradeName);
                Messages.send(msg, p);
                return true;
            }
        }
        return false;
    }

    private boolean Console(String[] args) {
        String targetName = args[0];
        if(Bukkit.getPlayer(targetName) == null){
            Messages.send("Le joueur n'est pas connecté ou n'existe pas!", MessageType.Error, null);
            return false;
        }

        Player t = Bukkit.getPlayer(targetName);

        // Check Target
        CIPlayer target = BDD.getPlayer(t.getUniqueId());
        if(target == null){
            Messages.send("Le joueur n'est pas connecté ou n'existe pas!", MessageType.Error, null);
            return false;
        }

        String action = args[1];
        if(action.equalsIgnoreCase("set")){
            String gradeName = args[2];
            if(GradeType.getByName(gradeName) == null){
                Messages.send("Le grade que vous avez choisi n'est pas valide", MessageType.Error, null);
                return false;
            }

            GradeType grade = GradeType.getByName(gradeName);

            target.getGrade().setGrade(grade);
            String msg = Messages.build("Votre grade a été changé en %grade%", MessageType.Info, target).replaceAll("%grade%", gradeName);
            Messages.send(msg, t);
            target.reloadScoreboard();
            t.setPlayerListName(target.getGrade().getGrade().getColor() + "["+target.getGrade().getGrade().getDisplayName()+"] " + target.getName());
            System.out.println(Messages.build("Vous avez changé le grade du joueur pour %grade%", MessageType.Success, null).replaceAll("%grade%", gradeName));
            return true;
        }
        else{
            if(args.length > 2){
                Messages.send("Vous avez entré le mauvais argument!", MessageType.Error, null);
                return false;
            }
            if(action.equalsIgnoreCase("rankup")){
                target.getGrade().rankup();
                String gradeName = target.getGrade().getGrade().getDisplayName();
                String msg = Messages.build("Votre grade a été changé en %grade%", MessageType.Info, target).replaceAll("%grade%", gradeName);
                Messages.send(msg, t);
                target.reloadScoreboard();
                t.setPlayerListName(target.getGrade().getGrade().getColor() + "["+target.getGrade().getGrade().getDisplayName()+"] " + target.getName());
                System.out.println(Messages.build("Vous avez changé le grade du joueur pour %grade%", MessageType.Success, null).replaceAll("%grade%", gradeName));
                return true;
            }
            else if(action.equalsIgnoreCase("derank")){
                target.getGrade().derank();
                String gradeName = target.getGrade().getGrade().getDisplayName();
                String msg = Messages.build("Votre grade a été changé en %grade%", MessageType.Info, target).replaceAll("%grade%", gradeName);
                Messages.send(msg, t);
                target.reloadScoreboard();
                t.setPlayerListName(target.getGrade().getGrade().getColor() + "["+target.getGrade().getGrade().getDisplayName()+"] " + target.getName());
                System.out.println(Messages.build("Vous avez changé le grade du joueur pour %grade%", MessageType.Success, null).replaceAll("%grade%", gradeName));

                return true;
            }
            else {
                Messages.send("Vous avez entré le mauvais argument!", MessageType.Error, null);
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

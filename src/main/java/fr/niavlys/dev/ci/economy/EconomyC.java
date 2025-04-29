package fr.niavlys.dev.ci.economy;

import fr.niavlys.dev.bn.main.BigNumbers;
import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.message.commonMessages.ValidationMessage;
import fr.niavlys.dev.ci.players.grades.GradeType;
import fr.niavlys.dev.ci.message.MessageType;
import fr.niavlys.dev.ci.message.Messages;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.main.CommonVerif;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EconomyC implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
        // /economy <player> <bronze/argent/or> <add/remove/set> <amount> <sign>
        if(CommonVerif.isConsole(s)){
            return Console(args);
        }
        else{
            return Joueur((Player) s, args);
        }
    }

    private boolean Joueur(Player p, String[] args) {
        CIPlayer player = BDD.getPlayer(p.getUniqueId());
        if(player == null){
            System.err.println("EconomyC: CIPlayer null (EconomyC:33)");
            return false;
        }
        if(!CommonVerif.hasPerm(player, GradeType.RESPONSABLE)) return false;

        if(!CommonVerif.verifNombreArg(args, 5, p)) return false;

        String targetName = args[0];
        if(!CommonVerif.verifOnline(targetName, p)) return false;

        Player t = Bukkit.getPlayer(targetName);
        CIPlayer target = BDD.getPlayer(t.getUniqueId());

        String type = args[1];
        List<String> types = List.of("bronze", "argent", "or");
        if(!CommonVerif.verifArg(p, type, types)) return false;

        String action = args[2];
        List<String> actions = List.of("add", "remove", "set");
        if(!CommonVerif.verifArg(p, action, actions)) return false;

        String amountStr = args[3];
        if(!CommonVerif.verifNombre(p, amountStr, false, true)) return false;
        Integer amountInt = Integer.parseInt(amountStr);

        String sign = args[4];
        List<String> signs = BigNumbers.getSigns();
        if(!CommonVerif.verifArg(p, sign, signs)) return false;

        String messageAdmin = SendMessage(t, target, type, action, amountInt, sign);
        Messages.send(messageAdmin, MessageType.Success, p);
        target.reloadScoreboard();
        return true;
    }
    private boolean Console(String[] args) {
        if(!CommonVerif.verifNombreArg(args, 4, null)) return false;

        String targetName = args[0];
        if(!CommonVerif.verifOnline(targetName, null)) return false;
        Player t = Bukkit.getPlayer(targetName);
        CIPlayer target = BDD.getPlayer(t.getUniqueId());

        String type = args[1];
        List<String> types = List.of("bronze", "argent", "or");
        if(!CommonVerif.verifArg(null, type, types)) return false;

        String action = args[2];
        List<String> actions = List.of("add", "remove", "set");
        if(!CommonVerif.verifArg(null, action, actions)) return false;

        String amountStr = args[3];
        if(!CommonVerif.verifNombre(null, amountStr, false, true)) return false;

        Integer amountInt = Integer.parseInt(amountStr);

        String sign = args[4];
        List<String> signs = BigNumbers.getSigns();
        if(!CommonVerif.verifArg(null, sign, signs)) return false;

        String messageAdmin = SendMessage(t, target, type, action, amountInt, sign);
        Messages.send(messageAdmin, MessageType.Success, null);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String msg, String[] args) {
        List<String> choice = new ArrayList<>();
        switch (args.length){
            case 1:
                for(Player p : Bukkit.getOnlinePlayers()){
                    choice.add(p.getName());
                }
                break;
            case 2:
                choice.addAll(List.of("bronze", "argent", "or"));
                break;
            case 3:
                choice.addAll(List.of("add", "remove", "set"));
                break;
            case 4:
                choice.add("<amount>");
                break;
            case 5:
                choice.addAll(BigNumbers.getSigns());
                break;
            default:
                return choice;
        }
        return choice;
    }

    private String SendMessage(Player t, CIPlayer target, String type, String action, Integer amountInt, String sign) {
        BigNumbers amount = Action(target, type, action, amountInt, sign);
        String message = null;
        String messageAdmin = null;

        if(action.equalsIgnoreCase("add")){
            message = ValidationMessage.AjouteXJoueur.getMessage();
            messageAdmin = ValidationMessage.AjouteXAdmin.getMessage();
        }
        else if(action.equalsIgnoreCase("remove")){
            message = ValidationMessage.RetireXJoueur.getMessage();
            messageAdmin = ValidationMessage.RetireXAdmin.getMessage();
        }
        else if(action.equalsIgnoreCase("set")){
            message = ValidationMessage.MisXJoueur.getMessage();
            messageAdmin = ValidationMessage.MisXAdmin.getMessage();
        }

        message.replaceAll("%nombre%", amount.toString());
        message.replaceAll("%type%", type);
        messageAdmin.replaceAll("%nombre%", amount.toString());
        messageAdmin.replaceAll("%type%", type);
        messageAdmin.replaceAll("%joueur%", target.getName());

        Messages.send(message, MessageType.Success, t);

        return messageAdmin;
    }
    private BigNumbers Action(CIPlayer target, String type, String action, Integer amountInt, String sign) {
        BigNumbers amount = new BigNumbers(amountInt, sign);

        if(type.equalsIgnoreCase("bronze")){
            if(action.equalsIgnoreCase("add")){
                target.getBalance().getBronze().add(amount);
            }
            else if(action.equalsIgnoreCase("remove")){
                target.getBalance().getBronze().remove(amount);
            }
            else if(action.equalsIgnoreCase("set")){
                target.getBalance().getBronze().set(amount);
            }
        }
        else if(type.equalsIgnoreCase("argent")){
            if(action.equalsIgnoreCase("add")){
                target.getBalance().getArgent().add(amount);
            }
            else if(action.equalsIgnoreCase("remove")){
                target.getBalance().getArgent().remove(amount);
            }
            else if(action.equalsIgnoreCase("set")){
                target.getBalance().getArgent().set(amount);
            }
        }
        else if(type.equalsIgnoreCase("or")){
            if(action.equalsIgnoreCase("add")){
                target.getBalance().getOr().add(amount);
            }
            else if(action.equalsIgnoreCase("remove")){
                target.getBalance().getOr().remove(amount);
            }
            else if(action.equalsIgnoreCase("set")){
                target.getBalance().getOr().set(amount);
            }
        }

        return amount;
    }

}

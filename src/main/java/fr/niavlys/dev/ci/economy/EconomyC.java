package fr.niavlys.dev.ci.economy;

import fr.niavlys.dev.bn.main.BigNumbers;
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

public class EconomyC implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
        // /economy <player> <bronze/argent/or> <add/remove/set> <amount> <sign>
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
            System.err.println("EconomyC: CIPlayer null (EconomyC:33)");
            return false;
        }
        if(!player.getGrade().hasPerm(GradeType.RESPONSABLE)){
            Messages.send(Traduction.NoPerm, MessageType.Error, p);
            return false;
        }

        if(args.length != 5){
            Messages.send(Traduction.PasAssezArg, MessageType.Error, p);
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

        String type = args[1];
        List<String> types = List.of("bronze", "argent", "or");
        if(!types.contains(type)){
            Messages.send(Traduction.BadArg, MessageType.Error, p);
            return false;
        }

        String action = args[2];
        List<String> actions = List.of("add", "remove", "set");
        if(!actions.contains(action)){
            Messages.send(Traduction.BadArg, MessageType.Error, p);
            return false;
        }

        String amountStr = args[3];
        try {
            int value = Integer.parseInt(amountStr);
            if(value < 0){
                Messages.send(Traduction.NombreNegatif, MessageType.Error, p);
                return false;
            }
        } catch (NumberFormatException e) {
            Messages.send(Traduction.EntrerUnNombre, MessageType.Error, p);
            return false;
        }

        Integer amountInt = Integer.parseInt(amountStr);

        String sign = args[4];
        List<String> signs = BigNumbers.getSigns();
        if(!signs.contains(sign)){
            Messages.send(Traduction.BadArg, MessageType.Error, p);
            return false;
        }

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
            else{
                Messages.send(Traduction.BadArg, MessageType.Error, p);
                return false;
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
            else{
                Messages.send(Traduction.BadArg, MessageType.Error, p);
                return false;
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
            else{
                Messages.send(Traduction.BadArg, MessageType.Error, p);
                return false;
            }
        }
        else{
            Messages.send(Traduction.BadArg, MessageType.Error, p);
            return false;
        }
        Messages.send(Messages.build(Traduction.MoneyChangeTarget, MessageType.Info, target)
                        .replaceAll("%amount%", amount.toString())
                        .replaceAll("%action%", action)
                        .replaceAll("%moneyType%", type)
                , t);
        Messages.send(Messages.build(Traduction.MoneyChangePlayer, MessageType.Success, player)
                        .replaceAll("%amount%", amount.toString())
                        .replaceAll("%action%", action)
                        .replaceAll("%moneyType%", type)
                , p);
        target.reloadScoreboard();
        return true;
    }

    private boolean Console(ConsoleCommandSender s, String[] args) {
        if(args.length != 4){
            Messages.send(Traduction.PasAssezArg, MessageType.Error, null);
            return false;
        }

        String targetName = args[0];
        if(Bukkit.getPlayer(targetName) == null){
            Messages.send(Traduction.JoueurNonCo, MessageType.Error, null);
            return false;
        }

        Player t = Bukkit.getPlayer(targetName);
        CIPlayer target = BDD.getPlayer(t.getUniqueId());
        if(target == null){
            Messages.send(Traduction.JoueurNonCo, MessageType.Error, null);
            return false;
        }

        String type = args[1];
        List<String> types = List.of("bronze", "argent", "or");
        if(!types.contains(type)){
            Messages.send(Traduction.BadArg, MessageType.Error, null);
            return false;
        }

        String action = args[2];
        List<String> actions = List.of("add", "remove", "set");
        if(!actions.contains(action)){
            Messages.send(Traduction.BadArg, MessageType.Error, null);
            return false;
        }

        String amountStr = args[3];
        try {
            int value = Integer.parseInt(amountStr);
            if(value <= 0){
                Messages.send(Traduction.NombreNegatif, MessageType.Error, null);
                return false;
            }
        } catch (NumberFormatException e) {
            Messages.send(Traduction.EntrerUnNombre, MessageType.Error, null);
            return false;
        }

        Integer amountInt = Integer.parseInt(amountStr);

        String sign = args[4];
        List<String> signs = BigNumbers.getSigns();
        if(!signs.contains(sign)){
            Messages.send(Traduction.BadArg, MessageType.Error, null);
            return false;
        }

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
            else{
                Messages.send(Traduction.BadArg, MessageType.Error, null);
                return false;
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
            else{
                Messages.send(Traduction.BadArg, MessageType.Error, null);
                return false;
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
            else{
                Messages.send(Traduction.BadArg, MessageType.Error, null);
                return false;
            }
        }
        else{
            Messages.send(Traduction.BadArg, MessageType.Error, null);
            return false;
        }
        Messages.send(Messages.build(Traduction.MoneyChangeTarget, MessageType.Info, target)
                        .replaceAll("%amount%", amount.toString())
                        .replaceAll("%action%", action)
                        .replaceAll("%moneyType%", type)
                , t);
        Messages.send(Messages.build(Traduction.MoneyChangePlayer, MessageType.Success, null)
                        .replaceAll("%amount%", amount.toString())
                        .replaceAll("%action%", action)
                        .replaceAll("%moneyType%", type)
                , null);
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
}

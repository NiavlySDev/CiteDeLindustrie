package fr.niavlys.dev.ci.economy;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.message.MessageType;
import fr.niavlys.dev.ci.message.Messages;
import fr.niavlys.dev.ci.message.commonMessages.ErrorMessage;
import fr.niavlys.dev.ci.message.commonMessages.ValidationMessage;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.main.CommonVerif;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoneyC implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
        // /money <type> <player>
        if(CommonVerif.isConsole(s)){
            Messages.send(ErrorMessage.NoConsole.getMessage(), MessageType.Error, null);
            return false;
        }
        Player p = (Player) s;
        CIPlayer player = BDD.getPlayer(p.getUniqueId());
        if(player == null){
            System.err.println("MoneyC: CIPlayer null (MoneyC:23)");
            return false;
        }

        List<String> types = Arrays.asList("bronze", "argent", "or");
        String type = args[0];
        if(!CommonVerif.verifArg(p, type, types)){
            return false;
        }

        String targetName = null;

        if(args.length == 1){
            targetName = player.getName();
        }
        else if(args.length == 2){
            targetName = args[1];
            if(!CommonVerif.verifOnline(targetName, p)){
                return false;
            }
            Player t = Bukkit.getPlayer(targetName);
        }
        else{
            Messages.send(ErrorMessage.TooManyArg.getMessage(), MessageType.Error, p);
            return false;
        }
        String amount = null;
        if(type.equalsIgnoreCase("bronze")){
            amount = player.getBalance().getBronze().toString();
        }
        else if(type.equalsIgnoreCase("argent")){
            amount = player.getBalance().getArgent().toString();
        }
        else if(type.equalsIgnoreCase("or")){
            amount = player.getBalance().getOr().toString();
        }
        Messages.send(
                Messages.build(
                        ValidationMessage.GetMoney.getMessage()
                                .replaceAll("%amount%", amount.toString())
                                .replaceAll("%type%", type)
                                .replaceAll("%joueur%", targetName)
                        , MessageType.Info, player)
                ,p
        );
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String msg, String[] args) {
        List<String> choice = new ArrayList<>();
        switch (args.length){
            default:
                return choice;
            case 1:
                choice.add("bronze");
                choice.add("argent");
                choice.add("or");
                break;
            case 2:
                for(Player p : Bukkit.getOnlinePlayers()){
                    choice.add(p.getName());
                }
                break;
        }
        return choice;
    }
}

package fr.niavlys.dev.ci.economy;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.message.MessageType;
import fr.niavlys.dev.ci.message.Messages;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.traduction.Traduction;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.xml.transform.stax.StAXResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoneyC implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
        // /money <type> <player>
        if(!(s instanceof Player)){
            System.out.println("Vous ne pouvez pas executer cette commande avec la console!");
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
        if(!types.contains(type)){
            Messages.send(Traduction.BadArg, MessageType.Error, p);
            return false;
        }

        if(args.length == 1){
            if(type.equalsIgnoreCase("bronze")){
                Messages.send(
                        Messages.build(
                                Traduction.MoneyAffichage,
                                MessageType.Info,
                                player
                        )
                                .replaceAll("%amount%", player.getBalance().getBronze().toString())
                                .replaceAll("%moneyType%", "bronze")
                        ,p
                );
                return true;
            }
            else if(type.equalsIgnoreCase("argent")){
                Messages.send(
                        Messages.build(
                                        Traduction.MoneyAffichage,
                                        MessageType.Info,
                                        player
                                )
                                .replaceAll("%amount%", player.getBalance().getArgent().toString())
                                .replaceAll("%moneyType%", "argent")
                        ,p
                );
                return true;
            }
            else if(type.equalsIgnoreCase("or")){
                Messages.send(
                        Messages.build(
                                        Traduction.MoneyAffichage,
                                        MessageType.Info,
                                        player
                                )
                                .replaceAll("%amount%", player.getBalance().getOr().toString())
                                .replaceAll("%moneyType%", "or")
                        ,p
                );
                return true;
            }
        }
        else if(args.length == 2){
            String targetName = args[1];
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
            if(type.equalsIgnoreCase("bronze")){
                Messages.send(
                        Messages.build(
                                        Traduction.MoneyAffichageOther,
                                        MessageType.Info,
                                        player
                                )
                                .replaceAll("%amount%", target.getBalance().getBronze().toString())
                                .replaceAll("%moneyType%", "bronze")
                        ,p
                );
                return true;
            }
            else if(type.equalsIgnoreCase("argent")){
                Messages.send(
                        Messages.build(
                                        Traduction.MoneyAffichageOther,
                                        MessageType.Info,
                                        player
                                )
                                .replaceAll("%amount%", target.getBalance().getArgent().toString())
                                .replaceAll("%moneyType%", "argent")
                        ,p
                );
                return true;
            }
            else if(type.equalsIgnoreCase("or")){
                Messages.send(
                        Messages.build(
                                        Traduction.MoneyAffichageOther,
                                        MessageType.Info,
                                        player
                                )
                                .replaceAll("%amount%", target.getBalance().getOr().toString())
                                .replaceAll("%moneyType%", "or")
                        ,p
                );
                return true;
            }
        }
        else{
            Messages.send(Traduction.TooMuchArg, MessageType.Error, p);
            return false;
        }
        return false;
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

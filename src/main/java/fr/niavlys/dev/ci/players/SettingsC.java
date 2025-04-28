package fr.niavlys.dev.ci.players;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.message.MessageType;
import fr.niavlys.dev.ci.message.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SettingsC implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
        // /settings <nick>
        // /settings lang <lang>
        // /settings nick <nickname>

        if(!(s instanceof Player)){
            System.err.println("Vous ne pouvez pas ouvrir les settings avec la console!");
            return false;
        }
        Player p = (Player) s;
        CIPlayer player = BDD.getPlayer(p.getUniqueId());
        if(player == null){
            System.err.println("SettingsC: CIPlayer null (SettingsC:23)");
            return false;
        }

        String subCmd = args[0];
        if(subCmd.equalsIgnoreCase("nick")){
            String nick = args[1];
            player.setName(nick);

            Messages.send(Messages.build("Vous avez changé votre nom en %nom%", MessageType.Success, player).replaceAll("%nom%", nick), p);
            return true;
        }
        else{
            Messages.send("Vous avez entré le mauvais argument!", MessageType.Error, p);
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String msg, String[] args) {
        List<String> choice = new ArrayList<>();
        switch (args.length){
            default:
                return choice;
            case 1:
                choice.add("nick");
                break;
            case 2:
                if(args[0].equalsIgnoreCase("nick")){
                    String name = s.getName();
                    for(int i = 0; i < name.length(); i++){
                        choice.add(name.substring(0, i + 1));
                    }
                }
                break;
        }
        return choice;
    }
}

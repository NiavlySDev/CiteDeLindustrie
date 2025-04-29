package fr.niavlys.dev.ci.quests.commands;

import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.quests.inventory.QuestGUI;
import fr.niavlys.dev.cv.main.CommonVerif;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuestC implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
        if(CommonVerif.isConsole(s)) return false;
        Player p = (Player) s;
        CIPlayer player = new CIPlayer(p);
        if(player == null){
            System.err.println("QuestC: CIPlayer null (QuestC:23)");
            return false;
        }
        p.openInventory(QuestGUI.build(p));
        return true;
    }
}

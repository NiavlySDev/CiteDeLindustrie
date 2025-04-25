package fr.niavlys.dev.ci.main;

import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.economy.EconomyC;
import fr.niavlys.dev.ci.players.*;
import fr.niavlys.dev.ci.shop.ShopC;
import fr.niavlys.dev.ci.shop.ShopListener;
import fr.niavlys.dev.pdm.main.PlayerDataManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static PlayerDataManager players;
    public static String name;
    public static String version;
    public static String author;

    @Override
    public void onEnable() {
        name = getDescription().getName();
        version = getDescription().getVersion();
        author = getDescription().getAuthors().get(0);

        players = new PlayerDataManager(name, "players.yml");
        System.out.println("[" + name + " v" + version + "] Configuration des joueurs initialisee.");

        createCommand("shop", new ShopC(), false);
        createCommand("grade", new GradeC(), true);
        createCommand("economy", new EconomyC(), true);
        createCommand("settings", new SettingsC(), true);

        createEvent(new ShopListener());
        createEvent(new JoinEvent());
        createEvent(new LeaveEvent());
        createEvent(new OnMessage());

        System.out.println("[" + name + " v" + version + "] Active.");
        System.out.println("[" + name + " v" + version + "] Developpe par " + author + ".");
    }

    @Override
    public void onDisable() {
        savePlayers();
        System.out.println("[" + name + " v" + version + "] Desactive.");
    }

    public void createCommand(String command, CommandExecutor classe, boolean tabCompleter) {
        getCommand(command).setExecutor(classe);
        System.out.println("[" + name + " v" + version + "] Commande '" + command + "' initialisee.");
        if (tabCompleter) {
            getCommand(command).setTabCompleter((TabCompleter) classe);
            System.out.println("[" + name + " v" + version + "] TabCompleter '" + command + "' initialise.");
        }
    }

    public void createEvent(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
        System.out.println("[" + name + " v" + version + "] Evenement '" + listener.getClass().getSimpleName() + "' initialise.");
    }

    public void savePlayers(){
        for (CIPlayer player : BDD.players.values()) {
            player.savePlayer();
            System.out.println("[" + name + " v" + version + "] Joueur " + player.getName() + " Sauvegarde");
        }
        System.out.println("[" + name + " v" + version + "] Joueurs Sauvegardes");
    }
}
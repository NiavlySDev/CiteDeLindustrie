package fr.niavlys.dev.ci.main;

import fr.niavlys.dev.ci.economy.convertisseur.ConvertMoneyC;
import fr.niavlys.dev.ci.economy.EconomyC;
import fr.niavlys.dev.ci.economy.MoneyC;
import fr.niavlys.dev.ci.message.OnMessage;
import fr.niavlys.dev.ci.players.*;
import fr.niavlys.dev.ci.players.grades.GradeC;
import fr.niavlys.dev.ci.inventory.GUIListener;
import fr.niavlys.dev.cm.main.ConfigDataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class CiteIndustrie extends JavaPlugin {

    public static ConfigDataManager players;
    public static ConfigDataManager shopPrices;
    public static String name;
    public static String version;
    public static String author;

    @Override
    public void onEnable() {
        name = getDescription().getName();
        version = getDescription().getVersion();
        author = getDescription().getAuthors().get(0);

        players = new ConfigDataManager(name, "players.yml");
        System.out.println("[" + name + " v" + version + "] Configuration des joueurs initialisee.");

        shopPrices = new ConfigDataManager(name, "shopPrices.yml");
        System.out.println("[" + name + " v" + version + "] Configuration des prix des items de la boutique initialisee.");

        loadPlayers();

        createCommand("grade", new GradeC(), true);
        createCommand("economy", new EconomyC(), true);
        createCommand("settings", new SettingsC(), true);
        createCommand("money", new MoneyC(), true);
        createCommand("convertmoney", new ConvertMoneyC(), false);

        createEvent(new GUIListener());
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
        for (CIPlayer player : fr.niavlys.dev.ci.donnees.BDD.players.values()) {
            player.savePlayer();
            System.out.println("[" + name + " v" + version + "] Joueur " + player.getName() + " Sauvegarde");
        }
        System.out.println("[" + name + " v" + version + "] Joueurs Sauvegardes");
    }
    public void loadPlayers(){
        for (Player p : Bukkit.getOnlinePlayers()) {
            CIPlayer player = new CIPlayer(p);
            player.loadPlayer();
            System.out.println("[" + name + " v" + version + "] Joueur " + player.getName() + " Charge");
        }
        System.out.println("[" + name + " v" + version + "] Joueurs Chargees");
    }
}
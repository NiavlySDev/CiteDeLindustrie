package fr.niavlys.dev.ci.players;

import fr.niavlys.dev.bn.main.BigNumbers;
import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.economy.Balance;
import fr.niavlys.dev.ci.main.CiteIndustrie;
import fr.niavlys.dev.ci.players.grades.Grade;
import fr.niavlys.dev.ci.players.grades.GradeType;
import fr.niavlys.dev.cm.main.ConfigDataManager;
import fr.niavlys.dev.gm.main.GradientManager;
import fr.niavlys.dev.sm.main.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.UUID;

public class CIPlayer {

    private String name;
    private UUID uuid;
    private Grade grade;
    private Balance balance;
    private ScoreboardManager scoreboard;
    private Integer questStarted;

    public CIPlayer(Player p){
        this.name = p.getName();
        this.uuid = p.getUniqueId();
        this.grade = new Grade(GradeType.OUVRIER);
        this.balance = new Balance();
        this.questStarted = 0;
        scoreboard = new ScoreboardManager(p, GradientManager.createGradientTitle("Cité de L'industrie", Color.RED, Color.BLUE));
        reloadScoreboard();
    }

    public void reloadScoreboard(){
        scoreboard.clearScoreboard();
        scoreboard.setScoreLine(30, this.getGrade().getGrade().getColor(), "Grade", this.getGrade().getGrade().getDisplayName());
        scoreboard.setScoreLine(29, ChatColor.RED, "Bronze", this.getBalance().getBronze().toString());
        scoreboard.setScoreLine(28, ChatColor.GRAY, "Argent", this.getBalance().getArgent().toString());
        scoreboard.setScoreLine(27, ChatColor.GOLD, "Or", this.getBalance().getOr().toString());
    }

    public String getName() {
        return name;
    }
    public UUID getUuid() {
        return uuid;
    }
    public Balance getBalance() {
        return balance;
    }
    public Grade getGrade() {
        return grade;
    }
    public Integer getQuestStarted(){
        return this.questStarted;
    }
    public void addQuestStarted(Integer questStarted){
        this.questStarted += questStarted;
    }
    public void removeQuestStarted(Integer questStarted){
        this.questStarted -= questStarted;
    }
    public void setQuestStarted(Integer questStarted){
        this.questStarted = questStarted;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void loadPlayer(){
        ConfigDataManager players = CiteIndustrie.players;
        if(!players.exists(this.uuid)){
            System.err.println("Joueur " + this.uuid + " n'existe pas, joueur par defaut!");
            BDD.addPlayer(this.getUuid(), this);
            return;
        }
        this.setName((String) players.get(this.uuid.toString(), "nick"));
        this.grade = new Grade(GradeType.getByInitials((String) players.get(this.uuid.toString(), "grade")));
        this.getBalance().getBronze().set(new BigNumbers((Double) players.get(this.uuid.toString(), "balance.bronze.entier"), (String) players.get(this.uuid.toString(), "balance.bronze.sign")));
        this.getBalance().getArgent().set(new BigNumbers((Double) players.get(this.uuid.toString(), "balance.argent.entier"), (String) players.get(this.uuid.toString(), "balance.argent.sign")));
        this.getBalance().getOr().set(new BigNumbers((Double) players.get(this.uuid.toString(), "balance.or.entier"), (String) players.get(this.uuid.toString(), "balance.or.sign")));
        BDD.addPlayer(this.getUuid(), this);
        reloadScoreboard();
        Bukkit.getPlayer(this.getUuid()).setPlayerListName(this.getGrade().getGrade().getColor() + "["+this.getGrade().getGrade().getDisplayName()+"] " + this.getName());
        System.out.println("Joueur " + this.getName() + " charge!");
    }
    
    public void savePlayer(){
        ConfigDataManager players = CiteIndustrie.players;
        players.set(this.getUuid().toString(), "balance.bronze.entier", this.getBalance().getBronze().getEntier());
        players.set(this.getUuid().toString(), "balance.bronze.sign", this.getBalance().getBronze().getSign());
        players.set(this.getUuid().toString(), "balance.argent.entier", this.getBalance().getArgent().getEntier());
        players.set(this.getUuid().toString(), "balance.argent.sign", this.getBalance().getArgent().getSign());
        players.set(this.getUuid().toString(), "balance.or.entier", this.getBalance().getOr().getEntier());
        players.set(this.getUuid().toString(), "balance.or.sign", this.getBalance().getOr().getSign());
        players.set(this.getUuid().toString(), "grade", this.getGrade().getGrade().getInitials());
        players.set(this.getUuid().toString(), "nick", this.getName());
    }
}

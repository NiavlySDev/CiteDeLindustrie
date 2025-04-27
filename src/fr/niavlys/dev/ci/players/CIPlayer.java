package fr.niavlys.dev.ci.players;

import fr.niavlys.dev.bn.main.BigNumbers;
import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.economy.Balance;
import fr.niavlys.dev.ci.players.grades.Grade;
import fr.niavlys.dev.ci.players.grades.GradeType;
import fr.niavlys.dev.ci.main.Main;
import fr.niavlys.dev.ci.traduction.Language;
import fr.niavlys.dev.pdm.main.ConfigDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CIPlayer {

    private String name;
    private UUID uuid;
    private Language lang;
    private Grade grade;
    private Balance balance;

    public CIPlayer(Player p){
        this.name = p.getName();
        this.uuid = p.getUniqueId();
        this.lang = Language.Français;
        this.grade = new Grade(GradeType.OUVRIER);
        this.balance = new Balance();
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
    public Language getLang() {
        return lang;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setLang(Language lang) {
        this.lang = lang;
    }

    public void loadPlayer(){
        ConfigDataManager players = Main.players;
        if(!players.exists(this.uuid)){
            System.err.println("Joueur " + this.uuid + " n'existe pas, joueur par defaut!");
            BDD.addPlayer(this.getUuid(), this);
            return;
        }
        this.setName((String) players.get(this.uuid.toString(), "nick"));
        this.setLang(Language.valueOf((String) players.get(this.uuid.toString(), "language")));
        this.grade = new Grade(GradeType.getByInitials((String) players.get(this.uuid.toString(), "grade")));
        this.getBalance().getBronze().set(new BigNumbers((Double) players.get(this.uuid.toString(), "balance.bronze.entier"), (String) players.get(this.uuid.toString(), "balance.bronze.sign")));
        this.getBalance().getArgent().set(new BigNumbers((Double) players.get(this.uuid.toString(), "balance.argent.entier"), (String) players.get(this.uuid.toString(), "balance.argent.sign")));
        this.getBalance().getOr().set(new BigNumbers((Double) players.get(this.uuid.toString(), "balance.or.entier"), (String) players.get(this.uuid.toString(), "balance.or.sign")));
        BDD.addPlayer(this.getUuid(), this);
        Bukkit.getPlayer(this.getUuid()).setPlayerListName(this.getGrade().getGrade().getColor() + "["+this.getGrade().getGrade().getDisplayName()+"] " + this.getName());
        System.out.println("Joueur " + this.getName() + " charge!");
    }
    
    public void savePlayer(){
        ConfigDataManager players = Main.players;
        players.set(this.getUuid().toString(), "balance.bronze.entier", this.getBalance().getBronze().getEntier());
        players.set(this.getUuid().toString(), "balance.bronze.sign", this.getBalance().getBronze().getSign());
        players.set(this.getUuid().toString(), "balance.argent.entier", this.getBalance().getArgent().getEntier());
        players.set(this.getUuid().toString(), "balance.argent.sign", this.getBalance().getArgent().getSign());
        players.set(this.getUuid().toString(), "balance.or.entier", this.getBalance().getOr().getEntier());
        players.set(this.getUuid().toString(), "balance.or.sign", this.getBalance().getOr().getSign());
        players.set(this.getUuid().toString(), "grade", this.getGrade().getGrade().getInitials());
        players.set(this.getUuid().toString(), "language", this.getLang().name());
        players.set(this.getUuid().toString(), "nick", this.getName());
    }
}

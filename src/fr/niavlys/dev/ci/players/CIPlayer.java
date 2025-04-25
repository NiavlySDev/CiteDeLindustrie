package fr.niavlys.dev.ci.players;

import fr.niavlys.dev.bn.main.BigNumbers;
import fr.niavlys.dev.ci.economy.Balance;
import fr.niavlys.dev.ci.grades.GradeType;
import fr.niavlys.dev.ci.main.Main;
import fr.niavlys.dev.ci.traduction.Language;
import fr.niavlys.dev.pdm.main.PlayerDataManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class CIPlayer {

    private String name;
    private UUID uuid;
    private Language lang;
    private Grade grade;
    private Balance balance;

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
        PlayerDataManager players = Main.players;
        if(!players.exists(this.uuid)){
            System.err.println("Joueur " + this.uuid + " n'existe pas, joueur par defaut!");
            return;
        }
        this.setName((String) players.get(this.uuid, "nick"));
        this.setLang(Language.valueOf((String) players.get(this.uuid, "language")));
        this.grade = new Grade(GradeType.getByInitials((String) players.get(this.uuid, "grade")));
        this.getBalance().getBronze().set(new BigNumbers((Double) players.get(this.uuid, "balance.bronze.entier"), (String) players.get(this.uuid, "balance.bronze.sign")));
        this.getBalance().getArgent().set(new BigNumbers((Double) players.get(this.uuid, "balance.argent.entier"), (String) players.get(this.uuid, "balance.argent.sign")));
        this.getBalance().getOr().set(new BigNumbers((Double) players.get(this.uuid, "balance.or.entier"), (String) players.get(this.uuid, "balance.or.sign")));
        System.out.println("Joueur " + this.getName() + " charge!");
    }
    
    public void savePlayer(){
        PlayerDataManager players = Main.players;
        players.set(this.getUuid(), "balance.bronze.entier", this.getBalance().getBronze().getEntier());
        players.set(this.getUuid(), "balance.bronze.sign", this.getBalance().getBronze().getSign());
        players.set(this.getUuid(), "balance.argent.entier", this.getBalance().getArgent().getEntier());
        players.set(this.getUuid(), "balance.argent.sign", this.getBalance().getArgent().getSign());
        players.set(this.getUuid(), "balance.or.entier", this.getBalance().getOr().getEntier());
        players.set(this.getUuid(), "balance.or.sign", this.getBalance().getOr().getSign());
        players.set(this.getUuid(), "grade", this.getGrade().getGrade().getInitials());
        players.set(this.getUuid(), "language", this.getLang().name());
        players.set(this.getUuid(), "nick", this.getName());
    }
}

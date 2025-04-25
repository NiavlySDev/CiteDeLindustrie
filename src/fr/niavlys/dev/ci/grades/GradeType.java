package fr.niavlys.dev.ci.grades;

import org.bukkit.ChatColor;

import java.util.List;

public enum GradeType {
    OUVRIER("Ouvrier", ChatColor.GRAY, "OUV"),
    ARTISAN("Artisan", ChatColor.GREEN, "ART"),
    CONTREMAITRE("Contremaître", ChatColor.AQUA, "CTM"),
    INGENIEUR("Ingénieur", ChatColor.BLUE, "ING"),
    INDUSTRIEL("Industriel", ChatColor.YELLOW, "IND"),
    INNOVATEUR("Innovateur", ChatColor.LIGHT_PURPLE, "INO"),
    VISIONNAIRE("Visionnaire", ChatColor.GOLD, "VIS"),
    CHEFPROJET("ChefProjet", ChatColor.GREEN, "CPJ"),
    SUPERVISEUR("Superviseur", ChatColor.BLUE, "SUP"),
    RESPONSABLE("Responsable", ChatColor.RED, "RES"),
    ORGANISATEUR("Organisateur", ChatColor.GOLD, "ORG");

    private final String displayName;
    private final ChatColor color;
    private final String initials;

    GradeType(String displayName, ChatColor color, String initials) {
        this.displayName = displayName;
        this.color = color;
        this.initials = initials;
    }
    public String getDisplayName() {
        return displayName;
    }
    public ChatColor getColor() {
        return color;
    }
    public String getInitials() {
        return initials;
    }
    public GradeType getNext(GradeType type){
        switch(type){
            default:
                return null;
            case OUVRIER:
                return ARTISAN;
            case ARTISAN:
                return CONTREMAITRE;
            case CONTREMAITRE:
                return INGENIEUR;
            case INGENIEUR:
                return INDUSTRIEL;
            case INDUSTRIEL:
                return INNOVATEUR;
            case INNOVATEUR:
                return VISIONNAIRE;
            case VISIONNAIRE:
                return CHEFPROJET;
            case CHEFPROJET:
                return SUPERVISEUR;
            case SUPERVISEUR:
                return RESPONSABLE;
            case RESPONSABLE:
                return ORGANISATEUR;
            case ORGANISATEUR:
                return null;
        }
    }
    public GradeType getPrevious(GradeType type){
        switch(type){
            default:
                return null;
            case ORGANISATEUR:
                return RESPONSABLE;
            case RESPONSABLE:
                return SUPERVISEUR;
            case SUPERVISEUR:
                return CHEFPROJET;
            case CHEFPROJET:
                return VISIONNAIRE;
            case VISIONNAIRE:
                return INNOVATEUR;
            case INNOVATEUR:
                return INDUSTRIEL;
            case INDUSTRIEL:
                return INGENIEUR;
            case INGENIEUR:
                return CONTREMAITRE;
            case CONTREMAITRE:
                return ARTISAN;
            case ARTISAN:
                return OUVRIER;
            case OUVRIER:
                return null;
        }
    }
    public boolean hasPerm(GradeType type){
        return this.ordinal() >= type.ordinal();
    }

    public static boolean exists(GradeType type){
        for(GradeType grade : GradeType.values()){
            if(grade == type){
                return true;
            }
        }
        return false;
    }
    public static GradeType getByName(String name){
        for(GradeType grade : GradeType.values()){
            if(grade.getDisplayName().equalsIgnoreCase(name)){
                return grade;
            }
        }
        return null;
    }
    public static GradeType getByInitials(String initials){
        for(GradeType grade : GradeType.values()){
            if(grade.getInitials().equalsIgnoreCase(initials)){
                return grade;
            }
        }
        return null;
    }
    public static List<String> getNames(){
        List<String> names = List.of();
        for(GradeType grade : GradeType.values()){
            names.add(grade.getDisplayName());
        }
        return names;
    }
}

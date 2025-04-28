package fr.niavlys.dev.ci.traduction;

public enum Language {

    Français,
    English;

    public static boolean isLang(String lang){
        for(Language l : Language.values()){
            if(l.name().equalsIgnoreCase(lang)){
                return true;
            }
        }
        return false;
    }
}

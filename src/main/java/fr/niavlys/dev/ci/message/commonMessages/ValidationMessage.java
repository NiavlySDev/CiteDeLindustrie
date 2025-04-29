package fr.niavlys.dev.ci.message.commonMessages;

public enum ValidationMessage {
    AjouteXJoueur("Vous avez gagné %nombre% %type%. (Donné par un administrateur)"),
    RetireXJoueur("Vous avez perdu %nombre% %type%. (Pris par un administrateur)"),
    MisXJoueur("Votre %type% a été mis a %nombre% par un administrateur."),
    AjouteXAdmin("Vous avez ajouté %nombre% %type% au joueur %joueur%."),
    RetireXAdmin("Vous avez retiré %nombre% %type% au joueur %joueur%."),
    MisXAdmin("Vous avez mis %type% à %nombre% au joueur %joueur%."),
    GetMoney("Le joueur %joueur% a %amount% %type%.")
    ;

    private final String message;

    ValidationMessage(String message){
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}

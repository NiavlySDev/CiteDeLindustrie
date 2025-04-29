package fr.niavlys.dev.ci.message.commonMessages;

public enum ErrorMessage {
    BadArg("Vous avez entré le mauvais argument! (%argument%)"),
    PlayerNotConnected("Le joueur n'est pas connecté ou n'existe pas!"),
    TooManyArg("Vous avez entré trop d'arguments!"),
    NotEnoughArg("Vous n'avez pas entré suffisament d'arguments!"),
    ZeroNegatif("Vous ne pouvez pas entrer de nombre négatifs ou null!"),
    Negatif("Vous ne pouvez pas entrer de nombre négatifs!"),
    NoPerm("Vous n'avez pas la permission d'executer cette commande!"),
    NotInteger("Vous devez entrer un nombre!"),
    NoConsole("Vous ne pouvez pas utiliser cette commande en console!"),
    ;

    private final String message;

    ErrorMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

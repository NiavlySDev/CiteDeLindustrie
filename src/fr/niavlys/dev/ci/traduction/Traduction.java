package fr.niavlys.dev.ci.traduction;

public enum Traduction {
    CategoryBlocs("Blocs", "Blocks"),
    CategoryNourriture("Nourriture", "Food"),
    CategoryMinerais("Minerais", "Ores"),
    CategoryUtilitaires("Utilitaires", "Utilities"),
    CategoryMobs("Créatures", "Mobs"),
    CategoryAutres("Autres", "Others"),
    RareteCommun("Commun", "Common"),
    RareteRare("Rare", "Rare"),
    RareteEpique("Epique", "Epic"),
    RareteLegendaire("Legendaire", "Legendary"),
    RareteMythique("Mythique", "Mythical"),
    RareteExotique("Exotique", "Exotic"),
    RareteEternel("Eternel", "Eternal"),
    Rarete("Rarete", "Rarity"),
    Prix("Prix", "Price"),
    Par("par"," per"),
    Inventaire("Inventaire", "Inventory"),
    Info("Information", "Information"),
    Warning("Attention", "Warning"),
    Erreur("Erreur", "Error"),
    Success("Succès", "Success"),
    // Messages
    ErreurPasAssezShop("Vous n'avez pas assez de cet item dans votre inventaire pour le vendre", "You don't have enough items in your inventory to sell this item"),
    ValidationVente("Vous avez vendu vos items dans le shop", "You sold your items in the shop"),
    JoueurNonCo("Le joueur n'est pas connecté ou n'existe pas!", "The player is not connected or does not exist!"),
    PasAssezArg("Vous n'avez pas rentré assez d'arguments", "You didn't enter enough arguments"),
    TooMuchArg("Vous avez rentrée trop d'arguments dans la commande !", "You entered too many arguments in the command!"),
    NoPerm("Vous n'avez pas la permission pour faire cet action", "You don't have permission to do this action"),
    MauvaisGrade("Le grade que vous avez choisi n'est pas valide", "The grade you chose is not valid"),
    GradeChangePlayer("Vous avez changé le grade du joueur pour %grade%", "You changed the grade of the player to %grade%"),
    GradeChangeTarget("Votre grade a été changé en %grade%", "Your grade has been changed to %grade%"),
    BadArg("Vous avez entré le mauvais argument!", "You entered the wrong argument!"),
    NombreNegatif("Vous ne pouvez pas entrer de nombre négatif ou nul!", "You can't enter a negative or null number!"),
    EntrerUnNombre("Vous devez entrer un nombre!", "You must enter a number!"),
    MoneyChangePlayer("Vous avez %action% %amount% %moneyType% au joueur!", "You %action% %amount% %moneyType% to the player!"),
    MoneyChangeTarget("Vous avez été %action% de %amount% %moneyType% par un administrateur!", "You have been %action% %amount% %moneyType% by an administrator!"),
    NotLanguage("Le language demandé n'est pas un langage valide !", "The requested language is not a valid language!"),
    LangueChangee("Vous avez changé votre langage en %lang%", "You changed your language to %lang%"),
    NomChange("Vous avez changé votre nom en %nom%", "You changed your name to %nom%"),
    ;

    private final String fr;
    private final String en;

    Traduction(String fr, String en) {
        this.fr = fr;
        this.en = en;
    }
    public String getFr() {
        return fr;
    }
    public String getEn() {
        return en;
    }
    public String getByLang(Language lang) {
        if (lang.equals(Language.Français)) {
            return getFr();
        } else {
            return getEn();
        }
    }
}
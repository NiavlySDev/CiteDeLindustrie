package fr.niavlys.dev.ci.shop;

import fr.niavlys.dev.bn.main.BigNumbers;
import fr.niavlys.dev.ci.players.CIPlayer;
import fr.niavlys.dev.ci.traduction.Traduction;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public enum ShopItem {
    OakWood(ShopCategories.Blocs, Material.OAK_LOG, ShopRarete.Commun),
    SpruceWood(ShopCategories.Blocs, Material.SPRUCE_LOG, ShopRarete.Commun),
    BirchWood(ShopCategories.Blocs, Material.BIRCH_LOG, ShopRarete.Commun),
    JungleWood(ShopCategories.Blocs, Material.JUNGLE_LOG, ShopRarete.Rare),
    AcaciaWood(ShopCategories.Blocs, Material.ACACIA_LOG, ShopRarete.Rare),
    DarkOakWood(ShopCategories.Blocs, Material.DARK_OAK_LOG, ShopRarete.Rare),
    Obsidian(ShopCategories.Blocs, Material.OBSIDIAN, ShopRarete.Epique),
    Glass(ShopCategories.Blocs, Material.GLASS, ShopRarete.Commun),
    ClayBall(ShopCategories.Blocs, Material.CLAY_BALL, ShopRarete.Commun),
    Clay(ShopCategories.Blocs, Material.CLAY, ShopRarete.Rare),
    Andesite(ShopCategories.Blocs, Material.ANDESITE, ShopRarete.Rare),
    Diorite(ShopCategories.Blocs, Material.DIORITE, ShopRarete.Rare),
    Granite(ShopCategories.Blocs, Material.GRANITE, ShopRarete.Rare),

    Iron(ShopCategories.Minerais, Material.IRON_INGOT, ShopRarete.Rare),
    Gold(ShopCategories.Minerais, Material.GOLD_INGOT, ShopRarete.Epique),
    Redstone(ShopCategories.Minerais, Material.REDSTONE, ShopRarete.Commun),
    Lapis(ShopCategories.Minerais, Material.LAPIS_LAZULI, ShopRarete.Commun),
    Diamond(ShopCategories.Minerais, Material.DIAMOND, ShopRarete.Legendaire),
    Emerald(ShopCategories.Minerais, Material.EMERALD, ShopRarete.Mythique),
    Netherite(ShopCategories.Minerais, Material.NETHERITE_INGOT, ShopRarete.Eternel),
    Quartz(ShopCategories.Minerais, Material.QUARTZ, ShopRarete.Commun),
    Prismarine(ShopCategories.Minerais, Material.PRISMARINE, ShopRarete.Commun),

    Bread(ShopCategories.Nourriture, Material.BREAD, ShopRarete.Commun),
    Apple(ShopCategories.Nourriture, Material.APPLE, ShopRarete.Commun),
    Beef(ShopCategories.Nourriture, Material.COOKED_BEEF, ShopRarete.Rare),
    Chicken(ShopCategories.Nourriture, Material.COOKED_CHICKEN, ShopRarete.Rare),
    Porkchop(ShopCategories.Nourriture, Material.COOKED_PORKCHOP, ShopRarete.Rare),
    Mutton(ShopCategories.Nourriture, Material.COOKED_MUTTON, ShopRarete.Rare),
    Rabbit(ShopCategories.Nourriture, Material.COOKED_RABBIT, ShopRarete.Rare),
    Cod(ShopCategories.Nourriture, Material.COOKED_COD, ShopRarete.Rare),
    Salmon(ShopCategories.Nourriture, Material.COOKED_SALMON, ShopRarete.Rare),
    TropicalFish(ShopCategories.Nourriture, Material.TROPICAL_FISH, ShopRarete.Commun),
    Pufferfish(ShopCategories.Nourriture, Material.PUFFERFISH, ShopRarete.Rare),
    ;

    private final ShopCategories category;
    private final Material mat;
    private final ShopRarete rarete;
    ShopItem(ShopCategories category, Material mat, ShopRarete rarete){
        this.category = category;
        this.mat = mat;
        this.rarete = rarete;
    }
    public ShopCategories getCategory() {
        return category;
    }
    public Material getMat() {
        return mat;
    }
    public ShopRarete getRarete() {
        return rarete;
    }

    public ItemStack buildItem(CIPlayer player){
        ItemStack item = new ItemStack(this.getMat());
        ItemMeta itemMeta = item.getItemMeta();
        if(itemMeta == null){
            return item;
        }

        // Strings
        ChatColor color = this.getRarete().getColor();
        String rarete = Traduction.Rarete.getByLang(player.getLang())+": ";
        String rareteName = this.getRarete().getName().getByLang(player.getLang());
        String prixName = Traduction.Prix.getByLang(player.getLang());
        String par = Traduction.Par.getByLang(player.getLang());
        String inventaire = Traduction.Inventaire.getByLang(player.getLang());

        itemMeta.setLore(Arrays.asList(
                color+rareteName+rarete,
                color+prixName+" "+par+" 1 : {prix}", // TODO: Prix Items
                color+prixName+" "+par+" Stack : {prix64}",
                color+prixName+" "+par+" "+inventaire+" : {prixInv}"
        ));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ShopItem getByMaterial(Material mat){
        for(ShopItem item : ShopItem.values()){
            if(item.getMat() == mat){
                return item;
            }
        }
        return null;
    }
}
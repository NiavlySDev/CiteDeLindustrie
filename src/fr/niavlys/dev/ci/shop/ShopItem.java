package fr.niavlys.dev.ci.shop;

import fr.niavlys.dev.bn.main.BigNumbers;
import fr.niavlys.dev.ci.main.Main;
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
    JungleWood(ShopCategories.Blocs, Material.JUNGLE_LOG, ShopRarete.Commun),
    AcaciaWood(ShopCategories.Blocs, Material.ACACIA_LOG, ShopRarete.Commun),
    DarkOakWood(ShopCategories.Blocs, Material.DARK_OAK_LOG, ShopRarete.Commun),
    Obsidian(ShopCategories.Blocs, Material.OBSIDIAN, ShopRarete.Rare),
    Glass(ShopCategories.Blocs, Material.GLASS, ShopRarete.Commun),
    ClayBall(ShopCategories.Blocs, Material.CLAY_BALL, ShopRarete.Commun),
    Clay(ShopCategories.Blocs, Material.CLAY, ShopRarete.Commun),
    Andesite(ShopCategories.Blocs, Material.ANDESITE, ShopRarete.Commun),
    Diorite(ShopCategories.Blocs, Material.DIORITE, ShopRarete.Commun),
    Granite(ShopCategories.Blocs, Material.GRANITE, ShopRarete.Commun),

    Iron(ShopCategories.Minerais, Material.IRON_INGOT, ShopRarete.Commun),
    Gold(ShopCategories.Minerais, Material.GOLD_INGOT, ShopRarete.Rare),
    Redstone(ShopCategories.Minerais, Material.REDSTONE, ShopRarete.Commun),
    Lapis(ShopCategories.Minerais, Material.LAPIS_LAZULI, ShopRarete.Commun),
    Diamond(ShopCategories.Minerais, Material.DIAMOND, ShopRarete.Epique),
    Emerald(ShopCategories.Minerais, Material.EMERALD, ShopRarete.Epique),
    Netherite(ShopCategories.Minerais, Material.NETHERITE_INGOT, ShopRarete.Legendaire),
    Quartz(ShopCategories.Minerais, Material.QUARTZ, ShopRarete.Commun),
    Prismarine(ShopCategories.Minerais, Material.PRISMARINE, ShopRarete.Commun),

    Bread(ShopCategories.Nourriture, Material.BREAD, ShopRarete.Commun),
    Apple(ShopCategories.Nourriture, Material.APPLE, ShopRarete.Commun),
    Beef(ShopCategories.Nourriture, Material.COOKED_BEEF, ShopRarete.Commun),
    Chicken(ShopCategories.Nourriture, Material.COOKED_CHICKEN, ShopRarete.Commun),
    Porkchop(ShopCategories.Nourriture, Material.COOKED_PORKCHOP, ShopRarete.Commun),
    Mutton(ShopCategories.Nourriture, Material.COOKED_MUTTON, ShopRarete.Commun),
    Rabbit(ShopCategories.Nourriture, Material.COOKED_RABBIT, ShopRarete.Commun),
    Cod(ShopCategories.Nourriture, Material.COOKED_COD, ShopRarete.Commun),
    Salmon(ShopCategories.Nourriture, Material.COOKED_SALMON, ShopRarete.Commun),
    TropicalFish(ShopCategories.Nourriture, Material.TROPICAL_FISH, ShopRarete.Commun),
    Pufferfish(ShopCategories.Nourriture, Material.PUFFERFISH, ShopRarete.Commun),

    NetherStar(ShopCategories.Mobs, Material.NETHER_STAR, ShopRarete.Mythique),
    DragonHead(ShopCategories.Mobs, Material.DRAGON_HEAD, ShopRarete.Exotique),
    DragonEgg(ShopCategories.Mobs, Material.DRAGON_EGG, ShopRarete.Eternel),
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

        Integer prix = (Integer) Main.shopPrices.get(this.name(), "price");
        BigNumbers price = new BigNumbers(prix);
        BigNumbers price64 = new BigNumbers(prix);
        BigNumbers price2304 = new BigNumbers(prix);
        price64.multiply(64);
        price2304.multiply(2304);

        itemMeta.setLore(Arrays.asList(
                color+rarete+rareteName,
                color+prixName+" "+par+" 1 : "+price.toString(),
                color+prixName+" "+par+" Stack : "+price64.toString(),
                color+prixName+" "+par+" "+inventaire+" : "+price2304.toString()
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
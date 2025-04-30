package fr.niavlys.dev.ci.quests;

import fr.niavlys.dev.bn.main.BigNumbers;
import fr.niavlys.dev.ci.quests.rewards.Reward;
import fr.niavlys.dev.ci.quests.rewards.RewardType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public enum QuestList {

    BreakStone(
        QuestType.Mining,
        "Miner de la Pierre",
        Material.STONE,
        List.of(
            new Reward(RewardType.Bronze, null, null, "+ 10 K Bronze", new BigNumbers(10, "K")),
            new Reward(RewardType.Argent, null, null, "+ 1 Argent", new BigNumbers(1))
        )
    ),
    BreakIron(
        QuestType.Mining,
        "Miner du fer",
        Material.IRON_ORE,
        List.of(
            new Reward(RewardType.Bronze, null, null, "+ 35 K Bronze", new BigNumbers(35, "K")),
            new Reward(RewardType.Argent, null, null, "+ 5 Argent", new BigNumbers(5))
        )
    ),
    CraftCake(
        QuestType.Crafting,
        "Crafter des gâteaux",
        Material.CAKE,
        List.of(
            new Reward(RewardType.Bronze, null, null, "+ 50 K Bronze", new BigNumbers(50, "K")),
            new Reward(RewardType.Argent, null, null, "+ 10 Argent", new BigNumbers(10)),
            new Reward(RewardType.Item, new ItemStack(Material.ANCIENT_DEBRIS, 2), null, "+ 2 Ancients Débrits", null)
        )
    )
    ;

    private final Material mat;
    private final QuestType type;
    private final String name;
    private final List<Reward> rewards;

    QuestList(QuestType type, String name, Material mat, List<Reward> rewards){
        this.type = type;
        this.name = name;
        this.rewards = rewards;
        this.mat = mat;
    }
    public QuestType getType() {
        return type;
    }
    public Material getMat() {
        return mat;
    }
    public String getName() {
        return name;
    }

    public Reward getReward(Integer reward) {
        return this.rewards.get(reward);
    }
    public Integer getNumberRewards(){
        return this.rewards.size();
    }
    public List<Reward> getRewards(){
        return this.rewards;
    }
}

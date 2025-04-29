package fr.niavlys.dev.ci.quests;

import fr.niavlys.dev.bn.main.BigNumbers;
import fr.niavlys.dev.ci.quests.rewards.Reward;
import fr.niavlys.dev.ci.quests.rewards.RewardType;

import java.util.List;

public enum QuestList {

    BreakStone(
        QuestType.Mining,
        "Miner de la Pierre",
        List.of(
            new Reward(RewardType.Bronze, null, null, null, new BigNumbers(10, "K")),
            new Reward(RewardType.Argent, null, null, null, new BigNumbers(1))
        )
    );

    private final QuestType type;
    private final String name;
    private final List<Reward> rewards;

    QuestList(QuestType type, String name, List<Reward> rewards){
        this.type = type;
        this.name = name;
        this.rewards = rewards;
    }
    public QuestType getType() {
        return type;
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

package fr.niavlys.dev.ci.quests;

import fr.niavlys.dev.ci.quests.rewards.Reward;

import java.util.List;

public enum Quest {

    ;

    private final QuestType type;
    private final String name;
    private final String description;
    private final List<Reward> reward;

    Quest(QuestType type, String name, String description, List<Reward> reward){
        this.type = type;
        this.name = name;
        this.description = description;
        this.reward = reward;
    }
    public QuestType getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public List<Reward> getReward() {
        return reward;
    }
}

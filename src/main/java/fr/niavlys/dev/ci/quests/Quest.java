package fr.niavlys.dev.ci.quests;

import org.bukkit.Material;

public class Quest {

    private final QuestList list;
    private final QuestTier tier;
    private final Material mat;

    public Quest(QuestList list, QuestTier tier, Material mat){
        this.list = list;
        this.tier = tier;
        this.mat = mat;
    }
    public QuestList getQuest() {
        return list;
    }
    public QuestTier getTier() {
        return tier;
    }
    public Material getMat() {
        return mat;
    }
}

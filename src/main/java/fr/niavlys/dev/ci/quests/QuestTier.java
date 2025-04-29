package fr.niavlys.dev.ci.quests;

public class QuestTier {

    private Integer level;
    private final Integer levelMax;
    private Integer amount;
    private final Integer amountPerLevel;
    private Integer percentage;

    public QuestTier(Integer levelMax, Integer amountPerLevel){
        this.level = 1;
        this.levelMax = levelMax;
        this.amount = 0;
        this.amountPerLevel = amountPerLevel;
        this.percentage = 0;
    }

    public boolean levelUp(){
        if(level < levelMax){
            if(amount >= amountPerLevel*level){
                amount -= amountPerLevel*level;
                level++;
                return true;
            }
        }
        return false;
    }
    public boolean isLevelMax(){
        return level >= levelMax;
    }

    public void calculatePercentage(){
        percentage = (int) (amount/(float)(amountPerLevel*level)*100);
    }

    public Integer getPercentage() {
        return percentage;
    }
    public Integer getLevel() {
        return level;
    }
    public Integer getAmount() {
        return amount;
    }
    public Integer getLevelMax(){
        return levelMax;
    }
    public Integer getAmountMaxForLevel(Integer level){
        return amountPerLevel*level;
    }

    public void addAmount(Integer amount){
        this.amount += amount;
        calculatePercentage();
    }
    public void removeAmount(Integer amount){
        this.amount -= amount;
        calculatePercentage();
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
        calculatePercentage();
    }

    public void setLevel(Integer level) {
        this.level = level;
        calculatePercentage();
    }
}

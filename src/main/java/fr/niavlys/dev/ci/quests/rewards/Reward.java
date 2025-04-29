package fr.niavlys.dev.ci.quests.rewards;

import fr.niavlys.dev.bn.main.BigNumbers;
import fr.niavlys.dev.ci.donnees.BDD;
import fr.niavlys.dev.ci.message.MessageType;
import fr.niavlys.dev.ci.message.Messages;
import fr.niavlys.dev.ci.players.CIPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Reward {

    private RewardType type;
    private ItemStack item;
    private String command;
    private String message;
    private BigNumbers money;

    public Reward() {
        this(null, null, null, null, null);
    }
    public Reward(RewardType type, ItemStack item, String command, String message, BigNumbers money) {
        this.type = type;
        this.item = item;
        this.command = command;
        this.message = message;
        this.money = money;
    }

    private void setType(RewardType type) {
        this.type = type;
    }
    private void setItem(ItemStack item) {
        this.item = item;
    }
    private void setCommand(String command) {
        this.command = command;
    }
    private void setMessage(String message) {
        this.message = message;
    }
    private void setMoney(BigNumbers money) {
        this.money = money;
    }

    public void initRewardItem(ItemStack item, String message){
        this.setType(RewardType.Item);
        this.setItem(item);
        this.setMessage(message);
    }
    public void initRewardCommand(String command, String message){
        this.setType(RewardType.Command);
        this.setCommand(command);
        this.setMessage(message);
    }
    public void initRewardOr(BigNumbers money, String message){
        this.setType(RewardType.Or);
        this.setMoney(money);
        this.setMessage(message);
    }
    public void initRewardArgent(BigNumbers money, String message){
        this.setType(RewardType.Argent);
        this.setMoney(money);
        this.setMessage(message);
    }
    public void initRewardBronze(BigNumbers money, String message){
        this.setType(RewardType.Bronze);
        this.setMoney(money);
        this.setMessage(message);
    }

    public void RewardItem(Player p){
        p.getInventory().addItem(item);
        Messages.send(message, MessageType.Quest, p);
    }
    public void RewardCommand(Player p){
        p.getServer().dispatchCommand(p.getServer().getConsoleSender(), command);
        Messages.send(message, MessageType.Quest, p);
    }
    public void RewardOr(Player p, Integer level){
        CIPlayer player = BDD.getPlayer(p.getUniqueId());
        BigNumbers moneyLevel = new BigNumbers(money.getNumber()*(level-1));
        player.getBalance().getOr().add(moneyLevel);
        Messages.send(message+" x "+(level-1), MessageType.Quest, p);
        player.reloadScoreboard();
    }
    public void RewardArgent(Player p, Integer level){
        CIPlayer player = BDD.getPlayer(p.getUniqueId());
        BigNumbers moneyLevel = new BigNumbers(money.getNumber()*(level-1));
        player.getBalance().getArgent().add(moneyLevel);
        Messages.send(message+" x "+(level-1), MessageType.Quest, p);
        player.reloadScoreboard();
    }
    public void RewardBronze(Player p, Integer level){
        CIPlayer player = BDD.getPlayer(p.getUniqueId());
        BigNumbers moneyLevel = new BigNumbers(money.getNumber()*(level-1));
        player.getBalance().getBronze().add(moneyLevel);
        Messages.send(message+" x "+(level-1), MessageType.Quest, p);
        player.reloadScoreboard();
    }
    public void RewardPlayer(Player p, Integer level){
        if(this.getType().equals(RewardType.Bronze)){
            RewardBronze(p, level);
        } else if (this.getType().equals(RewardType.Argent)) {
            RewardArgent(p, level);
        } else if (this.getType().equals(RewardType.Or)) {
            RewardOr(p, level);
        } else if (this.getType().equals(RewardType.Command)) {
            RewardCommand(p);
        } else if (this.getType().equals(RewardType.Item)) {
            RewardItem(p);
        }
    }

    public RewardType getType() {
        return type;
    }
    public ItemStack getItem() {
        return item;
    }
    public String getCommand() {
        return command;
    }
    public String getMessage() {
        return message;
    }
    public BigNumbers getMoney() {
        return money;
    }
}

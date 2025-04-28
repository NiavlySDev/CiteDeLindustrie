package fr.niavlys.dev.sm.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {
    private final Player player;
    private final Scoreboard scoreboard;
    private final Objective objective;


    public ScoreboardManager(Player player, String title) {
        this.player = player;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("board", "dummy", title);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(scoreboard);
    }

    public void setLine(int line, String text) {
        Score score = objective.getScore(text);
        score.setScore(line);
    }

    public void removeLine(String text) {
        scoreboard.resetScores(text);
    }

    public void clearScoreboard() {
        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }
    }

    public void setTitle(String title) {
        objective.setDisplayName(title);
    }

    private String getLineByScore(int score) {
        for (String entry : scoreboard.getEntries()) {
            if (objective.getScore(entry).getScore() == score) {
                return entry;
            }
        }
        return null;
    }

    public void updateLine(int line, String newText) {
        String oldText = getLineByScore(line);
        if (oldText != null) {
            removeLine(oldText);
            setLine(line, newText);
        }
    }

    public void moveLineUp(int line) {
        if (line <= 0) return;
        String currentLine = getLineByScore(line);
        String upperLine = getLineByScore(line + 1);
        if (currentLine != null && upperLine != null) {
            swapLines(line, line + 1);
        }
    }

    public void moveLineDown(int line) {
        if (line <= 0) return;
        String currentLine = getLineByScore(line);
        String lowerLine = getLineByScore(line - 1);
        if (currentLine != null && lowerLine != null) {
            swapLines(line, line - 1);
        }
    }

    public void swapLines(int line1, int line2) {
        String text1 = getLineByScore(line1);
        String text2 = getLineByScore(line2);
        if (text1 != null && text2 != null) {
            removeLine(text1);
            removeLine(text2);
            setLine(line2, text1);
            setLine(line1, text2);
        }
    }

    public String formatScoreLine(ChatColor titleColor, String title, String value) {
        return titleColor + title + ": " + ChatColor.WHITE + value;
    }

    public void setScoreLine(int line, ChatColor titleColor, String title, String value) {
        setLine(line, formatScoreLine(titleColor, title, value));
    }

    public void setColoredTitle(ChatColor color, String title) {
        objective.setDisplayName(color + title);
    }
}

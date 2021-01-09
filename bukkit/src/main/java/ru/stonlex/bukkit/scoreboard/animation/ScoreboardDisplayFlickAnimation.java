package ru.stonlex.bukkit.scoreboard.animation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bukkit.ChatColor;
import ru.stonlex.bukkit.scoreboard.ScoreboardDisplayAnimation;

import java.util.Collection;
import java.util.LinkedList;

@NoArgsConstructor
@Getter
public class ScoreboardDisplayFlickAnimation implements ScoreboardDisplayAnimation {

    private final Collection<ChatColor> colorCollection     = new LinkedList<>();
    private Collection<String> displayAnimation             = new LinkedList<>();


    public void addTextToAnimation(@NonNull String animationText) {
        for (ChatColor chatColor : colorCollection)
            displayAnimation.add(chatColor + animationText);
    }

    public void addColor(@NonNull ChatColor chatColor) {
        colorCollection.add(chatColor);
    }


    private int displayCounter = 0;
    private String currentDisplay;

    @Override
    public void nextDisplay() {
        this.currentDisplay = ((LinkedList<String>) displayAnimation).get(displayCounter);
        displayCounter++;

        if (displayCounter >= displayAnimation.size()) {
            displayCounter = 0;
        }
    }

}

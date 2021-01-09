package ru.stonlex.bukkit.scoreboard.animation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.stonlex.bukkit.scoreboard.ScoreboardDisplayAnimation;

import java.util.Collection;
import java.util.LinkedList;

@NoArgsConstructor
@Getter
public class ScoreboardDisplayCustomAnimation implements ScoreboardDisplayAnimation {

    private Collection<String> displayAnimation = new LinkedList<>();

    public void addTextToAnimation(@NonNull String animationText) {
        displayAnimation.add(animationText);
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

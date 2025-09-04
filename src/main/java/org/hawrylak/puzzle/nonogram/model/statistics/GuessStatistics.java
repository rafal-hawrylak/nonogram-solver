package org.hawrylak.puzzle.nonogram.model.statistics;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class GuessStatistics {

    private final String name;
    private int usage = 0;
    private int guessCount = 0;
    private int oppositeGuessCount = 0;
    private int revertCount = 0;

    public void increaseUsage() {
        usage++;
    }

    public void increaseGuessCount() {
        guessCount++;
    }

    public void increaseOppositeGuessCount() {
        oppositeGuessCount++;
    }

    public void increaseRevertCount() {
        revertCount++;
    }

    @Override
    public String toString() {
        return "GuessStatistics{" +
                name +
                "; usage=" + usage +
                "; guessCount=" + guessCount +
                "; oppositeGuessCount=" + oppositeGuessCount +
                "; revertCount=" + revertCount +
                '}';

    }
}

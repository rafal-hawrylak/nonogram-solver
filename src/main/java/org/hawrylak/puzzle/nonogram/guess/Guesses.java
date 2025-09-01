package org.hawrylak.puzzle.nonogram.guess;

import lombok.Getter;

import java.util.Optional;

@Getter
public class Guesses {

    private Optional<Checkpoint> checkpoint = Optional.empty();
    private Optional<GuessChoice> lastGuess = Optional.empty();

    public void setCheckpoint(Checkpoint checkpoint) {
        this.checkpoint = Optional.of(checkpoint);
    }

    public void setGuess(GuessChoice guess) {
        this.lastGuess = Optional.of(guess);
    }
}

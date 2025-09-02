package org.hawrylak.puzzle.nonogram.guess;

import java.util.Stack;

public class GuessBranch {

    private Stack<Checkpoint> checkpoints = new Stack<>();
    private Stack<GuessChoice> guesses = new Stack<>();

    public void addCheckpoint(Checkpoint checkpoint) {
        this.checkpoints.add(checkpoint);
    }

    public void addGuess(GuessChoice guess) {
        this.guesses.add(guess);
    }

    public boolean wasGuessMade() {
        return !guesses.isEmpty();
    }

    public Checkpoint lastCheckpoint() {
        return checkpoints.isEmpty() ? null : checkpoints.peek();
    }

    public Checkpoint popLastCheckpoint() {
        return checkpoints.isEmpty() ? null : checkpoints.pop();
    }

    public GuessChoice popLastGuess() {
        return guesses.isEmpty() ? null : guesses.pop();
    }

    public GuessChoice lastGuess() {
        return guesses.isEmpty() ? null : guesses.peek();
    }

    public int getGuessCount() {
        return guesses.size();
    }
}

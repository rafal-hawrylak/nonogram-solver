package org.hawrylak.puzzle.nonogram.guess;

public class ChosenGuesserProvider implements GuesserProvider {

    private final Guesser guesser = new RandomSingleEmptyFieldCloseToAnotherFullGlobalGuesser();

    @Override
    public Guesser get() {
        return guesser;
    }
}

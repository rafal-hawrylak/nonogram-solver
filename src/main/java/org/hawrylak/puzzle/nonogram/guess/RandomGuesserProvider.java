package org.hawrylak.puzzle.nonogram.guess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGuesserProvider implements GuesserProvider {

    private final Random random = new Random();
    private final List<Guesser> guessers = new ArrayList<>();

    public RandomGuesserProvider() {
        guessers.add(new RandomSingleEmptyFieldCloseToAnotherFullGlobalGuesser());
        guessers.add(new RandomSingleFullFieldCloseToAnotherFullGlobalGuesser());
        guessers.add(new RandomSingleFullFieldCloseToAnotherFullGuesser());
        guessers.add(new RandomSingleFullFieldGuesser());
        guessers.add(new RandomSingleRandomFieldCloseToAnotherFullGlobalGuesser());
    }

    @Override
    public Guesser get() {
        return guessers.get(random.nextInt(guessers.size()));
    }
}

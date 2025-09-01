package org.hawrylak.puzzle.nonogram.guess;

import org.hawrylak.puzzle.nonogram.model.Puzzle;

public interface Guesser {
    GuessChoice guess(Puzzle puzzle);
    String getName();
}

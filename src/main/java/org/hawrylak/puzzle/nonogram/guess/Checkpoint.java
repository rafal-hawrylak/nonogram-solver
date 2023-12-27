package org.hawrylak.puzzle.nonogram.guess;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.Puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class Checkpoint {

    private Puzzle savedPuzzle;
    private Optional<Checkpoint> nextCheckpoint = Optional.empty();
    private int numberOfGuessesSoFar;
    private List<GuessChoice> madeGuesses = new ArrayList<>();
    private Optional<GuessChoice> decision = Optional.empty();

}

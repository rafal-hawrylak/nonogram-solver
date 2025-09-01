package org.hawrylak.puzzle.nonogram.guess;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.statistics.SolversStatistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class Checkpoint {

    private final Puzzle savedPuzzle;
    private final SolversStatistics savedStats;
    private final int iteration;
    private Optional<Checkpoint> nextCheckpoint = Optional.empty();
    private int numberOfGuessesSoFar = 0;
    private List<GuessChoice> madeGuesses = new ArrayList<>();
    private Optional<GuessChoice> decision = Optional.empty();

}

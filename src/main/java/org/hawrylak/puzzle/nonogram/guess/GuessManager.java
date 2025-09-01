package org.hawrylak.puzzle.nonogram.guess;

import lombok.RequiredArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.statistics.SolversStatistics;
import org.hawrylak.puzzle.nonogram.solver.utils.UtilsProvider;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.utils.PuzzleCloner;
import org.hawrylak.puzzle.nonogram.utils.StatsCloner;

@RequiredArgsConstructor
public class GuessManager {
    public final static int MAX_TOTAL_NUMBER_OF_GUESSES = 3;

    private final PuzzleCloner puzzleCloner;
    private final StatsCloner statsCloner;
    private int totalNumberOfGuesses = 0;
    private final Guesses guesses = new Guesses();
    private final Guesser guesser = new RandomSingleFullFieldGuesser();
    private final UtilsProvider utils = UtilsProvider.instance();

    public boolean exceededMaxNumberOfGuesses() {
        return totalNumberOfGuesses >= MAX_TOTAL_NUMBER_OF_GUESSES;
    }

    public boolean exceededMaxNumberOfGuessesInCurrentBranch() {
        return false; // FIXME
    }

    public void guess(Puzzle puzzle, ChangedInIteration changes, SolversStatistics stats) {
        if (wasGuessMade()) {
            // FIXME implement rollback to a checkpoint
            throw new RuntimeException("not implemented doing the opposite guess");
        } else {
            var checkpoint = createCheckpoint(puzzle, changes, stats);
            guesses.setCheckpoint(checkpoint);
            var guess = guesser.guess(puzzle);
            guesses.setGuess(guess);
            System.out.println("Guess: " + guess);
            applyGuess(guess, puzzle, changes);
            totalNumberOfGuesses++;
        }
    }

    private boolean wasGuessMade() {
        return guesses.getCheckpoint().isPresent();
    }

    private Checkpoint createCheckpoint(Puzzle puzzle, ChangedInIteration changes, SolversStatistics stats) {
        var clonedPuzzle = puzzleCloner.deepClone(puzzle);
        var clonedStats = statsCloner.deepClone(stats);
        return new Checkpoint(clonedPuzzle, clonedStats, changes.getIteration());
    }

    private void applyGuess(GuessChoice guess, Puzzle puzzle, ChangedInIteration changes) {
        utils.getGapFiller().fillSingleField(puzzle, guess.getRow(), guess.getCol(), changes, guess.getState());
    }

    public GuessingState isAnyGuessEvaluated() {
        if (guesses.getLastGuess().isPresent()) {
            return guesses.getLastGuess().get().isOpposite() ? GuessingState.GUESSING_OPPOSITE : GuessingState.GUESSING_FIRST_TRIAL;
        } else {
            return GuessingState.NOT_GUESSING;
        }
    }
}

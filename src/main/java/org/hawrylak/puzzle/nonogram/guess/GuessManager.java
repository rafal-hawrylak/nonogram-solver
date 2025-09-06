package org.hawrylak.puzzle.nonogram.guess;

import lombok.Getter;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.statistics.SolversStatistics;
import org.hawrylak.puzzle.nonogram.solver.utils.UtilsProvider;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.utils.PuzzleCloner;
import org.hawrylak.puzzle.nonogram.utils.StatsCloner;

public class GuessManager {
    public final static int MAX_TOTAL_NUMBER_OF_GUESSES = 3000;
    public final static int MAX_TOTAL_NUMBER_OF_GUESSES_IN_SINGLE_BRANCH = 2000;

    private final CheckpointManager checkpointManager;

    public GuessManager(PuzzleCloner puzzleCloner, StatsCloner statsCloner) {
        this.checkpointManager = new CheckpointManager(puzzleCloner, statsCloner);
    }

    @Getter
    private int totalNumberOfGuesses = 0;
    private final GuessBranch guessBranch = new GuessBranch();
    private final Guesser guesser = new RandomSingleFullFieldGuesser();
    private final UtilsProvider utils = UtilsProvider.instance();

    public boolean exceededMaxNumberOfGuesses() {
        return totalNumberOfGuesses >= MAX_TOTAL_NUMBER_OF_GUESSES;
    }

    public boolean exceededMaxNumberOfGuessesInCurrentBranch() {
        return guessBranch.getGuessCount() >= MAX_TOTAL_NUMBER_OF_GUESSES_IN_SINGLE_BRANCH;
    }

    public void guess(Puzzle puzzle, ChangedInIteration changes, SolversStatistics stats) {
        var checkpoint = checkpointManager.create(puzzle, changes, stats);
        guessBranch.addCheckpoint(checkpoint);
        var guess = guesser.guess(puzzle);
        System.out.println("[guessing] guess: " + guess);
        guessBranch.addGuess(guess);
        applyGuess(guess, puzzle, changes);
        totalNumberOfGuesses++;
        stats.increaseGuesserUsage(guesser.getName());
        stats.increaseGuessCount(guesser.getName());
    }

    public Restored guessOpposite(Puzzle puzzle, ChangedInIteration changes, SolversStatistics stats) {
        Checkpoint checkpoint = guessBranch.lastCheckpoint();
        GuessChoice guess = guessBranch.popLastGuess();
        GuessChoice oppositeGuess = GuessChoice.opposite(guess);
        System.out.println("[guessing] guess opposite: " + oppositeGuess);
        Restored restored = checkpointManager.restore(checkpoint, changes);
        guessBranch.addGuess(oppositeGuess);
        applyGuess(oppositeGuess, restored.puzzle(), changes);
        stats.increaseGuesserUsage(guesser.getName());
        stats.increaseOppositeGuessCount(guesser.getName());
        return restored;
    }

    public Restored revertOneGuess(Puzzle puzzle, ChangedInIteration changes, SolversStatistics stats) {
        guessBranch.popLastCheckpoint();
        guessBranch.popLastGuess();
        Restored restored = guessBranch.lastGuess().isOpposite() ? revertOneGuess(puzzle, changes, stats) : guessOpposite(puzzle, changes, stats);
        System.out.println("[guessing] revert a guess to iteration: " + changes.getIteration());
        stats.increaseGuesserUsage(guesser.getName());
        stats.increaseRevertCount(guesser.getName());
        return restored;
    }

    private void applyGuess(GuessChoice guess, Puzzle puzzle, ChangedInIteration changes) {
        utils.getGapFiller().fillSingleField(puzzle, guess.getCol(), guess.getRow(), changes, guess.getState(), false);
    }

    public GuessingState isAnyGuessEvaluated() {
        if (guessBranch.wasGuessMade()) {
            return guessBranch.lastGuess().isOpposite() ? GuessingState.GUESSING_OPPOSITE : GuessingState.GUESSING_FIRST_TRIAL;
        } else {
            return GuessingState.NOT_GUESSING;
        }
    }
}

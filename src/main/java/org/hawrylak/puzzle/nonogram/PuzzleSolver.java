package org.hawrylak.puzzle.nonogram;

import org.hawrylak.puzzle.nonogram.guess.GuessManager;
import org.hawrylak.puzzle.nonogram.guess.Restored;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.model.Solution;
import org.hawrylak.puzzle.nonogram.model.statistics.PuzzleStatistics;
import org.hawrylak.puzzle.nonogram.model.statistics.SolversStatistics;
import org.hawrylak.puzzle.nonogram.solver.Solver;
import org.hawrylak.puzzle.nonogram.solver.provider.SpecificOrderSolversCollectionProvider;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.utils.PuzzleCloner;
import org.hawrylak.puzzle.nonogram.utils.PuzzleStatisticsCalculator;
import org.hawrylak.puzzle.nonogram.utils.StatsCloner;
import org.hawrylak.puzzle.nonogram.validation.PuzzleValidator;
import org.hawrylak.puzzle.nonogram.validation.ValidationException;

import java.util.Map;
import java.util.Optional;

public class PuzzleSolver {

    public static final boolean DEBUG = true;
    public static final boolean DEBUG_PRINT_FIELDS = false;
    public static final boolean GUESS_BRANCHING_ENABLED = true;
    public static final int ITERATIONS_TO_HARD_STOP_AFTER = DEBUG ? 400 : 300;
    public static final boolean HARD_STOP = true;

    private final PuzzleStatisticsCalculator puzzleStatisticsCalculator = new PuzzleStatisticsCalculator();

    public Solution solve(Puzzle puzzleToSolve) {

        var puzzleCloner = new PuzzleCloner();
        var statsCloner = new StatsCloner();
        var puzzle = puzzleCloner.clone(puzzleToSolve);

        var stats = new SolversStatistics();
        var changes = new ChangedInIteration(puzzle);
        var puzzleValidator = new PuzzleValidator();

        Map<String, Solver> solvers = new SpecificOrderSolversCollectionProvider().provide();
//        Map<String, Solver> solvers = new RandomOrderSolverProvider().provide();
        System.out.println("solvers = " + solvers);
        var guessManager = new GuessManager(puzzleCloner, statsCloner);

        while (changes.firstIteration() || changes.anyChange()) {
            try {
                if (HARD_STOP && changes.getIteration() >= ITERATIONS_TO_HARD_STOP_AFTER) {
                    break;
                }

                changes.nextIteration();
                System.out.println("iteration = " + changes.getIteration());

                markRowsAsSolved(puzzle);

                var breakAndContinue = false;
                for (String solverName : solvers.keySet()) {
                    changes.rememberPreviousPuzzle();
                    var solver = solvers.get(solverName);
                    solver.apply(puzzle, changes);
                    updateSolversStatsAndPrintDebug(puzzle, changes, stats, solverName);
                    if (DEBUG && changes.anyChange()) {
                        breakAndContinue = true;
                        break;
                    }
                }

                var validationResult = puzzleValidator.validate(puzzle);
                if (!validationResult.isValid()) {
                    throw new ValidationException(validationResult.getMessage());
                }

                if (breakAndContinue) {
                    continue;
                }

                markRowsAsSolved(puzzle);
                tryGuessing(puzzle, changes, guessManager, stats);

                System.out.println(puzzle.toString(changes));
            } catch (RuntimeException e) {
                var potentiallyRestored = handleException(e, guessManager, puzzle, changes, stats);
                if (potentiallyRestored.isPresent()) {
                    puzzle = potentiallyRestored.get().puzzle();
                    stats = potentiallyRestored.get().stats();
                }
            }
        }

        if (DEBUG) {
            System.out.println("stats = " + stats.toString().replaceAll(",", System.lineSeparator()) + System.lineSeparator());
        }
        boolean puzzleSolved = isPuzzleSolved(puzzle);
        return new Solution(puzzleSolved, puzzle, stats);
    }

    private void tryGuessing(Puzzle puzzle, ChangedInIteration changes, GuessManager guessManager, SolversStatistics stats) {
        boolean puzzleSolved = isPuzzleSolved(puzzle);
        if (!changes.anyChange() && !puzzleSolved && GUESS_BRANCHING_ENABLED) {
            System.out.println("Branch attempt #" + (guessManager.getTotalNumberOfGuesses() + 1));
            if (guessManager.exceededMaxNumberOfGuesses()) {
                System.out.println("Total number of guesses exceeded");
            } else {
                if (guessManager.exceededMaxNumberOfGuessesInCurrentBranch()) {
                    System.out.println("Total number of guesses in current branch exceeded");
                } else {
                    guessManager.guess(puzzle, changes, stats);
                }
            }
        }
    }

    private static Optional<Restored> handleException(RuntimeException e, GuessManager guessManager, Puzzle puzzle, ChangedInIteration changes, SolversStatistics stats) {
        if (GUESS_BRANCHING_ENABLED) {
            switch (guessManager.isAnyGuessEvaluated()) {
                case NOT_GUESSING -> {
                    System.out.println("Exception happened [no guessing]: " + e);
                    throw e;
                }
                case GUESSING_FIRST_TRIAL -> {
                    if (DEBUG) {
                        System.out.println("Exception happened [before guessing opposite]: " + e);
                    }
                    return Optional.of(guessManager.guessOpposite(puzzle, changes, stats));
                }
                case GUESSING_OPPOSITE -> {
                    if (DEBUG) {
                        System.out.println("Exception happened [after guessing opposite]: " + e);
                    }
                    return Optional.of(guessManager.revertOneGuess(puzzle, changes, stats));
                }
            }
        }
        System.out.println("Exception happened [no branching enabled]: " + e);
        throw e;
    }

    private void updateSolversStatsAndPrintDebug(Puzzle puzzle, ChangedInIteration changes, SolversStatistics stats, String solverName) {
        PuzzleStatistics diff = puzzleStatisticsCalculator.diff(changes.getPreviousPuzzle(), changes.getCurrentPuzzle());
        stats.increaseSolverUsage(solverName);
        stats.increaseSolverEmptyFieldsMarked(solverName, diff.numberOfEmptyFields());
        stats.increaseSolverFullFieldsMarked(solverName, diff.numberOfFullFields());
        if (DEBUG && changes.anyChange()) {
            System.out.println(puzzle.toString(changes, solverName + " " + diff));
        }
    }

    private void markRowsAsSolved(Puzzle puzzle) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            if (rowOrCol.solved) {
                continue;
            }
            if (rowOrCol.numbersToFind.stream().noneMatch(n -> !n.found)) {
                rowOrCol.solved = true;
            }
        }
    }

    private boolean isPuzzleSolved(Puzzle puzzle) {
        return puzzle.rowsOrCols.stream().allMatch(rowOrCol -> rowOrCol.solved);
    }

}

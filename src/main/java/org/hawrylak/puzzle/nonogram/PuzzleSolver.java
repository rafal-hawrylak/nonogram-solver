package org.hawrylak.puzzle.nonogram;

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
import org.hawrylak.puzzle.nonogram.validation.PuzzleValidator;
import org.hawrylak.puzzle.nonogram.validation.ValidationException;

import java.util.Map;

public class PuzzleSolver {

    public static final boolean DEBUG = true;
    public static final int ITERATIONS_TO_STOP_AFTER = DEBUG ? 300 : 100;
    public static final boolean HARD_STOP = true;

    private final PuzzleStatisticsCalculator puzzleStatisticsCalculator = new PuzzleStatisticsCalculator();

    public Solution solve(Puzzle puzzleToSolve) {

        var puzzle = new PuzzleCloner().clone(puzzleToSolve);

        var stats = new SolversStatistics();
        var changes = new ChangedInIteration(puzzle);
        var puzzleValidator = new PuzzleValidator();

        Map<String, Solver> solvers = new SpecificOrderSolversCollectionProvider().provide();
//        Map<String, Solver> solvers = new RandomOrderSolverProvider().provide();
        System.out.println("solvers = " + solvers);

        while (changes.firstIteration() || changes.anyChange()) {
            if (HARD_STOP && changes.getIteration() >= ITERATIONS_TO_STOP_AFTER) {
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
                updateStatsAndPrintDebug(puzzle, changes, stats, solverName);
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

            System.out.println(puzzle.toString(changes));
        }
        markRowsAsSolved(puzzle);

        if (DEBUG) {
            System.out.println("stats = " + stats.toString().replaceAll(",", System.lineSeparator()) + System.lineSeparator());
        }
        boolean puzzleSolved = isPuzzleSolved(puzzle);
        return new Solution(puzzleSolved, puzzle, stats);
    }

    private void updateStatsAndPrintDebug(Puzzle puzzle, ChangedInIteration changes, SolversStatistics stats, String solverName) {
        PuzzleStatistics diff = puzzleStatisticsCalculator.diff(changes.getPreviousPuzzle(), changes.getCurrentPuzzle());
        stats.increaseUsage(solverName);
        stats.increaseEmptyFieldsMarked(solverName, diff.numberOfEmptyFields());
        stats.increaseFullFieldsMarked(solverName, diff.numberOfEmptyFields());
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

package org.hawrylak.puzzle.nonogram;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.model.solution.Solution;
import org.hawrylak.puzzle.nonogram.model.solution.SolversStatistics;
import org.hawrylak.puzzle.nonogram.solver.Solver;
import org.hawrylak.puzzle.nonogram.solver.provider.SpecificOrderSolversProvider;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.utils.PuzzleCloner;

import java.util.Map;

public class PuzzleSolver {

    public static final boolean DEBUG = true;
    public static final int ITERATIONS_TO_STOP_AFTER = DEBUG ? 300 : 100;
    public static final boolean HARD_STOP = true;

    public Solution solve(Puzzle puzzleToSolve) {

        var puzzle = new PuzzleCloner().clone(puzzleToSolve);
        var stats = new SolversStatistics();
        var changes = new ChangedInIteration(puzzle);

        Map<String, Solver> solvers = new SpecificOrderSolversProvider().provide();
        System.out.println("solvers = " + solvers);

        while (changes.firstIteration() || changes.anyChange()) {
            if (HARD_STOP && changes.getIteration() >= ITERATIONS_TO_STOP_AFTER) {
                break;
            }

            changes.nextIteration();
            System.out.println("iteration = " + changes.getIteration());

            // rules
            markRowsAsSolved(puzzle);

            var breakAndContinue = false;
            for (String solverName : solvers.keySet()) {
                var solver = solvers.get(solverName);
                solver.apply(puzzle, changes);
                if (DEBUG && changes.anyChange()) {
                    statsAndPrintDebug(puzzle, changes, stats, solverName);
                    breakAndContinue = true;
                    break;
                }
            }
            if (breakAndContinue) {
                continue;
            }

            System.out.println(puzzle.toString(changes));
        }
        markRowsAsSolved(puzzle);

        if (DEBUG) {
            System.out.println("stats = " + stats.getSolversUsage().getStats().toString().replaceAll(",", System.lineSeparator()) + System.lineSeparator());
        }
        boolean puzzleSolved = isPuzzleSolved(puzzle);
        return new Solution(puzzleSolved, puzzle, stats);
    }

    private void statsAndPrintDebug(Puzzle puzzle, ChangedInIteration changes, SolversStatistics stats, String solverName) {
        if (DEBUG) {
            System.out.println(puzzle.toString(changes, solverName));
            var counter = stats.getSolversUsage().getStats().getOrDefault(solverName, 0);
            stats.getSolversUsage().getStats().put(solverName, counter + 1);
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

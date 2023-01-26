package org.hawrylak.puzzle.nonogram;

import java.util.HashMap;
import java.util.Map;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.Solver;
import org.hawrylak.puzzle.nonogram.solver.provider.OriginalOrderSolversProvider;

public class PuzzleSolver {

    public boolean solve(Puzzle puzzle) {

        boolean debug = true;
        Map<String, Integer> stats = new HashMap<>();
        var changes = new ChangedInIteration(puzzle, debug);
        var hardStop = true;
        var iterationsToStopAfter = debug ? 250 : 100;

        Map<String, Solver> solvers = new OriginalOrderSolversProvider().provide();

        while (changes.firstIteration() || changes.anyChange()) {
            if (hardStop && changes.getIteration() >= iterationsToStopAfter) {
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
                if (changes.debugModeAndChangesDone()) {
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

        if (changes.isDebug()) {
            System.out.println("stats = " + stats.toString().replaceAll(",", System.lineSeparator()) + System.lineSeparator());
        }
        return isPuzzleSolved(puzzle);
    }

    private void statsAndPrintDebug(Puzzle puzzle, ChangedInIteration changes, Map<String, Integer> stats, String debugHeader) {
        if (changes.isDebug()) {
            System.out.println(puzzle.toString(changes, debugHeader));
            var counter = stats.getOrDefault(debugHeader, 0);
            stats.put(debugHeader, counter + 1);
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

package org.hawrylak.puzzle.nonogram.solver;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.FieldFinder;
import org.hawrylak.puzzle.nonogram.GapFinder;
import org.hawrylak.puzzle.nonogram.NumberSelector;
import org.hawrylak.puzzle.nonogram.RowSelector;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solvers.CloseAllGapsBeforeFirstAndAfterLastFoundNumberSolver;
import org.hawrylak.puzzle.nonogram.solvers.CloseAllTheGapsIfAllFullMarkedSolver;
import org.hawrylak.puzzle.nonogram.solvers.CloseAtEdgesSolver;
import org.hawrylak.puzzle.nonogram.solvers.CloseTheOnlyCombinationSolver;
import org.hawrylak.puzzle.nonogram.solvers.CloseTooSmallToFitAnythingSolver;
import org.hawrylak.puzzle.nonogram.solvers.CloseTooSmallToFitFirstOrLastNumberSolver;
import org.hawrylak.puzzle.nonogram.solvers.CloseWhenAllNumbersAreFoundSolver;
import org.hawrylak.puzzle.nonogram.solvers.CloseWhenSingleGapWithNumbersNotFoundSolver;
import org.hawrylak.puzzle.nonogram.solvers.CloseWithOneEndSolver;
import org.hawrylak.puzzle.nonogram.solvers.ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumberSolver;
import org.hawrylak.puzzle.nonogram.solvers.FillTheNumbersWithStartAndEndNotConnectedSolver;
import org.hawrylak.puzzle.nonogram.solvers.FindUnmergableSubGapsForBiggestForFirstAndLastNotFoundSolver;
import org.hawrylak.puzzle.nonogram.solvers.FindUnmergableSubGapsForBiggestSolver;
import org.hawrylak.puzzle.nonogram.solvers.FitTheBiggestNumbersInOnlyPossibleGapsSolver;
import org.hawrylak.puzzle.nonogram.solvers.IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparatelySolver;
import org.hawrylak.puzzle.nonogram.solvers.MarkEndingsOfSubGapWhenThereIsNoBiggerNumberSolver;
import org.hawrylak.puzzle.nonogram.solvers.MarkMinimalAndMaximalSubgapsSolver;
import org.hawrylak.puzzle.nonogram.solvers.NarrowGapsBeforeFirstAndAfterLastSolver;
import org.hawrylak.puzzle.nonogram.solvers.SecondSubGapMayBeClosedSolver;
import org.hawrylak.puzzle.nonogram.solvers.Solver;
import org.hawrylak.puzzle.nonogram.solvers.TryToAssignNumberToFilledGapSolver;
import org.hawrylak.puzzle.nonogram.solvers.TryToFillGapsBetweenGapsWithKnownNumbersSolver;

public class PuzzleSolver {

    public boolean solve(Puzzle puzzle) {

        boolean debug = true;
        Map<String, Integer> stats = new HashMap<>();
        var changes = new ChangedInIteration(puzzle, debug);
        var hardStop = true;
        var iterationsToStopAfter = debug ? 200 : 100;

        Map<String, Solver> solvers = getOrderedSolvers();

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

    private Map<String, Solver> getOrderedSolvers() {
        FieldFinder fieldFinder = new FieldFinder();
        RowSelector rowSelector = new RowSelector();
        NumberSelector numberSelector = new NumberSelector();
        GapFinder gapFinder = new GapFinder();
        GapFiller gapFiller = new GapFiller(fieldFinder, numberSelector, gapFinder);
        GapCloser gapCloser = new GapCloser(fieldFinder, gapFiller, numberSelector);

        Map<String, Solver> solvers = new LinkedHashMap<>();
        addSolver(solvers, new CloseTooSmallToFitAnythingSolver(gapFinder, gapCloser));
        addSolver(solvers, new CloseAtEdgesSolver(rowSelector, gapFiller));
        addSolver(solvers, new CloseWithOneEndSolver(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new CloseTheOnlyCombinationSolver(gapFiller));
        addSolver(solvers, new CloseWhenAllNumbersAreFoundSolver(gapFinder, gapCloser));
        addSolver(solvers, new CloseAllTheGapsIfAllFullMarkedSolver(gapFinder, rowSelector, gapCloser));
        addSolver(solvers, new FitTheBiggestNumbersInOnlyPossibleGapsSolver(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new CloseWhenSingleGapWithNumbersNotFoundSolver(gapFinder, gapFiller));
        addSolver(solvers, new CloseAllGapsBeforeFirstAndAfterLastFoundNumberSolver(gapFinder, gapCloser));
        addSolver(solvers, new FillTheNumbersWithStartAndEndNotConnectedSolver(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new NarrowGapsBeforeFirstAndAfterLastSolver(gapFinder, gapCloser, numberSelector));
        addSolver(solvers, new MarkEndingsOfSubGapWhenThereIsNoBiggerNumberSolver(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new FindUnmergableSubGapsForBiggestSolver(gapFinder, gapCloser, numberSelector));
        addSolver(solvers, new TryToFillGapsBetweenGapsWithKnownNumbersSolver(gapFinder, gapFiller, numberSelector));
        addSolver(solvers, new TryToAssignNumberToFilledGapSolver(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new CloseTooSmallToFitFirstOrLastNumberSolver(gapFinder, gapCloser, numberSelector));
        addSolver(solvers, new FindUnmergableSubGapsForBiggestForFirstAndLastNotFoundSolver(gapFinder, gapCloser, numberSelector));
        addSolver(solvers, new ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumberSolver(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparatelySolver(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new SecondSubGapMayBeClosedSolver(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new MarkMinimalAndMaximalSubgapsSolver(gapFinder, numberSelector, gapFiller));

        return solvers;
    }

    private void addSolver(Map<String, Solver> solvers, Solver solver) {
        solvers.put(solver.getName(), solver);
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

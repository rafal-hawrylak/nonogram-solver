package org.hawrylak.puzzle.nonogram.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.CloseAllGapsBeforeFirstAndAfterLastFoundNumber;
import org.hawrylak.puzzle.nonogram.solver.CloseAllTheGapsIfAllFullMarked;
import org.hawrylak.puzzle.nonogram.solver.CloseAtEdges;
import org.hawrylak.puzzle.nonogram.solver.CloseTheOnlyCombination;
import org.hawrylak.puzzle.nonogram.solver.CloseTooSmallToFitAnything;
import org.hawrylak.puzzle.nonogram.solver.CloseTooSmallToFitFirstOrLastNumber;
import org.hawrylak.puzzle.nonogram.solver.CloseWhenAllNumbersAreFound;
import org.hawrylak.puzzle.nonogram.solver.CloseWhenSingleGapWithNumbersNotFound;
import org.hawrylak.puzzle.nonogram.solver.CloseWithOneEnd;
import org.hawrylak.puzzle.nonogram.solver.ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber;
import org.hawrylak.puzzle.nonogram.solver.FillTheNumbersWithStartAndEndNotConnected;
import org.hawrylak.puzzle.nonogram.solver.FindUnmergableSubGapsForBiggestForFirstAndLastNotFound;
import org.hawrylak.puzzle.nonogram.solver.FindUnmergableSubGapsForBiggest;
import org.hawrylak.puzzle.nonogram.solver.FitTheBiggestNumbersInOnlyPossibleGaps;
import org.hawrylak.puzzle.nonogram.solver.IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately;
import org.hawrylak.puzzle.nonogram.solver.MarkEndingsOfSubGapWhenThereIsNoBiggerNumber;
import org.hawrylak.puzzle.nonogram.solver.MarkMinimalAndMaximalSubgaps;
import org.hawrylak.puzzle.nonogram.solver.NarrowGapsBeforeFirstAndAfterLast;
import org.hawrylak.puzzle.nonogram.solver.SecondSubGapMayBeClosed;
import org.hawrylak.puzzle.nonogram.solver.Solver;
import org.hawrylak.puzzle.nonogram.solver.TryToAssignNumberToFilledGap;
import org.hawrylak.puzzle.nonogram.solver.TryToFillGapsBetweenGapsWithKnownNumbers;

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
        addSolver(solvers, new CloseTooSmallToFitAnything(gapFinder, gapCloser));
        addSolver(solvers, new CloseAtEdges(rowSelector, gapFiller));
        addSolver(solvers, new CloseWithOneEnd(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new CloseTheOnlyCombination(gapFiller));
        addSolver(solvers, new CloseWhenAllNumbersAreFound(gapFinder, gapCloser));
        addSolver(solvers, new CloseAllTheGapsIfAllFullMarked(gapFinder, rowSelector, gapCloser));
        addSolver(solvers, new FitTheBiggestNumbersInOnlyPossibleGaps(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new CloseWhenSingleGapWithNumbersNotFound(gapFinder, gapFiller));
        addSolver(solvers, new CloseAllGapsBeforeFirstAndAfterLastFoundNumber(gapFinder, gapCloser));
        addSolver(solvers, new FillTheNumbersWithStartAndEndNotConnected(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new NarrowGapsBeforeFirstAndAfterLast(gapFinder, gapCloser, numberSelector));
        addSolver(solvers, new MarkEndingsOfSubGapWhenThereIsNoBiggerNumber(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new FindUnmergableSubGapsForBiggest(gapFinder, gapCloser, numberSelector));
        addSolver(solvers, new TryToFillGapsBetweenGapsWithKnownNumbers(gapFinder, gapFiller, numberSelector));
        addSolver(solvers, new TryToAssignNumberToFilledGap(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new CloseTooSmallToFitFirstOrLastNumber(gapFinder, gapCloser, numberSelector));
        addSolver(solvers, new FindUnmergableSubGapsForBiggestForFirstAndLastNotFound(gapFinder, gapCloser, numberSelector));
        addSolver(solvers, new ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new SecondSubGapMayBeClosed(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new MarkMinimalAndMaximalSubgaps(gapFinder, numberSelector, gapFiller));

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

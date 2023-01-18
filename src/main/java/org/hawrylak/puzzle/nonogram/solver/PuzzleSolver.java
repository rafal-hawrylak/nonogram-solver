package org.hawrylak.puzzle.nonogram.solver;

import java.util.HashMap;
import java.util.Map;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.FieldFinder;
import org.hawrylak.puzzle.nonogram.GapFinder;
import org.hawrylak.puzzle.nonogram.NumberSelector;
import org.hawrylak.puzzle.nonogram.RowSelector;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;

public class PuzzleSolver {

    private final FieldFinder fieldFinder = new FieldFinder();
    private final RowSelector rowSelector = new RowSelector();
    private final NumberSelector numberSelector = new NumberSelector();
    private final GapFinder gapFinder = new GapFinder();
    private final GapFiller gapFiller = new GapFiller(fieldFinder, numberSelector, gapFinder);
    private final GapCloser gapCloser = new GapCloser(fieldFinder, gapFinder, gapFiller, numberSelector);
    private final NumberCloser numberCloser = new NumberCloser(fieldFinder, rowSelector, numberSelector, gapFinder, gapFiller, gapCloser);

    public boolean solve(Puzzle puzzle) {

        boolean debug = true;
        Map<String, Integer> stats = new HashMap<>();
        var changes = new ChangedInIteration(puzzle, debug);
        var hardStop = true;
        var iterationsToStopAfter = debug ? 200 : 100;
        while (changes.firstIteration() || changes.anyChange()) {
            if (hardStop && changes.getIteration() >= iterationsToStopAfter) {
                break;
            }

            changes.nextIteration();
            System.out.println("iteration = " + changes.getIteration());

            // rules
            markRowsAsSolved(puzzle);

            gapCloser.closeTooSmallToFitAnything(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "closeTooSmallToFitAnything");
                continue;
            }
            numberCloser.closeAtEdges(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "closeAtEdges");
                continue;
            }
            numberCloser.closeWithOneEnd(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "closeWithOneEnd");
                continue;
            }
            numberCloser.closeTheOnlyCombination(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "closeTheOnlyCombination");
                continue;
            }
            gapCloser.closeWhenAllNumbersAreFound(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "closeWhenAllNumbersAreFound");
                continue;
            }
            numberCloser.closeAllTheGapsIfAllFullMarked(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "closeAllTheGapsIfAllFullMarked");
                continue;
            }
            numberCloser.fitTheBiggestNumbersInOnlyPossibleGaps(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "fitTheBiggestNumbersInOnlyPossibleGaps");
                continue;
            }
            gapCloser.closeWhenSingleGapWithNumbersNotFound(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "closeWhenSingleGapWithNumbersNotFound");
                continue;
            }
            gapCloser.closeAllGapsBeforeFirstAndAfterLastFoundNumber(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "closeAllGapsBeforeFirstAndAfterLastFoundNumber");
                continue;
            }
            numberCloser.fillTheNumbersWithStartAndEndNotConnected(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "fillTheNumbersWithStartAndEndNotConnected");
                continue;
            }
            gapCloser.narrowGapsBeforeFirstAndAfterLast(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "narrowGapsBeforeFirstAndAfterLast");
                continue;
            }
            numberCloser.markEndingsOfSubGapWhenThereIsNoBiggerNumber(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "markEndingsOfSubGapWhenThereIsNoBiggerNumber");
                continue;
            }
            gapCloser.findUnmergableSubGapsForBiggest(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "findUnmergableSubGapsForBiggest");
                continue;
            }
            gapFiller.tryToFillGapsBetweenGapsWithKnownNumbers(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "tryToFillGapsBetweenGapsWithKnownNumbers");
                continue;
            }
            gapFiller.tryToAssignNumberToFilledGap(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "tryToAssignNumberToFilledGap");
                continue;
            }
            gapCloser.closeTooSmallToFitFirstOrLastNumber(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "closeTooSmallToFitFirstOrLastNumber");
                continue;
            }
            gapCloser.findUnmergableSubGapsForBiggestForFirstAndLastNotFound(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "findUnmergableSubGapsForBiggestForFirstAndLastNotFound");
                continue;
            }
            numberCloser.extendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "extendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber");
                continue;
            }
            numberCloser.ifAllNumbersWontFitIntoSingleGapTryToFitThemSeparately(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "ifAllNumbersWontFitIntoSingleGapTryToFitThemSeparately");
                continue;
            }
            numberCloser.secondSubGapMayBeClosed(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "secondSubGapMayBeClosed");
                continue;
            }
            numberCloser.markMinimalAndMaximalSubgaps(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                statsAndPrintDebug(puzzle, changes, stats, "markMinimalAndMaximalSubgaps");
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

package org.hawrylak.puzzle.nonogram.solver;

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
    private final GapFiller gapFiller = new GapFiller(fieldFinder, gapFinder);
    private final GapCloser gapCloser = new GapCloser(fieldFinder, gapFinder, gapFiller, numberSelector);
    private final NumberCloser numberCloser = new NumberCloser(fieldFinder, rowSelector, numberSelector, gapFinder, gapFiller, gapCloser);

    public boolean solve(Puzzle puzzle) {

        boolean debug = true;
        var changes = new ChangedInIteration(puzzle, debug);
        var hardStop = true;
        var iterationsToStopAfter = debug ? 50 : 100;
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
                printDebug(puzzle, changes, debug, "closeTooSmallToFitAnything");
                continue;
            }
            numberCloser.closeAtEdges(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                printDebug(puzzle, changes, debug, "closeAtEdges");
                continue;
            }
            numberCloser.closeWithOneEnd(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                printDebug(puzzle, changes, debug, "closeWithOneEnd");
                continue;
            }
            numberCloser.closeTheOnlyCombination(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                printDebug(puzzle, changes, debug, "closeTheOnlyCombination");
                continue;
            }
            gapCloser.closeWhenAllNumbersAreFound(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                printDebug(puzzle, changes, debug, "closeWhenAllNumbersAreFound");
                continue;
            }
            numberCloser.closeAllTheGapsIfAllFullMarked(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                printDebug(puzzle, changes, debug, "closeAllTheGapsIfAllFullMarked");
                continue;
            }
            numberCloser.fitTheBiggestNumbersInOnlyPossibleGaps(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                printDebug(puzzle, changes, debug, "fitTheBiggestNumbersInOnlyPossibleGaps");
                continue;
            }
            gapCloser.closeWhenSingleGapWithNumbersNotFound(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                printDebug(puzzle, changes, debug, "closeWhenSingleGapWithNumbersNotFound");
                continue;
            }
            gapCloser.closeAllGapsBeforeFirstAndAfterLastFoundNumber(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                printDebug(puzzle, changes, debug, "closeAllGapsBeforeFirstAndAfterLastFoundNumber");
                continue;
            }
            numberCloser.fillTheNumbersWithStartAndEndNotConnected(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                printDebug(puzzle, changes, debug, "fillTheNumbersWithStartAndEndNotConnected");
                continue;
            }
            gapCloser.narrowGapsBeforeFirstAndAfterLast(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                printDebug(puzzle, changes, debug, "narrowGapsBeforeFirstAndAfterLast");
                continue;
            }
            numberCloser.markEndingsOfSubGapWhenThereIsNoBiggerNumber(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                printDebug(puzzle, changes, debug, "markEndingsOfSubGapWhenThereIsNoBiggerNumber");
                continue;
            }
            gapCloser.findUnmergableSubGaps(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                printDebug(puzzle, changes, debug, "findUnmergableSubGaps");
                continue;
            }
            gapFiller.tryToFillGapsBetweenGapsWithKnownNumbers(puzzle, changes);
            if (changes.debugModeAndChangesDone()) {
                printDebug(puzzle, changes, debug, "tryToFillGapsBetweenGapsWithKnownNumbers");
                continue;
            }

            System.out.println(puzzle.toString(changes));
        }

        markRowsAsSolved(puzzle);
        return isPuzzleSolved(puzzle);
    }

    private void printDebug(Puzzle puzzle, ChangedInIteration changes, boolean debug, String debugHeader) {
        if (debug) {
            System.out.println(puzzle.toString(changes, debugHeader));
        }
    }

    private void markRowsAsSolved(Puzzle puzzle) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            if (rowOrCol.solved) continue;
            if (rowOrCol.numbersToFind.stream().noneMatch(n -> !n.found)) {
                rowOrCol.solved = true;
            }
        }
    }

    private boolean isPuzzleSolved(Puzzle puzzle) {
        return puzzle.rowsOrCols.stream().allMatch(rowOrCol -> rowOrCol.solved);
    }

}

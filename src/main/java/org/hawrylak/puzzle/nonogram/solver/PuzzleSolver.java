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

        var changes = new ChangedInIteration(puzzle);
        boolean debug = false;
        var hardStop = true;
        var iterationsToStopAfter = 50;
        while (changes.firstIteration() || changes.anyChange()) {
            changes.nextIteration();
            System.out.println("iteration = " + changes.getIteration());

            // rules
            markRowsAsSolved(puzzle);
            gapCloser.closeTooSmallToFitAnything(puzzle, changes);
            printDebug(puzzle, changes, debug, "closeTooSmallToFitAnything");
            numberCloser.closeAtEdges(puzzle, changes);
            printDebug(puzzle, changes, debug, "closeAtEdges");
            numberCloser.closeWithOneEnd(puzzle, changes);
            printDebug(puzzle, changes, debug, "closeWithOneEnd");
            numberCloser.closeTheOnlyCombination(puzzle, changes);
            printDebug(puzzle, changes, debug, "closeTheOnlyCombination");
            gapCloser.closeWhenAllNumbersAreFound(puzzle, changes);
            printDebug(puzzle, changes, debug, "closeWhenAllNumbersAreFound");
            numberCloser.closeAllTheGapsIfAllFullMarked(puzzle, changes);
            printDebug(puzzle, changes, debug, "closeAllTheGapsIfAllFullMarked");
            numberCloser.fitTheNumbersInOnlyPossibleGaps(puzzle, changes);
            printDebug(puzzle, changes, debug, "closeTheNumbersAlreadyFilledButNotMarked");
            gapCloser.closeWhenSingleGapWithNumbersNotFound(puzzle, changes);
            printDebug(puzzle, changes, debug, "closeWhenSingleGapWithNumbersNotFound");
            gapCloser.closeAllGapsBeforeFirstAndAfterLastFoundNumber(puzzle, changes);
            printDebug(puzzle, changes, debug, "closeAllGapsBeforeFirstFoundNumber");
            numberCloser.fillTheNumbersWithStartAndEndNotConnected(puzzle, changes);
            printDebug(puzzle, changes, debug, "fillTheNumbersWithStartAndEndNotConnected");
            gapCloser.narrowGapsBeforeFirstAndAfterLast(puzzle, changes);
            printDebug(puzzle, changes, debug, "narrowGapsBetweenFirstAndAfterLast");
            numberCloser.markEndingsOfSubGapWhenThereIsNoBiggerNumber(puzzle, changes);
            printDebug(puzzle, changes, debug, "markEndingsOfSubGapWhenThereIsNoBiggerNumber");
            gapCloser.findUnmergableSubGaps(puzzle, changes);
            printDebug(puzzle, changes, debug, "findUnmergableSubGaps");
            gapFiller.tryToFillGapsBetweenGapsWithKnownNumbers(puzzle, changes);
            printDebug(puzzle, changes, debug, "tryToFillGapsBetweenGapsWithKnownNumbers");

            System.out.println(puzzle.toString(changes));

            if (hardStop && changes.getIteration() >= iterationsToStopAfter) {
                break;
            }
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

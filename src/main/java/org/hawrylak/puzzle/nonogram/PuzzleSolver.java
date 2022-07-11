package org.hawrylak.puzzle.nonogram;

public class PuzzleSolver {

    private final FieldFinder fieldFinder = new FieldFinder();
    private final RowSelector rowSelector = new RowSelector();
    private final NumberSelector numberSelector = new NumberSelector();
    private final GapFinder gapFinder = new GapFinder();
    private final GapFiller gapFiller = new GapFiller(fieldFinder);
    private final GapCloser gapCloser = new GapCloser(fieldFinder, gapFinder, gapFiller, numberSelector);
    private final NumberCloser numberCloser = new NumberCloser(rowSelector, numberSelector, gapFinder, gapFiller, gapCloser);

    boolean solve(Puzzle puzzle) {

        var changesLast = new ChangedInIteration(puzzle);
        var changesCurrent = new ChangedInIteration(puzzle);
        boolean debug = false;
        var hardStop = true;
        var iterationsToStopAfter = 50;
        while (changesLast.firstIteration() || changesLast.anyChange()) {
            System.out.println("iteration = " + changesLast.iteration);

            // rules
            markRowsAsSolved(puzzle);
            gapCloser.closeTooSmallToFitAnything(puzzle, changesLast, changesCurrent);
            printDebug(puzzle, changesCurrent, debug, "closeTooSmallToFitAnything");
            numberCloser.closeAtEdges(puzzle, changesLast, changesCurrent);
            printDebug(puzzle, changesCurrent, debug, "closeAtEdges");
            numberCloser.closeWithOneEnd(puzzle, changesLast, changesCurrent);
            printDebug(puzzle, changesCurrent, debug, "closeWithOneEnd");
            numberCloser.closeTheOnlyCombination(puzzle, changesCurrent);
            printDebug(puzzle, changesCurrent, debug, "closeTheOnlyCombination");
            gapCloser.closeWhenAllNumbersAreFound(puzzle, changesCurrent);
            printDebug(puzzle, changesCurrent, debug, "closeWhenAllNumbersAreFound");
            numberCloser.closeAllTheGapsIfAllFullMarked(puzzle, changesLast, changesCurrent);
            printDebug(puzzle, changesCurrent, debug, "closeAllTheGapsIfAllFullMarked");
            numberCloser.fitTheNumbersInOnlyPossibleGaps(puzzle, changesCurrent);
            printDebug(puzzle, changesCurrent, debug, "closeTheNumbersAlreadyFilledButNotMarked");
            gapCloser.closeWhenSingleGapWithNumbersNotFound(puzzle, changesCurrent);
            printDebug(puzzle, changesCurrent, debug, "closeWhenSingleGapWithNumbersNotFound");

            System.out.println(puzzle.toString(changesCurrent));
            changesLast.nextIteration(changesCurrent);

            if (hardStop && changesCurrent.iteration >= iterationsToStopAfter) {
                break;
            }
        }

        markRowsAsSolved(puzzle);
        return isPuzzleSolved(puzzle);
    }

    private void printDebug(Puzzle puzzle, ChangedInIteration changesCurrent, boolean debug, String debugHeader) {
        if (debug) {
            System.out.println(puzzle.toString(changesCurrent, debugHeader));
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

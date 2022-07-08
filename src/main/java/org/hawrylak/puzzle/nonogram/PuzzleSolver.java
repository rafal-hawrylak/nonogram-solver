package org.hawrylak.puzzle.nonogram;

public class PuzzleSolver {

    private final RowSelector rowSelector = new RowSelector();
    private final NumberSelector numberSelector = new NumberSelector();
    private final GapFinder gapFinder = new GapFinder();
    private final GapFiller gapFiller = new GapFiller(gapFinder, numberSelector);
    private final GapCloser gapCloser = new GapCloser(gapFinder, gapFiller, numberSelector);
    private final NumberCloser numberCloser = new NumberCloser(rowSelector, numberSelector, gapFinder, gapFiller, gapCloser);

    boolean solve(Puzzle puzzle) {

        var changesLast = new ChangedInIteration(puzzle);
        var changesCurrent = new ChangedInIteration(puzzle);
        var hardStop = true;
        var iterationsToStopAfter = 10;
        while (changesLast.firstIteration() || changesLast.anyChange()) {
            System.out.println("iteration = " + changesLast.iteration);

            // rules
            markRowsAsSolved(puzzle);
            gapCloser.closeTooSmallToFitAnything(puzzle, changesLast, changesCurrent);
            gapFiller.fillTheOnlyMatchingGaps(puzzle, changesLast, changesCurrent);
            numberCloser.closeAtEdges(puzzle, changesLast, changesCurrent);
            numberCloser.closeWithOneEnd(puzzle, changesLast, changesCurrent);
            numberCloser.closeTheOnlyCombination(puzzle, changesCurrent);
            gapCloser.closeWhenAllNumbersAreFound(puzzle, changesCurrent);
            numberCloser.closeAllTheGapsIfAllFullMarked(puzzle, changesLast, changesCurrent);
            gapCloser.closeWhenSingleGapWithNumbersNotFound(puzzle, changesCurrent);

            System.out.println(puzzle.toString(changesCurrent));
            changesLast.nextIteration(changesCurrent);

            if (hardStop && changesCurrent.iteration >= iterationsToStopAfter) {
                break;
            }
        }

        return isPuzzleSolved(puzzle);
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

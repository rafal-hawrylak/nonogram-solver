package org.hawrylak.puzzle.nonogram;

public class PuzzleSolver {

    private final GapFinder gapFinder = new GapFinder();
    private final GapCloser gapCloser = new GapCloser(gapFinder);
    private final GapFiller gapFiller = new GapFiller(gapFinder, gapCloser);
    private final RowSelector rowSelector = new RowSelector();
    private final NumberCloser numberCloser = new NumberCloser(rowSelector, gapFinder, gapFiller);

    boolean solve(Puzzle puzzle) {

        var changesLast = new ChangedInIteration(puzzle);
        var changesCurrent = new ChangedInIteration(puzzle);
        var hardStop = true;
        var iterationsToStopAfter = 5;
        while (changesLast.firstIteration() || changesLast.anyChange()) {
            System.out.println("iteration = " + changesLast.iteration);

            // rules
            markRowsAsSolved(puzzle);
            gapCloser.closeNotNeeded(puzzle, changesLast, changesCurrent);
            gapFiller.fillTheOnlyMatchingGaps(puzzle, changesLast, changesCurrent);
            numberCloser.closeAtEdges(puzzle, changesLast, changesCurrent);
            numberCloser.closeWithOneEnd(puzzle, changesLast, changesCurrent);
            numberCloser.closeTheOnlyCombination(puzzle, changesLast, changesCurrent);

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

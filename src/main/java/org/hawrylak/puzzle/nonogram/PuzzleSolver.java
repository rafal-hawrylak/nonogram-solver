package org.hawrylak.puzzle.nonogram;

public class PuzzleSolver {

    private final GapFinder gapFinder = new GapFinder();
    private final GapCloser gapCloser = new GapCloser(gapFinder);
    private final GapFiller gapFiller = new GapFiller(gapFinder, gapCloser);
    private final RowSelector rowSelector = new RowSelector();
    private final NumberCloser numberCloser = new NumberCloser(rowSelector, gapFiller);

    boolean solve(Puzzle puzzle) {

        var changesLast = new ChangedInIteration(puzzle);
        var changesCurrent = new ChangedInIteration(puzzle);
        while (changesLast.firstIteration() || changesLast.anyChange()) {
            System.out.println("iteration = " + changesLast.iteration);

            // rules
            markRowsAsSolved(puzzle);
            gapCloser.closeNotNeeded(puzzle, changesLast, changesCurrent);
            gapFiller.fillTheOnlyMatchingGaps(puzzle, changesLast, changesCurrent);
            numberCloser.closeAtEdges(puzzle, changesLast, changesCurrent);

            System.out.println(puzzle.toString(changesCurrent));
            changesLast.nextIteration(changesCurrent);
        }

        return isPuzzleSolved(puzzle);
    }

    private void markRowsAsSolved(Puzzle puzzle) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            if (rowOrCol.solved) continue;
            if (rowOrCol.numbersToFind.stream().filter(n -> !n.found).count() == 0) {
                rowOrCol.solved = true;
            }
        }
    }

    private boolean isPuzzleSolved(Puzzle puzzle) {
        return puzzle.rowsOrCols.stream().allMatch(rowOrCol -> rowOrCol.solved);
    }

}

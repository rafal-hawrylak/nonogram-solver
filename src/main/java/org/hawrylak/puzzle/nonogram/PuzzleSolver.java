package org.hawrylak.puzzle.nonogram;

public class PuzzleSolver {

    private final GapFinder gapFinder = new GapFinder();
    private final GapCloser gapCloser = new GapCloser(gapFinder);

    private final GapFiller gapFiller = new GapFiller(gapFinder, gapCloser);

    private final NumberCloser numberCloser = new NumberCloser();

    boolean solve(Puzzle puzzle) {

        var changesLast = new ChangedInIteration(puzzle);
        var changesCurrent = new ChangedInIteration(puzzle);
        while (changesLast.firstIteration() || changesLast.anyChange()) {
            System.out.println("iteration = " + changesLast.iteration);

            // rules
            markRowsAsSolved(puzzle);
            gapCloser.closeNotNeeded(puzzle, changesLast, changesCurrent);
            gapFiller.fillTheOnlyMatchingGaps(puzzle, changesLast, changesCurrent);
            numberCloser.close(puzzle, changesLast, changesCurrent);

            System.out.println(puzzle.toString(changesCurrent));
            changesLast.nextIteration(changesCurrent);
        }

        return isPuzzleSolved(puzzle);
    }

    private void markRowsAsSolved(Puzzle puzzle) {
        for (RowOrCol rowsOrCol : puzzle.rowsOrCols) {
            if (rowsOrCol.solved) continue;
            if (rowsOrCol.numbersToFind.stream().filter(n -> !n.found).count() == 0) {
                rowsOrCol.solved = true;
            }
        }
    }

    private boolean isPuzzleSolved(Puzzle puzzle) {
        return puzzle.rowsOrCols.stream().allMatch(rowOrCol -> rowOrCol.solved);
    }

}

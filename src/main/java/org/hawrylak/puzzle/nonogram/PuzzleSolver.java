package org.hawrylak.puzzle.nonogram;

public class PuzzleSolver {

    private final GapFinder gapFinder = new GapFinder();
    private final GapCloser gapCloser = new GapCloser(gapFinder);

    private final GapFiller gapFiller = new GapFiller(gapFinder, gapCloser);

    private final NumberCloser numberCloser = new NumberCloser();

    boolean solve(Puzzle puzzle) {

        var changes = new ChangedInIteration(puzzle);
        while (changes.firstIteration() || changes.anyChange()) {
            System.out.println("iteration = " + changes.iteration);
            changes.clear();

            // rules
            gapCloser.closeNotNeeded(puzzle, changes);
            gapFiller.fillTheOnlyMatchingGaps(puzzle, changes);
            numberCloser.close(puzzle, changes);

            System.out.println(puzzle.toString(changes));
            changes.nextIteration();
        }

        return isPuzzleSolved(puzzle);
    }

    private boolean isPuzzleSolved(Puzzle puzzle) {
        return puzzle.rowsOrCols.stream().allMatch(rowOrCol -> rowOrCol.solved);
    }

}

package org.hawrylak.puzzle.nonogram;

import java.util.List;

public class PuzzleSolver {

    private final GapFinder gapFinder = new GapFinder();
    private final GapCloser gapCloser = new GapCloser(gapFinder);

    boolean solve(Puzzle puzzle) {

        var changes = new ChangedInLastIteration(puzzle.width, puzzle.height);
        while (changes.firstIteration() || changes.anyChange()) {
            System.out.println("iteration = " + changes.iteration);
            changes.clear();

            // rules
            gapCloser.closeNotNeeded(puzzle, changes);
            fillTheOnlyMatchingGap(puzzle, changes);

            System.out.println(puzzle.toString(changes));
            changes.nextIteration();
        }

        return isPuzzleSolved(puzzle);
    }

    private void fillTheOnlyMatchingGap(Puzzle puzzle,
        ChangedInLastIteration changedInLastIteration) {
        boolean anyChange = false;
        int i = -1;
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            i++;
            if (!rowOrCol.solved) {
                List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
                for (NumberToFind number : rowOrCol.numbersToFind) {
                    if (!number.found) {
                        // TODO implement
                    }
                }
            }
        }

        // TODO use changedInLastIteration
    }

    private boolean isPuzzleSolved(Puzzle puzzle) {
        return puzzle.rowsOrCols.stream().allMatch(rowOrCol -> rowOrCol.solved);
    }

}

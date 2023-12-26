package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFiller;

/*
  ex
    .  .  ■  .  .  ■  ■  .  .  ■  ■  ■  ■  ■  .| 5 5
    to
    .  .  ■  ■  ■  ■  ■  .  .  ■  ■  ■  ■  ■  .| 5 5
    AND

    .  .  ■  ■  ■  ■  ■  .  .  ■  ■  .  .  ■  .| 5 5
    to
    .  .  ■  ■  ■  ■  ■  .  .  ■  ■  ■  ■  ■  .| 5 5

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! BUG:
     .  .  ■  ■  ■  ■  ■  .  ■  .  ■  .  .  .  .| 5 3 3
     will produce
     .  .  ■  ■  ■  ■  ■  .  ■  ■  ■  .  .  .  .| 5 3 3[M]
     but can be
     .  .  ■  ■  ■  ■  ■  .  ■  ■  ■  .  ■  ■  ■| 5 3 3[M]
 */
@AllArgsConstructor
public class FillTheNumbersWithStartAndEndNotConnected extends Solver {

    private GapFinder gapFinder;
    private NumberSelector numberSelector;
    private GapFiller gapFiller;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var firstNotFound = numberSelector.getFirstNotFound(rowOrCol.numbersToFind);
            if (firstNotFound.isPresent()) {
                var gap = gapFinder.findFirstWithoutNumberAssigned(puzzle, rowOrCol).get();
                gapFiller.mergeSubGaps(puzzle, changes, rowOrCol, gap, firstNotFound.get(), true);
            }
            var lastNotFound = numberSelector.getLastNotFound(rowOrCol.numbersToFind);
            if (lastNotFound.isPresent()) {
                var gap = gapFinder.findLastWithoutNumberAssigned(puzzle, rowOrCol).get();
                gapFiller.mergeSubGaps(puzzle, changes, rowOrCol, gap, lastNotFound.get(), false);
            }
        }
    }
}

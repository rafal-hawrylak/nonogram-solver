package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapCloser;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;

/*
  ex
    .  x  .  x  ■  ■  ■  x  .  . | 3 1
    to
    x  x  x  x  ■  ■  ■  x  .  . | 3 1

  and

    .  .  x  ■  ■  ■  x  .  x  . | 1 3
    to
    .  .  x  ■  ■  ■  x  x  x  x | 1 3
 */
@AllArgsConstructor
public class CloseAllGapsBeforeFirstAndAfterLastFoundNumber extends Solver {

    private GapFinder gapFinder;
    private GapCloser gapCloser;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            if (rowOrCol.numbersToFind.isEmpty()) {
                continue;
            }
            var gaps = gapFinder.find(puzzle, rowOrCol);
             var firstNumber = rowOrCol.numbersToFind.getFirst();
            if (firstNumber.found) {
                var gapsBeforeNumber = gapFinder.gapsBeforeNumber(gaps, firstNumber);
                for (Gap gap : gapsBeforeNumber) {
                    gapCloser.closeAsEmpty(gap, puzzle, changes);
                }
            }
            var lastNumber = rowOrCol.numbersToFind.getLast();
            if (lastNumber.found) {
                var gapsAfterNumber = gapFinder.gapsAfterNumber(gaps, lastNumber);
                for (Gap gap : gapsAfterNumber) {
                    gapCloser.closeAsEmpty(gap, puzzle, changes);
                }
            }
        }
    }
}

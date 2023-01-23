package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.utils.GapCloser;

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
public class CloseAllGapsBeforeFirstAndAfterLastFoundNumber implements Solver {

    private GapFinder gapFinder;
    private GapCloser gapCloser;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            if (rowOrCol.numbersToFind.isEmpty()) {
                continue;
            }
            var gaps = gapFinder.find(puzzle, rowOrCol);
            var firstNumber = rowOrCol.numbersToFind.get(0);
            if (firstNumber.found) {
                var gapsBeforeNumber = gapFinder.gapsBeforeNumber(gaps, firstNumber);
                for (Gap gap : gapsBeforeNumber) {
                    gapCloser.closeAsEmpty(gap, puzzle, changes);
                }
            }
            var lastNumber = rowOrCol.numbersToFind.get(rowOrCol.numbersToFind.size() - 1);
            if (lastNumber.found) {
                var gapsAfterNumber = gapFinder.gapsAfterNumber(gaps, lastNumber);
                for (Gap gap : gapsAfterNumber) {
                    gapCloser.closeAsEmpty(gap, puzzle, changes);
                }
            }
        }
    }
}
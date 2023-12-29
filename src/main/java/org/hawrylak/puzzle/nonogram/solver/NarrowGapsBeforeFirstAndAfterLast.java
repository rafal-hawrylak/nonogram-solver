package org.hawrylak.puzzle.nonogram.solver;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapCloser;

/*
ex
x  x  .  .  ■  ■  ■  .  .  .  .  .  .  .  . | 4 1 1
to
x  x  x  .  ■  ■  ■  .  .  .  .  .  .  .  . | 4 1 1

and

x  x  .  .  .  .  .  .  .  ■  ■  ■  .  .  . | 1 1 4
to
x  x  .  .  .  .  .  .  .  ■  ■  ■  .  x  x | 1 1 4
*/
@AllArgsConstructor
public class NarrowGapsBeforeFirstAndAfterLast extends Solver {

    private GapFinder gapFinder;
    private GapCloser gapCloser;
    private NumberSelector numberSelector;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var firstNotFound = numberSelector.getFirstNotFound(rowOrCol.numbersToFind);
            if (firstNotFound.isPresent()) {
                var number = firstNotFound.get();
                var gap = gapFinder.findFirstWithoutNumberAssigned(puzzle, rowOrCol).get();
                if (!gap.filledSubGaps.isEmpty()) {
                    var firstSubGap = gap.filledSubGaps.get(0);
                    var missingNumberPart = number.number - firstSubGap.length;
                    if (missingNumberPart >= 0) {
                        if (firstSubGap.start - gap.start > missingNumberPart) {
                            var nextNumber = numberSelector.getNext(rowOrCol.numbersToFind, number);
                            if (nextNumber.isEmpty() || nextNumber.get().found || (firstSubGap.start - gap.start <= number.number
                                && firstSubGap.end - gap.start >= number.number)) {
                                var fakeGap = new Gap(rowOrCol, gap.start, firstSubGap.start - missingNumberPart - 1,
                                    firstSubGap.start - missingNumberPart - gap.start);
                                gapCloser.closeAsEmpty(fakeGap, puzzle, changes);
                            }
                        }
                    } else {
                        // firstSubGap is not for firstNotFound but for some next
                    }
                }
            }
            /*
                missingNumberPart = 3
                number = 9
                     0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19
                 6|  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  .  .  .  .  .  .  .| 2 2 3 9  g.e = 19  lSG.e = 9
                 6|  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  .  .  .  .  .  .  .| 2 2 3 9  g.e - lSG.e = 10  ### NOT OK

                 6|  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  .  .  .  .  .  .| 2 2 3 9  g.e = 19  lSG.e = 10
                 6|  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  x  x  x  x  x  x| 2 2 3 9  g.e - lSG.e = 9  ### OK

                 6|  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  .  .  .  .  .| 2 2 3 9  g.e = 19  lSG.e = 11
                 6|  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  x  x  x  x  x| 2 2 3 9  g.e - lSG.e = 8  ### OK

                 6|  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  .  .  .  .| 2 2 3 9
                 6|  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  x  x  x  x| 2 2 3 9

                 6|  .  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  .  .  .| 2 2 3 9
                 6|  .  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  x  x  x| 2 2 3 9

                 6|  .  .  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  .  .| 2 2 3 9
                 6|  .  .  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  x  x| 2 2 3 9

                 6|  .  .  .  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  .| 2 2 3 9  g.e = 19  lSG.s = 10
                 6|  .  .  .  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  x| 2 2 3 9  g.e - lSG.s = 9  ### OK

                 6|  .  .  .  .  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .| 2 2 3 9  g.e = 19  lSG.s = 11
                 6|  .  .  .  .  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .| 2 2 3 9  g.e - lSG.s = 8  ### NOT OK
             */
            var lastNotFound = numberSelector.getLastNotFound(rowOrCol.numbersToFind);
            if (lastNotFound.isPresent()) {
                var number = lastNotFound.get();
                var gap = gapFinder.findLastWithoutNumberAssigned(puzzle, rowOrCol).get();
                if (!gap.filledSubGaps.isEmpty()) {
                    var lastSubGap = gap.filledSubGaps.get(gap.filledSubGaps.size() - 1);
                    var missingNumberPart = number.number - lastSubGap.length;
                    if (missingNumberPart >= 0) {
                        if (gap.end - lastSubGap.end > missingNumberPart) {
                            var prevNumber = numberSelector.getPrevious(rowOrCol.numbersToFind, number);
                            if (prevNumber.isEmpty() || prevNumber.get().found || (gap.end - lastSubGap.end <= number.number
                                && gap.end - lastSubGap.start >= number.number)) {
                                var fakeGap = new Gap(rowOrCol, lastSubGap.end + missingNumberPart + 1, gap.end,
                                    gap.end - lastSubGap.end - missingNumberPart);
                                gapCloser.closeAsEmpty(fakeGap, puzzle, changes);
                            }
                        }
                    } else {
                        // lastSubGap is not for lastNotFound but for some next
                    }
                }
            }
        }
    }
}

package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapCloser;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;

@AllArgsConstructor
public class FindUnmergableSubGapsForBiggest extends Solver {

    private GapFinder gapFinder;
    private GapCloser gapCloser;
    private NumberSelector numberSelector;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var biggestNotFound = numberSelector.getBiggestNotFound(rowOrCol.numbersToFind);
            if (biggestNotFound.isEmpty()) {
                continue;
            }
            var biggest = biggestNotFound.get(0).number;
            var gaps = gapFinder.find(puzzle, rowOrCol);
            for (Gap gap : gaps) {
                for (int i = 0; i < gap.filledSubGaps.size() - 1; i++) {
                    var subGap = gap.filledSubGaps.get(i);
                    var nextSubGap = gap.filledSubGaps.get(i + 1);
                    boolean mergeable = gapFinder.areSubGapsMergeable(biggest, subGap, nextSubGap);
                    var onlySingleFieldBetweenSubGaps = subGap.end + 2 == nextSubGap.start;
                    if (!mergeable && onlySingleFieldBetweenSubGaps) {
                        var fakeGap = new Gap(rowOrCol, subGap.end + 1, subGap.end + 1, 1);
                        gapCloser.closeAsEmpty(fakeGap, puzzle, changes);
                    }
                }
            }
        }
    }
}

package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapCloser;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.solver.utils.Utils;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;

@AllArgsConstructor
public class FindUnmergableSubGapsForBiggestForFirstAndLastNotFound extends Solver {

    private GapFinder gapFinder;
    private GapCloser gapCloser;
    private NumberSelector numberSelector;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var gaps = gapFinder.find(puzzle, rowOrCol);
            var firstGap = gapFinder.findFirstWithoutNumberAssigned(puzzle, rowOrCol);
            var firstNumber = numberSelector.getFirstNotFound(rowOrCol.numbersToFind);
            if (firstGap.isPresent() && firstNumber.isPresent()) {
                if (firstGap.get().filledSubGaps.size() >= 2) {
                    var firstSubGap = firstGap.get().filledSubGaps.get(0);
                    var nextSubGap = firstGap.get().filledSubGaps.get(1);
                    var missingNumberPart = firstNumber.get().number - firstSubGap.length;
                    if (missingNumberPart >= 0) {
                        if (firstSubGap.start - firstGap.get().start >= missingNumberPart &&
                            firstSubGap.start - firstGap.get().start <= firstNumber.get().number) {
                            boolean mergeable = gapFinder.areSubGapsMergeable(gaps, firstNumber.get().number, firstSubGap, nextSubGap);
                            var onlySingleFieldBetweenSubGaps = firstSubGap.end + 2 == nextSubGap.start;
                            if (!mergeable && onlySingleFieldBetweenSubGaps) {
                                var fakeGap = new Gap(rowOrCol, firstSubGap.end + 1, firstSubGap.end + 1, 1);
                                gapCloser.closeAsEmpty(fakeGap, puzzle, changes);
                            }
                        }
                    }

                }
            }
            var lastGap = gapFinder.findLastWithoutNumberAssigned(puzzle, rowOrCol);
            var lastNumber = numberSelector.getLastNotFound(rowOrCol.numbersToFind);
            if (lastGap.isPresent() && lastNumber.isPresent()) {
                if (lastGap.get().filledSubGaps.size() >= 2) {
                    var lastSubGap = lastGap.get().filledSubGaps.getLast();
                    var prevSubGap = Utils.previous(lastGap.get().filledSubGaps, lastSubGap).get();
                    var missingNumberPart = lastNumber.get().number - lastSubGap.length;
                    if (missingNumberPart >= 0) {
                        if (lastGap.get().end - lastSubGap.end >= missingNumberPart &&
                            lastGap.get().end - lastSubGap.end <= lastNumber.get().number) {
                            boolean mergeable = gapFinder.areSubGapsMergeable(gaps, lastNumber.get().number, prevSubGap, lastSubGap);
                            var onlySingleFieldBetweenSubGaps = prevSubGap.end + 2 == lastSubGap.start;
                            if (!mergeable && onlySingleFieldBetweenSubGaps) {
                                var fakeGap = new Gap(rowOrCol, prevSubGap.end + 1, prevSubGap.end + 1, 1);
                                gapCloser.closeAsEmpty(fakeGap, puzzle, changes);
                            }
                        }
                    }
                }
            }
        }
    }
}

package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFiller;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;

@AllArgsConstructor
public class ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber extends Solver {

    private GapFinder gapFinder;
    private NumberSelector numberSelector;
    private GapFiller gapFiller;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var firstGap = gapFinder.findFirstWithoutNumberAssigned(puzzle, rowOrCol);
            if (firstGap.isEmpty()) {
                continue;
            }
            var firstNumber = numberSelector.getFirstNotFound(rowOrCol.numbersToFind);
            if (firstNumber.isEmpty()) {
                continue;
            }
            var gap = firstGap.get();
            var number = firstNumber.get();
            if (!gap.filledSubGaps.isEmpty()) {
                var firstSubGap = gap.filledSubGaps.get(0);
                var missingNumberPart = number.number - firstSubGap.length;
                if (missingNumberPart >= 0) {
                    if (gap.start == firstSubGap.start && missingNumberPart == 0) {
                        gapFiller.fillTheGapEntirely(firstSubGap, number, rowOrCol, puzzle, changes);
                    } else if (firstSubGap.start - gap.start < missingNumberPart) {
                        var end = gap.start + number.number - 1;
                        var fakeGap = new Gap(rowOrCol, firstSubGap.start, end, end - firstSubGap.start + 1);
                        gapFiller.fillTheGap(fakeGap, rowOrCol, puzzle, changes);
                    }
                }
            }

            var lastGap = gapFinder.findLastWithoutNumberAssigned(puzzle, rowOrCol);
            if (lastGap.isEmpty()) {
                continue;
            }
            var lastNumber = numberSelector.getLastNotFound(rowOrCol.numbersToFind);
            if (lastNumber.isEmpty()) {
                continue;
            }
            gap = lastGap.get();
            number = lastNumber.get();
            if (!gap.filledSubGaps.isEmpty()) {
                var lastSubGap = gap.filledSubGaps.getLast();
                var missingNumberPart = number.number - lastSubGap.length;
                if (missingNumberPart >= 0) {
                    if (gap.end == lastSubGap.end && missingNumberPart == 0) {
                        gapFiller.fillTheGapEntirely(lastSubGap, number, rowOrCol, puzzle, changes);
                    } else if (gap.end - lastSubGap.end < missingNumberPart) {
                        var start = gap.end - number.number + 1;
                        var fakeGap = new Gap(rowOrCol, start, lastSubGap.end, lastSubGap.end - start + 1);
                        gapFiller.fillTheGap(fakeGap, rowOrCol, puzzle, changes);
                    }
                }
            }
        }
    }
}

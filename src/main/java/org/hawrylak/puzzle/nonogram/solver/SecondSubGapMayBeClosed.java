package org.hawrylak.puzzle.nonogram.solver;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.utils.Utils;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.utils.GapFiller;

@AllArgsConstructor
public class SecondSubGapMayBeClosed extends Solver {

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
            var nextNumber = numberSelector.getNext(rowOrCol.numbersToFind, number);
            if (gap.filledSubGaps.size() >= 2 && nextNumber.isPresent()) {
                var firstSubGap = Utils.getFirst(gap.filledSubGaps).get();
                var secondSubGap = Utils.next(gap.filledSubGaps, firstSubGap).get();
                var missingNumberPart = number.number - firstSubGap.length;
                if (missingNumberPart >= 0 && firstSubGap.start - gap.start <= missingNumberPart) {
                    if (!gapFinder.areSubGapsMergeable(number.number, firstSubGap, secondSubGap)) {
                        if (!gapFinder.numberFitsBetweenSubGaps(nextNumber.get().number, firstSubGap, secondSubGap)) {
                            if (nextNumber.get().number == secondSubGap.length) {
                                var fakeGap = new Gap(rowOrCol, secondSubGap.start, secondSubGap.end, secondSubGap.length, Optional.of(nextNumber.get()));
                                gapFiller.fillTheGapEntirely(fakeGap, nextNumber.get(), rowOrCol, puzzle, changes);
                            }
                        }
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
            var previousNumber = numberSelector.getPrevious(rowOrCol.numbersToFind, number);
            if (gap.filledSubGaps.size() >= 2 && previousNumber.isPresent()) {
                var lastSubGap = Utils.getLast(gap.filledSubGaps).get();
                var lastButOneSubGap = Utils.previous(gap.filledSubGaps, lastSubGap).get();
                var missingNumberPart = number.number - lastSubGap.length;
                if (missingNumberPart >= 0 && gap.end - lastSubGap.end <= missingNumberPart) {
                    if (!gapFinder.areSubGapsMergeable(number.number, lastButOneSubGap, lastSubGap)) {
                        if (!gapFinder.numberFitsBetweenSubGaps(previousNumber.get().number, lastButOneSubGap, lastSubGap)) {
                            if (previousNumber.get().number == lastButOneSubGap.length) {
                                var fakeGap = new Gap(rowOrCol, lastButOneSubGap.start, lastButOneSubGap.end, lastButOneSubGap.length, Optional.of(previousNumber.get()));
                                gapFiller.fillTheGapEntirely(fakeGap, previousNumber.get(), rowOrCol, puzzle, changes);
                            }
                        }
                    }
                }
            }
        }
    }
}

package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.model.SubGap;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFiller;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MergeSubGapForBiggestIfPreviousDoesNotMatch extends Solver {

    private GapFinder gapFinder;
    private GapFiller gapFiller;
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
            if (gaps.size() > 1) {
                continue;
            }
            List<SubGap> subGaps = gaps.get(0).filledSubGaps;
            if (subGaps.size() < 3) {
                continue;
            }
            var allBeforeBiggest = numberSelector.allPrevious(rowOrCol.numbersToFind, biggestNotFound.get(0));
            var allAfterBiggest = numberSelector.allNext(rowOrCol.numbersToFind, biggestNotFound.get(0));
            if (allBeforeBiggest.isEmpty()) {

            } else if (allBeforeBiggest.size() == 1) {
                var maxSubGap = subGaps.stream().max(Comparator.comparingInt(s -> s.length)).get();
                if (allAfterBiggest.stream().filter(n -> !n.found).anyMatch(n -> n.number >= maxSubGap.length)) {
                    continue;
                }
                var previousNumber = allBeforeBiggest.get(0);
                var maxSubGapIndex = subGaps.indexOf(maxSubGap);
                if (maxSubGapIndex != 2) {
                    continue;
                }
                var firstSubGap = subGaps.get(0);
                var nextSubGap = subGaps.get(1);
                boolean mergeableWithFirstNumber = gapFinder.areSubGapsMergeable(previousNumber.number, firstSubGap, nextSubGap);
                boolean mergeableWithSecondNumber = gapFinder.areSubGapsMergeable(biggest, nextSubGap, maxSubGap);
                if (!mergeableWithFirstNumber && mergeableWithSecondNumber) {
                    var fakeGap = new Gap(rowOrCol, nextSubGap.end + 1, maxSubGap.start - 1, maxSubGap.start - nextSubGap.end + 1, Optional.empty());
                    gapFiller.fillTheGap(fakeGap, rowOrCol, puzzle, changes);
                }
            }
        }
    }
}

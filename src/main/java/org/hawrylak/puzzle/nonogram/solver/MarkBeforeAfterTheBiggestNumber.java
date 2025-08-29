package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.*;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFiller;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;

import java.util.List;

import static org.hawrylak.puzzle.nonogram.model.Gap.toSubGaps;

@AllArgsConstructor
public class MarkBeforeAfterTheBiggestNumber extends Solver {

    // only before implemented so far

    private GapFinder gapFinder;
    private NumberSelector numberSelector;
    private GapFiller gapFiller;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var gaps = gapFinder.findWithoutAssignedNumber(puzzle, rowOrCol);
            var subGaps = gapFinder.allSubGaps(gaps);
            var allNumbers = rowOrCol.numbersToFind;
            var numbersToFind = numberSelector.onlyNotFound(rowOrCol.numbersToFind);
            var biggest = numberSelector.getBiggest(numbersToFind);
            if (gaps.size() >= 2) {
                var firstBiggest = biggest.getFirst();
                var numbersBeforeBiggest = numberSelector.allPrevious(allNumbers, firstBiggest);
                if (!numbersBeforeBiggest.isEmpty()) {
                    var lastBeforeBiggest = numbersBeforeBiggest.getLast();
                    if (!lastBeforeBiggest.found) {
                        var biggestSubGaps = gapFinder.biggestSubGap(subGaps);
                        if (biggestSubGaps.size() == 1) {
                            var biggestSubGap = biggestSubGaps.getFirst();
                            var numbersMatchingBiggestSubGap = numberSelector.findNumbersMatchingSubGap(biggestSubGap, numbersToFind);
                            if (numbersMatchingBiggestSubGap.size() == 1 || onlyFirstBiggestNumberIsMatchingIntoGaps(numbersMatchingBiggestSubGap, biggestSubGap, numbersToFind, gaps)) {
                                if (firstBiggest.equals(numbersMatchingBiggestSubGap.getFirst())) {
                                    var subGapBeforeBiggest = gapFinder.previous(subGaps, biggestSubGap);
                                    if (subGapBeforeBiggest.isPresent()) {
                                        if (onlyFirstNumberBeforeBiggestNumberIsMatchingIntoGaps(lastBeforeBiggest, numbersMatchingBiggestSubGap.getFirst(), subGapBeforeBiggest.get(), biggestSubGap, numbersToFind, gaps)) {
                                            if (!gapFinder.areSubGapsMergeable(firstBiggest.number, subGapBeforeBiggest.get(), biggestSubGap)) {
                                                if (lastBeforeBiggest.number == subGapBeforeBiggest.get().length) {
                                                    gapFiller.fillTheGapEntirely(subGapBeforeBiggest.get(), lastBeforeBiggest, rowOrCol, puzzle, changes);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean onlyFirstNumberBeforeBiggestNumberIsMatchingIntoGaps(NumberToFind lastBeforeBiggest, NumberToFind firstBiggestNumber, SubGap beforeBiggestSubGap, SubGap biggestSubGap, List<NumberToFind> numbers, List<Gap> gaps) {
        if (firstBiggestNumber.number < biggestSubGap.length) {
            return false;
        }
        var gapsStartingBeforeFirstSubGap = gapFinder.gapsStartingBeforeSubGapCut(beforeBiggestSubGap, gaps);
        var gapsToCheck = gapFinder.allPreviousAndThis(gapsStartingBeforeFirstSubGap, gapFinder.getGapOfSubGap(gaps, biggestSubGap));
        var gapToShorten = gapsToCheck.removeLast();
        int shortenedLength = firstBiggestNumber.number;
        int shortenedEnd = Math.min(biggestSubGap.start + shortenedLength - 1, gapToShorten.end);
        var gapShortened = new Gap(gapToShorten.rowOrCol, gapToShorten.start, shortenedEnd, shortenedEnd - gapToShorten.start + 1, gapToShorten.assignedNumber, gapToShorten.filledSubGaps);
        gapsToCheck.add(gapShortened);
        // take into account filled fields
        var fitsWithTheNumberBefore = gapFiller.doAllNumbersFitInGaps(List.of(lastBeforeBiggest, firstBiggestNumber), toSubGaps(gapsToCheck));
        if (!fitsWithTheNumberBefore) {
            return false;
        }
        var numberBeforeLastBeforeBiggest = numberSelector.getPrevious(numbers, lastBeforeBiggest);
        if (numberBeforeLastBeforeBiggest.isEmpty()) {
            return true;
        }
        // take into account filled fields
        var fitsWithTheNumberBeforeBefore = gapFiller.doAllNumbersFitInGaps(List.of(numberSelector.artificial(1), lastBeforeBiggest, firstBiggestNumber), toSubGaps(gapsToCheck));
        return !fitsWithTheNumberBeforeBefore;
    }

    private boolean onlyFirstBiggestNumberIsMatchingIntoGaps(List<NumberToFind> biggestNumbers, SubGap subGap, List<NumberToFind> numbers, List<Gap> gaps) {
        var firstBiggestNumber = biggestNumbers.getFirst();
        var numberToFitBeforeFirstBiggest = numberSelector.allPreviousAndThis(numbers, firstBiggestNumber);
        var gapsEndingAfterSubGap = gapFinder.gapsEndingAfterSubGapCut(subGap, gaps);
        var fitsWithTheFirstNumber = gapFiller.doAllNumbersFitInGaps(numberToFitBeforeFirstBiggest, toSubGaps(gapsEndingAfterSubGap));
        if (!fitsWithTheFirstNumber) {
            return false;
        }
        var secondBiggestNumber = biggestNumbers.get(1);
        var numberToFitBeforeSecondBiggest = numberSelector.allPreviousAndThis(numbers, secondBiggestNumber);
        var fitsWithTheSecondNumber = gapFiller.doAllNumbersFitInGaps(numberToFitBeforeSecondBiggest, toSubGaps(gapsEndingAfterSubGap));
        return !fitsWithTheSecondNumber;
    }

}

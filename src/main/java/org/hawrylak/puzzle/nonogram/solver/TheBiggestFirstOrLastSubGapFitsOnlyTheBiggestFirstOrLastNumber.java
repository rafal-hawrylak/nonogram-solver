package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.*;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFiller;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.solver.utils.Utils;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/*
  ex
. . . . . . . . . . . . . . . . . . . . ■ ■ ■ . . . . . . . . . . . . | 3 1 11

 to
. . . . . . . . . . . . . . . . . . . . ■ ■ ■ . . . . . . . . x x x x | 3 1 11
 */
@AllArgsConstructor
public class TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber extends Solver {

    private GapFinder gapFinder;
    private NumberSelector numberSelector;
    private GapFiller gapFiller;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var gapsWithoutAssignedNumber = gapFinder.findWithoutAssignedNumber(puzzle, rowOrCol);
            var biggestNumberNotFound = numberSelector.getBiggestNotFound(rowOrCol.numbersToFind);
            var biggestSubGap = gapsWithoutAssignedNumber.stream().flatMap(g -> g.filledSubGaps.stream())
                .max(Comparator.comparingInt(s -> s.length));
            if (biggestSubGap.isPresent()) {
                var firstGap = gapsWithoutAssignedNumber.get(0);
                var lastGap = Utils.getLast(gapsWithoutAssignedNumber).get();
                var firstSubGapOptional = Utils.getFirst(firstGap.filledSubGaps);
                var lastSubGapOptional = Utils.getLast(lastGap.filledSubGaps);
                if (firstSubGapOptional.isPresent() && biggestSubGap.get().equals(firstSubGapOptional.get())
                || lastSubGapOptional.isPresent() && biggestSubGap.get().equals(lastSubGapOptional.get())) {
                    var allGaps = gapFinder.find(puzzle, rowOrCol);
                    var allNumbersNotFound = numberSelector.getNotFound(rowOrCol.numbersToFind);
                    var firstNumberNotFound = Utils.getFirst(allNumbersNotFound).get();
                    var lastNumberNotFound = Utils.getLast(allNumbersNotFound).get();
                    int biggestValue = biggestNumberNotFound.get(0).number;
                    if (firstNumberNotFound.number == biggestValue && isTheFirstNumberTheOnlyMatch(firstGap, biggestSubGap.get(),
                        allNumbersNotFound, firstNumberNotFound)) {
                        var end = biggestSubGap.get().end - biggestValue;
                        if (firstGap.start <= end && areInTheSameGap(firstGap.start, end, allGaps)) {
                            var fakeGap = new Gap(rowOrCol, firstGap.start, end, end - firstGap.start + 1);
                            gapFiller.fillTheGap(fakeGap, FieldState.EMPTY, rowOrCol, puzzle, changes);
                        }
                    }
                    if (lastNumberNotFound.number == biggestValue && isTheLastNumberTheOnlyMatch(lastGap, biggestSubGap.get(),
                        allNumbersNotFound, lastNumberNotFound)) {
                        var start = biggestSubGap.get().start + biggestValue;
                        if (start <= lastGap.end && areInTheSameGap(start, lastGap.end, allGaps)) {
                            var fakeGap = new Gap(rowOrCol, start, lastGap.end, lastGap.end - start + 1);
                            gapFiller.fillTheGap(fakeGap, FieldState.EMPTY, rowOrCol, puzzle, changes);
                        }
                    }
                }
            }
        }
    }

    private boolean isTheFirstNumberTheOnlyMatch(Gap firstGap, SubGap biggestSubGap, List<NumberToFind> allNumbersNotFound,
        NumberToFind firstNumberNotFound) {
        var firstNumberMatchingTheSize = findFirstNumberMatchingTheSize(numberSelector.allNext(allNumbersNotFound, firstNumberNotFound), biggestSubGap.length);
        if (firstNumberMatchingTheSize.isEmpty()) {
            return true;
        }
        var size = sumFieldsUpToNumber(allNumbersNotFound, firstNumberMatchingTheSize.get());
        var allowedSize = biggestSubGap.start - firstGap.start;
        return size > allowedSize;
    }

    private boolean isTheLastNumberTheOnlyMatch(Gap lastGap, SubGap biggestSubGap, List<NumberToFind> allNumbersNotFound,
        NumberToFind lastNumberNotFound) {
        var lastNumberMatchingTheSize = findFirstNumberMatchingTheSize(Utils.reverse(numberSelector.allPrevious(allNumbersNotFound, lastNumberNotFound)), biggestSubGap.length);
        if (lastNumberMatchingTheSize.isEmpty()) {
            return true;
        }
        var size = sumFieldsUpToNumber(Utils.reverse(allNumbersNotFound), lastNumberMatchingTheSize.get());
        var allowedSize = lastGap.end - biggestSubGap.end;
        return size > allowedSize;
    }

    private Optional<NumberToFind> findFirstNumberMatchingTheSize(List<NumberToFind> numbers, int length) {
        return numbers.stream().filter(n -> n.number >= length).findFirst();
    }

    private int sumFieldsUpToNumber(List<NumberToFind> numbers, NumberToFind lastNumber) {
        var sum = 0;
        var numbersSummed = 0;
        for (var number : numbers) {
            if (number.equals(lastNumber)) {
                break;
            }
            numbersSummed++;
            sum += number.number;
        }
        return sum + numbersSummed - 1;
    }

    private boolean areInTheSameGap(int a, int b, List<Gap> gaps) {
        var gapAtPositionA = gapFinder.getPossibleGapAtPosition(gaps, a, a);
        var gapAtPositionB = gapFinder.getPossibleGapAtPosition(gaps, b, b);
        if (gapAtPositionA.isEmpty() || gapAtPositionB.isEmpty()) {
            return false;
        }
        return gapAtPositionA.get().equals(gapAtPositionB.get());
    }
}

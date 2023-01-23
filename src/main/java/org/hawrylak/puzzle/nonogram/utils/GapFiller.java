package org.hawrylak.puzzle.nonogram.utils;

import static org.hawrylak.puzzle.nonogram.utils.Utils.getStart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.utils.NumberSelector.NumberBeforeCurrentAndAfter;
import org.hawrylak.puzzle.nonogram.model.FieldState;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.NumberToFind;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.model.SubGap;

@AllArgsConstructor
public class GapFiller {

    private final FieldFinder fieldFinder;
    private final NumberSelector numberSelector;
    private final GapFinder gapFinder;

    public void fillTheGapEntirely(Gap gap, NumberToFind number, RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes) {
        fillTheGap(gap, rowOrCol, puzzle, changes);
        number.found = true;
        number.foundStart = gap.start;
        number.foundEnd = gap.end;
        fillSingleField(rowOrCol, puzzle, changes, gap.start - 1, FieldState.EMPTY);
        fillSingleField(rowOrCol, puzzle, changes, gap.end + 1, FieldState.EMPTY);
    }

    public void fillTheGapEntirelyWithNumbers(Puzzle puzzle, ChangedInIteration changes, RowOrCol rowOrCol,
        List<NumberToFind> numbersToClose, int start) {
        for (NumberToFind numberToClose : numbersToClose) {
            var length = numberToClose.number;
            var fakeGap = new Gap(rowOrCol, start, start + length - 1, length, Optional.of(numberToClose));
            fillTheGapEntirely(fakeGap, numberToClose, rowOrCol, puzzle, changes);
            rowOrCol.solved = true;
            start += length + 1;
        }
    }

    public void fillTheNumberAtPosition(RowOrCol rowOrCol, NumberToFind numberToClose, int c, int r, boolean startingFrom, Puzzle puzzle,
        ChangedInIteration changes) {
        int start = getStart(rowOrCol, numberToClose.number, c, r, startingFrom);
        var fakeGap = new Gap(rowOrCol, start, start + numberToClose.number - 1, numberToClose.number, Optional.empty());
        fillTheGapEntirely(fakeGap, numberToClose, rowOrCol, puzzle, changes);
    }

    public boolean fillSingleField(RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes, int i, FieldState state) {
        if (rowOrCol.horizontal) {
            if (i >= 0 && i < puzzle.width) {
                if (!state.equals(puzzle.fields[i][rowOrCol.number])) {
                    puzzle.fields[i][rowOrCol.number] = state;
                    changes.markChangeSingle(i, rowOrCol.number);
                    return true;
                }
            }
        } else {
            if (i >= 0 && i < puzzle.height) {
                if (!state.equals(puzzle.fields[rowOrCol.number][i])) {
                    puzzle.fields[rowOrCol.number][i] = state;
                    changes.markChangeSingle(rowOrCol.number, i);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean fillTheGap(Gap gap, RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes) {
        var anyFilled = false;
        for (int i = gap.start; i <= gap.end; i++) {
            var filled = fillSingleField(rowOrCol, puzzle, changes, i, FieldState.FULL);
            anyFilled = anyFilled || filled;
        }
        return anyFilled;
    }

    public void fillTheGapPartiallyForSingleNumber(Gap gap, NumberToFind number, RowOrCol rowOrCol, Puzzle puzzle,
        ChangedInIteration changes) {
        if (2 * number.number > gap.length) {
            var howManyFieldsMayBeSet = 2 * number.number - gap.length;
            var start = gap.start + (gap.length - number.number);
            var end = start + howManyFieldsMayBeSet - 1;
            var fakeGap = new Gap(rowOrCol, start, end, howManyFieldsMayBeSet, Optional.empty());
            fillTheGap(fakeGap, rowOrCol, puzzle, changes);
        }
        List<Integer> fullFieldsInGap = fieldFinder.findFieldsSetInGap(gap, puzzle, rowOrCol, FieldState.FULL);
        if (!fullFieldsInGap.isEmpty()) {
            var min = fullFieldsInGap.stream().min(Integer::compareTo).get();
            var max = fullFieldsInGap.stream().max(Integer::compareTo).get();
            var toFillSize = max - min + 1;
            if (toFillSize == number.number) {
                // already .  .  ■  ■  ■  ■  ■  .  .  .| 5
                // or      .  .  ■  .  .  ■  ■  .  .  .| 5
                var fakeGap = new Gap(rowOrCol, min, max, toFillSize, Optional.of(number));
                fillTheGapEntirely(fakeGap, number, rowOrCol, puzzle, changes);
            } else if (toFillSize != fullFieldsInGap.size()) {
                // with holes, partially filled, patch the holes
                // from  .  .  .  .  ■  .  ■  .  .  .| 5
                //   to  .  .  .  .  ■  ■  ■  .  .  .| 5
                var fakeGap = new Gap(rowOrCol, min, max, toFillSize, Optional.empty());
                fillTheGap(fakeGap, rowOrCol, puzzle, changes);
            }
            // mark as empty:
            // from  .  .  .  .  ■  ■  ■  .  .  .| 5
            //   to  x  x  .  .  ■  ■  ■  .  .  x| 5
            var howManyMoreToFill = number.number - toFillSize;
            for (int i = gap.start; i <= min - howManyMoreToFill - 1; i++) {
                fillSingleField(rowOrCol, puzzle, changes, i, FieldState.EMPTY);
            }
            for (int i = max + howManyMoreToFill + 1; i <= gap.end; i++) {
                fillSingleField(rowOrCol, puzzle, changes, i, FieldState.EMPTY);
            }
        }
    }

    public void fillTheGapPartiallyForNNumbers(Gap gap, List<NumberToFind> numbers, RowOrCol rowOrCol, Puzzle puzzle,
        ChangedInIteration changes) {
        var gapDiff = numberSelector.calculateGapDiff(gap, numbers);
        if (gapDiff < 0) {
            return;
        }
        var sumSoFar = 0;
        for (int i = 0; i < numbers.size(); i++) {
            var number = numbers.get(i);
            var start = gap.start + gapDiff + sumSoFar + i;
            var end = gap.start + sumSoFar + number.number + i - 1;
            var howManyFieldsMayBeSet = number.number - gapDiff;
            if (howManyFieldsMayBeSet > 0 && end <= gap.end) {
                var fakeGap = new Gap(rowOrCol, start, end, howManyFieldsMayBeSet, Optional.empty());
                fillTheGap(fakeGap, rowOrCol, puzzle, changes);
            }
            sumSoFar += number.number;
        }

        gap = gapFinder.refreshSubGaps(gap, puzzle, rowOrCol).get();
        if (numbers.size() == 1 && gap.filledSubGaps.size() == 2) {
            mergeSubGaps(puzzle, changes, rowOrCol, gap, numbers.get(0), true);
        }
        if (numbers.size() == 2 && gap.filledSubGaps.size() == 2) {
            var firstNumber = numbers.get(0);
            var secondNumber = numbers.get(1);
            var firstSubGap = gap.filledSubGaps.get(0);
            var secondSubGap = gap.filledSubGaps.get(1);
            var mergeableForFirstNumber = gapFinder.areSubGapsMergeable(firstNumber.number, firstSubGap, secondSubGap);
            var mergeableForSecondNumber = gapFinder.areSubGapsMergeable(secondNumber.number, firstSubGap, secondSubGap);
            if (!mergeableForFirstNumber && !mergeableForSecondNumber) {
                var minLength = secondSubGap.start - gap.start - 1;
                if (minLength == firstNumber.number) {
                    var fakeGap = new Gap(rowOrCol, gap.start, gap.start + minLength - 1, minLength, Optional.empty());
                    fillTheGapEntirely(fakeGap, firstNumber, rowOrCol, puzzle, changes);
                }
            }
        }
    }

    public void mergeSubGaps(Puzzle puzzle, ChangedInIteration changes, RowOrCol rowOrCol, Gap gap, NumberToFind number, boolean first) {
        if (gap.filledSubGaps.size() >= 2) {
            var firstSubGap = first ? gap.filledSubGaps.get(0) : gap.filledSubGaps.get(gap.filledSubGaps.size() - 2);
            var secondSubGap = first ? gap.filledSubGaps.get(1) : gap.filledSubGaps.get(gap.filledSubGaps.size() - 1);
            var distanceFromEdge = first ? secondSubGap.start - gap.start : gap.end - firstSubGap.end;
            if (number.number >= distanceFromEdge) {
                for (int i = firstSubGap.end + 1; i < secondSubGap.start; i++) {
                    fillSingleField(rowOrCol, puzzle, changes, i, FieldState.FULL);
                }
            }
        }
    }

    public boolean findAndMarkTheOnlyPossibleCombinationForNumbers(Puzzle puzzle, ChangedInIteration changes, RowOrCol rowOrCol,
        List<Gap> gaps, Gap gap, List<NumberBeforeCurrentAndAfter> allPossibleSplitsAtNumber, OnlyPossibleCombinationGapMode gapMode) {

        var result = findTheOnlyPossibleCombinationForNumbers(rowOrCol, gaps, gap, allPossibleSplitsAtNumber, gapMode,
            OnlyPossibleCombinationSubGapMode.NO);
        if (result.isNumber()) {
            var number = result.getNumber();
            var start = gapMode.isStartingFrom() ? gap.start : gap.end - number.number + 1;
            var end = gapMode.isEndingAt() ? gap.end : gap.start + number.number - 1;
            var fakeGap = new Gap(rowOrCol, start, end, number.number, Optional.of(number));
            fillTheGapEntirely(fakeGap, number, rowOrCol, puzzle, changes);
            return true;
        } else if (result.isValue()) {
            var number = result.getValue();
            var start = gapMode.isStartingFrom() ? gap.start : gap.end - number + 1;
            var end = gapMode.isEndingAt() ? gap.end : gap.start + number - 1;
            var fakeGap = new Gap(rowOrCol, start, end, number, Optional.empty());
            var anyFilled = fillTheGap(fakeGap, rowOrCol, puzzle, changes);
            if (gapMode.isStartingFrom() && !gapMode.isEndingAt()) {
                return fillSingleField(rowOrCol, puzzle, changes, end + 1, FieldState.EMPTY) || anyFilled;
            }
            if (!gapMode.isStartingFrom() && gapMode.isEndingAt()) {
                return fillSingleField(rowOrCol, puzzle, changes, start - 1, FieldState.EMPTY) || anyFilled;
            }
            return anyFilled;
        }
        return false;
    }

    public OnlyPossibleCombinationResult findTheOnlyPossibleCombinationForNumbers(RowOrCol rowOrCol, List<Gap> gaps, Gap gap,
        List<NumberBeforeCurrentAndAfter> allPossibleSplitsAtNumber, OnlyPossibleCombinationGapMode gapMode,
        OnlyPossibleCombinationSubGapMode subGapMode) {
        var previousGaps = gapFinder.allPrevious(gaps, gap);
        var nextGaps = gapFinder.allNext(gaps, gap);
        var splitsMatchingConditions = new ArrayList<NumberBeforeCurrentAndAfter>();
        var splitsMatchingConditionsByNumberValue = new HashMap<Integer, NumberBeforeCurrentAndAfter>();
        for (var split : allPossibleSplitsAtNumber) {
            if (split.current().found) {
                continue;
            }
            boolean doAllNumbersFitBefore = doAllNumbersFitBefore(rowOrCol, gap, previousGaps, split, gapMode, subGapMode);
            boolean doAllNumbersFitAfter = doAllNumbersFitAfter(rowOrCol, gap, nextGaps, split, gapMode, subGapMode);
            if (!doAllNumbersFitBefore || !doAllNumbersFitAfter) {
                continue;
            }
            var complyWithSubGapsBefore = //previousGaps.size() == 1 &&
                numbersComplyWithSubGaps(split.before(), previousGaps);
            var complyWithSubGapsAfter = //nextGaps.size() == 1 &&
                numbersComplyWithSubGaps(split.after(), nextGaps);
            if (complyWithSubGapsBefore && complyWithSubGapsAfter) {
                splitsMatchingConditions.add(split);
                splitsMatchingConditionsByNumberValue.put(split.current().number, split);
            }
        }
        var result = OnlyPossibleCombinationResult.builder();
        if (splitsMatchingConditions.size() == 1) {
            result.number(splitsMatchingConditions.get(0).current());
        } else if (splitsMatchingConditionsByNumberValue.size() == 1) {
            result.value(splitsMatchingConditionsByNumberValue.keySet().stream().findFirst().get());
        }
        return result.build();
    }

    private boolean doAllNumbersFitBefore(RowOrCol rowOrCol, Gap gap, List<Gap> previousGaps, NumberBeforeCurrentAndAfter split,
        OnlyPossibleCombinationGapMode gapMode, OnlyPossibleCombinationSubGapMode subGapMode) {
        if (gapMode.isEnabled()) {
            var beforeAndCurrentNumber = gapMode.isStartingFrom() ? split.before() : Utils.mergeLists(split.before(), split.current());
            var previousAndCurrentGaps = gapMode.isStartingFrom() ? previousGaps : Utils.mergeLists(previousGaps, gap);
            return doAllNumbersFitInGaps(beforeAndCurrentNumber, previousAndCurrentGaps);
        } else if (subGapMode.isEnabled()) {
            var beforeAndCurrentNumber = split.before();
            List<Gap> previousAndCurrentGaps;
            if (gap.start == subGapMode.subGap.start) {
                previousAndCurrentGaps = previousGaps;
            } else {
                var partialGapBeforeSubGap = new Gap(rowOrCol, gap.start, subGapMode.subGap.start - 1, subGapMode.subGap.start - gap.start,
                    Optional.empty());
                previousAndCurrentGaps = Utils.mergeLists(previousGaps, partialGapBeforeSubGap);
            }
            return doAllNumbersFitInGaps(beforeAndCurrentNumber, previousAndCurrentGaps);
        }
        return false;
    }

    private boolean doAllNumbersFitAfter(RowOrCol rowOrCol, Gap gap, List<Gap> nextGaps, NumberBeforeCurrentAndAfter split,
        OnlyPossibleCombinationGapMode gapMode, OnlyPossibleCombinationSubGapMode subGapMode) {
        if (gapMode.isEnabled()) {
            var afterAndCurrentNumber = gapMode.isEndingAt() ? split.after() : Utils.mergeLists(split.current(), split.after());
            var nextAndCurrentGaps = gapMode.isEndingAt() ? nextGaps : Utils.mergeLists(gap, nextGaps);
            return doAllNumbersFitInGaps(afterAndCurrentNumber, nextAndCurrentGaps);
        } else if (subGapMode.isEnabled()) {
            var afterAndCurrentNumber = split.after();
            List<Gap> nextAndCurrentGaps;
            if (gap.end == subGapMode.subGap.end) {
                nextAndCurrentGaps = nextGaps;
            } else {
                var partialGapAfterSubGap = new Gap(rowOrCol, subGapMode.subGap.end + 1, gap.end, gap.end - subGapMode.subGap.end,
                    Optional.empty());
                nextAndCurrentGaps = Utils.mergeLists(partialGapAfterSubGap, nextGaps);
            }
            return doAllNumbersFitInGaps(afterAndCurrentNumber, nextAndCurrentGaps);
        }
        return false;
    }

    private boolean doAllNumbersFitInGaps(List<NumberToFind> numbers, List<Gap> gaps) {
        if (numbers.isEmpty()) {
            return true;
        }
        if (gaps.isEmpty()) {
            return false;
        }
        var firstNumberIndex = 0;
        var lastNumberIndex = numbers.size() - 1;
        for (Gap gap : gaps) {
            var soFarInThisGap = 0;
            if (firstNumberIndex > lastNumberIndex) {
                return true;
            }
            for (NumberToFind number : numbers.subList(firstNumberIndex, lastNumberIndex + 1)) {
                if (soFarInThisGap + number.number <= gap.length) {
                    soFarInThisGap += number.number + 1;
                    firstNumberIndex++;
                } else {
                    break;
                }
            }
        }
        return firstNumberIndex > lastNumberIndex;
    }

    private boolean numbersComplyWithSubGaps(List<NumberToFind> numbers, List<Gap> gaps) {
        var numberIndex = 0;
        for (Gap gap : gaps) {
            for (SubGap subGap : gap.filledSubGaps) {
                for (; numberIndex < numbers.size(); numberIndex++) {
                    if (subGap.length <= numbers.get(numberIndex).number) {
                        numberIndex++;
                        break;
                    }
                }
                if (numberIndex >= numbers.size()) {
                    return subGap.equals(gap.filledSubGaps.get(gap.filledSubGaps.size() - 1));
                }
            }
        }
        return true;
    }
}
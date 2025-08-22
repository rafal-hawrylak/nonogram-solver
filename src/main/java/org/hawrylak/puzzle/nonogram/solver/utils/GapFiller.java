package org.hawrylak.puzzle.nonogram.solver.utils;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.*;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector.NumberBeforeCurrentAndAfter;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.hawrylak.puzzle.nonogram.model.Gap.toSubGaps;

@AllArgsConstructor
public class GapFiller {

    private final FieldFinder fieldFinder;
    private final NumberSelector numberSelector;
    private final GapFinder gapFinder;

    public void fillTheGapEntirely(SubGap gap, NumberToFind number, RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes) {
        fillTheGap(gap, rowOrCol, puzzle, changes);
        if (!number.found) {
            changes.markChangeNumber(rowOrCol, number);
        }
        number.found = true;
        number.foundStart = gap.start;
        number.foundEnd = gap.end;
        fillSingleField(rowOrCol, puzzle, changes, gap.start - 1, FieldState.EMPTY);
        fillSingleField(rowOrCol, puzzle, changes, gap.end + 1, FieldState.EMPTY);
    }

    public void fillTheGapEntirelyWithNumberUnknown(SubGap gap, RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes) {
        fillTheGap(gap, rowOrCol, puzzle, changes);
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
        int start = Utils.getStart(rowOrCol, numberToClose.number, c, r, startingFrom);
        var fakeGap = new Gap(rowOrCol, start, start + numberToClose.number - 1, numberToClose.number);
        fillTheGapEntirely(fakeGap, numberToClose, rowOrCol, puzzle, changes);
    }

    public boolean fillSingleField(RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes, int i, FieldState state) {
        if (rowOrCol.horizontal) {
            if (i >= 0 && i < puzzle.width) {
                FieldState currentState = puzzle.fields[i][rowOrCol.number];
                validateStateChange(changes, state, currentState, i, rowOrCol.number);
                if (!state.equals(currentState)) {
                    puzzle.fields[i][rowOrCol.number] = state;
                    changes.markChangeField(i, rowOrCol.number);
                    return true;
                }
            }
        } else {
            if (i >= 0 && i < puzzle.height) {
                FieldState currentState = puzzle.fields[rowOrCol.number][i];
                validateStateChange(changes, state, currentState, rowOrCol.number, i);
                if (!state.equals(currentState)) {
                    puzzle.fields[rowOrCol.number][i] = state;
                    changes.markChangeField(rowOrCol.number, i);
                    return true;
                }
            }
        }
        return false;
    }

    private static void validateStateChange(ChangedInIteration changes, FieldState state, FieldState currentState, int c, int r) {
        if ((FieldState.EMPTY.equals(state) && FieldState.FULL.equals(currentState)) || (FieldState.EMPTY.equals(currentState)
            && FieldState.FULL.equals(state))) {
            throw new IllegalStateException("Changing state of the field from " + currentState + " to " + state + " at [" + c + "; " + r
                + "] is not allowed. iteration = " + changes.getIteration());
        }
    }

    public boolean fillTheGap(SubGap gap, RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes) {
        return fillTheGap(gap, FieldState.FULL, rowOrCol, puzzle, changes);
    }

    public boolean fillTheGap(SubGap gap, FieldState state, RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes) {
        var anyFilled = false;
        for (int i = gap.start; i <= gap.end; i++) {
            var filled = fillSingleField(rowOrCol, puzzle, changes, i, state);
            anyFilled = anyFilled || filled;
        }
        return anyFilled;
    }

    public void fillTheGapPartiallyForSingleNumber(SubGap gap, NumberToFind number, RowOrCol rowOrCol, Puzzle puzzle,
        ChangedInIteration changes) {
        if (2 * number.number > gap.length) {
            var howManyFieldsMayBeSet = 2 * number.number - gap.length;
            var start = gap.start + (gap.length - number.number);
            var end = start + howManyFieldsMayBeSet - 1;
            var fakeGap = new Gap(rowOrCol, start, end, howManyFieldsMayBeSet);
            fillTheGap(fakeGap, rowOrCol, puzzle, changes);
            return;
        }
        List<Integer> fullFieldsInGap = fieldFinder.findFieldsSetInGap(gap, puzzle, rowOrCol, FieldState.FULL);
        if (!fullFieldsInGap.isEmpty()) {
            int min = fullFieldsInGap.stream().min(Integer::compareTo).get();
            int max = fullFieldsInGap.stream().max(Integer::compareTo).get();
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
                var fakeGap = new Gap(rowOrCol, min, max, toFillSize);
                fillTheGap(fakeGap, rowOrCol, puzzle, changes);
            }
            // mark as empty:
            // from  .  .  .  .  ■  ■  ■  .  .  .| 5
            //   to  x  x  .  .  ■  ■  ■  .  .  x| 5
            var howManyMoreToFill = number.number - toFillSize;
            fillMultipleFields(gap.start, min - howManyMoreToFill - 1, rowOrCol, puzzle, changes, FieldState.EMPTY);
            fillMultipleFields(max + howManyMoreToFill + 1, gap.end, rowOrCol, puzzle, changes, FieldState.EMPTY);
        }
    }

    public void fillMultipleFields(int start, int end, RowOrCol rowOrCol, Puzzle puzzle,
                                   ChangedInIteration changes, FieldState fieldState) {
        for (int i = start; i <= end; i++) {
            fillSingleField(rowOrCol, puzzle, changes, i, fieldState);
        }
    }

    public void fillTheGapPartiallyForSingleNumberWithEdges(Gap gap, NumberToFind number, RowOrCol rowOrCol, Puzzle puzzle,
                                                   ChangedInIteration changes) {
        fillTheGapPartiallyForSingleNumber(gap, number, rowOrCol, puzzle, changes);
        var refreshedGap = gapFinder.refreshSubGaps(gap, puzzle, rowOrCol);
        if (refreshedGap.isEmpty()) {
            return;
        }
        gap = refreshedGap.get();
        if (gap.filledSubGaps.size() == 1) {
            var subGap = gap.filledSubGaps.getFirst();
            var howManyEmptyFromFront = subGap.end - number.number;
            if (howManyEmptyFromFront >= gap.start) {
                fillMultipleFields(gap.start, howManyEmptyFromFront, rowOrCol, puzzle, changes, FieldState.EMPTY);
            }
            var howManyEmptyFromEnd = subGap.start + number.number;
            if (howManyEmptyFromEnd <= gap.end) {
                fillMultipleFields(howManyEmptyFromEnd, gap.end, rowOrCol, puzzle, changes, FieldState.EMPTY);
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
                var fakeGap = new Gap(rowOrCol, start, end, howManyFieldsMayBeSet);
                fillTheGap(fakeGap, rowOrCol, puzzle, changes);
            }
            sumSoFar += number.number;
        }

        gap = gapFinder.refreshSubGaps(gap, puzzle, rowOrCol).get();
        if (numbers.size() == 1 && gap.filledSubGaps.size() == 2) {
            mergeSubGaps(puzzle, changes, rowOrCol, gap, numbers.getFirst(), true);
        }
        if (numbers.size() == 2 && gap.filledSubGaps.size() == 1) {
            var firstNumber = numbers.get(0);
            var secondNumber = numbers.get(1);
            var subGap = gap.filledSubGaps.getFirst();
            var fromGapStartToSubGapStart = subGap.start - gap.start + 1;
            var fromGapStartToSubGapEnd = subGap.end - gap.start + 1;
            if (fromGapStartToSubGapStart > firstNumber.number && fromGapStartToSubGapStart < firstNumber.number + 2) {
                var howManyToMark = fromGapStartToSubGapEnd - firstNumber.number;
                var fakeGap = new Gap(rowOrCol, gap.start, gap.start + howManyToMark - 1, howManyToMark);
                fillTheGap(fakeGap, FieldState.EMPTY, rowOrCol, puzzle, changes);
            }
            var fromSubGapEndToGapEnd = gap.end - subGap.end + 1;
            var fromSubGapStartToGapEnd = gap.end - subGap.start + 1;
            if (fromSubGapEndToGapEnd > secondNumber.number && fromSubGapEndToGapEnd < secondNumber.number + 2) {
                var howManyToMark = fromSubGapStartToGapEnd - secondNumber.number;
                var fakeGap = new Gap(rowOrCol, gap.end - howManyToMark + 1, gap.end, howManyToMark);
                fillTheGap(fakeGap, FieldState.EMPTY, rowOrCol, puzzle, changes);
            }
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
                    var fakeGap = new Gap(rowOrCol, gap.start, gap.start + minLength - 1, minLength);
                    fillTheGapEntirely(fakeGap, firstNumber, rowOrCol, puzzle, changes);
                }
            }
        }
    }

    public void mergeSubGaps(Puzzle puzzle, ChangedInIteration changes, RowOrCol rowOrCol, Gap gap, NumberToFind number, boolean first) {
        if (gap.filledSubGaps.size() >= 2) {
            var firstSubGap = first ? gap.filledSubGaps.get(0) : gap.filledSubGaps.get(gap.filledSubGaps.size() - 2);
            var secondSubGap = first ? gap.filledSubGaps.get(1) : gap.filledSubGaps.getLast();
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
            var fakeGap = new Gap(rowOrCol, start, end, number);
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
            var forSureDoNotComplyWithSubGapsBefore = numbersForSureDoNotComplyWithSubGaps(split.before(), previousGaps, gapMode);
            var forSureDoNotComplyWithSubGapsAfter = numbersForSureDoNotComplyWithSubGaps(split.after(), nextGaps, gapMode);
            var complyWithSubGaps = !forSureDoNotComplyWithSubGapsBefore && !forSureDoNotComplyWithSubGapsAfter;
            if (complyWithSubGaps) {
                splitsMatchingConditions.add(split);
                splitsMatchingConditionsByNumberValue.put(split.current().number, split);
            }
        }
        var result = OnlyPossibleCombinationResult.builder();
        if (splitsMatchingConditions.size() == 1) {
            result.number(splitsMatchingConditions.getFirst().current());
        } else if (splitsMatchingConditionsByNumberValue.size() == 1) {
            result.value(splitsMatchingConditionsByNumberValue.keySet().stream().findFirst().get());
        }
        return result.build();
    }

    public boolean doAllNumbersFitInGaps(List<NumberToFind> numbers, List<SubGap> gaps) {
        if (numbers.isEmpty()) {
            return true;
        }
        if (gaps.isEmpty()) {
            return false;
        }
        var firstNumberIndex = 0;
        var lastNumberIndex = numbers.size() - 1;
        for (SubGap gap : gaps) {
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

    private boolean doAllNumbersFitBefore(RowOrCol rowOrCol, Gap gap, List<Gap> previousGaps, NumberBeforeCurrentAndAfter split,
        OnlyPossibleCombinationGapMode gapMode, OnlyPossibleCombinationSubGapMode subGapMode) {
        if (gapMode.isEnabled()) {
            var beforeAndCurrentNumber = gapMode.isStartingFrom() ? split.before() : Utils.mergeLists(split.before(), split.current());
            var previousAndCurrentGaps = toSubGaps(gapMode.isStartingFrom() ? previousGaps : Utils.mergeLists(previousGaps, gap));
            return doAllNumbersFitInGaps(beforeAndCurrentNumber, previousAndCurrentGaps);
        } else if (subGapMode.isEnabled()) {
            var beforeAndCurrentNumber = split.before();
            List<SubGap> previousAndCurrentGaps;
            if (gap.start == subGapMode.subGap.start) {
                previousAndCurrentGaps = toSubGaps(previousGaps);
            } else {
                var partialGapBeforeSubGap = new Gap(rowOrCol, gap.start, subGapMode.subGap.start - 1, subGapMode.subGap.start - gap.start);
                previousAndCurrentGaps = toSubGaps(Utils.mergeLists(previousGaps, partialGapBeforeSubGap));
            }
            return doAllNumbersFitInGaps(beforeAndCurrentNumber, previousAndCurrentGaps);
        }
        return false;
    }

    private boolean doAllNumbersFitAfter(RowOrCol rowOrCol, Gap gap, List<Gap> nextGaps, NumberBeforeCurrentAndAfter split,
        OnlyPossibleCombinationGapMode gapMode, OnlyPossibleCombinationSubGapMode subGapMode) {
        if (gapMode.isEnabled()) {
            var afterAndCurrentNumber = gapMode.isEndingAt() ? split.after() : Utils.mergeLists(split.current(), split.after());
            var nextAndCurrentGaps = toSubGaps(gapMode.isEndingAt() ? nextGaps : Utils.mergeLists(gap, nextGaps));
            return doAllNumbersFitInGaps(afterAndCurrentNumber, nextAndCurrentGaps);
        } else if (subGapMode.isEnabled()) {
            var afterAndCurrentNumber = split.after();
            List<SubGap> nextAndCurrentGaps;
            if (gap.end == subGapMode.subGap.end) {
                nextAndCurrentGaps = toSubGaps(nextGaps);
            } else {
                var partialGapAfterSubGap = new Gap(rowOrCol, subGapMode.subGap.end + 1, gap.end, gap.end - subGapMode.subGap.end);
                nextAndCurrentGaps = toSubGaps(Utils.mergeLists(partialGapAfterSubGap, nextGaps));
            }
            return doAllNumbersFitInGaps(afterAndCurrentNumber, nextAndCurrentGaps);
        }
        return false;
    }

    public boolean numbersForSureDoNotComplyWithSubGaps(List<NumberToFind> numbers, List<Gap> gaps, OnlyPossibleCombinationGapMode gapMode) {
        if (gapMode.isEnabled()) {
            var maxFilledSubGap = gaps.stream().flatMap(g -> g.filledSubGaps.stream()).map(s -> s.length).max(Integer::compareTo);
            var maxNumberToFit = numbers.stream().map(n -> n.number).max(Integer::compareTo);
            if (maxNumberToFit.isEmpty()) {
                return maxFilledSubGap.isPresent();
            }
            if (maxFilledSubGap.isEmpty()) {
                return false;
            }
            if (numbers.size() == 1 && gaps.size() == 1) {
                var number = numbers.getFirst();
                var gap = gaps.getFirst();
                var startOfFirstSubGap = gap.filledSubGaps.getFirst().start;
                var endOfLastSubGap = gap.filledSubGaps.getLast().end;
                if (endOfLastSubGap - startOfFirstSubGap + 1 > number.number) {
                    return true;
                }
            }
            return maxNumberToFit.get() < maxFilledSubGap.get();
        } else {
            return false;
        }
    }
}

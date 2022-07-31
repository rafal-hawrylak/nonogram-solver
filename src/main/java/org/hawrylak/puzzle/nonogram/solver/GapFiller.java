package org.hawrylak.puzzle.nonogram.solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.FieldFinder;
import org.hawrylak.puzzle.nonogram.GapFinder;
import org.hawrylak.puzzle.nonogram.NumberSelector;
import org.hawrylak.puzzle.nonogram.NumberSelector.NumberBeforeCurrentAndAfter;
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

    public void fillSingleField(RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes, int i, FieldState state) {
        if (rowOrCol.horizontal) {
            if (i >= 0 && i < puzzle.width) {
                if (!state.equals(puzzle.fields[i][rowOrCol.number])) {
                    puzzle.fields[i][rowOrCol.number] = state;
                    changes.markChangeSingle(i, rowOrCol.number);
                }
            }
        } else {
            if (i >= 0 && i < puzzle.height) {
                if (!state.equals(puzzle.fields[rowOrCol.number][i])) {
                    puzzle.fields[rowOrCol.number][i] = state;
                    changes.markChangeSingle(rowOrCol.number, i);
                }
            }
        }
    }

    public void fillTheGap(Gap gap, RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes) {
        for (int i = gap.start; i <= gap.end; i++) {
            fillSingleField(rowOrCol, puzzle, changes, i, FieldState.FULL);
        }
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

    /*
      from  ■  x  .  .  ■  .  .  x  ■| 1 4 1
      to    ■  x  .  ■  ■  ■  .  x  ■| 1 4 1
      from  ■  x  x  x  ■  x  .  .  ■| 1 1 1 1
      to    ■  x  x  x  ■  x  ■  x  ■| 1 1 1 1
      from  ■  .  .  x  ■  x  x  x  ■| 1 1 1 1
      to    ■  x  ■  x  ■  x  x  x  ■| 1 1 1 1
     */
    public void tryToFillGapsBetweenGapsWithKnownNumbers(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            var gaps = gapFinder.find(puzzle, rowOrCol);
            var gapsWithoutNumbers = gapFinder.findWithoutAssignedNumber(puzzle, rowOrCol);
            for (Gap gap : gapsWithoutNumbers) {
                var previous = gapFinder.previous(gaps, gap);
                var next = gapFinder.next(gaps, gap);
                if ((previous.isEmpty() || previous.get().assignedNumber.isPresent()) && (next.isEmpty()
                    || next.get().assignedNumber.isPresent())) {
                    Optional<NumberToFind> numberPrevious = previous.isEmpty() ? Optional.empty() : previous.get().assignedNumber;
                    Optional<NumberToFind> numberNext = next.isEmpty() ? Optional.empty() : next.get().assignedNumber;
                    var numbersSubList = numberSelector.getNumbersBetween(rowOrCol.numbersToFind, numberPrevious, numberNext);
                    // TODO numbersSubList can be trimmed from numbers that are found - should be done smart
                    if (!numbersSubList.isEmpty()) {
                        fillTheGapPartiallyForNNumbers(gap, numbersSubList, rowOrCol, puzzle, changes);
                    }
                }
            }
        }
    }

    public void tryToAssignNumberToFilledGap(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            var gaps = gapFinder.find(puzzle, rowOrCol);
            for (Gap gap : gaps) {
                if (gap.assignedNumber.isEmpty() && gap.filledSubGaps.size() == 1) {
                    var subGap = gap.filledSubGaps.get(0);
                    if (subGap.start == gap.start && subGap.end == gap.end) {
                        var number = gap.length;
                        var numbersMatchingNumber = numberSelector.getNotFound(rowOrCol.numbersToFind).stream().filter(n -> n.number == number).toList();
                        if (numbersMatchingNumber.size() == 1) {
                            fillTheGapEntirely(gap, numbersMatchingNumber.get(0), rowOrCol, puzzle, changes);
                            continue;
                        }
                        var previousGaps = gapFinder.allPrevious(gaps, gap);
                        var nextGaps = gapFinder.allNext(gaps, gap);
                        var allPossibleNumberSplit = numberSelector.getAllPossibleNumberListsBefore(rowOrCol.numbersToFind, number);
                        var splitsMatchingConditions = new ArrayList<NumberBeforeCurrentAndAfter>();
                        for (var split : allPossibleNumberSplit) {
                            if (split.current().found) {
                                continue;
                            }
                            var doAllNumbersFitBefore = doAllNumbersFit(split.before(), previousGaps);
                            var doAllNumbersFitAfter = doAllNumbersFit(split.after(), nextGaps);
                            if (!doAllNumbersFitBefore || !doAllNumbersFitAfter) {
                                continue;
                            }
                            var singleGapBeforeAndComplyWithSubGaps =
                                previousGaps.size() == 1 && numbersComplyWithSubGaps(split.before(), previousGaps.get(0));
                            var singleGapAfterAndComplyWithSubGaps =
                                nextGaps.size() == 1 && numbersComplyWithSubGaps(split.after(), nextGaps.get(0));
                            if (singleGapBeforeAndComplyWithSubGaps && singleGapAfterAndComplyWithSubGaps) {
                                splitsMatchingConditions.add(split);
                            }
                        }
                        if (splitsMatchingConditions.size() == 1) {
                            splitsMatchingConditions.get(0).current().found = true;
                            splitsMatchingConditions.get(0).current().foundStart = gap.start;
                            splitsMatchingConditions.get(0).current().foundEnd = gap.end;
                            changes.markChange(rowOrCol);
                        }
                    }
                }
            }
        }
    }

    private boolean numbersComplyWithSubGaps(List<NumberToFind> numbers, Gap gap) {
        var numberIndex = 0;
        for (SubGap subGap : gap.filledSubGaps) {
            for (; numberIndex < numbers.size(); numberIndex++) {
                if (subGap.length <= numbers.get(numberIndex).number) {
                    numberIndex++;
                    break;
                }
            }
            if (numberIndex >= numbers.size()) {
                var isLastSubGap = subGap.equals(gap.filledSubGaps.get(gap.filledSubGaps.size() - 1));
                return isLastSubGap;
            }
        }
        return true;
    }

    private boolean doAllNumbersFit(List<NumberToFind> numbers, List<Gap> gaps) {
        if (gaps.isEmpty() && !numbers.isEmpty()) {
            return false;
        }
        if (numbers.isEmpty()) {
            return true;
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

}

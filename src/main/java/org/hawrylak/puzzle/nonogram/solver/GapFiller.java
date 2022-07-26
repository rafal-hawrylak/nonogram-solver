package org.hawrylak.puzzle.nonogram.solver;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.FieldFinder;
import org.hawrylak.puzzle.nonogram.GapFinder;
import org.hawrylak.puzzle.nonogram.model.FieldState;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.NumberToFind;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;

@AllArgsConstructor
public class GapFiller {

    private final FieldFinder fieldFinder;
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
        List<NumberToFind> numbersToClose, int start
    ) {
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

    public void fillTheGapPartiallyForNNumbers(Gap gap, List<NumberToFind> numbers, int gapDiff,
        RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes) {
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
                if (gap.assignedNumber.isPresent()) {
                    continue;
                }
                var previous = gapFinder.previous(gaps, gap);
                var next = gapFinder.next(gaps, gap);
                if ((previous.isEmpty() || previous.get().assignedNumber.isPresent()) && (next.isEmpty() || next.get().assignedNumber.isPresent())) {
                    Optional<NumberToFind> numberPrevious = previous.isEmpty() ? Optional.empty() : previous.get().assignedNumber;
                    Optional<NumberToFind> numberNext = next.isEmpty() ? Optional.empty() : next.get().assignedNumber;
                    var numbersSubList = getNumbersBetween(rowOrCol.numbersToFind, numberPrevious, numberNext);
                    if (!numbersSubList.isEmpty()) {
                        var numbersSum = numbersSubList.stream().map(n -> n.number).reduce(0, Integer::sum);
                        var gapDiff = gap.length - numbersSum - numbersSubList.size() + 1;
                        fillTheGapPartiallyForNNumbers(gap, numbersSubList, gapDiff, rowOrCol, puzzle, changes);
                    }
                }
            }
        }
    }

    private List<NumberToFind> getNumbersBetween(List<NumberToFind> numbers, Optional<NumberToFind> numberPrevious, Optional<NumberToFind> numberNext) {
        var start = numberPrevious.isPresent() ? numbers.indexOf(numberPrevious.get()) + 1 : 0;
        var end = numberNext.isPresent() ? numbers.indexOf(numberNext.get()) : numbers.size() - 1;
        return start <= end ? numbers.subList(start, end + 1) : Collections.emptyList();
    }
}

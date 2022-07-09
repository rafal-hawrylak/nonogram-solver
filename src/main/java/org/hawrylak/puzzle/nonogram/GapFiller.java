package org.hawrylak.puzzle.nonogram;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GapFiller {

    private final FieldFinder fieldFinder;

    public void fillTheGapEntirely(Gap gap, NumberToFind number, RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes) {
        fillTheGap(gap, rowOrCol, puzzle, changes);
        number.found = true;
        number.foundStart = gap.start;
        number.foundEnd = gap.end;
        fillSingleField(rowOrCol, puzzle, changes, gap.start - 1, FieldState.EMPTY);
        fillSingleField(rowOrCol, puzzle, changes, gap.end + 1, FieldState.EMPTY);
    }

    public void fillTheGapEntirelyWithNumbers(Puzzle puzzle, ChangedInIteration changesCurrent, RowOrCol rowOrCol,
        List<NumberToFind> numbersToClose, int start
    ) {
        for (NumberToFind numberToClose : numbersToClose) {
            var length = numberToClose.number;
            var fakeGap = new Gap(rowOrCol, start, start + length - 1, length, Optional.of(numberToClose));
            fillTheGapEntirely(fakeGap, numberToClose, rowOrCol, puzzle, changesCurrent);
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

    public void fillTheGapPartiallyForTwoNumbers(Gap gap, NumberToFind number1, NumberToFind number2, int gapDiff, RowOrCol rowOrCol,
        Puzzle puzzle,
        ChangedInIteration changes) {
        var howManyFieldsMayBeSet = number1.number - gapDiff;
        if (howManyFieldsMayBeSet > 0) {
            var start = gap.start + gapDiff;
            var end = start + howManyFieldsMayBeSet - 1;
            var fakeGap = new Gap(rowOrCol, start, end, howManyFieldsMayBeSet, Optional.empty());
            fillTheGap(fakeGap, rowOrCol, puzzle, changes);
        }
        var start = gap.start + gapDiff + number1.number + 1;
        var end = gap.start + number1.number + number2.number;
        howManyFieldsMayBeSet = number2.number - gapDiff;
        if (howManyFieldsMayBeSet > 0 && end <= gap.end) {
            var fakeGap = new Gap(rowOrCol, start, end, howManyFieldsMayBeSet, Optional.empty());
            fillTheGap(fakeGap, rowOrCol, puzzle, changes);
        }
    }
}

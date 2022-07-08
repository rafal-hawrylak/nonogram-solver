package org.hawrylak.puzzle.nonogram;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GapFiller {

    private final GapFinder gapFinder;
    private final NumberSelector numberSelector;

    public void fillTheOnlyMatchingGaps(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            if (changesLast.firstIteration() || changesLast.hasChanged(rowOrCol)) {
                if (!rowOrCol.solved) {
                    List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
                    if (gaps.size() == rowOrCol.numbersToFind.size()) {
                        tryToFillEachGap(gaps, rowOrCol, puzzle, changesCurrent);
                    } else if (gaps.size() > rowOrCol.numbersToFind.size()) {
                        tryToFillSomeGaps(gaps, rowOrCol, puzzle, changesCurrent);
                    } else {

                    }
                }
            }
        }
    }

    private void tryToFillSomeGaps(List<Gap> gaps, RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes) {
        var gapIndex = 0;
        // TODO - is it doable?
        for (NumberToFind number : rowOrCol.numbersToFind) {
            if (!number.found) {

            }
        }
    }

    // FIXME not very mature, lots of cases not covered
    private void tryToFillEachGap(List<Gap> gaps, RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes) {
        var offsetGap = 0;
        var offsetNumber = 0;
        for (int i = 0; i < gaps.size(); i++) {
            var number = rowOrCol.numbersToFind.get(i + offsetNumber);
            if (!number.found) {
                var gap = gaps.get(i + offsetGap);
                Optional<NumberToFind> nextNumber = numberSelector.getNext(rowOrCol.numbersToFind, number);
                Optional<Gap> nextGap = gapFinder.next(gaps, gap);
                if (nextGap.isEmpty()) {
                    if (gap.length == number.number) {
                        fillTheGapEntirely(gap, number, rowOrCol, puzzle, changes);
                    } else {
                        // TODO nextNumber.isPresent - try to fill better
                        fillTheGapPartiallyForSingleNumber(gap, number, rowOrCol, puzzle, changes);
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }

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

    public boolean isFieldAtState(RowOrCol rowOrCol, Puzzle puzzle, int i, FieldState state) {
        if (rowOrCol.horizontal) {
            if (i >= 0 && i < puzzle.width) {
                return state.equals(puzzle.fields[i][rowOrCol.number]);
            }
        } else {
            if (i >= 0 && i < puzzle.height) {
                return state.equals(puzzle.fields[rowOrCol.number][i]);
            }
        }
        return false;
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

    private void fillTheGapPartiallyForSingleNumber(Gap gap, NumberToFind number, RowOrCol rowOrCol, Puzzle puzzle,
        ChangedInIteration changes) {
        if (2 * number.number > gap.length) {
            var howManyFieldsMayBeSet = 2 * number.number - gap.length;
            var start = gap.start + (gap.length - number.number);
            var end = start + howManyFieldsMayBeSet - 1;
            var fakeGap = new Gap(rowOrCol, start, end, howManyFieldsMayBeSet, Optional.empty());
            fillTheGap(fakeGap, rowOrCol, puzzle, changes);
        }
    }

    public void fillTheGapPartiallyForTwoNumbers(Gap gap, NumberToFind number1, NumberToFind number2, RowOrCol rowOrCol, Puzzle puzzle,
        ChangedInIteration changes) {
        var howManyFieldsMayBeSet = number1.number - 1;
        if (howManyFieldsMayBeSet > 0) {
            var start = gap.start + 1;
            var end = start + howManyFieldsMayBeSet - 1;
            var fakeGap = new Gap(rowOrCol, start, end, howManyFieldsMayBeSet, Optional.empty());
            fillTheGap(fakeGap, rowOrCol, puzzle, changes);
        }
        howManyFieldsMayBeSet = number2.number - 1;
        if (howManyFieldsMayBeSet > 0) {
            var start = gap.end - howManyFieldsMayBeSet;
            var end = start + howManyFieldsMayBeSet - 1;
            var fakeGap = new Gap(rowOrCol, start, end, howManyFieldsMayBeSet, Optional.empty());
            fillTheGap(fakeGap, rowOrCol, puzzle, changes);
        }
    }
}

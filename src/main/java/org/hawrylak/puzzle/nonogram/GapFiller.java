package org.hawrylak.puzzle.nonogram;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GapFiller {

    private final GapFinder gapFinder;
    private final GapCloser gapCloser;

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
        for (NumberToFind number : rowOrCol.numbersToFind) {
            if (!number.found) {

//                while (true) { // TODO condition!!!!!!
//                    var gap = gaps.get(gapIndex);
//                    if (gap.length < number.number) {
//                        gapCloser.close(gap, puzzle, changes);
//                        gapIndex++;
//                    } else {
//
//                    }
//                }

            }
        }
    }

    private void tryToFillEachGap(List<Gap> gaps, RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes) {
        for (int i = 0; i < gaps.size(); i++) {
            var number = rowOrCol.numbersToFind.get(i);
            if (!number.found) {
                var gap = gaps.get(i);
                if (gap.length == number.number) {
                    fillTheGapEntirely(gap, number, rowOrCol, puzzle, changes);
                } else {
                    fillTheGapPartially(gap, number, rowOrCol, puzzle, changes);
                }
            }
        }
    }

    public void fillTheGapEntirely(Gap gap, NumberToFind number, RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes) {
        fillTheGap(gap, rowOrCol, puzzle, changes);
        number.found = true;
        fillSingleField(rowOrCol, puzzle, changes, gap.start - 1, FieldState.EMPTY);
        fillSingleField(rowOrCol, puzzle, changes, gap.end + 1, FieldState.EMPTY);
    }

    private void fillSingleField(RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes, int i, FieldState state) {
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

    private void fillTheGapPartially(Gap gap, NumberToFind number, RowOrCol rowOrCol, Puzzle puzzle, ChangedInIteration changes) {
        if (2 * number.number > gap.length) {
            var howManyFieldsMayBeSet = 2 * number.number - gap.length;
            var start = gap.start + (gap.length - number.number);
            var end = start + howManyFieldsMayBeSet - 1;
            var fakeGap = new Gap(rowOrCol, start, end, howManyFieldsMayBeSet);
            fillTheGap(fakeGap, rowOrCol, puzzle, changes);
        }
    }
}

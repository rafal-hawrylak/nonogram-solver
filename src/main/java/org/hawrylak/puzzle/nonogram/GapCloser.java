package org.hawrylak.puzzle.nonogram;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GapCloser {

    private final GapFinder gapFinder;
    private final GapFiller gapFiller;

    public void closeTooSmallToFillAnything(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            closeTooSmallToFillAnything(puzzle, changesLast, changesCurrent, rowOrCol);
        }
    }

    public void closeWhenAllNumbersAreFound(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            if (rowOrCol.numbersToFind.stream().allMatch(n -> n.found)) {
                List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
                for (Gap gap : gaps) {
                    closeAsEmpty(gap, puzzle, changesCurrent);
                }
            }
        }
    }

    private void closeTooSmallToFillAnything(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent,
        RowOrCol rowOrCol) {
        List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
        var min = rowOrCol.numbersToFind.stream().filter(n -> !n.found)
            .map(n -> n.number).min(Integer::compareTo);
        for (Gap gap : gaps) {
            if (min.isEmpty() || gap.length < min.get()) {
                closeAsEmpty(gap, puzzle, changesCurrent);
            }
        }
    }

    public void closeAsEmpty(Gap gap, Puzzle puzzle, ChangedInIteration changes) {
        for (int i = gap.start; i <= gap.end; i++) {
            gapFiller.fillSingleField(gap.rowOrCol, puzzle, changes, i, FieldState.EMPTY);
        }
    }
}

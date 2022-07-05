package org.hawrylak.puzzle.nonogram;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GapCloser {

    private final GapFinder gapFinder;

    void closeNotNeeded(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            closeNotNeededInRowOrCol(puzzle, changesLast, changesCurrent, rowOrCol);
        }
    }

    private void closeNotNeededInRowOrCol(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent,
        RowOrCol rowOrCol) {
        List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
        var min = rowOrCol.numbersToFind.stream().filter(n -> !n.found)
            .map(n -> n.number).min(Integer::compareTo);
        for (Gap gap : gaps) {
            if (min.isEmpty() || gap.length < min.get()) {
                close(gap, puzzle, changesCurrent);
            }
        }
    }

    public void close(Gap gap, Puzzle puzzle, ChangedInIteration changes) {
        var k = gap.rowOrCol.number;
        for (int i = gap.start; i <= gap.end; i++) {
            if (gap.rowOrCol.horizontal) {
                if (FieldState.UNKNOWN.equals(puzzle.fields[i][k])) {
                    puzzle.fields[i][k] = FieldState.EMPTY;
                    changes.markChangeSingle(i, k);
                }
            } else {
                if (FieldState.UNKNOWN.equals(puzzle.fields[k][i])) {
                    puzzle.fields[k][i] = FieldState.EMPTY;
                    changes.markChangeSingle(k, i);
                }
            }
        }
    }
}

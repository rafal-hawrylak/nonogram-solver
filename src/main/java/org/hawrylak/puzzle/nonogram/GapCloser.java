package org.hawrylak.puzzle.nonogram;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GapCloser {

    private final GapFinder gapFinder;

    void closeNotNeeded(Puzzle puzzle, ChangedInLastIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            closeNotNeededInRowOrCol(puzzle, changes, rowOrCol);
        }
    }

    private void closeNotNeededInRowOrCol(Puzzle puzzle, ChangedInLastIteration changes, RowOrCol rowOrCol) {
        if (changes.firstIteration() || changes.hasChanged(rowOrCol)) {
            List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
            var min = rowOrCol.numbersToFind.stream().filter(n -> !n.found)
                .map(n -> n.number).min(Integer::compareTo).get();
            for (Gap gap : gaps) {
                if (gap.length < min) {
                    close(gap, puzzle, changes);
                }
            }
        }
    }

    private void close(Gap gap, Puzzle puzzle, ChangedInLastIteration changes) {
        var k = gap.rowOrCol.number;
        for (int i = gap.start; i <= gap.end; i++) {
            if (gap.rowOrCol.horizontal) {
                puzzle.fields[i][k] = FieldState.EMPTY;
                changes.markChange(gap.rowOrCol, i, k);
            } else {
                puzzle.fields[k][i] = FieldState.EMPTY;
                changes.markChange(gap.rowOrCol, k, i);
            }
        }
    }
}

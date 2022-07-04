package org.hawrylak.puzzle.nonogram;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NumberCloser {

    private final RowSelector rowSelector;
    private final GapFiller gapFiller;

    public void closeAtEdges(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent) {
        // starting at edge
        for (int i = 0; i < puzzle.height; i++) {
            var rowOrCol = rowSelector.find(puzzle, i, true);
            tryToCloseFromEdge(puzzle, changesLast, changesCurrent, i, rowOrCol, true);
            tryToCloseFromEdge(puzzle, changesLast, changesCurrent, i, rowOrCol, false);
        }
        for (int i = 0; i < puzzle.width; i++) {
            var rowOrCol = rowSelector.find(puzzle, i, false);
            tryToCloseFromEdge(puzzle, changesLast, changesCurrent, i, rowOrCol, true);
            tryToCloseFromEdge(puzzle, changesLast, changesCurrent, i, rowOrCol, false);
        }
    }

    private void tryToCloseFromEdge(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent, int i,
        RowOrCol rowOrCol, boolean fromStart) {
        if (changesLast.firstIteration() || changesCurrent.hasChanged(rowOrCol)) {
            boolean hasNumbers = !rowOrCol.numbersToFind.isEmpty();
            var indexOfNumber = fromStart ? 0 : rowOrCol.numbersToFind.size() - 1;
            if (hasNumbers && !rowOrCol.numbersToFind.get(indexOfNumber).found) {
                var numberToFind = rowOrCol.numbersToFind.get(indexOfNumber);
                if (rowOrCol.horizontal) {
                    if (fromStart) {
                        if (FieldState.FULL.equals(puzzle.fields[0][i])) {
                            var fakeGap = new Gap(rowOrCol, 0, numberToFind.number - 1, numberToFind.number);
                            gapFiller.fillTheGapEntirely(fakeGap, numberToFind, rowOrCol, puzzle, changesCurrent);
                        }
                    } else {
                        if (FieldState.FULL.equals(puzzle.fields[puzzle.width - 1][i])) {
                            var fakeGap = new Gap(rowOrCol, puzzle.width - numberToFind.number, puzzle.width - 1, numberToFind.number);
                            gapFiller.fillTheGapEntirely(fakeGap, numberToFind, rowOrCol, puzzle, changesCurrent);
                        }
                    }
                } else {
                    if (fromStart) {
                        if (FieldState.FULL.equals(puzzle.fields[i][0])) {
                            var fakeGap = new Gap(rowOrCol, 0, numberToFind.number - 1, numberToFind.number);
                            gapFiller.fillTheGapEntirely(fakeGap, numberToFind, rowOrCol, puzzle, changesCurrent);
                        }
                    } else {
                        if (FieldState.FULL.equals(puzzle.fields[i][puzzle.height - 1])) {
                            var fakeGap = new Gap(rowOrCol, puzzle.height - numberToFind.number, puzzle.height - 1, numberToFind.number);
                            gapFiller.fillTheGapEntirely(fakeGap, numberToFind, rowOrCol, puzzle, changesCurrent);
                        }
                    }
                }
            }
        }
    }

    public void closeWithOneEnd(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            if (changesLast.firstIteration() || changesCurrent.hasChanged(rowOrCol)) {

            }
        }
    }

    public void closeWithTwoEnds(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent) {

    }

    public void closeWithNoEnds(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent) {

    }
}

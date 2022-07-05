package org.hawrylak.puzzle.nonogram;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NumberCloser {

    private final RowSelector rowSelector;
    private final GapFinder gapFinder;
    private final GapFiller gapFiller;

    public void closeAtEdges(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent) {
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
        if (changesLast.firstIteration() || changesLast.hasChanged(rowOrCol)) {
            boolean hasNumbers = !rowOrCol.numbersToFind.isEmpty();
            var indexOfNumber = fromStart ? 0 : rowOrCol.numbersToFind.size() - 1;
            if (hasNumbers && !rowOrCol.numbersToFind.get(indexOfNumber).found) {
                var numberToClose = rowOrCol.numbersToFind.get(indexOfNumber);
                if (rowOrCol.horizontal) {
                    if (fromStart) {
                        if (FieldState.FULL.equals(puzzle.fields[0][i])) {
                            var fakeGap = new Gap(rowOrCol, 0, numberToClose.number - 1, numberToClose.number, Optional.empty());
                            gapFiller.fillTheGapEntirely(fakeGap, numberToClose, rowOrCol, puzzle, changesCurrent);
                        }
                    } else {
                        if (FieldState.FULL.equals(puzzle.fields[puzzle.width - 1][i])) {
                            var fakeGap = new Gap(rowOrCol, puzzle.width - numberToClose.number, puzzle.width - 1, numberToClose.number,
                                Optional.empty());
                            gapFiller.fillTheGapEntirely(fakeGap, numberToClose, rowOrCol, puzzle, changesCurrent);
                        }
                    }
                } else {
                    if (fromStart) {
                        if (FieldState.FULL.equals(puzzle.fields[i][0])) {
                            var fakeGap = new Gap(rowOrCol, 0, numberToClose.number - 1, numberToClose.number, Optional.empty());
                            gapFiller.fillTheGapEntirely(fakeGap, numberToClose, rowOrCol, puzzle, changesCurrent);
                        }
                    } else {
                        if (FieldState.FULL.equals(puzzle.fields[i][puzzle.height - 1])) {
                            var fakeGap = new Gap(rowOrCol, puzzle.height - numberToClose.number, puzzle.height - 1, numberToClose.number,
                                Optional.empty());
                            gapFiller.fillTheGapEntirely(fakeGap, numberToClose, rowOrCol, puzzle, changesCurrent);
                        }
                    }
                }
            }
        }
    }

    public void closeWithOneEnd(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent) {
        for (int i = 0; i < puzzle.width; i++) {
            for (int j = 0; j < puzzle.height; j++) {
                if (FieldState.EMPTY.equals(puzzle.fields[j][i])) {
                    Collection<RowOrCol> rowAndCol = changesCurrent.findPerpendicularRowOrCol(i, j);
                    for (RowOrCol rowOrCol : rowAndCol) {
                        tryToCloseFromEmpty(rowOrCol, i, j, puzzle, changesCurrent);
                    }
                }
            }
        }
    }

    private void tryToCloseFromEmpty(RowOrCol rowOrCol, int i, int j, Puzzle puzzle, ChangedInIteration changesCurrent) {
        if (rowOrCol.horizontal) {
            tryToCloseFromFullNextToEmpty(rowOrCol, i, j - 1, false, puzzle, changesCurrent);
            tryToCloseFromFullNextToEmpty(rowOrCol, i, j + 1, true, puzzle, changesCurrent);
        } else {
            tryToCloseFromFullNextToEmpty(rowOrCol, i - 1, j, false, puzzle, changesCurrent);
            tryToCloseFromFullNextToEmpty(rowOrCol, i + 1, j, true, puzzle, changesCurrent);
        }
    }

    private void tryToCloseFromFullNextToEmpty(RowOrCol rowOrCol, int i, int j, boolean startingFrom, Puzzle puzzle,
        ChangedInIteration changesCurrent
    ) {
        if (i < 0 || i >= puzzle.width || j < 0 || j >= puzzle.height) {
            return;
        }
        if (!FieldState.FULL.equals(puzzle.fields[j][i])) {
            return;
        }
        List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
        var positionInGap = rowOrCol.horizontal ? j : i;
        var gapAtPosition = gapFinder.getGapAtPosition(gaps, positionInGap, positionInGap);
        if (gapAtPosition.assignedNumber.isPresent() && gapAtPosition.assignedNumber.get().found) {
            return;
        }
        var previousGap = gapFinder.previousGap(gaps, gapAtPosition);
        if (previousGap.isEmpty()) {
            var numberToClose = rowOrCol.numbersToFind.get(0);
            fillTheNumber(rowOrCol, numberToClose, i, j, startingFrom, puzzle, changesCurrent);
        } else {
            if (previousGap.get().assignedNumber.isPresent()) {
                // TODO find current number, next to the one from previous gap
            }
        }
    }

    private void fillTheNumber(RowOrCol rowOrCol, NumberToFind numberToClose, int i, int j, boolean startingFrom,
        Puzzle puzzle, ChangedInIteration changesCurrent) {
        int start;
        if (startingFrom) {
            start = rowOrCol.horizontal ? j : i;
        } else {
            start = rowOrCol.horizontal ? j - numberToClose.number + 1 : i - numberToClose.number + 1;
        }
        var fakeGap = new Gap(rowOrCol, start, start + numberToClose.number - 1, numberToClose.number, Optional.empty());
        gapFiller.fillTheGapEntirely(fakeGap, numberToClose, rowOrCol, puzzle, changesCurrent);
    }

    public void closeWithTwoEnds(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent) {

    }

    public void closeWithNoEnds(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent) {

    }

    public void closeTheOnlyCombination(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent) {
        // TODO if sum(numbers) + count(numbers) - 1 == width or height -> fill the whole row or col
    }
}

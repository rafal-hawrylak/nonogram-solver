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
        ChangedInIteration changesCurrent) {
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
        var nextGap = gapFinder.nextGap(gaps, gapAtPosition);
        if (nextGap.isPresent() && nextGap.get().assignedNumber.isPresent()) {
            var numberToClose = getPreviousNumber(rowOrCol.numbersToFind, nextGap.get().assignedNumber.get());
            if (numberToClose.isPresent()) {
                fillTheNumber(rowOrCol, numberToClose.get(), i, j, startingFrom, puzzle, changesCurrent);
            }
        } else if (previousGap.isPresent() && previousGap.get().assignedNumber.isPresent()) {
            var numberToClose = getNextNumber(rowOrCol.numbersToFind, previousGap.get().assignedNumber.get());
            if (numberToClose.isPresent()) {
                fillTheNumber(rowOrCol, numberToClose.get(), i, j, startingFrom, puzzle, changesCurrent);
            }
        } else {

            // TODO if (next.get().assignedNumber.isPresent()) {
        }
    }

    private Optional<NumberToFind> getNextNumber(List<NumberToFind> numbersToFind, NumberToFind numberToFind) {
        for (int i = 0; i < numbersToFind.size(); i++) {
            if (numbersToFind.get(i).equals(numberToFind)) {
                if (i == numbersToFind.size() - 1) {
                    return Optional.empty();
                } else {
                    return Optional.of(numbersToFind.get(i + 1));
                }
            }
        }
        return Optional.empty();
    }

    private Optional<NumberToFind> getPreviousNumber(List<NumberToFind> numbersToFind, NumberToFind numberToFind) {
        for (int i = 0; i < numbersToFind.size(); i++) {
            if (numbersToFind.get(i).equals(numberToFind)) {
                if (i == 0) {
                    return Optional.empty();
                } else {
                    return Optional.of(numbersToFind.get(i - 1));
                }
            }
        }
        return Optional.empty();
    }

    private void fillTheNumber(RowOrCol rowOrCol, NumberToFind numberToClose, int i, int j, boolean startingFrom, Puzzle puzzle,
        ChangedInIteration changesCurrent) {
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
        for (RowOrCol rowsOrCol : puzzle.rowsOrCols) {
            var sumOfNumbers = rowsOrCol.numbersToFind.stream().map(n -> n.number).reduce(0, Integer::sum);
            var countOfNumbers = rowsOrCol.numbersToFind.size();
            var limit = rowsOrCol.horizontal ? puzzle.width : puzzle.height;
            if (sumOfNumbers + countOfNumbers - 1 == limit) {
                var start = 0;
                for (NumberToFind numberToClose : rowsOrCol.numbersToFind) {
                    var length = numberToClose.number;
                    var fakeGap = new Gap(rowsOrCol, start, start + length - 1, length, Optional.of(numberToClose));
                    gapFiller.fillTheGapEntirely(fakeGap, numberToClose, rowsOrCol, puzzle, changesCurrent);
                    rowsOrCol.solved = true;
                    start += length + 1;
                }
            }
        }
    }
}

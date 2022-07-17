package org.hawrylak.puzzle.nonogram;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NumberCloser {

    private final RowSelector rowSelector;
    private final NumberSelector numberSelector;
    private final GapFinder gapFinder;
    private final GapFiller gapFiller;
    private final GapCloser gapCloser;


    /*
      ex
        .  .  .  .  x  .  .  <  x|
        .  .  .  .  v  .  ^  .  .|
        x  >  .  .  .  .  x  .  .|
     */
    public void closeAtEdges(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent) {
        for (int r = 0; r < puzzle.height; r++) {
            var rowOrCol = rowSelector.find(puzzle, r, true);
            tryToCloseFromEdge(puzzle, changesLast, changesCurrent, r, rowOrCol, true);
            tryToCloseFromEdge(puzzle, changesLast, changesCurrent, r, rowOrCol, false);
        }
        for (int c = 0; c < puzzle.width; c++) {
            var rowOrCol = rowSelector.find(puzzle, c, false);
            tryToCloseFromEdge(puzzle, changesLast, changesCurrent, c, rowOrCol, true);
            tryToCloseFromEdge(puzzle, changesLast, changesCurrent, c, rowOrCol, false);
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

    /*
      ex
        .  .  .  .  ^  .  .  .  .|
        .  .  .  <  x  >  .  .  .|
        .  .  .  .  v  .  .  .  .|
     */
    public void closeWithOneEnd(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent) {
        for (int c = 0; c < puzzle.width; c++) {
            for (int r = 0; r < puzzle.height; r++) {
                if (FieldState.EMPTY.equals(puzzle.fields[c][r])) {
                    var rowAndCol = changesCurrent.findPerpendicularRowOrCol(c, r);
                    for (RowOrCol rowOrCol : rowAndCol) {
                        tryToCloseFromEmpty(rowOrCol, c, r, puzzle, changesCurrent);
                    }
                }
            }
        }
    }

    private void tryToCloseFromEmpty(RowOrCol rowOrCol, int c, int r, Puzzle puzzle, ChangedInIteration changesCurrent) {
        if (rowOrCol.horizontal) {
            tryToCloseFromFullNextToEmpty(rowOrCol, c - 1, r, false, puzzle, changesCurrent);
            tryToCloseFromFullNextToEmpty(rowOrCol, c + 1, r, true, puzzle, changesCurrent);
        } else {
            tryToCloseFromFullNextToEmpty(rowOrCol, c, r - 1, false, puzzle, changesCurrent);
            tryToCloseFromFullNextToEmpty(rowOrCol, c, r + 1, true, puzzle, changesCurrent);
        }
    }

    private void tryToCloseFromFullNextToEmpty(RowOrCol rowOrCol, int c, int r, boolean startingFrom, Puzzle puzzle,
        ChangedInIteration changesCurrent) {
        if (c < 0 || c >= puzzle.width || r < 0 || r >= puzzle.height) {
            return;
        }
        if (!FieldState.FULL.equals(puzzle.fields[c][r])) {
            return;
        }
        List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
        var positionInGap = rowOrCol.horizontal ? c : r;
        var gapAtPosition = gapFinder.getGapAtPosition(gaps, positionInGap, positionInGap);
        if (gapAtPosition.assignedNumber.isPresent() && gapAtPosition.assignedNumber.get().found) {
            return;
        }
        var previousGap = gapFinder.previous(gaps, gapAtPosition);
        var nextGap = gapFinder.next(gaps, gapAtPosition);
        var fillingSuccessful = false;
        /*
         nextGap.isEmpty
         3|  ■  x  .  .  .  ?  x  x  x  x| 1 1 1
         nextGap.get().assignedNumber.isPresent()
         3|  ■  x  .  .  .  ?  x  x  ■  ■| 1 1 1 2
         */
        if (!startingFrom && (nextGap.isEmpty() || nextGap.get().assignedNumber.isPresent())) {
            var numberToClose = nextGap.isEmpty()
                ? numberSelector.getLast(rowOrCol.numbersToFind)
                : numberSelector.getPrevious(rowOrCol.numbersToFind, nextGap.get().assignedNumber.get());
            if (numberToClose.isPresent()) {
                fillTheNumber(rowOrCol, numberToClose.get(), c, r, startingFrom, puzzle, changesCurrent);
                fillingSuccessful = true;
            }
        }
        /*
        startingFrom AND ...
         previousGap.isEmpty
         3|  x  x  ?  .  .  .  .  x  x  x| 1 1 1
         OR previousGap.get().assignedNumber.isPresent()
         3|  ■  x  ?  .  .  .  x  x  ■  ■| 1 1 1 2
         */
        else if (startingFrom && (previousGap.isEmpty() || previousGap.get().assignedNumber.isPresent())) {
            var numberToClose = previousGap.isEmpty() ? numberSelector.getFirst(rowOrCol.numbersToFind)
                : numberSelector.getNext(rowOrCol.numbersToFind, previousGap.get().assignedNumber.get());
            if (numberToClose.isPresent()) {
                fillTheNumber(rowOrCol, numberToClose.get(), c, r, startingFrom, puzzle, changesCurrent);
                fillingSuccessful = true;
            }
        }
        /*
        startingFrom AND ...
         nextGap.isEmpty AND ...
             we can't fit more in this gap than the last number or the last number is the only number
             3|  .  .  .  x  ?  .  .  x  x  x  x  x| 1
             3|  .  .  .  x  ?  .  x  x  x  x  x  x| 1 1
         */
        else if (startingFrom && (nextGap.isEmpty() || nextGap.get().assignedNumber.isPresent())) {
            var lastNumber = nextGap.isEmpty()
                ? numberSelector.getLast(rowOrCol.numbersToFind).get()
                : numberSelector.getPrevious(rowOrCol.numbersToFind, nextGap.get().assignedNumber.get()).get();
            var lastButOneNumber = numberSelector.getPrevious(rowOrCol.numbersToFind, lastNumber);
            if (lastButOneNumber.isEmpty() || lastButOneNumber.get().found
                || lastButOneNumber.get().number + lastNumber.number + 1 > gapAtPosition.length) {
                fillTheNumber(rowOrCol, lastNumber, c, r, startingFrom, puzzle, changesCurrent);
                fillingSuccessful = true;
            }
        }
        /*
        !startingFrom AND ...
         previousGap.isEmpty AND
             we can't fit more in this gap than the first number
             3|  .  ?  x  x  ■  x  x  x| 1 1
         */
        else if (!startingFrom && previousGap.isEmpty()) {
            var firstNumber = numberSelector.getFirst(rowOrCol.numbersToFind).get();
            var secondNumber = numberSelector.getNext(rowOrCol.numbersToFind, firstNumber);
            if (secondNumber.isEmpty() || secondNumber.get().found
                || secondNumber.get().number + firstNumber.number + 1 > gapAtPosition.length) {
                fillTheNumber(rowOrCol, firstNumber, c, r, startingFrom, puzzle, changesCurrent);
                fillingSuccessful = true;
            }
        }
        /*
        !startingFrom AND ...
         the first not found number fits into the gap AND ...
         next number is empty or found or too big to fit into the gap AND ...
         previousGap.isEmpty
         3|  x  x  .  .  .  .  ?  x  x  x| 1 1 1
         OR previousGap.get().assignedNumber.isPresent()
         3|  ■  x  .  .  .  .  ?  x  ■  ■| 1 1 1 2
         */
        else if (!startingFrom && (previousGap.isEmpty() || previousGap.get().assignedNumber.isPresent())) {
            var firstNumber = previousGap.isEmpty()
                ? rowOrCol.numbersToFind.get(0)
                : numberSelector.getNext(rowOrCol.numbersToFind, previousGap.get().assignedNumber.get()).get();
            var secondNumber = numberSelector.getPrevious(rowOrCol.numbersToFind, firstNumber);
            if (secondNumber.isEmpty() || secondNumber.get().found
                || secondNumber.get().number + firstNumber.number + 1 > gapAtPosition.length) {
                fillTheNumber(rowOrCol, firstNumber, c, r, startingFrom, puzzle, changesCurrent);
                fillingSuccessful = true;
            }
        }

        /*
          this is the only not found number
             3|  .  .  .  x  ?  .  x  x  x  x  x  x| 1
             3|  ■  x  ■  x  ?  .  x  x  x  x  x  x| 1 1 1
         */
        if (!fillingSuccessful) {
            var allNotFoundNumbers = rowOrCol.numbersToFind.stream().filter(n -> !n.found).toList();
            if (allNotFoundNumbers.size() == 1) {
                var numberToClose = allNotFoundNumbers.get(0);
                fillTheNumber(rowOrCol, numberToClose, c, r, startingFrom, puzzle, changesCurrent);
            }
        }
    }

    private void fillTheNumber(RowOrCol rowOrCol, NumberToFind numberToClose, int c, int r, boolean startingFrom, Puzzle puzzle,
        ChangedInIteration changesCurrent) {
        int start;
        if (startingFrom) {
            start = rowOrCol.horizontal ? c : r;
        } else {
            start = rowOrCol.horizontal ? c - numberToClose.number + 1 : r - numberToClose.number + 1;
        }
        var fakeGap = new Gap(rowOrCol, start, start + numberToClose.number - 1, numberToClose.number, Optional.empty());
        gapFiller.fillTheGapEntirely(fakeGap, numberToClose, rowOrCol, puzzle, changesCurrent);
    }

    /*
      ex
        x  x  ■  ■  x  x  x  x  .  ■  ■  ■  ■  ■  .| 2 5
        to
        x  x  ■  ■  x  x  x  x  x  ■  ■  ■  ■  ■  x| 2 5

      and

        x  x  .  .  x  x  x  x  .  ■  ■  ■  ■  ■  .| 5
        to
        x  x  x  x  x  x  x  x  x  ■  ■  ■  ■  ■  x| 5
     */
    public void closeAllTheGapsIfAllFullMarked(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            var sumOfNumbers = rowOrCol.numbersToFind.stream().map(n -> n.number).reduce(0, Integer::sum);
            var countOfFullFields = rowSelector.countOfFields(puzzle, rowOrCol, FieldState.FULL);
            if (sumOfNumbers == countOfFullFields) {
                List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
                for (Gap gap : gaps) {
                    gapCloser.close(gap, puzzle, changesCurrent);
                }
            }
        }
    }

    /*
      ex
        x  x  .  .  x  x  x  x  .  ■  ■  ■  ■  ■  .| 2 5
        to
        x  x  .  .  x  x  x  x  x  ■  ■  ■  ■  ■  x| 2 5
     */
    public void fitTheNumbersInOnlyPossibleGaps(Puzzle puzzle, ChangedInIteration changesCurrent) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            var notClosedGaps = gapFinder.find(puzzle, rowOrCol).stream().filter(g -> g.assignedNumber.isEmpty()).toList();
            var biggerNumbersFirst = rowOrCol.numbersToFind.stream()
                .filter(n -> !n.found)
                .sorted(Comparator.comparingInt(NumberToFind::getNumber).reversed())
                .toList();
            if (!biggerNumbersFirst.isEmpty()) {
                var numberToFind = biggerNumbersFirst.get(0);
                var gapsTheAreCapableToFit = notClosedGaps.stream().filter(g -> g.length >= numberToFind.number).toList();
                if (gapsTheAreCapableToFit.size() == 1) {
                    var gap = gapsTheAreCapableToFit.get(0);
                    var gapWithMaxSubsequentFullFields = gapFinder.maxSubsequentCountOfFields(puzzle, rowOrCol, gap, FieldState.FULL);
                    // FIXME |.  .  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  .| 5 5
                    // will close first 5 from numbers but
                    if (gapWithMaxSubsequentFullFields.length == numberToFind.number) {
                        long countOfNumbersEqualtoFind = biggerNumbersFirst.stream().filter(n -> n.number == numberToFind.number).count();
                        if (countOfNumbersEqualtoFind == 1) {
                            var fakeGap = new Gap(rowOrCol, gapWithMaxSubsequentFullFields.start, gapWithMaxSubsequentFullFields.end,
                                numberToFind.number, Optional.of(numberToFind));
                            gapFiller.fillTheGapEntirely(fakeGap, numberToFind, rowOrCol, puzzle, changesCurrent);
                        }
                    } else {
                        if (biggerNumbersFirst.size() == 1) {
                            gapFiller.fillTheGapPartiallyForSingleNumber(gap, numberToFind, rowOrCol, puzzle, changesCurrent);
                        }
                    }
                }
            }
        }
    }

    public void closeTheOnlyCombination(Puzzle puzzle, ChangedInIteration changesCurrent) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            var sumOfNumbers = rowOrCol.numbersToFind.stream().map(n -> n.number).reduce(0, Integer::sum);
            var countOfNumbers = rowOrCol.numbersToFind.size();
            var limit = rowOrCol.horizontal ? puzzle.width : puzzle.height;
            if (sumOfNumbers + countOfNumbers - 1 == limit) {
                gapFiller.fillTheGapEntirelyWithNumbers(puzzle, changesCurrent, rowOrCol, rowOrCol.numbersToFind, 0);
            }
        }
    }
}

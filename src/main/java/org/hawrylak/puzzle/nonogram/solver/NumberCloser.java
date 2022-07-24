package org.hawrylak.puzzle.nonogram.solver;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.FieldFinder;
import org.hawrylak.puzzle.nonogram.FieldState;
import org.hawrylak.puzzle.nonogram.GapFinder;
import org.hawrylak.puzzle.nonogram.NumberSelector;
import org.hawrylak.puzzle.nonogram.model.NumberToFind;
import org.hawrylak.puzzle.nonogram.RowSelector;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.model.SubGap;

@AllArgsConstructor
public class NumberCloser {

    private final FieldFinder fieldFinder;

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
    public void closeAtEdges(Puzzle puzzle, ChangedInIteration changes) {
        for (int r = 0; r < puzzle.height; r++) {
            var rowOrCol = rowSelector.find(puzzle, r, true);
            tryToCloseFromEdge(puzzle, changes, r, rowOrCol, true);
            tryToCloseFromEdge(puzzle, changes, r, rowOrCol, false);
        }
        for (int c = 0; c < puzzle.width; c++) {
            var rowOrCol = rowSelector.find(puzzle, c, false);
            tryToCloseFromEdge(puzzle, changes, c, rowOrCol, true);
            tryToCloseFromEdge(puzzle, changes, c, rowOrCol, false);
        }
    }

    private void tryToCloseFromEdge(Puzzle puzzle, ChangedInIteration changes, int i,
        RowOrCol rowOrCol, boolean fromStart) {
        boolean hasNumbers = !rowOrCol.numbersToFind.isEmpty();
        var indexOfNumber = fromStart ? 0 : rowOrCol.numbersToFind.size() - 1;
        if (hasNumbers && !rowOrCol.numbersToFind.get(indexOfNumber).found) {
            var numberToClose = rowOrCol.numbersToFind.get(indexOfNumber);
            if (rowOrCol.horizontal) {
                if (fromStart) {
                    if (FieldState.FULL.equals(puzzle.fields[0][i])) {
                        var fakeGap = new Gap(rowOrCol, 0, numberToClose.number - 1, numberToClose.number, Optional.empty());
                        gapFiller.fillTheGapEntirely(fakeGap, numberToClose, rowOrCol, puzzle, changes);
                    }
                } else {
                    if (FieldState.FULL.equals(puzzle.fields[puzzle.width - 1][i])) {
                        var fakeGap = new Gap(rowOrCol, puzzle.width - numberToClose.number, puzzle.width - 1, numberToClose.number,
                            Optional.empty());
                        gapFiller.fillTheGapEntirely(fakeGap, numberToClose, rowOrCol, puzzle, changes);
                    }
                }
            } else {
                if (fromStart) {
                    if (FieldState.FULL.equals(puzzle.fields[i][0])) {
                        var fakeGap = new Gap(rowOrCol, 0, numberToClose.number - 1, numberToClose.number, Optional.empty());
                        gapFiller.fillTheGapEntirely(fakeGap, numberToClose, rowOrCol, puzzle, changes);
                    }
                } else {
                    if (FieldState.FULL.equals(puzzle.fields[i][puzzle.height - 1])) {
                        var fakeGap = new Gap(rowOrCol, puzzle.height - numberToClose.number, puzzle.height - 1, numberToClose.number,
                            Optional.empty());
                        gapFiller.fillTheGapEntirely(fakeGap, numberToClose, rowOrCol, puzzle, changes);
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
    public void closeWithOneEnd(Puzzle puzzle, ChangedInIteration changes) {
        for (int c = 0; c < puzzle.width; c++) {
            for (int r = 0; r < puzzle.height; r++) {
                if (FieldState.EMPTY.equals(puzzle.fields[c][r])) {
                    var rowAndCol = changes.findPerpendicularRowOrCol(c, r);
                    for (RowOrCol rowOrCol : rowAndCol) {
                        tryToCloseFromEmpty(rowOrCol, c, r, puzzle, changes);
                    }
                }
            }
        }
    }

    private void tryToCloseFromEmpty(RowOrCol rowOrCol, int c, int r, Puzzle puzzle, ChangedInIteration changes) {
        if (rowOrCol.horizontal) {
            tryToCloseFromFullNextToEmpty(rowOrCol, c - 1, r, false, puzzle, changes);
            tryToCloseFromFullNextToEmpty(rowOrCol, c + 1, r, true, puzzle, changes);
        } else {
            tryToCloseFromFullNextToEmpty(rowOrCol, c, r - 1, false, puzzle, changes);
            tryToCloseFromFullNextToEmpty(rowOrCol, c, r + 1, true, puzzle, changes);
        }
    }

    private void tryToCloseFromFullNextToEmpty(RowOrCol rowOrCol, int c, int r, boolean startingFrom, Puzzle puzzle,
        ChangedInIteration changes) {
        if (c < 0 || c >= puzzle.width || r < 0 || r >= puzzle.height) {
            return;
        }
        if (!FieldState.FULL.equals(puzzle.fields[c][r])) {
            return;
        }
        var gaps = gapFinder.find(puzzle, rowOrCol);
        var position = rowOrCol.horizontal ? c : r;
        var gapAtPosition = gapFinder.getGapAtPosition(gaps, position, position);
        if (gapAtPosition.assignedNumber.isPresent() && gapAtPosition.assignedNumber.get().found) {
            return;
        }
        var previousGap = gapFinder.previous(gaps, gapAtPosition);
        var nextGap = gapFinder.next(gaps, gapAtPosition);
        var fillingSuccessful = false;

        /*
        startingFrom AND ...
         previousGap.isEmpty
         3|  x  x  ?  .  .  .  .  x  x  x| 1 1 1
         OR previousGap.get().assignedNumber.isPresent()
         3|  ■  x  ?  .  .  .  x  x  ■  ■| 1 1 1 2
         */
        if (startingFrom && (previousGap.isEmpty() || previousGap.get().assignedNumber.isPresent())) {
            var numberToClose = previousGap.isEmpty() ? numberSelector.getFirst(rowOrCol.numbersToFind)
                : numberSelector.getNext(rowOrCol.numbersToFind, previousGap.get().assignedNumber.get());
            if (numberToClose.isPresent()) {
                fillTheNumber(rowOrCol, numberToClose.get(), c, r, startingFrom, puzzle, changes);
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
                fillTheNumber(rowOrCol, lastNumber, c, r, startingFrom, puzzle, changes);
                fillingSuccessful = true;
            }
        }
        /*
        !startingFrom AND ...
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
                fillTheNumber(rowOrCol, numberToClose.get(), c, r, startingFrom, puzzle, changes);
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
            var secondNumber = numberSelector.getNext(rowOrCol.numbersToFind, firstNumber);
            if (secondNumber.isEmpty() || secondNumber.get().found
                || secondNumber.get().number + firstNumber.number + 1 > gapAtPosition.length) {
                fillTheNumber(rowOrCol, firstNumber, c, r, startingFrom, puzzle, changes);
                fillingSuccessful = true;
            }
        }

        if (!fillingSuccessful) {
            var allNotFoundNumbers = rowOrCol.numbersToFind.stream().filter(n -> !n.found).toList();
            if (allNotFoundNumbers.size() == 1) {
                /*
                  this is the only not found number
                     3|  .  .  .  x  ?  .  x  x  x  x  x  x| 1
                     3|  ■  x  ■  x  ?  .  x  x  x  x  x  x| 1 1 1
                 */
                var numberToClose = allNotFoundNumbers.get(0);
                fillTheNumber(rowOrCol, numberToClose, c, r, startingFrom, puzzle, changes);
            } else {
                /*
                  gap partially filled with the biggest number
                     3|  .  .  .  x  ?  ■  ■  ■  .  .  .  .  .  x| 3 7 1
                 */
                var biggestNumbers = numberSelector.getBiggestNotFound(rowOrCol.numbersToFind);
                var biggestNumber = biggestNumbers.get(0);
                var secondBiggestNumbers = numberSelector.getSecondBiggestNotFound(rowOrCol.numbersToFind);
                var start = getStart(rowOrCol, biggestNumber, c, r, startingFrom);
                var fakeGapBiggestUnknown = new Gap(rowOrCol, start, start + biggestNumber.number - 1, biggestNumber.number, Optional.empty());
                var fakeGapBiggestKnown = new Gap(rowOrCol, start, start + biggestNumber.number - 1, biggestNumber.number, Optional.of(biggestNumber));
                Optional<SubGap> subGapAtPosition = getSubGapAtPosition(gapAtPosition.filledSubGaps, rowOrCol.horizontal, c, r);
                if (secondBiggestNumbers.isEmpty() || (subGapAtPosition.isPresent() && subGapAtPosition.get().length > secondBiggestNumbers.get(0).number)) {
                    if (biggestNumbers.size() == 1) {
                        gapFiller.fillTheGapEntirely(fakeGapBiggestKnown, biggestNumber, rowOrCol, puzzle, changes);
                        fillingSuccessful = true;
                    } else {
                        gapFiller.fillTheGap(fakeGapBiggestUnknown, rowOrCol, puzzle, changes);
                        fillingSuccessful = true;
                    }
                }
            }
        }
    }

    private Optional<SubGap> getSubGapAtPosition(List<SubGap> filledSubGaps, boolean horizontal, int c, int r) {
        var position = horizontal ? c : r;
        return filledSubGaps.stream().filter(s -> s.start <= position).filter(s -> s.end >= position).findFirst();
    }

    private void fillTheNumber(RowOrCol rowOrCol, NumberToFind numberToClose, int c, int r, boolean startingFrom, Puzzle puzzle,
        ChangedInIteration changes) {
        int start = getStart(rowOrCol, numberToClose, c, r, startingFrom);
        var fakeGap = new Gap(rowOrCol, start, start + numberToClose.number - 1, numberToClose.number, Optional.empty());
        gapFiller.fillTheGapEntirely(fakeGap, numberToClose, rowOrCol, puzzle, changes);
    }

    private int getStart(RowOrCol rowOrCol, NumberToFind numberToClose, int c, int r, boolean startingFrom) {
        int start;
        if (startingFrom) {
            start = rowOrCol.horizontal ? c : r;
        } else {
            start = rowOrCol.horizontal ? c - numberToClose.number + 1 : r - numberToClose.number + 1;
        }
        return start;
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

    public void closeAllTheGapsIfAllFullMarked(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            var sumOfNumbers = rowOrCol.numbersToFind.stream().map(n -> n.number).reduce(0, Integer::sum);
            var countOfFullFields = rowSelector.countOfFields(puzzle, rowOrCol, FieldState.FULL);
            if (sumOfNumbers == countOfFullFields) {
                List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
                for (Gap gap : gaps) {
                    gapCloser.close(gap, puzzle, changes);
                }
            }
        }
    }
    /*
      ex
        x  x  .  .  x  x  x  x  .  ■  ■  ■  ■  ■  .| 2 5
        to
        x  x  .  .  x  x  x  x  x  ■  ■  ■  ■  ■  x| 2 5
       AND...
        x  x  .  .  x  x  x  x  .  ■  ■  ■  ■  ■  .  .  ■  ■  ■  ■  ■  .| 2 5 5
        to
        x  x  .  .  x  x  x  x  x  ■  ■  ■  ■  ■  x  x  ■  ■  ■  ■  ■  x| 2 5 5
     */

    public void fitTheNumbersInOnlyPossibleGaps(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            var notClosedGaps = gapFinder.find(puzzle, rowOrCol).stream().filter(g -> g.assignedNumber.isEmpty()).toList();
            var biggestNumbers = numberSelector.getBiggestNotFound(rowOrCol.numbersToFind);
            if (!biggestNumbers.isEmpty()) {
                var numberToFind = biggestNumbers.get(0);
                var gapsTheAreCapableToFit = notClosedGaps.stream().filter(g -> g.length >= numberToFind.number).toList();
                if (gapsTheAreCapableToFit.size() == 1) {
                    var gap = gapsTheAreCapableToFit.get(0);
                    var gapWithMaxSubsequentFullFields = gapFinder.maxSubsequentCountOfFields(puzzle, rowOrCol, gap, FieldState.FULL);
                    if (gapWithMaxSubsequentFullFields.length == numberToFind.number) {
                        var fakeGap = new Gap(rowOrCol, gapWithMaxSubsequentFullFields.start, gapWithMaxSubsequentFullFields.end,
                            numberToFind.number, Optional.of(numberToFind));
                        if (biggestNumbers.size() == 1) {
                            gapFiller.fillTheGapEntirely(fakeGap, numberToFind, rowOrCol, puzzle, changes);
                        } else if (biggestNumbers.size() == 2) {
                            if (gapWithMaxSubsequentFullFields.start - gap.start <= numberToFind.number) {
                                gapFiller.fillTheGapEntirely(fakeGap, biggestNumbers.get(0), rowOrCol, puzzle, changes);
                            } else if (gap.end - gapWithMaxSubsequentFullFields.end <= numberToFind.number) {
                                gapFiller.fillTheGapEntirely(fakeGap, biggestNumbers.get(1), rowOrCol, puzzle, changes);
                            }
                        }
                    } else {
                        if (getNotFound(rowOrCol.numbersToFind).size() == 1) {
                            gapFiller.fillTheGapPartiallyForSingleNumber(gap, numberToFind, rowOrCol, puzzle, changes);
                        }
                    }
                }
            }
        }
    }

    private List<NumberToFind> getNotFound(List<NumberToFind> numbers) {
        return numbers.stream()
            .filter(n -> !n.found)
            .toList();
    }


    public void closeTheOnlyCombination(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            var sumOfNumbers = rowOrCol.numbersToFind.stream().map(n -> n.number).reduce(0, Integer::sum);
            var countOfNumbers = rowOrCol.numbersToFind.size();
            var limit = rowOrCol.horizontal ? puzzle.width : puzzle.height;
            if (sumOfNumbers + countOfNumbers - 1 == limit) {
                gapFiller.fillTheGapEntirelyWithNumbers(puzzle, changes, rowOrCol, rowOrCol.numbersToFind, 0);
            }
        }
    }

    /*
      ex
        .  .  ■  .  .  ■  ■  .  .  ■  ■  ■  ■  ■  .| 5 5
        to
        .  .  ■  ■  ■  ■  ■  .  .  ■  ■  ■  ■  ■  .| 5 5
        AND

        .  .  ■  ■  ■  ■  ■  .  .  ■  ■  .  .  ■  .| 5 5
        to
        .  .  ■  ■  ■  ■  ■  .  .  ■  ■  ■  ■  ■  .| 5 5
     */
    public void fillTheNumbersWithStartAndEndNotConnected(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            var firstNotFound = numberSelector.getFirstNotFound(rowOrCol.numbersToFind);
            if (firstNotFound.isPresent()) {
                var gap = gapFinder.findFirstWithoutNumberAssigned(puzzle, rowOrCol).get();
                if (gap.filledSubGaps.size() >= 2) {
                    var number = firstNotFound.get();
                    var firstSubGap = gap.filledSubGaps.get(0);
                    var secondSubGap = gap.filledSubGaps.get(1);
                    if (number.number >= secondSubGap.start - gap.start) {
                        for (int i = firstSubGap.end + 1; i < secondSubGap.start; i++) {
                            gapFiller.fillSingleField(rowOrCol, puzzle, changes, i, FieldState.FULL);
                        }
                    }
                }
            }
            var lastNotFound = numberSelector.getLastNotFound(rowOrCol.numbersToFind);
            if (lastNotFound.isPresent()) {
                var gap = gapFinder.findLastWithoutNumberAssigned(puzzle, rowOrCol).get();
                if (gap.filledSubGaps.size() >= 2) {
                    var number = lastNotFound.get();
                    var lastSubGap = gap.filledSubGaps.get(gap.filledSubGaps.size() - 1);
                    var lastButOneSubGap = gap.filledSubGaps.get(gap.filledSubGaps.size() - 2);
                    if (number.number >= gap.end - lastButOneSubGap.end) {
                        for (int i = lastButOneSubGap.end + 1; i < lastSubGap.start; i++) {
                            gapFiller.fillSingleField(rowOrCol, puzzle, changes, i, FieldState.FULL);
                        }
                    }
                }
            }
        }
    }

    /*
     ex
        16|  .  .  .  .  .  .  .  .  .  .  .  .  ■  ■  .  .  .  .  .  .| 2 2 2 2 1
        to
        16|  .  .  .  .  .  .  .  .  .  .  .  x  ■  ■  x  .  .  .  .  .| 2 2 2 2 1
     */
    public void markEndingsOfSubGapWhenThereIsNoBiggerNumber(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            var gaps = gapFinder.find(puzzle, rowOrCol);
            var biggestNumber = rowOrCol.numbersToFind.stream().map(n -> n.number).max(Integer::compareTo).get();
            for (Gap gap : gaps) {
                for (SubGap filledSubGap : gap.filledSubGaps) {
                    if (filledSubGap.length == biggestNumber) {
                        gapFiller.fillSingleField(rowOrCol, puzzle, changes, filledSubGap.start - 1, FieldState.EMPTY);
                        gapFiller.fillSingleField(rowOrCol, puzzle, changes, filledSubGap.end + 1, FieldState.EMPTY);
                    }
                }
            }
        }
    }
}

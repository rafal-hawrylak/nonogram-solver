package org.hawrylak.puzzle.nonogram.solver;

import static org.hawrylak.puzzle.nonogram.solver.utils.Utils.getStart;

import java.util.ArrayList;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector.NumberBeforeCurrentAndAfter;
import org.hawrylak.puzzle.nonogram.model.FieldState;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFiller;
import org.hawrylak.puzzle.nonogram.solver.utils.OnlyPossibleCombinationGapMode;

/*
  ex
    .  .  .  .  ^  .  .  .  .|
    .  .  .  <  x  >  .  .  .|
    .  .  .  .  v  .  .  .  .|
 */
@AllArgsConstructor
public class CloseWithOneEnd extends Solver {

    private GapFinder gapFinder;
    private NumberSelector numberSelector;
    private GapFiller gapFiller;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
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
        if (rowOrCol.solved) {
            return;
        }
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
                gapFiller.fillTheNumberAtPosition(rowOrCol, numberToClose.get(), c, r, startingFrom, puzzle, changes);
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
            var lastNumber = nextGap.isEmpty() ? numberSelector.getLast(rowOrCol.numbersToFind).get()
                : numberSelector.getPrevious(rowOrCol.numbersToFind, nextGap.get().assignedNumber.get()).get();
            var lastButOneNumber = numberSelector.getPrevious(rowOrCol.numbersToFind, lastNumber);
            if (lastButOneNumber.isEmpty() || lastButOneNumber.get().found
                || lastButOneNumber.get().number + lastNumber.number + 1 > gapAtPosition.length) {
                gapFiller.fillTheNumberAtPosition(rowOrCol, lastNumber, c, r, startingFrom, puzzle, changes);
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
            var numberToClose = nextGap.isEmpty() ? numberSelector.getLast(rowOrCol.numbersToFind)
                : numberSelector.getPrevious(rowOrCol.numbersToFind, nextGap.get().assignedNumber.get());
            if (numberToClose.isPresent()) {
                gapFiller.fillTheNumberAtPosition(rowOrCol, numberToClose.get(), c, r, startingFrom, puzzle, changes);
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
            var firstNumber = previousGap.isEmpty() ? rowOrCol.numbersToFind.get(0)
                : numberSelector.getNext(rowOrCol.numbersToFind, previousGap.get().assignedNumber.get()).get();
            var secondNumber = numberSelector.getNext(rowOrCol.numbersToFind, firstNumber);
            if (secondNumber.isEmpty() || secondNumber.get().found
                || secondNumber.get().number + firstNumber.number + 1 > gapAtPosition.length) {
                gapFiller.fillTheNumberAtPosition(rowOrCol, firstNumber, c, r, startingFrom, puzzle, changes);
                fillingSuccessful = true;
            }
        }

        var subGap = startingFrom ? gapAtPosition.getFirstSubGap().get() : gapAtPosition.getLastSubGap().get();
        var allNotFoundNumbers = numberSelector.getNotFound(rowOrCol.numbersToFind);
        var numberCandidates = numberSelector.between(allNotFoundNumbers, subGap.length, gapAtPosition.length);
        if (!fillingSuccessful) {
            /*
              this is the only not found number
                 3|  .  .  .  x  ?  .  x  x  x  x  x  x| 1
                 3|  ■  x  ■  x  ?  .  x  x  x  x  x  x| 1 1 1
             */
            if (numberCandidates.size() == 1) {
                var numberToClose = numberCandidates.get(0);
                gapFiller.fillTheNumberAtPosition(rowOrCol, numberToClose, c, r, startingFrom, puzzle, changes);
                fillingSuccessful = true;
            }
        }
        if (!fillingSuccessful) {
            /*
              gap partially filled with the biggest number
                 3|  .  .  .  x  ?  ■  ■  ■  .  .  .  .  .  x| 3 7 1
             */
            var biggestNumbers = numberSelector.getBiggestNotFound(rowOrCol.numbersToFind);
            var biggestNumber = biggestNumbers.get(0);
            var secondBiggestNumbers = numberSelector.getSecondBiggestNotFound(rowOrCol.numbersToFind);
            var start = getStart(rowOrCol, biggestNumber.number, c, r, startingFrom);
            var fakeGapBiggestUnknown = new Gap(rowOrCol, start, start + biggestNumber.number - 1, biggestNumber.number, Optional.empty());
            var fakeGapBiggestKnown = new Gap(rowOrCol, start, start + biggestNumber.number - 1, biggestNumber.number,
                Optional.of(biggestNumber));
            var subGapAtPosition = gapFinder.getSubGapAtPosition(gapAtPosition.filledSubGaps, rowOrCol.horizontal, c, r);
            if (secondBiggestNumbers.isEmpty() || (subGapAtPosition.isPresent() && subGapAtPosition.get().length > secondBiggestNumbers.get(
                0).number)) {
                if (biggestNumbers.size() == 1) {
                    gapFiller.fillTheGapEntirely(fakeGapBiggestKnown, biggestNumber, rowOrCol, puzzle, changes);
                    fillingSuccessful = true;
                } else {
                    gapFiller.fillTheGap(fakeGapBiggestUnknown, rowOrCol, puzzle, changes);
                    fillingSuccessful = true;
                }
            }
        }
        if (!fillingSuccessful) {
            // TODO only if (startingFrom) implemented
            /*
              the x  ■ can be expanded to x  ■  ■  ■  ■ ... if 4 is the minimal value
                                    or to x  ■  ■  ■  ■  ■ ... if x  ■  .  ■  ■  ■ must be merged to x  ■  ■  ■  ■  ■
                17|  .  .  .  .  .  ■  .  x  .  .  ■  ■  x  ■  .  ■  ■  ■  .  .  .  ■  .  .  .| 1 4 4 5 4
                17|  .  .  .  .  .  ■  .  x  .  .  ■  ■  x  ■  ■  ■  ■  ■  x  .  .  ■  .  .  .| 1 4 4 5 4
             */
            if (startingFrom) {
                var allPreviousNotUsedGaps = gapFinder.allPrevious(gaps, gapAtPosition).stream().filter(g -> g.assignedNumber.isEmpty())
                    .toList();
                var allSubGaps = gapFinder.allSubGaps(allPreviousNotUsedGaps);
                if (!allSubGaps.isEmpty()) {
                    var subGapIndex = 0;
                    var numberIndex = 0;
                    for (; numberIndex < allNotFoundNumbers.size(); ) {
                        var notFoundNumber = allNotFoundNumbers.get(numberIndex);
                        var wouldBeSatisfiedBySubGaps = false;
                        var currentSubGapIndex = subGapIndex;
                        var firstSubGap = allSubGaps.get(subGapIndex);
                        for (; currentSubGapIndex < allSubGaps.size(); currentSubGapIndex++) {
                            var currentSubGap = allSubGaps.get(currentSubGapIndex);
                            if (currentSubGapIndex == subGapIndex) {
                                if (currentSubGap.length >= notFoundNumber.number) {
                                    numberIndex++;
                                    subGapIndex++;
                                    wouldBeSatisfiedBySubGaps = true;
                                    break;
                                }
                            } else {
                                if (currentSubGap.end - firstSubGap.start + 1 > notFoundNumber.number) {
                                    subGapIndex = currentSubGapIndex;
                                    numberIndex++;
                                    wouldBeSatisfiedBySubGaps = true;
                                    break;
                                }
                            }
                        }
                        if (subGapIndex >= allSubGaps.size()) {
                            break;
                        }
                        if (!wouldBeSatisfiedBySubGaps) {
                            numberIndex++;
                            break;
                        }
                    }
                    var allNotSatisfiedNumbers = allNotFoundNumbers.subList(numberIndex, allNotFoundNumbers.size());
                    var minimalNotSatisfied = allNotSatisfiedNumbers.stream().map(n -> n.number).min(Integer::compareTo).get();
                    var start = getStart(rowOrCol, minimalNotSatisfied, c, r, startingFrom);
                    var fakeGap = new Gap(rowOrCol, start, start + minimalNotSatisfied - 1, minimalNotSatisfied, Optional.empty());
                    gapFiller.fillTheGap(fakeGap, rowOrCol, puzzle, changes);
                    fillingSuccessful = true;
                }
            } else {

            }
        }
        if (!fillingSuccessful) {
            /*
                     0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19
                23|  .  .  .  ■  .  .  x  ■  .  x  .  .  .  ■  .  .  x  .  .  .| 2 1 2 2 2 2
                                          ^ - this can't be "1" because "2 2 2 2" is not possible on the right side
                27|  .  .  .  .  .  .  x  ■  .  .  .  .  .  .  .  .  x  .  .  .| 1 1 1 1 2 1 1
                                          ^ - this can't be "2" because "1 1 1 1" is ont possible on the left side
             */
            var notFoundNumberValues = numberCandidates.stream().map(n -> n.number).distinct().toList();
            var allPossibleSplitsAtNumber = new ArrayList<NumberBeforeCurrentAndAfter>();
            for (var number : notFoundNumberValues) {
                allPossibleSplitsAtNumber.addAll(numberSelector.getAllPossibleSplitsAtNumber(rowOrCol.numbersToFind, number));
            }
            var gapMode = OnlyPossibleCombinationGapMode.builder().enabled(true).startingFrom(startingFrom).endingAt(!startingFrom).build();
            fillingSuccessful = gapFiller.findAndMarkTheOnlyPossibleCombinationForNumbers(puzzle, changes, rowOrCol, gaps, gapAtPosition,
                allPossibleSplitsAtNumber, gapMode);
        }
        if (!fillingSuccessful) {
            /*
                     0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24
                 2|  ■  ■  x  .  x  ■  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .| 2 1 1 1 1 2
                 2|  ■  ■  x  .  x  ■  x  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .| 2 1 1 1 1 2
                                    ^ - only "1" possible as other bigger number is "2" and "1 1 1 1" would not fit into previous gap
             */
            var smallest = numberSelector.smallest(numberCandidates).get().number;
            var allBigger = numberSelector.getAllBigger(numberCandidates, smallest);
            Gap fakeGap;
            if (startingFrom) {
                fakeGap = new Gap(rowOrCol, gapAtPosition.start, gapAtPosition.start + smallest - 1, smallest, Optional.empty());
            } else {
                fakeGap = new Gap(rowOrCol, gapAtPosition.end - smallest + 1, gapAtPosition.end, smallest, Optional.empty());
            }
            if (allBigger.isEmpty()) {
                gapFiller.fillTheGap(fakeGap, rowOrCol, puzzle, changes);
                fillingSuccessful = true;
            } else {
                var allPossibleSplitsAtNumber = new ArrayList<NumberBeforeCurrentAndAfter>();
                for (var number : numberCandidates) {
                    allPossibleSplitsAtNumber.addAll(numberSelector.getAllPossibleSplitsAtNumber(rowOrCol.numbersToFind, number.number));
                }
                var gapMode = OnlyPossibleCombinationGapMode.builder().enabled(true).startingFrom(startingFrom).endingAt(!startingFrom)
                    .build();
                fillingSuccessful = gapFiller.findAndMarkTheOnlyPossibleCombinationForNumbers(puzzle, changes, rowOrCol, gaps,
                    gapAtPosition, allPossibleSplitsAtNumber, gapMode);
                if (!fillingSuccessful) {
                    fillingSuccessful = gapFiller.fillTheGap(fakeGap, rowOrCol, puzzle, changes);
                }
            }
        }
    }
}

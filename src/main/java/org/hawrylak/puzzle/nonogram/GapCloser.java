package org.hawrylak.puzzle.nonogram;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GapCloser {

    private final FieldFinder fieldFinder;
    private final GapFinder gapFinder;
    private final GapFiller gapFiller;

    private final NumberSelector numberSelector;

    public void closeTooSmallToFitAnything(Puzzle puzzle, ChangedInIteration changesLast) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            closeTooSmallToFitAnything(puzzle, changesLast, rowOrCol);
        }
    }

    public void closeWhenAllNumbersAreFound(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            if (rowOrCol.numbersToFind.stream().allMatch(n -> n.found)) {
                List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
                for (Gap gap : gaps) {
                    closeAsEmpty(gap, puzzle, changes);
                }
            }
        }
    }

    /*
      ex
        x  x  ■  x  .  .  .  .  . | 1 5
        to
        x  x  ■  x  ■  ■  ■  ■  ■ | 1 5

      and

        x  x  ■  x  .  .  .  .  . | 1 4
        to
        x  x  ■  x  .  ■  ■  ■  . | 1 4

      and

        x  x  ■  x  .  .  .  .  .  . | 1 2 2
        to
        x  x  ■  x  .  ■  .  .  ■  . | 1 2 2
     */
    public void closeWhenSingleGapWithNumbersNotFound(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
            var gapsWithoutFoundNumbers = gaps.stream().filter(gap -> gap.assignedNumber.isEmpty()).toList();
            if (gapsWithoutFoundNumbers.size() == 1) {
                var gap = gapsWithoutFoundNumbers.get(0);
                var numbersNotFound = rowOrCol.numbersToFind.stream().filter(n -> !n.found).toList();
                var numbersSum = numbersNotFound.stream().map(n -> n.number).reduce(0, Integer::sum);
                if (numbersSum + numbersNotFound.size() - 1 == gap.length) {
                    gapFiller.fillTheGapEntirelyWithNumbers(puzzle, changes, rowOrCol, numbersNotFound, gap.start);
                } else {
                    var gapDiff = gap.length - numbersSum - numbersNotFound.size() + 1;
                    gapFiller.fillTheGapPartiallyForNNumbers(gap, numbersNotFound, gapDiff, rowOrCol, puzzle, changes);
                }
            }
        }
    }

    private void closeTooSmallToFitAnything(Puzzle puzzle, ChangedInIteration changes,
        RowOrCol rowOrCol) {
        List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
        var min = rowOrCol.numbersToFind.stream().filter(n -> !n.found).map(n -> n.number).min(Integer::compareTo);
        for (Gap gap : gaps) {
            if (min.isEmpty() || gap.length < min.get()) {
                closeAsEmpty(gap, puzzle, changes);
            }
        }
    }

    public void closeAsEmpty(Gap gap, Puzzle puzzle, ChangedInIteration changes) {
        for (int i = gap.start; i <= gap.end; i++) {
            if (fieldFinder.isFieldAtState(gap.rowOrCol, puzzle, i, FieldState.UNKNOWN)) {
                gapFiller.fillSingleField(gap.rowOrCol, puzzle, changes, i, FieldState.EMPTY);
            }
        }
    }

    public void close(Gap gap, Puzzle puzzle, ChangedInIteration changes) {
        if (fieldFinder.isFieldAtState(gap.rowOrCol, puzzle, gap.start, FieldState.UNKNOWN)) {
            closeAsEmpty(gap, puzzle, changes);
        } else if (fieldFinder.isFieldAtState(gap.rowOrCol, puzzle, gap.start, FieldState.FULL)) {
            var numberToClose = numberSelector.getForPositionAssumingAllFullInTheRowOrColFilled(gap.rowOrCol, puzzle, gap.start, gap.end);
            if (numberToClose.isPresent()) {
                gapFiller.fillTheGapEntirely(gap, numberToClose.get(), gap.rowOrCol, puzzle, changes);
            }
        }
    }

    /*
      ex
        .  x  .  x  ■  ■  ■  x  .  . | 3 1
        to
        x  x  x  x  ■  ■  ■  x  .  . | 3 1

      and

        .  .  x  ■  ■  ■  x  .  x  . | 1 3
        to
        .  .  x  ■  ■  ■  x  x  x  x | 1 3
     */
    public void closeAllGapsBeforeFirstAndAfterLastFoundNumber(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            if (rowOrCol.numbersToFind.isEmpty()) {
                continue;
            }
            var gaps = gapFinder.find(puzzle, rowOrCol);
            var firstNumber = rowOrCol.numbersToFind.get(0);
            if (firstNumber.found) {
                var gapsBeforeNumber = gapFinder.gapsBeforeNumber(gaps, firstNumber);
                for (Gap gap : gapsBeforeNumber) {
                    closeAsEmpty(gap, puzzle, changes);
                }
            }
            var lastNumber = rowOrCol.numbersToFind.get(rowOrCol.numbersToFind.size() - 1);
            if (lastNumber.found) {
                var gapsAfterNumber = gapFinder.gapsAfterNumber(gaps, lastNumber);
                for (Gap gap : gapsAfterNumber) {
                    closeAsEmpty(gap, puzzle, changes);
                }
            }
        }
    }
}

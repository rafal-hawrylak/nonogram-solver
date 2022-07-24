package org.hawrylak.puzzle.nonogram;

import java.util.List;
import java.util.Optional;
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

    private void closeTooSmallToFitAnything(Puzzle puzzle, ChangedInIteration changes, RowOrCol rowOrCol) {
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

    /*
  ex
    x  x  .  .  ■  ■  ■  .  .  .  .  .  .  .  . | 4 1 1
    to
    x  x  x  .  ■  ■  ■  .  .  .  .  .  .  .  . | 4 1 1

  and

    x  x  .  .  .  .  .  .  .  ■  ■  ■  .  .  . | 1 1 4
    to
    x  x  .  .  .  .  .  .  .  ■  ■  ■  .  x  x | 1 1 4
 */
    public void narrowGapsBeforeFirstAndAfterLast(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            var firstNotFound = numberSelector.getFirstNotFound(rowOrCol.numbersToFind);
            if (firstNotFound.isPresent()) {
                var number = firstNotFound.get();
                var gap = gapFinder.findFirstWithoutNumberAssigned(puzzle, rowOrCol).get();
                if (!gap.filledSubGaps.isEmpty()) {
                    var firstSubGap = gap.filledSubGaps.get(0);
                    var missingNumberPart = number.number - firstSubGap.length;
                    if (missingNumberPart >= 0) {
                        if (firstSubGap.start - gap.start > missingNumberPart) {
                            var nextNumber = numberSelector.getNext(rowOrCol.numbersToFind, number);
                            if (nextNumber.isEmpty() || nextNumber.get().found || (firstSubGap.start - gap.start <= number.number
                                && firstSubGap.end - gap.start >= number.number)) {
                                var fakeGap = new Gap(rowOrCol, gap.start, firstSubGap.start - missingNumberPart - 1,
                                    firstSubGap.start - missingNumberPart - gap.start, Optional.empty());
                                closeAsEmpty(fakeGap, puzzle, changes);
                            }
                        }
                    } else {
                        // firstSubGap is not for firstNotFound but for some next
                    }
                }
            }
            /*
                missingNumberPart = 3
                number = 9
                     0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19
                 6|  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  .  .  .  .  .  .  .| 2 2 3 9  g.e = 19  lSG.e = 9
                 6|  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  .  .  .  .  .  .  .| 2 2 3 9  g.e - lSG.e = 10  ### NOT OK

                 6|  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  .  .  .  .  .  .| 2 2 3 9  g.e = 19  lSG.e = 10
                 6|  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  x  x  x  x  x  x| 2 2 3 9  g.e - lSG.e = 9  ### OK

                 6|  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  .  .  .  .  .| 2 2 3 9  g.e = 19  lSG.e = 11
                 6|  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  x  x  x  x  x| 2 2 3 9  g.e - lSG.e = 8  ### OK

                 6|  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  .  .  .  .| 2 2 3 9
                 6|  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  x  x  x  x| 2 2 3 9

                 6|  .  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  .  .  .| 2 2 3 9
                 6|  .  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  x  x  x| 2 2 3 9

                 6|  .  .  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  .  .| 2 2 3 9
                 6|  .  .  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  x  x| 2 2 3 9

                 6|  .  .  .  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  .| 2 2 3 9  g.e = 19  lSG.s = 10
                 6|  .  .  .  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .  x| 2 2 3 9  g.e - lSG.s = 9  ### OK

                 6|  .  .  .  .  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .| 2 2 3 9  g.e = 19  lSG.s = 11
                 6|  .  .  .  .  .  .  .  .  .  .  .  ■  ■  ■  ■  ■  ■  .  .  .| 2 2 3 9  g.e - lSG.s = 8  ### NOT OK
             */
            var lastNotFound = numberSelector.getLastNotFound(rowOrCol.numbersToFind);
            if (lastNotFound.isPresent()) {
                var number = lastNotFound.get();
                var gap = gapFinder.findLastWithoutNumberAssigned(puzzle, rowOrCol).get();
                if (!gap.filledSubGaps.isEmpty()) {
                    var lastSubGap = gap.filledSubGaps.get(gap.filledSubGaps.size() - 1);
                    var missingNumberPart = number.number - lastSubGap.length;
                    if (missingNumberPart >= 0) {
                        if (gap.end - lastSubGap.end > missingNumberPart) {
                            var prevNumber = numberSelector.getPrevious(rowOrCol.numbersToFind, number);
                            if (prevNumber.isEmpty() || prevNumber.get().found || (gap.end - lastSubGap.end <= number.number
                                && gap.end - lastSubGap.start >= number.number)) {
                                var fakeGap = new Gap(rowOrCol, lastSubGap.end + missingNumberPart + 1, gap.end,
                                    gap.end - lastSubGap.end - missingNumberPart, Optional.empty());
                                closeAsEmpty(fakeGap, puzzle, changes);
                            }
                        }
                    } else {
                        // lastSubGap is not for lastNotFound but for some next
                    }
                }
            }
        }
    }

    public void findUnmergableSubGaps(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            var biggestNotFound = numberSelector.getBiggestNotFound(rowOrCol.numbersToFind);
            if (biggestNotFound.isEmpty()) {
                continue;
            }
            var biggest = biggestNotFound.get(0).number;
            var gaps = gapFinder.find(puzzle, rowOrCol);
            for (Gap gap : gaps) {
                for (int i = 0; i < gap.filledSubGaps.size() - 1; i++) {
                    var subGap = gap.filledSubGaps.get(i);
                    var nextSubGap = gap.filledSubGaps.get(i + 1);
                    var onlySingleFieldBetweenSubGaps = subGap.end + 2 == nextSubGap.start;
                    var sizeAfterMerging = subGap.length + nextSubGap.length + 1;
                    if (onlySingleFieldBetweenSubGaps && sizeAfterMerging > biggest) {
                        var fakeGap = new Gap(rowOrCol, subGap.end + 1, subGap.end + 1, 1, Optional.empty());
                        closeAsEmpty(fakeGap, puzzle, changes);
                    }
                }
            }

        }
    }
}

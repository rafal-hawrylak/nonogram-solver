package org.hawrylak.puzzle.nonogram;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GapCloser {

    private final GapFinder gapFinder;
    private final GapFiller gapFiller;

    private final NumberSelector numberSelector;

    public void closeTooSmallToFitAnything(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            closeTooSmallToFitAnything(puzzle, changesLast, changesCurrent, rowOrCol);
        }
    }

    public void closeWhenAllNumbersAreFound(Puzzle puzzle, ChangedInIteration changesCurrent) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            if (rowOrCol.numbersToFind.stream().allMatch(n -> n.found)) {
                List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
                for (Gap gap : gaps) {
                    closeAsEmpty(gap, puzzle, changesCurrent);
                }
            }
        }
    }

    public void closeWhenSingleGapWithNumbersNotFound(Puzzle puzzle, ChangedInIteration changesCurrent) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
            var gapsWithoutFoundNumbers = gaps.stream().filter(gap -> gap.assignedNumber.isEmpty()).toList();
            if (gapsWithoutFoundNumbers.size() == 1) {
                var gap = gapsWithoutFoundNumbers.get(0);
                var numbersNotFound = rowOrCol.numbersToFind.stream().filter(n -> !n.found).toList();
                var numbersSum = numbersNotFound.stream().map(n -> n.number).reduce(0, Integer::sum);
                if (numbersSum + numbersNotFound.size() - 1 == gap.length) {
                    gapFiller.fillTheGapEntirelyWithNumbers(puzzle, changesCurrent, rowOrCol, numbersNotFound, gap.start);
                }

                // TODO single number - partial fill

                // TODO two numbers - partial fill
                else if (numbersNotFound.size() == 2) {
                    if (numbersSum + numbersNotFound.size() == gap.length) {
                        gapFiller.fillTheGapPartiallyForTwoNumbers(gap, numbersNotFound.get(0), numbersNotFound.get(1), rowOrCol, puzzle,
                            changesCurrent);
                    }
                }
            }
        }
    }

    private void closeTooSmallToFitAnything(Puzzle puzzle, ChangedInIteration changesLast, ChangedInIteration changesCurrent,
        RowOrCol rowOrCol) {
        List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
        var min = rowOrCol.numbersToFind.stream().filter(n -> !n.found)
            .map(n -> n.number).min(Integer::compareTo);
        for (Gap gap : gaps) {
            if (min.isEmpty() || gap.length < min.get()) {
                closeAsEmpty(gap, puzzle, changesCurrent);
            }
        }
    }

    public void closeAsEmpty(Gap gap, Puzzle puzzle, ChangedInIteration changes) {
        for (int i = gap.start; i <= gap.end; i++) {
            if (gapFiller.isFieldAtState(gap.rowOrCol, puzzle, i, FieldState.UNKNOWN)) {
                gapFiller.fillSingleField(gap.rowOrCol, puzzle, changes, i, FieldState.EMPTY);
            }
        }
    }

    public void close(Gap gap, Puzzle puzzle, ChangedInIteration changes) {
        if (gapFiller.isFieldAtState(gap.rowOrCol, puzzle, gap.start, FieldState.UNKNOWN)) {
            closeAsEmpty(gap, puzzle, changes);
        } else if (gapFiller.isFieldAtState(gap.rowOrCol, puzzle, gap.start, FieldState.FULL)) {
            var numberToClose = numberSelector.getForPositionAssumingAllFullInTheRowOrColFilled(gap.rowOrCol, puzzle, gap.start, gap.end);
            if (numberToClose.isPresent()) {
                gapFiller.fillTheGapEntirely(gap, numberToClose.get(), gap.rowOrCol, puzzle, changes);
            }
        }
    }
}

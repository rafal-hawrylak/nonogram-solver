package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.NumberToFind;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapCloser;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFiller;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;

import java.util.List;

import static org.hawrylak.puzzle.nonogram.solver.utils.Utils.remove;

@AllArgsConstructor
public class OnlyFirstLastNumberMatchesFirstLastSubGap extends Solver {

    private GapFinder gapFinder;
    private NumberSelector numberSelector;
    private GapFiller gapFiller;
    private GapCloser gapCloser;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var numbers = numberSelector.getNotFound(rowOrCol.numbersToFind);
            var firstGap = gapFinder.findFirstWithoutNumberAssigned(puzzle, rowOrCol);
            if (firstGap.isPresent()) {
                var subGaps = firstGap.get().filledSubGaps;
                if (subGaps.size() == 1) {
                    var subGap = subGaps.get(0);
                    var firstNumber = numberSelector.getFirst(numbers);
                    var numbersMatching = numberSelector.findNumbersMatchingSubGap(subGap, numbers);
                    if (firstNumber.isEmpty() || !numbersMatching.contains(firstNumber.get())) {
                        continue;
                    }
                    numbersMatching = remove(numbersMatching, firstNumber.get());
                    boolean possibleToFitForAnyNumber = false;
                    for (NumberToFind number : numbersMatching) {
                        var numberToFit = numberSelector.allPrevious(numbers, number);
                        var fakeGap = new Gap(rowOrCol, firstGap.get().start,subGap.start - 2, subGap.start - firstGap.get().start - 1);
                        boolean fit = gapFiller.doAllNumbersFitInGaps(numberToFit, List.of(fakeGap));
                        if (fit) {
                            possibleToFitForAnyNumber = true;
                            break;
                        }
                    }
                    if (!possibleToFitForAnyNumber) {
                        var missingNumberPart = firstNumber.get().number - subGap.length;
                        int length = subGap.start - firstGap.get().start - missingNumberPart;
                        if (length > 0) {
                            var fakeGap = new Gap(rowOrCol, firstGap.get().start, subGap.start - missingNumberPart - 1, length);
                            gapCloser.closeAsEmpty(fakeGap, puzzle, changes);
                        }
                    }
                }
            }

            var lastGap = gapFinder.findLastWithoutNumberAssigned(puzzle, rowOrCol);
            if (lastGap.isPresent()) {
                var subGaps = lastGap.get().filledSubGaps;
                if (subGaps.size() == 1) {
                    var subGap = subGaps.get(0);
                    var lastNumber = numberSelector.getLast(numbers);
                    var numbersMatching = numberSelector.findNumbersMatchingSubGap(subGap, numbers);
                    if (lastNumber.isEmpty() || !numbersMatching.contains(lastNumber.get())) {
                        continue;
                    }
                    numbersMatching = remove(numbersMatching, lastNumber.get());
                    boolean possibleToFitForAnyNumber = false;
                    for (NumberToFind number : numbersMatching) {
                        var numberToFit = numberSelector.allNext(numbers, number);
                        var fakeGap = new Gap(rowOrCol, subGap.end + 2, lastGap.get().end, lastGap.get().end - subGap.end - 1);
                        boolean fit = gapFiller.doAllNumbersFitInGaps(numberToFit, List.of(fakeGap));
                        if (fit) {
                            possibleToFitForAnyNumber = true;
                            break;
                        }
                    }
                    if (!possibleToFitForAnyNumber) {
                        var missingNumberPart = lastNumber.get().number - subGap.length;
                        int length = lastGap.get().end - subGap.end - missingNumberPart;
                        if (length > 0) {
                            var fakeGap = new Gap(rowOrCol, subGap.end + missingNumberPart + 1, lastGap.get().end, length);
                            gapCloser.closeAsEmpty(fakeGap, puzzle, changes);
                        }
                    }
                }
            }
        }
    }
}

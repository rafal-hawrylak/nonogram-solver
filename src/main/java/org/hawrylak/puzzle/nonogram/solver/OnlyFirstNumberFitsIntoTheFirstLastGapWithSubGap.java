package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFiller;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;

import java.util.List;

@AllArgsConstructor
public class OnlyFirstNumberFitsIntoTheFirstLastGapWithSubGap extends Solver {

    private GapFinder gapFinder;
    private NumberSelector numberSelector;
    private GapFiller gapFiller;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var gaps = gapFinder.findWithoutAssignedNumber(puzzle, rowOrCol);
            if (gaps.size() >= 2) {
                var firstGap = gaps.get(0);
                if (!firstGap.filledSubGaps.isEmpty()) {
                    var numbers = numberSelector.getNotFound(rowOrCol.numbersToFind);
                    if (!numbers.isEmpty()) {
                        var firstNumber = numbers.get(0);
                        if (numbers.size() == 1) {
                            if (firstGap.filledSubGaps.size() == 2) {
                                gapFiller.mergeSubGaps(puzzle, changes, rowOrCol, firstGap, numbers.get(0), true);
                            } else if (firstGap.filledSubGaps.size() == 1) {
                                gapFiller.fillTheGapPartiallyForSingleNumberWithEdges(firstGap, firstNumber, rowOrCol, puzzle, changes);
                            }
                        } else {
                            var secondNumber = numbers.get(1);
                            if (!gapFiller.doAllNumbersFitInGaps(List.of(firstNumber, secondNumber), List.of(firstGap))) {
                                gapFiller.fillTheGapPartiallyForSingleNumberWithEdges(firstGap, firstNumber, rowOrCol, puzzle, changes);
                            }
                        }
                    }
                }
            }
        }
    }
}

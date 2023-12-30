package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFiller;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;

import java.util.List;

@AllArgsConstructor
public class ThreeGapsMatchOnlyThreeNumbersEach extends Solver {

    private GapFinder gapFinder;
    private GapFiller gapFiller;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var gaps = gapFinder.find(puzzle, rowOrCol);
            if (gaps.size() == 3 && rowOrCol.numbersToFind.size() == 3) {
                var firstGap = gaps.get(0);
                var secondGap = gaps.get(1);
                var thirdGap = gaps.get(2);
                var firstNumber = rowOrCol.numbersToFind.get(0);
                var secondNumber = rowOrCol.numbersToFind.get(1);
                var thirdNumber = rowOrCol.numbersToFind.get(2);
                if (gapFiller.doAllNumbersFitInGaps(List.of(firstNumber), List.of(firstGap))
                        && gapFiller.doAllNumbersFitInGaps(List.of(secondNumber), List.of(secondGap))
                        && gapFiller.doAllNumbersFitInGaps(List.of(thirdNumber), List.of(thirdGap))) {
                    if (! gapFiller.doAllNumbersFitInGaps(List.of(firstNumber, secondNumber), List.of(firstGap))
                        && ! gapFiller.doAllNumbersFitInGaps(List.of(firstNumber, secondNumber), List.of(secondGap))
                        && ! gapFiller.doAllNumbersFitInGaps(List.of(secondNumber, thirdNumber), List.of(secondGap))
                        && ! gapFiller.doAllNumbersFitInGaps(List.of(secondNumber, thirdNumber), List.of(thirdGap))) {
                        gapFiller.fillTheGapPartiallyForSingleNumberWithEdges(firstGap, firstNumber, rowOrCol, puzzle, changes);
                        gapFiller.fillTheGapPartiallyForSingleNumberWithEdges(secondGap, secondNumber, rowOrCol, puzzle, changes);
                        gapFiller.fillTheGapPartiallyForSingleNumberWithEdges(thirdGap, thirdNumber, rowOrCol, puzzle, changes);
                    }
                }
            }
        }
    }
}

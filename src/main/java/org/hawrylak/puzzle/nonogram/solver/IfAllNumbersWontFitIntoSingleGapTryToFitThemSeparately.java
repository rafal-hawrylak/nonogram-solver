package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFiller;

@AllArgsConstructor
public class IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately extends Solver {

    private GapFinder gapFinder;
    private NumberSelector numberSelector;
    private GapFiller gapFiller;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var gaps = gapFinder.findWithoutAssignedNumber(puzzle, rowOrCol);
            var numbers = numberSelector.getNotFound(rowOrCol.numbersToFind);
            var sum = numberSelector.sum(numbers);
            if (gaps.size() == 2 && numbers.size() == 2 && sum > gaps.get(0).length && sum > gaps.get(1).length) {
                gapFiller.fillTheGapPartiallyForSingleNumber(gaps.get(0), numbers.get(0), rowOrCol, puzzle, changes);
                gapFiller.fillTheGapPartiallyForSingleNumber(gaps.get(1), numbers.get(1), rowOrCol, puzzle, changes);
            }
        }
    }
}

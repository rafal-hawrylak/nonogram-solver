package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.utils.GapCloser;

@AllArgsConstructor
public class CloseTooSmallToFitFirstOrLastNumber extends Solver {

    private GapFinder gapFinder;
    private GapCloser gapCloser;
    private NumberSelector numberSelector;


    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var firstGap = gapFinder.findFirstWithoutNumberAssigned(puzzle, rowOrCol);
            var firstNumber = numberSelector.getFirstNotFound(rowOrCol.numbersToFind);
            if (firstGap.isPresent() && firstNumber.isPresent()) {
                if (firstNumber.get().number > firstGap.get().length) {
                    gapCloser.closeAsEmpty(firstGap.get(), puzzle, changes);
                }
            }
            var lastGap = gapFinder.findLastWithoutNumberAssigned(puzzle, rowOrCol);
            var lastNumber = numberSelector.getLastNotFound(rowOrCol.numbersToFind);
            if (lastGap.isPresent() && lastNumber.isPresent()) {
                if (lastNumber.get().number > lastGap.get().length) {
                    gapCloser.closeAsEmpty(lastGap.get(), puzzle, changes);
                }
            }
        }
    }
}

package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.*;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFiller;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;

/*
 ex
    16|  .  .  .  .  .  .  .  .  .  .  .  .  ■  ■  .  .  .  .  .  .| 2 2 2 2 1
    to
    16|  .  .  .  .  .  .  .  .  .  .  .  x  ■  ■  x  .  .  .  .  .| 2 2 2 2 1
 */
@AllArgsConstructor
public class MarkEndingsOfSubGapWhenThereIsNoBiggerNumber extends Solver {

    private GapFinder gapFinder;
    private NumberSelector numberSelector;
    private GapFiller gapFiller;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var gaps = gapFinder.find(puzzle, rowOrCol);
            var biggestNumber = numberSelector.getNotFound(rowOrCol.numbersToFind).stream().map(n -> n.number).max(Integer::compareTo);
            if (biggestNumber.isEmpty()) {
                continue;
            }
            for (Gap gap : gaps) {
                for (SubGap filledSubGap : gap.filledSubGaps) {
                    if (filledSubGap.length == biggestNumber.get()) {
                        gapFiller.fillSingleField(rowOrCol, puzzle, changes, filledSubGap.start - 1, FieldState.EMPTY);
                        gapFiller.fillSingleField(rowOrCol, puzzle, changes, filledSubGap.end + 1, FieldState.EMPTY);
                    }
                }
            }
        }
    }
}

package org.hawrylak.puzzle.nonogram.solver;

import java.util.List;
import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapCloser;

@AllArgsConstructor
public class CloseWhenAllNumbersAreFound extends Solver {

    private GapFinder gapFinder;
    private GapCloser gapCloser;
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            if (rowOrCol.numbersToFind.stream().allMatch(n -> n.found)) {
                List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
                for (Gap gap : gaps) {
                    gapCloser.closeAsEmpty(gap, puzzle, changes);
                }
            }
        }
    }
}

package org.hawrylak.puzzle.nonogram.solver;

import java.util.List;
import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapCloser;

@AllArgsConstructor
public class CloseTooSmallToFitAnything extends Solver {

    private GapFinder gapFinder;
    private GapCloser gapCloser;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            closeTooSmallToFitAnything(puzzle, changes, rowOrCol);
        }
    }

    private void closeTooSmallToFitAnything(Puzzle puzzle, ChangedInIteration changes, RowOrCol rowOrCol) {
        List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
        var min = rowOrCol.numbersToFind.stream().filter(n -> !n.found).map(n -> n.number).min(Integer::compareTo);
        for (Gap gap : gaps) {
            if (min.isEmpty() || gap.length < min.get()) {
                gapCloser.closeAsEmpty(gap, puzzle, changes);
            }
        }
    }
}

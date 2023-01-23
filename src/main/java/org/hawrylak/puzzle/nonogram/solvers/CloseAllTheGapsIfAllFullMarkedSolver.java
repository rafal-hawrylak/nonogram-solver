package org.hawrylak.puzzle.nonogram.solvers;

import java.util.List;
import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.GapFinder;
import org.hawrylak.puzzle.nonogram.RowSelector;
import org.hawrylak.puzzle.nonogram.model.FieldState;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.GapCloser;

/*
  ex
    x  x  ■  ■  x  x  x  x  .  ■  ■  ■  ■  ■  .| 2 5
    to
    x  x  ■  ■  x  x  x  x  x  ■  ■  ■  ■  ■  x| 2 5

  and

    x  x  .  .  x  x  x  x  .  ■  ■  ■  ■  ■  .| 5
    to
    x  x  x  x  x  x  x  x  x  ■  ■  ■  ■  ■  x| 5
 */
@AllArgsConstructor
public class CloseAllTheGapsIfAllFullMarkedSolver implements Solver {

    private GapFinder gapFinder;
    private RowSelector rowSelector;
    private GapCloser gapCloser;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            var sumOfNumbers = rowOrCol.numbersToFind.stream().map(n -> n.number).reduce(0, Integer::sum);
            var countOfFullFields = rowSelector.countOfFields(puzzle, rowOrCol, FieldState.FULL);
            if (sumOfNumbers == countOfFullFields) {
                List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
                for (Gap gap : gaps) {
                    gapCloser.close(gap, puzzle, changes);
                }
            }
        }
    }
}

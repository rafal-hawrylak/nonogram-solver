package org.hawrylak.puzzle.nonogram.solvers;

import java.util.List;
import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.GapFinder;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.GapFiller;

/*
  ex
    x  x  ■  x  .  .  .  .  . | 1 5
    to
    x  x  ■  x  ■  ■  ■  ■  ■ | 1 5

  and

    x  x  ■  x  .  .  .  .  . | 1 4
    to
    x  x  ■  x  .  ■  ■  ■  . | 1 4

  and

    x  x  ■  x  .  .  .  .  .  . | 1 2 2
    to
    x  x  ■  x  .  ■  .  .  ■  . | 1 2 2
 */
@AllArgsConstructor
public class CloseWhenSingleGapWithNumbersNotFoundSolver implements Solver {

    private GapFinder gapFinder;
    private GapFiller gapFiller;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            List<Gap> gaps = gapFinder.find(puzzle, rowOrCol);
            var gapsWithoutFoundNumbers = gaps.stream().filter(gap -> gap.assignedNumber.isEmpty()).toList();
            if (gapsWithoutFoundNumbers.size() == 1) {
                var gap = gapsWithoutFoundNumbers.get(0);
                var numbersNotFound = rowOrCol.numbersToFind.stream().filter(n -> !n.found).toList();
                var numbersSum = numbersNotFound.stream().map(n -> n.number).reduce(0, Integer::sum);
                if (numbersSum + numbersNotFound.size() - 1 == gap.length) {
                    gapFiller.fillTheGapEntirelyWithNumbers(puzzle, changes, rowOrCol, numbersNotFound, gap.start);
                } else {
                    gapFiller.fillTheGapPartiallyForNNumbers(gap, numbersNotFound, rowOrCol, puzzle, changes);
                }
            }
        }
    }
}

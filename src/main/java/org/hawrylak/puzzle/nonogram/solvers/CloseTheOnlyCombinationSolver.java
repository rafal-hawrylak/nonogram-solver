package org.hawrylak.puzzle.nonogram.solvers;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.GapFiller;

@AllArgsConstructor
public class CloseTheOnlyCombinationSolver implements Solver {

    private GapFiller gapFiller;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            var sumOfNumbers = rowOrCol.numbersToFind.stream().map(n -> n.number).reduce(0, Integer::sum);
            var countOfNumbers = rowOrCol.numbersToFind.size();
            var targetSum = rowOrCol.horizontal ? puzzle.width : puzzle.height;
            if (sumOfNumbers + countOfNumbers - 1 == targetSum) {
                gapFiller.fillTheGapEntirelyWithNumbers(puzzle, changes, rowOrCol, rowOrCol.numbersToFind, 0);
            }
        }
    }
}

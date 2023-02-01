package org.hawrylak.puzzle.nonogram.solver;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.NumberToFind;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.utils.GapFiller;

/*
  from  ■  x  .  .  ■  .  .  x  ■| 1 4 1
  to    ■  x  .  ■  ■  ■  .  x  ■| 1 4 1
  from  ■  x  x  x  ■  x  .  .  ■| 1 1 1 1
  to    ■  x  x  x  ■  x  ■  x  ■| 1 1 1 1
  from  ■  .  .  x  ■  x  x  x  ■| 1 1 1 1
  to    ■  x  ■  x  ■  x  x  x  ■| 1 1 1 1
 */
@AllArgsConstructor
public class TryToFillGapsBetweenGapsWithKnownNumbers extends Solver {

    private GapFinder gapFinder;
    private GapFiller gapFiller;
    private NumberSelector numberSelector;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var gaps = gapFinder.find(puzzle, rowOrCol);
            var gapsWithoutNumbers = gapFinder.findWithoutAssignedNumber(puzzle, rowOrCol);
            for (Gap gap : gapsWithoutNumbers) {
                var previous = gapFinder.previous(gaps, gap);
                var next = gapFinder.next(gaps, gap);
                if ((previous.isEmpty() || previous.get().assignedNumber.isPresent()) && (next.isEmpty()
                    || next.get().assignedNumber.isPresent())) {
                    Optional<NumberToFind> numberPrevious = previous.isEmpty() ? Optional.empty() : previous.get().assignedNumber;
                    Optional<NumberToFind> numberNext = next.isEmpty() ? Optional.empty() : next.get().assignedNumber;
                    var numbersSubList = numberSelector.getNumbersBetween(rowOrCol.numbersToFind, numberPrevious, numberNext);
                    // TODO numbersSubList can be trimmed from numbers that are found - should be done smart
                    if (!numbersSubList.isEmpty()) {
                        gapFiller.fillTheGapPartiallyForNNumbers(gap, numbersSubList, rowOrCol, puzzle, changes);
                    }
                }
            }
        }
    }
}

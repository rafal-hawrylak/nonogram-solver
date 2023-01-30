package org.hawrylak.puzzle.nonogram.solver;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.NumberToFind;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.utils.GapFiller;
import org.hawrylak.puzzle.nonogram.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.utils.Utils;

/*
  ex.
  6 |  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  x  ■  ■  ■  x  ■  ■  ■  ■  ■  ■  ■  x  .  .  ■  .  .  .  .  1  3  7  2  2
  to
  6 |  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  x  ■  ■  ■  x  ■  ■  ■  ■  ■  ■  ■  x  x  .  ■  .  .  .  .  1  3  7  2  2
                                                                                           ^
 */
@AllArgsConstructor
public class TryToNarrowGapsBetweenGapsWithKnownNumbers implements Solver {

    private GapFinder gapFinder;
    private GapFiller gapFiller;
    private NumberSelector numberSelector;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var gaps = gapFinder.find(puzzle, rowOrCol);
            var gapsWithoutNumbers = gapFinder.findWithoutAssignedNumber(puzzle, rowOrCol);
            for (Gap gap : gapsWithoutNumbers) {
                if (gap.filledSubGaps.isEmpty()) {
                    continue;
                }
                var previous = gapFinder.previous(gaps, gap);
                var next = gapFinder.next(gaps, gap);
                if (isEmptyOrAssigned(previous) && isEmptyOrAssigned(next)) {
                    Optional<NumberToFind> numberPrevious = previous.isEmpty() ? Optional.empty() : previous.get().assignedNumber;
                    Optional<NumberToFind> numberNext = next.isEmpty() ? Optional.empty() : next.get().assignedNumber;
                    var numbersSubList = numberSelector.getNumbersBetween(rowOrCol.numbersToFind, numberPrevious, numberNext);
                    // TODO numbersSubList can be trimmed from numbers that are found - should be done smart
                    if (!numbersSubList.isEmpty()) {
                        if (numbersSubList.size() == 1) {
                            gapFiller.fillTheGapPartiallyForSingleNumber(gap, Utils.getFirst(numbersSubList).get(), rowOrCol, puzzle, changes);
                        } else if (numbersSubList.size() == 2) {
                            gapFiller.fillTheGapPartiallyForNNumbers(gap, numbersSubList, rowOrCol, puzzle, changes);
                        }
                    }
                }
            }
        }
    }

    private boolean isEmptyOrAssigned(Optional<Gap> gap) {
        return gap.isEmpty() || gap.get().assignedNumber.isPresent();
    }
}

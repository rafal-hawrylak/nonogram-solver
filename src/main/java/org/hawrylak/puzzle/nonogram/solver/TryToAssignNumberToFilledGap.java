package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFiller;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.solver.utils.OnlyPossibleCombinationGapMode;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;

@AllArgsConstructor
public class TryToAssignNumberToFilledGap extends Solver {

    private GapFinder gapFinder;
    private NumberSelector numberSelector;
    private GapFiller gapFiller;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var gaps = gapFinder.find(puzzle, rowOrCol);
            for (Gap gap : gaps) {
                if (gap.assignedNumber.isEmpty() && gap.filledSubGaps.size() == 1) {
                    var subGap = gap.filledSubGaps.get(0);
                    if (subGap.start == gap.start && subGap.end == gap.end) {
                        var number = gap.length;
                        var numbersMatchingNumber = numberSelector.getNotFound(rowOrCol.numbersToFind).stream()
                            .filter(n -> n.number == number).toList();
                        if (numbersMatchingNumber.size() == 1) {
                            gapFiller.fillTheGapEntirely(gap, numbersMatchingNumber.get(0), rowOrCol, puzzle, changes);
                            continue;
                        }
                        var allPossibleSplitsAtNumber = numberSelector.getAllPossibleSplitsAtNumber(rowOrCol.numbersToFind, number);
                        var gapMode = OnlyPossibleCombinationGapMode.builder().enabled(true).startingFrom(true).endingAt(true).build();
                        gapFiller.findAndMarkTheOnlyPossibleCombinationForNumbers(puzzle, changes, rowOrCol, gaps, gap, allPossibleSplitsAtNumber, gapMode);
                    }
                }
            }
        }
    }
}

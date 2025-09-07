package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.model.SubGap;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFiller;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;

@AllArgsConstructor
public class MergeTwoFromThreeSubGapsIfThereAreOnlyTwoNumbers extends Solver {

    private GapFinder gapFinder;
    private GapFiller gapFiller;
    private NumberSelector numberSelector;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var gaps = gapFinder.findWithoutAssignedNumber(puzzle, rowOrCol);
            var subGaps = gapFinder.allSubGaps(gaps);
            var numbersToFind = numberSelector.getNotFound(rowOrCol.numbersToFind);
            if (subGaps.size() == 3 && numbersToFind.size() == 2) {
                boolean areTwoFirstSubGapsMergable = gapFinder.areSubGapsMergeable(gaps, numbersToFind.getFirst().number, subGaps.getFirst(), subGaps.get(1));
                boolean areTwoLastSubGapsMergable = gapFinder.areSubGapsMergeable(gaps, numbersToFind.getLast().number, subGaps.get(1), subGaps.getLast());
                if (areTwoFirstSubGapsMergable && !areTwoLastSubGapsMergable) {
                    gapFiller.mergeSubGaps(puzzle, changes, rowOrCol, subGaps.getFirst(), subGaps.get(1));
                    if (numbersToFind.getFirst().number == subGaps.get(1).end - subGaps.getFirst().start + 1) {
                        SubGap mergedSubGap = new SubGap(subGaps.getFirst().start, subGaps.get(1).end, subGaps.get(1).end - subGaps.getFirst().start + 1);
                        gapFiller.fillTheGapEntirely(mergedSubGap, numbersToFind.getFirst(), rowOrCol, puzzle, changes);
                    }
                } else if (!areTwoFirstSubGapsMergable && areTwoLastSubGapsMergable) {
                    gapFiller.mergeSubGaps(puzzle, changes, rowOrCol, subGaps.get(1), subGaps.getLast());
                    if (numbersToFind.getLast().number == subGaps.getLast().end - subGaps.get(1).start + 1) {
                        SubGap mergedSubGap = new SubGap(subGaps.get(1).start, subGaps.getLast().end, subGaps.getLast().end - subGaps.get(1).start + 1);
                        gapFiller.fillTheGapEntirely(mergedSubGap, numbersToFind.getLast(), rowOrCol, puzzle, changes);
                    }
                } else if (areTwoFirstSubGapsMergable && areTwoLastSubGapsMergable) {
                } else {
                    throw new IllegalStateException("At least two subGaps should be mergable");
                }
            }
        }
    }
}

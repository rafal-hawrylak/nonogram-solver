package org.hawrylak.puzzle.nonogram.solvers;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.GapFinder;
import org.hawrylak.puzzle.nonogram.NumberSelector;
import org.hawrylak.puzzle.nonogram.model.FieldState;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.GapFiller;

/*
  ex
    x  x  .  .  x  x  x  x  .  ■  ■  ■  ■  ■  .| 2 5
    to
    x  x  .  .  x  x  x  x  x  ■  ■  ■  ■  ■  x| 2 5
   AND...
    x  x  .  .  x  x  x  x  .  ■  ■  ■  ■  ■  .  .  ■  ■  ■  ■  ■  .| 2 5 5
    to
    x  x  .  .  x  x  x  x  x  ■  ■  ■  ■  ■  x  x  ■  ■  ■  ■  ■  x| 2 5 5
 */
@AllArgsConstructor
public class FitTheBiggestNumbersInOnlyPossibleGapsSolver implements Solver {

    private GapFinder gapFinder;
    private NumberSelector numberSelector;
    private GapFiller gapFiller;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            var notClosedGaps = gapFinder.find(puzzle, rowOrCol).stream().filter(g -> g.assignedNumber.isEmpty()).toList();
            var biggestNumbers = numberSelector.getBiggestNotFound(rowOrCol.numbersToFind);
            if (!biggestNumbers.isEmpty()) {
                var numberToFind = biggestNumbers.get(0);
                var gapsTheAreCapableToFit = notClosedGaps.stream().filter(g -> g.length >= numberToFind.number).toList();
                if (gapsTheAreCapableToFit.size() == 1) {
                    var gap = gapsTheAreCapableToFit.get(0);
                    var gapWithMaxSubsequentFullFields = gapFinder.maxSubsequentCountOfFields(puzzle, rowOrCol, gap, FieldState.FULL);
                    if (gapWithMaxSubsequentFullFields.length == numberToFind.number) {
                        var fakeGap = new Gap(rowOrCol, gapWithMaxSubsequentFullFields.start, gapWithMaxSubsequentFullFields.end,
                            numberToFind.number, Optional.of(numberToFind));
                        if (biggestNumbers.size() == 1) {
                            gapFiller.fillTheGapEntirely(fakeGap, numberToFind, rowOrCol, puzzle, changes);
                        } else if (biggestNumbers.size() == 2) {
                            if (gapWithMaxSubsequentFullFields.start - gap.start <= numberToFind.number) {
                                gapFiller.fillTheGapEntirely(fakeGap, biggestNumbers.get(0), rowOrCol, puzzle, changes);
                            } else if (gap.end - gapWithMaxSubsequentFullFields.end <= numberToFind.number) {
                                gapFiller.fillTheGapEntirely(fakeGap, biggestNumbers.get(1), rowOrCol, puzzle, changes);
                            }
                        }
                    } else {
                        var notFound = numberSelector.getNotFound(rowOrCol.numbersToFind);
                        if (notFound.size() == 1) {
                            gapFiller.fillTheGapPartiallyForSingleNumber(gap, numberToFind, rowOrCol, puzzle, changes);
                        } else {
                            var onlyCandidatesThatFit = numberSelector.findNumbersPossibleToFitInGap(gap, notFound);
                            if (onlyCandidatesThatFit.size() == 1 && onlyCandidatesThatFit.get(0).equals(numberToFind)) {
                                gapFiller.fillTheGapPartiallyForSingleNumber(gap, numberToFind, rowOrCol, puzzle, changes);
                            }
                        }
                    }
                }
            }
        }
    }
}

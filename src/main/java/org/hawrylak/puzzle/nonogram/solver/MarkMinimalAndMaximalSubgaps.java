package org.hawrylak.puzzle.nonogram.solver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.SubGap;
import org.hawrylak.puzzle.nonogram.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.utils.NumberSelector.NumberBeforeCurrentAndAfter;
import org.hawrylak.puzzle.nonogram.model.NumberToFind;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.utils.GapFiller;
import org.hawrylak.puzzle.nonogram.utils.OnlyPossibleCombinationGapMode;
import org.hawrylak.puzzle.nonogram.utils.OnlyPossibleCombinationSubGapMode;
import org.hawrylak.puzzle.nonogram.utils.Utils;

@AllArgsConstructor
public class MarkMinimalAndMaximalSubgaps implements Solver {

    private GapFinder gapFinder;
    private NumberSelector numberSelector;
    private GapFiller gapFiller;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var unassignedGaps = gapFinder.findWithoutAssignedNumber(puzzle, rowOrCol);
            if (unassignedGaps.isEmpty()) {
                continue;
            }
            var subGaps = gapFinder.allSubGaps(unassignedGaps);
            if (subGaps.isEmpty()) {
                continue;
            }
            var maxSubGap = subGaps.stream().max(Comparator.comparingInt(s -> s.length)).get();
            var gap = gapFinder.getGapAtPosition(unassignedGaps, maxSubGap.start, maxSubGap.end);
            var notFoundNumbers = numberSelector.getNotFound(rowOrCol.numbersToFind);
            var numbersMatchingMaxSubGap = notFoundNumbers.stream().filter(n -> n.number >= maxSubGap.length).toList();
            var allPossibleSplitsAtNumber = new ArrayList<NumberBeforeCurrentAndAfter>();
            for (NumberToFind number : numbersMatchingMaxSubGap) {
                allPossibleSplitsAtNumber.addAll(numberSelector.getAllPossibleSplitsAtNumber(notFoundNumbers, number.number));
            }
            var subGapMode = OnlyPossibleCombinationSubGapMode.builder().enabled(true).subGap(maxSubGap).build();
            var result = gapFiller.findTheOnlyPossibleCombinationForNumbers(
                rowOrCol, unassignedGaps, gap, allPossibleSplitsAtNumber, OnlyPossibleCombinationGapMode.NO, subGapMode);
            // TODO is value useful?
            if (result.isNumber()) {
                var theOnlyNumberMatching = result.getNumber();

                var previousSubGaps = Utils.allPrevious(subGaps, maxSubGap);
                var previousNumbers = Utils.allPrevious(notFoundNumbers, theOnlyNumberMatching);
                if (!previousSubGaps.isEmpty() && !previousNumbers.isEmpty()) {
                    var previousSubGap = Utils.previous(subGaps, maxSubGap).get();
                    var mergeable = gapFinder.areSubGapsMergeable(theOnlyNumberMatching.number, previousSubGap, maxSubGap);
                    if (mergeable) {
                        if (maxSubGap.length == theOnlyNumberMatching.number) {
                            var fakeGap = new Gap(rowOrCol, maxSubGap.start, maxSubGap.end, maxSubGap.length, Optional.empty());
                            gapFiller.fillTheGapEntirely(fakeGap, theOnlyNumberMatching, rowOrCol, puzzle, changes);
                            return;
                        }
                        previousSubGaps.remove(previousSubGap);
                    }
                    var maxPreviousNumber = previousNumbers.stream().max(Comparator.comparingInt(n -> n.number)).get();
                    for (SubGap subGap : previousSubGaps) {
                        if (subGap.length == maxPreviousNumber.number) {
                            var fakeGap = new Gap(rowOrCol, subGap.start, subGap.end, subGap.length, Optional.empty());
                            gapFiller.fillTheGapEntirelyWithNumberUnknown(fakeGap, rowOrCol, puzzle, changes);
                        }
                    }
                }

                var nextSubGaps = Utils.allNext(subGaps, maxSubGap);
                var nextNumbers = Utils.allNext(notFoundNumbers, theOnlyNumberMatching);
                if (!nextSubGaps.isEmpty() && !nextNumbers.isEmpty()) {
                    var nextSubGap = Utils.next(subGaps, maxSubGap).get();
                    var mergeable = gapFinder.areSubGapsMergeable(theOnlyNumberMatching.number, maxSubGap, nextSubGap);
                    if (mergeable) {
                        if (maxSubGap.length == theOnlyNumberMatching.number) {
                            var fakeGap = new Gap(rowOrCol, maxSubGap.start, maxSubGap.end, maxSubGap.length, Optional.empty());
                            gapFiller.fillTheGapEntirely(fakeGap, theOnlyNumberMatching, rowOrCol, puzzle, changes);
                            return;
                        }
                        nextSubGaps.remove(nextSubGap);
                    }
                    var maxNextNumber = nextNumbers.stream().max(Comparator.comparingInt(n -> n.number)).get();
                    for (SubGap subGap : nextSubGaps) {
                        if (subGap.length == maxNextNumber.number) {
                            var fakeGap = new Gap(rowOrCol, subGap.start, subGap.end, subGap.length, Optional.empty());
                            gapFiller.fillTheGapEntirelyWithNumberUnknown(fakeGap, rowOrCol, puzzle, changes);
                        }
                    }
                }
            }
        }
    }
}

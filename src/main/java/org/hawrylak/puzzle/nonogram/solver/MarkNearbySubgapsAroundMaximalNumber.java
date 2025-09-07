package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.*;
import org.hawrylak.puzzle.nonogram.solver.utils.*;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector.NumberBeforeCurrentAndAfter;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;

import java.util.ArrayList;
import java.util.Comparator;

@AllArgsConstructor
public class MarkNearbySubgapsAroundMaximalNumber extends Solver {

    private GapFinder gapFinder;
    private NumberSelector numberSelector;
    private GapFiller gapFiller;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var unassignedGaps = gapFinder.findWithoutAssignedNumber(puzzle, rowOrCol);
            var subGaps = gapFinder.allSubGaps(unassignedGaps);
            if (subGaps.isEmpty()) {
                continue;
            }
            var maxSubGap = subGaps.stream().max(Comparator.comparingInt(s -> s.length)).get();
            var gapWithMaxSubGap = gapFinder.getGapAtPosition(unassignedGaps, maxSubGap.start, maxSubGap.end);
            var notFoundNumbers = numberSelector.getNotFound(rowOrCol.numbersToFind);
            var numbersMatchingMaxSubGap = notFoundNumbers.stream().filter(n -> n.number >= maxSubGap.length).toList();
            var allPossibleSplitsAtNumber = new ArrayList<NumberBeforeCurrentAndAfter>();
            for (NumberToFind number : numbersMatchingMaxSubGap) {
                allPossibleSplitsAtNumber.addAll(numberSelector.getAllPossibleSplitsAtNumber(notFoundNumbers, number.number));
            }
            var subGapMode = OnlyPossibleCombinationSubGapMode.builder().enabled(true).subGap(maxSubGap).build();
            var result = gapFiller.findTheOnlyPossibleCombinationForNumbers(
                rowOrCol, unassignedGaps, gapWithMaxSubGap, allPossibleSplitsAtNumber, OnlyPossibleCombinationGapMode.NO, subGapMode);
            if (result.isNumber()) {
                var theOnlyNumberMatching = result.getNumber();
                if (maxSubGap.length == theOnlyNumberMatching.number) {
                    var fakeGap = new Gap(rowOrCol, maxSubGap.start, maxSubGap.end, maxSubGap.length);
                    gapFiller.fillTheGapEntirely(fakeGap, theOnlyNumberMatching, rowOrCol, puzzle, changes);
                    continue;
                }

                var previousSubGaps = Utils.allPrevious(subGaps, maxSubGap);
                var previousNumbers = Utils.allPrevious(notFoundNumbers, theOnlyNumberMatching);
                if (!previousSubGaps.isEmpty() && !previousNumbers.isEmpty()) {
                    var previousSubGap = Utils.previous(subGaps, maxSubGap).get();
                    var mergeable = gapFinder.areSubGapsMergeable(unassignedGaps, theOnlyNumberMatching.number, previousSubGap, maxSubGap);
                    if (!mergeable) {
                        var maxPreviousNumber = previousNumbers.stream().max(Comparator.comparingInt(n -> n.number)).get();
                        for (SubGap subGap : Utils.reverse(previousSubGaps)) {
                            if (subGap.length == maxPreviousNumber.number) {
                                var fakeGap = new Gap(rowOrCol, subGap.start, subGap.end, subGap.length);
                                gapFiller.fillTheGapEntirelyWithNumberUnknown(fakeGap, rowOrCol, puzzle, changes);
                            }
                        }
                    }
                }

                var nextSubGaps = Utils.allNext(subGaps, maxSubGap);
                var nextNumbers = Utils.allNext(notFoundNumbers, theOnlyNumberMatching);
                if (!nextSubGaps.isEmpty() && !nextNumbers.isEmpty()) {
                    var nextSubGap = Utils.next(subGaps, maxSubGap).get();
                    var mergeable = gapFinder.areSubGapsMergeable(unassignedGaps, theOnlyNumberMatching.number, maxSubGap, nextSubGap);
                    if (!mergeable) {
                        var maxNextNumber = nextNumbers.stream().max(Comparator.comparingInt(n -> n.number)).get();
                        for (SubGap subGap : nextSubGaps) {
                            if (subGap.length == maxNextNumber.number) {
                                var fakeGap = new Gap(rowOrCol, subGap.start, subGap.end, subGap.length);
                                gapFiller.fillTheGapEntirelyWithNumberUnknown(fakeGap, rowOrCol, puzzle, changes);
                            }
                        }
                    }
                }
            }
        }
    }
}

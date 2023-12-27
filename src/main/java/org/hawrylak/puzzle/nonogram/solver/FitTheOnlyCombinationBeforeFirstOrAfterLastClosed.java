package org.hawrylak.puzzle.nonogram.solver;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFiller;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;

import java.util.List;

@AllArgsConstructor
public class FitTheOnlyCombinationBeforeFirstOrAfterLastClosed extends Solver {

    private GapFinder gapFinder;
    private GapFiller gapFiller;
    private NumberSelector numberSelector;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var gaps = gapFinder.find(puzzle, rowOrCol);
            var firstFound = numberSelector.getFirstFound(rowOrCol.numbersToFind);
            if (firstFound.isPresent()) {
                var toFind = numberSelector.allPrevious(rowOrCol.numbersToFind, firstFound.get());
                if (!toFind.isEmpty()) {
                    var gapsBefore = gapFinder.gapsBeforeNumber(gaps, firstFound.get());
                    if (gapsBefore.size() == 2) {
                        var firstGap = gapsBefore.get(0);
                        var secondGap = gapsBefore.get(1);
                        boolean allNumbersFitInTheFirstGap = gapFiller.doAllNumbersFitInGaps(toFind, List.of(firstGap));
                        boolean allNumbersFitInTheSecondGap = gapFiller.doAllNumbersFitInGaps(toFind, List.of(secondGap));
                        if (!allNumbersFitInTheFirstGap && !allNumbersFitInTheSecondGap) {
                            var firstNumber = numberSelector.getFirst(toFind).get();
                            var allButFirst = numberSelector.allNext(toFind, firstNumber);
                            boolean firstNumberFitInTheFirstGap = gapFiller.doAllNumbersFitInGaps(List.of(firstNumber), List.of(firstGap));
                            boolean allButFirstNumbersFitInTheSecondGap = gapFiller.doAllNumbersFitInGaps(allButFirst, List.of(secondGap));
                            if (firstNumberFitInTheFirstGap && allButFirstNumbersFitInTheSecondGap) {
                                if (!allButFirst.isEmpty()) {
                                    var secondNumber = numberSelector.getNext(toFind, firstNumber).get();
                                    boolean twoFirstNumbersFitInTheFirstGap = gapFiller.doAllNumbersFitInGaps(List.of(firstNumber, secondNumber), List.of(firstGap));
                                    if (!twoFirstNumbersFitInTheFirstGap) {
                                        gapFiller.fillTheGapPartiallyForNNumbers(firstGap, List.of(firstNumber), rowOrCol, puzzle, changes);
                                        gapFiller.fillTheGapPartiallyForNNumbers(secondGap, allButFirst, rowOrCol, puzzle, changes);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            var lastFound = numberSelector.getLastFound(rowOrCol.numbersToFind);
            if (lastFound.isPresent()) {
                var toFind = numberSelector.allNext(rowOrCol.numbersToFind, lastFound.get());
                if (!toFind.isEmpty()) {
                    var gapsAfter = gapFinder.gapsAfterNumber(gaps, lastFound.get());
                    if (gapsAfter.size() == 2) {
                        var firstGap = gapsAfter.get(0);
                        var secondGap = gapsAfter.get(1);
                        boolean allNumbersFitInTheFirstGap = gapFiller.doAllNumbersFitInGaps(toFind, List.of(firstGap));
                        boolean allNumbersFitInTheSecondGap = gapFiller.doAllNumbersFitInGaps(toFind, List.of(secondGap));
                        if (!allNumbersFitInTheFirstGap && !allNumbersFitInTheSecondGap) {
                            var lastNumber = numberSelector.getLast(toFind).get();
                            var allButLast = numberSelector.allPrevious(toFind, lastNumber);
                            boolean allButLastNumbersFitInTheFirstGap = gapFiller.doAllNumbersFitInGaps(allButLast, List.of(firstGap));
                            boolean lastNumberFitInTheSecondGap = gapFiller.doAllNumbersFitInGaps(List.of(lastNumber), List.of(secondGap));
                            if (allButLastNumbersFitInTheFirstGap && lastNumberFitInTheSecondGap) {
                                if (!allButLast.isEmpty()) {
                                    var lastButOne = numberSelector.getPrevious(toFind, lastNumber).get();
                                    boolean twoLastNumbersFitInTheSecondGap = gapFiller.doAllNumbersFitInGaps(List.of(lastButOne, lastNumber), List.of(secondGap));
                                    if (!twoLastNumbersFitInTheSecondGap) {
                                        gapFiller.fillTheGapPartiallyForNNumbers(firstGap, allButLast, rowOrCol, puzzle, changes);
                                        gapFiller.fillTheGapPartiallyForNNumbers(secondGap, List.of(lastNumber), rowOrCol, puzzle, changes);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

package org.hawrylak.puzzle.nonogram.solver.provider;

import org.hawrylak.puzzle.nonogram.solver.*;
import org.hawrylak.puzzle.nonogram.solver.utils.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class OriginalOrderSolversCollectionProvider implements SolversCollectionProvider {

    private UtilsProvider utilsProvider = UtilsProvider.instance();

    @Override
    public Map<String, Solver> provide() {
        FieldFinder fieldFinder = new FieldFinder();
        RowSelector rowSelector = new RowSelector();
        NumberSelector numberSelector = new NumberSelector();
        GapFinder gapFinder = utilsProvider.getGapFinder();
        GapFiller gapFiller = new GapFiller(fieldFinder, numberSelector, gapFinder);
        GapCloser gapCloser = new GapCloser(fieldFinder, gapFiller, numberSelector);

        Map<String, Solver> solvers = new LinkedHashMap<>();
        addSolver(solvers, new CloseTooSmallToFitAnything(gapFinder, gapCloser));
        addSolver(solvers, new CloseAtEdges(rowSelector, gapFiller));
        addSolver(solvers, new CloseWithOneEnd(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new CloseTheOnlyCombination(gapFiller));
        addSolver(solvers, new CloseWhenAllNumbersAreFound(gapFinder, gapCloser));
        addSolver(solvers, new CloseAllTheGapsIfAllFullMarked(gapFinder, rowSelector, gapCloser));
        addSolver(solvers, new FitTheBiggestNumbersInOnlyPossibleGaps(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new CloseWhenSingleGapWithNumbersNotFound(gapFinder, gapFiller));
        addSolver(solvers, new CloseAllGapsBeforeFirstAndAfterLastFoundNumber(gapFinder, gapCloser));
        addSolver(solvers, new FillTheNumbersWithStartAndEndNotConnected(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new NarrowGapsBeforeFirstAndAfterLast(gapFinder, gapCloser, numberSelector));
        addSolver(solvers, new MarkEndingsOfSubGapWhenThereIsNoBiggerNumber(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new FindUnmergableSubGapsForBiggest(gapFinder, gapCloser, numberSelector));
        addSolver(solvers, new TryToFillGapsBetweenGapsWithKnownNumbers(gapFinder, gapFiller, numberSelector));
        addSolver(solvers, new TryToAssignNumberToFilledGap(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new CloseTooSmallToFitFirstOrLastNumber(gapFinder, gapCloser, numberSelector));
        addSolver(solvers, new FindUnmergableSubGapsForBiggestForFirstAndLastNotFound(gapFinder, gapCloser, numberSelector));
        addSolver(solvers, new ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new SecondSubGapMayBeClosed(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new MarkNearbySubgapsAroundMaximalNumber(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber(gapFinder, numberSelector, gapFiller));
        addSolver(solvers, new TryToNarrowGapsBetweenGapsWithKnownNumbers(gapFinder, gapFiller, numberSelector));
        addSolver(solvers, new MergeSubGapForBiggestIfPreviousDoesNotMatch(gapFinder, gapFiller, numberSelector));
        addSolver(solvers, new FitTheOnlyCombinationBeforeFirstOrAfterLastClosed(gapFinder, gapFiller, numberSelector));
        addSolver(solvers, new OnlyFirstLastNumberMatchesFirstLastSubGap(gapFinder, numberSelector, gapFiller, gapCloser));
        addSolver(solvers, new ThreeGapsMatchOnlyThreeNumbersEach(gapFinder, gapFiller));
        addSolver(solvers, new TwoOutOfMoreGapsMatchOnlyTwoFirstNumbersEach(gapFinder, gapFiller, gapCloser));
        addSolver(solvers, new OnlyFirstNumberFitsIntoTheFirstLastGapWithSubGap(gapFinder, numberSelector, gapFiller));

        return solvers;
    }

}

package org.hawrylak.puzzle.nonogram.solver.provider;

import java.util.LinkedHashMap;
import java.util.Map;
import org.hawrylak.puzzle.nonogram.solver.CloseAllGapsBeforeFirstAndAfterLastFoundNumber;
import org.hawrylak.puzzle.nonogram.solver.CloseAllTheGapsIfAllFullMarked;
import org.hawrylak.puzzle.nonogram.solver.CloseAtEdges;
import org.hawrylak.puzzle.nonogram.solver.CloseTheOnlyCombination;
import org.hawrylak.puzzle.nonogram.solver.CloseTooSmallToFitAnything;
import org.hawrylak.puzzle.nonogram.solver.CloseTooSmallToFitFirstOrLastNumber;
import org.hawrylak.puzzle.nonogram.solver.CloseWhenAllNumbersAreFound;
import org.hawrylak.puzzle.nonogram.solver.CloseWhenSingleGapWithNumbersNotFound;
import org.hawrylak.puzzle.nonogram.solver.CloseWithOneEnd;
import org.hawrylak.puzzle.nonogram.solver.ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber;
import org.hawrylak.puzzle.nonogram.solver.FillTheNumbersWithStartAndEndNotConnected;
import org.hawrylak.puzzle.nonogram.solver.FindUnmergableSubGapsForBiggest;
import org.hawrylak.puzzle.nonogram.solver.FindUnmergableSubGapsForBiggestForFirstAndLastNotFound;
import org.hawrylak.puzzle.nonogram.solver.FitTheBiggestNumbersInOnlyPossibleGaps;
import org.hawrylak.puzzle.nonogram.solver.IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately;
import org.hawrylak.puzzle.nonogram.solver.MarkEndingsOfSubGapWhenThereIsNoBiggerNumber;
import org.hawrylak.puzzle.nonogram.solver.MarkNearbySubgapsAroundMaximalNumber;
import org.hawrylak.puzzle.nonogram.solver.NarrowGapsBeforeFirstAndAfterLast;
import org.hawrylak.puzzle.nonogram.solver.SecondSubGapMayBeClosed;
import org.hawrylak.puzzle.nonogram.solver.Solver;
import org.hawrylak.puzzle.nonogram.solver.TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber;
import org.hawrylak.puzzle.nonogram.solver.TryToAssignNumberToFilledGap;
import org.hawrylak.puzzle.nonogram.solver.TryToFillGapsBetweenGapsWithKnownNumbers;
import org.hawrylak.puzzle.nonogram.solver.TryToNarrowGapsBetweenGapsWithKnownNumbers;
import org.hawrylak.puzzle.nonogram.utils.FieldFinder;
import org.hawrylak.puzzle.nonogram.utils.GapCloser;
import org.hawrylak.puzzle.nonogram.utils.GapFiller;
import org.hawrylak.puzzle.nonogram.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.utils.RowSelector;

public class OriginalOrderSolversProvider implements SolversProvider {

    @Override
    public Map<String, Solver> provide() {
        FieldFinder fieldFinder = new FieldFinder();
        RowSelector rowSelector = new RowSelector();
        NumberSelector numberSelector = new NumberSelector();
        GapFinder gapFinder = new GapFinder();
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

        return solvers;
    }
}

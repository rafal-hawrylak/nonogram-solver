package org.hawrylak.puzzle.nonogram.solver.provider;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hawrylak.puzzle.nonogram.solver.Solver;

public class SpecificOrderSolversProvider implements SolversProvider {

    private final static String SOLVERS = "IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately=IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately, NarrowGapsBeforeFirstAndAfterLast=NarrowGapsBeforeFirstAndAfterLast, CloseWhenAllNumbersAreFound=CloseWhenAllNumbersAreFound, FindUnmergableSubGapsForBiggest=FindUnmergableSubGapsForBiggest, MarkNearbySubgapsAroundMaximalNumber=MarkNearbySubgapsAroundMaximalNumber, CloseTooSmallToFitAnything=CloseTooSmallToFitAnything, TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber=TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber, FindUnmergableSubGapsForBiggestForFirstAndLastNotFound=FindUnmergableSubGapsForBiggestForFirstAndLastNotFound, CloseTooSmallToFitFirstOrLastNumber=CloseTooSmallToFitFirstOrLastNumber, CloseAtEdges=CloseAtEdges, TryToNarrowGapsBetweenGapsWithKnownNumbers=TryToNarrowGapsBetweenGapsWithKnownNumbers, TryToAssignNumberToFilledGap=TryToAssignNumberToFilledGap, SecondSubGapMayBeClosed=SecondSubGapMayBeClosed, CloseWhenSingleGapWithNumbersNotFound=CloseWhenSingleGapWithNumbersNotFound, FitTheBiggestNumbersInOnlyPossibleGaps=FitTheBiggestNumbersInOnlyPossibleGaps, CloseAllGapsBeforeFirstAndAfterLastFoundNumber=CloseAllGapsBeforeFirstAndAfterLastFoundNumber, FillTheNumbersWithStartAndEndNotConnected=FillTheNumbersWithStartAndEndNotConnected, CloseTheOnlyCombination=CloseTheOnlyCombination, ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber=ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber, TryToFillGapsBetweenGapsWithKnownNumbers=TryToFillGapsBetweenGapsWithKnownNumbers, MarkEndingsOfSubGapWhenThereIsNoBiggerNumber=MarkEndingsOfSubGapWhenThereIsNoBiggerNumber, CloseAllTheGapsIfAllFullMarked=CloseAllTheGapsIfAllFullMarked, CloseWithOneEnd=CloseWithOneEnd";
    private final List<String> solversNames = parse(SOLVERS);
    private final OriginalOrderSolversProvider originalOrderSolversProvider = new OriginalOrderSolversProvider();

    @Override
    public Map<String, Solver> provide() {
        Map<String, Solver> solvers = originalOrderSolversProvider.provide();
        LinkedHashMap<String, Solver> ordered = new LinkedHashMap<>(solvers.size());
        for (var solverName : solversNames) {
            ordered.put(solverName, solvers.get(solverName));
        }
        return ordered;
    }

    private List<String> parse(String solvers) {
        return Arrays.stream(solvers.split(","))
            .map(String::strip)
            .map(this::parseName)
            .toList();
    }

    private String parseName(String s) {
        return s.split("=")[0];
    }
}

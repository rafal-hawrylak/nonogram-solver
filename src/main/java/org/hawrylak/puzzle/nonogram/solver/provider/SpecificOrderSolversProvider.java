package org.hawrylak.puzzle.nonogram.solver.provider;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hawrylak.puzzle.nonogram.solver.Solver;

public class SpecificOrderSolversProvider implements SolversProvider {

    // SolveWholeCase017Test
    private final static String SOLVERS = "CloseTheOnlyCombination=CloseTheOnlyCombination, SecondSubGapMayBeClosed=SecondSubGapMayBeClosed, CloseWhenSingleGapWithNumbersNotFound=CloseWhenSingleGapWithNumbersNotFound, CloseTooSmallToFitFirstOrLastNumber=CloseTooSmallToFitFirstOrLastNumber, TryToFillGapsBetweenGapsWithKnownNumbers=TryToFillGapsBetweenGapsWithKnownNumbers, CloseAllTheGapsIfAllFullMarked=CloseAllTheGapsIfAllFullMarked, FindUnmergableSubGapsForBiggestForFirstAndLastNotFound=FindUnmergableSubGapsForBiggestForFirstAndLastNotFound, TryToAssignNumberToFilledGap=TryToAssignNumberToFilledGap, CloseTooSmallToFitAnything=CloseTooSmallToFitAnything, NarrowGapsBeforeFirstAndAfterLast=NarrowGapsBeforeFirstAndAfterLast, TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber=TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber, MarkNearbySubgapsAroundMaximalNumber=MarkNearbySubgapsAroundMaximalNumber, FitTheBiggestNumbersInOnlyPossibleGaps=FitTheBiggestNumbersInOnlyPossibleGaps, MarkEndingsOfSubGapWhenThereIsNoBiggerNumber=MarkEndingsOfSubGapWhenThereIsNoBiggerNumber, IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately=IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately, CloseAtEdges=CloseAtEdges, CloseWithOneEnd=CloseWithOneEnd, TryToNarrowGapsBetweenGapsWithKnownNumbers=TryToNarrowGapsBetweenGapsWithKnownNumbers, CloseAllGapsBeforeFirstAndAfterLastFoundNumber=CloseAllGapsBeforeFirstAndAfterLastFoundNumber, CloseWhenAllNumbersAreFound=CloseWhenAllNumbersAreFound, FillTheNumbersWithStartAndEndNotConnected=FillTheNumbersWithStartAndEndNotConnected, FindUnmergableSubGapsForBiggest=FindUnmergableSubGapsForBiggest, ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber=ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber, MergeSubGapForBiggestIfPreviousDoesNotMatch=MergeSubGapForBiggestIfPreviousDoesNotMatch";
    // SolveWholeCase011Test
    // FillTheNumbersWithStartAndEndNotConnected=FillTheNumbersWithStartAndEndNotConnected, CloseAllTheGapsIfAllFullMarked=CloseAllTheGapsIfAllFullMarked, FindUnmergableSubGapsForBiggestForFirstAndLastNotFound=FindUnmergableSubGapsForBiggestForFirstAndLastNotFound, CloseWhenSingleGapWithNumbersNotFound=CloseWhenSingleGapWithNumbersNotFound, ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber=ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber, MarkEndingsOfSubGapWhenThereIsNoBiggerNumber=MarkEndingsOfSubGapWhenThereIsNoBiggerNumber, FitTheBiggestNumbersInOnlyPossibleGaps=FitTheBiggestNumbersInOnlyPossibleGaps, CloseTooSmallToFitAnything=CloseTooSmallToFitAnything, MarkNearbySubgapsAroundMaximalNumber=MarkNearbySubgapsAroundMaximalNumber, TryToFillGapsBetweenGapsWithKnownNumbers=TryToFillGapsBetweenGapsWithKnownNumbers, CloseWhenAllNumbersAreFound=CloseWhenAllNumbersAreFound, TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber=TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber, SecondSubGapMayBeClosed=SecondSubGapMayBeClosed, TryToAssignNumberToFilledGap=TryToAssignNumberToFilledGap, CloseAllGapsBeforeFirstAndAfterLastFoundNumber=CloseAllGapsBeforeFirstAndAfterLastFoundNumber, CloseTooSmallToFitFirstOrLastNumber=CloseTooSmallToFitFirstOrLastNumber, FindUnmergableSubGapsForBiggest=FindUnmergableSubGapsForBiggest, NarrowGapsBeforeFirstAndAfterLast=NarrowGapsBeforeFirstAndAfterLast, CloseAtEdges=CloseAtEdges, IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately=IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately, CloseTheOnlyCombination=CloseTheOnlyCombination, CloseWithOneEnd=CloseWithOneEnd, TryToNarrowGapsBetweenGapsWithKnownNumbers=TryToNarrowGapsBetweenGapsWithKnownNumbers
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

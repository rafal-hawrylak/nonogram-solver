package org.hawrylak.puzzle.nonogram.solver.provider;

import org.hawrylak.puzzle.nonogram.solver.Solver;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SpecificOrderSolversCollectionProvider implements SolversCollectionProvider {

    // SolveWholeCase017Test
    private final static String SOLVERS = "OnlyFirstNumberFitsIntoTheFirstLastGapWithSubGap=OnlyFirstNumberFitsIntoTheFirstLastGapWithSubGap, TwoOutOfMoreGapsMatchOnlyTwoFirstNumbersEach=TwoOutOfMoreGapsMatchOnlyTwoFirstNumbersEach, ThreeGapsMatchOnlyThreeNumbersEach=ThreeGapsMatchOnlyThreeNumbersEach, OnlyFirstLastNumberMatchesFirstLastSubGap=OnlyFirstLastNumberMatchesFirstLastSubGap, FindUnmergableSubGapsForBiggest=FindUnmergableSubGapsForBiggest, CloseWhenAllNumbersAreFound=CloseWhenAllNumbersAreFound, MergeSubGapForBiggestIfPreviousDoesNotMatch=MergeSubGapForBiggestIfPreviousDoesNotMatch, SecondSubGapMayBeClosed=SecondSubGapMayBeClosed, ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber=ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber, CloseTooSmallToFitFirstOrLastNumber=CloseTooSmallToFitFirstOrLastNumber, FitTheBiggestNumbersInOnlyPossibleGaps=FitTheBiggestNumbersInOnlyPossibleGaps, TryToNarrowGapsBetweenGapsWithKnownNumbers=TryToNarrowGapsBetweenGapsWithKnownNumbers, CloseAtEdges=CloseAtEdges, FindUnmergableSubGapsForBiggestForFirstAndLastNotFound=FindUnmergableSubGapsForBiggestForFirstAndLastNotFound, IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately=IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately, MarkEndingsOfSubGapWhenThereIsNoBiggerNumber=MarkEndingsOfSubGapWhenThereIsNoBiggerNumber, TryToFillGapsBetweenGapsWithKnownNumbers=TryToFillGapsBetweenGapsWithKnownNumbers, CloseAllGapsBeforeFirstAndAfterLastFoundNumber=CloseAllGapsBeforeFirstAndAfterLastFoundNumber, TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber=TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber, CloseTooSmallToFitAnything=CloseTooSmallToFitAnything, FillTheNumbersWithStartAndEndNotConnected=FillTheNumbersWithStartAndEndNotConnected, CloseAllTheGapsIfAllFullMarked=CloseAllTheGapsIfAllFullMarked, CloseWhenSingleGapWithNumbersNotFound=CloseWhenSingleGapWithNumbersNotFound, CloseWithOneEnd=CloseWithOneEnd, CloseTheOnlyCombination=CloseTheOnlyCombination, TryToAssignNumberToFilledGap=TryToAssignNumberToFilledGap, MarkNearbySubgapsAroundMaximalNumber=MarkNearbySubgapsAroundMaximalNumber, NarrowGapsBeforeFirstAndAfterLast=NarrowGapsBeforeFirstAndAfterLast, FitTheOnlyCombinationBeforeFirstOrAfterLastClosed=FitTheOnlyCombinationBeforeFirstOrAfterLastClosed";
    // SolveWholeCase011Test
    // FillTheNumbersWithStartAndEndNotConnected=FillTheNumbersWithStartAndEndNotConnected, CloseAllTheGapsIfAllFullMarked=CloseAllTheGapsIfAllFullMarked, FindUnmergableSubGapsForBiggestForFirstAndLastNotFound=FindUnmergableSubGapsForBiggestForFirstAndLastNotFound, CloseWhenSingleGapWithNumbersNotFound=CloseWhenSingleGapWithNumbersNotFound, ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber=ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber, MarkEndingsOfSubGapWhenThereIsNoBiggerNumber=MarkEndingsOfSubGapWhenThereIsNoBiggerNumber, FitTheBiggestNumbersInOnlyPossibleGaps=FitTheBiggestNumbersInOnlyPossibleGaps, CloseTooSmallToFitAnything=CloseTooSmallToFitAnything, MarkNearbySubgapsAroundMaximalNumber=MarkNearbySubgapsAroundMaximalNumber, TryToFillGapsBetweenGapsWithKnownNumbers=TryToFillGapsBetweenGapsWithKnownNumbers, CloseWhenAllNumbersAreFound=CloseWhenAllNumbersAreFound, TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber=TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber, SecondSubGapMayBeClosed=SecondSubGapMayBeClosed, TryToAssignNumberToFilledGap=TryToAssignNumberToFilledGap, CloseAllGapsBeforeFirstAndAfterLastFoundNumber=CloseAllGapsBeforeFirstAndAfterLastFoundNumber, CloseTooSmallToFitFirstOrLastNumber=CloseTooSmallToFitFirstOrLastNumber, FindUnmergableSubGapsForBiggest=FindUnmergableSubGapsForBiggest, NarrowGapsBeforeFirstAndAfterLast=NarrowGapsBeforeFirstAndAfterLast, CloseAtEdges=CloseAtEdges, IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately=IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately, CloseTheOnlyCombination=CloseTheOnlyCombination, CloseWithOneEnd=CloseWithOneEnd, TryToNarrowGapsBetweenGapsWithKnownNumbers=TryToNarrowGapsBetweenGapsWithKnownNumbers
    // CloseTheOnlyCombination=CloseTheOnlyCombination, SecondSubGapMayBeClosed=SecondSubGapMayBeClosed, CloseWhenSingleGapWithNumbersNotFound=CloseWhenSingleGapWithNumbersNotFound, CloseTooSmallToFitFirstOrLastNumber=CloseTooSmallToFitFirstOrLastNumber, TryToFillGapsBetweenGapsWithKnownNumbers=TryToFillGapsBetweenGapsWithKnownNumbers, CloseAllTheGapsIfAllFullMarked=CloseAllTheGapsIfAllFullMarked, FindUnmergableSubGapsForBiggestForFirstAndLastNotFound=FindUnmergableSubGapsForBiggestForFirstAndLastNotFound, TryToAssignNumberToFilledGap=TryToAssignNumberToFilledGap, CloseTooSmallToFitAnything=CloseTooSmallToFitAnything, NarrowGapsBeforeFirstAndAfterLast=NarrowGapsBeforeFirstAndAfterLast, TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber=TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber, MarkNearbySubgapsAroundMaximalNumber=MarkNearbySubgapsAroundMaximalNumber, FitTheBiggestNumbersInOnlyPossibleGaps=FitTheBiggestNumbersInOnlyPossibleGaps, MarkEndingsOfSubGapWhenThereIsNoBiggerNumber=MarkEndingsOfSubGapWhenThereIsNoBiggerNumber, IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately=IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately, CloseAtEdges=CloseAtEdges, CloseWithOneEnd=CloseWithOneEnd, TryToNarrowGapsBetweenGapsWithKnownNumbers=TryToNarrowGapsBetweenGapsWithKnownNumbers, CloseAllGapsBeforeFirstAndAfterLastFoundNumber=CloseAllGapsBeforeFirstAndAfterLastFoundNumber, CloseWhenAllNumbersAreFound=CloseWhenAllNumbersAreFound, FillTheNumbersWithStartAndEndNotConnected=FillTheNumbersWithStartAndEndNotConnected, FindUnmergableSubGapsForBiggest=FindUnmergableSubGapsForBiggest, ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber=ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber, MergeSubGapForBiggestIfPreviousDoesNotMatch=MergeSubGapForBiggestIfPreviousDoesNotMatch
    // FindUnmergableSubGapsForBiggest=FindUnmergableSubGapsForBiggest, CloseWhenAllNumbersAreFound=CloseWhenAllNumbersAreFound, MergeSubGapForBiggestIfPreviousDoesNotMatch=MergeSubGapForBiggestIfPreviousDoesNotMatch, SecondSubGapMayBeClosed=SecondSubGapMayBeClosed, ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber=ExtendSubGapsAsManyFieldsAsPossibleForFirstAndLastNumber, CloseTooSmallToFitFirstOrLastNumber=CloseTooSmallToFitFirstOrLastNumber, FitTheBiggestNumbersInOnlyPossibleGaps=FitTheBiggestNumbersInOnlyPossibleGaps, TryToNarrowGapsBetweenGapsWithKnownNumbers=TryToNarrowGapsBetweenGapsWithKnownNumbers, CloseAtEdges=CloseAtEdges, FindUnmergableSubGapsForBiggestForFirstAndLastNotFound=FindUnmergableSubGapsForBiggestForFirstAndLastNotFound, IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately=IfAllNumbersWontFitIntoSingleGapTryToFitThemSeparately, MarkEndingsOfSubGapWhenThereIsNoBiggerNumber=MarkEndingsOfSubGapWhenThereIsNoBiggerNumber, TryToFillGapsBetweenGapsWithKnownNumbers=TryToFillGapsBetweenGapsWithKnownNumbers, CloseAllGapsBeforeFirstAndAfterLastFoundNumber=CloseAllGapsBeforeFirstAndAfterLastFoundNumber, TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber=TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber, CloseTooSmallToFitAnything=CloseTooSmallToFitAnything, FillTheNumbersWithStartAndEndNotConnected=FillTheNumbersWithStartAndEndNotConnected, CloseAllTheGapsIfAllFullMarked=CloseAllTheGapsIfAllFullMarked, CloseWhenSingleGapWithNumbersNotFound=CloseWhenSingleGapWithNumbersNotFound, CloseWithOneEnd=CloseWithOneEnd, CloseTheOnlyCombination=CloseTheOnlyCombination, TryToAssignNumberToFilledGap=TryToAssignNumberToFilledGap, MarkNearbySubgapsAroundMaximalNumber=MarkNearbySubgapsAroundMaximalNumber, NarrowGapsBeforeFirstAndAfterLast=NarrowGapsBeforeFirstAndAfterLast, FitTheOnlyCombinationBeforeFirstOrAfterLastClosed=FitTheOnlyCombinationBeforeFirstOrAfterLastClosed
    private final List<String> solversNames = parse(SOLVERS);
    private final OriginalOrderSolversCollectionProvider originalOrderSolversProvider = new OriginalOrderSolversCollectionProvider();

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

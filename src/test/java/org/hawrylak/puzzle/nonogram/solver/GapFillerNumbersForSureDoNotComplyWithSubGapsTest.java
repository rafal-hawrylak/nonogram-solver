package org.hawrylak.puzzle.nonogram.solver;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.utils.OnlyPossibleCombinationGapMode;
import org.hawrylak.puzzle.nonogram.solver.utils.OnlyPossibleCombinationSubGapMode;
import org.junit.jupiter.api.Test;

public class GapFillerNumbersForSureDoNotComplyWithSubGapsTest extends PuzzleSolverTestBase {

    @Test
    void testCase01() {
        String puzzleCase = "x■x■.■■x";
        List<Integer> numbersToFind = List.of(1, 4);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        print("before", puzzle);
        var rowOrCol = puzzle.rowsOrCols.get(0);
        var gaps = gapFinder.find(puzzle, rowOrCol);
        OnlyPossibleCombinationSubGapMode subGapMode = OnlyPossibleCombinationSubGapMode.builder().enabled(true).build();
        boolean answer = gapFiller.numbersForSureDoNotComplyWithSubGaps(rowOrCol.numbersToFind, gaps, OnlyPossibleCombinationGapMode.NO);
        assertFalse(answer);
    }
}

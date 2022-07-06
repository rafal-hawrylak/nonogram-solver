package org.hawrylak.puzzle.nonogram;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PuzzleSolverTestBase {

    protected final RowSelector rowSelector = new RowSelector();
    protected final NumberSelector numberSelector = new NumberSelector();
    protected final GapFinder gapFinder = new GapFinder();
    protected final GapFiller gapFiller = new GapFiller(gapFinder, numberSelector);
    protected final GapCloser gapCloser = new GapCloser(gapFinder, gapFiller);
    protected final NumberCloser numberCloser = new NumberCloser(rowSelector, numberSelector, gapFinder, gapFiller, gapCloser);

    protected final PuzzleStringConverter puzzleStringConverter = new PuzzleStringConverter();
    protected void assertPuzzle(Puzzle puzzle, String expectedPuzzle) {
        var actual = puzzleStringConverter.fromPuzzle(puzzle);
        assertEquals(expectedPuzzle, actual);
    }

    protected void print(String header, Puzzle puzzle) {
        System.out.println(header);
        System.out.println(puzzle);
    }
}

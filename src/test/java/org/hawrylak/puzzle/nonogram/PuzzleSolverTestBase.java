package org.hawrylak.puzzle.nonogram;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.GapCloser;
import org.hawrylak.puzzle.nonogram.solver.GapFiller;
import org.hawrylak.puzzle.nonogram.solver.NumberCloser;

public class PuzzleSolverTestBase {

    protected final FieldFinder fieldFinder = new FieldFinder();
    protected final RowSelector rowSelector = new RowSelector();
    protected final NumberSelector numberSelector = new NumberSelector();
    protected final GapFinder gapFinder = new GapFinder();
    protected final GapFiller gapFiller = new GapFiller(fieldFinder);
    protected final GapCloser gapCloser = new GapCloser(fieldFinder, gapFinder, gapFiller, numberSelector);
    protected final NumberCloser numberCloser = new NumberCloser(fieldFinder, rowSelector, numberSelector, gapFinder, gapFiller, gapCloser);

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

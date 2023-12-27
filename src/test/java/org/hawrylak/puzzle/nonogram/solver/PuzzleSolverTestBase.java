package org.hawrylak.puzzle.nonogram.solver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.utils.FieldFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.GapCloser;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFiller;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.NumberSelector;
import org.hawrylak.puzzle.nonogram.solver.utils.RowSelector;
import org.hawrylak.puzzle.nonogram.utils.PuzzleStringConverter;

public class PuzzleSolverTestBase {

    protected final FieldFinder fieldFinder = new FieldFinder();
    protected final RowSelector rowSelector = new RowSelector();
    protected final NumberSelector numberSelector = new NumberSelector();
    protected final GapFinder gapFinder = new GapFinder();
    protected final GapFiller gapFiller = new GapFiller(fieldFinder, numberSelector, gapFinder);
    protected final GapCloser gapCloser = new GapCloser(fieldFinder, gapFiller, numberSelector);

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

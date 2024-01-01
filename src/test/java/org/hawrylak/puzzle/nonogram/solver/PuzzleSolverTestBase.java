package org.hawrylak.puzzle.nonogram.solver;

import org.hawrylak.puzzle.nonogram.PuzzleSolver;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.Solution;
import org.hawrylak.puzzle.nonogram.solver.utils.*;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.utils.PuzzleStringConverter;
import org.hawrylak.puzzle.nonogram.validation.PuzzleValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PuzzleSolverTestBase {

    protected Solver solver;
    protected final FieldFinder fieldFinder = new FieldFinder();
    protected final RowSelector rowSelector = new RowSelector();
    protected final NumberSelector numberSelector = new NumberSelector();
    protected final GapFinder gapFinder = new GapFinder();
    protected final GapFiller gapFiller = new GapFiller(fieldFinder, numberSelector, gapFinder);
    protected final GapCloser gapCloser = new GapCloser(fieldFinder, gapFiller, numberSelector);

    protected final PuzzleStringConverter puzzleStringConverter = new PuzzleStringConverter();
    protected final PuzzleValidator puzzleValidator = new PuzzleValidator();

    protected void assertPuzzle(Puzzle puzzle, String expectedPuzzle) {
        var actual = puzzleStringConverter.fromPuzzle(puzzle);
        assertEquals(expectedPuzzle, actual);
    }

    protected void print(String header, Puzzle puzzle) {
        System.out.println(header);
        System.out.println(puzzle);
    }

    protected void solveAndAssert(Puzzle puzzleToSolve, String expectedPuzzle) {
        print("before", puzzleToSolve);
        var changes = new ChangedInIteration(puzzleToSolve);
        solver.apply(puzzleToSolve, changes);
        print("after", puzzleToSolve);
        assertPuzzle(puzzleToSolve, expectedPuzzle);
    }

    protected void solveAndAssertSystem(Puzzle before, String expectedPuzzle) {
        print("before", before);
        Solution solution = new PuzzleSolver().solve(before);
        print("after", solution.getPuzzle());
        assertTrue(solution.isSolved());
        assertPuzzle(solution.getPuzzle(), expectedPuzzle);
        System.out.println(solution.getPuzzle().compact());
    }
}

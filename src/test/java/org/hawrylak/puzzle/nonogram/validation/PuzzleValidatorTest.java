package org.hawrylak.puzzle.nonogram.validation;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleValidatorTest extends PuzzleSolverTestBase {

    @Test
    void singleUnresolvedPuzzleIsValid() {
        String puzzleCase = ".";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        var validationResult = puzzleValidator.validate(puzzle);
        assertEquals(ValidationResult.OK, validationResult);
    }

    @Test
    void singleEmptyPuzzleIsValid() {
        String puzzleCase = "x";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        var validationResult = puzzleValidator.validate(puzzle);
        assertEquals(ValidationResult.OK, validationResult);
    }

    @Test
    void singleFullPuzzleIsValid() {
        String puzzleCase = "■";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        var validationResult = puzzleValidator.validate(puzzle);
        assertEquals(ValidationResult.OK, validationResult);
    }

    @Test
    void twoUnresolvedPuzzleIsValid() {
        String puzzleCase = "..";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        var validationResult = puzzleValidator.validate(puzzle);
        assertEquals(ValidationResult.OK, validationResult);
    }

    @Test
    void twoEmptyPuzzleIsValid() {
        String puzzleCase = "xx";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        var validationResult = puzzleValidator.validate(puzzle);
        assertEquals(ValidationResult.OK, validationResult);
    }

    @Test
    void twoFullPuzzleIsValid() {
        String puzzleCase = "■■";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        var validationResult = puzzleValidator.validate(puzzle);
        assertFalse(validationResult.isValid());
        assertTrue(validationResult.getMessage().endsWith("that exceeds any number of this row col"));
    }

    @Test
    void oneAndOneFullPuzzleIsValid() {
        String puzzleCase = "■.■";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        var validationResult = puzzleValidator.validate(puzzle);
        assertFalse(validationResult.isValid());
        assertTrue(validationResult.getMessage().contains("that exceed total length of subgaps"));
    }

}
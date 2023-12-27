package org.hawrylak.puzzle.nonogram.utils;

import org.hawrylak.puzzle.nonogram.model.FieldState;
import org.hawrylak.puzzle.nonogram.model.NumberToFind;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleClonerTest {

    private final PuzzleStringConverter puzzleStringConverter = new PuzzleStringConverter();
    private final Random random = new Random();

    @Test
    void afterCloningAreIdenticalButNotTheSame() {
        // given
        String puzzleCase = "...x■.";
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase);
        PuzzleCloner puzzleCloner = new PuzzleCloner();

        // when
        var cloned = puzzleCloner.clone(puzzle);

        // then
        assertEquals(cloned.height, puzzle.height);
        assertEquals(cloned.width, puzzle.width);
        assertEquals(cloned.compact(), puzzle.compact());
        assertEqualsFields(cloned.fields, puzzle.fields);
        assertEqualsRowsAndCols(cloned.rowsOrCols, puzzle.rowsOrCols);
        assertNotSame(cloned, puzzle);
    }

    @Test
    void afterCloningAreOldUnchangedNewChanged() {
        // given
        String puzzleCase = "...x■.";
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase);
        PuzzleCloner puzzleCloner = new PuzzleCloner();

        // when
        var cloned = puzzleCloner.clone(puzzle);
        fullAllFields(cloned);
        changeRowsAndCols(cloned);

        // then
        assertNotEquals(cloned, puzzle);
        assertNotSame(cloned, puzzle);
        assertEquals(cloned.height, puzzle.height);
        assertEquals(cloned.width, puzzle.width);
        assertNotEquals(cloned.compact(), puzzle.compact());
        assertNotEquals(cloned.fields, puzzle.fields);
        assertNotEqualsFields(cloned.fields, puzzle.fields);
        assertNotEquals(cloned.rowsOrCols, puzzle.rowsOrCols);
        assertNotEqualsRowsAndCols(cloned.rowsOrCols, puzzle.rowsOrCols);
    }

    private void assertEqualsFields(FieldState[][] actual, FieldState[][] expected) {
        assertEquals(actual.length, expected.length);
        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[i].length; j++) {
                assertEquals(actual[i][j], expected[i][j]);
            }
        }
    }

    private void assertNotEqualsFields(FieldState[][] actual, FieldState[][] expected) {
        boolean anythingDifferent = false;
        assertEquals(actual.length, expected.length);
        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[i].length; j++) {
                if (actual[i][j] != expected[i][j]) anythingDifferent = true;
                if (anythingDifferent) {
                    break;
                }
            }
        }
        assertTrue(anythingDifferent, "There should be at least one difference in rows and cols");
    }

    private void assertEqualsRowsAndCols(List<RowOrCol> actual, List<RowOrCol> expected) {
        assertEquals(actual.size(), expected.size());
        for (int i = 0; i < actual.size(); i++) {
            var actualRowOrCol = actual.get(i);
            var expectedRowOrCol = expected.get(i);
            assertEquals(actualRowOrCol.horizontal, expectedRowOrCol.horizontal);
            assertEquals(actualRowOrCol.number, expectedRowOrCol.number);
            assertEquals(actualRowOrCol.solved, expectedRowOrCol.solved);
            assertEquals(actualRowOrCol.numbersToFind.size(), expectedRowOrCol.numbersToFind.size());
            for (int j = 0; j < actualRowOrCol.numbersToFind.size(); j++) {
                var actualNumber = actualRowOrCol.numbersToFind.get(j);
                var expectedNumber = expectedRowOrCol.numbersToFind.get(j);
                assertEquals(actualNumber.number, expectedNumber.number);
                assertEquals(actualNumber.found, expectedNumber.found);
                assertEquals(actualNumber.foundStart, expectedNumber.foundStart);
                assertEquals(actualNumber.foundEnd, expectedNumber.foundEnd);
            }
        }
    }

    private void assertNotEqualsRowsAndCols(List<RowOrCol> actual, List<RowOrCol> expected) {
        boolean anythingDifferent = false;
        assertEquals(actual.size(), expected.size());
        for (int i = 0; i < actual.size(); i++) {
            var actualRowOrCol = actual.get(i);
            var expectedRowOrCol = expected.get(i);
            if (actualRowOrCol.horizontal != expectedRowOrCol.horizontal) anythingDifferent = true;
            if (actualRowOrCol.number != expectedRowOrCol.number) anythingDifferent = true;
            if (actualRowOrCol.solved != expectedRowOrCol.solved) anythingDifferent = true;
            assertEquals(actualRowOrCol.numbersToFind.size(), expectedRowOrCol.numbersToFind.size());
            for (int j = 0; j < actualRowOrCol.numbersToFind.size(); j++) {
                var actualNumber = actualRowOrCol.numbersToFind.get(j);
                var expectedNumber = expectedRowOrCol.numbersToFind.get(j);
                if (actualNumber.number != expectedNumber.number) anythingDifferent = true;
                if (actualNumber.found != expectedNumber.found) anythingDifferent = true;
                if (actualNumber.foundStart != expectedNumber.foundStart) anythingDifferent = true;
                if (actualNumber.foundEnd != expectedNumber.foundEnd) anythingDifferent = true;
                if (anythingDifferent) {
                    break;
                }
            }
            if (anythingDifferent) {
                break;
            }
        }
        assertTrue(anythingDifferent, "There should be at least one difference in rows and cols");
    }

    private void fullAllFields(Puzzle puzzle) {
        for (int r = 0; r < puzzle.height; r++) {
            for (int c = 0; c < puzzle.width; c++) {
                puzzle.fields[c][r] = FieldState.FULL;
            }
        }
    }

    private void changeRowsAndCols(Puzzle puzzle) {
        for (RowOrCol rowsOrCol : puzzle.rowsOrCols) {
            rowsOrCol.horizontal = random.nextBoolean();
            rowsOrCol.number = random.nextInt(1000, 2000);
            rowsOrCol.solved = random.nextBoolean();
            for (NumberToFind number : rowsOrCol.numbersToFind) {
                number.found = random.nextBoolean();
                number.foundStart = random.nextInt(1000, 2000);
                number.foundEnd = random.nextInt(1000, 2000);
            }
        }
    }

}
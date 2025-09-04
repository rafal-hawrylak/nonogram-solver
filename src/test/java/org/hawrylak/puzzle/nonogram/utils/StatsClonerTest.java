package org.hawrylak.puzzle.nonogram.utils;

import org.hawrylak.puzzle.nonogram.model.statistics.SolversStatistics;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class StatsClonerTest {

    public static final String SOLVER_NAME = "solverName";
    private final PuzzleStringConverter puzzleStringConverter = new PuzzleStringConverter();
    private final Random random = new Random();

    @Test
    void afterCloningAreIdenticalButNotTheSame() {
        // given
        SolversStatistics stats = new SolversStatistics();
        stats.increaseSolverUsage(SOLVER_NAME);
        stats.increaseSolverEmptyFieldsMarked(SOLVER_NAME, 10);
        stats.increaseSolverFullFieldsMarked(SOLVER_NAME, 15);

        StatsCloner statsCloner = new StatsCloner();

        // when
        var cloned = statsCloner.deepClone(stats);

        // then
        assertEquals(cloned, stats);
        assertNotSame(cloned, stats);
    }

    @Test
    void afterCloningAreOldUnchangedNewChanged() {
        // given
        SolversStatistics stats = new SolversStatistics();
        stats.increaseSolverUsage(SOLVER_NAME);
        stats.increaseSolverEmptyFieldsMarked(SOLVER_NAME, 10);
        stats.increaseSolverFullFieldsMarked(SOLVER_NAME, 15);

        StatsCloner statsCloner = new StatsCloner();

        // when
        var cloned = statsCloner.deepClone(stats);
        cloned.increaseSolverUsage(SOLVER_NAME);
        cloned.increaseSolverEmptyFieldsMarked(SOLVER_NAME, 8);
        cloned.increaseSolverFullFieldsMarked(SOLVER_NAME, 12);

        // then
        assertNotEquals(cloned, stats);
        assertNotSame(cloned, stats);
    }

}
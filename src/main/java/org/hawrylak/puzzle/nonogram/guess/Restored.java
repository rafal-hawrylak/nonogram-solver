package org.hawrylak.puzzle.nonogram.guess;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.statistics.SolversStatistics;

public record Restored(Puzzle puzzle, SolversStatistics stats) {
}

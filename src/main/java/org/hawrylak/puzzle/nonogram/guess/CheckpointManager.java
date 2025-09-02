package org.hawrylak.puzzle.nonogram.guess;

import lombok.RequiredArgsConstructor;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.statistics.SolversStatistics;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.utils.PuzzleCloner;
import org.hawrylak.puzzle.nonogram.utils.StatsCloner;

@RequiredArgsConstructor
public class CheckpointManager {

    private final PuzzleCloner puzzleCloner;
    private final StatsCloner statsCloner;

    public Checkpoint create(Puzzle puzzle, ChangedInIteration changes, SolversStatistics stats) {
        var clonedPuzzle = puzzleCloner.deepClone(puzzle);
        var clonedStats = statsCloner.deepClone(stats);
        return new Checkpoint(clonedPuzzle, clonedStats, changes.getIteration());
    }

    public Restored restore(Checkpoint checkpoint, ChangedInIteration changes) {
        changes.setIteration(checkpoint.iteration());
        changes.setCurrentPuzzle(checkpoint.savedPuzzle());
        return new Restored(checkpoint.savedPuzzle(), checkpoint.savedStats());
    }
}

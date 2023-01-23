package org.hawrylak.puzzle.nonogram.solvers;

import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.model.Puzzle;

public interface Solver {

    default String getName() {
        return this.getClass().getName();
    }

    void apply(Puzzle puzzle, ChangedInIteration changes);
}

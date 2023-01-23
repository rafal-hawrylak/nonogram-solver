package org.hawrylak.puzzle.nonogram.solver;

import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.model.Puzzle;

public interface Solver {

    default String getName() {
        return this.getClass().getSimpleName();
    }

    void apply(Puzzle puzzle, ChangedInIteration changes);
}

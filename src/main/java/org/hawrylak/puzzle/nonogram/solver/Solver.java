package org.hawrylak.puzzle.nonogram.solver;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;

public abstract class Solver {

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public abstract void apply(Puzzle puzzle, ChangedInIteration changes);

    @Override
    public String toString() {
        return getName();
    }
}

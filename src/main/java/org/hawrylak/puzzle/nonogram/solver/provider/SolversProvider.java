package org.hawrylak.puzzle.nonogram.solver.provider;

import java.util.Map;
import org.hawrylak.puzzle.nonogram.solver.Solver;

public interface SolversProvider {

    Map<String, Solver> provide();

    default void addSolver(Map<String, Solver> solvers, Solver solver) {
        solvers.put(solver.getName(), solver);
    }
}

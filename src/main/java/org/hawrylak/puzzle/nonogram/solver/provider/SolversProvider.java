package org.hawrylak.puzzle.nonogram.solver.provider;

import org.hawrylak.puzzle.nonogram.solver.Solver;

import java.util.Map;

public interface SolversProvider {

    Map<String, Solver> provide();

    default void addSolver(Map<String, Solver> solvers, Solver solver) {
        solvers.put(solver.getName(), solver);
    }
}

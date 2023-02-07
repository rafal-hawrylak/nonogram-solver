package org.hawrylak.puzzle.nonogram.solver.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.hawrylak.puzzle.nonogram.solver.Solver;

public class RandomOrderSolverProvider implements SolversProvider {

    private Map<String, Solver> originalOrderSolvers = randomize(new OriginalOrderSolversProvider().provide());

    @Override
    public Map<String, Solver> provide() {
        return originalOrderSolvers;
    }

    private Map<String, Solver> randomize(Map<String, Solver> solvers) {
        ArrayList<Entry<String, Solver>> entries = new ArrayList<>(solvers.entrySet());
        Collections.shuffle(entries);
        LinkedHashMap<String, Solver> shuffled = new LinkedHashMap<>(entries.size());
        for (var entry : entries) {
            shuffled.put(entry.getKey(), entry.getValue());
        }
        return shuffled;
    }
}

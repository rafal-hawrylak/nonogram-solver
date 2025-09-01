package org.hawrylak.puzzle.nonogram.utils;

import org.hawrylak.puzzle.nonogram.model.statistics.SolverStatistics;
import org.hawrylak.puzzle.nonogram.model.statistics.SolversStatistics;

import java.util.HashMap;
import java.util.Map;

public class StatsCloner {

    public SolversStatistics deepClone(SolversStatistics stats) {
        var statsMap = deepClone(stats.getStats());
        return new SolversStatistics(statsMap);
    }

    private Map<String, SolverStatistics> deepClone(Map<String, SolverStatistics> stats) {
        var cloned = new HashMap<String, SolverStatistics>(stats.size());
        for (Map.Entry<String, SolverStatistics> entry : stats.entrySet()) {
            cloned.put(entry.getKey(), deepClone(entry.getValue()));
        }
        return cloned;
    }

    private SolverStatistics deepClone(SolverStatistics statistics) {
        return new SolverStatistics(statistics.getName(), statistics.getUsage(), statistics.getFieldsMarked(), statistics.getEmptyFieldsMarked(), statistics.getFullFieldsMarked());
    }
}

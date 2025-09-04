package org.hawrylak.puzzle.nonogram.utils;

import org.hawrylak.puzzle.nonogram.model.statistics.GuessStatistics;
import org.hawrylak.puzzle.nonogram.model.statistics.SolverStatistics;
import org.hawrylak.puzzle.nonogram.model.statistics.SolversStatistics;

import java.util.HashMap;
import java.util.Map;

public class StatsCloner {

    public SolversStatistics deepClone(SolversStatistics stats) {
        var statsMap = deepCloneSolvers(stats.getSolversStats());
        var guessMap = deepCloneGuess(stats.getGuessStats());
        return new SolversStatistics(statsMap, guessMap, stats.getTotalGuessStats());
    }

    private Map<String, SolverStatistics> deepCloneSolvers(Map<String, SolverStatistics> stats) {
        var cloned = new HashMap<String, SolverStatistics>(stats.size());
        for (Map.Entry<String, SolverStatistics> entry : stats.entrySet()) {
            cloned.put(entry.getKey(), deepClone(entry.getValue()));
        }
        return cloned;
    }

    private Map<String, GuessStatistics> deepCloneGuess(Map<String, GuessStatistics> stats) {
        var cloned = new HashMap<String, GuessStatistics>(stats.size());
        for (Map.Entry<String, GuessStatistics> entry : stats.entrySet()) {
            cloned.put(entry.getKey(), deepClone(entry.getValue()));
        }
        return cloned;
    }

    private SolverStatistics deepClone(SolverStatistics statistics) {
        return new SolverStatistics(statistics.getName(), statistics.getUsage(), statistics.getFieldsMarked(), statistics.getEmptyFieldsMarked(), statistics.getFullFieldsMarked());
    }

    private GuessStatistics deepClone(GuessStatistics statistics) {
        return new GuessStatistics(statistics.getName(), statistics.getUsage(), statistics.getGuessCount(), statistics.getOppositeGuessCount(), statistics.getRevertCount());
    }
}

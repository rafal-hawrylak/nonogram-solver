package org.hawrylak.puzzle.nonogram.model.statistics;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SolversStatistics {

    private Map<String, SolverStatistics> stats = new HashMap<>();

    public int getUsage(String solverName) {
        makeSureSolverIsPresent(solverName);
        return stats.get(solverName).getUsage();
    }

    public int getFieldsMarked(String solverName) {
        makeSureSolverIsPresent(solverName);
        return stats.get(solverName).getFieldsMarked();
    }

    public int getEmptyFieldsMarked(String solverName) {
        makeSureSolverIsPresent(solverName);
        return stats.get(solverName).getEmptyFieldsMarked();
    }

    public int getFullFieldsMarked(String solverName) {
        makeSureSolverIsPresent(solverName);
        return stats.get(solverName).getFullFieldsMarked();
    }

    public void increaseUsage(String solverName) {
        makeSureSolverIsPresent(solverName);
        stats.get(solverName).increaseUsage();
    }

    public void increaseEmptyFieldsMarked(String solverName, int increase) {
        makeSureSolverIsPresent(solverName);
        stats.get(solverName).increaseEmptyFieldsMarked(increase);
    }

    public void increaseFullFieldsMarked(String solverName, int increase) {
        makeSureSolverIsPresent(solverName);
        stats.get(solverName).increaseFullFieldsMarked(increase);
    }

    private void makeSureSolverIsPresent(String solverName) {
        if (!stats.containsKey(solverName)) {
            stats.put(solverName, new SolverStatistics(solverName));
        }
    }

    @Override
    public String toString() {
        var list = stats.values().stream().sorted(Comparator.comparingInt(SolverStatistics::getUsage)).toList().reversed();
        return "SolversStatistics{\n" + list + "\n}";
    }
}

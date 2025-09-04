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

    private Map<String, SolverStatistics> solversStats = new HashMap<>();
    private Map<String, GuessStatistics> guessStats = new HashMap<>();
    private Map<String, GuessStatistics> totalGuessStats = new HashMap<>();

    public int getUsage(String solverName) {
        makeSureSolverIsPresent(solverName);
        return solversStats.get(solverName).getUsage();
    }

    public int getFieldsMarked(String solverName) {
        makeSureSolverIsPresent(solverName);
        return solversStats.get(solverName).getFieldsMarked();
    }

    public int getEmptyFieldsMarked(String solverName) {
        makeSureSolverIsPresent(solverName);
        return solversStats.get(solverName).getEmptyFieldsMarked();
    }

    public int getFullFieldsMarked(String solverName) {
        makeSureSolverIsPresent(solverName);
        return solversStats.get(solverName).getFullFieldsMarked();
    }

    public void increaseSolverUsage(String solverName) {
        makeSureSolverIsPresent(solverName);
        solversStats.get(solverName).increaseUsage();
    }

    public void increaseSolverEmptyFieldsMarked(String solverName, int increase) {
        makeSureSolverIsPresent(solverName);
        solversStats.get(solverName).increaseEmptyFieldsMarked(increase);
    }

    public void increaseSolverFullFieldsMarked(String solverName, int increase) {
        makeSureSolverIsPresent(solverName);
        solversStats.get(solverName).increaseFullFieldsMarked(increase);
    }

    private void makeSureSolverIsPresent(String solverName) {
        if (!solversStats.containsKey(solverName)) {
            solversStats.put(solverName, new SolverStatistics(solverName));
        }
    }

    public void increaseGuesserUsage(String guesserName) {
        makeSureGuesserIsPresent(guesserName);
        guessStats.get(guesserName).increaseUsage();
        totalGuessStats.get(guesserName).increaseUsage();
    }

    private void makeSureGuesserIsPresent(String guesserName) {
        if (!guessStats.containsKey(guesserName)) {
            guessStats.put(guesserName, new GuessStatistics(guesserName));
            totalGuessStats.put(guesserName, new GuessStatistics(guesserName));
        }
    }

    public void increaseGuessCount(String guesserName) {
        makeSureGuesserIsPresent(guesserName);
        guessStats.get(guesserName).increaseGuessCount();
        totalGuessStats.get(guesserName).increaseGuessCount();
    }

    public void increaseOppositeGuessCount(String guesserName) {
        makeSureGuesserIsPresent(guesserName);
        guessStats.get(guesserName).increaseOppositeGuessCount();
        totalGuessStats.get(guesserName).increaseOppositeGuessCount();
    }

    public void increaseRevertCount(String guesserName) {
        makeSureGuesserIsPresent(guesserName);
        guessStats.get(guesserName).increaseRevertCount();
        totalGuessStats.get(guesserName).increaseRevertCount();
    }

    @Override
    public String toString() {
        var solversList = solversStats.values().stream().sorted(Comparator.comparingInt(SolverStatistics::getUsage)).toList().reversed();
        var guessList = guessStats.values().stream().sorted(Comparator.comparingInt(GuessStatistics::getUsage)).toList().reversed();
        var totalGuessList = totalGuessStats.values().stream().sorted(Comparator.comparingInt(GuessStatistics::getUsage)).toList().reversed();
        return "SolversStatistics{\n\tsolvers:\n" + solversList + "\n\n\tguesses:\n" + guessList + "\n\n\ttotal guesses:\n" + totalGuessList + "\n}";
    }
}

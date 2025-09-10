package org.hawrylak.puzzle.nonogram.guess;

import org.hawrylak.puzzle.nonogram.model.FieldState;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class RandomSingleEmptyFieldCloseToAnotherFullGlobalGuesser implements Guesser {

    private final Random random = new Random();
    private final GapFinder gapFinder = new GapFinder();
    private final Guesser fallbackGuesser = new RandomSingleFullFieldGuesser();

    @Override
    public GuessChoice guess(Puzzle puzzle) {
        var candidatesWith4Adjacent = new ArrayList<GuessChoice>();
        var candidatesWith3Adjacent = new ArrayList<GuessChoice>();
        var candidatesWith2Adjacent = new ArrayList<GuessChoice>();
        var candidatesWith1Adjacent = new ArrayList<GuessChoice>();
        for (RowOrCol rowOrCol : puzzle.getUnsolvedRowsOrCols()) {
            var gaps = gapFinder.findWithoutAssignedNumber(puzzle, rowOrCol);
            if (gaps.isEmpty()) {
                continue;
            }
            List<Candidate> candidates = new ArrayList<>();
            gaps.forEach(gap -> candidates.addAll(addCandidatesCloserToFull(gap, rowOrCol, puzzle)));
            if (candidates.isEmpty()) {
                continue;
            }
            candidatesWith4Adjacent.addAll(getCandidatesWithNAdjacent(candidates, 4));
            candidatesWith3Adjacent.addAll(getCandidatesWithNAdjacent(candidates, 3));
            candidatesWith2Adjacent.addAll(getCandidatesWithNAdjacent(candidates, 2));
            candidatesWith1Adjacent.addAll(getCandidatesWithNAdjacent(candidates, 1));
        }
        if (!candidatesWith4Adjacent.isEmpty()) {
            return candidatesWith4Adjacent.get(random.nextInt(candidatesWith4Adjacent.size()));
        }
        if (!candidatesWith3Adjacent.isEmpty()) {
            return candidatesWith3Adjacent.get(random.nextInt(candidatesWith3Adjacent.size()));
        }
        if (!candidatesWith2Adjacent.isEmpty()) {
            return candidatesWith2Adjacent.get(random.nextInt(candidatesWith2Adjacent.size()));
        }
        if (!candidatesWith1Adjacent.isEmpty()) {
            return candidatesWith1Adjacent.get(random.nextInt(candidatesWith1Adjacent.size()));
        }
        return fallbackGuesser.guess(puzzle);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    private GuessChoice fallbackToRandom() {
        return null;
    }

    private static List<GuessChoice> getCandidatesWithNAdjacent(List<Candidate> candidates, int n) {
        return candidates.stream().filter(c -> c.numberOfAdjacentFull == n).map(Candidate::guessChoice).toList();
    }

    private Collection<Candidate> addCandidatesCloserToFull(Gap gap, RowOrCol rowOrCol, Puzzle puzzle) {
        List<Candidate> result = new ArrayList<>();
        for (int i = gap.start; i <= gap.end; i++) {
            if (rowOrCol.horizontal) {
                if (puzzle.fields[i][rowOrCol.number] == FieldState.UNKNOWN) {
                    int numberOfAdjacentFull = numberOfAdjacentFull(puzzle, i, rowOrCol.number);
                    result.add(new Candidate(new GuessChoice(rowOrCol.number, i, FieldState.EMPTY), numberOfAdjacentFull));
                }
            } else {
                if (puzzle.fields[rowOrCol.number][i] == FieldState.UNKNOWN) {
                    int numberOfAdjacentFull = numberOfAdjacentFull(puzzle, rowOrCol.number, i);
                    result.add(new Candidate(new GuessChoice(i, rowOrCol.number, FieldState.EMPTY), numberOfAdjacentFull));
                }
            }
        }
        return result;
    }

    private int numberOfAdjacentFull(Puzzle puzzle, int i, int j) {
        int result = 0;
        result += isFull(puzzle, i - 1, j);
        result += isFull(puzzle, i + 1, j);
        result += isFull(puzzle, i, j - 1);
        result += isFull(puzzle, i, j + 1);
        return result;
    }

    private int isFull(Puzzle puzzle, int i, int j) {
        return (i >= 0 && i < puzzle.width && j >= 0 && j < puzzle.height && puzzle.fields[i][j] == FieldState.FULL) ? 1 : 0;
    }

    private record Candidate(GuessChoice guessChoice, int numberOfAdjacentFull) {};
}

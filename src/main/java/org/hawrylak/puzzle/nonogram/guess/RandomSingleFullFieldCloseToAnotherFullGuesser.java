package org.hawrylak.puzzle.nonogram.guess;

import org.hawrylak.puzzle.nonogram.model.FieldState;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.RowSelector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class RandomSingleFullFieldCloseToAnotherFullGuesser implements Guesser {

    private final Random random = new Random();
    private final RowSelector rowSelector = new RowSelector();
    private final GapFinder gapFinder = new GapFinder();

    @Override
    public GuessChoice guess(Puzzle puzzle) {
        while (true) {
            var notFoundRowsOrCols = rowSelector.getNotFound(puzzle.rowsOrCols);
            if (notFoundRowsOrCols.isEmpty()) {
                continue;
            }
            RowOrCol rowOrCol = notFoundRowsOrCols.get(random.nextInt(notFoundRowsOrCols.size()));
            var gaps = gapFinder.findWithoutAssignedNumber(puzzle, rowOrCol);
            if (gaps.isEmpty()) {
                continue;
            }
            List<Candidate> candidates = new ArrayList<>();
            gaps.forEach(gap -> candidates.addAll(addCandidatesCloserToFull(gap, rowOrCol, puzzle)));
            if (candidates.isEmpty()) {
                continue;
            }
            var candidatesWith4Adjacent = getCandidatesWithNAdjacent(candidates, 4);
            if (!candidatesWith4Adjacent.isEmpty()) {
                return candidatesWith4Adjacent.get(random.nextInt(candidatesWith4Adjacent.size()));
            }
            var candidatesWith3Adjacent = getCandidatesWithNAdjacent(candidates, 3);
            if (!candidatesWith3Adjacent.isEmpty()) {
                return candidatesWith3Adjacent.get(random.nextInt(candidatesWith3Adjacent.size()));
            }
            var candidatesWith2Adjacent = getCandidatesWithNAdjacent(candidates, 2);
            if (!candidatesWith2Adjacent.isEmpty()) {
                return candidatesWith2Adjacent.get(random.nextInt(candidatesWith2Adjacent.size()));
            }
            var candidatesWith1Adjacent = getCandidatesWithNAdjacent(candidates, 1);
            if (!candidatesWith1Adjacent.isEmpty()) {
                return candidatesWith1Adjacent.get(random.nextInt(candidatesWith1Adjacent.size()));
            }
        }
    }

    private static List<GuessChoice> getCandidatesWithNAdjacent(List<Candidate> candidates, int n) {
        return candidates.stream().filter(c -> c.numberOfAdjacentFull == n).map(Candidate::guessChoice).toList();
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    private Collection<Candidate> addCandidatesCloserToFull(Gap gap, RowOrCol rowOrCol, Puzzle puzzle) {
        List<Candidate> result = new ArrayList<>();
        for (int i = gap.start; i <= gap.end; i++) {
            if (rowOrCol.horizontal) {
                if (puzzle.fields[i][rowOrCol.number] == FieldState.UNKNOWN) {
                    int numberOfAdjacentFull = numberOfAdjacentFull(puzzle, i, rowOrCol.number);
                    result.add(new Candidate(new GuessChoice(rowOrCol.number, i, FieldState.FULL), numberOfAdjacentFull));
                }
            } else {
                if (puzzle.fields[rowOrCol.number][i] == FieldState.UNKNOWN) {
                    int numberOfAdjacentFull = numberOfAdjacentFull(puzzle, rowOrCol.number, i);
                    result.add(new Candidate(new GuessChoice(i, rowOrCol.number, FieldState.FULL), numberOfAdjacentFull));
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

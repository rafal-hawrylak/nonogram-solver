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

public class RandomSingleFullFieldGuesser implements Guesser {

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
            List<GuessChoice> candidates = new ArrayList<>();
            gaps.forEach(gap -> candidates.addAll(addCandidates(gap, rowOrCol, puzzle)));
            if (candidates.isEmpty()) {
                continue;
            }
            return candidates.get(random.nextInt(candidates.size()));
        }
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    private Collection<GuessChoice> addCandidates(Gap gap, RowOrCol rowOrCol, Puzzle puzzle) {
        List<GuessChoice> result = new ArrayList<>();
        for (int i = gap.start; i <= gap.end; i++) {
            if (rowOrCol.horizontal) {
                if (puzzle.fields[i][rowOrCol.number] == FieldState.UNKNOWN) {
                    result.add(new GuessChoice(rowOrCol.number, i, FieldState.FULL));
                }
            } else {
                if (puzzle.fields[rowOrCol.number][i] == FieldState.UNKNOWN) {
                    result.add(new GuessChoice(i, rowOrCol.number, FieldState.FULL));
                }
            }
        }
        return result;
    }
}

package org.hawrylak.puzzle.nonogram.solver;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.solver.utils.RowSelector;
import org.hawrylak.puzzle.nonogram.model.FieldState;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFiller;

/*
  ex
    .  .  .  .  x  .  .  <  x|
    .  .  .  .  v  .  ^  .  .|
    x  >  .  .  .  .  x  .  .|
 */
@AllArgsConstructor
public class CloseAtEdges extends Solver {

    private final RowSelector rowSelector;
    private GapFiller gapFiller;

    @Override
    public void apply(Puzzle puzzle, ChangedInIteration changes) {
        for (int r = 0; r < puzzle.height; r++) {
            var rowOrCol = rowSelector.find(puzzle, r, true);
            tryToCloseFromEdge(puzzle, changes, r, rowOrCol, true);
            tryToCloseFromEdge(puzzle, changes, r, rowOrCol, false);
        }
        for (int c = 0; c < puzzle.width; c++) {
            var rowOrCol = rowSelector.find(puzzle, c, false);
            tryToCloseFromEdge(puzzle, changes, c, rowOrCol, true);
            tryToCloseFromEdge(puzzle, changes, c, rowOrCol, false);
        }
    }

    private void tryToCloseFromEdge(Puzzle puzzle, ChangedInIteration changes, int i, RowOrCol rowOrCol, boolean fromStart) {
        if (rowOrCol.solved) {
            return;
        }
        boolean hasNumbers = !rowOrCol.numbersToFind.isEmpty();
        var indexOfNumber = fromStart ? 0 : rowOrCol.numbersToFind.size() - 1;
        if (hasNumbers && !rowOrCol.numbersToFind.get(indexOfNumber).found) {
            var numberToClose = rowOrCol.numbersToFind.get(indexOfNumber);
            var fieldFirst = rowOrCol.horizontal ? puzzle.fields[0][i] : puzzle.fields[i][0];
            var fieldLastCoord = rowOrCol.horizontal ? puzzle.width - 1 : puzzle.height - 1;
            var fieldLast = rowOrCol.horizontal ? puzzle.fields[fieldLastCoord][i] : puzzle.fields[i][fieldLastCoord];
            var field = fromStart ? fieldFirst : fieldLast;
            var fieldStart = fromStart ? 0 : fieldLastCoord - numberToClose.number + 1;
            if (FieldState.FULL.equals(field)) {
                var fakeGap = new Gap(rowOrCol, fieldStart, fieldStart + numberToClose.number - 1, numberToClose.number, Optional.empty());
                gapFiller.fillTheGapEntirely(fakeGap, numberToClose, rowOrCol, puzzle, changes);
            }
        }
    }
}

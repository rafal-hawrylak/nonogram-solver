package org.hawrylak.puzzle.nonogram.solver.utils;

import lombok.AllArgsConstructor;
import org.hawrylak.puzzle.nonogram.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.model.FieldState;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.Puzzle;

@AllArgsConstructor
public class GapCloser {

    private final FieldFinder fieldFinder;
    private final GapFiller gapFiller;
    private final NumberSelector numberSelector;


    public void close(Gap gap, Puzzle puzzle, ChangedInIteration changes) {
        if (fieldFinder.isFieldAtState(gap.rowOrCol, puzzle, gap.start, FieldState.UNKNOWN)) {
            closeAsEmpty(gap, puzzle, changes);
        } else if (fieldFinder.isFieldAtState(gap.rowOrCol, puzzle, gap.start, FieldState.FULL)) {
            var numberToClose = numberSelector.getForPositionAssumingAllFullInTheRowOrColFilled(gap.rowOrCol, puzzle, gap.start, gap.end);
            if (numberToClose.isPresent()) {
                gapFiller.fillTheGapEntirely(gap, numberToClose.get(), gap.rowOrCol, puzzle, changes);
            }
        }
    }

    public void closeAsEmpty(Gap gap, Puzzle puzzle, ChangedInIteration changes) {
        for (int i = gap.start; i <= gap.end; i++) {
            if (fieldFinder.isFieldAtState(gap.rowOrCol, puzzle, i, FieldState.UNKNOWN)) {
                gapFiller.fillSingleField(gap.rowOrCol, puzzle, changes, i, FieldState.EMPTY);
            }
        }
    }

}

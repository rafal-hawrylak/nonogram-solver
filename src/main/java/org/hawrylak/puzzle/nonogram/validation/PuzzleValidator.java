package org.hawrylak.puzzle.nonogram.validation;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.model.SubGap;
import org.hawrylak.puzzle.nonogram.solver.utils.GapFinder;
import org.hawrylak.puzzle.nonogram.solver.utils.UtilsProvider;

public class PuzzleValidator {

    private UtilsProvider utilsProvider = UtilsProvider.instance();
    private GapFinder gapFinder = utilsProvider.getGapFinder();

    public ValidationResult validate(Puzzle puzzle) {
        var result = checkIfAnyOfTheSubGapIsBiggerThanMaxNumber(puzzle);
        if (!result.isValid()) {
            return result;
        }
        result = checkIfSumOfEmptyIsSmallerThanSumOfNumbers(puzzle);
        if (!result.isValid()) {
            return result;
        }

        return ValidationResult.OK;
    }

    private ValidationResult checkIfAnyOfTheSubGapIsBiggerThanMaxNumber(Puzzle puzzle) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            var gaps = gapFinder.find(puzzle, rowOrCol);
            var subGaps = gaps.stream().flatMap(g -> g.filledSubGaps.stream()).toList();
            for (SubGap subGap : subGaps) {
                boolean noneMatch = rowOrCol.numbersToFind.stream().noneMatch(n -> n.number >= subGap.length);
                if (noneMatch) {
                    String message = String.format("For row col %s there is a subgap %s that exceeds any number of this row col", rowOrCol, subGap);
                    return new ValidationResult(false, message);
                }
            }
        }
        return ValidationResult.OK;
    }

    private ValidationResult checkIfSumOfEmptyIsSmallerThanSumOfNumbers(Puzzle puzzle) {
        for (RowOrCol rowOrCol : puzzle.rowsOrCols) {
            var gaps = gapFinder.find(puzzle, rowOrCol);
            var subGapsLength = gaps.stream().flatMap(g -> g.filledSubGaps.stream()).mapToInt(s -> s.length).sum();
            var numbersSum = rowOrCol.numbersToFind.stream().mapToInt(n -> n.number).sum();
            if (subGapsLength > numbersSum) {
                String message = String.format("For row col %s the are numbers %s that exceed total length of subgaps = %s", rowOrCol, numbersSum, subGapsLength);
                return new ValidationResult(false, message);
            }
        }
        return ValidationResult.OK;
    }
}


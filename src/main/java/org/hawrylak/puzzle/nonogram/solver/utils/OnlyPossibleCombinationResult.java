package org.hawrylak.puzzle.nonogram.solver.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hawrylak.puzzle.nonogram.model.NumberToFind;

import java.util.Objects;

@Getter
@Builder
@ToString
public class OnlyPossibleCombinationResult {

    private NumberToFind number;
    private Integer value;

    public boolean isNumber() {
        return Objects.nonNull(number);
    }

    public boolean isValue() {
        return Objects.nonNull(value);
    }

    public boolean isNone() {
        return !isNumber() && !isValue();
    }
}

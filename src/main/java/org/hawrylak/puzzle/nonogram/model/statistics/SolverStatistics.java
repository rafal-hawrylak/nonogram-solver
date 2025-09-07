package org.hawrylak.puzzle.nonogram.model.statistics;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class SolverStatistics {

    private final String name;
    private int usage = 0;
    private int fieldsMarked = 0;
    private int emptyFieldsMarked = 0;
    private int fullFieldsMarked = 0;

    public void increaseUsage() {
        usage++;
    }

    public void increaseEmptyFieldsMarked(int increase) {
        fieldsMarked += increase;
        emptyFieldsMarked += increase;
    }

    public void increaseFullFieldsMarked(int increase) {
        fieldsMarked += increase;
        fullFieldsMarked += increase;
    }

    @Override
    public String toString() {
        return "SolverStatistics{" +
                name +
                "; usage=" + usage +
                "; fieldsMarked=" + fieldsMarked +
                "; emptyFieldsMarked=" + emptyFieldsMarked +
                "; fullFieldsMarked=" + fullFieldsMarked +
                '}';
    }
}

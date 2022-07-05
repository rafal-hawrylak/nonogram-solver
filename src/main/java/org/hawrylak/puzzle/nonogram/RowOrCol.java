package org.hawrylak.puzzle.nonogram;

import java.util.List;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"id"})
public class RowOrCol {

    int number;
    boolean horizontal;
    boolean solved = false;
    @Include
    private final UUID id = UUID.randomUUID();
    List<NumberToFind> numbersToFind;

    public RowOrCol(List<Integer> numbersToFind, int number, boolean horizontal) {
        this.numbersToFind = numbersToFind.stream()
            .map(NumberToFind::new).toList();
        this.number = number;
        this.horizontal = horizontal;
    }
}

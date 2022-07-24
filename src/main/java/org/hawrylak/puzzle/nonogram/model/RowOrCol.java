package org.hawrylak.puzzle.nonogram.model;

import java.util.List;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.ToString;
import org.hawrylak.puzzle.nonogram.NumberToFind;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"id"})
public class RowOrCol {

    public int number;
    public boolean horizontal;
    public boolean solved = false;
    public List<NumberToFind> numbersToFind;
    @Include
    private final UUID id = UUID.randomUUID();

    public RowOrCol(List<Integer> numbersToFind, int number, boolean horizontal) {
        this.numbersToFind = numbersToFind.stream()
            .map(NumberToFind::new).toList();
        this.number = number;
        this.horizontal = horizontal;
    }
}

package org.hawrylak.puzzle.nonogram.model;

import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"id"})
public class RowOrCol {

    public int number;
    public boolean horizontal;
    public boolean solved = false;
    public List<NumberToFind> numbersToFind;
    @Include
    public UUID id = UUID.randomUUID();

    public RowOrCol(List<Integer> numbersToFind, int number, boolean horizontal) {
        this.numbersToFind = numbersToFind.stream()
            .map(NumberToFind::new).toList();
        this.number = number;
        this.horizontal = horizontal;
    }

    public RowOrCol(UUID id, List<NumberToFind> numbersToFind, int number, boolean horizontal, boolean solved) {
        this.id = id;
        this.numbersToFind = numbersToFind;
        this.number = number;
        this.horizontal = horizontal;
        this.solved = solved;
    }
}

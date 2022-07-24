package org.hawrylak.puzzle.nonogram.model;

import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"id"})
public class NumberToFind {

    @Getter
    public final int number;
    @Include
    private final UUID id = UUID.randomUUID();
    public boolean found = false;
    public int foundStart = -1;
    public int foundEnd = -1;
}

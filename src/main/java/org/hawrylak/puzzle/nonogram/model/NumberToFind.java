package org.hawrylak.puzzle.nonogram.model;

import lombok.*;
import lombok.EqualsAndHashCode.Include;

import java.util.UUID;

@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"id"})
public class NumberToFind {

    @Getter
    public final int number;
    @Include
    public UUID id = UUID.randomUUID();
    public boolean found = false;
    public int foundStart = -1;
    public int foundEnd = -1;
}

package org.hawrylak.puzzle.nonogram;

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
    final int number;
    @Include
    private final UUID id = UUID.randomUUID();
    boolean found = false;
    int foundStart = -1;
    int foundEnd = -1;
}

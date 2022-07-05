package org.hawrylak.puzzle.nonogram;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Gap {

    RowOrCol rowOrCol;
    int start;
    int end;
    int length;
    Optional<NumberToFind> assignedNumber;

}

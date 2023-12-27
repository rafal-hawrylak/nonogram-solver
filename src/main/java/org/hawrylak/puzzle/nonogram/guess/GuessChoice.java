package org.hawrylak.puzzle.nonogram.guess;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hawrylak.puzzle.nonogram.model.FieldState;

@AllArgsConstructor
@Getter
public class GuessChoice {
    private int row;
    private int col;
    private FieldState state;
}

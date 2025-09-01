package org.hawrylak.puzzle.nonogram.guess;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hawrylak.puzzle.nonogram.model.FieldState;

@RequiredArgsConstructor
@Getter
@ToString
public class GuessChoice {
    private final int row;
    private final int col;
    private final FieldState state;
    private boolean opposite = false;

    public void setOpposite() {
        opposite = true;
    }
}

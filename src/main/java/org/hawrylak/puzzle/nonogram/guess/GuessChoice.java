package org.hawrylak.puzzle.nonogram.guess;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hawrylak.puzzle.nonogram.model.FieldState;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class GuessChoice {
    private final int row;
    private final int col;
    private final FieldState state;
    private boolean opposite = false;

    public static GuessChoice opposite(GuessChoice guess) {
        return new GuessChoice(guess.row, guess.col, opposite(guess.state), !guess.opposite);
    }

    private static FieldState opposite(FieldState state) {
        switch (state) {
            case EMPTY -> {
                return FieldState.FULL;
            }
            case FULL -> {
                return FieldState.EMPTY;
            }
            case UNKNOWN, OUTSIDE -> {
                throw new IllegalStateException("Guess can't have " + state + " state");
            }
        }
        return null;
    }
}

package com.kodilla.tictactoe.model;

import com.kodilla.tictactoe.logic.ErrorReason;
import com.kodilla.tictactoe.logic.GameValidationException;

import static com.kodilla.tictactoe.util.ValidationUtils.requireNonNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a player in the Tic-Tac-Toe game.
 * A player is associated with a {@link Figure} (X or O)
 * and may be a human or a computer-controlled player.
 */
public final class Player implements Serializable {
    private final Figure figure;
    private final String name;
    private final boolean isComputerPlayer;

    /**
     * Creates a player with a specified figure and computer control flag.
     * @param figure the figure (X or O) assigned to the player
     * @param isComputerPlayer true if the player is controlled by the computer, false otherwise
     * @throws GameValidationException if the figure is null
     */
    public Player(final Figure figure, final String name, final boolean isComputerPlayer) {
        requireNonNull(figure, ErrorReason.NULL_FIGURE);
        this.figure = figure;
        this.name = name;
        this.isComputerPlayer = isComputerPlayer;
    }

    public Player(Figure figure, String name) {
        this(figure, name, false);
    }

    public Player(Figure figure) {
        this(figure,"", false);
    }

    public String getName() {
        return name;
    }

    public Figure getFigure() {
        return figure;
    }

    public boolean isComputerPlayer() {
        return isComputerPlayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return isComputerPlayer == player.isComputerPlayer && figure == player.figure;
    }

    @Override
    public int hashCode() {
        return Objects.hash(figure, isComputerPlayer);
    }
}

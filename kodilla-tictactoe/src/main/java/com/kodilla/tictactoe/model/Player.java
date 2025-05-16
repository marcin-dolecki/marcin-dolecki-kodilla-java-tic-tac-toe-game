package com.kodilla.tictactoe.model;

import java.util.Objects;

/**
 * Represents a player in the Tic-Tac-Toe game.
 * A player is associated with a {@link Figure} (X or O)
 * and may be a human or a computer-controlled player.
 */
public class Player {
    private final Figure figure;
    private final boolean isComputerPlayer;

    /**
     * Creates a player with a specified figure and computer control flag.
     *
     * @param figure the figure (X or O) assigned to the player
     * @param isComputerPlayer true if the player is controlled by the computer, false otherwise
     */
    public Player(Figure figure, boolean isComputerPlayer) {
        this.figure = figure;
        this.isComputerPlayer = isComputerPlayer;
    }

    /**
     * Creates a human player with the specified figure.
     *
     * @param figure
     */
    public Player(Figure figure) {
        this(figure, false);
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

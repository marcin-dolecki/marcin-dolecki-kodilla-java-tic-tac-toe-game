package com.kodilla.tictactoe.classes;

import com.kodilla.tictactoe.enums.Figure;

import java.util.Objects;

public class Player {
    private final Figure figure;
    private final boolean isComputerPlayer;

    public Player(Figure figure, boolean isComputerPlayer) {
        this.figure = figure;
        this.isComputerPlayer = isComputerPlayer;
    }

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

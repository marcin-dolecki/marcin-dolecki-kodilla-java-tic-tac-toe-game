package com.kodilla.tictactoe.model;

import java.time.LocalDate;

public class Score {
    private String username;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int gamesDrawn;
    private LocalDate lastUpdated;

    public Score(String username, int gamesPlayed, int gamesWon, int gamesLost, int gamesDrawn, LocalDate lastUpdated) {
        this.username = username;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.gamesDrawn = gamesDrawn;
        this.lastUpdated = lastUpdated;
    }

    public void addGame(GameResult result) {
        this.gamesPlayed++;
        this.lastUpdated = LocalDate.now();
        switch(result) {
            case WIN:
                this.gamesWon++;
                break;
            case LOST:
                this.gamesLost++;
                break;
            case DRAW:
                this.gamesDrawn++;
                break;
            default:
                break;
        }
    }
    
    @Override
    public String toString() {
        return username + "|" + gamesPlayed + "|" + gamesWon + "|" + gamesLost + "|" + gamesDrawn + "|" + lastUpdated;
    }

    public static Score fromString(String line) {
        String[] parts = line.split("\\|");
        return new Score(
            parts[0],
            Integer.parseInt(parts[1]),
            Integer.parseInt(parts[2]),
            Integer.parseInt(parts[3]),
            Integer.parseInt(parts[4]),
            LocalDate.parse(parts[5])
        );
    }

    public String getUsername() {
        return username;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public int getGamesDrawn() {
        return gamesDrawn;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }
}

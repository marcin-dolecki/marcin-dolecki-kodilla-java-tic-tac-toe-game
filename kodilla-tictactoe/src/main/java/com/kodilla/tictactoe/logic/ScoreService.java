package com.kodilla.tictactoe.logic;

import com.kodilla.tictactoe.model.GameResult;
import com.kodilla.tictactoe.model.Score;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ScoreService {

    public static void addOrUpdateScore(List<Score> scores, String username, GameResult result) {
        Optional<Score> existing = scores.stream()
                .filter(score -> score.getUsername().equalsIgnoreCase(username))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().addGame(result);
        } else {
            scores.add(new Score(username, result, LocalDate.now()));
        }

        scores.sort(
            Comparator.<Score>comparingInt(Score::getGamesWon).reversed()
                .thenComparing(Comparator.comparingInt(Score::getGamesLost))
                .thenComparing(Comparator.comparingInt(Score::getGamesDrawn).reversed())
        );
    }
}

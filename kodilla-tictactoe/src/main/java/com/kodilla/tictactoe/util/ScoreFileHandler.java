package com.kodilla.tictactoe.util;

import com.kodilla.tictactoe.model.Score;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScoreFileHandler {
    private static final Path FILE_PATH = Paths.get("kodilla-tictactoe/src/main/resources/static/textFiles/scores.txt");

    public static List<Score> loadScores() {
        if (!Files.exists(FILE_PATH)) return new ArrayList<>();

        try (Stream<String> lines = Files.lines(FILE_PATH)) {
            return lines.map(Score::fromString).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Read error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void saveScores(List<Score> scores) {
        try (BufferedWriter writer = Files. newBufferedWriter(FILE_PATH)) {
            for (Score score : scores) {
                writer.write(score.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Write error: " + e.getMessage());
        }
    }
}

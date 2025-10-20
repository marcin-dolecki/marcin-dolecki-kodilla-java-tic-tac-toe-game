package com.kodilla.tictactoe.core;

import java.io.*;
import java.util.Optional;

public class SaveGameManager {
    private static final File GAME_FILE = getGameFile();

    public static void saveGame(GameState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GAME_FILE))) {
            oos.writeObject(state);
        } catch (Exception e) {
            System.out.println("Error saving game state: " + e.getMessage());
        }
    }

    public static Optional<GameState> loadGame() {
        if (!GAME_FILE.exists()) return Optional.empty();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GAME_FILE))) {
            return Optional.of((GameState) ois.readObject());
        } catch (Exception e) {
            System.out.println("Error loading game state: " + e.getMessage());
            return Optional.empty();
        }
    }

    public static void deleteSave() {
        try {
            if (GAME_FILE.exists()) {
                if (GAME_FILE.delete()) {
                    // nothing
                } else {
                    System.out.println("Failed to delete save file.");
                }
            }
        } catch (SecurityException e) {
            System.out.println("Error deleting save file: " + e.getMessage());
        }
    }

    private static File getGameFile() {
        File currentDir = new File(System.getProperty("user.dir"));
        if (currentDir.getName().equals("kodilla-tictactoe")) {
            return new File(currentDir, "gameState.sav");
        } else {
            return new File(currentDir, "kodilla-tictactoe/gameState.sav");
        }
    }
}

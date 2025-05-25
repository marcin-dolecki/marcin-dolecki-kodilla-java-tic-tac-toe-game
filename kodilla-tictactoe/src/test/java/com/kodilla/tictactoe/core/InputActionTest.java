package com.kodilla.tictactoe.core;

import org.junit.jupiter.api.Test;

import static com.kodilla.tictactoe.core.InputAction.Type.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class InputActionTest {
    @Test
    void shouldCreateMoveAction() {
        InputAction action = InputAction.move(2, 3);

        assertThat(action.getType()).isEqualTo(MOVE);
        assertThat(action.getRow()).isEqualTo(2);
        assertThat(action.getCol()).isEqualTo(3);
    }

    @Test
    void shouldCreateRestartAction() {
        InputAction action = InputAction.restart();

        assertThat(action.getType()).isEqualTo(RESTART);
        assertThat(action.getRow()).isEqualTo(-1);
        assertThat(action.getCol()).isEqualTo(-1);
    }

    @Test
    void shouldCreateQuitAction() {
        InputAction action = InputAction.quit();

        assertThat(action.getType()).isEqualTo(QUIT);
        assertThat(action.getRow()).isEqualTo(-1);
        assertThat(action.getCol()).isEqualTo(-1);
    }

    @Test
    void shouldBeEqualObjects() {
        InputAction move = InputAction.move(1, 1);
        InputAction differentMove = InputAction.move(2, 2);
        InputAction restart = InputAction.restart();

        assertThat(move).isNotEqualTo(differentMove);
        assertThat(move).isNotEqualTo(restart);
    }
}

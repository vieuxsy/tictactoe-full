package com.itd.tdd;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Demo for tdd framework util AssertJ
 * Benefits:
 * - fluent
 * - one import
 * use org.assertj.core.api.Java6Assertions.assertThat(.isEqualTo)(.hasSize)(.containsExactlyInAnyOrder)(.containsExactly)
 */
public class GameTurnAssertJTest {
    private GameTurn round;
    private final int id = 3;
    private final int x = 1;
    private final int y = 1;
    private final char player = 'X';

    @Before
    public void before() {
        round = new GameTurn(id, player, x, y);
    }

    @Test
    public void testSms() {

    }

    // check id is set after instantiation
    @Test
    public void whenInstantiatedThenIdIsSet() {
        assertThat(round.getId()).isEqualTo(id).isNotEqualTo(4711);
    }

    // check x is set after instantiation
    @Test
    public void whenInstantiatedThenXIsSet() {
        assertThat(round.getX()).isEqualTo(x);
    }

    // check y is set after instantiation
    @Test
    public void whenInstantiatedThenYIsSet() {
        assertThat(round.getY()).isEqualTo(y);
    }

    // check player is set after instantiation
    @Test
    public void whenInstantiatedThenPlayerIsSet() {
        assertThat(round.getPlayer()).isEqualTo(player);
    }

    // check allowedChars are O and X after instantiation
    @Test
    public void whenInstantiatedThenAllowedCharsAreOandX() {
        assertThat(round.getAllowedChars()).hasSize(2).containsExactlyInAnyOrder('X', 'O');
//        assertThat(round.getAllowedChars()).hasSize(2).containsExactly('O', 'X');
    }
}

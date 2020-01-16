package com.itd.tdd;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static com.itd.tdd.itdmatcher.ItdMatcher.itdEqualTo;

/**
 * Demo for tdd framework util Hamcrest
 * Benefits: https://objectpartners.com/2013/09/18/the-benefits-of-using-assertthat-over-other-assert-methods-in-unit-tests/
 * - readability
 * - type safety
 * - natively supported by JUnit (org.junit.Assert)
 * - portability: combine with TestNG and JUnit
 * - custom matchers
 *
 * use org.hamcrest.MatcherAssert.assertThat and org.junit.Assert.assertThat (with and without message),
 * is, not, equalTo, hasSize, containsInAnyOrder from org.hamcrest.Matchers.
 * implement ItdMatcher.itdEqualTo and use it here
 */
public class GameTurnHamcrestTest {
    private GameTurn round;
    private final int id = 3;
    private final int x = 1;
    private final int y = 1;
    private final char player = 'X';

    @Before
    public void before() {
        round = new GameTurn(id, player, x, y);
    }

    // check id is set after instantiation
    @Test
    public void whenInstantiatedThenIdIsSet() {
        assertThat("After instantiation id is set", round.getId(), is(equalTo(id)));
        assertThat("After instantiation id is set", round.getId(), is(not(equalTo(4711)))); // unnecessary
    }

    // check x is set after instantiation
    @Test
    public void whenInstantiatedThenXIsSet() {
        org.junit.Assert.assertThat(round.getX(), is(equalTo(x)));
    }

    // check y is set after instantiation
    @Test
    public void whenInstantiatedThenYIsSet() {
        assertThat(round.getY(), equalTo(y));
        assertThat(round.getY(), itdEqualTo( y));
    }

    // check player is set after instantiation
    @Test
    public void whenInstantiatedThenPlayerIsSet() {
        assertThat(round.getPlayer(), is( player));
    }

    // check allowedChars are O and X after instantiation
    @Test
    public void whenInstantiatedThenAllowedCharsAreOandX() {
        assertThat(round.getAllowedChars(), hasSize(2)); // unnecessary
        assertThat(round.getAllowedChars(), containsInAnyOrder('O', 'X'));
    }
}

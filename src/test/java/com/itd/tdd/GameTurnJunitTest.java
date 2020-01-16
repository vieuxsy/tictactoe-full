package com.itd.tdd;

import org.junit.*;
import org.junit.rules.TestName;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Demo for tdd framework JUnit
 * use assertEquals, assertTrue (with and without message
 */
public class GameTurnJunitTest {
    private GameTurn round;
    private final int id = 3;
    private final int x = 1;
    private final int y = 1;
    private final char player = 'X';

    @Rule
    public final TestName name = new TestName();

    @BeforeClass
    public static void beforeClass() {
        // called once on class initialization time
    }

    @AfterClass
    public static void afterClass() {
        // called once after all methods are executed
    }

    @Before
    public void before() {
        // called before each method execution
        round = new GameTurn(id, player, x, y);
    }

    @After
    public void after() {
        // called after each method execution
    }

    // check id is set after instantiation
    @Test
    public void whenInstantiatedThenIdIsSet() {
        assertEquals("After instantiation id is set", id, round.getId());
    }

    // check x is set after instantiation
    @Test
    public void whenInstantiatedThenXIsSet() {
        assertEquals(x, round.getX());
    }

    // check y is set after instantiation
    @Test
    public void whenInstantiatedThenYIsSet() {
        assertEquals(y, round.getY());
    }

    // check player is set after instantiation
    @Test
    public void whenInstantiatedThenPlayerIsSet() {
        assertEquals(player, round.getPlayer());
    }

    // check allowedChars are O and X after instantiation
    @Test
    public void whenInstantiatedThenAllowedCharsAreOandX() {
        List<Character> allowedChars = Arrays.asList('X', 'O');
        assertEquals(2, round.getAllowedChars().size());
        assertTrue(round.getAllowedChars().containsAll(allowedChars));
    }

    // check Rule TestName.getMethodName
    @Test
    public void whenGetMethodNameOfTestNameRuleInvokedThenReturnNameOfThisTest() {
        assertEquals("whenGetMethodNameOfTestNameRuleInvokedThenReturnNameOfThisTest", name.getMethodName());
    }

    // optional:
    // play around with Rules ExpectedException and TemporaryFolder
    // https://github.com/junit-team/junit4/wiki/rules (JUnit interceptors)
}
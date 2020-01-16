package com.itd.tdd;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Demo TDD
 */
public class GameTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private Game game;
    private GameState state;

    @Before
    public final void before() {
        state = mock(GameState.class);
        doReturn(true).when(state).clear();
        doReturn(true).when(state).save(any(GameTurn.class));
        game = new Game(state);
    }


    /****************************************************************************************
     * R1: play one piece within a board of 3x3
     ****************************************************************************************/

    /**
     * R1.1: throw RuntimeException if x not valid
     */
    @Test
    public void whenXOutsideBoardThenThrowRuntimeException() {
        exception.expect(RuntimeException.class);
        game.play(-1, 2);
    }

    /**
     * R1.2: throw RuntimeException if y not valid
     */
    @Test
    public void whenYOutsideBoardThenThrowRuntimeException() {
        exception.expect(RuntimeException.class);
        game.play(2, 5);
    }

    /**
     * R1.3: throw RuntimeException if field on x,y occupied
     */
    @Test
    public void whenOccupiedThenThrowRuntimeException() {
        game.play(1, 1);
        game.play(2, 1);
        exception.expect(RuntimeException.class);
        game.play(2, 1);
    }

    /*****************************************************************************************
     * R2: track last and next player
     ****************************************************************************************/

    /**
     * R2.1: first player is X
     */
    @Test
    public void givenFirstRoundWhenNextPlayerThenX() {
        assertEquals('X', game.nextPlayer());
    }

    /**
     * R2.2: if lastplayer was X, nextplayer is O
     */
    @Test
    public void givenLastRoundWasXWhenNextPlayerThenO() {
        game.play(1, 1);
        assertEquals('O', game.nextPlayer());
    }

    /**
     * R2.3: if lastplayer was O, nextplayer is X
     * N.B: false positiv, works without changing code, therefore useless
     */


    /*****************************************************************************************
     * R3: player who connects a line (horizontal, vertical, or diagonal) first wins
     ****************************************************************************************/

    /**
     * R3.1: before line connected play is in progress
     */
    @Test
    public void whenPlayThenInProgress() {
        GameResult actual = game.play(1, 1);
        assertEquals(GameResult.IN_PROGRESS, actual);
    }

    /**
     * R3.2: player who connects horizontal line first wins
     */
    @Test
    public void whenPlayAndWholeHorizontalLineFilledThenWinner() {
        game.play(1, 1); // X
        game.play(1, 2); // O
        game.play(2, 1); // X
        game.play(2, 2); // O
        GameResult actual = game.play(3, 1); // X
        assertEquals(GameResult.WINNER_IS_X, actual);
    }

    /**
     * R3.3: player who connects vertical line first wins
     */
    @Test
    public void whenPlayAndWholeVerticalLineFilledThenWinner() {
        game.play(2, 1); // X
        game.play(1, 1); // O
        game.play(3, 1); // X
        game.play(1, 2); // O
        game.play(2, 2); // X
        GameResult actual = game.play(1, 3); // O
        assertEquals(GameResult.WINNER_IS_O, actual);
    }

    /**
     * R3.4: player who connects top-bottom diagonal line first wins
     */
    @Test
    public void whenPlayAndTopBottomDiagonalLineFilledThenWinner() {
        game.play(1, 1); // X
        game.play(1, 2); // O
        game.play(2, 2); // X
        game.play(1, 3); // O
        GameResult actual = game.play(3, 3); // O
        assertEquals(GameResult.WINNER_IS_X, actual);
    }

    /**
     * R3.5: player who connects bottom-top diagonal line first wins
     */
    @Test
    public void whenPlayAndBottomTopDiagonalLineFilledThenWinner() {
        game.play(1, 3); // X
        game.play(1, 1); // O
        game.play(2, 2); // X
        game.play(1, 2); // O
        GameResult actual = game.play(3, 1); // O
        assertEquals(GameResult.WINNER_IS_X, actual);
    }

    /*****************************************************************************************
     * R4: play is draw if all fields are set and no line connected
     ****************************************************************************************/

    @Test
    public void whenAllFieldsFilledThenDraw() {
        game.play(1, 1);
        game.play(1, 2);
        game.play(1, 3);
        game.play(2, 1);
        game.play(2, 3);
        game.play(2, 2);
        game.play(3, 1);
        game.play(3, 3);
        GameResult actual = game.play(3, 2);
        assertEquals(GameResult.DRAW, actual);
    }

    /*****************************************************************************************
     * R5: use MongoDB as persistent storage for the game state. (TicTacToeGameStateTest)
     ****************************************************************************************/


    /*****************************************************************************************
     * R6: save each round to DB and clean DB for each new game session
     ****************************************************************************************/

    /**
     * R6.1: game state should be initialized at session begin (intantiation)
     */
    @Test
    public void whenInstantiatedThenSetState() {
        assertNotNull(game.getState());
    }

    /**
     * R6.2: each game round should be saved to DB. Focus on current class not external dependencies
     */
    @Test
    public void whenPlayThenSaveRoundIsInvoked() {
        GameTurn round = new GameTurn(1, 'X', 1, 3);
        game.play(round.getX(), round.getY());
        verify(state, times(1)).save(round);
    }

    /**
     * R6.3: throw RuntimeException if save failed. Save should defaultly return true
     */
    @Test
    public void whenPlayAndSaveReturnsFalseThenThrowRuntimeException() {
        doReturn(false).when(state).save(any(GameTurn.class));
        GameTurn round = new GameTurn(1, 'X', 1, 3);
        exception.expect(RuntimeException.class);
        game.play(round.getX(), round.getY());
    }

    /**
     * R6.4: the round should increase after each play
     */
    @Test
    public void whenPlayMultipleTimesThenTurnIncreased() {
        GameTurn round1 = new GameTurn(1, 'X', 1, 1);
        GameTurn round2 = new GameTurn(2, 'O', 1, 2);

        game.play(1, 1);
        verify(state, times(1)).save(round1);

        game.play(1, 2);
        verify(state, times(1)).save(round2);
    }

    /**
     * R6.5: game state data should be cleared at session begin (intantiation)
     */
    @Test
    public void whenInstantiatedThenStateClearInvoked() {
        verify(state, times(1)).clear();
    }

    /**
     * R6.6: throw RuntimeException if clearing state data fails
     */
    @Test
    public void whenInstantiatedAndClearReturnsFalseThenThrowRuntimeException() {
        doReturn(false).when(state).clear();
        exception.expect(RuntimeException.class);
        new Game(state);
    }

    /****************************************************************************************
     * BP1: check the code coverage
     ****************************************************************************************/

    /****************************************************************************************
     * BP2: integrate real MongoDb. TicTacToeIntTest
     ****************************************************************************************/

    /****************************************************************************************
     * R7: create a web UI to input x, y and call play. The played rounds should be tracked
     * implement TicTacToeGameUiController
     ****************************************************************************************/

    /****************************************************************************************
     * R8: create automated systemtest using ui. implement GameUiSeleniumTest (UI-Testing)
     ****************************************************************************************/

    /****************************************************************************************
     * R9: create automated acceptancetest using ui and cucumber.
     * implement TicTacToeCucumberSteps
     ****************************************************************************************/

}

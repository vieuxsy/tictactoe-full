package com.itd.tdd;

import javax.ejb.Singleton;
import java.io.Serializable;

@Singleton
public class TicTacToeSession implements Serializable {

    private Game currentGame;

    public void startNewGame() {
        currentGame = new Game();
    }

    public GameResult playTurn(final int x, int y) {
        if (currentGame == null) {
            startNewGame();
        }
        GameResult result;
        try {
            result = currentGame.play(x, y);
            result.setPlayer(currentGame.getLastPlayer()+"");
        } catch (RuntimeException re) {
            result = GameResult.ERROR;
            result.msg = re.getMessage();
        }
        return result;
    }
}

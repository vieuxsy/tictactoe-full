package com.itd.tdd;

public class Game {
    public static final char UNOCCUPIED = '\0';
    public static final String ERR_OCCUPIED = "Field is already occupied!";
    private Character[][] board = {
        {UNOCCUPIED, UNOCCUPIED, UNOCCUPIED}
        , {UNOCCUPIED, UNOCCUPIED, UNOCCUPIED}
        , {UNOCCUPIED, UNOCCUPIED, UNOCCUPIED}
    };

    public char getLastPlayer() {
        return lastPlayer;
    }

    private char lastPlayer = UNOCCUPIED;
    private static final int SIZE = 3;
    private GameState state;
    public GameState getState() {
        return state;
    }
    private int turn = 0;

    public Game() {
        this(new GameState());
    }

    protected Game(GameState state) {
        this.state = state;
        if (!this.state.clear()) {
            throw new RuntimeException("Dropping DB collection failed");
        }
    }

    public GameResult play(int x, int y) {
        checkCoordinate(x);
        checkCoordinate(y);
        lastPlayer = nextPlayer();
        setField(new GameTurn(++turn, lastPlayer, x, y));
        if (isWin(x, y)) {
            return lastPlayer == 'X' ? GameResult.WINNER_IS_X : GameResult.WINNER_IS_O;
        } else if (isDraw()) {
            return GameResult.DRAW;
        } else {
            return GameResult.IN_PROGRESS;
        }
    }

    public char nextPlayer() {
        return lastPlayer == 'X' ? 'O' : 'X';
    }

    private void checkCoordinate(int coordinate) {
        if (coordinate < 1 || coordinate > 3) {
            throw new RuntimeException("Coordinate must be between 1 and 3");
        }
    }

    private void setField(GameTurn round) {
        if (board[round.getX() - 1][round.getY() - 1] != UNOCCUPIED) {
            throw new RuntimeException(ERR_OCCUPIED);
        } else {
            board[round.getX() - 1][round.getY() - 1] = round.getPlayer();
            saveState(round);
        }
    }

    private void saveState(GameTurn round) {
        if (!getState().save(round)) {
            throw new RuntimeException("Could not save state to DB!");
        }
    }

    // 'X'=88 * 3 = 264 => X wins, 'O'=79 * 3 = 237 => O wins
    private boolean isWin(int x, int y) {
        int playerTotal = lastPlayer * SIZE;
        char horizontal = UNOCCUPIED;
        char vertical = UNOCCUPIED;
        char diagonal1 = UNOCCUPIED;
        char diagonal2 = UNOCCUPIED;
        for (int i = 0; i < SIZE; i++) {
            horizontal += board[i][y - 1];
            vertical += board[x - 1][i];
            diagonal1 += board[i][i];
            diagonal2 += board[i][SIZE - i - 1];
        }
        return horizontal == playerTotal
                || vertical == playerTotal
                || diagonal1 == playerTotal
                || diagonal2 == playerTotal;
    }

    // if one field is UNOCCUPIED false, else true
    private boolean isDraw() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (board[x][y] == UNOCCUPIED) {
                    return false;
                }
            }
        }
        return true;
    }
}

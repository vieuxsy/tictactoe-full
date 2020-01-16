package com.itd.tdd;
import org.jongo.marshall.jackson.oid.MongoId;

import java.util.Arrays;
import java.util.List;

public class GameTurn {

    @MongoId
    private int id;
    private char player;
    private int x;
    private int y;
    private List<Character> allowedChars;

    public GameTurn() {
        allowedChars = Arrays.asList('X', 'O');
    }

    public GameTurn(int id, char player, int x, int y) {
        this();
        this.id = id;
        this.player = player;
        this.x = x;
        this.y = y;
    }

    public char getPlayer() {
        return player;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Character> getAllowedChars() {
        return allowedChars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameTurn that = (GameTurn) o;

        if (id != that.id) return false;
        if (x != that.x) return false;
        if (y != that.y) return false;
        return player == that.player;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + (int) player;
        return result;
    }

    @Override
    public String toString() {
        return "GameTurn{" + "id=" + id + ", player=" + player + ", x=" + x + ", y=" + y + '}';
    }
}

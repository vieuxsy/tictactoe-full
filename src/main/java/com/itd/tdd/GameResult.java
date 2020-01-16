package com.itd.tdd;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GameResult {
    IN_PROGRESS
    , WINNER_IS_X
    , WINNER_IS_O
    , DRAW
    , ERROR
    ;

    String player;
    String msg;
    String result = this.name();

    public String getPlayer() {
        return player;
    }

    public void setPlayer(final String player) {
        this.player = player;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(final String result) {
        this.result = result;
    }
}

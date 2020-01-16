package com.itd.jaxrs;

import com.itd.tdd.GameResult;
import com.itd.tdd.GameTurn;
import com.itd.tdd.TicTacToeSession;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("Games")
public class GameResource {

	@Inject
	TicTacToeSession tttService;

	@GET
	public String foo() {
		return "game at " + System.currentTimeMillis();
	}

	@PUT
	public GameResult playTurn(GameTurn turn) {
		return tttService.playTurn(turn.getX(), turn.getY());
	}

	@DELETE
	public void resetGame() {
		tttService.startNewGame();
	}
}

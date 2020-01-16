package com.itd.tdd;

import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Demo for integration test
 * - tests should run fast. Therefore test only what is not yet unit tested
 */
public class GameIntTest {
    /****************************************************************************************
     * BP2: integrate real MongoDb.
     ****************************************************************************************/

    private final int id = 1;
    private final int x = 1;
    private final int y = 1;

    @Test
    public void testSms() {
        try {
        String originator = "+491797913398";
        String recipient = "+491797913398";
        String message = "Hi du, ich sende Dir eine SMS über Java. Ist das nicht toll?";
        String username = "admin";
        String password = "admin";

        String requestUrl  = "http://127.0.0.1:9501/api?action=sendmessage&" +
                "username=" + URLEncoder.encode(username, "UTF-8") +
                "&password=" + URLEncoder.encode(password, "UTF-8") +
                "&recipient=" + URLEncoder.encode(recipient, "UTF-8") +
                "&messagetype=SMS:TEXT" +
                "&messagedata=" + URLEncoder.encode(message, "UTF-8") +
                "&originator=" + URLEncoder.encode(originator, "UTF-8") +
                "&serviceprovider=GSMModem1" +
                "&responseformat=html";

        URL url = new URL(requestUrl);

        HttpURLConnection uc = (HttpURLConnection)url.openConnection();

        System.out.println(uc.getResponseMessage());

        uc.disconnect();

    } catch(Exception ex) {

        System.out.println(ex.getMessage());

    }
    }

    /**
     * 1: interface to MongoDb (drop and save) should work
     */
    @Test
    public void givenMongoDbIsUpWhenPlayThenDropAndSaveWork() throws UnknownHostException {
        Game game = new Game();
        game.play(x, y);
        GameTurn expectedRound = new GameTurn(id, 'X', x, y);
        GameTurn persistedRound = game.getState().findById(id);
        assertThat(persistedRound, is(expectedRound));
    }
}

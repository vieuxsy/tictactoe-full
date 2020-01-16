package com.itd.tdd;

import com.mongodb.MongoException;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.NonStrictExpectations;
import mockit.Mocked;
import org.jongo.MongoCollection;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Demo for mocking framework JMockit
 */
public class GameStateJMockitTest {

    private GameState state;

    @Mocked
    private MongoCollection mongoCollection;

    private GameTurn round;

    @Before
    public void setUp() throws UnknownHostException {
        round = new GameTurn(3, 'X', 2, 1);

        // instantiate class to test
        state = new GameState();

        // use Deencapsulation to set private field mongoCollection
        Deencapsulation.setField(state, "mongoCollection", mongoCollection);
    }

    // check save invocation.
    // use NonStrictExpectations and times to mock mongoCollection.save
    @Test
    public void whenSaveThenInvokeMongoCollectionSaveAndReturnTrue() throws Exception {
        new NonStrictExpectations() {
            {
                mongoCollection.save(any);
                times = 1;
            }
        };
        assertThat(state.save(round), is(equalTo(true)));
    }

    // check when exception occurs during save, then save returns false
    // use Expectations and result to mock mongoCollection.save
    @Test
    public void givenMongoExceptionWhenSaveThenReturnFalse() {
        new Expectations() {
            {
                mongoCollection.save(any);
                result = new MongoException("failed");
            }
        };
        assertThat(state.save(round), is(equalTo(false)));
    }

    // check clear invocation
    // use Expectations to mock mongoCollection.drop()
    @Test
    public void whenClearThenInvokeMongoCollectionDrop() {
        new Expectations() {
            {
                mongoCollection.drop();
            }
        };
        assertThat(state.clear(), is(equalTo(true)));
    }

    // check when exception occurs during clear, then clear returns false
    // use Expectations and result to mock mongoCollection.drop
    @Test
    public void givenMongoExceptionWhenClearThenReturnFalse() {
        new Expectations() {
            {
                mongoCollection.drop();
                result = new MongoException("failed");
            }
        };
        assertThat(state.clear(), is(equalTo(false)));
    }
}

package com.itd.tdd;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

public class GameState {

    public static final String DB_NAME = "ttt-db";
    public static final String COLLECTION_NAME = "ttt-collection";
    private MongoCollection mongoCollection;

    public GameState() {
        DB db = new MongoClient().getDB(DB_NAME);
        mongoCollection = new Jongo(db).getCollection(COLLECTION_NAME);
    }

    public boolean save(GameTurn round) {
        try {
            getMongoCollection().save(round);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clear() {
        try {
            getMongoCollection().drop();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public GameTurn findById(int id) {
        return getMongoCollection().findOne("{_id: #}", id).as(GameTurn.class);
    }

    public MongoCollection getMongoCollection() {
        return mongoCollection;
    }
}

package com.smartcodellc.gomon;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.ObjectId;

import static com.smartcodellc.gomon.util.Assert.notNull;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class DBObjectLoader {

    private DBCollection collection;

    public DBObjectLoader(DBCollection collection) {
        this.collection = collection;
    }

    public DBObject load(String id) {
        notNull(id, "The _id value must be provided.");
        BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));
        DBObject dbObject = collection.findOne(query);
        return dbObject;
    }
}

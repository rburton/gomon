package com.smartcodellc.gomon;

import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.Mongo;
import com.smartcodellc.gomon.definition.GomonMapping;
import com.smartcodellc.gomon.impl.GomonSessionImpl;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * GomonSessionFactory sessionFactory = new GomonSessionFactory(mongo, "blogs");
 * sessionFactory.getSession("collection");
 * sessionFactory.getSession(Blog.class);
 *
 * @author Richard L. Burton III - SmartCode LLC
 */
public class GomonSessionFactory {

    private Logger logger = Logger.getLogger(GomonSessionFactory.class);

    private Map<Class, GomonMapping> mappings = new HashMap<Class, GomonMapping>();

    private Mongo mongo;

    private DB db;

    public GomonSessionFactory(Mongo mongo, String database) {
        this.mongo = mongo;
        DBAddress address = mongo.getAddress();
        logger.info("Mongo Version: -----> " + mongo.getVersion());
        logger.info("Mongo Host: --------> " + address.getHost());
        logger.info("Mongo Port: --------> " + address.getPort());
        logger.info("Mongo DB Name: -----> " + address.getDBName());
        db = mongo.getDB(database);
    }

    public GomonSession getSession(String collection) {
        return new GomonSessionImpl(db.getCollection(collection));
    }

    public GomonSession getSession(Class collection) {
        GomonMapping mapping = mappings.get(collection);
        return new GomonSessionImpl(db.getCollection(mapping.getAlias()));
    }

}

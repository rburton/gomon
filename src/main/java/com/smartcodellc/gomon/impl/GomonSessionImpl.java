package com.smartcodellc.gomon.impl;

import com.mongodb.*;
import com.smartcodellc.gomon.*;
import com.smartcodellc.gomon.proxy.DBObjectProxy;
import com.smartcodellc.gomon.proxy.LazyDBObjectProxy;
import net.sf.cglib.proxy.MethodInterceptor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.smartcodellc.gomon.util.Assert.notNull;

/**
 *
 * <pre>
 * Mongo m = new Mongo();
 * DB db = m.getDB("mongo-orm");
 * GomonSessionImpl session = new GomonSessionImpl(db.getCollection("blog"));
 * Blog blog = session.get("4b7cb8f31fbfd6111d09d21e", Blog.class);
 * </pre>
 * <br/>
 * <br/>
 * <pre>
 * Mongo m = new Mongo();
 * DB db = m.getDB("mongo-orm");
 * GomonSessionImpl session = new GomonSessionImpl(db.getCollection("blog"));
 * Criteria criteria = Criteria.start("type")
 *                      .in(asList("computers", "books"));
 *
 * List<Blog> blogs = session.get(criteria, Blog.class);
 * </pre>
 * @author Richard L. Burton III - SmartCode LLC
 */
public class GomonSessionImpl implements GomonSession {

    private DBCollection collection;

    private GomonEnhancer enhancer;

    private DBObjectLoader dbObjectLoader;

    public GomonSessionImpl(DBCollection collection) {
        notNull(collection, "The DBCollection must not be null.");
        this.collection = collection;
        this.enhancer = new GomonEnhancer();
        dbObjectLoader = new DBObjectLoader(collection);
    }

    public <T> T get(String id, Class<T> type) {
        DBObject dbObject = dbObjectLoader.load(id);
        DBObjectProxy proxy = new DBObjectProxy(enhancer, dbObjectLoader);
        proxy.setDbObject(dbObject);
        if (dbObject != null) {
            return enhancer.enhance(proxy, type);
        }
        return null;
    }

    public <T> List<T> query(Criteria criteria, Class<T> type) {
        return query(criteria.get(), type);
    }

    public <T> List<T> query(DBObject query, Class<T> type) {
        DBCursor cursor = collection.find(query);
        if(cursor.hasNext()){
            List<T> results = new LinkedList<T>();
            while(cursor.hasNext()){
                DBObject record = cursor.next();
                DBObjectProxy proxy = new DBObjectProxy(enhancer, dbObjectLoader);
                proxy.setDbObject(record);
                results.add(enhancer.enhance(proxy, type));
            }
            return results;
        }else{
            return Collections.EMPTY_LIST;
        }
    }

    public <T> T load(String id, Class<T> type) {
        MethodInterceptor proxy = new LazyDBObjectProxy(enhancer, id, dbObjectLoader);
        return enhancer.enhance(proxy, type);
    }

}

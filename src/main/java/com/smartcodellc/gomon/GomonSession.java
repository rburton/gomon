package com.smartcodellc.gomon;

import com.mongodb.DBObject;

import java.util.List;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public interface GomonSession {
    
    <T> T get(String id, Class<T> type);

    <T> List<T> query(Criteria criteria, Class<T> type);

    <T> List<T> query(DBObject query, Class<T> type);

    <T> T load(String id, Class<T> type);
}

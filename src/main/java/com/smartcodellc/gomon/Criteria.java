package com.smartcodellc.gomon;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import static com.smartcodellc.gomon.Operators.*;

import java.util.regex.Pattern;

/**
 * Based upon http://github.com/julsonlim/mongo-java-driver
 * @author Richard L. Burton III - SmartCode LLC
 */
public class Criteria {
                     
 	private static final class NullObject {}

	private DBObject query;

	private String key;
    
	public Criteria() {
		query = new BasicDBObject();
	}

	/**
	 * Creates a new query with a document key
	 * @param key MongoDB document key
	 * @return Returns a new QueryBuilder
	 */
	public static Criteria start(String key) {
		return (new Criteria()).put(key);
	}

	/**
	 * Adds a new key to the query or sets an existing key to as current for chaining
	 * @param key MongoDB document key
	 * @return Returns the current QueryBuilder with an appended key operand
	 */
	public Criteria put(String key) {
		this.key = key;
		if(query.get(key) == null) {
			query.put(key, new NullObject());
		}
		return this;
	}

	/**
	 * Equivalent to <code>QueryBuilder.put(key)</code>. Intended for compound query chains to be more readable
	 * Example: QueryBuilder.start("a").greaterThan(1).and("b").lessThan(3)
	 * @param key MongoDB document key
	 * @return Returns the current QueryBuilder with an appended key operand
	 */
	public Criteria and(String key) {
		return put(key);
	}

	/**
	 * Equivalent to the $gt operator
	 * @param object Value to query
	 * @return Returns the current QueryBuilder with an appended "greater than" query
	 */
	public Criteria greaterThan(Object object) {
		addOperand(GT, object);
		return this;
	}

	/**
	 * Equivalent to the $gte operator
	 * @param object Value to query
	 * @return Returns the current QueryBuilder with an appended "greater than or equals" query
	 */
	public Criteria greaterThanEquals(Object object) {
		addOperand(GTE, object);
		return this;
	}

	/**
	 * Equivalent to the $lt operand
	 * @param object Value to query
	 * @return Returns the current QueryBuilder with an appended "less than" query
	 */
	public Criteria lessThan(Object object) {
		addOperand(LT, object);
		return this;
	}

	/**
	 * Equivalent to the $lte operand
	 * @param object Value to query
	 * @return Returns the current QueryBuilder with an appended "less than or equals" query
	 */
	public Criteria lessThanEquals(Object object) {
		addOperand(LTE, object);
		return this;
	}

	/**
	 * Equivalent of the find({key:value})
	 * @param object Value to query
	 * @return Returns the current QueryBuilder with an appended equality query
	 */
	public Criteria is(Object object) {
		addOperand(null, object);
		return this;
	}

	/**
	 * Equivalent of the $ne operand
	 * @param object Value to query
	 * @return Returns the current QueryBuilder with an appended inequality query
	 */
	public Criteria notEquals(Object object) {
		addOperand(NE, object);
		return this;
	}

	/**
	 * Equivalent of the $in operand
	 * @param object Value to query
	 * @return Returns the current QueryBuilder with an appended "in array" query
	 */
	public Criteria in(Object object) {
		addOperand(IN, object);
		return this;
	}

	/**
	 * Equivalent of the $nin operand
	 * @param object Value to query
	 * @return Returns the current QueryBuilder with an appended "not in array" query
	 */
	public Criteria notIn(Object object) {
		addOperand(NIN, object);
		return this;
	}

	/**
	 * Equivalent of the $mod operand
	 * @param object Value to query
	 * @return Returns the current QueryBuilder with an appended modulo query
	 */
	public Criteria mod(Object object) {
		addOperand(MOD, object);
		return this;
	}

	/**
	 * Equivalent of the $all operand
	 * @param object Value to query
	 * @return Returns the current QueryBuilder with an appended "matches all array contents" query
	 */
	public Criteria all(Object object) {
		addOperand(ALL, object);
		return this;
	}

	/**
	 * Equivalent of the $size operand
	 * @param object Value to query
	 * @return Returns the current QueryBuilder with an appended size operator
	 */
	public Criteria size(Object object) {
		addOperand(SIZE, object);
		return this;
	}

	/**
	 * Equivalent of the $exists operand
	 * @param object Value to query
	 * @return Returns the current QueryBuilder with an appended exists operator
	 */
	public Criteria exists(Object object) {
		addOperand(EXISTS, object);
		return this;
	}

	/**
	 * Passes a regular expression for a query
	 * @param regex Regex pattern object
	 * @return Returns the current QueryBuilder with an appended regex query
	 */
	public Criteria regex(Pattern regex) {
		addOperand(null, regex);
		return this;
	}

	/**
	 * Creates a <code>DBObject</code> query to be used for the driver's find operations
	 * @return Returns a DBObject query instance
	 * @throws RuntimeException if a key does not have a matching operand
	 */
	public DBObject get() {
		for(String key : query.keySet()) {
			if(query.get(key) instanceof NullObject) {
				throw new QueryBuilderException("No operand for key:" + key);
			}
		}
		return query;
	}

	private void addOperand(String op, Object value) {
		if(op == null) {
			query.put(key, value);
			return;
		}

		Object storedValue = query.get(key);
		BasicDBObject operand;
		if(!(storedValue instanceof DBObject)) {
			operand = new BasicDBObject();
			query.put(key, operand);
		} else {
			operand = (BasicDBObject)query.get(key);
		}
		operand.put(op, value);
	}

	@SuppressWarnings("serial")
	static class QueryBuilderException extends RuntimeException {
		QueryBuilderException(String message) {
			super(message);
		}
	}
}

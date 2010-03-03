package com.smartcodellc.gomon;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.smartcodellc.domain.Person;
import com.smartcodellc.gomon.impl.GomonSessionImpl;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.Assert.*;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class GomonSessionTest {

    private GomonSession session;

    @Test
    public void verifySuccessfulGet() {
        Person person = session.get("4b7cb8f31fbfd6111d09d21e", Person.class);
        assertEquals("Richard", person.getName());
        assertEquals(30, person.getAge());
    }
    
    @Test
    public void verifyNullForGetWithNoMatches() {
        Person person = session.get("111111111111111111111111", Person.class);
        assertNull(person);
    }

    @Test
    public void verifySuccessfulLoad() {
        Person person = session.load("4b7cb8f31fbfd6111d09d21D", Person.class);
        assertNotNull(person);
    }

    @Test
    public void verifyExceptionForLoadWithNoMatches() {
        Person person = session.load("4b7cb8f31fbfd6111d09d21D", Person.class);
        try {
            assertEquals("Richard", person.getName());
            fail("Failed!!");
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    public void verifyCriteriaList(){
        Criteria criteria = Criteria.start("name").in(asList("Richard"));
        List<Person> people = session.query(criteria, Person.class);
        assertEquals(1, people.size());
        Person person = people.get(0);
        assertEquals("Richard", person.getName());
        assertEquals(30, person.getAge());
    }

    @Test
    public void verifyCriteriaWithNestedChild(){
        Criteria criteria = Criteria.start("name").in(asList("Burton"));
        List<Person> people = session.query(criteria, Person.class);
        assertEquals(1, people.size());
        Person person = people.get(0);
        assertEquals("Burton", person.getName());
        assertEquals(30, person.getAge());

        Person child = person.getChild();
        assertNotNull(child);
        assertEquals("James", child.getName());
        assertEquals(30, child.getAge());
    }

    @Test
    public void verifyGetWithDBRef() {
        Person person = session.get("4b7e08ec1fbfd6111d09d220", Person.class);
        assertEquals("Jim", person.getName());
        Person child = person.getChild();
        assertNotNull(child);
        assertEquals("Richard", child.getName());
        assertEquals(30, child.getAge());
    }

    @Test
    public void verifyList() {
        Person person = session.get("4b7e15a91fbfd6111d09d221", Person.class);
        assertEquals("Friends", person.getName());
        List<Person> friends = person.getFriends();
        assertNotNull(friends);
        assertEquals(1, friends.size());
        Person child = friends.get(0);
        assertNotNull(child);
        assertEquals("James", child.getName());
        assertEquals(30, child.getAge());
    }

    @Before
    public void setup() throws UnknownHostException {
        Mongo mongo = new Mongo();
        DB db = mongo.getDB("mongo-orm");
        session = new GomonSessionImpl(db.getCollection("sample"));
    }
    
}

package com.smartcodellc.domain;

import com.smartcodellc.gomon.annotations.Mongo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class Person implements Serializable {

    private String name;

    private int age;

    private Person child;

    @Mongo(type=Person.class)
    private List<Person> friends;

    public List<Person> getFriends() {
        return friends;
    }

    public void setFriends(List<Person> friends) {
        this.friends = friends;
    }

    public Person getChild() {
        return child;
    }

    public void setChild(Person child) {
        this.child = child;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
}

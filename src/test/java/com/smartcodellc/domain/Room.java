package com.smartcodellc.domain;

import com.smartcodellc.gomon.annotations.Mongo;

import java.util.List;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class Room {

    @Mongo("n")
    private String name;

    @Mongo(value = "m", type = Person.class)
    private List<Person> members;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getMembers() {
        return members;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (name != null ? !name.equals(room.name) : room.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

}

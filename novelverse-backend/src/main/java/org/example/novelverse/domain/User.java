package org.example.novelverse.domain;

import java.util.List;

public class User {
    Integer id;
    String name;
    String password;
    String ifauthor;
    List<Integer> shelf;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIfauthor() {
        return ifauthor;
    }

    public void setIfauthor(String ifauthor) {
        this.ifauthor = ifauthor;
    }

    public List<Integer> getShelf() {
        return shelf;
    }

    public void setShelf(List<Integer> shelf) {
        this.shelf = shelf;
    }

}

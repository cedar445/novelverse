package org.example.novelverse.dao;

import org.example.novelverse.domain.User;

import java.util.List;

public interface UserDao {
    public void insert(User user);

    public void update(User user);

    public void delete(int id);

    public List<User> getAll();

    public User getById(int id);

    public User getByName(String name);
}

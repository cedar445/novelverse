package org.example.novelverse.service;

import org.example.novelverse.domain.User;

import java.util.List;

public interface UserService {
    public void insert(User user);

    public void update(User user);

    public void delete(Integer id);

    public User getById(Integer id);

    public User getByName(String name);

    public List<User> getAll();
}

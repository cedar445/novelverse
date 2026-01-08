package org.example.novelverse.service;

import org.example.novelverse.domain.User;

import java.util.List;

public interface UserService {
    public void insert(User user);

    public void update(User user);

    public void delete(int id);

    public User getById(int id);

    public List<User> getAll();
}

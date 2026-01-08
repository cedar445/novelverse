package org.example.novelverse.dao;

import org.example.novelverse.domain.Book;

import java.util.List;

public interface BookDao {
    public void insert(Book book);

    public void update(Book book);

    public void delete(int id);

    public List<Book> getAll();

    public Book getById(int id);

    public Book getByName(String name);

}

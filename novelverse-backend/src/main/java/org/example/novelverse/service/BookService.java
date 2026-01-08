package org.example.novelverse.service;

import org.example.novelverse.controller.Result;
import org.example.novelverse.domain.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {
    public void insert(Book book);

    public void update(Book book);

    public void delete(int id);

    public Book getById(int id);

    public List<Book> getAll();

    public int uploadTxt(MultipartFile file, int author_id) throws Exception;
}

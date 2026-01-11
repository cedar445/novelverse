package org.example.novelverse.service;

import org.example.novelverse.controller.Result;
import org.example.novelverse.domain.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {
    public void insert(Book book);

    public void update(Book book);

    public void delete(Integer id);

    public Book getById(Integer id);

    public List<Book> getAll();

    public Integer uploadTxt(MultipartFile file, Integer author_id) throws Exception;
}

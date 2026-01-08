package org.example.novelverse.service.impl;

import org.example.novelverse.dao.BookDao;
import org.example.novelverse.utils.TxtChapterParser;
import org.example.novelverse.domain.Book;
import org.example.novelverse.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    BookDao bookDao;
    @Autowired
    TxtChapterParser txtChapterParser;

    @Override
    public void insert(Book book) {
        bookDao.insert(book);
    }

    @Override
    public void update(Book book) {
        bookDao.update(book);
    }

    @Override
    public void delete(int id) {
        bookDao.delete(id);
    }

    @Override
    public Book getById(int id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public int uploadTxt(MultipartFile file, int author_id) throws Exception {

        // 1. 保存 txt 文件
        String path = "E:/Projects/Knowledges/java/Spring相关/springProject/" +
                "novelverse/novelverse-backend/src/main/resources/novels/" + file.getOriginalFilename();

        Path targetPath = Paths.get(path);
        if (Files.exists(targetPath)) {
            return -1;
        }
        file.transferTo(new File(path));

        // 2. 插入 book
        Book book = new Book();
        book.setAuthor_id(author_id);
        book.setName(file.getOriginalFilename());
        book.setFile_path(path);
        bookDao.insert(book);

        Book newBook = bookDao.getByName(file.getOriginalFilename());

        // 3. 解析章节
        txtChapterParser.parse(path, newBook.getId());

        return newBook.getId();
    }
}

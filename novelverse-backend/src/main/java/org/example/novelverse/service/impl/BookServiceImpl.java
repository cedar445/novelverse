package org.example.novelverse.service.impl;

import com.mysql.cj.xdevapi.JsonArray;
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
    public void delete(Integer id) {
        bookDao.delete(id);
    }

    @Override
    public Book getById(Integer id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Integer uploadTxt(MultipartFile file, List<String> tags, String description, Integer author_id) throws Exception {

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
        book.setTags(tags);
        book.setDescription(description);
        book.setFile_path(path);

        bookDao.insert(book);

        Book newBook = bookDao.getByName(file.getOriginalFilename());

        // 3. 解析章节
        int[] nums = new int[]{};
        nums = txtChapterParser.parse(path, newBook.getId());

        //4.插入字数
        newBook.setWord_count(nums[0]);
        newBook.setChapter_count(nums[1]);
        bookDao.update(newBook);

        return newBook.getId();
    }
}

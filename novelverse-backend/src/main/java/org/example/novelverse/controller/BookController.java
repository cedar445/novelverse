package org.example.novelverse.controller;

import org.example.novelverse.domain.Book;
import org.example.novelverse.domain.Chapter;
import org.example.novelverse.service.BookService;
import org.example.novelverse.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;

    @Autowired
    private ChapterService chapterService;


    @PostMapping
    public Result insertBook(@RequestBody Book book) {
        bookService.insert(book);
        return new Result(Code.INSERT_OK, book);
    }

    @PutMapping
    public Result updateBook(@RequestBody Book book) {
        bookService.update(book);
        return new Result(Code.UPDATE_OK, book);
    }

    @DeleteMapping("/{id}")
    public Result deleteBook(@PathVariable("id") int id) {
        bookService.delete(id);
        return new Result(Code.DELETE_OK, id);
    }

    @GetMapping("/{id}")
    public Result getBookById(@PathVariable("id") int id) {
        Book book = bookService.getById(id);
        return new Result(Code.GET_OK, book);
    }

    @GetMapping
    public Result getBookAll() {
        List<Book> books = bookService.getAll();
        return new Result(Code.GET_OK, books);
    }

    // ① 上传 txt，返回 bookId
    @PostMapping("/upload")
    public int upload(@RequestParam("file") MultipartFile file, @RequestParam("author_id") int author_id) throws Exception {
        return bookService.uploadTxt(file, author_id);
    }

    // ② 根据 bookId 获取章节列表
    @GetMapping("/{bookId}/chapters")
    public List<Chapter> listChapters(@PathVariable int bookId) {
        return chapterService.getByBookId(bookId);
    }
}

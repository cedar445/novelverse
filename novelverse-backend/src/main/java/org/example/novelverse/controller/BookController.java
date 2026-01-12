package org.example.novelverse.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.novelverse.domain.Book;
import org.example.novelverse.domain.Chapter;
import org.example.novelverse.service.BookService;
import org.example.novelverse.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService bookService;

    @Autowired
    private ChapterService chapterService;


    @PostMapping
    public Result insertBook(@RequestBody Book book, HttpServletRequest request) {
        if (book.getAuthor_id() == null) {
            book.setAuthor_id((Integer) request.getAttribute("userId"));
        }
        bookService.insert(book);
        return new Result(Code.INSERT_OK, book);
    }

    @PutMapping
    public Result updateBook(@RequestBody Book book, HttpServletRequest request) {
        if (book.getAuthor_id() == null) {
            book.setAuthor_id((Integer) request.getAttribute("userId"));
        }
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
    public Result upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("tags") List<String> tags,
            @RequestParam("description") String description,
            HttpServletRequest request) throws Exception {

        return new Result(Code.INSERT_OK,
                bookService.uploadTxt(
                        file,
                        tags,
                        description,
                        (Integer) request.getAttribute("userId")
                )
        );
    }

    // ② 根据 bookId 获取章节列表
    @GetMapping("/{bookId}/chapters")
    public Result listChapters(@PathVariable int bookId) {
        return new Result(Code.GET_OK, chapterService.getByBookId(bookId));
    }
}

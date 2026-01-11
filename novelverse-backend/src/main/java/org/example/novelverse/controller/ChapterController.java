package org.example.novelverse.controller;

import org.example.novelverse.domain.Chapter;
import org.example.novelverse.domain.Book;
import org.example.novelverse.service.BookService;
import org.example.novelverse.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/chapters")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private BookService bookService;

    // ③ 获取章节正文
    @GetMapping("/{id}/content")
    public String read(@PathVariable int id) throws Exception {

        Chapter chapter = chapterService.getById(id);
        Book book = bookService.getById(chapter.getBook_id());

        RandomAccessFile raf = new RandomAccessFile(book.getFile_path(), "r");
        raf.seek(chapter.getStart_offset());

        byte[] buf = new byte[
                chapter.getEnd_offset() - chapter.getStart_offset()
                ];
        raf.read(buf);
        raf.close();

        return new String(buf, StandardCharsets.UTF_8);
    }
}


package org.example.novelverse.controller;

import org.example.novelverse.domain.Chapter;
import org.example.novelverse.domain.Book;
import org.example.novelverse.service.BookService;
import org.example.novelverse.service.ChapterService;
import org.mozilla.universalchardet.UniversalDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/chapters")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public Result getChaptersByBookId(@PathVariable("id") int id) {
        List<Chapter> chapters = chapterService.getByBookId(id);
        return new Result(Code.GET_OK, chapters);
    }

    //  获取章节正文
    @GetMapping(value = "/{id}/content", produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> read(@PathVariable("id") int id) throws Exception {

        Chapter chapter = chapterService.getById(id);
        if (chapter == null) {
            return ResponseEntity.notFound().build();
        }

        Book book = bookService.getById(chapter.getBook_id());
        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        // 1. 获取编码
        String charsetName = book.getCharset();
        if (charsetName == null || charsetName.isEmpty()) {
            charsetName = "GB18030"; // 极端兜底
        }
        Charset charset = Charset.forName(charsetName);

        // 2. RandomAccessFile 精确读取章节字节
        try (RandomAccessFile raf = new RandomAccessFile(book.getFile_path(), "r")) {

            long start = chapter.getStart_offset();
            long end = chapter.getEnd_offset();
            long fileLength = raf.length();

            // 3. 越界校验
            if (start >= fileLength) {
                return ResponseEntity.ok().body(""); // 起始偏移超过文件长度，返回空
            }
            if (end > fileLength) {
                end = fileLength; // 修正 end_offset
            }
            if (start > end) {
                return ResponseEntity.ok().body(""); // 防止 start > end
            }

            int length = Math.toIntExact(end - start);
            byte[] buf = new byte[length];

            raf.seek(start);
            raf.readFully(buf);

            // 4. 解码原始内容
            String content = new String(buf, charset);

            // 5. 返回 UTF-8 给前端
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("text/plain; charset=UTF-8"))
                    .body(content);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("读取章节失败：" + e.getMessage());
        }
    }

    //获取指定章节内容
    @GetMapping(value = "/{id}/contentById", produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> getChapterContent(@PathVariable("id") int id) throws Exception {

        Chapter chapter = chapterService.getById(id);
        if (chapter == null) return ResponseEntity.notFound().build();

        Book book = bookService.getById(chapter.getBook_id());
        if (book == null) return ResponseEntity.notFound().build();

        String charsetName = book.getCharset();
        if (charsetName == null || charsetName.isEmpty()) charsetName = "GB18030";
        Charset charset = Charset.forName(charsetName);

        try (RandomAccessFile raf = new RandomAccessFile(book.getFile_path(), "r")) {

            long start = chapter.getStart_offset();
            long end = chapter.getEnd_offset();
            long fileLength = raf.length();

            // 修正 end_offset 超出文件长度
            if (end > fileLength) end = fileLength;

            if (start >= end) {
                return ResponseEntity.ok().body("");  // 起始偏移超过文件长度，直接返回空
            }

            int length = Math.toIntExact(end - start);
            byte[] buf = new byte[length];
            raf.seek(start);
            raf.readFully(buf);

            String content = new String(buf, charset);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("text/plain; charset=UTF-8"))
                    .body(content);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("读取章节失败：" + e.getMessage());
        }
    }

}


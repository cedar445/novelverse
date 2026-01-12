package org.example.novelverse.utils;

import org.example.novelverse.domain.Chapter;
import org.example.novelverse.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

@Component
public class TxtChapterParser {

    @Autowired
    ChapterService chapterService;

    private final Pattern CHAPTER_PATTERN =
            Pattern.compile("^(第\\s*[0-9一二三四五六七八九十百千]+\\s*章.*)$");

    public long[] parse(String path, int bookId) throws Exception {

        RandomAccessFile raf = new RandomAccessFile(path, "r");

        long offset = 0;
        int chapterIndex = 0;

        Chapter current = null;
        String line;

        List<Chapter> buffer = new ArrayList<>();
        final int BATCH_SIZE = 100;

        while ((line = raf.readLine()) != null) {

            String utf8Line =
                    new String(line.getBytes("ISO-8859-1"), StandardCharsets.UTF_8);

            Matcher matcher = CHAPTER_PATTERN.matcher(utf8Line);

            if (matcher.matches()) {

                long chapterStart = offset;

                // ✅ 只有这里，才结束上一章
                if (current != null) {
                    current.setEnd_offset(chapterStart);
                    buffer.add(current);
                }

                // ✅ 批量入库（只在 buffer 满时）
                if (buffer.size() >= BATCH_SIZE) {
                    chapterService.batchInsert(buffer);
                    buffer.clear();
                }

                current = new Chapter();
                current.setBook_id(bookId);
                current.setChapter_index(chapterIndex++);
                current.setTitle(utf8Line);
                current.setStart_offset(chapterStart);
            }

            // ✅ 每一行都只做一件事：更新 offset
            offset = raf.getFilePointer();
        }

        // 🔚 处理最后一章
        if (current != null) {
            current.setEnd_offset(offset);
            buffer.add(current);
        }

        // 🔚 插入剩余章节
        if (!buffer.isEmpty()) {
            chapterService.batchInsert(buffer);
        }

        raf.close();
        return new long[]{offset, chapterIndex};
    }

}

package org.example.novelverse.utils;

import org.example.novelverse.domain.Chapter;
import org.example.novelverse.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.regex.*;

@Component
public class TxtChapterParser {

    @Autowired
    ChapterService chapterService;

    private final Pattern CHAPTER_PATTERN =
            Pattern.compile("^(第\\s*[0-9一二三四五六七八九十百千]+\\s*章.*)$");

    public int[] parse(String path, int bookId) throws Exception {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8)
        );

        String line;
        int offset = 0;
        int chapterIndex = 0;

        Chapter current = null;

        while ((line = reader.readLine()) != null) {
            Matcher matcher = CHAPTER_PATTERN.matcher(line);

            if (matcher.matches()) {
                if (current != null) {
                    current.setEnd_offset(offset);
                    chapterService.insert(current);
                }

                current = new Chapter();
                current.setBook_id(bookId);
                current.setChapter_index(chapterIndex++);
                current.setTitle(line);
                current.setStart_offset(offset);
            }

            offset += line.length() + 1; // +换行
        }

        if (current != null) {
            current.setEnd_offset(offset);
            chapterService.insert(current);
        }

        reader.close();
        return new int[]{offset, chapterIndex};
    }
}

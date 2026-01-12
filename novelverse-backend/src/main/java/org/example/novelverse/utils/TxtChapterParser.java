package org.example.novelverse.utils;

import org.example.novelverse.domain.Chapter;
import org.example.novelverse.service.ChapterService;
import org.mozilla.universalchardet.UniversalDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TxtChapterParser {

    @Autowired
    private ChapterService chapterService;

    private static final Pattern CHAPTER_PATTERN =
            Pattern.compile("^第\\s*(?:0*[0-9]+|[一二三四五六七八九十百千]+)\\s*章.*$");

    public TxtParseResult parse(String path, int bookId) throws Exception {

        /* ========= 1. 读取原始字节 ========= */
        byte[] bytes = Files.readAllBytes(Paths.get(path));

        /* ========= 2. 自动检测编码 ========= */
        UniversalDetector detector = new UniversalDetector(null);
        detector.handleData(bytes, 0, bytes.length);
        detector.dataEnd();

        String charsetName = detector.getDetectedCharset();
        if (charsetName == null || !Charset.isSupported(charsetName)) {
            charsetName = "GB18030"; // 比 GBK 更安全
        }
        Charset charset = Charset.forName(charsetName);
        System.out.println("Detected charset = " + charsetName);

        /* ========= 3. 用正确编码构建 Reader ========= */
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(bytes), charset)
        );

        long offset = 0;          // 字节偏移
        long chapterIndex = 0;

        Chapter current = null;
        String line;

        List<Chapter> buffer = new ArrayList<>();
        final int BATCH_SIZE = 100;

        /* ========= 4. 逐行解析 ========= */
        while ((line = reader.readLine()) != null) {

            Matcher matcher = CHAPTER_PATTERN.matcher(line);

            if (matcher.matches()) {

                long chapterStart = offset;

                // 结束上一章
                if (current != null) {
                    current.setEnd_offset(chapterStart);
                    buffer.add(current);
                }

                // 批量入库
                if (buffer.size() >= BATCH_SIZE) {
                    chapterService.batchInsert(buffer);
                    buffer.clear();
                }

                current = new Chapter();
                current.setBook_id(bookId);
                current.setChapter_index((int) chapterIndex++);
                current.setTitle(line);
                current.setStart_offset(chapterStart);
            }

            // ⚠️ offset 按“原编码字节数”累计，包括换行符
            offset += (line + System.lineSeparator()).getBytes(charset).length;
        }

        /* ========= 5. 处理最后一章 ========= */
        if (current != null) {
            // 使用文件实际长度作为最后一章的 end_offset，保证最后一章不为空
            current.setEnd_offset(bytes.length);
            buffer.add(current);
        }

        if (!buffer.isEmpty()) {
            chapterService.batchInsert(buffer);
        }

        reader.close();
        System.out.println("Parse complete: totalBytes=" + bytes.length + ", chapters=" + chapterIndex);
        return new TxtParseResult(bytes.length, chapterIndex, charsetName);
    }
}

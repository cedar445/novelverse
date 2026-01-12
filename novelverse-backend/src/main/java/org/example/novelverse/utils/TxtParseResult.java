package org.example.novelverse.utils;

public class TxtParseResult {

    private long word_count;
    private long chapter_count;
    private String charset;

    public TxtParseResult(long word_count, long chapter_count, String charset) {
        this.word_count = word_count;
        this.chapter_count = chapter_count;
        this.charset = charset;
    }

    public long getWord_count() {
        return word_count;
    }

    public long getChapter_count() {
        return chapter_count;
    }

    public String getCharset() {
        return charset;
    }
}


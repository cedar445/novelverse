package org.example.novelverse.domain;

public class Chapter {
    Integer id;
    Integer book_id;
    Integer chapter_index;
    String title;
    long start_offset;
    long end_offset;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public Integer getChapter_index() {
        return chapter_index;
    }

    public void setChapter_index(Integer chapter_index) {
        this.chapter_index = chapter_index;
    }

    public long getStart_offset() {
        return start_offset;
    }

    public void setStart_offset(long start_offset) {
        this.start_offset = start_offset;
    }

    public long getEnd_offset() {
        return end_offset;
    }

    public void setEnd_offset(long end_offset) {
        this.end_offset = end_offset;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

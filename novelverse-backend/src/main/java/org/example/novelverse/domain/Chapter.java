package org.example.novelverse.domain;

public class Chapter {
    int id;
    int book_id;
    int chapter_index;
    String title;
    int start_offset;
    int end_offset;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getChapter_index() {
        return chapter_index;
    }

    public void setChapter_index(int chapter_index) {
        this.chapter_index = chapter_index;
    }

    public int getStart_offset() {
        return start_offset;
    }

    public void setStart_offset(int start_offset) {
        this.start_offset = start_offset;
    }

    public int getEnd_offset() {
        return end_offset;
    }

    public void setEnd_offset(int end_offset) {
        this.end_offset = end_offset;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

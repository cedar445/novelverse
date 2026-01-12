package org.example.novelverse.domain;


import java.util.List;

public class Book {
    Integer id;
    Integer author_id;
    String name;
    List<String> tags;
    String description;
    String file_path;
    long word_count;
    long chapter_count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public long getWord_count() {
        return word_count;
    }

    public void setWord_count(long word_count) {
        this.word_count = word_count;
    }

    public long getChapter_count() {
        return chapter_count;
    }

    public void setChapter_count(long chapter_count) {
        this.chapter_count = chapter_count;
    }
}

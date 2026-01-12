package org.example.novelverse.dao;

import org.example.novelverse.domain.Chapter;

import java.util.List;

public interface ChapterDao {

    int insert(Chapter chapter);

    int deleteByBookId(int book_id);

    Chapter getById(int id);

    List<Chapter> getByBookId(int book_id);

    void batchInsert(List<Chapter> chapters);
}

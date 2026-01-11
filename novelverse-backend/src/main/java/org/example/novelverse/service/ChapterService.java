package org.example.novelverse.service;

import org.example.novelverse.domain.Chapter;

import java.util.List;

public interface ChapterService {
    public void insert(Chapter chapter);

    public void delete(Integer id);

    public List<Chapter> getByBookId(Integer book_id);

    public Chapter getById(Integer id);
}

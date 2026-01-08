package org.example.novelverse.service.impl;

import org.example.novelverse.dao.ChapterDao;
import org.example.novelverse.domain.Chapter;
import org.example.novelverse.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;

    @Override
    public void insert(Chapter chapter) {
        chapterDao.insert(chapter);
    }

    @Override
    public void delete(int id) {
        chapterDao.deleteByBookId(id);
    }

    @Override
    public List<Chapter> getByBookId(int book_id) {
        return chapterDao.getByBookId(book_id);
    }

    @Override
    public Chapter getById(int id) {
        return chapterDao.getById(id);
    }
}

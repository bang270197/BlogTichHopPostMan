package com.codegym.service;

import com.codegym.model.Blog;
import com.codegym.model.Category;

import java.util.List;

public interface BlogService {
    Iterable<Blog> findAll();

    Blog findById(Long id);

    void save (Blog blog);

    void remove (Long id);
    List<Blog> findAllByCategory(Category category);
}

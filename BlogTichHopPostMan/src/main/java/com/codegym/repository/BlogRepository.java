package com.codegym.repository;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BlogRepository extends PagingAndSortingRepository<Blog,Long> {
    List<Blog> findAllByCategory(Category category);
}

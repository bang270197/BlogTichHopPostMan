package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.service.BlogService;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.PortUnreachableException;
import java.util.List;

@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/search-blog/{name}")
    public ResponseEntity<List<Blog>> searchBlog(@PathVariable String name) {
        Category category = categoryService.findAllByName(name);
        List<Blog> blogs=blogService.findAllByCategory(category);
       if (blogs==null){
           return new ResponseEntity<List<Blog>>(HttpStatus.NOT_FOUND);
       }
       return new ResponseEntity<List<Blog>>(blogs,HttpStatus.OK);

    }


    @GetMapping("/blogs/")
    public ResponseEntity<List<Blog>> listAllBlog() {
        List<Blog> blogs = (List<Blog>) blogService.findAll();
        if (blogs.isEmpty()) {
            return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
    }

    @PostMapping("/create-blogs/")
    public ResponseEntity<Void> createBlogs(@RequestBody Blog blog, UriComponentsBuilder uriComponentsBuilder) {
        blogService.save(blog);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/blogs/{id}").buildAndExpand(blog.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/update-blogs/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long id, @RequestBody Blog blog) {
        Blog blog1 = blogService.findById(id);
        if (blog1 == null) {
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }
        blog1.setTitle(blog.getTitle());
        blog1.setContent(blog.getContent());
        blog1.setStatus(blog.getStatus());
        blogService.save(blog1);
        return new ResponseEntity<Blog>(blog1, HttpStatus.OK);
    }

    @DeleteMapping("/delete-blog/{id}")
    public ResponseEntity<Blog> deleteBlog(@PathVariable Long id) {
        Blog blog = blogService.findById(id);

        if (blog == null) {
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }

        blogService.remove(id);
        return new ResponseEntity<Blog>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/detail-blogs/{id}")
    public ResponseEntity<Blog> detailBlog(@PathVariable Long id) {
        Blog blog = blogService.findById(id);
        if (blog == null) {
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Blog>(blog, HttpStatus.OK);
    }
}

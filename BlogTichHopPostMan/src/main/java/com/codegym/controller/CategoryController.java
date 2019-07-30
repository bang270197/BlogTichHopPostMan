package com.codegym.controller;


import com.codegym.model.Category;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class CategoryController {
@Autowired
    private CategoryService categoryService;

    @GetMapping("/categorys/")
    public ResponseEntity<List<Category>> listAllCategory(){
        List<Category> categories= (List<Category>) categoryService.findAll();
        if (categories.isEmpty()){
            return new ResponseEntity<List<Category>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Category>>(categories,HttpStatus.OK);
    }

    @PostMapping("/create-categorys/")
    public ResponseEntity<Void> createCategory(@RequestBody Category category, UriComponentsBuilder uriComponentsBuilder)
    {
        categoryService.save(category);
        HttpHeaders headers=new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/categorys/{id}").buildAndExpand(category.getId()).toUri());
        return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
    }

    @PutMapping("/update-categorys/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,@RequestBody Category category){
        Category category1=categoryService.findById(id);
        if (category1==null){
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
       category1.setName(category.getName());
        //category1.setBlogs(category.getBlogs());
        categoryService.save(category1);
        return new ResponseEntity<Category>(category1,HttpStatus.OK);
    }
//
    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<Category> deleteBlog(@PathVariable Long id)
    {
        Category category=categoryService.findById(id);

        if (category==null){
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }

        categoryService.remove(id);
        return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/detail-category/{id}")
    public ResponseEntity<Category> detailBlog(@PathVariable Long id)
    {
       Category category=categoryService.findById(id);
        if (category==null){
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Category>(category,HttpStatus.OK);
    }
}

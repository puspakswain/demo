package com.ww.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ww.demo.dto.CategoryDto;
import com.ww.demo.model.Category;
import com.ww.demo.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/products/categories")
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "Add new catgeory")
	@ResponseStatus(code = HttpStatus.OK)
	public Category saveCategory(@RequestBody CategoryDto categoryDto) {
	       return categoryService.saveCategory(categoryDto);
	 }

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "Get All Categories")
	public List<Category> getCategories() {
		
		return categoryService.findAll();
	}

	

	@DeleteMapping("/cache/")
	@Operation(description = "Clear category cache")
	public String clearCache() {
		categoryService.clearCache();
		return "Product data cleared from Cache !";

	}

}

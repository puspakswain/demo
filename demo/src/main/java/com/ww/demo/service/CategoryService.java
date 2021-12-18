package com.ww.demo.service;

import java.util.List;
import java.util.Map;

import com.ww.demo.dto.CategoryDto;
import com.ww.demo.model.Category;

public interface CategoryService {
	
	public Category saveCategory(CategoryDto categoryDto);
	public List<Category> findAll();
	public Map<Long,Category> getCategoryMap();
	public void clearCache();


}

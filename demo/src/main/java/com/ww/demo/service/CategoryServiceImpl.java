package com.ww.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ww.demo.dto.CategoryDto;
import com.ww.demo.exception.BadRequestException;
import com.ww.demo.model.Category;
import com.ww.demo.repository.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	private AtomicReference<Map<Long,Category>> categoryMap;;

	
	@Override
	public Category saveCategory(CategoryDto categoryDto) {
		Category category = new Category();
		
		try {
			BeanUtils.copyProperties(categoryDto, category);
			category.setCreationDateTime(LocalDateTime.now());
			category= categoryRepository.save(category);
		}
		catch(DataIntegrityViolationException ex) {
			 throw new BadRequestException("Category Exists");
		}
		
		return category;
	}

	@Override
    @Cacheable(value="Categories", key="'all'")
	public List<Category> findAll() {
		
		return categoryRepository.findAll();
	}

	@Override
	@CacheEvict(cacheNames = "Categories", allEntries = true)
	public void clearCache() {
	    log.info("Clearing category data from cache");
		
	}

	@Override
	public Map<Long, Category> getCategoryMap() {
		if(this.categoryMap == null) {
			Map<Long,Category> map = findAll().stream()
				      .collect(Collectors.toMap(Category::getCategoryId, Function.identity()));
			this.categoryMap = new AtomicReference<>(map);
		}
		return categoryMap.get();
	}
	


}

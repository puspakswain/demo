package com.ww.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ww.demo.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	public Optional<Category> findByCategoryAndSubCategory(String category, String subCategory);

}
	
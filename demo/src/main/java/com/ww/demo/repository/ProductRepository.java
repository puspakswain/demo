package com.ww.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ww.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
	
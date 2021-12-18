package com.ww.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductDetailView {

    private Long totalCount;
	private List<ProductDetail> products;
	
}

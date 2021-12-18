package com.ww.demo.dto;

import lombok.Data;

@Data
public class ProductDto {
	
	private String name;
	private String description;
	private String category;
	private String subCategory;
	private String url;
	private String currency;
	private String user;
}

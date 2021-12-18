package com.ww.demo.dto;

import lombok.Data;

@Data
public class ProductSkuDto {
	
	private String name;
	
	private String description;
	
	private Double retailPrice;
	
	private Double salePrice;
	
	private Long quantity;
	
	private String inventoryType;
	
	private String user;
}

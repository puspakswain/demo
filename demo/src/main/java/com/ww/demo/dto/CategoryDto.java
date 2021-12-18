package com.ww.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CategoryDto  {
	
	private String category;
	private String subCategory;
	private Boolean isActive;
	private String creationUser;
	

}

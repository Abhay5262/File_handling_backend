package com.File_Excel.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Excel_dto {
	
	private String order_no;
	private String sale;
	private String orderquantity;
	
	private MultipartFile file;
}

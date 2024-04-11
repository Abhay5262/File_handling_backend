package com.File_Excel.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.File_Excel.Entity.Excel_Entity;
import com.File_Excel.Repository.Excel_repo;
import com.File_Excel.dto.Excel_dto;
import com.File_Excel.services.Services;
import com.File_Excel.utils.Excel_helper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Excel_servicImpl implements Services {
	@Autowired
	private Excel_repo repo;

	public String save(MultipartFile file) {
		List<Excel_Entity> convertExcelToList;
		try {
			
			convertExcelToList = Excel_helper.convertExcelToList(file.getInputStream());
			repo.saveAll(convertExcelToList);
			String path= new ClassPathResource("static/Excelsheet").getFile().getAbsolutePath();
			
			String excelfile=(UUID.randomUUID().toString()).concat(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
			
			Files.copy(file.getInputStream(), Path.of(path+File.separator+excelfile), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Sheet uploaded";
	 	
	}

		
	public List<Excel_Entity>getdata(){
	
		return (List<Excel_Entity>) repo.findAll();
		
	}
	public Resource getexcelfile() {
		
		return Excel_helper.getexcelfile();
		
	}
	public ByteArrayInputStream getdataexcelfile() {
		
		try {
			return Excel_helper.dowmloadExcel(getdata());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	// Image + Data
		public String allfile(Excel_dto dto) {
			try {
				
				String imagePath = new ClassPathResource("static/img").getFile().getAbsolutePath();
				
				Excel_Entity user = new Excel_Entity();
				user.setOrder_no(dto.getOrder_no());
				user.setSale(dto.getSale());
				user.setOrderquantity(dto.getOrderquantity());
				
				Files.copy(dto.getFile().getInputStream(), Path.of(imagePath+File.separator+dto.getFile().getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				
				user.setImagepath(imagePath);
				
				repo.save(user);
				
				return "Data Stored";
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return "Data Not Stored";
		}


	@Override
	public String both(MultipartFile file, String data) {
		try {
			
			String imagePath = new ClassPathResource("static/img").getFile().getAbsolutePath();
			
			Excel_Entity user = new Excel_Entity();
			
			ObjectMapper mapper=new ObjectMapper();
			Excel_dto dto = mapper.readValue(data, Excel_dto.class);
			
			user.setOrder_no(dto.getOrder_no());
			user.setSale(dto.getSale());
			user.setOrderquantity(dto.getOrderquantity());
			
			Files.copy(file.getInputStream(), Path.of(imagePath+File.separator+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			
			user.setImagepath(imagePath);
			
			repo.save(user);
			
			return "Data Stored";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "Data Not Stored";
	}


	@Override
	public List<Excel_Entity> getpagination(Integer pagenumber,Integer pagesize) {

		Pageable pageable=PageRequest.of(pagenumber, pagesize);
		
		Page<Excel_Entity> all = repo.findAll(pageable);
		List<Excel_Entity> content = all.getContent();
	return content;
	}
}




		





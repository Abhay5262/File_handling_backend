package com.File_Excel.services;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.File_Excel.Entity.Excel_Entity;
import com.File_Excel.dto.Excel_dto;

@Service
public interface Services {
	public String save(MultipartFile file);
	public List<Excel_Entity>getdata();
	public List<Excel_Entity>getpagination(Integer pagenumber,Integer pagesize);
	public Resource getexcelfile();
	public ByteArrayInputStream getdataexcelfile();
	public String allfile(Excel_dto dto);
	public String both(MultipartFile file, String data);
}

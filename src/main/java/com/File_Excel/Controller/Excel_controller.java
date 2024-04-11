package com.File_Excel.Controller;



import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.File_Excel.Entity.Excel_Entity;
import com.File_Excel.dto.Excel_dto;
import com.File_Excel.services.Services;


@RestController
@CrossOrigin("*")
public class Excel_controller {
	@Autowired
	Services s;
	
	@PostMapping("/upload_sheet")
	public ResponseEntity<String>save(@RequestParam("file")MultipartFile file){
		return new ResponseEntity<String>(s.save(file),HttpStatus.ACCEPTED);
		
	}
	@GetMapping("/get_exceldata")
	public ResponseEntity<List<Excel_Entity>>getdata(){
		return new ResponseEntity<>(s.getdata(),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/excel_sheet")
	public ResponseEntity<Resource> getsheet(){	
		Resource resource =(Resource) s.getexcelfile();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attchment;filename=order.xlsx");
		
		return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource);
		
//		String filename="Format.xlsx";
//		ByteArrayInputStream getsheet = impl.getexcelfile();
//		InputStreamResource file=new InputStreamResource(getsheet);
//		
//		ResponseEntity<Resource> body = ResponseEntity.ok()
//				.header("Content-Disposition", "attachment; filename="+filename)
//				.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//				.body(file);
//		
//		return body;
	}
	@GetMapping("/excel_sheet_withdata")
	public ResponseEntity<Resource> getdatasheet(){
		
		String filename="withdata.xlsx";
		ByteArrayInputStream getsheet =s.getdataexcelfile();
		InputStreamResource file=new InputStreamResource(getsheet);
		
		ResponseEntity<Resource> body = ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename="+filename)
				.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.body(file);
		
		return body;
	}
	
	
	@PostMapping("/multiple_data")
	public ResponseEntity<String> allfile(@ModelAttribute Excel_dto e) {
		return new ResponseEntity<String>(s.allfile(e),HttpStatus.OK);
	}
	
	@PostMapping("/saveimgdata")
	public ResponseEntity<String> both(@RequestParam("file")MultipartFile file, @RequestParam("data") String data){
		System.err.println(file+data);
		return new ResponseEntity<String>(s.both(file, data), HttpStatus.OK);
	}
	
	@GetMapping("/pagination")
	public ResponseEntity<List<Excel_Entity>> pagination(@RequestParam(value = "pagenumber",defaultValue = "0")Integer pagenumber,@RequestParam(value = "pagesize",defaultValue = "5")Integer pagesize){
		return new ResponseEntity<List<Excel_Entity>>(s.getpagination(pagenumber, pagesize), HttpStatus.OK);
	}
}

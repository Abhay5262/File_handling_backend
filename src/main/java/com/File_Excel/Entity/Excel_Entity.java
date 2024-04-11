package com.File_Excel.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Excel_sheet")
public class Excel_Entity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sr_no;
	private String order_no;
	private String sale;
	private String orderquantity;
	private String imagepath;
	
}

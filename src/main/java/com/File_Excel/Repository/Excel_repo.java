package com.File_Excel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.File_Excel.Entity.Excel_Entity;
@Repository
public interface Excel_repo extends JpaRepository<Excel_Entity, Long> {

}

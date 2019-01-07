package com.a4tech.v5.excel.service;

import org.apache.poi.ss.usermodel.Workbook;

public interface IExcelParser {
	
       public String readExcel(String accessToken,Workbook workbook ,Integer asiNumber ,int batchId, String environmentType);
}

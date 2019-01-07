package com.a4tech.v5.product.service;

import org.apache.poi.ss.usermodel.Workbook;

public interface IProductService {
	
	public String readExcel(String accessToken,Workbook workbook ,Integer asiNumber ,int batchId);

}

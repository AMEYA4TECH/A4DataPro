package com.a4tech.product.service.imple;

import org.apache.poi.ss.usermodel.Workbook;




import com.a4tech.product.service.ProductService;
import com.a4tech.v2.core.excelMapping.V2ExcelMapping;

public class ProductServiceImpl implements ProductService{

	public V2ExcelMapping excelMapping  = new V2ExcelMapping();
	public int excelProducts(String AccessToken,Workbook workBook,int asiNumber,int batchId ) {
		// TODO Auto-generated method stub
		return excelMapping.readExcel(AccessToken,workBook,asiNumber,batchId);
	}
}

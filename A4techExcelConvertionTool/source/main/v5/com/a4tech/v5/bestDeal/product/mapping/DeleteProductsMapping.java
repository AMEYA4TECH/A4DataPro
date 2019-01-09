package com.a4tech.v5.bestDeal.product.mapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.a4tech.v5.bestDeal.product.parser.BestDealAttributeParser;
import com.a4tech.v5.bestDeal.product.parser.BestDealPriceGridParser;
import com.a4tech.v5.product.dao.service.ProductDao;
import com.a4tech.v5.product.service.postImpl.PostServiceImpl;
import com.a4tech.v5.util.ApplicationConstants;
import com.a4tech.v5.util.CommonUtility;

public class DeleteProductsMapping {
	
	private static final Logger _LOGGER = Logger.getLogger(DeleteProductsMapping.class);

	public List<String> getAllXids(Workbook workbook ,Integer asiNumber ,int batchId, String environmentType){
		  List<String> xidsList = new ArrayList<>();
 		try{
	    Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		String xid = null;
		
		while (iterator.hasNext()) {
			
			try{
			Row nextRow = iterator.next();
			if(nextRow.getRowNum() == ApplicationConstants.CONST_NUMBER_ZERO){
				continue;
			}
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				
				int columnIndex = cell.getColumnIndex();
				if(columnIndex  == 0){
					Cell xidCell = nextRow.getCell(1);
				     xid = CommonUtility.getCellValueStrinOrInt(xidCell);
					//xid = CommonUtility.getCellValueStrinOrInt(cell);
				}
				switch (columnIndex+1) {
				case 1://xid
					  xidsList.add(xid);
					 break;
			
			}  // end inner while loop
					 
		}
				
			}catch(Exception e){
		}
		}
		workbook.close();
	       return xidsList;
		}catch(Exception e){
			_LOGGER.error("Error while Processing excel sheet " +e.getMessage());
			return xidsList;
		}finally{
			try {
				workbook.close();
			} catch (IOException e) {
				_LOGGER.error("Error while Processing excel sheet" +e.getMessage());
	
			}
				_LOGGER.info("Complted processing of excel sheet ");
				
		}
		
	}

	


}

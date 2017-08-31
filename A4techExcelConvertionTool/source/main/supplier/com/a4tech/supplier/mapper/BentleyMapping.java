package com.a4tech.supplier.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import parser.bentley.BentleyAttributeParser;

import com.a4tech.core.errors.ErrorMessageList;
import com.a4tech.excel.service.IExcelParser;
import com.a4tech.lookup.service.LookupServiceData;
import com.a4tech.product.dao.service.ProductDao;
import com.a4tech.product.model.Color;
import com.a4tech.product.model.ImprintMethod;
import com.a4tech.product.model.Material;
import com.a4tech.product.model.Option;
import com.a4tech.product.model.Price;
import com.a4tech.product.model.PriceConfiguration;
import com.a4tech.product.model.PriceGrid;
import com.a4tech.product.model.Product;
import com.a4tech.product.model.ProductConfigurations;
import com.a4tech.product.model.RushTime;
import com.a4tech.product.model.Shape;
import com.a4tech.product.model.ShippingEstimate;
import com.a4tech.product.model.Size;
import com.a4tech.product.service.postImpl.PostServiceImpl;
import com.a4tech.util.ApplicationConstants;
import com.a4tech.util.CommonUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BentleyMapping  implements IExcelParser{
	private static final Logger _LOGGER = Logger.getLogger(PrimeLineExcelMapping.class);
	private PostServiceImpl postServiceImpl;
	private ProductDao productDaoObj;
	BentleyAttributeParser  bentleyAttributeParser;
	@Autowired
	ObjectMapper mapperObj;
	private LookupServiceData lookupServiceDataObj;
	
	 private HashMap<String, Product> sheetMap =new HashMap<String, Product>();
	 private HashMap<String, StringBuilder>  priceMap=new HashMap<String, StringBuilder>();
	public String readExcel(String accessToken,Workbook workbook ,Integer asiNumber ,int batchId){
		
		List<String> numOfProductsSuccess = new ArrayList<String>();
		List<String> numOfProductsFailure = new ArrayList<String>();
		String finalResult = null;
		Set<String>  productXids = new HashSet<String>();
		  Product productExcelObj = new Product();   
		  Product existingApiProduct = null;
		  ProductConfigurations productConfigObj=new ProductConfigurations();
		  List<PriceGrid> priceGrids = null;
		  List<String> repeatRows = new ArrayList<>();
		  StringBuilder listOfQuantity = new StringBuilder();
		  StringBuilder listOfPrices = new StringBuilder();
		  StringBuilder listOfDiscCodes = new StringBuilder();
		  String shippingitemValue="";
			String shippingWeightValue="";
			String dimLen="";
			String dimHieght="";
			String dimWidth="";
			List<String> listOfCategories = new ArrayList<>();
			String productName="";
		  
		try{
			_LOGGER.info("Total sheets in excel::"+workbook.getNumberOfSheets());
	
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		_LOGGER.info("Started Processing Product");
	    String productId = null;
	    String xid = null;
	    int columnIndex=0;
	    boolean existingFlag=false;
	   // String q1 = null,q2= null,q3= null,q4= null,q5= null,q6=null;
	    String baseDiscCode=null;
	   // List<String> imagesList   = new ArrayList<String>();
		while (iterator.hasNext()) {
			try{
			Row nextRow = iterator.next();
			if (nextRow.getRowNum() == 0)
				continue;
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			if(xid != null){
				productXids.add(xid);
				repeatRows.add(xid);
			}
			
			 boolean checkXid  = false; //imp line
			//boolean checkXid  = true; //imp line
			//xid=getProductXid(nextRow);
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
			    columnIndex = cell.getColumnIndex();
				if(columnIndex + 1 == 1){
					xid = getProductXid(nextRow);//CommonUtility.getCellValueStrinOrInt(cell);//
					checkXid = true;
				}else{
					checkXid = false;
				}
				if(checkXid){
					 if(!productXids.contains(xid)){
						 if(nextRow.getRowNum() != 1){
							priceGrids=	 new ArrayList<PriceGrid>();
							//priceGrids = primeLinePriceGridParser.getPriceGrids(priceMap);	
						// do pricing and other things over here
							productExcelObj.setPriceType("L");
							productExcelObj.setPriceGrids(priceGrids);
							productExcelObj.setProductConfigurations(productConfigObj);
							
							_LOGGER.info("Product Data from sheet 1: "
									+ mapperObj.writeValueAsString(productExcelObj));
							sheetMap.put(productId,productExcelObj);
							/*
							_LOGGER.info("Product Data from sheet 1: "
									+ mapperObj.writeValueAsString(productExcelObj));*/
							 /*int num = postServiceImpl.postProduct(accessToken, productExcelObj,asiNumber ,batchId);
							 	if(num ==1){
							 		numOfProductsSuccess.add("1");
							 	}else if(num == 0){
							 		numOfProductsFailure.add("0");
							 	}else{
							 		
							 	}*//*
							 	_LOGGER.info("list size>>>>>>>"+numOfProductsSuccess.size());
							 	_LOGGER.info("Failure list size>>>>>>>"+numOfProductsFailure.size());*/
								priceGrids = new ArrayList<PriceGrid>();
								productConfigObj = new ProductConfigurations();
								repeatRows.clear();
								baseDiscCode=null;
								shippingitemValue="";
								shippingWeightValue="";
								dimLen="";
								dimHieght="";
								dimWidth="";
								listOfCategories=new ArrayList<String>();
								listOfQuantity = new StringBuilder();
								listOfPrices = new StringBuilder();
								listOfDiscCodes=new StringBuilder();
								priceMap=new HashMap<String, StringBuilder>();
								
						 }
						    if(!productXids.contains(xid)){
						    	productXids.add(xid);
						    	repeatRows.add(xid);
						    }
						    productExcelObj = new Product();
						    existingApiProduct = postServiceImpl.getProduct(accessToken, xid); 
						     if(existingApiProduct == null){
						    	 _LOGGER.info("Existing Xid is not available,product treated as new product");
						    	 productExcelObj = new Product();
						    	 existingFlag=false;
						     }else{
						    	 _LOGGER.info("Existing Xid available,Processing existing Data");
						    	// productExcelObj=primeLineAttributeParser.getExistingProductData(existingApiProduct,existingApiProduct.getProductConfigurations(),accessToken);
						    	 productConfigObj=productExcelObj.getProductConfigurations();
								 existingFlag=true;
								 // priceGrids = productExcelObj.getPriceGrids();
						     }
					 }	
				}else{	
					if(productXids.contains(xid) && repeatRows.size() != 1){
						 if(isRepeateColumn(columnIndex+1)){
							 continue;
						 }
					}	
				}		
				switch (columnIndex + 1) {
					    case 1://Mapped XIDs
						productId=xid;//CommonUtility.getCellValueStrinOrInt(cell);
						productExcelObj.setExternalProductId(xid);
						break;
						case 2://SKU#
						
						break;
						case 3://Old SKU#
						
							break;
						case 4://Visual ref.
							break;
						case 5://Item name EN
							productName=CommonUtility.getCellValueStrinOrDecimal(cell);
							if(!StringUtils.isEmpty(productName)){		
						    productName=CommonUtility.removeRestrictSymbols(productName);
						    String tempName=productName;
							int len=tempName.length();
							 if(len>60){
								String strTemp=tempName.substring(0, 60);
								int lenTemp= strTemp.lastIndexOf(ApplicationConstants.CONST_VALUE_TYPE_SPACE);
								tempName=(String) strTemp.subSequence(0, lenTemp);
							}
							productExcelObj.setName(tempName);
							}
							break;
						case 6://Item name FR
							break;
						case 7://Brand
							
							break;
						case 8://Model/Coll.
							
							break;
						case 9://Color
							String colorValue=CommonUtility.getCellValueStrinOrInt(cell);
							 if(!StringUtils.isEmpty(colorValue)){
								 List<Color> colors =bentleyAttributeParser.getProductColors(colorValue);
								 productConfigObj.setColors(colors);
							 }
							break;
						case 10://Description EN
							String description =CommonUtility.getCellValueStrinOrInt(cell);
							 if(!StringUtils.isEmpty(description)){
							//description = CommonUtility.removeSpecialSymbols(description,specialCharacters);
						    description=CommonUtility.removeRestrictSymbols(description);
							int length=description.length();
							 if(length>800){
								String strTemp=description.substring(0, 800);
								int lenTemp= strTemp.lastIndexOf(ApplicationConstants.CONST_VALUE_TYPE_SPACE);
								description=(String) strTemp.subSequence(0, lenTemp);
							}
							description=CommonUtility.removeRestrictSymbols(description);
							productExcelObj.setDescription(description);
							 }
							break;
						case 11://Description FR
							
							break;
						case 12://Specs EN
							
							break;
						case 13://Specs FR
							
							break;
						case 14://Dimensions
							break;
						case 15://Sale
							break;
						case 16:
							break;
						case 17:
							break;
						case 18:
							break;
						case 19:
							break;
						case 20:
							
							break;
						case 21:
							
							break;
						case 22:
							
							break;
						case 23:
							
							break;
						case 24:
							break;
						case 25:
							break;
						case 26:
							break;
						case 27:
							break;
						case 28:
							break;
						case 29:
							break;
						case 30:
							break;
						case 31:
							break;
						case 32:
							break;
						case 33://Imprint
							String	imprintMethodVal=CommonUtility.getCellValueStrinOrInt(cell);
							if(!StringUtils.isEmpty(imprintMethodVal)){
							imprintMethodVal=imprintMethodVal.toUpperCase();
							String tempImpArr[]=imprintMethodVal.split(",");
							List<ImprintMethod> listOfImprintMethod= bentleyAttributeParser.getImprintMethods(Arrays.asList(tempImpArr));
							 productConfigObj.setImprintMethods(listOfImprintMethod);
							
							}
							break;
						case 34://1
							
							break;
						case 35://100
							
							break;
						case 36://250
							break;
						case 37://500
							break;
						case 38://1000
							break;
							
						case 39:
							break;
						case 40://
							
							break;
						case 41://
							
							break;
						case 42://
							
							break;
						case 43://
							
							break;
							
						case 44 :
							break;
							
						case 45 :
							break;
						case 46 :
							break;
						case 47 :
							break;
						case 48 :
							break;
						case 49 :
							break;
						case 50 :
							break;
						case 51 :
							break;
						case 52 :
							break;
						case 53 :
							break;
						case 54 :
							break;
						case 55 :
							break;
							
				}  // end inner while loop					 
			}		
		
			
			// listOfQuantity = new StringBuilder();
			// listOfPrices = new StringBuilder();
			// listOfDiscCodes=new StringBuilder();
			}catch(Exception e){
				_LOGGER.error("Error while Processing ProductId and cause :"+productExcelObj.getExternalProductId() +" "+e.getMessage()+"at column number(increament by 1):"+columnIndex);		 
				ErrorMessageList apiResponse = CommonUtility.responseconvertErrorMessageList("Product Data issue in Supplier Sheet 1: "
				+e.getMessage()+" at column number(increament by 1)"+columnIndex);
				productDaoObj.save(apiResponse.getErrors(),
						productExcelObj.getExternalProductId()+"-Failed", asiNumber, batchId);
				}
		}
		workbook.close();
		priceGrids=	 new ArrayList<PriceGrid>();
		//priceGrids = primeLinePriceGridParser.getPriceGrids(priceMap);	
		// do pricing over here
		productExcelObj.setPriceType("L");
		productExcelObj.setPriceGrids(priceGrids);
		productExcelObj.setProductConfigurations(productConfigObj);
		
		_LOGGER.info("Product Data from sheet 1: "
				+ mapperObj.writeValueAsString(productExcelObj));
		sheetMap.put(productId,productExcelObj);
		/*
		_LOGGER.info("Product Data from sheet 1: "
				+ mapperObj.writeValueAsString(productExcelObj));*/
		 /*int num = postServiceImpl.postProduct(accessToken, productExcelObj,asiNumber ,batchId);
		 	if(num ==1){
		 		numOfProductsSuccess.add("1");
		 	}else if(num == 0){
		 		numOfProductsFailure.add("0");
		 	}else{
		 		
		 	}*//*
		 	_LOGGER.info("list size>>>>>>>"+numOfProductsSuccess.size());
		 	_LOGGER.info("Failure list size>>>>>>>"+numOfProductsFailure.size());*/
			priceGrids = new ArrayList<PriceGrid>();
			productConfigObj = new ProductConfigurations();
			repeatRows.clear();
			baseDiscCode=null;
			shippingitemValue="";
			shippingWeightValue="";
			dimLen="";
			dimHieght="";
			dimWidth="";
			listOfCategories=new ArrayList<String>();
			listOfQuantity = new StringBuilder();
			listOfPrices = new StringBuilder();
			listOfDiscCodes=new StringBuilder();
			
				
			
		return finalResult;
		}catch(Exception e){
			_LOGGER.error("Error while Processing excel sheet " +e.getMessage());
			return finalResult;
		}finally{
			try {
				workbook.close();
			} catch (IOException e) { 
				_LOGGER.error("Error while Processing excel sheet" +e.getMessage());
	
			}
				_LOGGER.info("Complted processing of excel sheet ");
				_LOGGER.info("Total no of product:"+numOfProductsSuccess.size() );
		}
		
	}

	

	private String postingProducts(String accessToken,Integer asiNumber,int batchId,HashMap<String, Product> sheetMap) {
		List<String> numOfProductsSuccess = new ArrayList<String>();
		List<String> numOfProductsFailure = new ArrayList<String>();
		
		for (Map.Entry<String, Product> productEntry : sheetMap.entrySet())
		{
			try{
			Product productExcelObj=productEntry.getValue();
			productExcelObj.setPriceType("L");
			int num = postServiceImpl.postProduct(accessToken, productExcelObj,asiNumber,batchId);
		 	if(num ==1){
		 		numOfProductsSuccess.add("1");
		 	}else if(num == 0){
		 		numOfProductsFailure.add("0");
		 	}
			}catch(Exception e){
				_LOGGER.error("Error while posting product  "+e.getMessage());
			}
		}
	 	
	 	_LOGGER.info("list size>>>>>>"+numOfProductsSuccess.size());
	 	_LOGGER.info("Failure list size>>>>>>"+numOfProductsFailure.size());
	 	String  finalResult = numOfProductsSuccess.size() + "," + numOfProductsFailure.size();
	 	productDaoObj.saveErrorLog(asiNumber,batchId);
		return finalResult;
	}

	public String getProductXid(Row row){
		Cell xidCell =  row.getCell(0);
		String productXid = CommonUtility.getCellValueStrinOrInt(xidCell);
		if(StringUtils.isEmpty(productXid) || productXid.trim().equalsIgnoreCase("#N/A")){
		     xidCell = row.getCell(1);
		     productXid = CommonUtility.getCellValueStrinOrInt(xidCell);
		}
		return productXid;
	}
	
	public boolean isRepeateColumn(int columnIndex){
		if(columnIndex != 1 && columnIndex != 3 && 
				columnIndex != 9 && columnIndex != 10 && columnIndex != 11 && columnIndex != 12 && columnIndex != 13 &&
				columnIndex != 19 && columnIndex != 20 && columnIndex != 21 && columnIndex != 22 && columnIndex != 23 && 
				columnIndex != 43
				){
		//if(columnIndex != 1&&columnIndex != 3&&columnIndex != 4 && columnIndex != 6 && columnIndex != 9 && columnIndex != 24){
			return ApplicationConstants.CONST_BOOLEAN_TRUE;
		}
		return ApplicationConstants.CONST_BOOLEAN_FALSE;
	}
	
	public static List<PriceGrid> getPriceGrids(String basePriceName) 
	{
		List<PriceGrid> newPriceGrid=new ArrayList<PriceGrid>();
		try{
			Integer sequence = 1;
			List<PriceConfiguration> configuration = null;
			PriceGrid priceGrid = new PriceGrid();
			priceGrid.setCurrency(ApplicationConstants.CONST_STRING_CURRENCY_USD);
			priceGrid.setDescription(basePriceName);
			priceGrid.setPriceIncludes(ApplicationConstants.CONST_STRING_EMPTY);
			priceGrid.setIsQUR(ApplicationConstants.CONST_BOOLEAN_TRUE);
			priceGrid.setIsBasePrice(true);
			priceGrid.setSequence(sequence);
			List<Price>	listOfPrice = new ArrayList<Price>();
			priceGrid.setPrices(listOfPrice);
			priceGrid.setPriceConfigurations(configuration);
			newPriceGrid.add(priceGrid);
	}catch(Exception e){
		_LOGGER.error("Error while processing PriceGrid: "+e.getMessage());
	}
		_LOGGER.info("PriceGrid Processed");
		return newPriceGrid;
}
	public PostServiceImpl getPostServiceImpl() {
		return postServiceImpl;
	}

	public void setPostServiceImpl(PostServiceImpl postServiceImpl) {
		this.postServiceImpl = postServiceImpl;
	}
	public ProductDao getProductDaoObj() {
		return productDaoObj;
	}

	public void setProductDaoObj(ProductDao productDaoObj) {
		this.productDaoObj = productDaoObj;
	}
	
	public static final String CONST_STRING_COMBO_TEXT = "Combo";
	
	public ObjectMapper getMapperObj() {
		return mapperObj;
	}
	
	public void setMapperObj(ObjectMapper mapperObj) {
		this.mapperObj = mapperObj;
	}
	
	public LookupServiceData getLookupServiceDataObj() {
		return lookupServiceDataObj;
	}

	public void setLookupServiceDataObj(LookupServiceData lookupServiceDataObj) {
		this.lookupServiceDataObj = lookupServiceDataObj;
	}

	public static String removeSpecialChar(String tempValue){
		tempValue=tempValue.replaceAll("(CLASS|Day|DAYS|Service|Days|Hour|Hours|Week|Weeks|Rush|day|service|days|hour|hours|week|weeks|Rush|R|u|s|h|®|™|$)", "");
		tempValue=tempValue.replaceAll("\\(","");
		tempValue=tempValue.replaceAll("\\)","");
	return tempValue;

	}



	public BentleyAttributeParser getBentleyAttributeParser() {
		return bentleyAttributeParser;
	}
	public void setBentleyAttributeParser(
			BentleyAttributeParser bentleyAttributeParser) {
		this.bentleyAttributeParser = bentleyAttributeParser;
	}
	
	
}

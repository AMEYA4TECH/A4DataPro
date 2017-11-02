package com.a4tech.supplier.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import parser.beaconPromotions.BeaconProAttributeParser;
import parser.beaconPromotions.BeaconProPriceGridParser;

import com.a4tech.core.errors.ErrorMessageList;
import com.a4tech.excel.service.IExcelParser;
import com.a4tech.product.dao.service.ProductDao;
import com.a4tech.product.model.AdditionalColor;
import com.a4tech.product.model.AdditionalLocation;
import com.a4tech.product.model.Color;
import com.a4tech.product.model.Dimensions;
import com.a4tech.product.model.ImprintLocation;
import com.a4tech.product.model.ImprintMethod;
import com.a4tech.product.model.ImprintSize;
import com.a4tech.product.model.Material;
import com.a4tech.product.model.Option;
import com.a4tech.product.model.Origin;
import com.a4tech.product.model.Packaging;
import com.a4tech.product.model.PriceGrid;
import com.a4tech.product.model.Product;
import com.a4tech.product.model.ProductConfigurations;
import com.a4tech.product.model.ProductionTime;
import com.a4tech.product.model.ShippingEstimate;
import com.a4tech.product.model.Size;
import com.a4tech.product.service.postImpl.PostServiceImpl;
import com.a4tech.util.ApplicationConstants;
import com.a4tech.util.CommonUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.commons.lang.StringUtils;
public class BeaconProMapping implements IExcelParser{
	
	private static final Logger _LOGGER = Logger.getLogger(BeaconProMapping.class);
	PostServiceImpl postServiceImpl;
	ProductDao productDaoObj;
	BeaconProAttributeParser  beaconProAttributeParser;
	BeaconProPriceGridParser  beaconProPriceGridParser;
	
	@Autowired
	ObjectMapper mapperObj;
	
	public String readExcel(String accessToken,Workbook workbook ,Integer asiNumber,int batchId){
		int columnIndex = 0;
		List<String> numOfProductsSuccess = new ArrayList<String>();
		List<String> numOfProductsFailure = new ArrayList<String>();
		String finalResult = null;
		  
		  Set<String>  listOfProductXids = new HashSet<String>();
		  Product productExcelObj = new Product();  
		  Product existingApiProduct = null;
		  ProductConfigurations productConfigObj=new ProductConfigurations();
		  List<PriceGrid> priceGrids = new ArrayList<PriceGrid>();
		  List<ImprintMethod> imprintMethods = new ArrayList<ImprintMethod>();
		  String shippinglen="";
		  String shippingWid="";
		  String shippingH="";
		   String shippingWeightValue="";
		  String noOfitem="";
		  boolean existingFlag=false;
		  String plateScreenCharge="";
		  String plateScreenChargeCode="";
		  String plateReOrderCharge="";
		  String plateReOrderChargeCode="";
		  String extraColorRucnChrg="";
		  String extraLocRunChrg="";
		  String extraLocColorScreenChrg="";
		  StringBuilder listOfQuantity = new StringBuilder();
		  StringBuilder listOfPrices = new StringBuilder();
		  StringBuilder listOfDiscount = new StringBuilder();
		  String basePricePriceInlcude="";
		  String tempQuant1="";
		  String	imprintMethodValue="";
		  String impSize="";
		  String impLocVal="";
		  try{
			 
		_LOGGER.info("Total sheets in excel::"+workbook.getNumberOfSheets());
	    Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		_LOGGER.info("Started Processing Product");
		 List<ImprintSize> listOfImpSize=new ArrayList<ImprintSize>();
		 List<ImprintLocation> listOfImprintLocValues = new ArrayList<ImprintLocation>();
		 ShippingEstimate	shippingEstObj=new ShippingEstimate();
		
		Set<String>  productXids = new HashSet<String>();
		 List<String> repeatRows = new ArrayList<>();
		 ShippingEstimate ShipingObj=new ShippingEstimate();
		 Dimensions dimensionObj=new Dimensions();
		 String setUpchrgesVal="";
		 String repeatUpchrgesVal="";
		 String priceInlcudeFinal="";
		 String xid = null;
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
			
			 boolean checkXid  = false;
			
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
								 
								 // Ineed to set pricegrid over here
								   // Add repeatable sets here
								/* productExcelObj=bagMakersPriceGridParser.getPricingData(listOfPrices.toString(), listOfQuantity.toString(), listOfDiscount.toString(), basePricePriceInlcude, 
											plateScreenCharge, plateScreenChargeCode,
										    plateReOrderCharge, plateReOrderChargeCode, priceGrids, 
										    productExcelObj, productConfigObj);
								 	productExcelObj.setPriceType("L");*/
								 	//productExcelObj.setPriceGrids(priceGrids);
								 	productExcelObj.setProductConfigurations(productConfigObj);
								 	/* _LOGGER.info("Product Data : "
												+ mapperObj.writeValueAsString(productExcelObj));
								 	*/
								 	/*if(xidList.contains(productExcelObj.getExternalProductId().trim())){
								 		productExcelObj.setAvailability(new ArrayList<Availability>());
								 	}*/
								 	int num = postServiceImpl.postProduct(accessToken, productExcelObj,asiNumber,batchId);
								 	if(num ==1){
								 		numOfProductsSuccess.add("1");
								 	}else if(num == 0) {
								 		numOfProductsFailure.add("0");
								 	}else{
								 		
								 	}
								 	_LOGGER.info("list size of success>>>>>>>"+numOfProductsSuccess.size());
								 	_LOGGER.info("Failure list size>>>>>>>"+numOfProductsFailure.size());
									//reset all list and objects over here
									priceGrids = new ArrayList<PriceGrid>();
									productConfigObj = new ProductConfigurations();
									 ShipingObj=new ShippingEstimate();
									 dimensionObj=new Dimensions();
									 setUpchrgesVal="";
									 plateScreenCharge="";
							  		 plateScreenChargeCode="";
							  		 plateReOrderCharge="";
							  		 plateReOrderChargeCode="";
							  		 extraColorRucnChrg="";
							  	     extraLocRunChrg="";
							  	     extraLocColorScreenChrg="";
							  	    listOfQuantity = new StringBuilder();
							  	    listOfPrices = new StringBuilder();
							  	    listOfDiscount = new StringBuilder();
							  	    basePricePriceInlcude="";
							  	    tempQuant1="";
							  	   

							 }
							    if(!listOfProductXids.contains(xid)){
							    	listOfProductXids.add(xid);
							    }
								 productExcelObj = new Product();
								 existingApiProduct = postServiceImpl.getProduct(accessToken, xid); 
								     if(existingApiProduct == null){
								    	 _LOGGER.info("Existing Xid is not available,product treated as new product");
								    	 productExcelObj = new Product();
								    	 existingFlag=false;
								     }else{//need to confirm what existing data client wnts
								    	  //  productExcelObj=bagMakerAttributeParser.getExistingProductData(existingApiProduct, existingApiProduct.getProductConfigurations());
											productConfigObj=productExcelObj.getProductConfigurations();
											existingFlag=true;
										   // priceGrids = productExcelObj.getPriceGrids();
								     }
						 }
					}
					
					
					
					switch (columnIndex + 1) {
					case 1://XID
						productExcelObj.setExternalProductId(xid);
						break;
					
					case  2: //Product #
						String asiProdNo=CommonUtility.getCellValueStrinOrDecimal(cell);
						if(!StringUtils.isEmpty(asiProdNo)){
							productExcelObj.setAsiProdNo(asiProdNo);
						}
						break;
					case  3://Product Name
						String productName = CommonUtility.getCellValueStrinOrInt(cell);
						//productName = CommonUtility.removeSpecialSymbols(productName,specialCharacters);
						if(!StringUtils.isEmpty(productName)){
						int len=productName.length();
						 if(len>60){
							String strTemp=productName.substring(0, 60);
							int lenTemp= strTemp.lastIndexOf(ApplicationConstants.CONST_VALUE_TYPE_SPACE);
							productName=(String) strTemp.subSequence(0, lenTemp);
						}
						productName=CommonUtility.removeRestrictSymbols(productName);
						productExcelObj.setName(productName);
						}
						break;
					case  4://Plain Description
						String description =CommonUtility.getCellValueStrinOrInt(cell);
						//description = CommonUtility.removeSpecialSymbols(description,specialCharacters);
						if(!StringUtils.isEmpty(description)){
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
					case  5://Plain Keywords
						String keywords = CommonUtility.getCellValueStrinOrInt(cell);
						if(!StringUtils.isEmpty(keywords)){
						List<String> productKeywords = CommonUtility.getStringAsList(keywords,
                                ApplicationConstants.CONST_DELIMITER_COMMA);
						productExcelObj.setProductKeywords(productKeywords);
						/*List<String> productKeywordsTemp=new ArrayList<String>();
						for (String keyword : productKeywords) {
							if(keyword.length()<=30){
								productKeywordsTemp.add(keyword);
							}
						}*/
						
						}
						break;
					case  6://Notes Section
						String additionalProductInfo = CommonUtility.getCellValueStrinOrInt(cell);
						if(!StringUtils.isEmpty(additionalProductInfo)){
						additionalProductInfo=removeSpecialChar(additionalProductInfo);
						productExcelObj.setAdditionalProductInfo(additionalProductInfo);
						}else{
							productExcelObj.setAdditionalProductInfo(ApplicationConstants.CONST_STRING_EMPTY);
						}
						
						break;
					case  7://Technologo URL
						String productDataSheet=CommonUtility.getCellValueStrinOrInt(cell);
						if(!StringUtils.isEmpty(productDataSheet)){
						productExcelObj.setProductDataSheet(productDataSheet);
						}
						break;
					case  8://Technologo Bug
						//ignore
						break;
					case  9://Material
						String material=cell.getStringCellValue();
						if (!StringUtils.isEmpty(material)&& !material.equalsIgnoreCase("")) {
							List<Material> materiallist = new ArrayList<Material>();	
							materiallist=beaconProAttributeParser.getMaterialValue(material);						
							productConfigObj.setMaterials(materiallist);
						}
						break;
					case  10://Standard Includes

						break;
					case  11://Colors Availble
						//pending
						break;
					case  12://Imprint Method 1
						imprintMethodValue=cell.getStringCellValue();
						if(!StringUtils.isEmpty(imprintMethodValue)){
							 imprintMethods = beaconProAttributeParser.getImprintMethods(imprintMethodValue,imprintMethods);
								productConfigObj.setImprintMethods(imprintMethods); 
						  }
						break;
					case  13://Imprint Method 2
						imprintMethodValue=cell.getStringCellValue();
						if(!StringUtils.isEmpty(imprintMethodValue)){
							 imprintMethods = beaconProAttributeParser.getImprintMethods(imprintMethodValue,imprintMethods);
								productConfigObj.setImprintMethods(imprintMethods); 
						  }
						break;
					case  14://Imprint Method 3
						imprintMethodValue=cell.getStringCellValue();
						if(!StringUtils.isEmpty(imprintMethodValue)){
							 imprintMethods = beaconProAttributeParser.getImprintMethods(imprintMethodValue,imprintMethods);
								productConfigObj.setImprintMethods(imprintMethods); 
						  }
						break;
					case  15://Imprint size 1
						 impSize=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(impSize)){
                           
							 listOfImpSize=beaconProAttributeParser.getImprintSize(impSize,listOfImpSize);
							 productConfigObj.setImprintSize(listOfImpSize);
						 }
						break;
					case  16://Imprint Size 2
						 impSize=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(impSize)){
                          
							 listOfImpSize=beaconProAttributeParser.getImprintSize(impSize,listOfImpSize);
							 productConfigObj.setImprintSize(listOfImpSize);
						 }
						break;
					case  17://Imprint Size 3
						 impSize=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(impSize)){
                          
							 listOfImpSize=beaconProAttributeParser.getImprintSize(impSize,listOfImpSize);
							 productConfigObj.setImprintSize(listOfImpSize);
						 }
						break;
					case  18://Imprint location 1
						 impLocVal=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(impLocVal)){
							 listOfImprintLocValues=beaconProAttributeParser.getImprintLocationVal(impLocVal, listOfImprintLocValues);
							 productConfigObj.setImprintLocation(listOfImprintLocValues);
						 }
						break;
					case  19://Imprint location 2
						impLocVal=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(impLocVal)){
							 listOfImprintLocValues=beaconProAttributeParser.getImprintLocationVal(impLocVal, listOfImprintLocValues);
							 productConfigObj.setImprintLocation(listOfImprintLocValues);
						 }
						break;
					case  20://Imprint location 3
						impLocVal=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(impLocVal)){
							 listOfImprintLocValues=beaconProAttributeParser.getImprintLocationVal(impLocVal, listOfImprintLocValues);
							 productConfigObj.setImprintLocation(listOfImprintLocValues);
						 }
						break;
					case  21://Standard Packaging
						String packaging=cell.getStringCellValue();
						if(!StringUtils.isEmpty(packaging)){
							List<Packaging> packagingList=beaconProAttributeParser.getPackagingCriteria(packaging);
							productConfigObj.setPackaging(packagingList);
							
						}	
						break;
					case  22://Product Size
						String sizeValue=cell.getStringCellValue();
						if(!StringUtils.isEmpty(sizeValue)){
							int flag=checkSizesWords(sizeValue);
						/*	if(flag==0){
								1)15" (w) x 12-1/2" (h) x 4-1/2" (d)
								2)3-5/8" x 3-5/8" x 3-5/8"
								3)XS, S, M/L, XL
								4)S-4XL, LT-4XLT
								5)152" x 64"
								6)94" (dia)
								7)6-9 sq in.
								  1-3 sq inches
								8)Small, Medium,  4XL
								sizeValue=sizeValue.toLowerCase().replace("small", "S");
								sizeValue=sizeValue.toLowerCase().replace("medium", "M");
								if(sizeValue.contains("(") && sizeValue.contains(")")){
								}else if(sizeValue.contains("x")){
								}else if(sizeValue.contains("sq in") || sizeValue.contains("sq inches")){
								}else if(){	
								}
							}*/	
						}		
						break;	
					case  23://Prod. Time
						//production time
						String prodTimeLo = "";
						ProductionTime productionTime = new ProductionTime();
						List<ProductionTime> listOfProductionTime = new ArrayList<ProductionTime>();
						prodTimeLo=CommonUtility.getCellValueStrinOrInt(cell);
						if(!StringUtils.isEmpty(prodTimeLo)){
					    prodTimeLo=prodTimeLo.replaceAll(ApplicationConstants.CONST_STRING_DAYS,ApplicationConstants.CONST_STRING_EMPTY);
						productionTime.setBusinessDays(prodTimeLo);
						productionTime.setDetails(ApplicationConstants.CONST_STRING_DAYS);
						listOfProductionTime.add(productionTime);
						productConfigObj.setProductionTime(listOfProductionTime);
						}
						break;
					case  24://Shipping Weight/box
						String shippingWt=CommonUtility.getCellValueDouble(cell);
						 if(!StringUtils.isEmpty(shippingWt)){
							 shippingEstObj=beaconProAttributeParser.getShippingEstimates(shippingWt,shippingEstObj,"WT",0,"",new Dimensions());
							 productConfigObj.setShippingEstimates(shippingEstObj);
						 }
						break;
					case  25://Shipping Qty/Box
						String shippingItem=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(shippingItem)){
							 shippingEstObj=beaconProAttributeParser.getShippingEstimates(shippingItem,shippingEstObj,"NOI",0,"",new Dimensions());
							 productConfigObj.setShippingEstimates(shippingEstObj);
						 }
						break;
					case  26://Shipping Dim. 1
						String dimLen=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(dimLen)){
							 shippingEstObj=beaconProAttributeParser.getShippingEstimates(dimLen,shippingEstObj,"SDIM",0,dimLen,new Dimensions());
							 productConfigObj.setShippingEstimates(shippingEstObj);
						 }
						break;
					case 27://Shipping Dim 2
						String dimWid=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(dimWid)){
							 shippingEstObj=beaconProAttributeParser.getShippingEstimates(dimWid,shippingEstObj,"SDIM",1,dimWid,shippingEstObj.getDimensions());
							 productConfigObj.setShippingEstimates(shippingEstObj);
						 }
						break;
					case 28://Shipping Dim 3
						String dimH=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(dimH)){
							 shippingEstObj=beaconProAttributeParser.getShippingEstimates(dimH,shippingEstObj,"SDIM",2,dimH,shippingEstObj.getDimensions());
							 productConfigObj.setShippingEstimates(shippingEstObj);
						 }
						break;
					case 29://Origination Zip Code
						//(Ignore)
						break;
					case 30://SAGE Category 1
						//(Ignore)
						break;
					case 31://SAGE Category 2
						//(Ignore)
						break;
					case 32://Current Catalog Page
						
						break;
					case 33://Combined Charges

						break;
					case 34://Qty 1

						break;
					case 35://Qty 2

						break;
					case 36://Qty 3

						break;
					case 37://Qty 4

						break;
					case 38://Qty 5

						break;
					case 39://Qty 6

						break;
					case 40://Qty 7

						break;
					case 41://Qty 8

						break;
					case  42://Qty 9

						break;
					case  43://USD1

						break;
					case  44://USD2

						break;
					case  45://USD3

						break;
					case  46://USD4

						break;
					case  47://USD5

						break;
					case  48://USD6

						break;
					case  49://USD7

						break;
					case  50://USD8

						break;
					
					case  51://USD9

						break;
						
					case  52://Discount Code

						break;
					
					}
				} // end inner while loop
			}catch(Exception e){
				_LOGGER.error("Error while Processing ProductId and cause :"+productExcelObj.getExternalProductId() +" "+e.getMessage()+"at column number(increament by 1):"+columnIndex);		 
				ErrorMessageList apiResponse = CommonUtility.responseconvertErrorMessageList("Product Data issue in Supplier Sheet: "
				+e.getMessage()+" at column number(increament by 1)"+columnIndex);
				productDaoObj.save(apiResponse.getErrors(),
						productExcelObj.getExternalProductId()+"-Failed", asiNumber, batchId);
				}
	}
	workbook.close();
	 	
	 // setting pricing over here
	 // need to create a map over here 
	 //color upcharges
	 //same goes here as well
	 //color & location
	 // same goes here as well
	 // Ineed to set pricegrid over here
     // Add repeatable sets here
		/*productExcelObj=bagMakersPriceGridParser.getPricingData(listOfPrices.toString(), listOfQuantity.toString(), listOfDiscount.toString(),basePricePriceInlcude,  
																plateScreenCharge, plateScreenChargeCode,
															    plateReOrderCharge, plateReOrderChargeCode, priceGrids, 
															    productExcelObj, productConfigObj);*/
		
	 	productExcelObj.setPriceType("L");
	 	//productExcelObj.setPriceGrids(priceGrids);
	 	productExcelObj.setProductConfigurations(productConfigObj);
	 	/* _LOGGER.info("Product Data : "
					+ mapperObj.writeValueAsString(productExcelObj));
	 	*/
	 	/*if(xidList.contains(productExcelObj.getExternalProductId().trim())){
	 		productExcelObj.setAvailability(new ArrayList<Availability>());
	 	}*/
	 	int num = postServiceImpl.postProduct(accessToken, productExcelObj,asiNumber,batchId);
	 	if(num ==1){
	 		numOfProductsSuccess.add("1");
	 	}else if(num == 0) {
	 		numOfProductsFailure.add("0");
	 	}else{
	 		
	 	}
	 	_LOGGER.info("list size>>>>>>"+numOfProductsSuccess.size());
	 	_LOGGER.info("Failure list size>>>>>>"+numOfProductsFailure.size());
       finalResult = numOfProductsSuccess.size() + "," + numOfProductsFailure.size();
       
       productDaoObj.saveErrorLog(asiNumber,batchId);

   	//reset all list and objects over here
		priceGrids = new ArrayList<PriceGrid>();
		productConfigObj = new ProductConfigurations();
		ShipingObj=new ShippingEstimate();
		dimensionObj=new Dimensions();
		setUpchrgesVal="";
		plateScreenCharge="";
 		 plateScreenChargeCode="";
 		 plateReOrderCharge="";
 		 plateReOrderChargeCode="";
 		 extraColorRucnChrg="";
 	     extraLocRunChrg="";
 	     extraLocColorScreenChrg="";
 	    listOfQuantity = new StringBuilder();
  	    listOfPrices = new StringBuilder();
  	    listOfDiscount = new StringBuilder();
  	   basePricePriceInlcude="";
  	   tempQuant1="";
       return finalResult;
	}catch(Exception e){
		_LOGGER.error("Error while Processing excel sheet ,Error message: "+e.getMessage()+"for column"+columnIndex+1);
		return finalResult;
	}finally{
		try {
			workbook.close();
		} catch (IOException e) {
			_LOGGER.error("Error while Processing excel sheet, Error message: "+e.getMessage()+"for column" +columnIndex+1);

		}
			_LOGGER.info("Complted processing of excel sheet ");
			_LOGGER.info("Total no of product:"+numOfProductsSuccess.size() );
	}
	
}
	
	public static int  checkSizesWords(String sizeValue){
		int i=org.apache.commons.lang.StringUtils.indexOfAny(sizeValue, new String[]{
				"stick","compact","extended","bag","tripod","closed","lens","clip","diameter","pages","star","arrow","case","highlighter",
				"sides","blade","all","open","blocks","tall","available","folded","small","tie","strings",
				"cap","rain","gauge","bottle","approx","barrel","head","k3a","m3a","ties","with","widest","scraper","assembled","round"
		});
		return i;
	}
	public String getProductXid(Row row){
		Cell xidCell =  row.getCell(0);
		String productXid = CommonUtility.getCellValueStrinOrInt(xidCell);
		if(StringUtils.isEmpty(productXid) || productXid.trim().equalsIgnoreCase("#N/A")){
		     xidCell = row.getCell(2);
		     productXid = CommonUtility.getCellValueStrinOrInt(xidCell);
		}
		return productXid;
	}
	
	
	
	public static String removeSpecialChar(String tempValue){
		tempValue=tempValue.trim();
		tempValue=tempValue.replaceAll("(Day|Service|Sheets|Packages|Roll|Rolls|Days|Hour|Hours|Week|Weeks|Rush|R|u|s|h)", "");
		tempValue=tempValue.replaceAll("\\(","");
		tempValue=tempValue.replaceAll("\\)","");
	return tempValue;
	}
	
	public static List<String> getImprintAliasList(List<ImprintMethod> listOfImprintMethod){
		ArrayList<String> tempList=new ArrayList<String>();
		
		for (ImprintMethod tempMthd : listOfImprintMethod) {
			String strTemp=tempMthd.getAlias();
			if(strTemp.contains("LASER")|| strTemp.contains("DIRECT")|| strTemp.contains("VINYL")||strTemp.contains("DYE")||strTemp.contains("HEAT")||strTemp.contains("EPOXY")){
				tempList.add(strTemp);
			}
		}
		return tempList;
		
	}
	
	public ProductDao getProductDaoObj() {
		return productDaoObj;
	}

	public void setProductDaoObj(ProductDao productDaoObj) {
		this.productDaoObj = productDaoObj;
	}

	public PostServiceImpl getPostServiceImpl() {
		return postServiceImpl;
	}

	public void setPostServiceImpl(PostServiceImpl postServiceImpl) {
		this.postServiceImpl = postServiceImpl;
	}


	public  static String getQuantValue(String value){
		String temp[]=value.split("-");
		return temp[0];
		
	}


	public BeaconProAttributeParser getBeaconProAttributeParser() {
		return beaconProAttributeParser;
	}


	public void setBeaconProAttributeParser(
			BeaconProAttributeParser beaconProAttributeParser) {
		this.beaconProAttributeParser = beaconProAttributeParser;
	}


	public BeaconProPriceGridParser getBeaconProPriceGridParser() {
		return beaconProPriceGridParser;
	}


	public void setBeaconProPriceGridParser(
			BeaconProPriceGridParser beaconProPriceGridParser) {
		this.beaconProPriceGridParser = beaconProPriceGridParser;
	}
	
	

}

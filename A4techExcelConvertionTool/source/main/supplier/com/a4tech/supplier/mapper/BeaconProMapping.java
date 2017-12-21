package com.a4tech.supplier.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
import parser.beaconPromotions.BeaconProductMaterialParser;
import parser.beaconPromotions.UpchargeConstants;

import com.a4tech.core.errors.ErrorMessageList;
import com.a4tech.excel.service.IExcelParser;
import com.a4tech.lookup.service.LookupServiceData;
import com.a4tech.product.dao.service.ProductDao;
import com.a4tech.product.model.AdditionalColor;
import com.a4tech.product.model.AdditionalLocation;
import com.a4tech.product.model.Apparel;
import com.a4tech.product.model.Catalog;
import com.a4tech.product.model.Color;
import com.a4tech.product.model.Dimension;
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
import com.a4tech.product.model.Value;
import com.a4tech.product.model.Values;
import com.a4tech.product.service.postImpl.PostServiceImpl;
import com.a4tech.sage.product.util.LookupData;
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
	BeaconProductMaterialParser beaconProductMaterialParser;
	private LookupServiceData lookupServiceDataObj;
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
		
		  StringBuilder listOfQuantity = new StringBuilder();
		  StringBuilder listOfPrices = new StringBuilder();
		  StringBuilder listOfDiscount = new StringBuilder();
		  String basePricePriceInlcude="";
		  String tempQuant1="";
		  String	imprintMethodValue="";
		  String impSize="";
		  String impLocVal="";
		  try{
			  String basePriceInclude="";
			  String productName="";
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
								 if( !StringUtils.isEmpty(listOfPrices.toString())){
									 priceGrids=new ArrayList<PriceGrid>();
									 String discCheck=listOfDiscount.toString();
									 if(discCheck.contains("0")){
										 priceGrids = beaconProPriceGridParser.getPriceGridsQur();
									 }else{
										 
										/* (String listOfPrices, String listOfQuan, String discountCodes,String currency, String priceInclude,
			 boolean isBasePrice,String qurFlag, String priceName, 
			 String criterias,Integer sequence,String serviceCharge,String upChargeType,String upchargeUsageType,String optionName,
			 List<PriceGrid> existingPriceGrid)  */
									 priceGrids = beaconProPriceGridParser.getPriceGrids(listOfPrices.toString(),listOfQuantity.toString(), 
											    listOfDiscount.toString(),ApplicationConstants.CONST_STRING_CURRENCY_USD,basePriceInclude,
												ApplicationConstants.CONST_BOOLEAN_TRUE, ApplicationConstants.CONST_STRING_FALSE, 
												productName,null,1,null,null,null,null,priceGrids);
									 }
									 }
									 
									 if(CollectionUtils.isEmpty(priceGrids)){
											priceGrids = beaconProPriceGridParser.getPriceGridsQur();	
										}
									 	productExcelObj.setPriceType("L");
									 	productExcelObj.setPriceGrids(priceGrids);
									 	productExcelObj.setProductConfigurations(productConfigObj);
								 	 _LOGGER.info("Product Data : "
												+ mapperObj.writeValueAsString(productExcelObj));
								 	
								 	int num = 0;//postServiceImpl.postProduct(accessToken, productExcelObj,asiNumber,batchId);
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
							  	    listOfQuantity = new StringBuilder();
							  	    listOfPrices = new StringBuilder();
							  	    listOfDiscount = new StringBuilder();
							  	    listOfImpSize=new ArrayList<ImprintSize>();
							  	    listOfImprintLocValues = new ArrayList<ImprintLocation>();
							  	    imprintMethods = new ArrayList<ImprintMethod>();
							  	    basePricePriceInlcude="";
							  	    tempQuant1="";
							  	    productName="";
							  	    basePriceInclude="";
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
								    	    productExcelObj=beaconProAttributeParser.getExistingProductData(existingApiProduct, existingApiProduct.getProductConfigurations());
											//productConfigObj=productExcelObj.getProductConfigurations();
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
						 productName = CommonUtility.getCellValueStrinOrInt(cell);
						//productName = CommonUtility.removeSpecialSymbols(productName,specialCharacters);
						if(!StringUtils.isEmpty(productName.trim())){
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
						if(!StringUtils.isEmpty(description.trim())){
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
						if(!StringUtils.isEmpty(keywords.trim())){
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
						if(!StringUtils.isEmpty(additionalProductInfo.trim())){
						additionalProductInfo=removeSpecialChar(additionalProductInfo);
						productExcelObj.setAdditionalProductInfo(additionalProductInfo);
						}else{
							productExcelObj.setAdditionalProductInfo(ApplicationConstants.CONST_STRING_EMPTY);
						}
						
						break;
					case  7://Technologo URL
						String productDataSheet=CommonUtility.getCellValueStrinOrInt(cell);
						if(!StringUtils.isEmpty(productDataSheet.trim())){
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
						basePriceInclude=CommonUtility.getCellValueStrinOrInt(cell);
						break;
					case  11://Colors Availble
						String colorValue=CommonUtility.getCellValueStrinOrInt(cell);
						if(!StringUtils.isEmpty(colorValue)){
							 List<Color> colors =beaconProAttributeParser.getProductColors(colorValue);
							 productConfigObj.setColors(colors);
						 }
						//pending
						break;
					case  12://Imprint Method 1
						imprintMethodValue=cell.getStringCellValue();
						if(!StringUtils.isEmpty(imprintMethodValue.trim())){
							 imprintMethods = beaconProAttributeParser.getImprintMethods(imprintMethodValue,imprintMethods);
								productConfigObj.setImprintMethods(imprintMethods); 
						  }
						break;
					case  13://Imprint Method 2
						imprintMethodValue=cell.getStringCellValue();
						if(!StringUtils.isEmpty(imprintMethodValue.trim())){
							 imprintMethods = beaconProAttributeParser.getImprintMethods(imprintMethodValue,imprintMethods);
								productConfigObj.setImprintMethods(imprintMethods); 
						  }
						break;
					case  14://Imprint Method 3
						imprintMethodValue=cell.getStringCellValue();
						if(!StringUtils.isEmpty(imprintMethodValue.trim())){
							 imprintMethods = beaconProAttributeParser.getImprintMethods(imprintMethodValue,imprintMethods);
								productConfigObj.setImprintMethods(imprintMethods); 
						  }
						break;
					case  15://Imprint size 1
						 impSize=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(impSize.trim())){
                           
							 listOfImpSize=beaconProAttributeParser.getImprintSize(impSize,listOfImpSize);
							 productConfigObj.setImprintSize(listOfImpSize);
						 }
						break;
					case  16://Imprint Size 2
						 impSize=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(impSize.trim())){
                          
							 listOfImpSize=beaconProAttributeParser.getImprintSize(impSize,listOfImpSize);
							 productConfigObj.setImprintSize(listOfImpSize);
						 }
						break;
					case  17://Imprint Size 3
						 impSize=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(impSize.trim())){
                          
							 listOfImpSize=beaconProAttributeParser.getImprintSize(impSize,listOfImpSize);
							 productConfigObj.setImprintSize(listOfImpSize);
						 }
						break;
					case  18://Imprint location 1
						 impLocVal=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(impLocVal.trim())){
							 listOfImprintLocValues=beaconProAttributeParser.getImprintLocationVal(impLocVal, listOfImprintLocValues);
							 productConfigObj.setImprintLocation(listOfImprintLocValues);
						 }
						break;
					case  19://Imprint location 2
						impLocVal=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(impLocVal.trim())){
							 listOfImprintLocValues=beaconProAttributeParser.getImprintLocationVal(impLocVal, listOfImprintLocValues);
							 productConfigObj.setImprintLocation(listOfImprintLocValues);
						 }
						break;
					case  20://Imprint location 3
						impLocVal=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(impLocVal.trim())){
							 listOfImprintLocValues=beaconProAttributeParser.getImprintLocationVal(impLocVal, listOfImprintLocValues);
							 productConfigObj.setImprintLocation(listOfImprintLocValues);
						 }
						break;
					case  21://Standard Packaging
						String packaging=cell.getStringCellValue();
						if(!StringUtils.isEmpty(packaging.trim())){
							List<Packaging> packagingList=beaconProAttributeParser.getPackagingCriteria(packaging);
							productConfigObj.setPackaging(packagingList);
							
						}	
						break;
					case  22://Product Size
						String sizeValue=cell.getStringCellValue();
						if(!StringUtils.isEmpty(sizeValue.trim())){
							Size size=new Size();
							int flag=checkSizesWords(sizeValue);
							if(flag!=0){
								/*1)15" (w) x 12-1/2" (h) x 4-1/2" (d)
								2)3-5/8" x 3-5/8" x 3-5/8"
								3)XS, S, M/L, XL
								4)S-4XL, LT-4XLT
								5)152" x 64"
								6)94" (dia)
								7)6-9 sq in.
								  1-3 sq inches
								8)Small, Medium,  4XL*/
								sizeValue=sizeValue.toLowerCase().replace("small", "S");
								sizeValue=sizeValue.toLowerCase().replace("medium", "M");
								if(sizeValue.contains("(") && sizeValue.contains(")")){
									sizeValue=sizeValue.replace("\"", "");
									String tempSTR[]=sizeValue.split("x");

									ArrayList<Value> valueList = new ArrayList<Value>();
									List<Values> valueslist = new ArrayList<Values>();
									 Dimension finalDimensionObj=new Dimension();
									Values valuesObj = new Values();
									Value valueObj = null;
									for (String value : tempSTR) {
										String attrVal=value.substring(value.indexOf("(")+1);
										value=value.replace("(", "@@");
										value=value.replace(")", "");
										value=value.replace("-", " ");
										String temp[]=value.split("@@");
										attrVal=sizeDimMap.get(temp[1].toLowerCase().trim());
										value=value.replace("@@"+temp[1], "");
										valueObj = new Value();
										valueObj.setValue(value.trim());
										valueObj.setUnit("in");
										valueObj.setAttribute(attrVal);
										valueList.add(valueObj);
										
									}
									
									valuesObj.setValue(valueList);
									valueslist.add(valuesObj);
									finalDimensionObj.setValues(valueslist);	
								
									size.setDimension(finalDimensionObj);
									productConfigObj.setSizes(size);
								}else if(sizeValue.contains("x") && !sizeValue.contains("(") && !sizeValue.contains(")")){
									sizeValue=sizeValue.replace("\"", "");
									String tempSTR[]=sizeValue.split("x");

									ArrayList<Value> valueList = new ArrayList<Value>();
									List<Values> valueslist = new ArrayList<Values>();
									 Dimension finalDimensionObj=new Dimension();
									Values valuesObj = new Values();
									Value valueObj = null;
									int count=1;
									for (String value : tempSTR) {
										value=value.replace("-", " ");
										valueObj = new Value();
										valueObj.setValue(value);
										valueObj.setUnit("in");
										if(count==1){
											valueObj.setAttribute("Length");
										}else if(count==2){
											valueObj.setAttribute("Width");
										}else if(count==3){
											valueObj.setAttribute("Height");
										}
										valueList.add(valueObj);
										count++;
									}
									
									valuesObj.setValue(valueList);
									valueslist.add(valuesObj);
									finalDimensionObj.setValues(valueslist);	
								
									size.setDimension(finalDimensionObj);
									productConfigObj.setSizes(size);
									
									
									
									
									
								}else if(sizeValue.contains("sq in") || sizeValue.contains("sq inches")){
									sizeValue=sizeValue.replace("-", " ");
									sizeValue=sizeValue.replace("sq in", " ");
									sizeValue=sizeValue.replace(".", " ");
									sizeValue=sizeValue.replace("sq inches", " ");
									sizeValue=sizeValue.replace("\"", "");
									String tempSTR[]=sizeValue.split("x");

									ArrayList<Value> valueList = new ArrayList<Value>();
									List<Values> valueslist = new ArrayList<Values>();
									 Dimension finalDimensionObj=new Dimension();
									Values valuesObj = new Values();
									Value valueObj = null;
									int count=1;
									for (String value : tempSTR) {
										value=value.replace("-", " ");
										valueObj = new Value();
										valueObj.setValue(value);
										valueObj.setUnit("in");
										if(count==1){
											valueObj.setAttribute("Length");
										}else if(count==2){
											valueObj.setAttribute("Width");
										}else if(count==3){
											valueObj.setAttribute("Height");
										}
										valueList.add(valueObj);
										count++;
									}
									
									valuesObj.setValue(valueList);
									valueslist.add(valuesObj);
									finalDimensionObj.setValues(valueslist);	
								
									size.setDimension(finalDimensionObj);
									productConfigObj.setSizes(size);
								}else if(checkSizesWordsForStdNum(sizeValue)){
									String strTemp=sizeValue;
									if(sizeValue.contains("-")){
										sizeValue=sizeAttri.get(sizeValue);
									}
									
									//Size size = new Size();
									Apparel apparel = new Apparel();
									List<Value> listOfSizeValues = getSizeValues(sizeValue);
									apparel.setType("Standard & Numbered");
									apparel.setValues(listOfSizeValues);
									size.setApparel(apparel);
									productConfigObj.setSizes(size);
								}
							}
						}		
						break;	
					case  23://Prod. Time
						//production time
						String prodTimeLo = "";
						ProductionTime productionTime = new ProductionTime();
						List<ProductionTime> listOfProductionTime = new ArrayList<ProductionTime>();
						prodTimeLo=CommonUtility.getCellValueStrinOrInt(cell);
						if(!StringUtils.isEmpty(prodTimeLo.trim())){
					    prodTimeLo=prodTimeLo.replaceAll(ApplicationConstants.CONST_STRING_DAYS,ApplicationConstants.CONST_STRING_EMPTY);
						productionTime.setBusinessDays(prodTimeLo);
						productionTime.setDetails(ApplicationConstants.CONST_STRING_DAYS);
						listOfProductionTime.add(productionTime);
						productConfigObj.setProductionTime(listOfProductionTime);
						}
						break;
					case  24://Shipping Weight/box
						String shippingWt=CommonUtility.getCellValueDouble(cell);
						 if(!StringUtils.isEmpty(shippingWt.trim())){
							 shippingEstObj=beaconProAttributeParser.getShippingEstimates(shippingWt,shippingEstObj,"WT",0,"",new Dimensions());
							 productConfigObj.setShippingEstimates(shippingEstObj);
						 }
						break;
					case  25://Shipping Qty/Box
						String shippingItem=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(shippingItem.trim())){
							 shippingEstObj=beaconProAttributeParser.getShippingEstimates(shippingItem,shippingEstObj,"NOI",0,"",new Dimensions());
							 productConfigObj.setShippingEstimates(shippingEstObj);
						 }
						break;
					case  26://Shipping Dim. 1
						String dimLen=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(dimLen.trim())){
							 shippingEstObj=beaconProAttributeParser.getShippingEstimates(dimLen,shippingEstObj,"SDIM",0,dimLen,new Dimensions());
							 productConfigObj.setShippingEstimates(shippingEstObj);
						 }
						break;
					case 27://Shipping Dim 2
						String dimWid=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(dimWid.trim())){
							 shippingEstObj=beaconProAttributeParser.getShippingEstimates(dimWid,shippingEstObj,"SDIM",1,dimWid,shippingEstObj.getDimensions());
							 productConfigObj.setShippingEstimates(shippingEstObj);
						 }
						break;
					case 28://Shipping Dim 3
						String dimH=CommonUtility.getCellValueStrinOrInt(cell);
						 if(!StringUtils.isEmpty(dimH.trim())){
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
						
						//Canyon|6
						//Beacon|174, 176, 184
						//Canyon|Disco
						try{
						String catValue=CommonUtility.getCellValueStrinOrInt(cell);
						boolean flag=false;
						if(catValue.contains("|")){
						String arrTemp[]=catValue.split("\\|");
						Pattern pattern = Pattern.compile(".*[^0-9].*");
						flag=pattern.matcher(arrTemp[1]).matches();
						
						if(!flag){
							String catValues=arrTemp[0];
							String catpages=arrTemp[1];
							List<Catalog> catalogs=new ArrayList<Catalog>();
							 List<String> catalogsList=new ArrayList<>();
							catalogsList = beaconProAttributeParser.getCatalog(accessToken);
							ArrayList<String> tempList=new ArrayList<String>();
							Catalog catlogObj=new Catalog();
							List<String> catalogValues = catalogsList.stream()
	                                .filter(ctName -> catValues.contains(ctName))
	                                .collect(Collectors.toList());
							if(!CollectionUtils.isEmpty(catalogValues)){
								for (String catNam : catalogValues) {
									catlogObj.setCatalogName(catNam);
								}
							}
							catlogObj.setCatalogPage(catpages);
							catalogs.add(catlogObj);
							productExcelObj.setCatalogs(catalogs);
							/*if(CatYear.contains("2017")){
							catlogObj.setCatalogName("2017 Goldstar Canada");
							catlogObj.setCatalogPage(PageNO);
							catalogList.add(catlogObj);
							productExcelObj.setCatalogs(catalogList);
							}*/
							
							
						}else{
							
							String value=null;
							 List<String> catalogsList=new ArrayList<>();
							catalogsList = beaconProAttributeParser.getCatalog(accessToken);
							ArrayList<String> tempList=new ArrayList<String>();
							List<Catalog> catalogs=new ArrayList<Catalog>();
							Catalog catlogObj=new Catalog();
							List<String> catalogValues = catalogsList.stream()
	                                .filter(mtrlName -> value.contains(mtrlName))
	                                .collect(Collectors.toList());
							if(!CollectionUtils.isEmpty(catalogValues)){
								for (String catNam : catalogValues) {
									catlogObj.setCatalogName(catNam);
									catlogObj.setCatalogPage("");
									catalogs.add(catlogObj);
									
								}
								productExcelObj.setCatalogs(catalogs);
							}
							
							
						}
						
						}else{
							List<Catalog> catalogs=new ArrayList<Catalog>();
							Catalog catlogObj=new Catalog();
							catlogObj.setCatalogName(catValue);
							catlogObj.setCatalogPage("");
							catalogs.add(catlogObj);
							productExcelObj.setCatalogs(catalogs);
						}
						
 						/*if(CatYear.contains("2017")){
							catlogObj.setCatalogName("2017 Goldstar Canada");
							catlogObj.setCatalogPage(PageNO);
							catalogList.add(catlogObj);
							productExcelObj.setCatalogs(catalogList);
						}*/
					}catch(Exception e){
						_LOGGER.error("Error while processing catalog");
					}
						
						break;
					case 33://Combined Charges
							String combineCharge=CommonUtility.getCellValueStrinOrInt(cell);
							if(!StringUtils.isEmpty(combineCharge))
							{
								String combArr[]=combineCharge.split("\\|");
								for (String strComb : combArr) {
									strComb=UpchargeConstants.UPCHARGE_MAP.get(strComb);
									if(!StringUtils.isEmpty(strComb)){
									productExcelObj=beaconProAttributeParser.getCombinedCharge(strComb, productExcelObj);
								}
								}
							}
							
						break;
					case 34://Qty 1
					case 35://Qty 2
					case 36://Qty 3
					case 37://Qty 4
					case 38://Qty 5
					case 39://Qty 6
					case 40://Qty 7
					case 41://Qty 8
					case  42://Qty 9
						String quantity = CommonUtility.getCellValueStrinOrInt(cell);
						  if(!StringUtils.isEmpty(quantity)){
					        	 listOfQuantity.append(quantity).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
					         }
						break;
					case  43://USD1
					case  44://USD2
					case  45://USD3
					case  46://USD4
					case  47://USD5
					case  48://USD6
					case  49://USD7
					case  50://USD8
					case  51://USD9
							
							String listPrice3=CommonUtility.getCellValueStrinOrDecimal(cell);
							listPrice3=listPrice3.replaceAll(" ","");
							if(!StringUtils.isEmpty(listPrice3)){
								listOfPrices.append(listPrice3.trim()).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
							 }
						break;
						
					case  52://Discount Code
						String discount = CommonUtility.getCellValueStrinOrInt(cell);
						  if(!StringUtils.isEmpty(discount)){
					        	 listOfDiscount.append(discount).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
					         }
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
	
		
	 if( !StringUtils.isEmpty(listOfPrices.toString())){
		 priceGrids=new ArrayList<PriceGrid>();
		 String discCheck=listOfDiscount.toString();
		 if(discCheck.contains("0")){
			 priceGrids = beaconProPriceGridParser.getPriceGridsQur();
		 }else{
		 priceGrids = beaconProPriceGridParser.getPriceGrids(listOfPrices.toString(),listOfQuantity.toString(), 
				    listOfDiscount.toString(),ApplicationConstants.CONST_STRING_CURRENCY_USD,
					basePriceInclude,ApplicationConstants.CONST_BOOLEAN_TRUE, ApplicationConstants.CONST_STRING_FALSE, 
					productName,null,1,null,null,null,null,priceGrids);
		 }
		 }
		 
		 if(CollectionUtils.isEmpty(priceGrids)){
				priceGrids = beaconProPriceGridParser.getPriceGridsQur();	
			}
		 	productExcelObj.setPriceType("L");
		 	productExcelObj.setPriceGrids(priceGrids);
		 	productExcelObj.setProductConfigurations(productConfigObj);
		 	 _LOGGER.info("Product Data : "
						+ mapperObj.writeValueAsString(productExcelObj));
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
		
 	    listOfQuantity = new StringBuilder();
  	    listOfPrices = new StringBuilder();
  	    listOfDiscount = new StringBuilder();
  	    listOfImpSize=new ArrayList<ImprintSize>();
  	    listOfImprintLocValues = new ArrayList<ImprintLocation>();
  	    imprintMethods = new ArrayList<ImprintMethod>();
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
	
	public static boolean  checkSizesWordsForStdNum(String sizeValue){
		int i=org.apache.commons.lang.StringUtils.indexOfAny(sizeValue, new String[]{
				"XS","XLT","4XLT","XL","X","S","L","M","T","LT","XS"
		});
		if(i==0){
			return true;
		}else{
			return false;
		}
		
		
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
	
	public LookupServiceData getLookupServiceDataObj() {
		return lookupServiceDataObj;
	}

	public void setLookupServiceDataObj(LookupServiceData lookupServiceDataObj) {
		this.lookupServiceDataObj = lookupServiceDataObj;
	}
	
	public BeaconProductMaterialParser getBeaconProductMaterialParser() {
		return beaconProductMaterialParser;
	}

	public void setBeaconProductMaterialParser(
			BeaconProductMaterialParser beaconProductMaterialParser) {
		this.beaconProductMaterialParser = beaconProductMaterialParser;
	}

	public static List<Value> getSizeValues(String sizes){
		List<Value> listOfSizeValues = new ArrayList<Value>();
		Value valueObj = null;
		String[] values = sizes.split(ApplicationConstants.CONST_DELIMITER_COMMA);
		for (String string : values) {
			valueObj = new Value();
			valueObj.setValue(string);
			listOfSizeValues.add(valueObj);
		}
		return listOfSizeValues;
	}
	static HashMap<String, String> sizeDimMap=new HashMap<String, String>();
	static HashMap<String, String> sizeAttri=new HashMap<String, String>();
	static{
		sizeDimMap.put("w", "Width");
		sizeDimMap.put("h", "Height");
		sizeDimMap.put("d", "Depth");
		sizeDimMap.put("l", "Length");
		sizeDimMap.put("dia", "Dia");
		sizeDimMap.put("diameter", "Dia");
		
		sizeAttri.put("XS-3XL","XS,S,M,L,XL,2XL,3XL,XL,2XL,3XL");
		sizeAttri.put("1X-3X","XS,S,M,L,XL,2XL,3XL,XL,2XL,3XL");
		sizeAttri.put("XS-4X","XS,X,M,L,XL,2XL,3XL,4XL");
		sizeAttri.put("XS-3XL","XS,S,M,L,XL,2XL,3XL");
		sizeAttri.put("3XL-4XL","3XL,4XL");
		sizeAttri.put("S-4XL","S,M,L,XL,2XL,3XL,4XL");
		sizeAttri.put("LT-4XLT","LT,XLT,2XLT,3XLT,4XLT");
		sizeAttri.put("S-3XL","S,M,L,XL,2XL,3XL");
		sizeAttri.put("L-XL","L,XL");
		sizeAttri.put("S-M","S,M");
		sizeAttri.put("M-2XL","M,L,XL,2XL");
		sizeAttri.put("2XL-4XL","2XL,3XL,4Xl");
		sizeAttri.put("M–2X","M,L,XL,2XS");
		
		}
	}

package com.a4tech.v2.core.excelMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.a4tech.product.dao.service.ProductDao;
import com.a4tech.util.ApplicationConstants;
import com.a4tech.util.LookupData;
import com.a4tech.v2.core.model.Artwork;
import com.a4tech.v2.core.model.Catalog;
import com.a4tech.v2.core.model.Color;
import com.a4tech.v2.core.model.Image;
import com.a4tech.v2.core.model.ImprintColor;
import com.a4tech.v2.core.model.ImprintMethod;
import com.a4tech.v2.core.model.Inventory;
import com.a4tech.v2.core.model.Material;
import com.a4tech.v2.core.model.Option;
import com.a4tech.v2.core.model.Origin;
import com.a4tech.v2.core.model.Personalization;
import com.a4tech.v2.core.model.PriceGrid;
import com.a4tech.v2.core.model.Product;
import com.a4tech.v2.core.model.ProductConfigurations;
import com.a4tech.v2.core.model.ProductNumber;
import com.a4tech.v2.core.model.ProductSkus;
import com.a4tech.v2.core.model.ProductionTime;
import com.a4tech.v2.core.model.RushTime;
import com.a4tech.v2.core.model.SameDayRush;
import com.a4tech.v2.core.model.Samples;
import com.a4tech.v2.core.model.Shape;
import com.a4tech.v2.core.model.ShippingEstimate;
import com.a4tech.v2.core.model.Size;
import com.a4tech.v2.core.model.TradeName;
import com.a4tech.v2.criteria.parser.CatalogParser;
import com.a4tech.v2.criteria.parser.PersonlizationParser;
import com.a4tech.v2.criteria.parser.PriceGridParser;
import com.a4tech.v2.criteria.parser.ProductArtworkProcessor;
import com.a4tech.v2.criteria.parser.ProductColorParser;
import com.a4tech.v2.criteria.parser.ProductImprintColorParser;
import com.a4tech.v2.criteria.parser.ProductImprintMethodParser;
import com.a4tech.v2.criteria.parser.ProductMaterialParser;
import com.a4tech.v2.criteria.parser.ProductNumberParser;
import com.a4tech.v2.criteria.parser.ProductOptionParser;
import com.a4tech.v2.criteria.parser.ProductOriginParser;
import com.a4tech.v2.criteria.parser.ProductPackagingParser;
import com.a4tech.v2.criteria.parser.ProductRushTimeParser;
import com.a4tech.v2.criteria.parser.ProductSameDayParser;
import com.a4tech.v2.criteria.parser.ProductSampleParser;
import com.a4tech.v2.criteria.parser.ProductShapeParser;
import com.a4tech.v2.criteria.parser.ProductSizeParser;
import com.a4tech.v2.criteria.parser.ProductSkuParser;
import com.a4tech.v2.criteria.parser.ProductThemeParser;
import com.a4tech.v2.criteria.parser.ProductTradeNameParser;
import com.a4tech.v2.criteria.parser.ProductionTimeParser;
import com.a4tech.v2.criteria.parser.ShippingEstimationParser;
import com.a4tech.v2.service.postImpl.PostServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

public class V2ExcelMapping {
	
	private static final Logger _LOGGER = Logger.getLogger(V2ExcelMapping.class);
	@Autowired
	PostServiceImpl postServiceImplV2 ;//= new PostServiceImpl();
	
	ProductDao productDaoObj;
	@SuppressWarnings("finally")
	public int readExcel(String accessToken,Workbook workbook,int asiNumber,int batchId){
		ImprintColor imprintColorsObj = new ImprintColor();
		 //List<ImprintColorValue> imprintColors = new ArrayList<ImprintColorValue>();
		List<String> numOfProducts = new ArrayList<String>();
		//Workbook workbook = null;
		List<String>  productXids = new ArrayList<String>();
		String productId = null;
		  Product productExcelObj = new Product();   
		  ProductConfigurations productConfigObj=new ProductConfigurations();
		  ProductSkuParser skuparserobj=new ProductSkuParser();
		  String externalProductId = null;
		  String currencyType = null;
		  String priceQurFlag = null;
		  String priceType    = null;
		  String basePriceName = null;
		  String priceIncludes = null;
		  PriceGridParser priceGridParser = new PriceGridParser();
		  String upChargeName = null;
		  String upChargeQur = null;
		  String upchargeType = null;
		  String upChargeDetails = null;
		  String upChargeLevel = null;
		  List<PriceGrid> priceGrids = new ArrayList<PriceGrid>();
		  
		  
		  ProductNumberParser pnumberParser=new ProductNumberParser();
		try{
	    Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		_LOGGER.info("Started Processing Product");
		StringBuilder listOfQuantity = new StringBuilder();
		StringBuilder listOfPrices = new StringBuilder();
		StringBuilder listOfDiscount = new StringBuilder();
		StringBuilder basePriceCriteria =  new StringBuilder();
		StringBuilder UpCharQuantity = new StringBuilder();
		StringBuilder UpCharPrices = new StringBuilder();
		StringBuilder UpCharDiscount = new StringBuilder();
		StringBuilder UpCharCriteria = new StringBuilder();
		String quantity = null;
		String SKUCriteria1 =null;
		String SKUCriteria2 =null;
		String skuvalue  =null;
		String Inlink  =null;
		String Instatus  =null;
		String InQuantity=null;
		ProductSkus skuObj= new ProductSkus();
		List<ProductSkus> productsku=new ArrayList<ProductSkus>();
		
		String productNumberCriteria1=null;
		String productNumberCriteria2=null;
		String productNumber=null;
		ProductNumber		pnumObj=new ProductNumber();
		List<ProductNumber> pnumberList=new ArrayList<ProductNumber>();
		
		List<Option> option=new ArrayList<Option>();
		Option optionobj= new Option();
		ProductOptionParser optionparserobj=new ProductOptionParser();
		String optiontype =null;
		String optionname =null;
		String optionvalues =null;
		String optionadditionalinfo =null;
		String canorder =null;
		String reqfororder =null;
		String productName = null;
		while (iterator.hasNext()) {
			
			try{
			Row nextRow = iterator.next();
			if (nextRow.getRowNum() == 0)
				continue;
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			
			RushTime rushTime =new RushTime();
			SameDayRush sameDayObj=new SameDayRush();
			Samples samples=new Samples();
			
			List<Color> color = new ArrayList<Color>();
			List<Origin> origin = new ArrayList<Origin>();
			List<String> lineNames = new ArrayList<String>();
			List<String> categories = new ArrayList<String>();
			List<String> productKeywords = new ArrayList<String>();
			List<String> complianceCerts = new ArrayList<String>();
			List<String> safetyWarnings = new ArrayList<String>();
			List<Personalization> personalizationlist = new ArrayList<Personalization>();
			List<String> packaging = new ArrayList<String>();
			List<String> themes = new ArrayList<String>();
			List<String> tradeName = new ArrayList<String>();
			List<ImprintMethod> imprintMethods = new ArrayList<ImprintMethod>();
			List<Artwork> artworkList = new ArrayList<Artwork>();
			List<Shape> shapeList=new ArrayList<Shape>();
			List<ProductionTime> productionTimeList = new ArrayList<ProductionTime>();
			List<Material> materialList=new ArrayList<Material>();
			
			ProductColorParser colorparser=new ProductColorParser();
			ProductOriginParser originParser=new ProductOriginParser();
			ProductRushTimeParser rushTimeParser=new ProductRushTimeParser();
			ProductSameDayParser sameDayParser=new ProductSameDayParser();
			ProductSampleParser sampleParser =new ProductSampleParser();
			PersonlizationParser personalizationParser=new PersonlizationParser();
			CatalogParser catlogparser=new CatalogParser();
			ShippingEstimationParser shipinestmt = new ShippingEstimationParser();
			ProductSizeParser sizeParser=new ProductSizeParser();
			ProductPackagingParser packagingParser=new ProductPackagingParser();
			ProductTradeNameParser tradeNameParser=new ProductTradeNameParser();
			ProductImprintMethodParser imprintMethodParser=new ProductImprintMethodParser();
			ProductArtworkProcessor artworkProcessor=new ProductArtworkProcessor();
			ProductShapeParser shapeParser=new ProductShapeParser();
			ProductionTimeParser productionTimeParser =new ProductionTimeParser();
			ProductThemeParser themeParser=new ProductThemeParser();
			ProductMaterialParser materialParser=new ProductMaterialParser();
			ProductImprintColorParser imprintColorParser =new ProductImprintColorParser();
			 List<Image> imgList = new ArrayList<Image>();
			 List<Catalog> catalogList = new ArrayList<Catalog>();
		        
		    //Image imgObj =new Image(); 
			
			Inventory inventoryObj = new Inventory();
	        Size sizeObj = null;
			ShippingEstimate ShipingItem = null;
			
			String shippingitemValue = null;
			String shippingdimensionValue = null;
			String sizeGroup=null;
			String rushService=null;
			String prodSample=null;
			
			 productXids.add(externalProductId);
			 boolean checkXid  = false;
			 
			
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String xid = null;
				int columnIndex = cell.getColumnIndex();
				if(columnIndex + 1 == 1){
					if(cell.getCellType() == Cell.CELL_TYPE_STRING){
						xid = cell.getStringCellValue();
					}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
						xid = String.valueOf((int)cell.getNumericCellValue());
					}else{
						
					}
					 //xid = cell.getStringCellValue();
					 if(productXids.contains(xid)){
						 productXids.add(xid);
					 }else{
						 productXids = new ArrayList<String>();
					 }
					 
					checkXid = true;
				}else{
					checkXid = false;
				}
				if(checkXid){
					 if(!productXids.contains(xid)){
						 if(nextRow.getRowNum() != 1){
							 System.out.println("Java object converted to JSON String, written to file");
							 ObjectMapper mapper = new ObjectMapper();
							   // Add repeatable sets here
							 	productExcelObj.setPriceGrids(priceGrids);
							 	//productConfigObj.setOptions(option);
							 	productExcelObj.setProductConfigurations(productConfigObj);
							 	productExcelObj.setProductRelationSkus(productsku);
							 	productExcelObj.setProductNumbers(pnumberList);
							 	//productList.add(productExcelObj);
							 	int num = postServiceImplV2.postProduct(accessToken, productExcelObj,asiNumber);
							 	if(num ==1){
							 		numOfProducts.add("1");
							 	}
								//System.out.println(mapper.writeValueAsString(productExcelObj));
							 	_LOGGER.info("list size>>>>>>>"+numOfProducts.size());
								
								// reset for repeateable set 
								priceGrids = new ArrayList<PriceGrid>();
								productConfigObj = new ProductConfigurations();
								productsku = new ArrayList<ProductSkus>();
								pnumberList = new ArrayList<ProductNumber>();
								option=new ArrayList<Option>();
								
						 }
						    if(!productXids.contains(xid)){
						    	productXids.add(xid);
						    }
							productExcelObj = new Product();
					 }
				}
				if(productXids.size() >1  && !LookupData.isRepeateIndex(String.valueOf(columnIndex+1))){
					continue;
				}

				switch (columnIndex + 1) {
				case 1:
					if(cell.getCellType() == Cell.CELL_TYPE_STRING){
						productId = cell.getStringCellValue();
					}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
						productId = String.valueOf((int)cell.getNumericCellValue());
					}else{
						
					}
					productExcelObj.setExternalProductId(productId);
					 /*externalProductId = cell.getStringCellValue();
					productExcelObj.setExternalProductId(externalProductId);*/
					break;
					
				case 2:
					/*String name = cell.getStringCellValue();
					
					if(!StringUtils.isEmpty(name)){
					productExcelObj.setName(cell.getStringCellValue());
					}else{
						productExcelObj.setName(ApplicationConstants.CONST_STRING_EMPTY);
					}*/
					 productName = cell.getStringCellValue();
						productExcelObj.setName(productName);
					break;
		
				case 3:
					// data is not avaiable
					/*int asiProdNo = 0;
					if(cell.getCellType() == Cell.CELL_TYPE_STRING){
						try{
							asiProdNo = Integer.parseInt(cell.getStringCellValue());
							productExcelObj.setAsiProdNo(Integer.toString(asiProdNo));
						}catch(NumberFormatException nfe){
							
						}
					  }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
						  asiProdNo = (int) cell.getNumericCellValue();
						  productExcelObj.setAsiProdNo(Integer.toString(asiProdNo));
					  }*/ 
					break;
			
				case 4:
					
				    break;
					
				case 5:
					
					break;
					
				case 6:
					
					break;
					
				case 7:
					
					break;
					
				case 8:
					
					String productDescription = cell.getStringCellValue();
					productDescription = productName;
					productExcelObj.setDescription(productDescription);
					break;
					
				case 9:
					
					break;
					
					
				case 10:

					 Image imgObj = null;
					String image = cell.getStringCellValue();
					if(!StringUtils.isEmpty(image)){
					String imgArr[] = image.split(",");
					for(int i =0, num=1;i<=imgArr.length-1;i++)
					{
						imgObj = new Image();
						if(i==0){
							imgObj.setImageURL(imgArr[0]);
							imgObj.setRank(num++);
							imgObj.setIsPrimary(true);
						}else {
							imgObj.setImageURL(imgArr[1]);
							 imgObj.setRank(num++);
							 imgObj.setIsPrimary(false);
						}
						imgList.add(imgObj);
					}
					}else{				
				}
					productExcelObj.setImages(imgList);		
					
					break;
					
				case 11:
					
					
					break;
					
					
				
				case 12:

					String categoryName = cell.getStringCellValue();
					List<String> listOfCategories = new ArrayList<String>();
					//listOfCategories.add(categoryName);
					listOfCategories.add("USB/FLASH DRIVES123");
					productExcelObj.setCategories(listOfCategories);
					break;

				case 13:
					String Keywords = cell.getStringCellValue();
					List<String> listOfKeywords = new ArrayList<String>();
					if(Keywords.contains(ApplicationConstants.CONST_DELIMITER_AMPERSAND)){
						listOfKeywords.addAll(Arrays.asList(Keywords.split(ApplicationConstants.CONST_DELIMITER_AMPERSAND)));
					}else if(Keywords.contains("USBs")){
						String[] keys = Keywords.split("USBs");
						for (String key : keys) {
							    if(key.contains("USB")){
							    	listOfKeywords.addAll(Arrays.asList(key.split("USB")));
							    }else{
							    	listOfKeywords.add(key);
							    }
						}
					}
					productExcelObj.setProductKeywords(listOfKeywords);
					break;
					
				case 14: 
					// change logic here
					String colorValue=cell.getStringCellValue();
					if(!StringUtils.isEmpty(colorValue)){
					color=colorparser.getColorCriteria(colorValue);
					if(color!=null){
					productConfigObj.setColors(color);
					}
					}
					break;
					
				case 15:
					
					String material=cell.getStringCellValue();
					List<Material> listOfMaterialList =  new ArrayList<Material>();
					String[] materials = material.split(ApplicationConstants.CONST_DELIMITER_COMMA);
					for (String matr : materials) {
						Material matl = new Material();
						matl.setName(matr);
						matl.setAlias(matr);
						listOfMaterialList.add(matl);
					}
					productConfigObj.setMaterials(listOfMaterialList);
					break;
					
				case 16:
					 sizeGroup = cell.getStringCellValue();	
				break;
				
				case 17:
					
					String sizeValue = cell.getStringCellValue();
					sizeObj=sizeParser.getSizes(sizeGroup,sizeValue);
					if(sizeObj.getApparel()!=null || sizeObj.getCapacity()!=null || sizeObj.getDimension()!=null||sizeObj.getOther()!=null||sizeObj.getVolume()!=null){
					productConfigObj.setSizes(sizeObj);
					}
					break;
				
				 case 18:
					String shapeValue=cell.getStringCellValue();
					if(!StringUtils.isEmpty(shapeValue)){
					shapeList=shapeParser.getShapeCriteria(shapeValue);
					if(shapeList!=null){
					productConfigObj.setShapes(shapeList);
					}
					}
					break;
					
				case 19:
					String themeValue=cell.getStringCellValue();
					if(!StringUtils.isEmpty(themeValue)){
					themes=themeParser.getThemeCriteria(themeValue);
					if(themes!=null){
					productConfigObj.setThemes(themes);
					}
					}
					break;
					
				case 20:
					
					String brandName = cell.getStringCellValue();
					List<String> listOfBrands = new ArrayList<String>();
					TradeName tradeName1 = new TradeName();
					tradeName1.setName(brandName);
					listOfBrands.add(brandName);
					productConfigObj.setTradeNames(listOfBrands);
					break;
					
				case 21:
				
					String origin1 = cell.getStringCellValue();
					List<String> listOfOrigins = new ArrayList<String>();
					Origin origins = new Origin();
					origins.setName(origin1);
					listOfOrigins.add(origin1);
					productConfigObj.setOrigins(listOfOrigins);
					break;
					
				case 22:
					 optiontype=cell.getStringCellValue();
				   break;
					
				case 23:
					 optionname=cell.getStringCellValue();

					
				   break;
				   
				case 24:
					 optionvalues=cell.getStringCellValue();					
					break;
					
				case 25:
					 canorder=cell.getStringCellValue();	
					
					
					break;
					
				case 26:
					 reqfororder=cell.getStringCellValue();	
					
					break;
					
				case 27:
					optionadditionalinfo=cell.getStringCellValue();	
					
					break;
					
				case 28:
					String imprintValue=cell.getStringCellValue();
					if(!StringUtils.isEmpty(imprintValue)){
					imprintMethods=imprintMethodParser.getImprintCriteria(imprintValue);
					if(imprintMethods!=null){
					productConfigObj.setImprintMethods(imprintMethods);
					}
					}
					break;
					
				case 29:
					String lineName = cell.getStringCellValue();
					if(!StringUtils.isEmpty(lineName)){
					String lineNameArr[] = lineName.split(ApplicationConstants.CONST_STRING_COMMA_SEP);
					for (String string : lineNameArr) {
						lineNames.add(string);
					}
					productExcelObj.setLineNames(lineNames);
					}
					break;
					
				case 30:
					String artwork = cell.getStringCellValue();
					if(!StringUtils.isEmpty(artwork)){
					artworkList=artworkProcessor.getArtworkCriteria(artwork);
					if(artworkList!=null){
					productConfigObj.setArtwork(artworkList);
					}}
					break;
					
				case 31:
					String imprintColor = cell.getStringCellValue();
					if(!StringUtils.isEmpty(imprintColor)){
						imprintColorsObj=imprintColorParser.getImprintColorCriteria(imprintColor);
					if(imprintColorsObj!=null){
					productConfigObj.setImprintColors(imprintColorsObj); 
					
					}}
					 
					break;
					
				case 33:
					String persnlization = cell.getStringCellValue();
					if(!StringUtils.isEmpty(persnlization)){
					personalizationlist = personalizationParser.getPersonalization(persnlization);
					productConfigObj.setPersonalization(personalizationlist);
					}
					break;
					
					
				case 38:
					prodSample = cell.getStringCellValue();
					break;
					
				case 39:
					String specSample  = cell.getStringCellValue();
					boolean flag=false;
					if(!StringUtils.isEmpty(specSample)){
						flag=true;
					}
					samples=sampleParser.getSampleCriteria(prodSample, specSample,flag);
					if(samples!=null){
					productConfigObj.setSamples(samples);
					}
					break;
					
				case 40:
					String productionTime = cell.getStringCellValue();
					if(!StringUtils.isEmpty(productionTime)){
					productionTimeList=productionTimeParser.getProdTimeCriteria(productionTime);
					if(productionTimeList!=null){
					productConfigObj.setProductionTime(productionTimeList);
					}
				}
					break;	
					
					
				case 41:
					 rushService = cell.getStringCellValue();
					break;
					
				case 42:
					if(!StringUtils.isEmpty(rushService) && !rushService.equalsIgnoreCase(ApplicationConstants.CONST_CHAR_N)){
					String rushTimeValue = cell.getStringCellValue();
					rushTime=rushTimeParser.getRushTimeCriteria(rushTimeValue);
					if(rushTime!=null){
					productConfigObj.setRushTime(rushTime);
					}}
					break;
				
				case 43:
					/*String sameDayService=cell.getStringCellValue();
					if(!StringUtils.isEmpty(sameDayService)){
						sameDayObj=sameDayParser.getSameDayRush(sameDayService);
						if(sameDayObj!=null){
						productConfigObj.setSameDayRush(sameDayObj);
						}}*/
					break;
					
				case 44:
					/*String packagingValue=cell.getStringCellValue();
					if(!StringUtils.isEmpty(packagingValue)){
						  packaging=packagingParser.getPackagingCriteria(packagingValue);
						  if(packaging!=null){
							 productConfigObj.setPackaging(packaging);
					     }
					}*/
					break;
				case 45:
					/*shippingitemValue = cell.getStringCellValue();*/
					break;
					
				case 46:
					/*shippingdimensionValue = cell.getStringCellValue();*/
                	break;
					
				case 47:
					/*String shippingWeightValue = cell.getStringCellValue();
					ShipingItem = shipinestmt.getShippingEstimates(shippingitemValue, shippingdimensionValue,shippingWeightValue);
					if(ShipingItem.getDimensions()!=null || ShipingItem.getNumberOfItems()!=null || ShipingItem.getWeight()!=null ){
					productConfigObj.setShippingEstimates(ShipingItem);
					}*/
					break;
					
				case 48:
					/*String shipperBillsBy = cell.getStringCellValue();
					if(!StringUtils.isEmpty(shipperBillsBy.trim())){
					productExcelObj.setShipperBillsBy(cell.getStringCellValue());
					}else{
						productExcelObj.setShipperBillsBy(ApplicationConstants.CONST_STRING_EMPTY);
					}*/
					break;
					
				case 49:
					/*String additionalShippingInfo = cell.getStringCellValue();
					if(!StringUtils.isEmpty(additionalShippingInfo)){
					productExcelObj.setAdditionalShippingInfo(additionalShippingInfo);
					}else{
						productExcelObj.setAdditionalShippingInfo(ApplicationConstants.CONST_STRING_EMPTY);	
					}*/
					break;
					
				case 50:
					/*String canShipInPlainBox = cell.getStringCellValue();
					if(!StringUtils.isEmpty(canShipInPlainBox)){
					if (canShipInPlainBox.trim().equalsIgnoreCase(ApplicationConstants.CONST_CHAR_Y)) {
						productExcelObj.setCanShipInPlainBox(true);
					} else {
						productExcelObj.setCanShipInPlainBox(false);
					}
					}else{ productExcelObj.setCanShipInPlainBox(false);
					}*/
					break;
					
				case 51:
					/*String complianceCert = cell.getStringCellValue();
					if(!StringUtils.isEmpty(complianceCert)){
					String complianceCertArr[] = complianceCert.split(ApplicationConstants.CONST_STRING_COMMA_SEP);
					for (String string : complianceCertArr) {
						complianceCerts.add(string);
					} 
					productExcelObj.setComplianceCerts(complianceCerts);
					}*/
					break;
					
				case 52:
					/*String productDataSheet = cell.getStringCellValue();
					if(!StringUtils.isEmpty(productDataSheet)){
					productExcelObj.setProductDataSheet(productDataSheet);
					}else{
						productExcelObj.setProductDataSheet(ApplicationConstants.CONST_STRING_EMPTY);
					}*/
					break;
					
				case 53:
					/*String safetyWarning = cell.getStringCellValue();
					if(!StringUtils.isEmpty(safetyWarning)){
					String safetyWarningsArr[] = safetyWarning.split(ApplicationConstants.CONST_STRING_COMMA_SEP);
					for (String string : safetyWarningsArr) {
						safetyWarnings.add(string.trim());
					} 
					productExcelObj.setSafetyWarnings(safetyWarnings);
					}*/
					break;

				case 54:
					/*String additionalProductInfo = cell.getStringCellValue();
					if(!StringUtils.isEmpty(additionalProductInfo)){
					productExcelObj.setAdditionalProductInfo(additionalProductInfo);
					}else{
						productExcelObj.setAdditionalProductInfo(ApplicationConstants.CONST_STRING_EMPTY);
					}*/
					break;

				case 55:
					/*String distributorOnlyComments = cell.getStringCellValue();
					if(!StringUtils.isEmpty(distributorOnlyComments)){
					productExcelObj.setDistributorOnlyComments(distributorOnlyComments);
					}
					else{
						productExcelObj.setDistributorOnlyComments(ApplicationConstants.CONST_STRING_EMPTY);
					}*/
					break;

				case 56:
					/*String productDisclaimer = cell.getStringCellValue();
					if(!StringUtils.isEmpty(productDisclaimer)){
					productExcelObj.setProductDisclaimer(productDisclaimer);
					}
					else{
						productExcelObj.setProductDisclaimer(ApplicationConstants.CONST_STRING_EMPTY);
					} */
					break;
					
				case 57:
					/*basePriceName = cell.getStringCellValue();*/
					basePriceName = productName;
					break;
					
				case 58:
					/*String criteria1 = cell.getStringCellValue();
					if(!StringUtils.isEmpty(criteria1)){
						basePriceCriteria.append(criteria1).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
					}*/
					break;
			
				case 59:
					/*String criteria2 = cell.getStringCellValue();
					if(!StringUtils.isEmpty(criteria2)){
						basePriceCriteria.append(criteria2);
					}*/
					break;
				case 60:
				case 61:
				case 62:
				case 63:
				case 64:
				case 65:
				case 66:
				case 67:
				case 68:
				case 69:
					if(cell.getCellType() == Cell.CELL_TYPE_STRING){
						quantity = cell.getStringCellValue();
				         if(!StringUtils.isEmpty(quantity)){
				        	 listOfQuantity.append(quantity).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
				         }
					}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
						int quantity1 = (int)cell.getNumericCellValue();
				         if(!StringUtils.isEmpty(quantity1)){
				        	 listOfQuantity.append(quantity1).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
				         }
					}else{
					}
			          break;
				case 70:
				case 71:
				case 72:
				case 73:
				case 74:
				case 75:
				case 76:
				case 77:
				case 78:
				case 79:       
					if(cell.getCellType() == Cell.CELL_TYPE_STRING){
					quantity = cell.getStringCellValue();
			         if(!StringUtils.isEmpty(quantity)){
			        	 listOfPrices.append(quantity).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
			         }
				}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
					double quantity1 = (double)cell.getNumericCellValue();
			         if(!StringUtils.isEmpty(quantity1)){
			        	 listOfPrices.append(quantity1).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
			         }
				}else{
				}
				  break;
				case 80:
				case 81:
				case 82:
				case 83:
				case 84:
				case 85:
				case 86:
				case 87:
				case 88:
				case 89:	
					quantity = cell.getStringCellValue();
			         if(!StringUtils.isEmpty(quantity)){
			        	 listOfDiscount.append(quantity).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
			         }
					  break;
				case 91:
					   priceIncludes = cell.getStringCellValue();
					   break;
				case 92:
					 priceQurFlag = cell.getStringCellValue();
					 break;
				case 93:
					 currencyType = cell.getStringCellValue();
					 break;
				case 94:
					if(cell.getCellType() ==  Cell.CELL_TYPE_BOOLEAN){
						if(!StringUtils.isEmpty(String.valueOf(cell.getBooleanCellValue()))){
							productExcelObj.setCanOrderLessThanMinimum(cell.getBooleanCellValue());
							}else
							{
								productExcelObj.setCanOrderLessThanMinimum(false);
							} 
					}else{
						productExcelObj.setCanOrderLessThanMinimum(false);
					}
					break;

				case 95:
					 priceType = cell.getStringCellValue();
					if(!StringUtils.isEmpty(priceType)){
						if(priceType.equalsIgnoreCase("List")){
					productExcelObj.setPriceType(ApplicationConstants.CONST_PRICE_TYPE_CODE_LIST);
						}else if(priceType.equalsIgnoreCase("Net")){
							productExcelObj.setPriceType(ApplicationConstants.CONST_PRICE_TYPE_CODE_NET);
						}
					}else{
						productExcelObj.setPriceType(ApplicationConstants.CONST_STRING_EMPTY);
					}
					break;

				case 96:
					boolean breakOutByPrice = cell.getBooleanCellValue();
					if(!StringUtils.isEmpty(String.valueOf(breakOutByPrice))){
					productExcelObj.setBreakOutByPrice(breakOutByPrice);
					}else{
						productExcelObj.setBreakOutByPrice(false);	
					}
					break;
				
				case 97:
					upChargeName = cell.getStringCellValue();
					break;//upcharge name
				
				case 98:
					String upCriteria1= cell.getStringCellValue();
					if(!StringUtils.isEmpty(upCriteria1)){
						UpCharCriteria.append(upCriteria1).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
					}
					break;//upcharge criteria_1
				
				case 99:
					String upCriteria2= cell.getStringCellValue();
					if(!StringUtils.isEmpty(upCriteria2)){
						UpCharCriteria.append(upCriteria2);
					}
					break;//upcharge criteria_2
				
				case 100:
					upchargeType = cell.getStringCellValue();
					break;//upcharge type
				
				case 101:
					upChargeLevel = cell.getStringCellValue();
					break;//upcharge level
					
				case 102:
				case 103:
				case 104:
				case 105:
				case 106:
				case 107:
				case 108:
				case 109:
				case 110:
				case 111:
					if(cell.getCellType() == Cell.CELL_TYPE_STRING){
						quantity = cell.getStringCellValue();
				         if(!StringUtils.isEmpty(quantity)){
				        	 UpCharQuantity.append(quantity).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
				         }
					}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
						int quantity1 = (int)cell.getNumericCellValue();
				         if(!StringUtils.isEmpty(quantity1)){
				        	 UpCharQuantity.append(quantity1).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
				         }
					}else{
						
					}
					 break; // upcharge quanytity
					
				case 112:
				case 113:
				case 114:
				case 115:
				case 116:
				case 117:
				case 118:
				case 119:
				case 120:
				case 121:
					if(cell.getCellType() == Cell.CELL_TYPE_STRING){
						quantity = cell.getStringCellValue();
				         if(!StringUtils.isEmpty(quantity)){
				        	 UpCharPrices.append(quantity).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
				         }
					}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
						int quantity1 = (int)cell.getNumericCellValue();
				         if(!StringUtils.isEmpty(quantity1)){
				        	 UpCharPrices.append(quantity1).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
				         }
					}else{
						
					}
					 break; // upcharge prices
				case 122:
				case 123:
				case 124:
				case 125:
				case 126:
				case 127:
				case 128:
				case 129:
				case 130:
				case 131:
					if(cell.getCellType() == Cell.CELL_TYPE_STRING){
						quantity = cell.getStringCellValue();
				         if(!StringUtils.isEmpty(quantity)){
				        	 UpCharDiscount.append(quantity).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
				         }
					}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
						int quantity1 = (int)cell.getNumericCellValue();
				         if(!StringUtils.isEmpty(quantity1)){
				        	 UpCharDiscount.append(quantity1).append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
				         }
					}else{
						
					}
					 break; // upcharge discount
				case 132:
					upChargeDetails = cell.getStringCellValue();
					break;// upcharge details
				case 133:
					    upChargeQur = cell.getStringCellValue();
					break;// QUR Flag
				case 134:
					// data is not available
					/*String priceConfirmedThru = cell.getStringCellValue();
					//mmddyy in excel  //yymmdd in api
					String strArr[]=priceConfirmedThru.split("/");
					priceConfirmedThru=strArr[2]+"/"+strArr[0]+"/"+strArr[1];
					priceConfirmedThru=priceConfirmedThru.replaceAll("/", "-");
					 
					priceConfirmedThru=priceConfirmedThru+"T00:00:00";
					 	productExcelObj.setPriceConfirmedThru(priceConfirmedThru);*/
					break;
					
				case 136:
					  productNumberCriteria1 = cell.getStringCellValue();
				
					break;
				case 137:
					  productNumberCriteria2 = cell.getStringCellValue();
					
					break;
				case 138:
					productNumber = cell.getStringCellValue();
					
					 
					
					break;
				case 140:
				   SKUCriteria1 = cell.getStringCellValue();
					break;
					
				case 141:
				   SKUCriteria2 = cell.getStringCellValue();
					break;
					
				case 144:
					skuvalue = cell.getStringCellValue();
					break;
					
				case 145:
					Inlink = cell.getStringCellValue();
					break;
					
					
				case 146:
				    Instatus = cell.getStringCellValue();
					break;
					
				
				case 147:
					//int InQuantity= cell.getStringCellValue();
					int InQuantity1 = 0;
					if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
						InQuantity1 = (int) cell.getNumericCellValue();
					}else if(cell.getCellType() == Cell.CELL_TYPE_STRING){
						try{
							InQuantity1 = Integer.parseInt(cell.getStringCellValue());
						}catch(NumberFormatException nfe){
							_LOGGER.error("Criteria Level Inventory Quantity::Invalid value");
						}
						
					}
					skuObj=skuparserobj.getProductRelationSkus(SKUCriteria1, SKUCriteria2, skuvalue, Inlink, Instatus,Integer.toString(InQuantity1));
					
					break;	
				case 148:
					String distributorViewOnly = cell.getStringCellValue();
					if(!StringUtils.isEmpty(distributorViewOnly) && distributorViewOnly.trim().equalsIgnoreCase(ApplicationConstants.CONST_CHAR_Y)) {
						productExcelObj.setSeoFlag(true);
						
					} else {
						productExcelObj.setSeoFlag(false);
					}
					break;
					
				case 149:
					String seoFlag = cell.getStringCellValue();
					if(!StringUtils.isEmpty(seoFlag) && seoFlag.trim().equalsIgnoreCase(ApplicationConstants.CONST_CHAR_Y)) {
						productExcelObj.setSeoFlag(true);
					} else {
						productExcelObj.setSeoFlag(false);
					}
					break;
				}
				
				//productExcelObj.setProductConfigurations(productConfigObj);l
			}  // end inner while loop
			if(( (listOfPrices != null && !listOfPrices.toString().isEmpty()) || (priceQurFlag != null && priceQurFlag.equalsIgnoreCase("Y")))){
				priceGrids = priceGridParser.getPriceGrids(listOfPrices.toString(), listOfQuantity.toString(), listOfDiscount.toString(), currencyType,
						priceIncludes, true, priceQurFlag, basePriceName,basePriceCriteria.toString(),priceGrids);	
			}
			//if()
			 
				if(UpCharCriteria != null && !UpCharCriteria.toString().isEmpty()){
					priceGrids = priceGridParser.getUpchargePriceGrid(UpCharQuantity.toString(), UpCharPrices.toString(), UpCharDiscount.toString(), UpCharCriteria.toString(), 
							 upChargeQur, currencyType, upChargeName, upchargeType, upChargeLevel, new Integer(1), priceGrids);
				}
				
				if(!StringUtils.isEmpty(skuvalue)){
					skuObj=skuparserobj.getProductRelationSkus(SKUCriteria1, SKUCriteria2, skuvalue, Inlink, Instatus,InQuantity);
					productsku.add(skuObj);
				}
				
				if(!StringUtils.isEmpty(productNumber)){
					pnumObj=pnumberParser.getProductNumer(productNumberCriteria1, productNumberCriteria2, productNumber);
					if(pnumObj!=null){
					pnumberList.add(pnumObj);
					}
				}
				
				if(!StringUtils.isEmpty(optionname) && !StringUtils.isEmpty(optiontype) && !StringUtils.isEmpty(optionvalues) ){
					optionobj=optionparserobj.getOptions(optiontype, optionname, optionvalues, canorder, reqfororder, optionadditionalinfo);
					option.add(optionobj);		
					productConfigObj.setOptions(option);	
				}
				
				upChargeQur = null;
				UpCharCriteria = new StringBuilder();
				priceQurFlag = null;
				listOfPrices = new StringBuilder();
				UpCharPrices = new StringBuilder();
				UpCharDiscount = new StringBuilder();
				UpCharQuantity = new StringBuilder();
				skuvalue = null;
			    Inlink = null;
			    Instatus = null;
			    InQuantity = null;
			    SKUCriteria1 = null;
			    SKUCriteria2 = null;
			    productNumberCriteria1=null; 
			    productNumberCriteria2=null;
			    productNumber=null;
			    optiontype=null;
			    optionname=null;
			    optionvalues=null;
			    canorder=null;
			    reqfororder=null;
			    optionadditionalinfo=null;
			
			}catch(Exception e){
			//e.printStackTrace();
			_LOGGER.error("Error while Processing Product :"+productExcelObj.getExternalProductId() );		 
		}
		}
		workbook.close();
		//inputStream.close();
		ObjectMapper mapper = new ObjectMapper();
		//System.out.println("Final product JSON, written to file");
		 ObjectMapper mapper1 = new ObjectMapper();
		   // Add repeatable sets here
		 	productExcelObj.setPriceGrids(priceGrids);
		 	productExcelObj.setProductConfigurations(productConfigObj);
		 	productExcelObj.setProductRelationSkus(productsku);
		 	productExcelObj.setProductNumbers(pnumberList);
		 	//productList.add(productExcelObj);
		 	int num = postServiceImplV2.postProduct(accessToken, productExcelObj,asiNumber);
		 	if(num ==1){
		 		numOfProducts.add("1");
		 	}
		 	_LOGGER.info("list size>>>>>>"+numOfProducts.size());
			//System.out.println(mapper1.writeValueAsString(productExcelObj));

		}catch(Exception e){
			_LOGGER.error("Error while Processing excel sheet ");
			return 0;
		}finally{
			productDaoObj.getErrorLog(asiNumber,batchId);
			try {
				workbook.close();
			//inputStream.close();
			} catch (IOException e) {
				_LOGGER.error("Error while Processing excel sheet");
	
			}
				_LOGGER.info("Complted processing of excel sheet ");
				_LOGGER.info("Total no of product:"+numOfProducts.size() );
				return numOfProducts.size();
		}
	
	}
	
	public PostServiceImpl getPostServiceImplV2() {
		return postServiceImplV2;
	}
	public void setPostServiceImplV2(PostServiceImpl postServiceImplV2) {
		this.postServiceImplV2 = postServiceImplV2;
	}
	public ProductDao getProductDaoObj() {
		return productDaoObj;
	}
	public void setProductDaoObj(ProductDao productDaoObj) {
		this.productDaoObj = productDaoObj;
	}
	
	

}

package parser.beaconPromotions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import parser.tomaxusa.TomaxConstants;

import com.a4tech.lookup.service.LookupServiceData;
import com.a4tech.lookup.service.restService.LookupRestService;
import com.a4tech.product.model.BlendMaterial;
import com.a4tech.product.model.Catalog;
import com.a4tech.product.model.Color;
import com.a4tech.product.model.Combo;
import com.a4tech.product.model.Dimension;
import com.a4tech.product.model.Dimensions;
import com.a4tech.product.model.ImprintLocation;
import com.a4tech.product.model.ImprintMethod;
import com.a4tech.product.model.ImprintSize;
import com.a4tech.product.model.Material;
import com.a4tech.product.model.NumberOfItems;
import com.a4tech.product.model.Option;
import com.a4tech.product.model.OptionValue;
import com.a4tech.product.model.Packaging;
import com.a4tech.product.model.PriceGrid;
import com.a4tech.product.model.Product;
import com.a4tech.product.model.ProductConfigurations;
import com.a4tech.product.model.ShippingEstimate;
import com.a4tech.product.model.Size;
import com.a4tech.product.model.Value;
import com.a4tech.product.model.Values;
import com.a4tech.product.model.Weight;
import com.a4tech.util.ApplicationConstants;
import com.a4tech.util.CommonUtility;

public class BeaconProAttributeParser {
	private static final Logger _LOGGER = Logger.getLogger(BeaconProAttributeParser.class);
	private LookupServiceData lookupServiceDataObj;
	private LookupRestService  lookupRestServiceObj;
	BeaconProPriceGridParser  beaconProPriceGridParser;
	public static List<com.a4tech.lookup.model.Catalog> catalogs            = null;
	public static ArrayList<String> tempCatList=new ArrayList<String>();
		public List<Material> getMaterialValue(String material) {
			  List<Material> materiallist = new ArrayList<Material>();
			  Material materialObj = new Material();
		 // material=material.replace(";","/");
		  //material=material.replace(",","/");
			// add condition here to check if its present in custom map
		  String tempAliasForblend=material;
		  String tempValue= UpchargeConstants.MATERIAL_MAP.get(material);
		  String tempMat=material;
		  if(tempValue!=null){
			  if(tempValue.toUpperCase().contains("COMBO")){
					String ComboValue[]={};
					String MaterialForCombo=tempValue;
					MaterialForCombo=MaterialForCombo.replace("%%%%", " ");
			 		
					//materialValue1=materialValue1.replaceAll("%","");
					
			 	   //materialValue1=materialValue1.replaceAll("88 Polyvinyl Chloride/12 PlyWATERPROOF","Polyester:Vinyl");
				   // materialValue1=materialValue1.replaceAll( "80 PVC/20 PolyesterWATERPROOF","PVC:Polyester");
				   //materialValue1=materialValue1.replaceAll("62 Cotton/38 PVCWATERPROOF","Cotton:PVC");
					
					
					
					Combo comboObj = new Combo();
					ComboValue=tempValue.split(":");
					
		    		//for (String materialValue : ComboValue) {
		    			 materialObj = new Material();
						 //materialObj = getMaterialValue(listOfLookupMaterial.toString(), materialValue1+" "+finalTempAliasVal);
		    			 materialObj.setName(ComboValue[0]);
		    			 materialObj.setAlias(MaterialForCombo.replace(":Combo:","-"));
		    			 comboObj.setName(ComboValue[2]);
		    			 materialObj.setCombo(comboObj);
		    		// }
		    			 materiallist.add(materialObj);
					//return listOfMaterial;
					
				
			  }else if(tempValue.toUpperCase().contains("BLEND")){
				  tempValue=tempValue.replace("::", ":");
				   //if (listOfLookupMaterial.size()==2 ||  listOfLookupMaterial.size()==3) {
					   
					   String tempArr[]=tempValue.split(":");
					   String materialOne="";
					   String materialTwo="";
					   String percOne="";
					   String percTwo="";
					   
					   if(tempArr.length>3 || tempArr.length==5){
						   materialOne=tempArr[1];
						   materialTwo=tempArr[3];
						   percOne=tempArr[2];
						   percTwo=tempArr[4];
					   }else if(tempArr.length<=3 || tempArr.length==3){
						    materialOne=tempArr[1];
						    materialTwo=tempArr[2];
						    percOne="50";
						    percTwo="50";
					   }
					   List<String> listOfLookupMaterialBlend1 = getMaterialType(materialOne
							    .toUpperCase());
					   if (listOfLookupMaterialBlend1.isEmpty()) { 
						   listOfLookupMaterialBlend1.add("Other Fabric");
					   }
					   List<String> listOfLookupMaterialBlend2 = getMaterialType(materialTwo
							    .toUpperCase());
					   if (listOfLookupMaterialBlend2.isEmpty()) { 
						   listOfLookupMaterialBlend2.add("Other Fabric");
					   }
					 /*  String PercentageValue[]=new String [2];
					   if(material.contains("%")){
							   PercentageValue=material.split("%");
							  PercentageValue[0]=PercentageValue[0].replace("[^0-9|.x%/ ]", "");
						  }else{
							  PercentageValue[0]="50";
						  }*/
				    BlendMaterial blendObj=new BlendMaterial();
				    BlendMaterial blendObj1=new BlendMaterial();
				               // int PercentageValue1=100-Integer.parseInt(PercentageValue[0]);
				               // String PercentageValue2=Integer.toString(PercentageValue1);
				       materialObj.setName("Blend");
				       List<BlendMaterial> listOfBlend= new ArrayList<>();
				       blendObj.setPercentage(percOne);
				       blendObj.setName(listOfLookupMaterialBlend1.get(0));
				       blendObj1.setPercentage(percTwo);
				       blendObj1.setName(listOfLookupMaterialBlend2.get(0));
				       listOfBlend.add(blendObj);
				       listOfBlend.add(blendObj1);
				       materialObj.setAlias(tempAliasForblend);
				    materialObj.setBlendMaterials(listOfBlend);
				    materiallist.add(materialObj); 
				  // }
				  
			  }else{
				  String tempArr[]=tempValue.split(",");
				  for (String string : tempArr) {
					
				
				  List<String> listOfLookupMaterial = getMaterialType(tempValue
						    .toUpperCase());
						   //tempAliasForblend=material;
						   if (!listOfLookupMaterial.isEmpty()) { 
				  materialObj = getMaterialValue(listOfLookupMaterial.toString(),
						  string);
				  
					    materiallist.add(materialObj); 
						   }else{
							   materialObj.setName("Other");
					    		// materialObj.setAlias(values[1]);//
					    		 materialObj.setAlias(string);
					    		 
							    materiallist.add(materialObj); 
				  }
				  }
			  }
			  
			  
		  }
		  ////
		  else{
		  List<String> listOfLookupMaterial = getMaterialType(material
		    .toUpperCase());
		   tempAliasForblend=material;
		  /*if(listOfLookupMaterial.get(0).equalsIgnoreCase("OTHER"))
		  {
		   listOfLookupMaterial.remove(0);
		   Collections.swap(listOfLookupMaterial, 0, 1);
		   
		  }*/
		 
		  if (!listOfLookupMaterial.isEmpty()) { 
			  if(isBlendMaterial(material)){ 
		   //if (listOfLookupMaterial.size()==2 ||  listOfLookupMaterial.size()==3) {
			   
			   String tempArr[]=material.split("/");
			   List<String> listOfLookupMaterialBlend1 = getMaterialType(tempArr[0]
					    .toUpperCase());
			   if (listOfLookupMaterialBlend1.isEmpty()) { 
				   listOfLookupMaterialBlend1.add("Other Fabric");
			   }
			   List<String> listOfLookupMaterialBlend2 = getMaterialType(tempArr[1]
					    .toUpperCase());
			   if (listOfLookupMaterialBlend2.isEmpty()) { 
				   listOfLookupMaterialBlend2.add("Other Fabric");
			   }
			   String PercentageValue[]=new String [2];
			   if(material.contains("%")){
					   PercentageValue=material.split("%");
					  PercentageValue[0]=PercentageValue[0].replace("[^0-9|.x%/ ]", "");
				  }else{
					  PercentageValue[0]="50";
				  }
			   
					  
					  
		    BlendMaterial blendObj=new BlendMaterial();
		    BlendMaterial blendObj1=new BlendMaterial();
		                int PercentageValue1=100-Integer.parseInt(PercentageValue[0]);
		                String PercentageValue2=Integer.toString(PercentageValue1);
		    materialObj.setName("Blend");
		       List<BlendMaterial> listOfBlend= new ArrayList<>();
		       blendObj.setPercentage(PercentageValue[0]);
		       blendObj.setName(listOfLookupMaterialBlend1.get(0));
		       blendObj1.setPercentage(PercentageValue2);
		       blendObj1.setName(listOfLookupMaterialBlend2.get(0));
		       listOfBlend.add(blendObj);
		       listOfBlend.add(blendObj1);
		       materialObj.setAlias(tempAliasForblend);
		    materialObj.setBlendMaterials(listOfBlend);
		    materiallist.add(materialObj); 
		  // }
		  }else {  
		    materialObj = getMaterialValue(listOfLookupMaterial.toString(),
		      material);
		    materiallist.add(materialObj); 
		   }
		   
		       
		  }   else {  
			  materialObj.setName("Other");
	    		// materialObj.setAlias(values[1]);//
	    		 materialObj.setAlias(material);
			    materiallist.add(materialObj); 
			   }
		  }
		  return materiallist;
		 }
	
		
	public List<String> getMaterialType(String value){
		List<String> listOfLookupMaterials = lookupServiceDataObj.getMaterialValues();
		List<String> finalMaterialValues = listOfLookupMaterials.stream()
				                                  .filter(mtrlName -> value.contains(mtrlName))
				                                  .collect(Collectors.toList());
                                               
				
		return finalMaterialValues;	
	}
		
	public Material getMaterialValue(String name,String alias){
		Material materialObj = new Material();
		name = CommonUtility.removeCurlyBraces(name);
		materialObj.setName(name);
		materialObj.setAlias(alias);
		return materialObj;
	}
	/*public Material getMaterialValue(String name,String alias ,String materialType){
		Material materialObj = new Material();
		 Combo comboObj = null;
		 String[] materials = null;
		 name = CommonUtility.removeCurlyBraces(name);
		if(name.contains(",")){
			materials = name.split(","); 
			materialObj.setName(materials[0]);
			materialObj.setAlias(alias);
			comboObj = new Combo();
      	comboObj.setName(materials[1]);
      	materialObj.setCombo(comboObj);
		}
		return materialObj;
	}*/
/*	public boolean iscombo(String data){ // 70% Modacrylic /25% Cotton /5% 
		  if(data.split("_").length ==3){
			  return true;
		  }
		  else if(data.split("/").length ==3){
			  return true;
		  }
		return false;
	}*/
	public boolean isBlendMaterial(String data){
		//if(data.contains("%"))
		if(data.contains("/"))
		{
			return true;
		}else if(data.contains("_")){
			return true;
		}
		
		/*if(data.split("_").length ==2 ){ //51% Polyester/49% Cocona® 37.5
			return true;
		}else if(data.split("/").length ==2){  //55% Cotton_45% Polyester
			return true;}*/
		return false;
	}
		
	public static boolean  isComboMtrl(String material){
		boolean flag=false;
		if(material.equalsIgnoreCase("88% Polyvinyl Chloride/12% Ply%%%%WATERPROOF")){
			flag=true;
		}else if(material.equalsIgnoreCase("80% PVC/20% Polyester%%%%WATERPROOF")){
			flag=true;
		}else if(material.equalsIgnoreCase("62% Cotton/38% PVC%%%%WATERPROOF")){
			flag=true;
		}
		/*String MaterialForCombo=materialValue1.replaceAll("%","");
		materialValue1=materialValue1.replaceAll("%","");
		materialValue1=materialValue1.replaceAll("88 Polyvinyl Chloride/12 PlyWATERPROOF","Polyester:Vinyl");
	    materialValue1=materialValue1.replaceAll( "80 PVC/20 PolyesterWATERPROOF","PVC:Polyester");
		materialValue1=materialValue1.replaceAll("62 Cotton/38 PVCWATERPROOF","Cotton:PVC");*/
		return flag;
	}
	
	
	public List<ImprintMethod> getImprintMethods(String imprintValue,List<ImprintMethod> listOfImprintMethods){
		
		if(CollectionUtils.isEmpty(listOfImprintMethods)){
			listOfImprintMethods = new ArrayList<ImprintMethod>();
		 }
		//for (String value : listOfImprintMethods) {
			ImprintMethod imprintMethodObj =new ImprintMethod();
			if(lookupServiceDataObj.isImprintMethod(imprintValue.toUpperCase())){
				imprintMethodObj.setAlias(imprintValue);
				imprintMethodObj.setType(imprintValue);
			}else{
				imprintMethodObj.setAlias(imprintValue);
				imprintMethodObj.setType("OTHER");
			}
			listOfImprintMethods.add(imprintMethodObj);
		//}
		
		
		return listOfImprintMethods;
		
	}
	
public List<ImprintSize> getImprintSize(String imprintMethValue,List<ImprintSize> listOfImprintSize){
	if(CollectionUtils.isEmpty(listOfImprintSize)){
		listOfImprintSize = new ArrayList<ImprintSize>();
	 }
		ImprintSize imprSizeObj = new ImprintSize();
		imprSizeObj.setValue(imprintMethValue);
		listOfImprintSize.add(imprSizeObj);
		return listOfImprintSize;
	}

	public List<ImprintLocation>  getImprintLocationVal(String imprintLocValue,List<ImprintLocation> listOfImprintLocValues){
		if(CollectionUtils.isEmpty(listOfImprintLocValues)){
			listOfImprintLocValues = new ArrayList<ImprintLocation>();
		 }
			ImprintLocation imprintLocation = new ImprintLocation();
			imprintLocation.setValue(imprintLocValue);
			listOfImprintLocValues.add(imprintLocation);
			
		return listOfImprintLocValues;
	}
	public static List<Packaging> getPackagingCriteria(String packaging){
		List<Packaging> packagingList =new ArrayList<Packaging>();
		Packaging packObj;
		try{
		String packagingArr[] = packaging.split(ApplicationConstants.CONST_STRING_COMMA_SEP);
		for (String tempPackaging : packagingArr) {
			packObj=new Packaging();
			packObj.setName(tempPackaging);
 			packagingList.add(packObj);
		}
		}catch(Exception e){
			_LOGGER.error("Error while processing Product Packaging :"+e.getMessage());             
		   	return null;
		   }
		 
		return packagingList;
		
	}
	
	public ShippingEstimate getShippingEstimates( String shippingValue,ShippingEstimate shippingEstObj,String str,int dimNo,String shipDimValue,Dimensions dimObj) {
		//ShippingEstimate shipingObj = new ShippingEstimate();
		try{
		if(str.equals("NOI")){
		List<NumberOfItems> listOfNumberOfItems = new ArrayList<NumberOfItems>();
		NumberOfItems itemObj = new NumberOfItems();
		itemObj.setUnit(ApplicationConstants.CONST_STRING_SHIPPING_NUMBER_UNIT_CASE);
		itemObj.setValue(shippingValue);
		listOfNumberOfItems.add(itemObj);
		shippingEstObj.setNumberOfItems(listOfNumberOfItems);
		}
		
		if (str.equals("WT")) {
			List<Weight> listOfWeight = new ArrayList<Weight>();
			Weight weightObj = new Weight();
			weightObj.setUnit(ApplicationConstants.CONST_STRING_SHIPPING_WEIGHT);
			weightObj.setValue(shippingValue);
			listOfWeight.add(weightObj);
			shippingEstObj.setWeight(listOfWeight);
		}
		
		if (str.equals("SDIM")) {
			if(dimObj==null){
				dimObj=new Dimensions();
			}
			String unit="in";
			shippingValue=shippingValue.toUpperCase();
			shippingValue=shippingValue.replaceAll("”","\"");
			shippingValue=shippingValue.replaceAll("\"","");
			shippingValue=shippingValue.replaceAll(";",",");
			shippingValue=shippingValue.replaceAll(":","");
			//sizeValue=sizeValue.replaceAll(".","");
			//String valuesArr[]=shippingValue.split(",");
			//if(valuesArr.length>1){
				//shippingValue=valuesArr[0];
			//}else{
			//shippingValue=removeSpecialChar(shippingValue,1);
			//String shipDimenArr[] = shippingValue.split("X");
			//List<Dimensions> dimenlist = new ArrayList<Dimensions>();
			//Dimensions dimensionObj = new Dimensions();
			//for (int i = 0; i <= shipDimenArr.length - 1; i++) {
			//	String[] shipDimenArr1 = shipDimenArr[i].split(ApplicationConstants.CONST_DELIMITER_COLON);
				if (dimNo == 0) {
					dimObj.setLength(shipDimValue);
					dimObj.setLengthUnit(unit);
				} else if (dimNo == 1) {
					dimObj.setWidth(shipDimValue);
					dimObj.setWidthUnit(unit);
				} else if (dimNo == 2) {
					dimObj.setHeight(shipDimValue);
					dimObj.setHeightUnit(unit);
				}
			//	dimenlist.add(dimensionObj);
			//}
			shippingEstObj.setDimensions(dimObj);
			//}
		}
		}catch(Exception e){
			_LOGGER.error("Error While processing shipping info"+e.getLocalizedMessage());
			return shippingEstObj;
		}
		return shippingEstObj;
	}
	
	public List<Catalog> getProductCatalog(String catalogValue){
		List<Catalog> listOfCatalog = new ArrayList<Catalog>();
		String catalogName="";
		String pageNo="";
		String tempArr[]={};
		if(catalogValue.contains("|")){
			tempArr=catalogValue.split("|");
			catalogName=tempArr[0];
			pageNo=tempArr[1];
		}
		Catalog catalogObj=new Catalog();
		catalogObj.setCatalogName(catalogName);
		catalogObj.setCatalogPage(pageNo);
		listOfCatalog.add(catalogObj);
		return listOfCatalog;
	}
	
	public Size getSizes(String sizeValue) {
		Size sizeObj = new Size();
		String sizeGroup="dimension";
		String unitValue="in";
		String attriValueLen="Length";
		//1.75 DIA x 4.5
		try{
			String DimenArr[] = {sizeValue} ;
			 if(sizeValue.contains("x")){
				 DimenArr=sizeValue.split("x");
				 sizeGroup="dimension";
			 }
			
			if (sizeGroup.equals("dimension")) {
			Dimension dimensionObj = new Dimension();
			List<Values> valuesList = new ArrayList<Values>();
			List<Value> valuelist;
			Values valuesObj = null;
			Value valObj;
		
				valuesObj = new Values();
				valuelist = new ArrayList<Value>(); 
				//int count=1;
				for (String tempValue : DimenArr) {
					valObj = new Value();
					if(tempValue.contains("(") && tempValue.contains(")")){
						String tempVal=CommonUtility.extractValueSpecialCharacter("(", ")",tempValue);
						attriValueLen=sizeDimMap.get(tempVal);
						if(StringUtils.isEmpty(attriValueLen)){
							attriValueLen="Length";
						}
						attriValueLen=CommonUtility.replaceValues("(", "", attriValueLen);
					}
					valObj.setAttribute(attriValueLen.trim());
					valObj.setValue(tempValue.trim());
					valObj.setUnit(unitValue);
					valuelist.add(valObj);
					valuesObj.setValue(valuelist);
					//count++;
				}
				valuesList.add(valuesObj);
			//}
			dimensionObj.setValues(valuesList);
			sizeObj.setDimension(dimensionObj);
			}
		}
		catch(Exception e)
		{
			
			_LOGGER.error("Error while processing Size :"+e.getMessage());
			return null;
		}
		return sizeObj;
	}	
	
public Product getExistingProductData(Product existingProduct , ProductConfigurations existingProductConfig){
		
		ProductConfigurations newProductConfigurations=new ProductConfigurations();
		Product newProduct=new Product();
		List<String> listCategories=new ArrayList<String>();
		List<Catalog> listCatalog=new ArrayList<Catalog>();
		try{
			if(existingProductConfig==null){
				return new Product();
			}
			
		/*	//Image
			List<Image> imagesList=existingProduct.getImages();
			if(!CollectionUtils.isEmpty(imagesList)){
				List<Image> newImagesList=new ArrayList<Image>();
				for (Image image : imagesList) {
					image.setConfigurations( new ArrayList<Configurations>());
					newImagesList.add(image);
				}
				newProduct.setImages(imagesList);
			}*/
			
			//Categories
			listCategories=existingProduct.getCategories();
			if(!CollectionUtils.isEmpty(listCategories)){
				newProduct.setCategories(listCategories);
			}
		 
			List<String> productKeywords=existingProduct.getProductKeywords();
			if(!CollectionUtils.isEmpty(productKeywords)){
				newProduct.setProductKeywords(productKeywords);
			}
			listCatalog=existingProduct.getCatalogs();
			if(!CollectionUtils.isEmpty(listCatalog)){
				newProduct.setCatalogs(listCatalog);
			}
		newProduct.setProductConfigurations(newProductConfigurations);
		}catch(Exception e){
			_LOGGER.error("Error while processing Existing Product Data " +e.getMessage());
			newProduct.setProductConfigurations(newProductConfigurations);
			return newProduct;
		}
		 _LOGGER.info("Completed processing Existing Data");
		return newProduct;
	}
	
	public ArrayList<String> getCatalog(String authToken){
		if(catalogs == null){
		 catalogs = lookupRestServiceObj.getCatalogs(authToken);
		 for (com.a4tech.lookup.model.Catalog catalog : catalogs) {
			 tempCatList.add(catalog.getName().trim());
		}
	 	}
	 	return tempCatList;
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
	
	public List<Color> getProductColors(String color){
		List<Color> listOfColors = new ArrayList<>();
		try{
		Color colorObj = null;
		color=color.replaceAll("\\|",",");
		String[] colors =getValuesOfArray(color, ",");
		for (String colorName : colors) {
			if(StringUtils.isEmpty(colorName)){
				continue;
			}
			colorName=colorName.replaceAll("&","/");
			colorName=colorName.replaceAll(" w/","/");
			colorName=colorName.replaceAll(" W/","/");
			//colorName = colorName.trim();
			
			colorObj = new Color();
			String colorGroup = UpchargeConstants.getColorGroup(colorName.trim());
			//if (colorGroup == null) {
				//if (colorGroup!=null && colorGroup.contains(ApplicationConstants.CONST_DELIMITER_FSLASH)) {
			if (colorName.contains("/") || colorGroup.contains(ApplicationConstants.CONST_DELIMITER_FSLASH) || colorGroup.contains("COMBO")) {
				
				if(colorGroup==null){
					colorGroup=colorName;
				}
				colorGroup=colorGroup.replaceAll("&","/");
				colorGroup=colorGroup.replaceAll(" w/","/");
				colorGroup=colorGroup.replaceAll(" W/","/");
				String delmtr=ApplicationConstants.CONST_DELIMITER_FSLASH;
				if(colorGroup.toUpperCase().contains("COMBO")){
					delmtr=":COMBO:";
				}
				//if (colorName.contains(ApplicationConstants.CONST_DELIMITER_FSLASH)) {
					if(isComboColor(colorGroup)){
						
						List<Combo> listOfCombo = null;
						String[] comboColors = CommonUtility.getValuesOfArray(colorGroup,delmtr);
						String colorFirstName = UpchargeConstants.getColorGroup(comboColors[0].trim());
						colorObj.setName(colorFirstName == null?"Other":colorFirstName);
						int combosSize = comboColors.length;
						if (combosSize == ApplicationConstants.CONST_INT_VALUE_TWO) {
							String colorComboFirstName = UpchargeConstants.getColorGroup(comboColors[1].trim());
							colorComboFirstName = colorComboFirstName == null?"Other":colorComboFirstName;
							listOfCombo = getColorsCombo(colorComboFirstName, ApplicationConstants.CONST_STRING_EMPTY,
									combosSize);
						} else{
							String colorComboFirstName = UpchargeConstants.getColorGroup(comboColors[1].trim());
							colorComboFirstName = colorComboFirstName == null?"Other":colorComboFirstName;
							
							String colorComboSecondName = UpchargeConstants.getColorGroup(comboColors[2].trim());
							colorComboSecondName = colorComboSecondName == null?"Other":colorComboSecondName;
							listOfCombo = getColorsCombo(colorComboFirstName,colorComboSecondName, combosSize);
						}
						String alias="";
						if(colorGroup.contains(":COMBO:")){
							alias = colorGroup.replaceAll(":COMBO:", "-");
						}else{
							alias = colorGroup.replaceAll(ApplicationConstants.CONST_DELIMITER_FSLASH, "-");
						}
						colorObj.setAlias(alias);
						colorObj.setCombos(listOfCombo);
					} else {
						String[] comboColors = CommonUtility.getValuesOfArray(colorGroup,delmtr);
						String mainColorGroup = UpchargeConstants.getColorGroup(comboColors[0].trim());
						if(mainColorGroup != null){
							colorObj.setName(mainColorGroup);
							colorObj.setAlias(colorName);
						} else {
							colorObj.setName(ApplicationConstants.CONST_VALUE_TYPE_OTHER);
							colorObj.setAlias(colorName);
						}
					}
				/*} else {
					if (colorGroup == null) {
					colorGroup = ApplicationConstants.CONST_VALUE_TYPE_OTHER;
					}
					colorObj.setName(colorGroup);
					colorObj.setAlias(colorName);
				}*/
			} else {
				if (colorGroup == null) {
					colorGroup = ApplicationConstants.CONST_VALUE_TYPE_OTHER;
					}
				colorObj.setName(colorGroup);
				colorObj.setAlias(colorName);
			}
			listOfColors.add(colorObj);
		}
		}catch(Exception e){
			_LOGGER.error("Error while processing color: "+e.getMessage());
		}
		return listOfColors;
	}
	private List<Combo> getColorsCombo(String firstValue,String secondVal,int comboLength){
		List<Combo> listOfCombo = new ArrayList<>();
		Combo comboObj1 = new Combo();
		Combo comboObj2 = new Combo();
		comboObj1.setName(firstValue);
		comboObj1.setType(ApplicationConstants.CONST_STRING_SECONDARY);
		comboObj2.setName(secondVal);
		comboObj2.setType(ApplicationConstants.CONST_STRING_TRIM);
		if(comboLength == ApplicationConstants.CONST_INT_VALUE_TWO){
			listOfCombo.add(comboObj1);
		} else {
			listOfCombo.add(comboObj1);
			listOfCombo.add(comboObj2);
		}
		return listOfCombo;
	}
	
	public static boolean isComboColor(String colorValue){
		String delmtr="/";
		if(colorValue.toUpperCase().contains("COMBO")){
			delmtr=":COMBO:";
		}
    	String[] colorVals = CommonUtility.getValuesOfArray(colorValue,delmtr);
    	String mainColor       = null;
    	String secondaryColor  = null;
    	String thirdColor      = null;
    	if(colorVals.length == ApplicationConstants.CONST_INT_VALUE_TWO){
    		 mainColor = UpchargeConstants.getColorGroup(colorVals[0].trim());
    		 secondaryColor = UpchargeConstants.getColorGroup(colorVals[1].trim());
    		 if(mainColor != null && secondaryColor != null){
    			 return true;
    		 }
    	} else if(colorVals.length == ApplicationConstants.CONST_INT_VALUE_THREE){
    		 mainColor      = UpchargeConstants.getColorGroup(colorVals[0].trim());
    		 secondaryColor = UpchargeConstants.getColorGroup(colorVals[1].trim());
    		 thirdColor     = UpchargeConstants.getColorGroup(colorVals[2].trim());
    		 if(mainColor != null && secondaryColor != null && thirdColor != null){
    			 return true;
    		 }
    	} else{
    		
    	}
    	return false;
    }
	
	public static String[] getValuesOfArray(String data,String delimiter){
		   if(!StringUtils.isEmpty(data)){
			   return data.split(delimiter);
		   }
		   return null;
	   }

/*public List<Color> getColorCriteria(String colorValue) {
		
		Color colorObj = null;
		List<Color> colorList = new ArrayList<Color>();
		//HighCaliberConstants
		try {
		//Map<String, String> HCLCOLOR_MAP=new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
		// Map<String, String> HCLCOLOR_MAP =HighCaliberConstants.getHCLCOLOR_MAP();
			List<Combo> comboList = null;
			String value = colorValue;
			String tempcolorArray[]=value.split(ApplicationConstants.CONST_STRING_COMMA_SEP);
			for (String colorVal : tempcolorArray) {
			String strColor=colorVal;
			strColor=strColor.replaceAll("&","/");
			//strColor=strColor.replaceAll(" w/","/");
			//strColor=strColor.replaceAll(" W/","/");
			boolean isCombo = false;
				colorObj = new Color();
				comboList = new ArrayList<Combo>();
    			isCombo = isComboColors(strColor);
    			if(isCombo){
    				if(HighCaliberConstants.HCLCOLOR_MAP.get(strColor.trim())!=null){
    				//if(HCLCOLOR_MAP.get(strColor.trim())!=null){
    					isCombo=false;
    				}
    			}
    			
				if (!isCombo) {
					String colorName=TomaxConstants.TCOLOR_MAP.get(strColor.trim());
					//String colorName=HCLCOLOR_MAP.get(strColor.trim());
					if(StringUtils.isEmpty(colorName)){
						colorName=ApplicationConstants.CONST_STRING_UNCLASSIFIED_OTHER;
					}
					colorObj.setName(colorName);
					colorObj.setAlias(colorVal.trim());
					colorList.add(colorObj);
				} else {
					//245-Mid Brown/Navy
					String colorArray[] = strColor.split(ApplicationConstants.CONST_DELIMITER_FSLASH);
					//if(colorArray.length==2){
					String combo_color_1=TomaxConstants.TCOLOR_MAP.get(colorArray[0].trim());
					//String combo_color_1=HCLCOLOR_MAP.get(colorArray[0].trim());
					if(StringUtils.isEmpty(combo_color_1)){
						combo_color_1=ApplicationConstants.CONST_STRING_UNCLASSIFIED_OTHER;
					}
					colorObj.setName(combo_color_1);
					colorObj.setAlias(strColor);
					
					Combo comboObj = new Combo();
					String combo_color_2=TomaxConstants.TCOLOR_MAP.get(colorArray[1].trim());
					//String combo_color_2=HCLCOLOR_MAP.get(colorArray[1].trim());
					if(StringUtils.isEmpty(combo_color_2)){
						combo_color_2=ApplicationConstants.CONST_STRING_UNCLASSIFIED_OTHER;
					}
					comboObj.setName(combo_color_2.trim());
					comboObj.setType(ApplicationConstants.CONST_STRING_SECONDARY);
					if(colorArray.length==3){
						String combo_color_3=TomaxConstants.TCOLOR_MAP.get(colorArray[2].trim());
						//String combo_color_3=HCLCOLOR_MAP.get(colorArray[2].trim());
						if(StringUtils.isEmpty(combo_color_3)){
							combo_color_3=ApplicationConstants.CONST_STRING_UNCLASSIFIED_OTHER;
						}
						Combo comboObj2 = new Combo();
						comboObj2.setName(combo_color_3.trim());
						comboObj2.setType(ApplicationConstants.CONST_STRING_TRIM);
						comboList.add(comboObj2);
					}
					comboList.add(comboObj);
					colorObj.setCombos(comboList);
					colorList.add(colorObj);
				 	}
		}
		//}
		} catch (Exception e) {
			_LOGGER.error("Error while processing Color :" + e.getMessage());
			return new ArrayList<Color>();
		}
		_LOGGER.info("Colors Processed");
		return colorList;
		}*/

	private boolean isComboColors1(String value) {
	boolean result = false;
	if (value.contains("/")) {
		result = true;
	}
	return result;
	}
	public LookupServiceData getLookupServiceDataObj() {
		return lookupServiceDataObj;
	}


	public void setLookupServiceDataObj(LookupServiceData lookupServiceDataObj) {
		this.lookupServiceDataObj = lookupServiceDataObj;
	}


	public LookupRestService getLookupRestServiceObj() {
		return lookupRestServiceObj;
	}


	public void setLookupRestServiceObj(LookupRestService lookupRestServiceObj) {
		this.lookupRestServiceObj = lookupRestServiceObj;
	}
	
	
	public Product getCombinedCharge(String combStr,Product productExcelObj){
		ProductConfigurations productConfigurations=productExcelObj.getProductConfigurations();
		List<PriceGrid> existingPriceGrids=productExcelObj.getPriceGrids();
		try{
				//"LMIN:Less than Minimum@@@@@G#####55");
				//IMOP:   Add'l Decoration Option: Consecutive Numbering@@@@@A@@@@@0.55___0.48___0.27___0.17___0.13");
		//combStr="PCKG:Special Packaging: Rustic Tin RT810@@@@@C@@@@@19.5";
		//List<PriceGrid> existingPriceGrids=productExcelObj.getPriceGrids();
		String tempStr[]=combStr.split(":");
		String tempString=combStr.replace(tempStr[0]+":", "");
		String valueArr[]=tempString.split("@@@@@");
		String critValue=valueArr[0];
		String discValue=valueArr[1];		
		String pricesTemp=valueArr[2];
		
		if(tempStr[0].equals("IMMD")){
			
		}else if(tempStr[0].equals("IMOP")){
			//Shipping: Early Release
			//Corporate Gift Handling (per location, including first)
			String imprintOptionName="Imprint";
			List<Option> listOfOptionNew =new ArrayList<Option>();
			
			if(critValue.contains(":")){
				String tempArr[]=critValue.split(":");
				imprintOptionName=tempArr[0];
				critValue=tempArr[1];
			}
			critValue=critValue.trim();
			imprintOptionName=imprintOptionName.trim();
			listOfOptionNew=getOptions( critValue,"Imprint",imprintOptionName);
			List<Option> listOfOptionTemp=productConfigurations.getOptions();
			if(!CollectionUtils.isEmpty(listOfOptionTemp)){
				listOfOptionNew.addAll(listOfOptionTemp);
			}
			productConfigurations.setOptions(listOfOptionNew);
			existingPriceGrids=beaconProPriceGridParser.getPriceGrids(pricesTemp,"1",discValue,"USD","",
					false, "False", critValue, "Imprint Option",
					 1,"Optional","Imprint Option Charge", "Other",imprintOptionName,
					existingPriceGrids);
			
		
		
		}else if(tempStr[0].equals("PROP")){

			//Shipping: Early Release
			//Corporate Gift Handling (per location, including first)
			String productOptionName="Product";
			List<Option> listOfOptionNew =new ArrayList<Option>();
			if(critValue.contains(":")){
				String tempArr[]=critValue.split(":");
				productOptionName=tempArr[0];
				critValue=tempArr[1];
			}
			critValue=critValue.trim();
			productOptionName=productOptionName.trim();
			listOfOptionNew=getOptions( critValue,"Product",productOptionName);
			List<Option> listOfOptionTemp=productConfigurations.getOptions();
			if(!CollectionUtils.isEmpty(listOfOptionTemp)){
				listOfOptionNew.addAll(listOfOptionTemp);
			}
			productConfigurations.setOptions(listOfOptionNew);
			existingPriceGrids=beaconProPriceGridParser.getPriceGrids(pricesTemp,"1",discValue,"USD","",
					false, "False", critValue, "Product Option",
					 1,"Optional","Product Option Charge", "Other",productOptionName,
					existingPriceGrids);
			
		
		}else if(tempStr[0].equals("LMIN")){
			/*(String listOfPrices, String listOfQuan, String discountCodes,String currency, String priceInclude,
					 boolean isBasePrice,String qurFlag, String priceName, 
					 String criterias,Integer sequence,String upChargeType,String upchargeUsageType,
					 List<PriceGrid> existingPriceGrid)*/
			critValue=critValue.trim();
			existingPriceGrids=beaconProPriceGridParser.getPriceGrids(pricesTemp,"1",discValue,"USD","",
					false, "False", critValue, "Less than Minimum",
					 1,"Optional","Less than Minimum Charge", "Other",null,
					existingPriceGrids);
		}else if(tempStr[0].equals("SHOP")){
			//Shipping: Early Release
			//Corporate Gift Handling (per location, including first)
			String shippingName="Shipping";
			List<Option> listOfOptionNew =new ArrayList<Option>();
			if(critValue.contains(":")){
				String tempArr[]=critValue.split(":");
				shippingName=tempArr[0];
				critValue=tempArr[1];
			}
			critValue=critValue.trim();
			shippingName=shippingName.trim();
			listOfOptionNew=getOptions( critValue,"Shipping",shippingName);
			List<Option> listOfOptionTemp=productConfigurations.getOptions();
			if(!CollectionUtils.isEmpty(listOfOptionTemp)){
				listOfOptionNew.addAll(listOfOptionTemp);
			}
			productConfigurations.setOptions(listOfOptionNew);
			existingPriceGrids=beaconProPriceGridParser.getPriceGrids(pricesTemp,"1",discValue,"USD","",
					false, "False", critValue, "Shipping Option",
					 1,"Optional","Shipping Charge", "Other",shippingName,
					existingPriceGrids);
			
		}else if(tempStr[0].equals("PCKG")){
			if(critValue.contains(":")){
				String tempArr[]=critValue.split(":");
				critValue=tempArr[1];
			}
			critValue=critValue.trim();
			List<Packaging> packagingList=getPackagingCriteria(critValue);
			List<Packaging> packagingListTemp=productConfigurations.getPackaging();
			if(!CollectionUtils.isEmpty(packagingListTemp)){
				packagingList.addAll(packagingListTemp);
			}
			
			productConfigurations.setPackaging(packagingList);
			existingPriceGrids=beaconProPriceGridParser.getPriceGrids(pricesTemp,"1",discValue,"USD","",
					false, "False", critValue, 
					"Packaging", 1,"Optional","Packaging Charge", "Other",null,
					existingPriceGrids);
			
		}
		productExcelObj.setProductConfigurations(productConfigurations);
		productExcelObj.setPriceGrids(existingPriceGrids);
		}catch(Exception e){
			_LOGGER.error("Error while processing combine charges "+e.getMessage()+"for Value"+combStr);
			productExcelObj.setProductConfigurations(productConfigurations);
			productExcelObj.setPriceGrids(existingPriceGrids);
		}
		return productExcelObj;
		
	}

	//public static List<Option> getOptions(List<String> opntnList,String optionNamee) {
	public static List<Option> getOptions(String optionDataValue,String optionType,String optionNamee) {
		List<Option> optionList=new ArrayList<>();
		try{
			   Option optionObj=new Option();
			   List<OptionValue> valuesList=new ArrayList<OptionValue>();
				 OptionValue optionValueObj=null;
				//  for (String optionDataValue: opntnList) {
					  optionValueObj=new OptionValue();
					  optionValueObj.setValue(optionDataValue.trim());
					  valuesList.add(optionValueObj);
				//  }
					  optionObj.setOptionType(optionType);
					  optionObj.setName(optionNamee.trim());
					  optionObj.setValues(valuesList); 
					  optionObj.setAdditionalInformation("");
					  optionObj.setCanOnlyOrderOne(false);
					  optionObj.setRequiredForOrder(false);
					  optionList.add(optionObj);
		   }catch(Exception e){
			   _LOGGER.error("Error while processing Options :"+e.getMessage());          
		      return new ArrayList<Option>();
		      
		     }
		  return optionList;
		  
	 }
	
	public BeaconProPriceGridParser getBeaconProPriceGridParser() {
		return beaconProPriceGridParser;
	}


	public void setBeaconProPriceGridParser(
			BeaconProPriceGridParser beaconProPriceGridParser) {
		this.beaconProPriceGridParser = beaconProPriceGridParser;
	}	
	
	}	
		
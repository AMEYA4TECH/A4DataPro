package parser.v5.AccessLine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import parser.sunGraphix.SunGraphixAttributeParser;

import com.a4tech.lookup.service.LookupServiceData;
import com.a4tech.product.model.Color;
import com.a4tech.product.model.Dimensions;
import com.a4tech.product.model.Image;
import com.a4tech.product.model.ImprintLocation;
import com.a4tech.product.model.ImprintMethod;
import com.a4tech.product.model.Material;
import com.a4tech.product.model.NumberOfItems;
import com.a4tech.product.model.Product;
import com.a4tech.product.model.ProductConfigurations;
import com.a4tech.product.model.Shape;
import com.a4tech.product.model.ShippingEstimate;
import com.a4tech.product.model.Weight;
import com.a4tech.util.ApplicationConstants;
import com.a4tech.util.CommonUtility;

public class AccessLineAttributeParser {
	private static final Logger _LOGGER = Logger.getLogger(AccessLineAttributeParser.class);
	private static LookupServiceData lookupServiceData;
public Product getExistingProductData(Product existingProduct , ProductConfigurations existingProductConfig){
		
		ProductConfigurations newProductConfigurations=new ProductConfigurations();
		Product newProduct=new Product();
		
		try{
			//categories
			List<String> listCategories=new ArrayList<String>();
			listCategories=existingProduct.getCategories();
			if(!CollectionUtils.isEmpty(listCategories)){
				newProduct.setCategories(listCategories);
			}
		//Images
		List<Image> images=existingProduct.getImages();
		if(!CollectionUtils.isEmpty(images)){
			newProduct.setImages(images);
		}
		//keywords
		List<String> productKeywords=existingProduct.getProductKeywords();
		if(!CollectionUtils.isEmpty(productKeywords)){
			newProduct.setProductKeywords(productKeywords);
		}
		
		newProduct.setProductConfigurations(newProductConfigurations);
		}catch(Exception e){
			_LOGGER.error("Error while processing Existing Product Data " +e.getMessage());
		}
		 _LOGGER.info("Completed processing Existing Data");
		return newProduct;
		
	}

public List<String> getProductCategories(String categoryVal){
	List<String> listOfCategories = new ArrayList<>();
	
	 List<String> categories = CommonUtility.getStringAsList(categoryVal,
				ApplicationConstants.CONST_DELIMITER_COMMA);
	 for (String catValue : categories) {
		 if(lookupServiceData.isCategory(catValue.trim())){
				listOfCategories.add(catValue);
			}else{
				System.out.println("No categroy match");
			}
	}
	
	return listOfCategories;
}
	public static ShippingEstimate getShippingEstimates( String shippingValue,String sdimVAl,ShippingEstimate shippingEstObj,String str,String sdimType) {
	//ShippingEstimate shipingObj = new ShippingEstimate();
	if(str.equals("NOI")){
		List<NumberOfItems> listOfNumberOfItems = new ArrayList<NumberOfItems>();
		NumberOfItems itemObj = new NumberOfItems();
		itemObj.setUnit(ApplicationConstants.CONST_STRING_SHIPPING_NUMBER_UNIT_CASE);
		itemObj.setValue(shippingValue.trim());
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
		Dimensions dimensionObj=shippingEstObj.getDimensions();
		Dimensions tempObj=new Dimensions();
		if(dimensionObj== null || dimensionObj.equals(tempObj)){
			dimensionObj=new Dimensions();
		}
		String unit="in";
				//Dimensions dimensionObj = new Dimensions();
		if (sdimType.equals("L")){//if (sdimType.equals("L")){
				dimensionObj.setLength(sdimVAl.trim());
				dimensionObj.setLengthUnit(unit);
		}
		if (sdimType.equals("W")){
				dimensionObj.setWidth(sdimVAl.trim());
				dimensionObj.setWidthUnit(unit);
		}
		if (sdimType.equals("H")){
				dimensionObj.setHeight(sdimVAl.trim());
				dimensionObj.setHeightUnit(unit);
		}
		shippingEstObj.setDimensions(dimensionObj);
				}
	return shippingEstObj;
}
	
	public static ProductConfigurations getImprintMethod(String data,ProductConfigurations prodConfig){
		List<ImprintMethod> listOfImprintMethod = prodConfig.getImprintMethods();
		try{
		//List<ImprintMethod> imprintMethodsList = new ArrayList<ImprintMethod>();
			String imprintMethod="";
			String imprintLocation="";
			HashSet<String> setVal=new HashSet<String>();
			HashSet<String> setImpMthd=new HashSet<String>();
			ImprintMethod	imprintMethodObj = null;
			List<ImprintLocation> impvaluesList=new ArrayList<ImprintLocation>();
			boolean impflag=true;
			String[] imprintMethodValues = data.split(ApplicationConstants.CONST_DELIMITER_COMMA);
			for (String imprintMethodName : imprintMethodValues) {
			if(imprintMethodName.contains("on") || imprintMethodName.contains("behind") || imprintMethodName.contains("below")){
				if(imprintMethodName.contains("on")){
					String str[]=imprintMethodName.split("on");
					imprintMethod=str[0];
					//imprintLocation="on "+str[1];
					imprintLocation=str[1];
					//imprintLocation=imprintLocation.substring(imprintLocation.indexOf("on"),imprintLocation.length());
				}else if(imprintMethodName.contains("behind")){
					String str[]=imprintMethodName.split("behind");
					imprintMethod=str[0];
					//imprintLocation="behind "+str[1];
					imprintLocation=str[1];
				}else if(imprintMethodName.contains("below")){
					String str[]=imprintMethodName.split("below");
					imprintMethod=str[0];
					//imprintLocation="below "+str[1];
					imprintLocation=str[1];
				}
				//setImpMthd.add(imprintMethod);
				setVal.add(imprintLocation);
				//ImprintLocation locObj=new ImprintLocation();
				//locObj.setValue(imprintLocation);
				//values.add(locObj);
				
			}
			
		if(CollectionUtils.isEmpty(listOfImprintMethod)){
			//List<ImprintMethod> listOfImprintMethodTemp = productConfigObj.getImprintMethods();
			listOfImprintMethod=new ArrayList<ImprintMethod>();
		}
		
	
			if(StringUtils.isEmpty(imprintMethod)){
				if(!StringUtils.isEmpty(data)){
					imprintMethod=data;
				}else{
					continue;	
				}
				
			}
			imprintMethodObj = new ImprintMethod();
			imprintMethod=imprintMethod.trim();
			String alias = "";
			if(imprintMethod.contains("Foil")){
				alias = imprintMethod.trim().toUpperCase();
				//imprintMethodName=imprintMethodName.replace("Foil", "Foil Stamped");
				imprintMethod = "Foil Stamped";
		    } else if(imprintMethod.contains("Deboss")){
		    	alias = imprintMethod.trim().toUpperCase();
    		    //imprintMethodName=imprintMethodName.replaceAll("Deboss", "Debossed");
		    	imprintMethod = "Debossed";
	        } else if(imprintMethod.contains("4C")){
	        	 alias = imprintMethod.trim().toUpperCase();
	        	 imprintMethod = "Full Color";
	        }else if(imprintMethod.contains("Pad")){
	        	 alias = imprintMethod.trim().toUpperCase();
	        	 imprintMethod = "Pad Print";
	        }else if(imprintMethod.contains("Silk")){
	        	if(impflag){
	        	 alias = imprintMethod.trim().toUpperCase();
	        	 imprintMethod = "Silkscreen";
	        	 impflag=false;
	        	}else{
	        		alias="";
	        		imprintMethod="";
	        	}
	        }
			if(!StringUtils.isEmpty(imprintMethod)){
			imprintMethod = imprintMethod.trim().toUpperCase();
			  if(lookupServiceData.isImprintMethod(imprintMethod)){
				  imprintMethodObj.setType(imprintMethod);
				  if(StringUtils.isEmpty(alias)){
					  imprintMethodObj.setAlias(imprintMethod);
				  } else{
					  imprintMethodObj.setAlias(alias);
				  }
				  
			  }else{
				  imprintMethodObj.setType(ApplicationConstants.CONST_VALUE_TYPE_OTHER);
				  imprintMethodObj.setAlias(imprintMethod);
			  }
			  listOfImprintMethod.add(imprintMethodObj);
			}
			  //productConfigObj.setImprintMethods(listOfImprintMethod);
		}
			
			
			//setVal.add(imprintLocation);
			//ImprintLocation locObj=new ImprintLocation();
			//locObj.setValue(imprintLocation);
			//values.add(locObj);
			for (String stringImp : setVal) {
				ImprintLocation locObj=new ImprintLocation();
				locObj.setValue(stringImp);
				impvaluesList.add(locObj);
			}
			
			prodConfig.setImprintLocation(impvaluesList);
			prodConfig.setImprintMethods(listOfImprintMethod);
		}catch(Exception e){
			_LOGGER.error("Error while processing Size :"+e.getMessage());
			return prodConfig;
		}
		return prodConfig;
	}

	public LookupServiceData getLookupServiceData() {
		return lookupServiceData;
	}

	public void setLookupServiceData(LookupServiceData lookupServiceData) {
		this.lookupServiceData = lookupServiceData;
	}
	
	
}

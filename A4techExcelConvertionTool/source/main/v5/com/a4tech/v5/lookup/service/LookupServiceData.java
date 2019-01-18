package com.a4tech.v5.lookup.service;

import java.util.List;

import com.a4tech.v5.lookup.model.Catalog;
import com.a4tech.v5.lookup.service.restService.LookupRestService;
import com.a4tech.v5.util.ApplicationConstants;

public class LookupServiceData {
	
	private LookupRestService  lookupRestServiceV5;
	public static List<String> imprintMethods 		= null;
	public static List<String> materialValues 		= null;
	public static List<String> shapes 				= null;
	public static List<String> listOfOrigins 		= null;
	public List<String> 	   listOfFobPoints 		= null;
	public List<String>        lineNames 			= null;
	public List<String>        tradeNames  			= null;
	public static List<String> categories 			= null;
	public  List<Catalog> catalogs                  = null;
	public static List<String>   themes  		    = null;
    public static List<String> packages             = null;
    public static List<String> colorValues 		= null;
    
	public  List<String> getImprintMethods(){
		  if(imprintMethods == null){
			  imprintMethods = lookupRestServiceV5.getImprintMethodData();
			  return imprintMethods;
		  }
		return imprintMethods;
	}
	public  List<String> getMaterialValues(){
		 
		  if(materialValues == null){
			  materialValues = lookupRestServiceV5.getMaterialsData();
			  return materialValues;
		  }
		return materialValues;
	}
	public  List<String> getColorValues(){
		 
		  if(colorValues == null){
			  colorValues = lookupRestServiceV5.getColorData();
			  return colorValues;
		  }
		return colorValues;
	}
	public List<String> getShapeValues(){
		if(shapes == null){
			shapes = lookupRestServiceV5.getShapesData();
		}
		return shapes;
	}
	public List<String> getOrigins(){
		if(listOfOrigins == null){
			listOfOrigins = lookupRestServiceV5.getOrigins();
		}
		return listOfOrigins;
	}
	public List<String> getFobPoints(String authToken,String environemtType){
		if(listOfFobPoints == null){
			listOfFobPoints = lookupRestServiceV5.getFobPoints(authToken,environemtType);
		}
		return listOfFobPoints;
	}
	public List<String> getLineNames(String authToken,String environemtType){
		if(lineNames == null){
			lineNames = lookupRestServiceV5.getLineNames(authToken,environemtType);
		}
		return lineNames;
	}
	public List<String> getCategories(){
		if(categories == null){
			categories = lookupRestServiceV5.getCategories();
		}
		return categories;
	}
	public List<String> getTradeNames(String tradeName){
		if(tradeNames == null){
			tradeNames = lookupRestServiceV5.getTradeNames(tradeName);
		}
		return tradeNames;
	}
	public boolean isImprintMethod(String imprintValue){
		if(imprintMethods == null){
			imprintMethods = getImprintMethods();
		}
		if(imprintMethods != null){
			return imprintMethods.contains(imprintValue);
		}
		return false;
	}
	
	public boolean isMaterial(String matrlValue){
		if(materialValues == null){
			materialValues = getMaterialValues();
		}
		if(materialValues != null){
			return materialValues.contains(matrlValue);
		}
		return false;
	}
	
	public String getMaterialTypeValue(String materialName){
		if(materialValues == null){
			materialValues = getMaterialValues();
		}
		    if(materialValues != null){
		    	 for (String mtrlName : materialValues) {
					    if(materialName.contains(mtrlName)){
					    	return mtrlName;
					    }
				}
		    }
		return ApplicationConstants.CONST_VALUE_TYPE_OTHER;
	}
	
	public boolean isShape(String name){
		if(shapes == null){
			shapes = getShapeValues();
		}
		return shapes.contains(name);
	}
     public boolean isOrigin(String countryName){
		if(listOfOrigins == null){
			listOfOrigins = getOrigins();
		}
		return listOfOrigins.contains(countryName);
	}
     public boolean isCategory(String categoryName){
    	 if(categories == null){
    		 categories = getCategories();
 		}
 		return categories.contains(categoryName);
     }
     
     public List<Catalog> getCatalog(String authToken,String environemtType){
    	 if(catalogs == null){
    		 catalogs = lookupRestServiceV5.getCatalogs(authToken,environemtType);
    	 }
    	 return catalogs;
     }
     
     public List<String> getTheme(String authToken){
    	 if(themes == null){
    		 themes = lookupRestServiceV5.getTheme();
    	 }
    	 return themes;
     }
     public boolean isTradeName(String tradeName){
 		if(tradeNames == null){
 			tradeNames = getTradeNames(tradeName);
 		}
 		if(tradeNames != null){
 			return tradeNames.contains(tradeName);
 		}
 		return false;
 	}
    public boolean isTheme(String themeVal){
    	if(themes == null){
    		themes = getTheme(themeVal);
 		}
 		if(themes != null){
 			return themes.contains(themeVal);
 		}
    	return false;
    }
     public List<String> getPackageValues(){
    	 if(packages == null){
    		 packages = lookupRestServiceV5.getPackages();
    	 }
    	 return packages;
     }
	public LookupRestService getLookupRestServiceV5() {
		return lookupRestServiceV5;
	}
	public void setLookupRestServiceV5(LookupRestService lookupRestServiceV5) {
		this.lookupRestServiceV5 = lookupRestServiceV5;
	}
 	
	
	
}

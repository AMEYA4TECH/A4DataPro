package parser.beaconPromotions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.a4tech.lookup.service.LookupServiceData;
import com.a4tech.lookup.service.restService.LookupRestService;
import com.a4tech.product.model.BlendMaterial;
import com.a4tech.product.model.Combo;
import com.a4tech.product.model.Material;
import com.a4tech.util.CommonUtility;

public class BeaconProductMaterialParser {

	private static final Logger _LOGGER = Logger.getLogger(BeaconProductMaterialParser.class);

	private LookupServiceData lookupServiceDataObj;
	private LookupRestService lookupRestServiceObj;

	public List<Material> getMaterialList(String materialValue1) {

		Material materialObj = new Material();
		List<Material> listOfMaterial = new ArrayList<>();
		try {
			if(isComboMtrl(materialValue1)){
				
				String ComboValue[]={};
				String MaterialForCombo=materialValue1;
				MaterialForCombo=MaterialForCombo.replace("%%%%", " ");
		 		
				materialValue1=materialValue1.replaceAll("%","");
				
		 		materialValue1=materialValue1.replaceAll("88 Polyvinyl Chloride/12 PlyWATERPROOF","Polyester:Vinyl");
			    materialValue1=materialValue1.replaceAll( "80 PVC/20 PolyesterWATERPROOF","PVC:Polyester");
				materialValue1=materialValue1.replaceAll("62 Cotton/38 PVCWATERPROOF","Cotton:PVC");
				
				
				
				Combo comboObj = new Combo();
				ComboValue=materialValue1.split(":");
				
	    		 //for (String materialValue : ComboValue) {
	    			 materialObj = new Material();
					 //materialObj = getMaterialValue(listOfLookupMaterial.toString(), materialValue1+" "+finalTempAliasVal);
	    			 materialObj.setName(ComboValue[0]);
	    			 materialObj.setAlias(MaterialForCombo);
	    			 comboObj.setName(ComboValue[1]);
	    			 materialObj.setCombo(comboObj);
	    		// }
	    		 listOfMaterial.add(materialObj);
				return listOfMaterial;
				
			}
			
			String tempAliasValArr[]=materialValue1.split("%%%%");
			String finalTempAliasVal="";
			materialValue1=tempAliasValArr[0];
			
			if(tempAliasValArr.length==2){
				finalTempAliasVal=tempAliasValArr[1];
			}
			
		List<String> listOfLookupMaterial = getMaterialType(materialValue1.toUpperCase());
		if(!listOfLookupMaterial.isEmpty()){
			int numOfMaterials = listOfLookupMaterial.size();
			/////////////////
			String valuesTempArr[]=null;
			boolean flag=false;
			if(materialValue1.contains("/"))
			{
				valuesTempArr = CommonUtility.getValuesOfArray(materialValue1,"/");
				flag=true;
			}else if(materialValue1.contains("_"))
			{
				valuesTempArr = CommonUtility.getValuesOfArray(materialValue1,"_");
				flag=true;
			}
			
			////////////////////
			  //if(numOfMaterials == 1){ // this condition used to single material value(E.X 100% Cotton)
			if(numOfMaterials == 1 && !flag){ // this condition used to single material value(E.X 100% Cotton)
				  materialObj = getMaterialValue(listOfLookupMaterial.toString(), materialValue1+" "+finalTempAliasVal);
				  listOfMaterial.add(materialObj);
			  }else if(isBlendMaterial(materialValue1)){   // this condition for blend material
				  
				String[] values = null;
				if(materialValue1.contains("/"))
				{
				 values = CommonUtility.getValuesOfArray(materialValue1,"/");
				}else if(materialValue1.contains("_"))
				{
					 values = CommonUtility.getValuesOfArray(materialValue1,"_");
				}
		    	 BlendMaterial blentMaterialObj = null;
		    	 List<BlendMaterial> listOfBlendMaterial = new ArrayList<>();
				     if(values.length == 2){
				    	 for (String materialValue : values) {
				    		 blentMaterialObj = new BlendMaterial();
				    		 String mtrlType = getMaterialType(materialValue.toUpperCase()).toString();
				    		 if(materialValue.contains("%")){
								  String percentage = materialValue.split("%")[0];
								  materialObj.setName("BLEND");
								  materialObj.setAlias(materialValue1+" "+finalTempAliasVal); 
								  
								  if(!StringUtils.isEmpty(mtrlType)){
									  mtrlType=CommonUtility.removeCurlyBraces(mtrlType);
								  }
								  if(!StringUtils.isEmpty(mtrlType)){
								  blentMaterialObj.setName(mtrlType);
								  }else{
									  blentMaterialObj.setName("Other Fabric"); 
								  }
								  blentMaterialObj.setPercentage(percentage);
								  listOfBlendMaterial.add(blentMaterialObj);
							  }
				    		
				    	 }
				    	    materialObj.setBlendMaterials(listOfBlendMaterial);
				    		listOfMaterial.add(materialObj);
				     }else if(values.length == 3) { // this condition for combo and blend values
				    	 Combo comboObj = new Combo();
			    	
				    	 int secondValuePercentage = 0  ;
				    		 for(int i=0;i<2;i++){
				    		 blentMaterialObj = new BlendMaterial();
							  String mtrlType = getMaterialType(values[i].toUpperCase()).toString();
							  if(values[i].contains("%")){
								  String percentage = values[0].split("%")[0];
								  if(i == 0){
									  secondValuePercentage = 100-Integer.parseInt(percentage);
								  }
								  if(!StringUtils.isEmpty(mtrlType)){
								  mtrlType=CommonUtility.removeCurlyBraces(mtrlType);
								  }
								  if(!StringUtils.isEmpty(mtrlType)){
									  blentMaterialObj.setName(mtrlType);  
								  }else{
									blentMaterialObj.setName("Other Fabric");  
								  }
									 if(i ==0){
										  blentMaterialObj.setPercentage(percentage);
									  }else{
										  blentMaterialObj.setPercentage(Integer.toString(secondValuePercentage));
									  }
								  
								 /*else if(values[i].contains("%")){
								 blentMaterialObj.setPercentage(Integer.toString(percentage1));
								  }*/
								  listOfBlendMaterial.add(blentMaterialObj);

							  }
							  else{
								  materialObj.setName(CommonUtility.removeCurlyBraces(mtrlType));
								  materialObj.setAlias(materialValue1+" "+finalTempAliasVal);  
							  }
						} //[70% Modacrylic , 25% Cotton , 5%],[59% Cotton, 39% Polyester, 2% Sp]
				    	 String valuesTemp[]=values[2].split("%");
				    	 
				    	 if( valuesTemp.length==1){
				    		 materialObj.setName("Other");
				    		// materialObj.setAlias(values[1]);//
				    		 materialObj.setAlias(materialValue1+" "+finalTempAliasVal);
				    	 }else if(valuesTemp.length==2){
				    		 //materialObj.setName(valuesTemp[1]);
				    		 //materialObj.setName(values[2].toString());/////////////////imp thingtoday
				    		 List<String> listOfLookupMaterialTemp = getMaterialType(values[2].toString().toUpperCase());
				    		if(!listOfLookupMaterialTemp.isEmpty()){
				    			materialObj.setName(listOfLookupMaterialTemp.get(0));
				    		}else{
				    			materialObj.setName(values[2].toString());
				    		}
				    		
				    		// materialObj.setName(valuesTemp[2]);
				    		 materialObj.setAlias(materialValue1+" "+finalTempAliasVal); 
				    	 }
				    	 comboObj.setBlendMaterials(listOfBlendMaterial);
				    	 comboObj.setName("Blend");
				    	 materialObj.setCombo(comboObj);
				    	 listOfMaterial.add(materialObj);
				     }	        
			  }
		}else{ // used for Material is not available in lookup, then it goes in Others
			if(materialValue1.equalsIgnoreCase("Unassigned")){
				
			}else{
			materialObj = getMaterialValue("Other", materialValue1+" "+finalTempAliasVal);
			listOfMaterial.add(materialObj);
			}
		}
		tempAliasValArr=new String[tempAliasValArr.length];;
		} catch (Exception e) {
			_LOGGER.error("Error while Material1 processing :" + e.getMessage());
		
		}
		_LOGGER.info("Material processed");
		return listOfMaterial;
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
		
		/*if(data.split("_").length ==2 ){ //51% Polyester/49% Cocona� 37.5
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
		
	
	/* public List<Material> getMaterialList(String materialValue2,List<Material> listOfMaterial){
			Material materialObj = new Material();
			 List<String> listOfLookupMaterial = getMaterialType(materialValue2.toUpperCase());
			//try{
			//if(!listOfLookupMaterial.isEmpty()){
			int numOfMaterials = listOfLookupMaterial.size();
				  if(numOfMaterials == 1){ 
					  materialObj = getMaterialValue(listOfLookupMaterial.toString(), materialValue2);
					  listOfMaterial.add(materialObj);
				  }
				  else { 	
					 materialObj = getMaterialValue("Other", materialValue2);
					listOfMaterial.add(materialObj);
				  }
				return listOfMaterial;
			 catch (Exception e) {
			_LOGGER.error("Error while Material1 processing :" + e.getMessage());
			 //	_LOGGER.info("Material1 processed");
			}
		 
		
			
		
*/
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


}

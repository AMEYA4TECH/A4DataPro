package com.a4tech.v5.excel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.a4tech.v5.product.model.PriceConfiguration;
import com.a4tech.v5.product.model.PriceUnit;
import com.a4tech.v5.util.ApplicationConstants;
import com.a4tech.v5.util.LookupData;
import com.a4tech.v5.product.model.Price;
import com.a4tech.v5.product.model.PriceGrid;

public abstract class PriceServicee {
	
	private static Logger              _LOGGER       =  Logger.getLogger(PriceServicee.class);
	
	//public abstract List<Price> getPrices(String[] prices, String[] quantity , String[] discount,String currency ,String isQUR);
	public List<PriceGrid> getPriceGrids( boolean isBasePrice,String priceName_Desc,String priceInclude,Integer sequence,
			String listOfPrices, String listOfQuan, String discountCodes,
			String currency ,String isQUR,
			String criterias,String serviceCharge,String upChargeType,String upchargeUsageType,String optnype,
			List<PriceGrid> existingPriceGrid){
		

		try{
			if(CollectionUtils.isEmpty(existingPriceGrid)){
				existingPriceGrid=new ArrayList<PriceGrid>();
				sequence=1;
			}else{
				sequence= existingPriceGrid.size()+1;
			}
		//Integer sequence = 1;
		List<PriceConfiguration> configuration = null;
		PriceGrid priceGrid = new PriceGrid();
		String[] prices = listOfPrices
				.split(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
		String[] quantity = listOfQuan
				.split(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
		
		//priceGrid.setCurrency(currency);
		priceGrid.setDescription(priceName_Desc);
		priceGrid.setPriceIncludes(priceInclude);
		/*priceGrid
				.setIsQUR(qurFlag.equalsIgnoreCase(ApplicationConstants.CONST_STRING_FALSE) ? ApplicationConstants.CONST_BOOLEAN_FALSE
						: ApplicationConstants.CONST_BOOLEAN_TRUE);*/
		if(!isBasePrice){
			if(criterias.contains("Product Option")){
				priceGrid.setServiceCharge("Optional");
			}else{
				priceGrid.setServiceCharge(ApplicationConstants.CONST_STRING_SERVICECHARGE);
			}
			
			priceGrid.setUpchargeType(upChargeType);
			priceGrid.setUpchargeUsageType(upchargeUsageType);
			//UpchargeUsageType
		}
		priceGrid.setIsBasePrice(isBasePrice);
		priceGrid.setSequence(sequence);
		List<Price> listOfPrice = null;
		//if (!priceGrid.getIsQUR()) {
			listOfPrice = getPrices(prices, quantity, discountCodes,currency,isQUR);
		//} else {
		//	listOfPrice = new ArrayList<Price>();
		//}
		priceGrid.setPrices(listOfPrice);
		if (criterias != null && !criterias.isEmpty()) {
			configuration = getConfigurations(criterias+":"+priceName_Desc,optnype);//because over here pricename & criteria value is same
		}
		priceGrid.setPriceConfigurations(configuration);
		existingPriceGrid.add(priceGrid);
		}catch(Exception e){
			_LOGGER.error("Error while processing PriceGrid: "+e.getMessage());
		}
		return existingPriceGrid;

	
		
	}
	public static List<Price> getPrices(String[] prices, String[] quantity, String discount,String currency,String isQUR) {

		List<Price> listOfPrices = new ArrayList<Price>();
		try{
			int tempLen=prices.length;
			StringBuilder tempv = new StringBuilder() ;
			for (int i = 1; i <=tempLen; i++) {
				tempv.append(discount);
				tempv=tempv.append(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
			}
			
			discount=tempv.toString();
		for (int PriceNumber = 0, sequenceNum = 1; PriceNumber < prices.length && PriceNumber < quantity.length
				      && PriceNumber < discount.length(); PriceNumber++, sequenceNum++) {

			Price price = new Price();
			PriceUnit priceUnit = new PriceUnit();
			String temp[]=discount.split(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
			price.setSequence(sequenceNum);
			try {
				price.setQty(Integer.valueOf(quantity[PriceNumber].replace(".0","")));
			} catch (NumberFormatException nfe) {
				price.setQty(ApplicationConstants.CONST_NUMBER_ZERO);
			}
			price.setPrice(prices[PriceNumber]);
			price.setDiscountCode(temp[PriceNumber]);
			priceUnit
					.setItemsPerUnit(ApplicationConstants.CONST_STRING_VALUE_ONE);
			price.setPriceUnit(priceUnit);
			price.setCurrency(currency);
			price
			.setIsQUR(isQUR.equalsIgnoreCase(ApplicationConstants.CONST_STRING_FALSE) ? ApplicationConstants.CONST_BOOLEAN_FALSE
					: ApplicationConstants.CONST_BOOLEAN_TRUE);
			listOfPrices.add(price); 
		}
		}
		catch (Exception e) {
			_LOGGER.error("Error while processing PriceGrid Prices: "+e.getMessage());
		}
		return listOfPrices;
	}
	
	public static List<PriceConfiguration> getConfigurations(String criterias,String optionName) {
		List<PriceConfiguration> priceConfiguration = new ArrayList<PriceConfiguration>();
		String[] config = null;
		PriceConfiguration configs = null;
		try{
		if (criterias
				.contains(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID)) {
			//criterias=criterias.split(criterias.lastIndexOf(":"));
			/*if(criterias.contains("Additional Color, Addiotnal Location")){
				criterias=criterias.replace(":Additional Color, Addiotnal Location", "");
				
			}*/
			String[] configuraions = criterias
					.split(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
			for (String criteria : configuraions) {
				PriceConfiguration configuraion = new PriceConfiguration();
				config = criteria.split(ApplicationConstants.CONST_DELIMITER_COLON);
				String criteriaValue = LookupData.getCriteriaValue(config[0]);
				configuraion.setCriteria(criteriaValue);
				if (config[1].contains(ApplicationConstants.CONST_STRING_COMMA_SEP)) {
					String[] values = config[1].split(ApplicationConstants.CONST_STRING_COMMA_SEP);
					for (String Value : values) {
						configs = new PriceConfiguration();
						configs.setCriteria(criteriaValue);
						configs.setValue(Arrays.asList((Object) Value));
						/*if(criteriaValue.equals("Add bottom board")){
						configs.setOptionName("Optional Bottom Board");
						}*/
						if (!StringUtils.isEmpty(optionName)) {
							configs.setOptionName(optionName);
						}
						priceConfiguration.add(configs);
					}
				} else {
					configs = new PriceConfiguration();
					configs.setCriteria(criteriaValue);
					configs.setValue(Arrays.asList((Object) config[1]));
					if (!StringUtils.isEmpty(optionName)) {
						configs.setOptionName(optionName);
					}
					priceConfiguration.add(configs);
				}
			}

		} else {
			configs = new PriceConfiguration();
			config = criterias.split(ApplicationConstants.CONST_DELIMITER_COLON);
			//String criteriaValue = LookupData.getCriteriaValue(config[0]);
			configs.setCriteria(config[0]);
			configs.setValue(Arrays.asList((Object) config[1]));
			/*if(config[1].equals("Add bottom board")){
				configs.setOptionName("Optional Bottom Board");
				}*/
			if (!StringUtils.isEmpty(optionName)) {
				configs.setOptionName(optionName);
			}
			priceConfiguration.add(configs);
		}
		}catch(Exception e){
			_LOGGER.error("Error while processing PriceGrid PriceConfiguration: "+e.getMessage());
		}
		return priceConfiguration;
	}
	
	
	
	// is qur
}

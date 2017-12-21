package parser.beaconPromotions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import parser.tomaxusa.TomaxPriceGridParser;

import com.a4tech.product.model.Price;
import com.a4tech.product.model.PriceConfiguration;
import com.a4tech.product.model.PriceGrid;
import com.a4tech.product.model.PriceUnit;
import com.a4tech.util.ApplicationConstants;
import com.a4tech.util.CommonUtility;
import com.a4tech.util.LookupData;

public class BeaconProPriceGridParser {

	private Logger              _LOGGER       =  Logger.getLogger(TomaxPriceGridParser.class);
    public List<PriceGrid> getPriceGrids(String listOfPrices, String listOfQuan, String discountCodes,String currency, String priceInclude,
			 boolean isBasePrice,String qurFlag, String priceName, 
			 String criterias,Integer sequence,String serviceCharge,String upChargeType,String upchargeUsageType,String optionName,
			 List<PriceGrid> existingPriceGrid) 
				{
		try{
			if(CollectionUtils.isEmpty(existingPriceGrid)){
				existingPriceGrid=new ArrayList<PriceGrid>();
				sequence = 1;
			}else{
				sequence = existingPriceGrid.size()+1;
			}
			
			
		//Integer sequence = 1;
		List<PriceConfiguration> configuration = null;
		PriceGrid priceGrid = new PriceGrid();
		String[] prices = listOfPrices
				.split(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
		String[] quantity = listOfQuan
				.split(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
		
		priceGrid.setCurrency(currency);
		String tempDesc=priceName;
		if(tempDesc.contains("@@@@@")){
			String strTempp[]=tempDesc.split("@@@@@");
			priceGrid.setDescription(strTempp[1]);
		}else{
			priceGrid.setDescription(priceName);
		}
		priceGrid.setPriceIncludes(priceInclude);
		priceGrid
				.setIsQUR(qurFlag.equalsIgnoreCase(ApplicationConstants.CONST_STRING_FALSE) ? ApplicationConstants.CONST_BOOLEAN_FALSE
						: ApplicationConstants.CONST_BOOLEAN_TRUE);
		if(!isBasePrice){
			priceGrid.setServiceCharge(ApplicationConstants.CONST_STRING_SERVICECHARGE);
			priceGrid.setUpchargeType(upChargeType);
			priceGrid.setUpchargeUsageType(upchargeUsageType);
			
			//UpchargeUsageType
		}
		
		
		priceGrid.setIsBasePrice(isBasePrice);
		priceGrid.setSequence(sequence);
		List<Price> listOfPrice = null;
		if (!priceGrid.getIsQUR()) {
			listOfPrice = getPrices(prices, quantity, discountCodes);
		} else {
			listOfPrice = new ArrayList<Price>();
		}
		priceGrid.setPrices(listOfPrice);
		if (criterias != null && !criterias.isEmpty()) {
			configuration = getConfigurations(criterias+":"+priceName,optionName);//because over here pricename & criteria value is same
		}
		priceGrid.setPriceConfigurations(configuration);
		existingPriceGrid.add(priceGrid);
		}catch(Exception e){
			_LOGGER.error("Error while processing PriceGrid: "+e.getMessage());
		}
		return existingPriceGrid;

	}

		public List<Price> getPrices(String[] prices, String[] quantity, String discount) {

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
				listOfPrices.add(price); 
			}
			}
			catch (Exception e) {
				_LOGGER.error("Error while processing PriceGrid Prices: "+e.getMessage());
			}
			return listOfPrices;
		}
		
		public List<PriceConfiguration> getConfigurations(String criterias,String optionName) {
			List<PriceConfiguration> priceConfiguration = new ArrayList<PriceConfiguration>();
			String[] config = null;
			PriceConfiguration configs = null;
			try{
			if (criterias
					.contains(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID)) {
				String[] configuraions = criterias
						.split(ApplicationConstants.PRICE_SPLITTER_BASE_PRICEGRID);
				for (String criteria : configuraions) {
					PriceConfiguration configuraion = new PriceConfiguration();
					config = criteria.split(ApplicationConstants.CONST_DELIMITER_COLON);
					String criteriaValue = LookupData.getCriteriaValue(config[0]);
					configuraion.setCriteria(criteriaValue);
					if(!StringUtils.isEmpty(optionName))
					{
						configs.setOptionName(optionName);
					}
					if (config[1].contains(ApplicationConstants.CONST_STRING_COMMA_SEP)) {
						String[] values = config[1].split(ApplicationConstants.CONST_STRING_COMMA_SEP);
						for (String Value : values) {
							configs = new PriceConfiguration();
							configs.setCriteria(criteriaValue);
							configs.setValue(Arrays.asList((Object) Value));
							priceConfiguration.add(configs);
						}
					} else {
						if(!StringUtils.isEmpty(optionName))
						{
							configs.setOptionName(optionName);
						}
						configs = new PriceConfiguration();
						configs.setCriteria(criteriaValue);
						configs.setValue(Arrays.asList((Object) config[1]));
						priceConfiguration.add(configs);
					}
				}

			} else {
				configs = new PriceConfiguration();
				config = criterias.split(ApplicationConstants.CONST_DELIMITER_COLON);
				//String criteriaValue = LookupData.getCriteriaValue(config[0]);
				configs.setCriteria(config[0]);
				configs.setValue(Arrays.asList((Object) config[1]));
				if(!StringUtils.isEmpty(optionName))
				{
					configs.setOptionName(optionName);
				}
				priceConfiguration.add(configs);
			}
			}catch(Exception e){
				_LOGGER.error("Error while processing PriceGrid PriceConfiguration: "+e.getMessage());
			}
			return priceConfiguration;
		}

		
		public List<PriceGrid> getPriceGridsQur( ) 
		{
			List<PriceGrid> newPriceGrid=new ArrayList<PriceGrid>();
			try{
				Integer sequence = 1;
				//List<PriceConfiguration> configuration = null;
				PriceGrid priceGrid = new PriceGrid();
				priceGrid.setIsBasePrice(true);
				priceGrid.setIsQUR(ApplicationConstants.CONST_BOOLEAN_TRUE);
				priceGrid.setDescription(ApplicationConstants.CONST_STRING_EMPTY);
				priceGrid.setPriceIncludes(ApplicationConstants.CONST_STRING_EMPTY);
				priceGrid.setSequence(sequence);
				priceGrid.setCurrency(ApplicationConstants.CONST_STRING_CURRENCY_USD);
				List<Price>	listOfPrice = new ArrayList<Price>();
				priceGrid.setPrices(listOfPrice);
				//priceGrid.setPriceConfigurations(configuration);
				newPriceGrid.add(priceGrid);
		}catch(Exception e){
			_LOGGER.error("Error while processing PriceGrid: "+e.getMessage());
		}
			_LOGGER.info("PriceGrid Processed");
			return newPriceGrid;
	}
}
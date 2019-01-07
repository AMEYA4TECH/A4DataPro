package com.a4tech.v5.excel;

import java.util.List;

import com.a4tech.product.model.Price;
import com.a4tech.product.model.PriceGrid;

public abstract class PriceServicee {
	public abstract List<Price> getPrices(String[] prices, String[] quantity , String[] discount,String currency ,String isQUR);
	public abstract List<PriceGrid> getPriceGrids( boolean isBasePrice,String priceName_Desc,String priceInclude,Integer sequence,
			String listOfPrices, String listOfQuan, String discountCodes,
			String criterias,String serviceCharge,String upChargeType,String upchargeUsageType,String optnype,
			List<PriceGrid> existingPriceGrid);
	// is qur
}

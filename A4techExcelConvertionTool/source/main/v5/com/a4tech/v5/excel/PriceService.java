package com.a4tech.v5.excel;

import java.util.List;

import com.a4tech.v5.product.model.Price;

public interface PriceService {
	public List<Price> getPrices(String[] prices, String[] quantity , String[] discount);
}

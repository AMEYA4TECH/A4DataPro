package com.a4tech.excel;

import java.util.List;

import com.a4tech.product.model.Price;

public interface PriceService {
	public List<Price> getPrices(String[] prices, String[] quantity , String[] discount);
}

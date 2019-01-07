package com.a4tech.v5.product.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtherSize {
    
	@JsonProperty("Values")
	private List<Value> values;
	
	public List<Value> getValues() {
		return values;
	}

	public void setValues(List<Value> values) {
		this.values = values;
	}

}

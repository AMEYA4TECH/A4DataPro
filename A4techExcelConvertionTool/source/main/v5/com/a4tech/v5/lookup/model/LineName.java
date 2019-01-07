package com.a4tech.v5.lookup.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LineName {
	@JsonProperty("LineNames")
	private List<String> lineNames = null;

	public List<String> getLineNames() {
		return lineNames;
	}

	public void setLineNames(List<String> lineNames) {
		this.lineNames = lineNames;
	}

	
}

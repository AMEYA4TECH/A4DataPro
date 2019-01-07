package com.a4tech.v5.v2.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Catalog {
	@JsonProperty("CatalogName")
	private String catalogName;
	@JsonProperty("CatalogPage")
	private String catalogPage;
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	public String getCatalogPage() {
		return catalogPage;
	}
	public void setCatalogPage(String catalogPage) {
		this.catalogPage = catalogPage;
	}
	
}

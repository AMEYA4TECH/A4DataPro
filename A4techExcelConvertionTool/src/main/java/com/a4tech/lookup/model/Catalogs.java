package com.a4tech.lookup.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Catalogs {
	@JsonProperty("Catalogs")
  private List<Catalog> listOfCatalog;

	public List<Catalog> getListOfCatalog() {
	return listOfCatalog;
	}

	public void setListOfCatalog(List<Catalog> listOfCatalog) {
		this.listOfCatalog = listOfCatalog;
	}
}

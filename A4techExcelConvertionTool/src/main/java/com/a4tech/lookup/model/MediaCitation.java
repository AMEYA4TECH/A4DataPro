package com.a4tech.lookup.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class MediaCitation {
	
	@JsonProperty("MediaCitations")
    private List<String> mediaCitations = new ArrayList<String>();


	public List<String> getListOfMediaCitation() {
		return mediaCitations;
	}

	public void setListOfMediaCitation(List<String> listOfMediaCitation) {
		this.mediaCitations = listOfMediaCitation;
	}
	

}

package com.a4tech.core.errors;

import java.util.List;

import com.a4tech.v5.core.errors.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessageList {
	
	@JsonProperty("Errors")
	private List<ErrorMessage> errors;
	@JsonProperty("SuccessMessage")
    private List<String> successMsg;
    
	public List<String> getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(List<String> successMsg) {
		this.successMsg = successMsg;
	}

	public List<ErrorMessage> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorMessage> errors) {
		this.errors = errors;
	}

}

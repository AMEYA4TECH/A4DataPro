package com.a4tech.v5.product.service.postImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.a4tech.v5.core.errors.ErrorMessage;
import com.a4tech.v5.core.errors.ErrorMessageList;
import com.a4tech.v5.core.model.ExternalAPIResponse;
import com.a4tech.v5.product.dao.service.ProductDao;
import com.a4tech.v5.product.model.Product;
import com.a4tech.v5.util.CommonUtility;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.a4tech.v5.product.dao.service.ProductDao;


public class PostServiceImpl  {//implements PostService

	private Logger _LOGGER = Logger.getLogger(getClass());
	
    private com.a4tech.v5.product.dao.service.ProductDao productDaoObjV5;
	private RestTemplate restTemplate;
	private String postApiURL ;
	private String getProductUrl;
	@Autowired
	@Qualifier("objectMapperV5")
	ObjectMapper objectMapperV5;
	/*@Autowired
	private Environment environment;*/
	//@Override
	public int postProduct(String authTokens, Product product,int asiNumber ,int batchId, String environmentType) throws IOException {
        String sheetName = "";
        String xid = product.getExternalProductId();
		try {
			_LOGGER.info("Product Data : "
					+ objectMapperV5.writeValueAsString(product));
			if(environmentType.contains(":")){
				String[] sheetNameAndEnvType = CommonUtility.getValuesOfArray(environmentType, ":");
				environmentType = sheetNameAndEnvType[0];
				sheetName = sheetNameAndEnvType[1];	
				xid = xid+":"+sheetName;
			}
			if(environmentType.equals("Sand")){
				postApiURL = "https://sandbox-productservice.asicentral.com/api/v5/product/";
			} else{
				postApiURL = "https://productservice.asicentral.com/api/v5/product/";
			}
			//postApiURL = environment.getProperty(environmentType+".product.post.URL");
			HttpHeaders headers = new HttpHeaders();
			headers.add("AuthToken", authTokens);
			headers.add("Content-Type", "application/json ; charset=utf-8");
			/*_LOGGER.info("Product Data : "
					+ mapperObj.writeValueAsString(product));*/
			
			_LOGGER.info("Product Data : "
			+ objectMapperV5.writeValueAsString(product));
			
			HttpEntity<Product> requestEntity = new HttpEntity<Product>(
					product, headers);
			ResponseEntity<ExternalAPIResponse> response = restTemplate
					.exchange(postApiURL, HttpMethod.POST, requestEntity,
							ExternalAPIResponse.class);
			_LOGGER.info("Result : " + response);
			return 1;
		} catch (HttpClientErrorException hce) {
			String response = hce.getResponseBodyAsString();
			try {
				_LOGGER.info("ASI Error Response Msg :" + response);
				ErrorMessageList apiResponse = objectMapperV5.readValue(response,
						ErrorMessageList.class);
				
				productDaoObjV5.save(apiResponse.getErrors(),xid, asiNumber, batchId);
				boolean isFailProduct = isFailProduct(apiResponse.getErrors());
				if(isFailProduct){
					return 5;
				}
				/*List<ErrorMessage> errors = apiResponse.getErrors();
				if(errors.contains("Your product could not be saved")){
					return 5;
				}*/
			} catch (IOException e) {
				// TODO Auto-generated catch block
				_LOGGER.error("unable to connect External API System:"
						+ e.getCause());
				return -1;
			}
			return 0;

		} catch (HttpServerErrorException serverEx) {
			String serverResponse = "";
			boolean flag=false;
			ErrorMessageList apiResponse = new ErrorMessageList();
			List<ErrorMessage> errorsList=new ArrayList<ErrorMessage>();
			ErrorMessage errorMessageObj=new ErrorMessage();
			if(!serverEx.getResponseBodyAsString().isEmpty()){
				serverResponse = serverEx.getResponseBodyAsString();
			}else{
				serverResponse=serverEx.getMessage();
				
				
				errorMessageObj.setMessage(serverResponse);
				errorMessageObj.setReason(serverResponse);
				errorsList.add(errorMessageObj);
				apiResponse.setErrors(errorsList);
				flag=true;
			}
			
			try {
				if(!flag){
				apiResponse = objectMapperV5.readValue(serverResponse,
						ErrorMessageList.class);
				}
				
				productDaoObjV5.save(apiResponse.getErrors(),xid, asiNumber, batchId);
				_LOGGER.info("Error JSON:" + apiResponse);

			} catch (JsonParseException | JsonMappingException e) {
				_LOGGER.error("Error while reading ErrorMessageList object to JSON:"
						+ e.getCause());
			}
			return 0;

		} catch (Exception hce) {
			_LOGGER.error("Exception while posting product to ExternalAPI", hce);
			String serverErrorMsg = hce.getMessage();
			if (serverErrorMsg != null
					&& serverErrorMsg
							.equalsIgnoreCase("500 Internal Server Error")) {
				_LOGGER.info("internal server msg received from ExternalAPI ");
				ErrorMessageList errorMsgList = CommonUtility
						.responseconvertErrorMessageList(serverErrorMsg);
				productDaoObjV5.save(errorMsgList.getErrors(),xid, asiNumber, batchId);
				return 0;
			} else if (hce.getCause() != null) {
				String errorMsg = hce.getCause().toString();
				if (errorMsg.contains("java.net.UnknownHostException")
						|| errorMsg.contains("java.net.NoRouteToHostException")
						|| errorMsg.contains("java.net.SocketTimeoutException")) {
					ErrorMessageList errorMsgList = CommonUtility
							.responseconvertErrorMessageList(errorMsg);
					productDaoObjV5.save(errorMsgList.getErrors(),xid, asiNumber, batchId);
					return 0;
				}
			} else {

			}
			return -1;
		}

	}
	//@Override
	public Product getProduct(String authToken,String productId, String environmentType){
	try{
		if(environmentType.equals("Sand")){
			getProductUrl = "https://sandbox-productservice.asicentral.com/api/v5/product/{xid}";
		} else{
			getProductUrl = "https://productservice.asicentral.com/api/v5/product/{xid}";
		}
		//postApiURL = environment.getProperty(environmentType+".product.get.URL");
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("AuthToken", authToken);
		 headers.add("Content-Type", "application/json");
		 HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
	     ResponseEntity<Product> getResponse  =	restTemplate.exchange(getProductUrl, HttpMethod.GET, requestEntity, 
	    		                                                                            Product.class ,productId);
	     Product product = getResponse.getBody();
	    _LOGGER.info("Product from API::"
				+ objectMapperV5.writeValueAsString(product));
	    return product;  
	  }catch(HttpClientErrorException hce){
		_LOGGER.error("HttpClientError ::"+hce.getMessage());
	  }catch(Exception e){
		_LOGGER.error("Exception ::"+e.getMessage());
	 }
	return null;
  }
	
	public int deleteProduct(String authTokens, String productId,int asiNumber ,int batchId,String environmentType) throws IOException {

		try {
			String deleteProductUrl="https://sandbox-productservice.asicentral.com/api/v5/product/";
			
			if(environmentType.equals("Prod")){
				deleteProductUrl="https://productservice.asicentral.com/api/v5/product/";
			}
			//productId="3558-55093AWDD";
			 HttpHeaders headers = new HttpHeaders();
			 headers.add("Accept",  "application/json");
			 headers.add("Content-Type", "application/json");
			 headers.add("AuthToken", authTokens);
			
			 HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		     ResponseEntity<ErrorMessageList> getResponse  =	restTemplate.exchange(deleteProductUrl+productId, HttpMethod.DELETE, requestEntity, 
		    		 ErrorMessageList.class);
				_LOGGER.info("Result : " + getResponse);
		    _LOGGER.info("Delete Response from ASI::"
					+ objectMapperV5.writeValueAsString(getResponse));
		     return 1; 
		  } catch (HttpClientErrorException hce) {
			String response = hce.getResponseBodyAsString();
			try {
				_LOGGER.info("ASI Error Response Msg :" + response);
				ErrorMessageList apiResponse = objectMapperV5.readValue(response,
						ErrorMessageList.class);
				productDaoObjV5.save(apiResponse.getErrors(),
						productId, asiNumber, batchId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				_LOGGER.error("unable to connect External API System:"
						+ e.getCause());
				return -1;
			}
			return 0;

		} catch (HttpServerErrorException serverEx) {
			String serverResponse = serverEx.getResponseBodyAsString();

			ErrorMessageList apiResponse;
			try {
				apiResponse = objectMapperV5.readValue(serverResponse,
						ErrorMessageList.class);
				productDaoObjV5.save(apiResponse.getErrors(),
						productId, asiNumber, batchId);
				_LOGGER.info("Error JSON:" + apiResponse);

			} catch (JsonParseException | JsonMappingException e) {
				_LOGGER.error("Error while reading ErrorMessageList object to JSON:"
						+ e.getCause());
			}
			return 0;

		} catch (Exception hce) {
			_LOGGER.error("Exception while posting product to ExternalAPI", hce);
			String serverErrorMsg = hce.getMessage();
			if (serverErrorMsg != null
					&& serverErrorMsg
							.equalsIgnoreCase("500 Internal Server Error")) {
				_LOGGER.info("internal server msg received from ExternalAPI ");
				ErrorMessageList errorMsgList = CommonUtility
						.responseconvertErrorMessageList(serverErrorMsg);
				productDaoObjV5.save(errorMsgList.getErrors(),
						productId, asiNumber, batchId);
				return 0;
			} else if (hce.getCause() != null) {
				String errorMsg = hce.getCause().toString();
				if (errorMsg.contains("java.net.UnknownHostException")
						|| errorMsg.contains("java.net.NoRouteToHostException")
						|| errorMsg.contains("java.net.SocketTimeoutException")) {
					ErrorMessageList errorMsgList = CommonUtility
							.responseconvertErrorMessageList(errorMsg);
					productDaoObjV5.save(errorMsgList.getErrors(),
							productId, asiNumber, batchId);
					return 0;
				}
			} else {

			}
			return -1;
		}

	}
 private boolean isFailProduct(List<ErrorMessage> errors){
	 for (ErrorMessage errorMessage : errors) {
			if(errorMessage.getReason() == null){
				continue;
			}
				String tempMessage=errorMessage.getMessage();
			if (tempMessage.contains("Your product could not be saved")
					|| tempMessage.toLowerCase().contains("internal server error")) {
					return true;
				}
		}
	 return false;
 }
 
 
	
	public com.a4tech.v5.product.dao.service.ProductDao getProductDaoObjV5() {
	return productDaoObjV5;
}
public void setProductDaoObjV5(
		com.a4tech.v5.product.dao.service.ProductDao productDaoObjV5) {
	this.productDaoObjV5 = productDaoObjV5;
}
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public String getPostApiURL() {
		return postApiURL;
	}

	public void setPostApiURL(String postApiURL) {
		this.postApiURL = postApiURL;
	}
	
	public String getGetProductUrl() {
		return getProductUrl;
	}

	public void setGetProductUrl(String getProductUrl) {
		this.getProductUrl = getProductUrl;
	}
	public ObjectMapper getObjectMapperV5() {
		return objectMapperV5;
	}
	public void setObjectMapperV5(ObjectMapper objectMapperV5) {
		this.objectMapperV5 = objectMapperV5;
	}
	
	
}

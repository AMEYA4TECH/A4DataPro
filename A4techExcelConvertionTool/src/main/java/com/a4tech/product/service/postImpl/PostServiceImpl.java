package com.a4tech.product.service.postImpl;



import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.a4tech.core.dao.Error;
import com.a4tech.core.dao.ProductLoggerDAO;
import com.a4tech.core.model.ExternalAPIResponse;
import com.a4tech.product.model.Product;
import com.a4tech.product.service.LoginService;
import com.a4tech.product.service.PostService;
import com.a4tech.service.loginImpl.LoginServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PostServiceImpl {
		  
	    private Logger              _LOGGER              = Logger.getLogger(getClass());
	    private Product product1;
		 
	    @Autowired
	    private ProductLoggerDAO  logDAO;
	    
		public Product getProduct1() {
			return product1;
		}
		@Required
		public void setProduct1(Product product1) {
			_LOGGER.info("inside setter method");
			this.product1 = product1;
			_LOGGER.info("after setter method"+product1 + "ending");
		}

		private RestTemplate restTemplate = new RestTemplate();
		private String postApiURL = "https://sandbox-productservice.asicentral.com/v3/product/";
		private static String authToken = null;
		
		LoginServiceImpl loginServiceImp = new LoginServiceImpl();
		LoginService loginService;
		
		public int postProduct(String authTokens, Product product,String companyId) {
			
			com.a4tech.core.dao.Product productLogObj=new com.a4tech.core.dao.Product();
			Error errorObj =new Error();
			List<Error> errorList=new ArrayList<Error>();
			try {
				if(product1 != null){
					//_LOGGER.info("after setter method"+product1);
				}else{
					//_LOGGER.info("object not created>>"+product1);
				}
	            
	            if(authToken == null || authToken.isEmpty()){
	            	//authToken = loginServiceImp.doLogin();
	            	//authToken = loginService.doLogin();
	            }
	        	HttpHeaders headers = new HttpHeaders();
	        	headers.add("AuthToken", authTokens);
	        	//headers.setContentType(MediaType.APPLICATION_JSON);
	        	headers .add("Content-Type", "application/json ; charset=utf-8");
	            ObjectMapper mapper1 = new ObjectMapper();
	           _LOGGER.info("Product Data : " + mapper1.writeValueAsString(product));
	            HttpEntity<Product> requestEntity = new HttpEntity<Product>(product, headers);
	            productLogObj.setCompanyId(companyId);
	            productLogObj.setProductNumber(product.getExternalProductId());
	            
	            productLogObj.setProductStatus(true);
	            productLogObj.setErrors(errorList);
	            
	            ResponseEntity<ExternalAPIResponse> response = restTemplate.exchange(postApiURL, HttpMethod.POST, requestEntity, ExternalAPIResponse.class);
	            _LOGGER.info("Result : " + response);
	            
	            logDAO.save(productLogObj,errorList );
	            return 1;
	        }catch (HttpClientErrorException hce) {
	            _LOGGER.error("Exception while posting product to ASI", hce);
	            productLogObj.setProductStatus(false);
	            hce.getMessage();
	            return 0;
	        } catch (Exception e) {
	            e.printStackTrace();
	            _LOGGER.error("Exception while posting product to ASI", e);
	            productLogObj.setProductStatus(false);
	              return 0;
	        }
	    }
		public LoginService getLoginService() {
			return loginService;
		}
		@Autowired
		public void setLoginService(LoginService loginService) {
			this.loginService = loginService;
		}
				public RestTemplate getRestTemplateClass() {
			return restTemplate;
		}

		public void setRestTemplateClass(RestTemplate restTemplateClass) {
			this.restTemplate = restTemplateClass;
		}

		public String getPostApiURL() {
			return postApiURL;
		}

		public void setPostApiURL(String postApiURL) {
			this.postApiURL = postApiURL;
		}
		
	}

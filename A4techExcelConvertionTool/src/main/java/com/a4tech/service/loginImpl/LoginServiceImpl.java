package com.a4tech.service.loginImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.a4tech.core.model.AccessBean;
import com.a4tech.product.service.ILoginService;
import com.a4tech.util.ApplicationConstants;
import com.fasterxml.jackson.databind.ObjectMapper;


public class LoginServiceImpl implements ILoginService {

	private Logger _LOGGER = Logger.getLogger(getClass());
	
	private static final Integer AUTH_TOKEN_EXPIRE_MINUTES = 65;
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SZ";
    
	AccessBean accessBean = null;
	private RestTemplate restTemplate ;
	@Autowired
	@Qualifier("objectMapper")
	ObjectMapper objectMapper;
	private String loginApiURL ;
	/*@Autowired
	private Environment environment;*/

	@Override
	public String doLogin(String asiNumber,String userName,String password, String environmentType) {
		//ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
       
        AccessBean accessBean = null;
        try {
        	if(environmentType.equals("Sand")){
        		loginApiURL = "https://sandbox-productservice.asicentral.com/api/v4/Login";
        	} else{
        		loginApiURL = "https://productservice.asicentral.com/api/v4/Login";
        	}
        	//loginApiURL = environment.getProperty(environmentType+".login.endpoint.URL");
        	HttpHeaders header = new HttpHeaders();
            header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            header.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> body = new HashMap<String, String>();     
          
            body.put(ApplicationConstants.CONST_ASI_NUMBER, asiNumber);
            body.put(ApplicationConstants.CONST_USERNAME,userName);
            body.put(ApplicationConstants.CONST_PASSWORD, password);
            HttpEntity<?> httpEntity = new HttpEntity<Object>(body, header);
            _LOGGER.info("Hitting ASI endpoint for Login");
            ResponseEntity<AccessBean> response = restTemplate.exchange(loginApiURL, HttpMethod.POST, httpEntity, AccessBean.class);
            accessBean = new AccessBean();
            accessBean = response.getBody();
            _LOGGER.info("ASI response recieved");
            accessBean.setAccessToken(accessBean.getAccessToken());

            Calendar expireTimeCalculator = Calendar.getInstance();
            expireTimeCalculator.add(Calendar.MINUTE, AUTH_TOKEN_EXPIRE_MINUTES);
            
            DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
            accessBean.setTokenExpirationTime(dateFormatter.format(expireTimeCalculator.getTime()));
            
            
        
        }catch(HttpClientErrorException hce){
        	_LOGGER.error("Error while doing login "+hce.getMessage());
        	if(hce.getMessage().equalsIgnoreCase("400 Bad Request")){
        		return "unAuthorized";	
        	}
        } catch (Exception e) {
        	
        	_LOGGER.error("Error while doing login "+e.getMessage(),e);
        }
        if(accessBean != null){
        	return accessBean.getAccessToken();
        }
        return null;
	}

	public String getLoginApiURL() {
		return loginApiURL;
	}

	public void setLoginApiURL(String loginApiURL) {
		this.loginApiURL = loginApiURL;
	}
	
	 public RestTemplate getRestTemplate() {
			return restTemplate;
		}

		public void setRestTemplate(RestTemplate restTemplate) {
			this.restTemplate = restTemplate;
		}


}

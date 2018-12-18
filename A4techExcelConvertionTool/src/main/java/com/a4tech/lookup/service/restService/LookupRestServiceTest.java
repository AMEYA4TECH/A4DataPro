package com.a4tech.lookup.service.restService;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/application-config.xml")
public class LookupRestServiceTest {

	@Autowired
	private RestTemplate restTemplate ;
	
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	LookupRestService lookObj;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	//@Test(expected = Exception.class)
	/*@Test
	public void testGetOriginLookupUrl() throws Exception {
		//fail("Not yet implemented");	
		thrown.expect(Exception.class);
		
		List<String> str=lookObj.getFobPoints("as",null);
		//System.out.println(str);
		//assertEquals(12, str.size());
		
	}*/
	
	//@Test(expected = Exception.class)
		@Test
		public void testGetOriginLookupUrl() throws Exception {
			//fail("Not yet implemented");	
			thrown.expect(Exception.class);
			
			List<String> str=lookObj.getFobPoints("as",null);
			//System.out.println(str);
			//assertEquals(12, str.size());
			
		}

}

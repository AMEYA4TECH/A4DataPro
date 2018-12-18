import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.a4tech.controller.FileUpload;
import com.a4tech.service.loginImpl.LoginServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/application-config.xml")
public class FileLoginTest extends FileUpload {
	@Autowired
	private RestTemplate restTemplate ;
	
	@Autowired
	LoginServiceImpl loginServiceImpl;
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

	@Test
	public void testdoLogin() {
		
		String asiNumber="304581";
		String userName="a430458api";
		String password="password1";
		String environmentType="Sand";
		//fail("Not yet implemented");
		
		String str=loginServiceImpl.doLogin(asiNumber, userName, password, environmentType);
		assertEquals("200",str );
	}

}

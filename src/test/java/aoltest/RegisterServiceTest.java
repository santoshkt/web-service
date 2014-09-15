package aoltest;

import static org.junit.Assert.*;
import javax.servlet.http.HttpServletRequest;
import org.mockito.*;

import aol.RegisterService;
import org.apache.commons.lang3.RandomStringUtils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RegisterServiceTest {

	public RegisterServiceTest() {
	}

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
	public void testProcessRegistration1() {

		HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
		Mockito.when(mockRequest.getParameter("firstname")).thenReturn(
				"xx");
		Mockito.when(mockRequest.getParameter("lastname")).thenReturn(
				"zz");
		Mockito.when(mockRequest.getParameter("email")).thenReturn(
				RandomStringUtils.randomAlphabetic(20) + "@example.com");
		Mockito.when(mockRequest.getParameter("pwd")).thenReturn("abcdef");
		Mockito.when(mockRequest.getParameter("cpwd")).thenReturn("abcdef");
		
		String result = RegisterService.processRegistration(mockRequest);
		assertEquals("success", result);

	}

	@Test
	public void testProcessRegistration2() {

		HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
		
		Mockito.when(mockRequest.getParameter("firstname")).thenReturn("Santosh");
		Mockito.when(mockRequest.getParameter("lastname")).thenReturn("Tadikonda");
		Mockito.when(mockRequest.getParameter("email")).thenReturn("stadikon@gmu.edu");
		Mockito.when(mockRequest.getParameter("pwd")).thenReturn("santosh");
		Mockito.when(mockRequest.getParameter("cpwd")).thenReturn("santosh");
		
		String result = RegisterService.processRegistration(mockRequest);
		
		if(result == null || result.equals("success"))
			fail("An error message is expected but received a null or success.");
		
	}
	
	@Test
	public void testProcessRegistration3(){
		
		String result = RegisterService.processRegistration(null);
		assertNull(result);
	
	}

}

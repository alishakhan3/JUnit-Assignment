package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class UnitTesting {
	
	@Test
	public void testSendEmail() {
		EmailService es=mock(EmailService.class);
		user u=new user(es);
		
		String email="test@test.com";
		String subject="Test Subject";
		String body="Test Body";
		
		u.sendEmail(email,subject,body);
		
		verify(es, times(1)).sendEmail(email, subject, body);
		
	}
	
	@Test 
	public void testValidEmail() {
		assertTrue(isValidEmail("test@test.com"));
		assertTrue(isValidEmail("test-example@gmail.com"));
		assertTrue(isValidEmail("test.example@test-example.co"));
	}
	
	@Test
	public void testInvalidEmail() {
		assertFalse(isValidEmail("?test@test.com"), "Invalid Email Type");
		assertFalse(isValidEmail("testgmail.com"), "Invalid Email Type");
		assertFalse(isValidEmail("test@_com"), "Invalid Email Type");
	}
	
	private boolean isValidEmail(String email) {
		String regex="^[a-zA-Z0-9_.-]+@[a-zA-Z0-9.-]+$";
		Pattern p=Pattern.compile(regex);
		Matcher m=p.matcher(email);
		return m.matches();
	}
	
	@Test
	public void testPasswordLength() {
		assertTrue(isValidLength("123456"));
		assertTrue(isValidLength("12345678901234567890"));
		assertFalse(isValidLength("12345"), "Password length should be at least 6 characters");
		assertFalse(isValidLength("123456789012345678901"), "Password length should not be more than 20 characters");
	}
	
	private boolean isValidLength(String pwd) {
		return pwd.length()>=6 && pwd.length()<=20;
	}
	

}

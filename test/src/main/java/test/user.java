package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class user{
	public Map<String, String> users = new HashMap<>();
	public EmailService es;

	public user(EmailService es) { this.es=es;}
	 

	public boolean registerUser(String email, String pwd) {
		boolean flag=false;
		int code=isValidLength(pwd);
		if(code==1) {
			System.out.println("Password length should be at least 6 characters");
		}
		else if(code==2) {
			System.out.println("Password length should not be more than 20 characters");
		}
		else if(code==0) {
			users.put(email, pwd);
			System.out.println("Registration Successful");
			flag=true;
		}
		return flag;
	}// new user
	
	public int isValidLength(String pwd) {
		if(pwd.length()<6) {
			return 1;
		}
		else if(pwd.length()>20) {
			return 2;
		}
		return 0;
	}
	
	public boolean isValidEmail(String email) {
		String regex="^[a-zA-Z0-9_.-]+@[a-zA-Z0-9.-]+$";
		Pattern p=Pattern.compile(regex);
		Matcher m=p.matcher(email);
		return m.matches();
	}

	public boolean userExists(String email) {
		if (users.containsKey(email)) {
			return true;
		}
		return false;
	}// checking for Forgot Password

	public boolean newPassword(String email, String pwd) {
		boolean flag=false;
		int code=isValidLength(pwd);
		if(code==1) {
			System.out.println("Password length should be at least 6 characters");
		}
		else if(code==2) {
			System.out.println("Password length should not be more than 20 characters");
		}
		else if(code==0) {
			users.replace(email, pwd);
			flag=true;
		}
		return flag;
		
	}// updated Password

	public int login(String email, String pwd) {
		if (!users.containsKey(email)) {
			return 1;
		}
		else if (!(users.containsKey(email) && users.get(email).equals(pwd))) {
			return 2;
		}
		return 0;
	}// logging in
	
	
	public void sendEmail(String to, String subject, String body) {
		es.sendEmail(to, subject, body);
	}
	
	
	public static void main(String args[]) {
		
		EmailService es=new EmailService() {
			@Override
			public void sendEmail(String to, String subject, String body) {
				System.out.println("Email sent to "+to+ "\nSubject: "+subject+"\nBody: "+body);
			}

		};
		user u = new user(es);
		String email="";
		String pwd="";
		Scanner sc = new Scanner(System.in);

		System.out.println("Registering User\n");

		System.out.println("Enter an email: ");
		email = sc.nextLine();
		if(!u.isValidEmail(email))
		{
			System.out.println("Invalid Email");
			System.exit(0);
		}

		System.out.println("Enter a password: ");
		pwd = sc.nextLine();

		if(!u.registerUser(email, pwd))
			System.exit(0);
		
		System.out.println("\nForgot Password\n");

		System.out.println("Enter your email: ");
		email = sc.nextLine();

		if (u.userExists(email)) {
			System.out.println("\nEmail Sent Successfully\n");

			System.out.println("Enter your new password: ");
			pwd = sc.nextLine();

			if(!u.newPassword(email, pwd))
				System.exit(0);

		} else {
			System.out.println("\nInvalid Email");
		}

		System.out.println("\nLog Into Your Account\n");

		System.out.println("Enter your email: ");
		email = sc.nextLine();

		System.out.println("Enter your password: ");
		pwd = sc.nextLine();
		
		int code=u.login(email, pwd);

		if (code==0) {
			System.out.println("\nLogin Successful");
		} else if (code==1){
			System.out.println("\nInvalid email");
		}
		else if (code==2){
			System.out.println("\nInvalid password");
		}

		sc.close();
	}// main
}// class

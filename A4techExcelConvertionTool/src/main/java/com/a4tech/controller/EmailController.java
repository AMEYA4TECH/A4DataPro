package com.a4tech.controller;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
@RequestMapping("/sendEmail.do")
public class EmailController {
 
    @Autowired
    private JavaMailSender mailSender;
     
    @RequestMapping(method = RequestMethod.POST)
    public String doSendEmail(HttpServletRequest request,Model model) {
       
         
    	final String username = "ameymorea4tech@gmail.com";
		final String password = "AmeyMoreA4Tech11";
		String filename=(String) request.getSession().getAttribute("asiNumber");
		 filename = filename+".txt";
		String filepath = "D:\\A4 ESPUpdate\\ErrorFiles\\";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {
			if(!StringUtils.isEmpty(filepath)){
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("ameymorea4tech@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("amey.more@a4technology.com"));
			message.setSubject("Product Error Batch File");
			//message.setText("Kindly find the attached " +filename +"Product Error File");
			  // Create the message part
	         BodyPart messageBodyPart = new MimeBodyPart();

	         // Now set the actual message
	         messageBodyPart.setText("Kindly find the attached " +filename +" Product Error File"
	        		 + "\n\n\n\n Note: This is a System Generated Message Kindly Do not reply back");
	        
	         Multipart multipart = new MimeMultipart();
	         multipart.addBodyPart(messageBodyPart);
	         messageBodyPart = new MimeBodyPart();
	         String filenameAttach = filepath+ filename;
	         DataSource source = new FileDataSource(filenameAttach);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName(filename);
	         multipart.addBodyPart(messageBodyPart);
	        message.setContent(multipart);
			Transport.send(message);
			 
			System.out.println("Done");
			
			}
		}catch(Exception e){
			 
		}
		
		 SimpleMailMessage message = new SimpleMailMessage();
		  message.setFrom("amey.more@a4technology.com");
		  message.setTo("sharvari.patil@a4technology.com");
		  message.setSubject("Test Mail"); 
		  message.setText("Hi"); 
		  //sending message  
		  //mailSender.send(message); 
		  model.addAttribute("successmsg", "Email Sent SuccessFully");
        return "success";    
        }

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
    
    
    
}
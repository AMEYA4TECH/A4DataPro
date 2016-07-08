package com.a4tech.core.errors;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.a4tech.core.model.FileBean;
import com.a4tech.product.dao.service.ProductDao;

public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger _LOGGER = Logger.getLogger(ProductDao.class);
//,HttpSession httpSession
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String filename=(String) request.getSession().getAttribute("asiNumber");
		 filename = filename+".txt";
		String filepath = "D:\\A4 ESPUpdate\\ErrorFiles\\";
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ filename + "\"");
		FileInputStream fileInputStream = new FileInputStream(filepath
				+ filename);

		int i;
		while ((i = fileInputStream.read()) != -1) {
			out.write(i);
		}
		fileInputStream.close();
		out.close();
		
		  	final String username = "ameymorea4tech@gmail.com";
			final String password = "AmeyMoreA4Tech11";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session1 = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });

			try {
				if(!StringUtils.isEmpty(filepath)){
				Message message = new MimeMessage(session1);
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
				//Transport.send(message);
				_LOGGER.info("Email Send SuccessFully ");
				System.out.println("Done");
				
				}
			}catch(Exception e){
				_LOGGER.error("Error while sending email" +e.toString());
			}
	}
}
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
			
	}
}
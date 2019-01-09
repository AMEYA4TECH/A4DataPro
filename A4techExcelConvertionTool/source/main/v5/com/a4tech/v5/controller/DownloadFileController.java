package com.a4tech.v5.controller;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.a4tech.v5.controller.DownloadFileController;
import com.a4tech.v5.util.ApplicationConstants;

@Controller
@RequestMapping("/downloadFile")
public class DownloadFileController {
	
	private static Logger _LOGGER = Logger.getLogger(DownloadFileController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public void gerErrorLogFile(HttpServletRequest request,
			HttpServletResponse response,Model model) throws ServletException, FileNotFoundException {

		response.setContentType("text/html");
		String batchId=(String) request.getSession().getAttribute("batchId"); 
		String fileName= batchId+ApplicationConstants.CONST_STRING_DOT_TXT;
		Path filePath = Paths.get(ApplicationConstants.CONST_STRING_DOWNLOAD_FILE_PATH,fileName);
			if (Files.exists(filePath)) {
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader("Content-Disposition", "attachment; filename=\""
						+ fileName + "\"");
				try {
					Files.copy(filePath, response.getOutputStream());
					response.getOutputStream().flush();
				} catch (IOException e) {
					_LOGGER.error("unable to read error log file: "+e.getMessage());
				}
			} else{
				throw new FileNotFoundException("file " + fileName + " was not found");
			}
			
	}
}

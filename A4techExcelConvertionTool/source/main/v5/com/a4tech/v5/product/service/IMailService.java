package com.a4tech.v5.product.service;

public interface IMailService {
	public boolean 	sendMail(String supplierId,int batchId);
	public void 	fileProcessStart(String body,String subject);
	public void 	supplierLoginFailureMail(String supplierNo,String body ,String subject);
	public void     fileProcessCompleted(String body,String subject,int batchNo);
	public void     ftpServerFailure();
	public void     fileProcessFail(String fileName);
	public void     numberOfFileProcess(String subject,String fileNames);

}

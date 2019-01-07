package com.a4tech.v5.ftp.service;

import java.io.File;

public interface FtpService {
  public boolean uploadFile(File mFile,String asiNumber,String environmentType);
  public void downloadFiles();
  //public void filesMove();
}

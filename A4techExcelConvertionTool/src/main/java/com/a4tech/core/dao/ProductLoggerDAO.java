package com.a4tech.core.dao;

import java.util.List;



public interface ProductLoggerDAO {
	public void save(Product productLoggerObj,List<Error> errorList);
}

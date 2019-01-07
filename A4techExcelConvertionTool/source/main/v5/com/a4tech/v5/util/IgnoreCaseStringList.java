package com.a4tech.v5.util;

import java.util.ArrayList;

public class IgnoreCaseStringList extends ArrayList<String>{
	@Override
	public boolean contains(Object o) {
		String paramStr = (String)o;
        for (String name : this) {
            if (paramStr.equalsIgnoreCase(name)) 
            	return true;
        }
        return false;		
	}
 }
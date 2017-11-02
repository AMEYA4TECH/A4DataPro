package com.a4tech.controller;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;

public class TestClass {
public static void main(String[] args) {
	int i=StringUtils.indexOfAny("blocks", new String[]{
			"stick","compact","extended","bag","tripod","closed","lens","clip","diameter","star","arrow","case","highlighter",
			"sides","blade","all","open","blocks","tall","available","folded","small","tie","strings",
			"cap","rain","gauge","bottle","approx","barrel","head","k3a","m3a","ties","with","widest","scraper","assembled","round"
	});
	System.out.println(i);
		
	HashSet<String> tp=new HashSet<String>();
		tp.add("AA");
		tp.add("BB");
		tp.add("CC");
		tp.add("DD");

for (String string : tp) {
	if(string.equals("CC")){
		
	}else{
	System.out.println(string);
}}

HashMap<String, String> sizeDimMap=new HashMap<String, String>();
	sizeDimMap.put("w", "Width");
	sizeDimMap.put("h", "Width");
	sizeDimMap.put("d", "Width");
	sizeDimMap.put("l", "Width");
	sizeDimMap.put("dia", "Width");
	sizeDimMap.put("diameter", "Width");
	
	}





	}
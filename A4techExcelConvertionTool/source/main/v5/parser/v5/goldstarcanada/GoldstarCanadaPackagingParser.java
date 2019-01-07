package parser.v5.goldstarcanada;

import java.util.ArrayList;
import java.util.List;

import com.a4tech.product.model.Packaging;
import com.a4tech.util.ApplicationConstants;

public class GoldstarCanadaPackagingParser {
	
	public List<Packaging> getPackageValues(String packageValues){
		List<Packaging> listOfPackage = new ArrayList<Packaging>();
		Packaging packaging = null;
		if(packageValues.contains(ApplicationConstants.CONST_DELIMITER_COMMA)){
			String[] packValues = packageValues.split(ApplicationConstants.CONST_DELIMITER_COMMA);
			for (String pack : packValues) {
				packaging = new Packaging();
			   packaging.setName(pack);
			   listOfPackage.add(packaging);
			}
		}else{
			packaging = new Packaging();
			   packaging.setName(packageValues);
			   listOfPackage.add(packaging);
		}
		return listOfPackage;
		
	}

}

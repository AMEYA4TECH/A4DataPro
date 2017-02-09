package parser.highcaliberline;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class HighCaliberConstants {
	public static Map<String, String> HCLCOLOR_MAP =new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);//new HashMap<String, String>();
	private static Logger _LOGGER = Logger.getLogger(HighCaliberConstants.class);

 static SessionFactory sessionFactory;
	//public static Map<String, String> COLOR_MAP =new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);//new HashMap<String, String>();
	public static Map<String, String>  Colormap(){
		
		Session session = null;
		
		try{
			 session = sessionFactory.openSession();
		Criteria colorCri = session.createCriteria(HighCalColorEntity.class);
		  List<HighCalColorEntity> colorList = colorCri.list();
	        for(HighCalColorEntity tempColor : colorList){
	        	if(StringUtils.isEmpty(tempColor.getColorvalue())){
	        		continue;
	        	}
	        	String strTemp[]={};
	        	strTemp= tempColor.getColorvalue().split("===");
	        	HCLCOLOR_MAP.put(strTemp[0].trim(),strTemp[1].trim());
	        }
	}catch(Exception ex){
		_LOGGER.error("Error in dao block for highcaliber colors: "+ex.getMessage());
	}finally{
		if(session !=null){
			try{
				session.close();
			}catch(Exception ex){
				_LOGGER.warn("Error while close session object in highcaliber color class");
			}
			}
		}
		
		return HCLCOLOR_MAP;
	
	}
	
	
	
	public static Map<String, String> getHCLCOLOR_MAP() {
		if(CollectionUtils.isEmpty(HCLCOLOR_MAP)){
			HCLCOLOR_MAP=Colormap();
		}
		
		
		return HCLCOLOR_MAP;
	}
	public static void setHCLCOLOR_MAP(Map<String, String> hCLCOLOR_MAP) {
		HCLCOLOR_MAP = hCLCOLOR_MAP;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
/*static {
	HCLCOLOR_MAP.put("Yellow","Medium Yellow");
	HCLCOLOR_MAP.put("Forest Green","Dark Green");
	HCLCOLOR_MAP.put("Teal","Medium Green");
	HCLCOLOR_MAP.put("Navy Blue","Dark Blue");
	HCLCOLOR_MAP.put("Reflex Blue","Medium Blue");
	HCLCOLOR_MAP.put("Royal Blue","Bright Blue");
	HCLCOLOR_MAP.put("Blue","Medium Blue");
	HCLCOLOR_MAP.put("Burgundy","Dark Red");
	HCLCOLOR_MAP.put("Purple","Medium Purple");
	HCLCOLOR_MAP.put("Brown","Medium Brown");
	HCLCOLOR_MAP.put("Gray","Medium Gray");
	HCLCOLOR_MAP.put("Black","Medium Black");
	HCLCOLOR_MAP.put("White","Medium White");
	HCLCOLOR_MAP.put("Green","Medium Green");
	HCLCOLOR_MAP.put("Red","Medium Red");
	HCLCOLOR_MAP.put("Orange","Medium Orange");
	HCLCOLOR_MAP.put("Light Blue","Light Blue");
	HCLCOLOR_MAP.put("Process Yellow","Medium Yellow");
	HCLCOLOR_MAP.put("Custom Colors","Other");
	HCLCOLOR_MAP.put("Silver","Medium Gray");
	HCLCOLOR_MAP.put("Solid White","Medium White");
	HCLCOLOR_MAP.put("Maroon","Dark Red");
	HCLCOLOR_MAP.put("Gold","Dark Yellow");
	HCLCOLOR_MAP.put("Bright Pink","Bright Pink");
	HCLCOLOR_MAP.put("Bright Green","Bright Green");
	HCLCOLOR_MAP.put("Pink","Medium Pink");
	HCLCOLOR_MAP.put("Bright Orange","Bright Orange");
	HCLCOLOR_MAP.put("Deep Royal","Bright Blue");
	HCLCOLOR_MAP.put("Royal","Bright Blue");
	HCLCOLOR_MAP.put("Navy","Dark Blue");
	HCLCOLOR_MAP.put("Charcoal","Medium Gray");
	HCLCOLOR_MAP.put("Assorted (250 Min)","Assorted");
	HCLCOLOR_MAP.put("Translucent Black","Medium Black");
	HCLCOLOR_MAP.put("Clear","Clear");
	HCLCOLOR_MAP.put("Matte Black","Medium Black");
	HCLCOLOR_MAP.put("Matte Blue","Medium Blue");
	HCLCOLOR_MAP.put("Matte Red","Medium Red");
	HCLCOLOR_MAP.put("Matte White","Medium White");
	HCLCOLOR_MAP.put("Frosted Clear","Clear");
	HCLCOLOR_MAP.put("Key Lime Cream","Light Green");
	HCLCOLOR_MAP.put("Lime","Bright Green");
	HCLCOLOR_MAP.put("Fuchsia","Medium Pink");
	HCLCOLOR_MAP.put("Dark Green","Dark Green");
	HCLCOLOR_MAP.put("Metallic Silver","Silver Metal");
	HCLCOLOR_MAP.put("Translucent Green","Clear Green");
	HCLCOLOR_MAP.put("Smoke","Dark Gray");
	HCLCOLOR_MAP.put("Assorted","Assorted");
	HCLCOLOR_MAP.put("Lime Green","Bright Green");
	HCLCOLOR_MAP.put("Translucent Blue","Clear Blue");
	HCLCOLOR_MAP.put("Translucent Pink","Light Pink");
	HCLCOLOR_MAP.put("Solid Orange","Medium Orange");
	HCLCOLOR_MAP.put("Solid Yellow","Medium Yellow");
	HCLCOLOR_MAP.put("Translucent Purple","Clear Purple");
	HCLCOLOR_MAP.put("Translucent Red","Clear Red");
	HCLCOLOR_MAP.put("Solid Black","Medium Black");
	HCLCOLOR_MAP.put("Chrome","Chrome Metal");
	HCLCOLOR_MAP.put("Translucent Orange","Clear Orange");
	HCLCOLOR_MAP.put("Khaki","Medium Brown");
	HCLCOLOR_MAP.put("Solid Blue","Medium Blue");
	HCLCOLOR_MAP.put("Coral Pink","Medium Pink");
	HCLCOLOR_MAP.put("Blue-Black","Medium Blue");
	HCLCOLOR_MAP.put("Solid Green","Medium Green");
	HCLCOLOR_MAP.put("Solid Purple","Medium Purple");
	HCLCOLOR_MAP.put("Translucent Yellow","Clear Yellow");
	HCLCOLOR_MAP.put("Translucent Smoke","Clear Black");
	HCLCOLOR_MAP.put("Stainless Steel","Silver Metal");
	HCLCOLOR_MAP.put("Matte Finish Gun Metal","Medium Gray");
	HCLCOLOR_MAP.put("White-Black","Multi Color");
	HCLCOLOR_MAP.put("White-Blue","Multi Color");
	HCLCOLOR_MAP.put("White-Red","Multi Color");
	HCLCOLOR_MAP.put("Cyan Blue","Light Blue");
	HCLCOLOR_MAP.put("Light Green","Light Green");
	HCLCOLOR_MAP.put("Plum","Medium Purple");
	HCLCOLOR_MAP.put("Eco Green","Medium Green");
	HCLCOLOR_MAP.put("Solid Red","Medium Red");
	HCLCOLOR_MAP.put("Neon Yellow","Bright Yellow");
	HCLCOLOR_MAP.put("Frosted","Clear");
	HCLCOLOR_MAP.put("Solid Eco Green","Bright Green");
	HCLCOLOR_MAP.put("Transparent Blue","Medium Blue");
	HCLCOLOR_MAP.put("Transparent Black","Medium Black");
	HCLCOLOR_MAP.put("Pearl-Red","Multi Color");
	HCLCOLOR_MAP.put("Pearl-Green","Medium Green");
	HCLCOLOR_MAP.put("Pearl-Black","Multi Color");
	HCLCOLOR_MAP.put("Fushia","Light Purple");
	HCLCOLOR_MAP.put("Translucent Smoke Gray","Medium Gray");
	HCLCOLOR_MAP.put("Grey","Medium Gray");
	HCLCOLOR_MAP.put("Shiny Black","Medium Black");
	HCLCOLOR_MAP.put("Shiny Blue","Medium Blue");
	HCLCOLOR_MAP.put("Shiny Green","Medium Green");
	HCLCOLOR_MAP.put("Shiny Orange","Medium Orange");
	HCLCOLOR_MAP.put("Shiny Red","Medium Red");
	HCLCOLOR_MAP.put("Custom","Other");
	HCLCOLOR_MAP.put("Neon Green","Bright Green");
	HCLCOLOR_MAP.put("Copper","Medium Brown");
	HCLCOLOR_MAP.put("Neon Orange","Bright Orange");
	HCLCOLOR_MAP.put("Gunmetal Gray","Medium Gray");
	HCLCOLOR_MAP.put("Black / Grey","Medium Black");
	HCLCOLOR_MAP.put("Blue / Grey","Medium Blue");
	HCLCOLOR_MAP.put("White / Black","Medium White");
	HCLCOLOR_MAP.put("White / Blue","Medium White");
	HCLCOLOR_MAP.put("White / Grey","Medium White");
	HCLCOLOR_MAP.put("White / Orange","Medium White");
	HCLCOLOR_MAP.put("White / Pink","Medium White");
	}*/
}
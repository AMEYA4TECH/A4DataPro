package parser.v5.evansManufacturing;

import java.util.HashMap;
import java.util.Map;

public class EveanManufColorAndImprintMethodMapping {
public static Map<String, String> COLOR_MAP =new HashMap<String, String>();
public static Map<String,String>   IMPRINT_METHOD_GROUP = new HashMap<>();	
	static{
		IMPRINT_METHOD_GROUP.put("Imprint Method","Group Name");
		IMPRINT_METHOD_GROUP.put("Screen printed","Silkscreen=Screen printed");
		IMPRINT_METHOD_GROUP.put("Pad Printed","Pad Print=Pad Printed");
		IMPRINT_METHOD_GROUP.put("Laser engraved","Laser Engraved=Laser engraved");
		IMPRINT_METHOD_GROUP.put("Standard 1 Color Imprint- Heat Transfer","Heat Transfer=Heat Transfer");
		IMPRINT_METHOD_GROUP.put("Dome printed","Printed=Dome printed");
		IMPRINT_METHOD_GROUP.put("Clear Wrap Printed","Printed=Clear Wrap Printed");
		IMPRINT_METHOD_GROUP.put("Laser Engraved or Pad Printed","Laser Engraved=Laser engraved, Silkscreen=Pad Printed");
		IMPRINT_METHOD_GROUP.put("Digital direct printed","Printed=Digital direct printed");
		IMPRINT_METHOD_GROUP.put("Digital Direct","Printed=Digital Direct");
		IMPRINT_METHOD_GROUP.put("Heat Transfer","Heat Transfer=Heat Transfer");
		IMPRINT_METHOD_GROUP.put("Dura-panel","Printed=Dura-Panel");
		IMPRINT_METHOD_GROUP.put("Full color wrap insert","Full Color=Full color wrap insert");
		IMPRINT_METHOD_GROUP.put("Embroidered patch","Embroidered=Embroidered patch");
		IMPRINT_METHOD_GROUP.put("Laser Engraved or Screen Printed","Laser Engraved=Laser engraved, Silkscreen=Screen Printed");
		IMPRINT_METHOD_GROUP.put("Screen Printed Barrel","Silkscreen=Screen Printed Barrel");
		IMPRINT_METHOD_GROUP.put("Heat Transfer - 2 color","Heat Transfer=Heat Transfer");
		IMPRINT_METHOD_GROUP.put("Pad Printed Sandwich Keeper","Pad Print=Pad Printed,ImprintLocation=Sandwich Keeper");
		IMPRINT_METHOD_GROUP.put("Screen Printed Reuseable Sandwich & Snack Bag","Silkscreen=screen printed,ImprintLocation=Reuseable Sandwich & Snack Bag");
		IMPRINT_METHOD_GROUP.put("Pad Printed Clip","Pad Print=Pad Printed Clip");
		IMPRINT_METHOD_GROUP.put("Screen Printed. 8275 Cinch Up Bag","Silkscreen=Screen Printed,ImprintLocation=8275 Cinch Up Bag");
		IMPRINT_METHOD_GROUP.put("Screen Printed Clutch Lunch Bag","Silkscreen=Screen Printed,ImprintLocation=Clutch Lunch Bag");
		IMPRINT_METHOD_GROUP.put("Screen Printed - Pen","Silkscreen=Screen Printed,ImprintLocation=Pen");
		IMPRINT_METHOD_GROUP.put("Pad printed eraser","Pad Print=Pad Printed,ImprintLocation=eraser");
		IMPRINT_METHOD_GROUP.put("Laser Engraved","Laser Engraved=Laser Engraved");
		IMPRINT_METHOD_GROUP.put("Laser or Pad Printed Clip","Laser Engraved=Laser Clip,Pad Print=Pad Printed Clip");
		IMPRINT_METHOD_GROUP.put("Pad printed","Pad Print=Pad Printed");

		COLOR_MAP.put("Name","Color Group");
		COLOR_MAP.put("Yellow","Medium Yellow");
		COLOR_MAP.put("Neon Pink","Bright Pink");
		COLOR_MAP.put("Neon Green","Bright Green");
		COLOR_MAP.put("Blue","Medium Blue");
		COLOR_MAP.put("Custom","Other");
		COLOR_MAP.put("Black","Medium Black");
		COLOR_MAP.put("Red","Medium Red");
		COLOR_MAP.put("Royal Blue","Bright Blue");
		COLOR_MAP.put("Lime","Bright Green");
		COLOR_MAP.put("Purple","Medium Purple");
		COLOR_MAP.put("Metallic Dark Red","Metallic Red");
		COLOR_MAP.put("Metallic Cobalt Blue","Metallic Blue");
		COLOR_MAP.put("Metallic Purple","Metallic Purple");
		COLOR_MAP.put("Metallic Lime","Metallic Green");
		COLOR_MAP.put("Metallic Gunmetal","Metallic Gray");
		COLOR_MAP.put("Metallic Copper","Metallic Orange");
		COLOR_MAP.put("White","Medium White");
		COLOR_MAP.put("Translucent Blue","Clear Blue");
		COLOR_MAP.put("Translucent Red","Clear Red");
		COLOR_MAP.put("Translucent Green","Clear Green");
		COLOR_MAP.put("Translucent Lime","Clear Green");
		COLOR_MAP.put("Translucent Orange","Clear Orange");
		COLOR_MAP.put("Translucent Purple","Clear Purple");
		COLOR_MAP.put("Translucent Frost","Clear");
		COLOR_MAP.put("Clear","Clear");
		COLOR_MAP.put("Pink","Medium Pink");
		COLOR_MAP.put("Orange","Medium Orange");
		COLOR_MAP.put("Gold","Dark Yellow");
		COLOR_MAP.put("Burgundy","Dark Red");
		COLOR_MAP.put("Hunter Green","Dark Green");
		COLOR_MAP.put("Navy","Dark Blue");
		COLOR_MAP.put("Tan","Light Brown");
		COLOR_MAP.put("Sky Blue","Light Blue");
		COLOR_MAP.put("Gray","Medium Gray");
		COLOR_MAP.put("Green","Medium Green");
		COLOR_MAP.put("Dark Blue","Dark Blue");
		COLOR_MAP.put("Dark Green","Dark Green");
		COLOR_MAP.put("Transparent Pink","Clear Pink");
		COLOR_MAP.put("Transparent Green","Clear Green");
		COLOR_MAP.put("Transparent Blue","Clear Blue");
		COLOR_MAP.put("Transparent Purple","Clear Purple");
		COLOR_MAP.put("Transparent Orange","Clear Orange");
		COLOR_MAP.put("Transparent Red","Clear Red");
		COLOR_MAP.put("Transparent Smoke","Clear Gray");
		COLOR_MAP.put("Forest Green","Medium Green");
		COLOR_MAP.put("Frost","Clear");
		COLOR_MAP.put("Natural Wood","Medium Brown");
		COLOR_MAP.put("Assorted","Assorted");
		COLOR_MAP.put("Silver","Medium Gray");
		COLOR_MAP.put("Metallic Blue","Metallic Blue");
		COLOR_MAP.put("Neon Blue","Bright Blue");
		COLOR_MAP.put("Metallic Pink","Metallic Pink");
		COLOR_MAP.put("Metallic Red","Metallic Red");
		COLOR_MAP.put("Metallic Yellow","Metallic Yellow");
		COLOR_MAP.put("Metallic Green","Metallic Green");
		COLOR_MAP.put("Metallic Brown","Metallic Brown");
		COLOR_MAP.put("Metallic Black","Metallic Black");
		COLOR_MAP.put("Metallic Aqua","Metallic Blue");
		COLOR_MAP.put("Translucent Aqua","Clear Blue");
		COLOR_MAP.put("Translucent Smoke","Clear Gray");
		COLOR_MAP.put("Translucent Clear","Clear");
		COLOR_MAP.put("Translucent Yellow","Clear Yellow");
		COLOR_MAP.put("Frost Blue","Clear Blue");
		COLOR_MAP.put("Frost Red","Clear Red");
		COLOR_MAP.put("Frost Orange","Clear Orange");
		COLOR_MAP.put("Frost Lime","Clear Green");
		COLOR_MAP.put("Frost Smoke","Clear Gray");
		COLOR_MAP.put("Translucent Pink","Clear Pink");
		COLOR_MAP.put("Neon Yellow","Bright Yellow");
		COLOR_MAP.put("Neon Orange","Bright Orange");
		COLOR_MAP.put("Natural","DARK WHITE");
		COLOR_MAP.put("Frost Purple","Medium Purple");
		COLOR_MAP.put("Process Blue","Medium Blue");
		COLOR_MAP.put("Brown","Medium Brown");
		COLOR_MAP.put("Teal Blue","Medium Green");
		COLOR_MAP.put("Translucent Ice Blue","Clear Blue");
		COLOR_MAP.put("Translucent Mint","Clear Green");
		COLOR_MAP.put("Light Gray","Light Gray");
		COLOR_MAP.put("Celery Green","Light Green");
		COLOR_MAP.put("Gunmetal","Medium Gray");
		COLOR_MAP.put("Salsa","Medium Red");
		COLOR_MAP.put("Azul","Medium Blue");
		COLOR_MAP.put("Tortilla","Light Brown");
		COLOR_MAP.put("Sun Yellow","Medium Yellow");
		COLOR_MAP.put("Frost Green","Medium Green");
		COLOR_MAP.put("Frost Light Blue","Light Blue");
		COLOR_MAP.put("Transparent Light Blue","Clear Blue");
		COLOR_MAP.put("Metallic Silver","Metallic Gray");
		COLOR_MAP.put("Metallic Light Blue","Metallic Blue");
		COLOR_MAP.put("Metallic Charcoal","Metallic Gray");
		COLOR_MAP.put("Metallic Wine","Metallic Red");
		COLOR_MAP.put("Light Blue","Light Blue");
		COLOR_MAP.put("Kelly Green","Bright Green");
		COLOR_MAP.put("Charcoal","Medium Gray");
		COLOR_MAP.put("Transparent Lime","Clear Green");
		COLOR_MAP.put("Frost Pink","Clear Pink");
		COLOR_MAP.put("Frost Royal Blue","Medium Blue");
		COLOR_MAP.put("Camouflage","Multi Color");
		COLOR_MAP.put("Granite","Medium Gray");
		COLOR_MAP.put("Blue Marble","Medium Blue");
		COLOR_MAP.put("Red Marble","Medium Red");
		COLOR_MAP.put("Gray Marble","Medium Gray");
		COLOR_MAP.put("Light Blue Marble","Light Blue");
		COLOR_MAP.put("Harvest Green","Medium Green");
		COLOR_MAP.put("Fuchsia","Bright Pink");
		COLOR_MAP.put("Black Checker","Medium Black");
		COLOR_MAP.put("White Checker","Medium White");
		COLOR_MAP.put("White Zebra","Medium White");
		COLOR_MAP.put("Cream","Light Yellow");
		COLOR_MAP.put("Maroon","Dark Red");
		COLOR_MAP.put("Teal","Medium Green");
		COLOR_MAP.put("Rainbow","Multi Color");
		COLOR_MAP.put("Copper","Medium Orange");
		COLOR_MAP.put("Navy Blue","Dark Blue");
		COLOR_MAP.put("Army Green","Medium Green");
		COLOR_MAP.put("Translucent Magenta","Clear Pink");
		COLOR_MAP.put("Metallic Olive Green","Metallic Green");
		COLOR_MAP.put("Metallic Steel Blue","Metallic Blue");
		COLOR_MAP.put("Metallic Teal","Medium Green");
		COLOR_MAP.put("Metallic Berry","Metallic Pink");
		COLOR_MAP.put("Metallic Emerald Green","Metallic Green");
		COLOR_MAP.put("Frosted Red","Medium Red");
		COLOR_MAP.put("Frosted Blue","Clear Blue");
		COLOR_MAP.put("Frosted Purple","Clear Purple");
		COLOR_MAP.put("Frosted Green","Clear Green");
		COLOR_MAP.put("Aqua","Light Blue");
		COLOR_MAP.put("Metallic White","Metallic White");
		COLOR_MAP.put("Outdoor Camo","Multi Color");
		COLOR_MAP.put("Woodland Camo","Multi Color");
		COLOR_MAP.put("Neon Lime","Bright Green");
		COLOR_MAP.put("Khaki","Light Brown");
		COLOR_MAP.put("Metallic Champagne","Metallic Yellow");
		COLOR_MAP.put("Transparent  Light Blue","Clear Blue");
		COLOR_MAP.put("Metallic Rose Gold","Metallic Pink");
		COLOR_MAP.put("Stainless Steel","Medium Gray");
		COLOR_MAP.put("Metallic Orange","Metallic Orange");
		COLOR_MAP.put("Emerald Green","Medium Green");
		COLOR_MAP.put("Matte Metallic Blue","Metallic Blue");
		COLOR_MAP.put("Matte Metallic Gunmetal","Metallic Gray");
		COLOR_MAP.put("Matte Metallic Dark Red","Metallic Red");
		COLOR_MAP.put("Matte Metallic Green","Metallic Green");
		COLOR_MAP.put("Matte Black","Medium Black");
		COLOR_MAP.put("Matte White","Medium White");
		COLOR_MAP.put("Matte Light Blue","Light Blue");
		COLOR_MAP.put("Matte Lime","Bright Green");
		COLOR_MAP.put("Blaze Orange","Bright Orange");
		COLOR_MAP.put("Full Color","Multi Color");
		COLOR_MAP.put("Peach","Light Orange");
		COLOR_MAP.put("Cork","Light Brown");
		COLOR_MAP.put("Olive Green","Medium Green");
		COLOR_MAP.put("Bamboo","Light Brown");
		COLOR_MAP.put("Chrome","Chrome Metal");
		COLOR_MAP.put("Smoke","Medium Gray");
		COLOR_MAP.put("Matte Blue","Medium Blue");
		COLOR_MAP.put("Caribbean Blue","Medium Blue");
		COLOR_MAP.put("Rainforest Green","Medium Green");
		COLOR_MAP.put("Desert Fire","Medium Yellow");
		COLOR_MAP.put("Neon Purple","Medium Purple");
		COLOR_MAP.put("Lime Green","Light Green");
		COLOR_MAP.put("Cobalt Blue","Medium Blue");
		COLOR_MAP.put("Translucent Mocha","CLEAR BROWN");
		COLOR_MAP.put("Dark Red","Medium Red");
		COLOR_MAP.put("Translucent Fuchsia","Light Red");		
	}
	
	public static String getColorGroup(String colorName){
		String colorGroup = COLOR_MAP.get(colorName);
		return colorGroup == null ?"Other": colorGroup;
	}
	public static String getImprintMethodGroup(String imprintMethodVal){
		String imprintMethodGroup = IMPRINT_METHOD_GROUP.get(imprintMethodVal);
		return imprintMethodGroup == null ?"Other": imprintMethodGroup;
	}
	
	
}

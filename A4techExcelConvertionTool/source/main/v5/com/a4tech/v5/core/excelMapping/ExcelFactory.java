package com.a4tech.v5.core.excelMapping;



import com.a4tech.v5.excel.service.IExcelParser;
import com.a4tech.v5.sage.product.mapping.SageProductsExcelMapping;
import com.a4tech.v5.supplier.mapper.RadiousMapping;
import com.a4tech.v5.supplier.mapper.SunGraphixMapping;
import com.a4tech.v5.supplier.mapper.SunGraphixRevisedMapping;
import com.a4tech.v5.supplier.mapper.WBTIndustriesMapper;
//import com.a4tech.v5.supplier.mapper.CutterBuckExcelMapping;

public class ExcelFactory {
	//private AdspecProductsExcelMapping 		adspecMapping;
	//private KlProductsExcelMapping 			klMapping;
	private SageProductsExcelMapping 		sageExcelMappingV5;
	 private WBTIndustriesMapper	            wbtIndustriesMapperV5;
	 private RadiousMapping                  radiMappingV5;	
	 private SunGraphixRevisedMapping 				sunGraphixMappingV5;
	/*private DCProductsExcelMapping 			dcProductExcelMapping;
	private KukuProductsExcelMapping 		kukuProductsExcelMapping;
	private RFGLineProductExcelMapping 		rfgLineProductExcelMapping;
	private BBIProductsExcelMapping 		bbiProductsExcelMapping;
	private NewProductsExcelMapping 		newProductsExcelMapping;
	private ApparelProductsExcelMapping 	apparealExcelMapping;
	private ESPTemplateMapping 				espTemplateMapping;
	private BroberryExcelMapping 			broberryExcelMapping;
	private BestDealProductsExcelMapping 	bdProdcutsMapping;
	private RiversEndExcelMapping 			riversEndExcelMapping;
	private BambamProductExcelMapping 		bamExcelMapping;
 //   private CutterBuckExcelMapping 			cbExcelMapping;
    private CrystalDExcelMapping 			cdExcelMapping;
    private GoldstarCanadaExcelMapping 		goldcanadaExcelMapping;
    private MilestoneExcelMapping 			milestoneExcelMapping;
	//private HighCaliberLineExcelMapping 	hcLineExcelMapping;
	private WholeSaleExcelMapping 			wholeSaleExcelMapping;
	private PrimeLineExcelMapping 			primeLineExcelMapping;
	private GoldBondExcelMapping  			goldBandExcelMapping;
	private PSLMapping 						pslMapping;
	private TomaxUsaMapping 				tomaxUsaMapping;
	private ProGolfMapping 					proGolfMapping;
	private DacassoMapping 					dacassoMapping;
	private SageRMKWorldWideMapping 		sage80289Mapping;
	private BrandwearExcelMapping 			brandwearExcelMapping;
	private BallProMapping       			ballProMapping;
	private PSLcadMapping 					pslcadMapping;
	private TwintechMapping 				twintechMapping;
	private AlfaMapping       				alfaMapping;
	private BellaCanvas                     bellaCanvasMapping;
	private HarvestIndustrialExcelMapping   harvestMapping;	
	private HighCaliberLineMappingRevised hcLineExcelMapping;
	private TowelSpecialtiesMapping        towelSpecialties;
	private BagMakersMapping 				bagMakersMapping; 
	private GillStudiosMapping 				gillStudiosMapping;
	private BlueGenerationMapping			blueGenerationMapping;
	private FITSAccessoriesMapping          fitsAccessoriesMapping;
	private BayStateMapping                 baysStateMapping;
    private MaxplusMapping                  maxplusmapping;
	private GempirepromotionsMapping        gempiresMapping;
	private EdwardsGarmentMapping 			edwardsGarmentMapping;
	private TekweldMapping 					tekweldMapping;
    private InternationlMerchMapping        merchMapping;	
    private HeadWearMapping                 headWearMapping;
    private SportCanadaExcelMapping         sportMapping;
    private SunScopeMapping                 sunScopeMapping;
    //private SunGraphixMapping 				sunGraphixMapping;
    private SunGraphixRevisedMapping        sunGraphixMapping;
    private CbMapping                       cbExeMapping;
    private PelicanGraphicMapping           pelicanGraphicMapping;
    private SportsManBagMapping             sportsManBagMapping;
    private SimplifiedsourcingMapping       simplifiedMapping;
	private SolidDimensionMapping 			solidDimensionMapping;
    private EveanManufacturingCanadaMapping eveanManufactureMapping;
    private SportUSAMapping					sportUSAMapping;
    private SportAzxCandMapping 			sportAzxCandMapping;
    private DigiSpecMapping                 digiSpecMapping;
    private DouglasBridgeMapper             douglasBridgeMapper;
	private RadiousMapping                  radiMapping;	
    private WBTIndustriesMapper	            wbtIndustriesMapper;
    private ZenithExport                    zenithMapping;
    private BloominPromotionsMapper         bloominPromotion;
     private PioneerLLCMapping 				pioneerLLCMapping;
     private TeamworkAthleticMapping        teamWorkAthleticMapper;
	  private AccessLineMapping				accessLineMapping;*/
     
    
	public  IExcelParser getExcelParserObject(String name){
		if(name.equalsIgnoreCase("sage") || name.equals("55204")){
			   return sageExcelMappingV5;
		   }else if(name.equals("96640")){
				  return wbtIndustriesMapperV5;
			  } else if(name.equalsIgnoreCase("sunGraphixMapping") || name.equals("90125")){
				  return sunGraphixMappingV5;
			  } else if(name.equals("49916")){
				  return radiMappingV5;
			  } 
		  /* if(name.equalsIgnoreCase("Apparel") || name.equals("44620")){
			   return apparealExcelMapping;
		   }else if(name.equalsIgnoreCase("kl") || name.equals("64905")){
			   return klMapping;
		   }else if(name.equalsIgnoreCase("Adspec") || name.equals("32125")){
			   return adspecMapping;
		   }else*/ 
		  /* else if(name.equalsIgnoreCase("dc") || name.equals("55205")){
			   return dcProductExcelMapping;
		   }else if(name.equalsIgnoreCase("kuku") || name.equals("65851")){
			   return kukuProductsExcelMapping;
		   }else if(name.equalsIgnoreCase("rfg") || name.equals("82283")){
			   return rfgLineProductExcelMapping;
		   }else if(name.equalsIgnoreCase("bbi") || name.equals("40445")){
			   return bbiProductsExcelMapping;
		   }else if(name.equalsIgnoreCase("espTemplate") || name.equals("91561")){
			   return espTemplateMapping;   
		   }else if(name.equalsIgnoreCase("broberry") || name.equals("42057")){
			   return broberryExcelMapping;  
		   }else if(name.equalsIgnoreCase("bestDeal") || name.equals("47791")){
			   return bdProdcutsMapping;
		   }else if(name.equalsIgnoreCase("riversend") || name.equals("82588")){
			   return riversEndExcelMapping;
		   }else if(name.equalsIgnoreCase("cbExcel") || name.equals("47965")){
			   return cbExeMappingcbExcelMapping;//old file
		   }
		   else if(name.equalsIgnoreCase("cdExcel") || name.equals("47759")){
			   return cdExcelMapping;
		   }
		   else if(name.equalsIgnoreCase("bambam") || name.equals("38228")){
			   return bamExcelMapping;
		   }
		   else if(name.equalsIgnoreCase("goldCanada") || name.equals("57711") || name.equals("73295") ){
			   return goldcanadaExcelMapping;

		   }else if(name.equalsIgnoreCase("highCaliberLine") || name.equals("43442")){
			   return hcLineExcelMapping;

		   }else if(name.equalsIgnoreCase("wholeSale") || name.equals("91284")){
			   return wholeSaleExcelMapping;
			  
		   }else if(name.equalsIgnoreCase("goldBond") || name.equals("57653")){
			   return goldBandExcelMapping;
		   }else if(name.equalsIgnoreCase("prime") || name.equals("79530")){
			   return primeLineExcelMapping;
		   }
	      else if(name.equalsIgnoreCase("psl") || name.equals("75613")){
		      return pslMapping;
	      }else if(name.equalsIgnoreCase("milestone") || name.equals("71173")){
  			   return milestoneExcelMapping;
	      } else if(name.equalsIgnoreCase("proGolf") || name.equals("79680")){
 			   return proGolfMapping;
	      } else if(name.equalsIgnoreCase("dacasso") || name.equals("48125")){
	    	  return dacassoMapping;
	      } else if(name.equalsIgnoreCase("tomaxusa") || name.equals("91435")){
			   return tomaxUsaMapping;
		  } else if(name.equalsIgnoreCase("sage80289") || name.equals("80289")){
			  return sage80289Mapping;
		  } else if(name.equalsIgnoreCase("brandwear") || name.equals("41545")){
			   return brandwearExcelMapping;
		  } else if(name.equals("ballPro") || name.equals("38120")){
			  return ballProMapping;
		  }
	      else if(name.equalsIgnoreCase("pslcad") || name.equals("90345")){
		      return pslcadMapping;
	      }
	      else if(name.equalsIgnoreCase("twintech") || name.equals("83140")){
		      return twintechMapping;
	      } else if(name.equalsIgnoreCase("alfa") || name.equals("34042")){
	    	  return alfaMapping;
	      } else if(name.equalsIgnoreCase("bellaCanvas") || name.equals("39590")){
		      return bellaCanvasMapping;

	      }  else if(name.equalsIgnoreCase("harvest") || name.equals("61670") || name.equals("71685") || name.equals("91584")){
		      return harvestMapping;//TotesFactory

	      } else if(name.equalsIgnoreCase("towelSpe") || name.equals("91605")){
	    	  return towelSpecialties;
	      }else if(name.equalsIgnoreCase("bagMakersMapping") || name.equals("37940")){
		      return bagMakersMapping;
	      }else if(name.equalsIgnoreCase("gillStudios") || name.equals("56950")){
			   return gillStudiosMapping;
		  } else if(name.equalsIgnoreCase("blueGeneration") || name.equals("40653")){
			  return blueGenerationMapping;
		  } else if(name.equalsIgnoreCase("FITSAccessories ") || name.equals("71107")){
			  return fitsAccessoriesMapping;
		  }else if(name.equalsIgnoreCase("gempiresMapping") || name.equals("55610")){
			  return gempiresMapping;
		  } else if(name.equals("38980") || name.equalsIgnoreCase("bayState")){
			  return baysStateMapping;
		  } else if(name.equalsIgnoreCase("maxplusmapping") || name.equals("69718")){
			  return maxplusmapping;
		  }else if(name.equalsIgnoreCase("edwardsGarment") || name.equals("51752")){
			   return edwardsGarmentMapping;
		  }
		  else if(name.equalsIgnoreCase("tekweld") || name.equals("90807")){
			   return tekweldMapping;
		  } else if(name.equalsIgnoreCase("internationalMerchMapping") || name.equals("62820")){
			  return merchMapping;
		  } else if(name.equalsIgnoreCase("headWearMapping") || name.equals("60282")){
			  return headWearMapping;
		  }else if(name.equalsIgnoreCase("sportMapping") || name.equals("30251")){
			  return sportAzxCandMapping;
		  }  else if(name.equalsIgnoreCase("sunScope") || name.equals("90075")){
			  return sunScopeMapping;
		  }  else if(name.equalsIgnoreCase("sunGraphixMapping") || name.equals("90125")){
			  return sunGraphixMapping;
		  } else if(name.equalsIgnoreCase("pelicanGraphics") || name.equals("76797")){
			  return pelicanGraphicMapping;
		  }else if(name.equalsIgnoreCase("sportsManBag") || name.equals("88877")){
			  return sportsManBagMapping;
		  }else if(name.equalsIgnoreCase("simplified") || name.equals("87326")|| name.equals("91597") ){
			  return simplifiedMapping;
		  } else if(name.equalsIgnoreCase("solidDimension") || name.equals("88156")){
			  return solidDimensionMapping;
		  } else if(name.equals("52841")){
			  return eveanManufactureMapping;
		  }else if(name.equals("30250")){
			  return sportUSAMapping;
		  } else if(name.equals("49716")){
			  return digiSpecMapping;
		  } else if(name.equals("49916")){
			  return radiMapping;
		  } else if(name.equals("50710")){
			  return douglasBridgeMapper;
		  } else if(name.equals("96640")){
			  return wbtIndustriesMapper;
		  }else if(name.equals("79840")){
			  return zenithMapping;
		  } else if(name.equals("40646")){
			  return bloominPromotion;
		  } else if(name.equals("76771")){
			  return pioneerLLCMapping;
		  } else if(name.equals("90673")){
			  return teamWorkAthleticMapper;
		  } else if(name.equals("30458")){
			  return accessLineMapping;
		  }*/
		return null;
	}


	public SageProductsExcelMapping getSageExcelMappingV5() {
		return sageExcelMappingV5;
	}


	public void setSageExcelMappingV5(SageProductsExcelMapping sageExcelMappingV5) {
		this.sageExcelMappingV5 = sageExcelMappingV5;
	}


	public WBTIndustriesMapper getWbtIndustriesMapperV5() {
		return wbtIndustriesMapperV5;
	}


	public void setWbtIndustriesMapperV5(WBTIndustriesMapper wbtIndustriesMapperV5) {
		this.wbtIndustriesMapperV5 = wbtIndustriesMapperV5;
	}


	public RadiousMapping getRadiMappingV5() {
		return radiMappingV5;
	}


	public void setRadiMappingV5(RadiousMapping radiMappingV5) {
		this.radiMappingV5 = radiMappingV5;
	}


	public SunGraphixRevisedMapping getSunGraphixMappingV5() {
		return sunGraphixMappingV5;
	}


	public void setSunGraphixMappingV5(SunGraphixRevisedMapping sunGraphixMappingV5) {
		this.sunGraphixMappingV5 = sunGraphixMappingV5;
	}
	
	

	/*public void setBloominPromotion(BloominPromotionsMapper bloominPromotion) {
			this.bloominPromotion = bloominPromotion;
		}

	public InternationlMerchMapping getMerchMapping() {
		return merchMapping;
	}

	public void setMerchMapping(InternationlMerchMapping merchMapping) {
		this.merchMapping = merchMapping;
	}

	public ESPTemplateMapping getEspTemplateMapping() {
		return espTemplateMapping;
	}

	public void setEspTemplateMapping(ESPTemplateMapping espTemplateMapping) {
		this.espTemplateMapping = espTemplateMapping;
	}

	public AdspecProductsExcelMapping getAdspecMapping() {
		return adspecMapping;
	}
	public void setAdspecMapping(AdspecProductsExcelMapping adspecMapping) {
		this.adspecMapping = adspecMapping;
	}
	public KlProductsExcelMapping getKlMapping() {
		return klMapping;
	}
	public void setKlMapping(KlProductsExcelMapping klMapping) {
		this.klMapping = klMapping;
	}*/
	

	/*public DCProductsExcelMapping getDcProductExcelMapping() {
		return dcProductExcelMapping;
	}

	public void setDcProductExcelMapping(
			DCProductsExcelMapping dcProductExcelMapping) {
		this.dcProductExcelMapping = dcProductExcelMapping;
	}

	public KukuProductsExcelMapping getKukuProductsExcelMapping() {
		return kukuProductsExcelMapping;
	}

	public void setKukuProductsExcelMapping(
			KukuProductsExcelMapping kukuProductsExcelMapping) {
		this.kukuProductsExcelMapping = kukuProductsExcelMapping;
	}

	public RFGLineProductExcelMapping getRfgLineProductExcelMapping() {
		return rfgLineProductExcelMapping;
	}

	public void setRfgLineProductExcelMapping(
			RFGLineProductExcelMapping rfgLineProductExcelMapping) {
		this.rfgLineProductExcelMapping = rfgLineProductExcelMapping;
	}

	public BBIProductsExcelMapping getBbiProductsExcelMapping() {
		return bbiProductsExcelMapping;
	}

	public void setBbiProductsExcelMapping(
			BBIProductsExcelMapping bbiProductsExcelMapping) {
		this.bbiProductsExcelMapping = bbiProductsExcelMapping;
	}

	public NewProductsExcelMapping getNewProductsExcelMapping() {
		return newProductsExcelMapping;
	}

	public void setNewProductsExcelMapping(
			NewProductsExcelMapping newProductsExcelMapping) {
		this.newProductsExcelMapping = newProductsExcelMapping;
	}

	public ApparelProductsExcelMapping getApparealExcelMapping() {
		return apparealExcelMapping;
	}

	public void setApparealExcelMapping(
			ApparelProductsExcelMapping apparealExcelMapping) {
		this.apparealExcelMapping = apparealExcelMapping;
	}

	public BroberryExcelMapping getBroberryExcelMapping() {
		return broberryExcelMapping;
	}

	public void setBroberryExcelMapping(BroberryExcelMapping broberryExcelMapping) {
		this.broberryExcelMapping = broberryExcelMapping;
	}
	public BestDealProductsExcelMapping getBdProdcutsMapping() {
		return bdProdcutsMapping;
	}

	public void setBdProdcutsMapping(BestDealProductsExcelMapping bdProdcutsMapping) {
		this.bdProdcutsMapping = bdProdcutsMapping;
	}

	public RiversEndExcelMapping getRiversEndExcelMapping() {
		return riversEndExcelMapping;
	}

	public void setRiversEndExcelMapping(RiversEndExcelMapping riversEndExcelMapping) {
		this.riversEndExcelMapping = riversEndExcelMapping;
	}

	public BambamProductExcelMapping getBamExcelMapping() {
		return bamExcelMapping;
	}

	public void setBamExcelMapping(BambamProductExcelMapping bamExcelMapping) {
		this.bamExcelMapping = bamExcelMapping;
	}

	public CutterBuckExcelMapping getCbExcelMapping() {
		return cbExcelMapping;
	}

	public void setCbExcelMapping(CutterBuckExcelMapping cbExcelMapping) {
		this.cbExcelMapping = cbExcelMapping;
	}
	
	public CrystalDExcelMapping getCdExcelMapping() {
		return cdExcelMapping;
	}

	public void setCdExcelMapping(CrystalDExcelMapping cdExcelMapping) {
		this.cdExcelMapping = cdExcelMapping;
	}


	public GoldstarCanadaExcelMapping getGoldcanadaExcelMapping() {
		return goldcanadaExcelMapping;
	}

	public void setGoldcanadaExcelMapping(
			GoldstarCanadaExcelMapping goldcanadaExcelMapping) {
		this.goldcanadaExcelMapping = goldcanadaExcelMapping;
	}
	public HighCaliberLineMappingRevised getHcLineExcelMapping() {
		return hcLineExcelMapping;
	}

	public void setHcLineExcelMapping(HighCaliberLineMappingRevised hcLineExcelMapping) {
		this.hcLineExcelMapping = hcLineExcelMapping;

	}


	public WholeSaleExcelMapping getWholeSaleExcelMapping() {
		return wholeSaleExcelMapping;
	}

	public void setWholeSaleExcelMapping(WholeSaleExcelMapping wholeSaleExcelMapping) {
		this.wholeSaleExcelMapping = wholeSaleExcelMapping;
	}

	public PrimeLineExcelMapping getPrimeLineExcelMapping() {
		return primeLineExcelMapping;
	}

	public void setPrimeLineExcelMapping(PrimeLineExcelMapping primeLineExcelMapping) {
		this.primeLineExcelMapping = primeLineExcelMapping;
	}


	public MilestoneExcelMapping getMilestoneExcelMapping() {
		return milestoneExcelMapping;
	}

	public void setMilestoneExcelMapping(MilestoneExcelMapping milestoneExcelMapping) {
		this.milestoneExcelMapping = milestoneExcelMapping;
	}

	public GoldBondExcelMapping getGoldBandExcelMapping() {
		return goldBandExcelMapping;
	}

	public void setGoldBandExcelMapping(GoldBondExcelMapping goldBandExcelMapping) {
		this.goldBandExcelMapping = goldBandExcelMapping;
	}

	public ProGolfMapping getProGolfMapping() {
		return proGolfMapping;
	}

	public void setProGolfMapping(ProGolfMapping proGolfMapping) {
		this.proGolfMapping = proGolfMapping;
	}
	public PSLMapping getPslMapping() {
		return pslMapping;
	}

	public void setPslMapping(PSLMapping pslMapping) {
		this.pslMapping = pslMapping;
	}
	public DacassoMapping getDacassoMapping() {
		return dacassoMapping;
	}

	public void setDacassoMapping(DacassoMapping dacassoMapping) {
		this.dacassoMapping = dacassoMapping;
	}

	public TomaxUsaMapping getTomaxUsaMapping() {
		return tomaxUsaMapping;
	}

	public void setTomaxUsaMapping(TomaxUsaMapping tomaxUsaMapping) {
		this.tomaxUsaMapping = tomaxUsaMapping;
	}	
	public SageRMKWorldWideMapping getSage80289Mapping() {
		return sage80289Mapping;
	}
	public void setSage80289Mapping(SageRMKWorldWideMapping sage80289Mapping) {
		this.sage80289Mapping = sage80289Mapping;
	}
	public BrandwearExcelMapping getBrandwearExcelMapping() {
		return brandwearExcelMapping;
	}

	public void setBrandwearExcelMapping(BrandwearExcelMapping brandwearExcelMapping) {
		this.brandwearExcelMapping = brandwearExcelMapping;
	}
	public BallProMapping getBallProMapping() {
		return ballProMapping;
	}

	public void setBallProMapping(BallProMapping ballProMapping) {
		this.ballProMapping = ballProMapping;
	}
	public PSLcadMapping getPslcadMapping() {
		return pslcadMapping;
	}

	public void setPslcadMapping(PSLcadMapping pslcadMapping) {
		this.pslcadMapping = pslcadMapping;
	}

	public TwintechMapping getTwintechMapping() {
		return twintechMapping;
	}

	public void setTwintechMapping(TwintechMapping twintechMapping) {
		this.twintechMapping = twintechMapping;
	}

	public BellaCanvas getBellaCanvasMapping() {
		return bellaCanvasMapping;
	}

	public void setBellaCanvasMapping(BellaCanvas bellaCanvasMapping) {
		this.bellaCanvasMapping = bellaCanvasMapping;
	}
	public AlfaMapping getAlfaMapping() {
		return alfaMapping;
	}
	public void setAlfaMapping(AlfaMapping alfaMapping) {
		this.alfaMapping = alfaMapping;
	}
	public TowelSpecialtiesMapping getTowelSpecialties() {
		return towelSpecialties;
	}

	public void setTowelSpecialties(TowelSpecialtiesMapping towelSpecialties) {
		this.towelSpecialties = towelSpecialties;
	}
	public BagMakersMapping getBagMakersMapping() {
		return bagMakersMapping;
	}


	public HarvestIndustrialExcelMapping getHarvestMapping() {
		return harvestMapping;
	}

	public void setHarvestMapping(HarvestIndustrialExcelMapping harvestMapping) {
		this.harvestMapping = harvestMapping;
	}


	public void setBagMakersMapping(BagMakersMapping bagMakersMapping) {
		this.bagMakersMapping = bagMakersMapping;
	}
	
	public void setGillStudiosMapping(GillStudiosMapping gillStudiosMapping) {
		this.gillStudiosMapping = gillStudiosMapping;
	}	
	public GillStudiosMapping getGillStudiosMapping() {
		return gillStudiosMapping;
	}
	public BlueGenerationMapping getBlueGenerationMapping() {
		return blueGenerationMapping;
	}

	public void setBlueGenerationMapping(BlueGenerationMapping blueGenerationMapping) {
		this.blueGenerationMapping = blueGenerationMapping;
	}
	
	public MaxplusMapping getMaxplusmapping() {
		return maxplusmapping;
	}

	public void setMaxplusmapping(MaxplusMapping maxplusmapping) {
		this.maxplusmapping = maxplusmapping;
	}
	public FITSAccessoriesMapping getFitsAccessoriesMapping() {
		return fitsAccessoriesMapping;
	}

	public void setFitsAccessoriesMapping(FITSAccessoriesMapping fitsAccessoriesMapping) {
		this.fitsAccessoriesMapping = fitsAccessoriesMapping;
	}
	public GempirepromotionsMapping getGempiresMapping() {
		return gempiresMapping;
	}

	public void setGempiresMapping(GempirepromotionsMapping gempiresMapping) {
		this.gempiresMapping = gempiresMapping;
	}

	public BayStateMapping getBaysStateMapping() {
		return baysStateMapping;
	}
	public void setBaysStateMapping(BayStateMapping baysStateMapping) {
		this.baysStateMapping = baysStateMapping;
	}
	
	public EdwardsGarmentMapping getEdwardsGarmentMapping() {
		return edwardsGarmentMapping;
	}

	public void setEdwardsGarmentMapping(EdwardsGarmentMapping edwardsGarmentMapping) {
		this.edwardsGarmentMapping = edwardsGarmentMapping;
	}


	public TekweldMapping getTekweldMapping() {
		return tekweldMapping;
	}

	public void setTekweldMapping(TekweldMapping tekweldMapping) {
		this.tekweldMapping = tekweldMapping;
	}
	public HeadWearMapping getHeadWearMapping() {
		return headWearMapping;
	}

	public void setHeadWearMapping(HeadWearMapping headWearMapping) {
		this.headWearMapping = headWearMapping;
	}


	public SportCanadaExcelMapping getSportMapping() {
		return sportMapping;
	}

	public void setSportMapping(SportCanadaExcelMapping sportMapping) {
		this.sportMapping = sportMapping;
	}

	public SunScopeMapping getSunScopeMapping() {
		return sunScopeMapping;
	}
	public void setSunScopeMapping(SunScopeMapping sunScopeMapping) {
		this.sunScopeMapping = sunScopeMapping;
	}

	

	public SunGraphixRevisedMapping getSunGraphixMapping() {
		return sunGraphixMapping;
	}

	public void setSunGraphixMapping(SunGraphixRevisedMapping sunGraphixMapping) {
		this.sunGraphixMapping = sunGraphixMapping;
	}

	public CbMapping getCbExeMapping() {
		return cbExeMapping;
	}

	public void setCbExeMapping(CbMapping cbExeMapping) {
		this.cbExeMapping = cbExeMapping;
	}
	
	public PelicanGraphicMapping getPelicanGraphicMapping() {
		return pelicanGraphicMapping;
	}

	public void setPelicanGraphicMapping(PelicanGraphicMapping pelicanGraphicMapping) {
		this.pelicanGraphicMapping = pelicanGraphicMapping;
	}

	public SimplifiedsourcingMapping getSimplifiedMapping() {
		return simplifiedMapping;
	}

	public void setSimplifiedMapping(SimplifiedsourcingMapping simplifiedMapping) {
		this.simplifiedMapping = simplifiedMapping;
	}

	public SolidDimensionMapping getSolidDimensionMapping() {
		return solidDimensionMapping;
	}

	public void setSolidDimensionMapping(SolidDimensionMapping solidDimensionMapping) {
		this.solidDimensionMapping = solidDimensionMapping;
	}
	public SportsManBagMapping getSportsManBagMapping() {
		return sportsManBagMapping;
	}

	public void setSportsManBagMapping(SportsManBagMapping sportsManBagMapping) {
		this.sportsManBagMapping = sportsManBagMapping;
	}
	public void setEveanManufactureMapping(EveanManufacturingCanadaMapping eveanManufactureMapping) {
		this.eveanManufactureMapping = eveanManufactureMapping;
	}

	public SportUSAMapping getSportUSAMapping() {
		return sportUSAMapping;
	}

	public void setSportUSAMapping(SportUSAMapping sportUSAMapping) {
		this.sportUSAMapping = sportUSAMapping;
	}

	public SportAzxCandMapping getSportAzxCandMapping() {
		return sportAzxCandMapping;
	}

	public void setSportAzxCandMapping(SportAzxCandMapping sportAzxCandMapping) {
		this.sportAzxCandMapping = sportAzxCandMapping;
	}
	public DigiSpecMapping getDigiSpecMapping() {
		return digiSpecMapping;
	}

	public void setDigiSpecMapping(DigiSpecMapping digiSpecMapping) {
		this.digiSpecMapping = digiSpecMapping;
	}
	public void setDouglasBridgeMapper(DouglasBridgeMapper douglasBridgeMapper) {
		this.douglasBridgeMapper = douglasBridgeMapper;
	}

	public RadiousMapping getRadiMapping() {
		return radiMapping;
	}

	public void setRadiMapping(RadiousMapping radiMapping) {
		this.radiMapping = radiMapping;
	}
	public WBTIndustriesMapper getWbtIndustriesMapper() {
		return wbtIndustriesMapper;
	}

	public void setWbtIndustriesMapper(WBTIndustriesMapper wbtIndustriesMapper) {
		this.wbtIndustriesMapper = wbtIndustriesMapper;
	}
	public PioneerLLCMapping getPioneerLLCMapping() {
		return pioneerLLCMapping;
	}

	public ZenithExport getZenithMapping() {
		return zenithMapping;
	}

	public void setZenithMapping(ZenithExport zenithMapping) {
		this.zenithMapping = zenithMapping;
	}

	public void setPioneerLLCMapping(PioneerLLCMapping pioneerLLCMapping) {
		this.pioneerLLCMapping = pioneerLLCMapping;
	}
	 public void setTeamWorkAthleticMapper(TeamworkAthleticMapping teamWorkAthleticMapper) {
			this.teamWorkAthleticMapper = teamWorkAthleticMapper;
		}
	public AccessLineMapping getAccessLineMapping() {
			return accessLineMapping;
		}

		public void setAccessLineMapping(AccessLineMapping accessLineMapping) {
			this.accessLineMapping = accessLineMapping;
		}*/

}
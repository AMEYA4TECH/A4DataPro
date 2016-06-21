package com.a4tech.core.dao;


import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.util.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductLoggerDAOImpl implements ProductLoggerDAO {
	
	private Logger              _LOGGER              = Logger.getLogger(getClass());
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	 
	public void save(Product productLoggerObj,List<Error> errorList) {
		Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        // session.persist(extloggerObj);
        session.save(productLoggerObj);
        tx.commit();
        session.close();

	}

	
	/*public boolean logActivity(String requestType, String xid, String batchId, BigInteger ssoId, String applicationCode, String ipAddress,
            Date radarGetRequested, Date radarGetResponded, long radarResponsTimeMS, Date apiTransformationStarted,
            Date apiTransformationFinished, long apiTransformationTimeMS, long apiConfigProcessingTime, long apiPriceGridProcessingTime, long apiBasicDetailsProcessingTime,
            Date radarPostRequested, Date radarPostResponded, Long radarPostTotalTimeMS,
            long apiTotalTime, String productJSON) {

        if (_LOGGER.isDebugEnabled()) {
            _LOGGER.debug("Start logging the stats in db...");
        }
        
      //  SimpleDateFormat formatter = new SimpleDateFormat(SQL_DATE_TIME_FORMAT);

     ExternalAPILogger apiLog = new ExternalAPILogger();
//        apiLog.setLogID(getNextKey());
        apiLog.setRequestType(requestType);
        apiLog.setExternalProductId(xid);
        apiLog.setBatchId(batchId);
        apiLog.setSignOnId(ssoId);
        apiLog.setApplicationCode(applicationCode);
        apiLog.setIpAddress(ipAddress);
        if(radarGetRequested != null) {
            apiLog.setRadarGetRequestedDate(formatter.format(radarGetRequested));
        } else {
            apiLog.setRadarGetRequestedDate(null);
        }
        if(radarGetResponded != null) {
            apiLog.setRadarGetRespondedDate(formatter.format(radarGetResponded));
            apiLog.setRadarGetTotalCNT(radarResponsTimeMS);
        } else {
            apiLog.setRadarGetRespondedDate(null);
            apiLog.setRadarGetTotalCNT(0l);
        }
        
        if(apiTransformationStarted != null) {
            apiLog.setApiTransformationStartedDate(formatter.format(apiTransformationStarted));
        } else {
            apiLog.setApiTransformationStartedDate(formatter.format(null));
        }
        
        if(apiTransformationFinished != null) {
            apiLog.setApiTransformationFinishedDate(formatter.format(apiTransformationFinished));
            apiLog.setApiTransformationTotalCNF(apiTransformationTimeMS);
        } else {
            apiLog.setApiTransformationFinishedDate(null);
            apiLog.setApiTransformationTotalCNF(0l);
        }
        
        if(radarPostRequested != null) {
            apiLog.setRadarPostRequestedDate(formatter.format(radarPostRequested));
        } else {
            apiLog.setRadarPostRequestedDate(null);
        }
        
        if(radarPostResponded != null) {
            apiLog.setRadarPostRespondedDate(formatter.format(radarPostResponded));
            apiLog.setRadarPostTotalCNT(radarPostTotalTimeMS);
        } else {
            apiLog.setRadarPostRespondedDate(null);
            apiLog.setRadarPostTotalCNT(0l);
        }
        apiLog.setApiConfigurationTimeCNT(apiConfigProcessingTime);
        apiLog.setApiBasicDetailsTimeCNT(apiBasicDetailsProcessingTime);
        apiLog.setApiPriceGridTimeCNT(apiPriceGridProcessingTime);
        apiLog.setTotalTimeCNT(apiTotalTime);
        
        if(!StringUtils.isEmpty(productJSON)) {
           apiLog.setProductJSON(productJSON);
        }
        
        if (_LOGGER.isDebugEnabled()) { _LOGGER.debug("Preparation of LogActivity object done..."); }
        // setting defaults...
        apiLog.setUpdateSource("");

        try {
            if (_LOGGER.isDebugEnabled()) { _LOGGER.debug("Opening Session"); }
            Session session = sessionFactory.openSession();
            if (_LOGGER.isDebugEnabled()) { _LOGGER.debug("Benginning Transaction"); }
            Transaction tx = session.beginTransaction();
            if (_LOGGER.isDebugEnabled()) { _LOGGER.debug("Inserting Log Data"); }
            session.save(apiLog);
            if (_LOGGER.isDebugEnabled()) { _LOGGER.debug("Committing Data"); }
            tx.commit();
            if (_LOGGER.isDebugEnabled()) { _LOGGER.debug("Data Successfully inserted."); }
            if (_LOGGER.isDebugEnabled()) { _LOGGER.debug("Closing session"); }
            session.close();

            if (_LOGGER.isDebugEnabled()) { _LOGGER.debug(apiLog); }
            if (_LOGGER.isDebugEnabled()) { _LOGGER.debug("Logged the activity into db successfully"); }
            return true;

        } catch (Exception e) {
            _LOGGER.error("Unable to log activity into DB", e);
            return false;
        }
*/
}
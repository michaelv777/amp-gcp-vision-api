/**
 * 
 */
package com.amp.common.api.vision.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amp.common.api.vision.dto.ReceiptDTO;
import com.amp.common.api.vision.handler.RequestHandlerFactory;
import com.amp.common.api.vision.handler.RequestHandlerInterface;
import com.amp.common.api.vision.jpa.ReceiptConfigurationM;
import com.amp.common.api.vision.jpa.VendorM;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.gson.JsonObject;

/**
 * @author mveksler
 *
 */
@Service
public class OcrParserService 
{
	@Autowired
    private EntityManagerFactory emf;
	
	private static final Logger LOG = 
			LoggerFactory.getLogger(OcrParserService.class);
	
	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public OcrParserService()
	{
		
	}
	
	public ReceiptDTO processVisionApiResponse(
			JsonObject receiptPayload,
			TextAnnotation receiptAnnotation)
	{
		ReceiptDTO resObject = new ReceiptDTO();
		
		ReceiptConfigurationM vendorConfig = null;
				
		boolean cRes = true;
		
		try
		{
			String vendorName = this.getReceiptConfigurationVendor(receiptAnnotation);
			if ( vendorName.equals(StringUtils.EMPTY))
			{
				LOG.error("No vendorName found!");
				
				cRes = false;
			}
			//---
			if ( cRes )
			{
				vendorConfig = this.getReceiptConfigurationByVendor(vendorName);
			
				if ( null == vendorConfig )
				{
					LOG.error(String.format("No metadata found for %s", vendorName));
					
					cRes = false;
				}
			}
			//---
			if ( cRes )
			{
				RequestHandlerInterface requestHandler = 
						new RequestHandlerFactory().getRequestHandler(vendorName);
				
				resObject = requestHandler.runProcessData(
						receiptPayload, receiptAnnotation, vendorConfig);
			}
			
			return resObject;
		}
		catch( Exception e )
		{
			LOG.error(e.getMessage(), e);
			
			return new ReceiptDTO();
		}
	}
	
	@SuppressWarnings("unchecked")
	private String getReceiptConfigurationVendor(TextAnnotation receiptAnnotation)
	{
		String vendorName = StringUtils.EMPTY;
		
		EntityManager entityManager = null; 
        EntityTransaction tx = null;

        try
		{
			String rawText = receiptAnnotation.getText();
			rawText = rawText.replaceAll(System.getProperty("line.separator"), StringUtils.EMPTY);
			rawText = rawText.replaceAll("(\\\\r\\\\n|\\\\n)", StringUtils.EMPTY);
			rawText = rawText.replaceAll(" ", StringUtils.EMPTY);
			
			entityManager = this.createManager(FlushModeType.COMMIT);
            if (LOG.isDebugEnabled()) 
            {
                LOG.debug(String.format("About to get configuration for: %s", vendorName));
            }
            
            //---
            tx = entityManager.getTransaction();
            
            tx.begin();
            
            try
            {
            	Query vendorQuery = entityManager.createNamedQuery(
            			"VendorM.findAll", VendorM.class);
            	
            	List<VendorM> vendors = (List<VendorM>) vendorQuery.getResultList();
            	
            	for( VendorM vendor : vendors )
            	{
            		if ( rawText.toLowerCase().contains(vendor.getName().toLowerCase()))
            		{
            			vendorName = vendor.getName().toLowerCase();
            		}
            	}
            }
            catch (NoResultException ex) 
            {
    			LOG.error(String.format("No metadata found for %s", vendorName));
    	    } 
            
            tx.commit();
            
			return vendorName;
		}
		catch( Exception ex )
		{
			LOG.error(ex.getMessage(), ex);
        	
            if (tx != null)
            {
                tx.rollback();
            }
            
            throw ex;
		}
		finally
        {
        	if ( entityManager != null )
        	{
        		entityManager.close();
        	}
        }
	}

	private ReceiptConfigurationM getReceiptConfigurationByVendor(String vendorName)
	{
    	EntityManager entityManager = null; 
        EntityTransaction tx = null;
        
        ReceiptConfigurationM vendorConfig = null;
        
		try 
		{
			entityManager = this.createManager(FlushModeType.COMMIT);
            if (LOG.isDebugEnabled()) 
            {
                LOG.debug(String.format("About to get configuration for: %s", vendorName));
            }
            
            //---
            tx = entityManager.getTransaction();
            
            tx.begin();
            
            try
            {
            	Query vendorConfigQuery = entityManager.createNamedQuery(
            			"ReceiptConfigurationM.findByVendor", ReceiptConfigurationM.class).
            				setParameter("vendorName", vendorName);
            	
            	vendorConfig = (ReceiptConfigurationM) vendorConfigQuery.getSingleResult();
            }
            catch (NoResultException ex) 
            {
    			LOG.error(String.format("No metadata found for %s", vendorName));
    	    } 
            
            tx.commit();
            
            return vendorConfig;
        } 
        catch (Exception ex)
        {
        	LOG.error(ex.getMessage(), ex);
        	
            if (tx != null)
            {
                tx.rollback();
            }
            
            throw ex;
        } 
        finally
        {
        	if ( entityManager != null )
        	{
        		entityManager.close();
        	}
        }
    }
	
	private EntityManager createManager(FlushModeType mode) 
    {
        EntityManager entityManager = 
        		this.getEmf().createEntityManager();
        
    	entityManager.setFlushMode(mode);
    
    	return entityManager;
    }
}

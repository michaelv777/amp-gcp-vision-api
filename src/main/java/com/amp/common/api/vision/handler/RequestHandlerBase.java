/**
 * 
 */
package com.amp.common.api.vision.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.vision.dto.ReceiptDTO;
import com.amp.common.api.vision.jpa.ReceiptConfigurationM;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.gson.JsonObject;

/**
 * @author mveksler
 *
 */
public abstract class RequestHandlerBase implements RequestHandlerInterface
{
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(RequestHandlerBase.class);
	
	@Override
	public boolean init() 
	{
		return true;
	}

	@Override
	public ReceiptDTO runProcessData(
			JsonObject receiptPayload,
			TextAnnotation receiptAnnotation,
			ReceiptConfigurationM vendorConfig) 
	{
		String cMethodName = "";
		
		boolean cRes = true;
		
		ReceiptDTO receiptDTO = new ReceiptDTO();
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        cRes = this.validateInput(receiptPayload, receiptAnnotation, vendorConfig);
	        //---
	        if ( cRes )
	        {
	    		
	        }
	        
	        
	        
	        
			return receiptDTO;
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
			
			return new ReceiptDTO();
		}
		finally
		{
			this.releaseResources();
		}
	}

	@Override
	public boolean releaseResources() 
	{
		return true;
	}
	
	protected boolean validateInput(
			JsonObject receiptPayload,
			TextAnnotation receiptAnnotation,
			ReceiptConfigurationM vendorConfig) 
	{
		boolean cRes = true;
		
		try
		{
	        if ( null == receiptPayload )
	        {
	        	LOGGER.error("::receiptPayload is null");
	        	
	        	cRes = false;
	        }
	        if ( null == receiptAnnotation )
	        {
	        	LOGGER.error("::receiptAnnotation is null");
	        	
	        	cRes = false;
	        }
	        if ( null == vendorConfig )
	        {
	        	LOGGER.error("::vendorConfig is null");
	        	
	        	cRes = false;
	        }
	        //---
	        
			return cRes;
		}
		catch( Exception e )
		{
			LOGGER.error("::Exception:" + e.getMessage(), e);
			
			return false;
		}
	}

}

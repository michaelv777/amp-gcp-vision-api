/**
 * 
 */
package com.amp.common.api.vision.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.vision.dto.ReceiptDTO;
import com.amp.common.api.vision.handler.RequestHandlerBase;
import com.amp.common.api.vision.jpa.ReceiptConfigurationM;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.gson.JsonObject;

/**
 * @author MVEKSLER
 *
 */
public class RequestHandlerDefault extends RequestHandlerBase 
{
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(RequestHandlerDefault.class);
	
	public RequestHandlerDefault()
	{
		super();
		
		String cMethodName = "";
    	
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(),e);
		}
	}
	
	//---
	@Override
	public ReceiptDTO runProcessData(
			JsonObject receiptPayload,
			TextAnnotation receiptAnnotation,
			ReceiptConfigurationM vendorConfig) 
	{
		@SuppressWarnings("unused")
		String cMethodName = "";
    	
		@SuppressWarnings("unused")
		boolean cRes = true;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	       
	        ReceiptDTO receiptDTO = super.runProcessData(
	        		receiptPayload, receiptAnnotation, vendorConfig);
	        
			return receiptDTO;
		}
		catch( Exception e )
		{
			LOGGER.error(e.getMessage(), e);
    		
    		return new ReceiptDTO();
		}
	}
}

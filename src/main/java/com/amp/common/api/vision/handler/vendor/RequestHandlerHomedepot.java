/**
 * 
 */
package com.amp.common.api.vision.handler.vendor;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.vision.dto.ReceiptDTO;
import com.amp.common.api.vision.dto.ReceiptItemDTO;
import com.amp.common.api.vision.dto.ReceiptItemDTOWrapper;
import com.amp.common.api.vision.handler.RequestHandlerBase;
import com.amp.common.api.vision.handler.receipt.config.ReceiptConfiguration;
import com.amp.common.api.vision.handler.receipt.parser.vendor.ItemsDataParserHomedepot;
import com.amp.common.api.vision.jpa.ReceiptConfigurationM;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.DocumentContext;

/**
 * @author MVEKSLER
 *
 */
public class RequestHandlerHomedepot extends RequestHandlerBase 
{
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(RequestHandlerHomedepot.class);
	
	public RequestHandlerHomedepot()
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
	
	@Override
	public Set<ReceiptItemDTO> getItems(
			DocumentContext jsonContext, 
			TextAnnotation receiptAnnotation,
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		ReceiptItemDTOWrapper value = null;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        value = new ItemsDataParserHomedepot().handleData(
	        		jsonContext, receiptAnnotation, receiptConfig);
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
		}
		
		return value.getItemsSet();
	}
}

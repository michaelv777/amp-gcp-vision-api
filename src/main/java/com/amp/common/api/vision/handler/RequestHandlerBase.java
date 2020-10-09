/**
 * 
 */
package com.amp.common.api.vision.handler;

import java.math.BigDecimal;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.vision.dto.ReceiptDTO;
import com.amp.common.api.vision.handler.receipt.config.ReceiptConfiguration;
import com.amp.common.api.vision.handler.receipt.parser.DateTimeParser;
import com.amp.common.api.vision.handler.receipt.parser.SubtotalParser;
import com.amp.common.api.vision.handler.receipt.parser.TaxAmountParser;
import com.amp.common.api.vision.handler.receipt.parser.TaxRateParser;
import com.amp.common.api.vision.handler.receipt.parser.TotalParser;
import com.amp.common.api.vision.jpa.ReceiptConfigurationM;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

/**
 * @author mveksler
 *
 */
public abstract class RequestHandlerBase implements RequestHandlerInterface, IReceiptData
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
	        
	        if ( cRes )
	        {
	        	DocumentContext jsonContext = JsonPath.
		        		using(Configuration.defaultConfiguration()).
		        		parse(receiptPayload.toString());
	        	
	        	Gson gson = new GsonBuilder().setLenient().create();
	        	
	        	ReceiptConfiguration receiptConfig = gson.fromJson(
	        		vendorConfig.getReceiptconfiguration(), ReceiptConfiguration.class); 
	        	
	        	receiptDTO.setPurchaseDate(this.getPurchaseDate(
	        			jsonContext, receiptAnnotation, receiptConfig));
	        	
	        	receiptDTO.setTotal(this.getTotal(
	        			jsonContext, receiptAnnotation, receiptConfig));
	        	
	        	receiptDTO.setSubtotal(this.getSubtotal(
	        			jsonContext, receiptAnnotation, receiptConfig));
	        	
	        	receiptDTO.setSalesTaxRate(this.getTaxRate(
	        			jsonContext, receiptAnnotation, receiptConfig));
	        	
	        	receiptDTO.setTaxAmount(this.getTaxAmount(
	        			jsonContext, receiptAnnotation, receiptConfig));
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
	public Instant getPurchaseDate(DocumentContext jsonContext, 
								   TextAnnotation receiptAnnotation,
								   ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        Instant value = new DateTimeParser().handleData(
	        		jsonContext, receiptAnnotation, receiptConfig);
	        
	        return value;
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
			
			return null;
		}
	}
	
	@Override
	public BigDecimal getTotal(DocumentContext jsonContext, 
							   TextAnnotation receiptAnnotation,
							   ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		BigDecimal value = null;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        value = new TotalParser().handleData(
	        		jsonContext, receiptAnnotation, receiptConfig);
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
		}
		
		return value;
	}
	
	@Override
	public BigDecimal getSubtotal(DocumentContext jsonContext, 
							   	  TextAnnotation receiptAnnotation,
							      ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		BigDecimal value = null;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        value = new SubtotalParser().handleData(
	        		jsonContext, receiptAnnotation, receiptConfig);
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
		}
		
		return value;
	}
	
	@Override
	public BigDecimal getTaxRate(DocumentContext jsonContext, 
							   	 TextAnnotation receiptAnnotation,
							   	 ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		BigDecimal value = null;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        value = new TaxRateParser().handleData(
	        		jsonContext, receiptAnnotation, receiptConfig);
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
		}
		
		return value;
	}
	
	@Override
	public BigDecimal getTaxAmount(DocumentContext jsonContext, 
							   	 TextAnnotation receiptAnnotation,
							   	 ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		BigDecimal value = null;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        value = new TaxAmountParser().handleData(
	        		jsonContext, receiptAnnotation, receiptConfig);
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
		}
		
		return value;
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

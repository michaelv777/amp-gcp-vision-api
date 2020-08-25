/**
 * 
 */
package com.amp.common.api.vision.handler;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.vision.dto.ReceiptDTO;
import com.amp.common.api.vision.handler.receipt.ReceiptConfiguration;
import com.amp.common.api.vision.handler.receipt.ReceiptDataInterface;
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
public abstract class RequestHandlerBase implements RequestHandlerInterface, ReceiptDataInterface
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
	        	DocumentContext jsonContext = JsonPath.
		        		using(Configuration.defaultConfiguration()).
		        		parse(receiptPayload.toString());
	        	
	        	Gson gson = new GsonBuilder().setLenient().create();
	        	
	        	ReceiptConfiguration receiptConfig = gson.fromJson(
	        		vendorConfig.getReceiptconfiguration(), ReceiptConfiguration.class); 
	        	
	        	receiptDTO.setPurchaseDate(this.getPurchaseDate(
	        			jsonContext, receiptConfig));
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
	public Instant getPurchaseDate(
			DocumentContext jsonContext, 
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        String purchaseDateValue = StringUtils.EMPTY;
	        String purchaseTimeValue = StringUtils.EMPTY;
	        String purchaseAMPMValue = StringUtils.EMPTY;
	        String purchaseDateTimeValue = StringUtils.EMPTY;
	        String purchaseDateFormat = receiptConfig.getPurchaseDateTime().getPurchaseDateFormat();
	        
        	net.minidev.json.JSONArray purchaseDate = 
        		jsonContext.read(receiptConfig.getPurchaseDateTime().getPurchaseDate()); 
        	
        	if ( purchaseDate.size() >= 1)
	        {
	        	purchaseDateValue = (String)purchaseDate.get(0);
	        }
       
        	net.minidev.json.JSONArray purchaseTime = 
        		jsonContext.read(receiptConfig.getPurchaseDateTime().getPurchaseTime());
        	
        	if ( purchaseTime.size() >= 1)
	        {
        		purchaseTimeValue = (String)purchaseTime.get(0);
	        }
       
        	net.minidev.json.JSONArray purchaseAMPM = 
        		jsonContext.read(receiptConfig.getPurchaseDateTime().getPurchaseAMPM());
        	
        	if ( purchaseAMPM.size() >= 1)
	        {
        		purchaseAMPMValue = (String)purchaseAMPM.get(0);
	        }
        
       
        	if ( !purchaseDateValue.equals(StringUtils.EMPTY))
        	{
        		purchaseDateTimeValue += purchaseDateValue;
        	}
        	
        	if ( !purchaseTimeValue.equals(StringUtils.EMPTY))
        	{
        		purchaseDateTimeValue += " ";
        		purchaseDateTimeValue += purchaseTimeValue;
        	}
        	
        	if ( !purchaseAMPMValue.equals(StringUtils.EMPTY))
        	{
        		purchaseDateTimeValue += " ";
        		purchaseDateTimeValue += purchaseAMPMValue;
        	}
        	
        	Date date = new SimpleDateFormat(purchaseDateFormat).parse(purchaseDateTimeValue);
        
        	Instant value = date.toInstant();
        	
        	LOGGER.debug(value.toString());
	       
	        
	        /*
	        net.minidev.json.JSONArray purchaseDate = jsonContext.read(
	        		receiptConfig.getPurchaseDateTime().getPurchaseDate());
	        
	        if ( purchaseDate.size() >= 1 )
	        {
	        	String purchaseDateValue = (String)purchaseDate.get(0);
	        	
	        	LocalDate date = LocalDate.parse(purchaseDateValue);
	        
	        	value = date.atStartOfDay(ZoneId.of("Etc/UTC")).toInstant();
	        }
	        */
	        return value;
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
			
			return null;
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

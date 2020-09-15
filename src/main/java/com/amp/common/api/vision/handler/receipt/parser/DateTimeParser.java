/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.parser;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.vision.handler.RequestHandlerBase;
import com.amp.common.api.vision.handler.receipt.config.DateTimeConfigurationItem;
import com.amp.common.api.vision.handler.receipt.config.ReceiptConfiguration;
import com.google.cloud.vision.v1.TextAnnotation;
import com.jayway.jsonpath.DocumentContext;

/**
 * @author mveksler
 *
 */
public class DateTimeParser 
{
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(RequestHandlerBase.class);
	
	public Instant handleData(
			DocumentContext jsonContext, 
			TextAnnotation receiptAnnotation, 
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		Instant value = null;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        Map<Integer, DateTimeConfigurationItem> configurationItemsMap = new 
	        		TreeMap<Integer, DateTimeConfigurationItem>();
	        
	        for( DateTimeConfigurationItem configurationItem : receiptConfig.getPurchaseDateTime().getConfigurationItems() )
	        {
	        	configurationItemsMap.put(configurationItem.getPriority(), configurationItem);
	        }
	        
	        for( Map.Entry<Integer, DateTimeConfigurationItem> configurationItemEntry : configurationItemsMap.entrySet() )
	        {
	        	DateTimeConfigurationItem configurationItem = configurationItemEntry.getValue();
	        	
	        	String configType = configurationItem.getType();
	        	
	        	if ( configType.equalsIgnoreCase(ConfigurationType.JSON_REGEX.getConfigurationType()) )
	        	{
	        		value = this.handleDataWithJsonPath(jsonContext, configurationItem);
	        	}
	        	else if ( configType.equalsIgnoreCase(ConfigurationType.JSON_PATH.getConfigurationType()) )
	        	{
	        		value = this.handleDataWithJsonPath(jsonContext, configurationItem);
	        	}
	        	else
	        	{
	        		value = this.handleDataWithJsonPath(jsonContext, configurationItem);
	        	}
	        }
	      
	        return value;
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
			
			return null;
		}
	}
	
	//---
	protected Instant handleDataWithJsonPath(
			DocumentContext jsonContext, 
			DateTimeConfigurationItem configurationItem)
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
	        
	        String purchaseDateFormat = configurationItem.getPurchaseDateFormat();
	        
        	net.minidev.json.JSONArray purchaseDate = 
        		jsonContext.read(configurationItem.getPurchaseDate()); 
        	
        	if ( purchaseDate.size() >= 1)
	        {
	        	purchaseDateValue = (String)purchaseDate.get(0);
	        }
       
        	net.minidev.json.JSONArray purchaseTime = 
        		jsonContext.read(configurationItem.getPurchaseTime());
        	
        	if ( purchaseTime.size() >= 1)
	        {
        		purchaseTimeValue = (String)purchaseTime.get(0);
	        }
       
        	net.minidev.json.JSONArray purchaseAMPM = 
        		jsonContext.read(configurationItem.getPurchaseAMPM());
        	
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
        	
        	//Calendar calendar = Calendar.getInstance();  
        	
        	Date date = new SimpleDateFormat(purchaseDateFormat).parse(purchaseDateTimeValue);
        
        	Instant value = date.toInstant();
        	
        	LOGGER.debug(value.toString());
	      
	        return value;
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
			
			return null;
		}
	}
	
	//---
	protected Instant handleDataWithJsonRegex(
			TextAnnotation receiptAnnotation,
			DateTimeConfigurationItem configurationItem)
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
	        
	        String purchaseDateFormat = configurationItem.getPurchaseDateFormat();
	        
	        /*
        	net.minidev.json.JSONArray purchaseDate = 
        		jsonContext.read(configurationItem.getPurchaseDate()); 
        	
        	if ( purchaseDate.size() >= 1)
	        {
	        	purchaseDateValue = (String)purchaseDate.get(0);
	        }
       
        	net.minidev.json.JSONArray purchaseTime = 
        		jsonContext.read(configurationItem.getPurchaseTime());
        	
        	if ( purchaseTime.size() >= 1)
	        {
        		purchaseTimeValue = (String)purchaseTime.get(0);
	        }
       
        	net.minidev.json.JSONArray purchaseAMPM = 
        		jsonContext.read(configurationItem.getPurchaseAMPM());
        	
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
        	
        	//Calendar calendar = Calendar.getInstance();  
        	
        	Date date = new SimpleDateFormat(purchaseDateFormat).parse(purchaseDateTimeValue);
        
        	Instant value = date.toInstant();
        	
        	LOGGER.debug(value.toString());
	      	*/
	        Instant value = new Date().toInstant();
	        
	        return value;
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
			
			return null;
		}
	}
}

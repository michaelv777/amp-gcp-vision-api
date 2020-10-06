/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.parser;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.vision.handler.RequestHandlerBase;
import com.amp.common.api.vision.handler.receipt.config.ReceiptConfiguration;
import com.amp.common.api.vision.handler.receipt.config.SubtotalConfigurationItem;
import com.amp.common.api.vision.utils.RegexParser;
import com.google.cloud.vision.v1.TextAnnotation;
import com.jayway.jsonpath.DocumentContext;

/**
 * @author mveksler
 *
 */
public class SubtotalParser 
{
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(RequestHandlerBase.class);
	
	public BigDecimal handleData(
			DocumentContext jsonContext, 
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
	        
	        Map<Integer, SubtotalConfigurationItem> configurationItemsMap = new 
	        		TreeMap<Integer, SubtotalConfigurationItem>();
	        
	        for( SubtotalConfigurationItem configurationItem : receiptConfig.getSubtotal().getConfigurationItems() )
	        {
	        	configurationItemsMap.put(configurationItem.getPriority(), configurationItem);
	        }
	        
	        for( Map.Entry<Integer, SubtotalConfigurationItem> configurationItemEntry : configurationItemsMap.entrySet() )
	        {
	        	SubtotalConfigurationItem configurationItem = configurationItemEntry.getValue();
	        	
	        	String configType = configurationItem.getType();
	        	
	        	if ( configType.equalsIgnoreCase(ConfigurationType.JSON_REGEX.getConfigurationType()) )
	        	{
	        		value = this.handleDataWithJsonRegex(receiptAnnotation, configurationItem);
	        	}
	        	else if ( configType.equalsIgnoreCase(ConfigurationType.JSON_PATH.getConfigurationType()) )
	        	{
	        		value = this.handleDataWithJsonPath(jsonContext, configurationItem);
	        	}
	        	else
	        	{
	        		value = this.handleDataWithJsonRegex(receiptAnnotation, configurationItem);
	        	}
	        	
	        	if ( value != null )
	        	{
	        		break;
	        	}
	        }
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
		}
		
		return value;
	}
	
	//---
	protected BigDecimal handleDataWithJsonPath(
			DocumentContext jsonContext, 
			SubtotalConfigurationItem configurationItem)
	{
		String cMethodName = "";
		
		BigDecimal value = null;
		 
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        if ( jsonContext == null || configurationItem == null )
	        {
	        	LOGGER.error(cMethodName + "::Error: NULL parameters");
	        	
	        	return value;
	        }
	        
        	net.minidev.json.JSONArray data = 
        		jsonContext.read(configurationItem.getValue()); 
        	
        	if ( data.size() >= 1)
	        {
        		String valueStr = (String)data.get(0);
        		
        		if ( NumberUtils.isCreatable(valueStr) )
        		{
        			value = new BigDecimal(valueStr);
        		}
	        }
        	
        	LOGGER.debug(value.toString());
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
		}
		
		return value;
	}
	
	//---
	protected BigDecimal handleDataWithJsonRegex(
			TextAnnotation receiptAnnotation,
			SubtotalConfigurationItem configurationItem)
	{
		String cMethodName = "";
		
		BigDecimal value = null;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        if ( receiptAnnotation == null || configurationItem == null )
	        {
	        	LOGGER.error(cMethodName + "::Error: NULL parameters");
	        	
	        	return value;
	        }
	        
	        RegexParser regexParser = new RegexParser();
	        
	        String payloadContentText = receiptAnnotation.getText();
	        		
	        String valueStr = regexParser.getGroupValueByRegex(
	        		payloadContentText, 
					configurationItem.getValue(), 
					configurationItem.getMatch(), 
					configurationItem.getGroup());
					
			if ( NumberUtils.isCreatable(valueStr) )
    		{
    			value = new BigDecimal(valueStr);
    		}
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
		}
		
		return value;
	}
}

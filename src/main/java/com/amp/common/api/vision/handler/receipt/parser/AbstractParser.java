/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.parser;

import java.math.BigDecimal;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.vision.handler.receipt.config.ConfigurationItem;
import com.amp.common.api.vision.utils.RegexParser;
import com.google.cloud.vision.v1.TextAnnotation;
import com.jayway.jsonpath.DocumentContext;

/**
 * @author mveksler
 *
 */
public class AbstractParser implements IDataParser
{
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(AbstractParser.class);

	protected final String PARSER_VALUE_NOT_FOUND = "NA";
	//---
	public String handleStringDataWithJsonPath(
			DocumentContext jsonContext, 
			ConfigurationItem configurationItem)
	{
		String cMethodName = "";
		
		String value = null;
		 
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
        		value = (String)data.get(0);
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
	public String handleStringDataWithJsonRegex(
			TextAnnotation receiptAnnotation,
			ConfigurationItem configurationItem,
			boolean... flags)
	{
		String cMethodName = "";
		
		String value = null;
		
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
	        		
	        value = regexParser.getGroupValueByRegex(
	        		payloadContentText, 
					configurationItem.getValue(), 
					configurationItem.getMatch(), 
					configurationItem.getGroup(),
					flags);
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
		}
		
		return value;
	}
	
	//---
	public BigDecimal handleDecimalDataWithJsonPath(
			DocumentContext jsonContext, 
			ConfigurationItem configurationItem)
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
	public BigDecimal handleDecimalDataWithJsonRegex(
			TextAnnotation receiptAnnotation,
			ConfigurationItem configurationItem, 
			boolean... flags)
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

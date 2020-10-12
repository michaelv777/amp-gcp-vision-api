/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.parser;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.vision.dto.ReceiptItemDTO;
import com.amp.common.api.vision.handler.receipt.config.ConfigurationItem;
import com.amp.common.api.vision.handler.receipt.config.ReceiptConfiguration;
import com.google.cloud.vision.v1.TextAnnotation;
import com.jayway.jsonpath.DocumentContext;

/**
 * @author mveksler
 *
 */
public class ItemsDataParser extends AbstractParser
{
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(ItemsDataParser.class);
	
	public Set<ReceiptItemDTO> handleData(
			DocumentContext jsonContext, 
			TextAnnotation receiptAnnotation, 
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		Set<ReceiptItemDTO> itemsSet = new LinkedHashSet<ReceiptItemDTO>();
		
		//List<ReceiptItemDTO> itemsList = new LinkedList<ReceiptItemDTO>();
				
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        List<String> itemsListStr = this.getItemsData(
	        		jsonContext, receiptAnnotation, receiptConfig);
	        
	        
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
		}
		
		/*
		if ( itemsList != null )
		{
			itemsSet = new LinkedHashSet<ReceiptItemDTO>(itemsList);
		}
		*/
		
		return itemsSet;
	}
	
	public List<String> getItemsData(
			DocumentContext jsonContext, 
			TextAnnotation receiptAnnotation, 
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		List<String> values = new LinkedList<String>(); 
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        Map<Integer, ConfigurationItem> configurationItemsMap = new 
	        		TreeMap<Integer, ConfigurationItem>();
	        
	        for( ConfigurationItem configurationItem : receiptConfig.getItemsData().getItemsDetails() )
	        {
	        	configurationItemsMap.put(configurationItem.getPriority(), configurationItem);
	        }
	        
	        for( Map.Entry<Integer, ConfigurationItem> configurationItemEntry : configurationItemsMap.entrySet() )
	        {
	        	ConfigurationItem configurationItem = configurationItemEntry.getValue();
	        	
	        	String configType = configurationItem.getType();
	        	
	        	if ( configType.equalsIgnoreCase(ConfigurationType.JSON_REGEX.getConfigurationType()) )
	        	{
	        		List<String> valuesStr = this.handleStringsListDataWithJsonRegex(receiptAnnotation, configurationItem);
	        		
	        		if ( valuesStr != null && !valuesStr.isEmpty())
		        	{
	        			values.addAll(valuesStr);
		        	}
	        	}
	        }
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
		}
		
		return values;
	}
}

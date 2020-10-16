/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.vision.dto.ReceiptItemDTOWrapper;
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
	
	public ReceiptItemDTOWrapper handleData(
			DocumentContext jsonContext, 
			TextAnnotation receiptAnnotation, 
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		ReceiptItemDTOWrapper itemsData = new ReceiptItemDTOWrapper();
				
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        List<String> items = this.getItemsDetails(jsonContext, receiptAnnotation, receiptConfig);
	        itemsData.setItems(items);
	        
	        List<String> prices = this.getItemsPrices(jsonContext, receiptAnnotation, receiptConfig);
	        itemsData.setPrices(prices);
	        
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
		}
		
		return itemsData;
	}
	
	public List<String> getItemsDetails(
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
	
	public List<String> getItemsPrices(
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
	        
	        for( ConfigurationItem configurationItem : receiptConfig.getItemsData().getItemsPrices() )
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

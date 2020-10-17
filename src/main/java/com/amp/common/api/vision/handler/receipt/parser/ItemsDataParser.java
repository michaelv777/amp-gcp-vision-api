/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.parser;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.vision.dto.ReceiptItemDTO;
import com.amp.common.api.vision.dto.ReceiptItemDTOWrapper;
import com.amp.common.api.vision.handler.receipt.config.ItemsDetailsConfiguration;
import com.amp.common.api.vision.handler.receipt.config.ReceiptConfiguration;
import com.google.cloud.vision.v1.TextAnnotation;
import com.jayway.jsonpath.DocumentContext;

/**
 * @author mveksler
 *
 */
public class ItemsDataParser extends AbstractParser implements IItemsDataParser
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
	        
	        itemsData = this.getItemsDetails(jsonContext, receiptAnnotation, receiptConfig);
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
		}
		
		return itemsData;
	}
	
	public ReceiptItemDTOWrapper getItemsDetails(
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
	        
	        Map<Integer, ItemsDetailsConfiguration> configurationItemsMap = new 
	        		TreeMap<Integer, ItemsDetailsConfiguration>();
	        
	        for( ItemsDetailsConfiguration configurationItem : receiptConfig.getItemsData().getItemsDetails() )
	        {
	        	configurationItemsMap.put(configurationItem.getPriority(), configurationItem);
	        }
	        
	        for( Map.Entry<Integer, ItemsDetailsConfiguration> configurationItemEntry : configurationItemsMap.entrySet() )
	        {
	        	ItemsDetailsConfiguration configurationItem = configurationItemEntry.getValue();
	        	
	        	String configType = configurationItem.getType();
	        	
	        	if ( configType.equalsIgnoreCase(ConfigurationType.JSON_REGEX.getConfigurationType()) )
	        	{	        		
	        		List<String> items = this.handleStringsListDataWithJsonRegex(receiptAnnotation, configurationItem);
	        		if ( items != null && !items.isEmpty())
		        	{
	        			itemsData.getItems().addAll(items);
	        			
	        			itemsData.getItemsSet().addAll(this.parseItemsData(items, configurationItem));
		        	}
	        		//---
	        	}
	        }
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
		}
		
		return itemsData;
	}

	@Override
	public Set<ReceiptItemDTO> parseItemsData(
			List<String> items, ItemsDetailsConfiguration configurationItem)
	{
		return new LinkedHashSet<ReceiptItemDTO>();
	}
	
}

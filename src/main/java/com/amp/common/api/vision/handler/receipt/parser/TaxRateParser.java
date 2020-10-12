/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.parser;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.vision.handler.RequestHandlerBase;
import com.amp.common.api.vision.handler.receipt.config.ConfigurationItem;
import com.amp.common.api.vision.handler.receipt.config.ReceiptConfiguration;
import com.google.cloud.vision.v1.TextAnnotation;
import com.jayway.jsonpath.DocumentContext;

/**
 * @author mveksler
 *
 */
public class TaxRateParser extends AbstractParser
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
	        
	        Map<Integer, ConfigurationItem> configurationItemsMap = new 
	        		TreeMap<Integer, ConfigurationItem>();
	        
	        for( ConfigurationItem configurationItem : receiptConfig.getTaxRate().getConfigurationItems() )
	        {
	        	configurationItemsMap.put(configurationItem.getPriority(), configurationItem);
	        }
	        
	        for( Map.Entry<Integer, ConfigurationItem> configurationItemEntry : configurationItemsMap.entrySet() )
	        {
	        	ConfigurationItem configurationItem = configurationItemEntry.getValue();
	        	
	        	String configType = configurationItem.getType();
	        	
	        	if ( configType.equalsIgnoreCase(ConfigurationType.JSON_REGEX.getConfigurationType()) )
	        	{
	        		value = this.handleDecimalDataWithJsonRegex(receiptAnnotation, configurationItem);
	        	}
	        	else if ( configType.equalsIgnoreCase(ConfigurationType.JSON_PATH.getConfigurationType()) )
	        	{
	        		value = this.handleDecimalDataWithJsonPath(jsonContext, configurationItem);
	        	}
	        	else
	        	{
	        		value = this.handleDecimalDataWithJsonRegex(receiptAnnotation, configurationItem);
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
}

/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.parser;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.vision.dto.StoreDTO;
import com.amp.common.api.vision.handler.receipt.config.ConfigurationItem;
import com.amp.common.api.vision.handler.receipt.config.ReceiptConfiguration;
import com.google.cloud.vision.v1.TextAnnotation;
import com.jayway.jsonpath.DocumentContext;

/**
 * @author mveksler
 *
 */
public class StoreParser extends AbstractParser
{
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(StoreParser.class);
	
	public StoreDTO handleData(
			DocumentContext jsonContext, 
			TextAnnotation receiptAnnotation, 
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		StoreDTO value = new StoreDTO();
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        value.setName(this.getName(
	        		jsonContext, receiptAnnotation, receiptConfig));
	        
	        value.setAddress1(this.getStreetAddress(
	        		jsonContext, receiptAnnotation, receiptConfig));
	        
	        value.setCity(this.getCity(
	        		jsonContext, receiptAnnotation, receiptConfig));
	        
	        value.setPostalCode(this.getPostalCode(
	        		jsonContext, receiptAnnotation, receiptConfig));
	        
	        value.setProvince(this.getProvince(
	        		jsonContext, receiptAnnotation, receiptConfig));
	        
	        value.setCountry(this.getCountry(
	        		jsonContext, receiptAnnotation, receiptConfig));
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
		}
		
		return value;
	}
	
	public String getName(
			DocumentContext jsonContext, 
			TextAnnotation receiptAnnotation, 
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		String value = null;
		
		boolean isLowerCase = true;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        Map<Integer, ConfigurationItem> configurationItemsMap = new 
	        		TreeMap<Integer, ConfigurationItem>();
	        
	        for( ConfigurationItem configurationItem : receiptConfig.getStore().getNameConfiguration() )
	        {
	        	configurationItemsMap.put(configurationItem.getPriority(), configurationItem);
	        }
	        
	        for( Map.Entry<Integer, ConfigurationItem> configurationItemEntry : configurationItemsMap.entrySet() )
	        {
	        	ConfigurationItem configurationItem = configurationItemEntry.getValue();
	        	
	        	String configType = configurationItem.getType();
	        	
	        	if ( configType.equalsIgnoreCase(ConfigurationType.JSON_REGEX.getConfigurationType()) )
	        	{
	        		value = this.handleStringDataWithJsonRegex(receiptAnnotation, configurationItem, isLowerCase);
	        	}
	        	else if ( configType.equalsIgnoreCase(ConfigurationType.JSON_PATH.getConfigurationType()) )
	        	{
	        		value = this.handleStringDataWithJsonPath(jsonContext, configurationItem);
	        	}
	        	else
	        	{
	        		value = this.handleStringDataWithJsonRegex(receiptAnnotation, configurationItem, isLowerCase);
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
		
		return (StringUtils.isNotBlank(value) ? value : PARSER_VALUE_NOT_FOUND);
	}
	
	public String getStreetAddress(
			DocumentContext jsonContext, 
			TextAnnotation receiptAnnotation, 
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		String value = StringUtils.EMPTY;
		
		boolean isLowerCase = true;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        Map<Integer, ConfigurationItem> configurationItemsMap = new 
	        		TreeMap<Integer, ConfigurationItem>();
	        
	        for( ConfigurationItem configurationItem : receiptConfig.getStore().getConfigurationItemsStreetAddress() )
	        {
	        	configurationItemsMap.put(configurationItem.getPriority(), configurationItem);
	        }
	        
	        for( Map.Entry<Integer, ConfigurationItem> configurationItemEntry : configurationItemsMap.entrySet() )
	        {
	        	ConfigurationItem configurationItem = configurationItemEntry.getValue();
	        	
	        	String configType = configurationItem.getType();
	        	
	        	if ( configType.equalsIgnoreCase(ConfigurationType.JSON_REGEX.getConfigurationType()) )
	        	{
	        		value = this.handleStringDataWithJsonRegex(receiptAnnotation, configurationItem, isLowerCase);
	        	}
	        	else if ( configType.equalsIgnoreCase(ConfigurationType.JSON_PATH.getConfigurationType()) )
	        	{
	        		value = this.handleStringDataWithJsonPath(jsonContext, configurationItem);
	        	}
	        	else
	        	{
	        		value = this.handleStringDataWithJsonRegex(receiptAnnotation, configurationItem, isLowerCase);
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
	public String getCity(
			DocumentContext jsonContext, 
			TextAnnotation receiptAnnotation, 
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		String value = StringUtils.EMPTY;
		
		boolean isLowerCase = true;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        Map<Integer, ConfigurationItem> configurationItemsMap = new 
	        		TreeMap<Integer, ConfigurationItem>();
	        
	        for( ConfigurationItem configurationItem : receiptConfig.getStore().getCityConfiguration() )
	        {
	        	configurationItemsMap.put(configurationItem.getPriority(), configurationItem);
	        }
	        
	        for( Map.Entry<Integer, ConfigurationItem> configurationItemEntry : configurationItemsMap.entrySet() )
	        {
	        	ConfigurationItem configurationItem = configurationItemEntry.getValue();
	        	
	        	String configType = configurationItem.getType();
	        	
	        	if ( configType.equalsIgnoreCase(ConfigurationType.JSON_REGEX.getConfigurationType()) )
	        	{
	        		value = this.handleStringDataWithJsonRegex(receiptAnnotation, configurationItem, isLowerCase);
	        	}
	        	else if ( configType.equalsIgnoreCase(ConfigurationType.JSON_PATH.getConfigurationType()) )
	        	{
	        		value = this.handleStringDataWithJsonPath(jsonContext, configurationItem);
	        	}
	        	else
	        	{
	        		value = this.handleStringDataWithJsonRegex(receiptAnnotation, configurationItem, isLowerCase);
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
	public String getProvince(
			DocumentContext jsonContext, 
			TextAnnotation receiptAnnotation, 
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		String value = StringUtils.EMPTY;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        Map<Integer, ConfigurationItem> configurationItemsMap = new 
	        		TreeMap<Integer, ConfigurationItem>();
	        
	        for( ConfigurationItem configurationItem : receiptConfig.getStore().getProvinceConfiguration())
	        {
	        	configurationItemsMap.put(configurationItem.getPriority(), configurationItem);
	        }
	        
	        for( Map.Entry<Integer, ConfigurationItem> configurationItemEntry : configurationItemsMap.entrySet() )
	        {
	        	ConfigurationItem configurationItem = configurationItemEntry.getValue();
	        	
	        	String configType = configurationItem.getType();
	        	
	        	if ( configType.equalsIgnoreCase(ConfigurationType.JSON_REGEX.getConfigurationType()) )
	        	{
	        		value = this.handleStringDataWithJsonRegex(receiptAnnotation, configurationItem);
	        	}
	        	else if ( configType.equalsIgnoreCase(ConfigurationType.JSON_PATH.getConfigurationType()) )
	        	{
	        		value = this.handleStringDataWithJsonPath(jsonContext, configurationItem);
	        	}
	        	else
	        	{
	        		value = this.handleStringDataWithJsonRegex(receiptAnnotation, configurationItem);
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
	public String getPostalCode(
			DocumentContext jsonContext, 
			TextAnnotation receiptAnnotation, 
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		String value = StringUtils.EMPTY;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        Map<Integer, ConfigurationItem> configurationItemsMap = new 
	        		TreeMap<Integer, ConfigurationItem>();
	        
	        for( ConfigurationItem configurationItem : receiptConfig.getStore().getPostalCodeConfiguration())
	        {
	        	configurationItemsMap.put(configurationItem.getPriority(), configurationItem);
	        }
	        
	        for( Map.Entry<Integer, ConfigurationItem> configurationItemEntry : configurationItemsMap.entrySet() )
	        {
	        	ConfigurationItem configurationItem = configurationItemEntry.getValue();
	        	
	        	String configType = configurationItem.getType();
	        	
	        	if ( configType.equalsIgnoreCase(ConfigurationType.JSON_REGEX.getConfigurationType()) )
	        	{
	        		value = this.handleStringDataWithJsonRegex(receiptAnnotation, configurationItem);
	        	}
	        	else if ( configType.equalsIgnoreCase(ConfigurationType.JSON_PATH.getConfigurationType()) )
	        	{
	        		value = this.handleStringDataWithJsonPath(jsonContext, configurationItem);
	        	}
	        	else
	        	{
	        		value = this.handleStringDataWithJsonRegex(receiptAnnotation, configurationItem);
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
		
		return (StringUtils.isNotBlank(value) ? value : PARSER_VALUE_NOT_FOUND);
	}
	
	//---
	public String getCountry(
			DocumentContext jsonContext, 
			TextAnnotation receiptAnnotation, 
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		String value = StringUtils.EMPTY;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        Map<Integer, ConfigurationItem> configurationItemsMap = new 
	        		TreeMap<Integer, ConfigurationItem>();
	        
	        for( ConfigurationItem configurationItem : receiptConfig.getStore().getCountryConfiguration())
	        {
	        	configurationItemsMap.put(configurationItem.getPriority(), configurationItem);
	        }
	        
	        for( Map.Entry<Integer, ConfigurationItem> configurationItemEntry : configurationItemsMap.entrySet() )
	        {
	        	ConfigurationItem configurationItem = configurationItemEntry.getValue();
	        	
	        	String configType = configurationItem.getType();
	        	
	        	if ( configType.equalsIgnoreCase(ConfigurationType.JSON_REGEX.getConfigurationType()) )
	        	{
	        		value = this.handleStringDataWithJsonRegex(receiptAnnotation, configurationItem);
	        	}
	        	else if ( configType.equalsIgnoreCase(ConfigurationType.JSON_PATH.getConfigurationType()) )
	        	{
	        		value = this.handleStringDataWithJsonPath(jsonContext, configurationItem);
	        	}
	        	else
	        	{
	        		value = this.handleStringDataWithJsonRegex(receiptAnnotation, configurationItem);
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
		
		return (StringUtils.isNotBlank(value) ? value : PARSER_VALUE_NOT_FOUND);
	}
}

/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.parser.vendor;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.vision.dto.ReceiptItemDTO;
import com.amp.common.api.vision.handler.receipt.config.ItemsDetailsConfiguration;
import com.amp.common.api.vision.handler.receipt.config.ItemsParserConfiguration;
import com.amp.common.api.vision.handler.receipt.parser.ItemsDataParser;

/**
 * @author mveksler
 *
 */
public class ItemsDataParserHomedepot extends ItemsDataParser
{
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(ItemsDataParserHomedepot.class);

	@Override
	public Set<ReceiptItemDTO> parseItemsData(
			List<String> items, ItemsDetailsConfiguration configurationItem) 
	{
		String cMethodName = "";
		
		Set<ReceiptItemDTO> itemsSet = new LinkedHashSet<ReceiptItemDTO>();
				
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        for( String item : items )
	        {
	        	int dataPrefixIndex = -1;
	        	int dataSuffixIndex = -1;
	        	String dataPrefix = StringUtils.EMPTY;
	        	String dataSuffix = StringUtils.EMPTY;
	        	
	        	for ( ItemsParserConfiguration parserConfiguration : configurationItem.getItemsParser())
		        {
		        	if ( StringUtils.isNotBlank(parserConfiguration.getItemsDelimiter()) )
		        	{
		        		dataPrefixIndex = item.lastIndexOf(parserConfiguration.getItemsDelimiter());
		        		if ( dataPrefixIndex != -1 )
		        		{
				        	dataPrefix = item.substring(0, dataPrefixIndex);
				        	dataSuffix = item.substring(dataPrefixIndex + parserConfiguration.getItemsDelimiter().length());
				        	
				        	String[] dataPrefixDel = dataPrefix.split(parserConfiguration.getItemsDelimiter());
				        	
				        	
		        		}
		        	}
		        }
	        }
		}
		catch( Exception e )
		{
			LOGGER.error(cMethodName + "::Exception:" + e.getMessage(), e);
		}
		
		return itemsSet;
	}
}

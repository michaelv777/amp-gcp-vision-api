/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.parser.vendor;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.vision.dto.ReceiptItemDTO;
import com.amp.common.api.vision.handler.receipt.config.ItemsDetailsConfiguration;
import com.amp.common.api.vision.handler.receipt.config.ItemsParserConfiguration;
import com.amp.common.api.vision.handler.receipt.parser.ItemsDataParser;
import com.amp.common.api.vision.utils.RegexParser;

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
	        	for ( ItemsParserConfiguration parserConfiguration : configurationItem.getItemsParser())
		        {
		        	if ( StringUtils.isNotBlank(parserConfiguration.getItemsDelimiter()) )
		        	{
		        		int itemsDataPrefixIndex = item.lastIndexOf(parserConfiguration.getItemsDelimiter());
		        		
		        		/*
		        		 * Get Items Details
		        		 */
		        		if ( itemsDataPrefixIndex != -1 )
		        		{
		        			String itemsDetails = item.substring(0, itemsDataPrefixIndex);
							itemsDetails = itemsDetails.replaceAll(PARSER_CR_NL_REGEX, StringUtils.EMPTY).trim();
		        			
		        			String itemsPrices = item.substring(itemsDataPrefixIndex + parserConfiguration.getItemsDelimiter().length());
		        			itemsPrices = itemsPrices.replaceAll(PARSER_CR_NL_REGEX, StringUtils.SPACE).trim();
		        			
				        	String[] itemsDetailsDel = itemsDetails.split(parserConfiguration.getItemsDelimiter());
				        	String[] itemsPricesDel  = itemsPrices.split(parserConfiguration.getItemsPricesDelimiter());
				        	int itemsPricesIdx = 0;
				        	
			        		for( String itemDetailsDel : itemsDetailsDel)
			        		{
			        			//String[] itemProperties = itemDetailsDel.trim().split(parserConfiguration.getItemsFieldsDelimiter());
			        			
			        			String itemCode = new RegexParser().getGroupValueByRegex(
			        					itemDetailsDel, 
			        					parserConfiguration.getItemCodePattern(), 
			        					1, 
			        					parserConfiguration.getItemCodePatternGroup());
			        			
			        			String itemName = new RegexParser().getGroupValueByRegex(
			        					itemDetailsDel, 
			        					parserConfiguration.getItemNamePattern(), 
			        					1, 
			        					parserConfiguration.getItemNamePatternGroup()); 
			        			
			        			ReceiptItemDTO itemDTO = new ReceiptItemDTO();
			        			itemDTO.setName(itemCode + StringUtils.SPACE + itemName.trim());
			        			itemsSet.add(itemDTO);
			        			
			        			/*
				        		 * Get Item Price/Qty details
				        		 */
			        			String quantityPriceStr  = StringUtils.EMPTY;
			        			String itemTotalPriceStr = StringUtils.EMPTY;
			        			
			        			//---prices parsing
			        			if ( (itemsPricesDel.length - itemsPricesIdx) >= 2)
			        			{
			        				quantityPriceStr  = itemsPricesDel[itemsPricesIdx].trim();
			        				itemTotalPriceStr = itemsPricesDel[itemsPricesIdx + 1].trim();
			        				
			        				if ( quantityPriceStr.indexOf("@") != -1 )
			        				{
			        					itemsPricesIdx = this.setMultipleQuantityPrice(
			        							itemDTO, quantityPriceStr, itemTotalPriceStr, itemsPricesIdx);
			        				}
			        				else
			        				{
			        					itemsPricesIdx = this.setSingleQuantityPrice(
			        							itemDTO, quantityPriceStr, itemsPricesIdx);
			        				}
			        			}
			        			else if ( (itemsPricesDel.length - itemsPricesIdx) == 1)
			        			{
			        				itemsPricesIdx = this.setSingleQuantityPrice(
			        							itemDTO, itemsPricesDel[itemsPricesIdx], itemsPricesIdx);
			        			}
			        			else
			        			{
			        				LOGGER.error(cMethodName + "::Exception:Prices array does not match the items array!");
			        			}
			        		}
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


	protected int setSingleQuantityPrice(
			ReceiptItemDTO itemDTO, 
			String itemTotalPriceStr,
			int itemsPricesIdx)
	{
		double itemTotalPrice = 0;
		
		if ( NumberUtils.isParsable(itemTotalPriceStr))
		{
			itemTotalPrice = Double.valueOf(itemTotalPriceStr);
			itemDTO.setPrice(new BigDecimal(itemTotalPrice).setScale(2, BigDecimal.ROUND_UP));
			itemDTO.setQuantity(1);
		}
		
		return (itemsPricesIdx + 1);
	}
	
	
	protected int setMultipleQuantityPrice(
			ReceiptItemDTO itemDTO,
			String quantityPriceStr, 
			String itemTotalPriceStr, 
			int itemsPricesIdx)
	{
		int quantity = 0;
		double itemTotalPrice = 0;
		//i.e. 13@6.69 86.97
		
		String[] quantityPriceStrDel = quantityPriceStr.split("@");
		if ( quantityPriceStrDel.length >= 1 )
		{
			if ( NumberUtils.isParsable(quantityPriceStrDel[0]))
			{
				quantity = Integer.valueOf(quantityPriceStrDel[0]);
				itemDTO.setQuantity(quantity);
			}
			
			if ( NumberUtils.isParsable(itemTotalPriceStr))
			{
				itemTotalPrice = Double.valueOf(itemTotalPriceStr);
				itemDTO.setPrice(new BigDecimal(itemTotalPrice).setScale(2, BigDecimal.ROUND_UP));
			}
		}
		
		return (itemsPricesIdx + 2);
	}
}

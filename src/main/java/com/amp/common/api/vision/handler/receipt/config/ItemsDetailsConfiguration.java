/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.config;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author mveksler
 *
 */
public class ItemsDetailsConfiguration extends ConfigurationItem
{
	private static final long serialVersionUID = 1L;
	
	@SerializedName("itemsParser")
	@Expose
	public List<ItemsParserConfiguration> itemsParser = 
		new ArrayList<ItemsParserConfiguration>();

	/**
	 * @return the itemsParser
	 */
	public List<ItemsParserConfiguration> getItemsParser() {
		return itemsParser;
	}

	/**
	 * @param itemsParser the itemsParser to set
	 */
	public void setItemsParser(List<ItemsParserConfiguration> itemsParser) {
		this.itemsParser = itemsParser;
	}
}

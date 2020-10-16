/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author mveksler
 *
 */
public class ItemsDataConfiguration implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("itemsDetails")
	@Expose
	public List<ConfigurationItem> itemsDetails = 
		new ArrayList<ConfigurationItem>();

	@SerializedName("itemsPrices")
	@Expose
	public List<ConfigurationItem> itemsPrices = 
		new ArrayList<ConfigurationItem>();

	@SerializedName("itemsDataParser")
	@Expose
	public List<ItemsDataParserConfiguration> itemsDataParser = 
		new ArrayList<ItemsDataParserConfiguration>();
	
	@SerializedName("itemsPricesParser")
	@Expose
	public List<ItemsDataParserConfiguration> itemsPricesParser = 
		new ArrayList<ItemsDataParserConfiguration>();
	
	/**
	 * @return the itemsDetails
	 */
	public List<ConfigurationItem> getItemsDetails() {
		return itemsDetails;
	}

	/**
	 * @param itemsDetails the itemsDetails to set
	 */
	public void setItemsDetails(List<ConfigurationItem> itemsDetails) {
		this.itemsDetails = itemsDetails;
	}

	/**
	 * @return the itemsPrices
	 */
	public List<ConfigurationItem> getItemsPrices() {
		return itemsPrices;
	}

	/**
	 * @param itemsPrices the itemsPrices to set
	 */
	public void setItemsPrices(List<ConfigurationItem> itemsPrices) {
		this.itemsPrices = itemsPrices;
	}

	/**
	 * @return the itemsDataParser
	 */
	public List<ItemsDataParserConfiguration> getItemsDataParser() {
		return itemsDataParser;
	}

	/**
	 * @param itemsDataParser the itemsDataParser to set
	 */
	public void setItemsDataParser(List<ItemsDataParserConfiguration> itemsDataParser) {
		this.itemsDataParser = itemsDataParser;
	}

	/**
	 * @return the itemsPricesParser
	 */
	public List<ItemsDataParserConfiguration> getItemsPricesParser() {
		return itemsPricesParser;
	}

	/**
	 * @param itemsPricesParser the itemsPricesParser to set
	 */
	public void setItemsPricesParser(List<ItemsDataParserConfiguration> itemsPricesParser) {
		this.itemsPricesParser = itemsPricesParser;
	}
}

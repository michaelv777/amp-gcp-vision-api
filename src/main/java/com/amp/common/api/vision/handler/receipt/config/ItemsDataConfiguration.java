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
	public List<ConfigurationItem> itemsDetails = new ArrayList<ConfigurationItem>();

	@SerializedName("itemsPrices")
	@Expose
	public List<ConfigurationItem> itemsPrices = new ArrayList<ConfigurationItem>();

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
}

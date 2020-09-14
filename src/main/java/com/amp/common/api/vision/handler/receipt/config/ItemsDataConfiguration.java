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
	public List<String> itemsDetails = new ArrayList<String>();

	@SerializedName("itemsPrices")
	@Expose
	public List<String> itemsPrices = new ArrayList<String>();

	/**
	 * @return the itemsDetails
	 */
	public List<String> getItemsDetails() {
		return itemsDetails;
	}

	/**
	 * @param itemsDetails the itemsDetails to set
	 */
	public void setItemsDetails(List<String> itemsDetails) {
		this.itemsDetails = itemsDetails;
	}

	/**
	 * @return the itemsPrices
	 */
	public List<String> getItemsPrices() {
		return itemsPrices;
	}

	/**
	 * @param itemsPrices the itemsPrices to set
	 */
	public void setItemsPrices(List<String> itemsPrices) {
		this.itemsPrices = itemsPrices;
	}
	
	
}

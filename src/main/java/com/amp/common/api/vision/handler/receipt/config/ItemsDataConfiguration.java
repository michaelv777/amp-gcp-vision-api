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
	private static final long serialVersionUID = 1L;
	
	@SerializedName("itemsDetails")
	@Expose
	public List<ItemsDetailsConfiguration> itemsDetails = 
		new ArrayList<ItemsDetailsConfiguration>();

	/**
	 * @return the itemsDetails
	 */
	public List<ItemsDetailsConfiguration> getItemsDetails() {
		return itemsDetails;
	}

	/**
	 * @param itemsDetails the itemsDetails to set
	 */
	public void setItemsDetails(List<ItemsDetailsConfiguration> itemsDetails) {
		this.itemsDetails = itemsDetails;
	}
}

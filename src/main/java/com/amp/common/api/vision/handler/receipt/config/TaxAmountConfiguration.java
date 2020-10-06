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
public class TaxAmountConfiguration implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("taxAmountConfigutation")
	@Expose
	public List<TaxAmountConfigurationItem> configurationItems = 
		new ArrayList<TaxAmountConfigurationItem>();

	/**
	 * @return the configurationItems
	 */
	public List<TaxAmountConfigurationItem> getConfigurationItems() {
		return configurationItems;
	}

	/**
	 * @param configurationItems the configurationItems to set
	 */
	public void setConfigurationItems(List<TaxAmountConfigurationItem> configurationItems) {
		this.configurationItems = configurationItems;
	}
	
}

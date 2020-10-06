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
public class TaxRateConfiguration implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("taxRateConfigutation")
	@Expose
	public List<TaxRateConfigurationItem> configurationItems = 
		new ArrayList<TaxRateConfigurationItem>();

	/**
	 * @return the configurationItems
	 */
	public List<TaxRateConfigurationItem> getConfigurationItems() {
		return configurationItems;
	}

	/**
	 * @param configurationItems the configurationItems to set
	 */
	public void setConfigurationItems(List<TaxRateConfigurationItem> configurationItems) {
		this.configurationItems = configurationItems;
	}
	
}

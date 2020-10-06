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
public class TotalConfiguration implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("totalConfigutation")
	@Expose
	public List<TotalConfigurationItem> configurationItems = 
		new ArrayList<TotalConfigurationItem>();

	/**
	 * @return the configurationItems
	 */
	public List<TotalConfigurationItem> getConfigurationItems() {
		return configurationItems;
	}

	/**
	 * @param configurationItems the configurationItems to set
	 */
	public void setConfigurationItems(List<TotalConfigurationItem> configurationItems) {
		this.configurationItems = configurationItems;
	}
	
}

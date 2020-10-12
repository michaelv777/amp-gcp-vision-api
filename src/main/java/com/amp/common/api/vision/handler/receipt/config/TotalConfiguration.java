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
	public List<ConfigurationItem> configurationItems = 
		new ArrayList<ConfigurationItem>();

	/**
	 * @return the configurationItems
	 */
	public List<ConfigurationItem> getConfigurationItems() {
		return configurationItems;
	}

	/**
	 * @param configurationItems the configurationItems to set
	 */
	public void setConfigurationItems(List<ConfigurationItem> configurationItems) {
		this.configurationItems = configurationItems;
	}
	
}

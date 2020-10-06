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
public class SubtotalConfiguration implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("subtotalConfigutation")
	@Expose
	public List<SubtotalConfigurationItem> configurationItems = 
		new ArrayList<SubtotalConfigurationItem>();

	/**
	 * @return the configurationItems
	 */
	public List<SubtotalConfigurationItem> getConfigurationItems() {
		return configurationItems;
	}

	/**
	 * @param configurationItems the configurationItems to set
	 */
	public void setConfigurationItems(List<SubtotalConfigurationItem> configurationItems) {
		this.configurationItems = configurationItems;
	}
	
}

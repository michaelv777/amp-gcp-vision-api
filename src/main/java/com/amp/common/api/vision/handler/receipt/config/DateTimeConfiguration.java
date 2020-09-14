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
public class DateTimeConfiguration implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("purchaseDateTimeConfiguration")
	@Expose
	public List<DateTimeConfigurationItem> configurationItems = 
		new ArrayList<DateTimeConfigurationItem>();

	/**
	 * @return the configurationItems
	 */
	public List<DateTimeConfigurationItem> getConfigurationItems() {
		return configurationItems;
	}

	/**
	 * @param configurationItems the configurationItems to set
	 */
	public void setConfigurationItems(List<DateTimeConfigurationItem> configurationItems) {
		this.configurationItems = configurationItems;
	}
	
}

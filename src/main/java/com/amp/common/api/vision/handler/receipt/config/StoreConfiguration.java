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
public class StoreConfiguration implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("streetAddressConfiguration")
	@Expose
	public List<ConfigurationItem> configurationItemsStreetAddress = 
		new ArrayList<ConfigurationItem>();
	
	@SerializedName("streetNumberConfiguration")
	@Expose
	public List<ConfigurationItem> configurationItemsStreetNumber = 
		new ArrayList<ConfigurationItem>();

	@SerializedName("streetNameConfiguration")
	@Expose
	public List<ConfigurationItem> configurationItemsStreetName = 
		new ArrayList<ConfigurationItem>();
	
	/**
	 * @return the configurationItemsStreetNumber
	 */
	public List<ConfigurationItem> getConfigurationItemsStreetNumber() {
		return configurationItemsStreetNumber;
	}

	/**
	 * @param configurationItemsStreetNumber the configurationItemsStreetNumber to set
	 */
	public void setConfigurationItemsStreetNumber(List<ConfigurationItem> configurationItemsStreetNumber) {
		this.configurationItemsStreetNumber = configurationItemsStreetNumber;
	}

	/**
	 * @return the configurationItemsStreetName
	 */
	public List<ConfigurationItem> getConfigurationItemsStreetName() {
		return configurationItemsStreetName;
	}

	/**
	 * @param configurationItemsStreetName the configurationItemsStreetName to set
	 */
	public void setConfigurationItemsStreetName(List<ConfigurationItem> configurationItemsStreetName) {
		this.configurationItemsStreetName = configurationItemsStreetName;
	}

	/**
	 * @return the configurationItemsStreetAddress
	 */
	public List<ConfigurationItem> getConfigurationItemsStreetAddress() {
		return configurationItemsStreetAddress;
	}

	/**
	 * @param configurationItemsStreetAddress the configurationItemsStreetAddress to set
	 */
	public void setConfigurationItemsStreetAddress(List<ConfigurationItem> configurationItemsStreetAddress) {
		this.configurationItemsStreetAddress = configurationItemsStreetAddress;
	}
}

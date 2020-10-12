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
	
	@SerializedName("nameConfiguration")
	@Expose
	public List<ConfigurationItem> nameConfiguration = 
		new ArrayList<ConfigurationItem>();
	
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
	
	@SerializedName("cityConfiguration")
	@Expose
	public List<ConfigurationItem> cityConfiguration = 
		new ArrayList<ConfigurationItem>();
	
	@SerializedName("provinceConfiguration")
	@Expose
	public List<ConfigurationItem> provinceConfiguration = 
		new ArrayList<ConfigurationItem>();
	
	@SerializedName("postalCodeConfiguration")
	@Expose
	public List<ConfigurationItem> postalCodeConfiguration = 
		new ArrayList<ConfigurationItem>();
	
	@SerializedName("countryConfiguration")
	@Expose
	public List<ConfigurationItem> countryConfiguration = 
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

	/**
	 * @return the cityConfiguration
	 */
	public List<ConfigurationItem> getCityConfiguration() {
		return cityConfiguration;
	}

	/**
	 * @param cityConfiguration the cityConfiguration to set
	 */
	public void setCityConfiguration(List<ConfigurationItem> cityConfiguration) {
		this.cityConfiguration = cityConfiguration;
	}

	/**
	 * @return the provinceConfiguration
	 */
	public List<ConfigurationItem> getProvinceConfiguration() {
		return provinceConfiguration;
	}

	/**
	 * @param provinceConfiguration the provinceConfiguration to set
	 */
	public void setProvinceConfiguration(List<ConfigurationItem> provinceConfiguration) {
		this.provinceConfiguration = provinceConfiguration;
	}

	/**
	 * @return the postalCodeConfiguration
	 */
	public List<ConfigurationItem> getPostalCodeConfiguration() {
		return postalCodeConfiguration;
	}

	/**
	 * @param postalCodeConfiguration the postalCodeConfiguration to set
	 */
	public void setPostalCodeConfiguration(List<ConfigurationItem> postalCodeConfiguration) {
		this.postalCodeConfiguration = postalCodeConfiguration;
	}

	/**
	 * @return the countryConfiguration
	 */
	public List<ConfigurationItem> getCountryConfiguration() {
		return countryConfiguration;
	}

	/**
	 * @param countryConfiguration the countryConfiguration to set
	 */
	public void setCountryConfiguration(List<ConfigurationItem> countryConfiguration) {
		this.countryConfiguration = countryConfiguration;
	}

	/**
	 * @return the nameConfiguration
	 */
	public List<ConfigurationItem> getNameConfiguration() {
		return nameConfiguration;
	}

	/**
	 * @param nameConfiguration the nameConfiguration to set
	 */
	public void setNameConfiguration(List<ConfigurationItem> nameConfiguration) {
		this.nameConfiguration = nameConfiguration;
	}
}

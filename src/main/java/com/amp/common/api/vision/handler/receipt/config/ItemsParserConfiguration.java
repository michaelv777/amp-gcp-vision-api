/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.config;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author mveksler
 *
 */
public class ItemsParserConfiguration implements Serializable
{
	private static final long serialVersionUID = 1L;

	@SerializedName("priority")
	@Expose
	public Integer priority = 0;
	
	@SerializedName("itemsDelimiter")
	@Expose
	public String itemsDelimiter = StringUtils.EMPTY;
	
	@SerializedName("itemsFieldsDelimiter")
	@Expose
	public String itemsFieldsDelimiter = StringUtils.EMPTY;
	
	@SerializedName("itemsPricesDelimiter")
	@Expose
	public String itemsPricesDelimiter = StringUtils.EMPTY;
	
	@SerializedName("itemCodePattern")
	@Expose
	public String itemCodePattern = StringUtils.EMPTY;
	
	@SerializedName("itemCodePatternGroup")
	@Expose
	public Integer itemCodePatternGroup = -1;
	
	@SerializedName("itemNamePatternGroup")
	@Expose
	public Integer itemNamePatternGroup = -1;
	
	@SerializedName("itemNamePattern")
	@Expose
	public String itemNamePattern = StringUtils.EMPTY;
    
	@SerializedName("itemPriceIndex")
	@Expose
	public Integer itemPriceIndex = -1;
	
	@SerializedName("itemQuantityIndex")
	@Expose
	public Integer itemQuantityIndex = -1;
	
	/**
	 * @return the itemCodePatternGroup
	 */
	public Integer getItemCodePatternGroup() {
		return itemCodePatternGroup;
	}

	/**
	 * @param itemCodePatternGroup the itemCodePatternGroup to set
	 */
	public void setItemCodePatternGroup(Integer itemCodePatternGroup) {
		this.itemCodePatternGroup = itemCodePatternGroup;
	}

	/**
	 * @return the itemNamePatternGroup
	 */
	public Integer getItemNamePatternGroup() {
		return itemNamePatternGroup;
	}

	/**
	 * @param itemNamePatternGroup the itemNamePatternGroup to set
	 */
	public void setItemNamePatternGroup(Integer itemNamePatternGroup) {
		this.itemNamePatternGroup = itemNamePatternGroup;
	}

	/**
	 * @return the itemCodePattern
	 */
	public String getItemCodePattern() {
		return itemCodePattern;
	}

	/**
	 * @param itemCodePattern the itemCodePattern to set
	 */
	public void setItemCodePattern(String itemCodePattern) {
		this.itemCodePattern = itemCodePattern;
	}

	/**
	 * @return the itemNamePattern
	 */
	public String getItemNamePattern() {
		return itemNamePattern;
	}

	/**
	 * @param itemNamePattern the itemNamePattern to set
	 */
	public void setItemNamePattern(String itemNamePattern) {
		this.itemNamePattern = itemNamePattern;
	}

	/**
	 * @return the itemsDelimiter
	 */
	public String getItemsDelimiter() {
		return itemsDelimiter;
	}

	/**
	 * @param itemsDelimiter the itemsDelimiter to set
	 */
	public void setItemsDelimiter(String itemsDelimiter) {
		this.itemsDelimiter = itemsDelimiter;
	}

	/**
	 * @return the itemsFieldsDelimiter
	 */
	public String getItemsFieldsDelimiter() {
		return itemsFieldsDelimiter;
	}

	/**
	 * @param itemsFieldsDelimiter the itemsFieldsDelimiter to set
	 */
	public void setItemsFieldsDelimiter(String itemsFieldsDelimiter) {
		this.itemsFieldsDelimiter = itemsFieldsDelimiter;
	}

	/**
	 * @return the itemPriceIndex
	 */
	public Integer getItemPriceIndex() {
		return itemPriceIndex;
	}

	/**
	 * @param itemPriceIndex the itemPriceIndex to set
	 */
	public void setItemPriceIndex(Integer itemPriceIndex) {
		this.itemPriceIndex = itemPriceIndex;
	}

	/**
	 * @return the itemQuantityIndex
	 */
	public Integer getItemQuantityIndex() {
		return itemQuantityIndex;
	}

	/**
	 * @param itemQuantityIndex the itemQuantityIndex to set
	 */
	public void setItemQuantityIndex(Integer itemQuantityIndex) {
		this.itemQuantityIndex = itemQuantityIndex;
	}
	
	/**
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	/**
	 * @return the itemsPricesDelimiter
	 */
	public String getItemsPricesDelimiter() {
		return itemsPricesDelimiter;
	}

	/**
	 * @param itemsPricesDelimiter the itemsPricesDelimiter to set
	 */
	public void setItemsPricesDelimiter(String itemsPricesDelimiter) {
		this.itemsPricesDelimiter = itemsPricesDelimiter;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((itemPriceIndex == null) ? 0 : itemPriceIndex.hashCode());
		result = prime * result + ((itemQuantityIndex == null) ? 0 : itemQuantityIndex.hashCode());
		result = prime * result + ((itemsDelimiter == null) ? 0 : itemsDelimiter.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ItemsParserConfiguration)) {
			return false;
		}
		ItemsParserConfiguration other = (ItemsParserConfiguration) obj;
		
		if (itemPriceIndex == null) {
			if (other.itemPriceIndex != null) {
				return false;
			}
		} else if (!itemPriceIndex.equals(other.itemPriceIndex)) {
			return false;
		}
		if (itemQuantityIndex == null) {
			if (other.itemQuantityIndex != null) {
				return false;
			}
		} else if (!itemQuantityIndex.equals(other.itemQuantityIndex)) {
			return false;
		}
		if (itemsDelimiter == null) {
			if (other.itemsDelimiter != null) {
				return false;
			}
		} else if (!itemsDelimiter.equals(other.itemsDelimiter)) {
			return false;
		}
		return true;
	}
}

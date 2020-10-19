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
	
	@SerializedName("itemFieldsCount")
	@Expose
	public Integer itemFieldsCount = 0;

	@SerializedName("itemCodeIndex")
	@Expose
	public Integer itemCodeIndex = -1;
	
	@SerializedName("itemNameIndex")
	@Expose
	public Integer itemNameIndex = -1;
    
	@SerializedName("itemPriceIndex")
	@Expose
	public Integer itemPriceIndex = -1;
	
	@SerializedName("itemQuantityIndex")
	@Expose
	public Integer itemQuantityIndex = -1;
	
	/**
	 * @return the itemCodeIndex
	 */
	public Integer getItemCodeIndex() {
		return itemCodeIndex;
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
	 * @param itemCodeIndex the itemCodeIndex to set
	 */
	public void setItemCodeIndex(Integer itemCodeIndex) {
		this.itemCodeIndex = itemCodeIndex;
	}

	/**
	 * @return the itemNameIndex
	 */
	public Integer getItemNameIndex() {
		return itemNameIndex;
	}

	/**
	 * @param itemNameIndex the itemNameIndex to set
	 */
	public void setItemNameIndex(Integer itemNameIndex) {
		this.itemNameIndex = itemNameIndex;
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
	 * @return the itemFieldsCount
	 */
	public Integer getItemFieldsCount() {
		return itemFieldsCount;
	}

	/**
	 * @param itemFieldsCount the itemFieldsCount to set
	 */
	public void setItemFieldsCount(Integer itemFieldsCount) {
		this.itemFieldsCount = itemFieldsCount;
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
		result = prime * result + ((itemCodeIndex == null) ? 0 : itemCodeIndex.hashCode());
		result = prime * result + ((itemNameIndex == null) ? 0 : itemNameIndex.hashCode());
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
		if (itemCodeIndex == null) {
			if (other.itemCodeIndex != null) {
				return false;
			}
		} else if (!itemCodeIndex.equals(other.itemCodeIndex)) {
			return false;
		}
		if (itemNameIndex == null) {
			if (other.itemNameIndex != null) {
				return false;
			}
		} else if (!itemNameIndex.equals(other.itemNameIndex)) {
			return false;
		}
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

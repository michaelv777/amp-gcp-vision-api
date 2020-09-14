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
public class DateTimeConfigurationItem implements Serializable
{
	private static final long serialVersionUID = 1L;

	@SerializedName("type")
	@Expose
	public Integer priority = 0;
	
	@SerializedName("type")
	@Expose
	public String type = StringUtils.EMPTY;
	
	@SerializedName("purchaseDate")
	@Expose
	public String purchaseDate = StringUtils.EMPTY;

	@SerializedName("purchaseTime")
	@Expose
	public String purchaseTime = StringUtils.EMPTY;
	
	@SerializedName("purchaseAMPM")
	@Expose
	public String purchaseAMPM = StringUtils.EMPTY;
	
	@SerializedName("purchaseDateFormat")
	@Expose
	public String purchaseDateFormat = StringUtils.EMPTY;
	
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	/**
	 * @return the purchaseTime
	 */
	public String getPurchaseTime() {
		return purchaseTime;
	}

	/**
	 * @param purchaseTime the purchaseTime to set
	 */
	public void setPurchaseTime(String purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	/**
	 * @return the purchaseAMPM
	 */
	public String getPurchaseAMPM() {
		return purchaseAMPM;
	}

	/**
	 * @param purchaseAMPM the purchaseAMPM to set
	 */
	public void setPurchaseAMPM(String purchaseAMPM) {
		this.purchaseAMPM = purchaseAMPM;
	}

	/**
	 * @return the purchaseDateFormat
	 */
	public String getPurchaseDateFormat() {
		return purchaseDateFormat;
	}

	/**
	 * @param purchaseDateFormat the purchaseDateFormat to set
	 */
	public void setPurchaseDateFormat(String purchaseDateFormat) {
		this.purchaseDateFormat = purchaseDateFormat;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (!(obj instanceof DateTimeConfigurationItem)) {
			return false;
		}
		DateTimeConfigurationItem other = (DateTimeConfigurationItem) obj;
		if (priority == null) {
			if (other.priority != null) {
				return false;
			}
		} else if (!priority.equals(other.priority)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}
}

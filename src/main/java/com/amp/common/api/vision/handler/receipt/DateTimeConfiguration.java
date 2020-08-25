/**
 * 
 */
package com.amp.common.api.vision.handler.receipt;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

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
		result = prime * result + ((purchaseAMPM == null) ? 0 : purchaseAMPM.hashCode());
		result = prime * result + ((purchaseDate == null) ? 0 : purchaseDate.hashCode());
		result = prime * result + ((purchaseDateFormat == null) ? 0 : purchaseDateFormat.hashCode());
		result = prime * result + ((purchaseTime == null) ? 0 : purchaseTime.hashCode());
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
		if (!(obj instanceof DateTimeConfiguration)) {
			return false;
		}
		DateTimeConfiguration other = (DateTimeConfiguration) obj;
		if (purchaseAMPM == null) {
			if (other.purchaseAMPM != null) {
				return false;
			}
		} else if (!purchaseAMPM.equals(other.purchaseAMPM)) {
			return false;
		}
		if (purchaseDate == null) {
			if (other.purchaseDate != null) {
				return false;
			}
		} else if (!purchaseDate.equals(other.purchaseDate)) {
			return false;
		}
		if (purchaseDateFormat == null) {
			if (other.purchaseDateFormat != null) {
				return false;
			}
		} else if (!purchaseDateFormat.equals(other.purchaseDateFormat)) {
			return false;
		}
		if (purchaseTime == null) {
			if (other.purchaseTime != null) {
				return false;
			}
		} else if (!purchaseTime.equals(other.purchaseTime)) {
			return false;
		}
		return true;
	}
}

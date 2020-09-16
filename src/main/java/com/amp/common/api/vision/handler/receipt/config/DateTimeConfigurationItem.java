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

	@SerializedName("priority")
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
	
	@SerializedName("purchaseDateDayGroup")
	@Expose
	public Integer purchaseDateDayGroup;
	
	@SerializedName("purchaseDateMonthGroup")
	@Expose
	public Integer purchaseDateMonthGroup;
	
	@SerializedName("purchaseDateYearGroup")
	@Expose
	public Integer purchaseDateYearGroup;
     
	@SerializedName("purchaseTimeHourGroup")
	@Expose
	public Integer purchaseTimeHourGroup;
	
	@SerializedName("purchaseTimeHourMinuteGroup")
	@Expose
	public Integer purchaseTimeMinuteGroup;
	
	@SerializedName("purchaseTimeAMPMGroup")
	@Expose
	public Integer purchaseTimeAMPMGroup;
    
	@SerializedName("purchaseDateDayMatch")
	@Expose
	public Integer purchaseDateDayMatch;
	
	@SerializedName("purchaseDateMonthMatch")
	@Expose
	public Integer purchaseDateMonthMatch;
	
	@SerializedName("purchaseDateYearMatch")
	@Expose
	public Integer purchaseDateYearMatch;
	
	@SerializedName("purchaseTimeHourMatch")
	@Expose
	public Integer purchaseTimeHourMatch;
	
	@SerializedName("purchaseTimeMinuteMatch")
	@Expose
	public Integer purchaseTimeMinuteMatch;
	
	@SerializedName("purchaseTimeAMPMMatch")
	@Expose
	public Integer purchaseTimeAMPMMatch;
	
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

	
	/**
	 * @return the purchaseDateDayGroup
	 */
	public Integer getPurchaseDateDayGroup() {
		return purchaseDateDayGroup;
	}

	/**
	 * @param purchaseDateDayGroup the purchaseDateDayGroup to set
	 */
	public void setPurchaseDateDayGroup(Integer purchaseDateDayGroup) {
		this.purchaseDateDayGroup = purchaseDateDayGroup;
	}

	/**
	 * @return the purchaseDateMonthGroup
	 */
	public Integer getPurchaseDateMonthGroup() {
		return purchaseDateMonthGroup;
	}

	/**
	 * @param purchaseDateMonthGroup the purchaseDateMonthGroup to set
	 */
	public void setPurchaseDateMonthGroup(Integer purchaseDateMonthGroup) {
		this.purchaseDateMonthGroup = purchaseDateMonthGroup;
	}

	/**
	 * @return the purchaseDateYearGroup
	 */
	public Integer getPurchaseDateYearGroup() {
		return purchaseDateYearGroup;
	}

	/**
	 * @param purchaseDateYearGroup the purchaseDateYearGroup to set
	 */
	public void setPurchaseDateYearGroup(Integer purchaseDateYearGroup) {
		this.purchaseDateYearGroup = purchaseDateYearGroup;
	}

	/**
	 * @return the purchaseTimeHourGroup
	 */
	public Integer getPurchaseTimeHourGroup() {
		return purchaseTimeHourGroup;
	}

	/**
	 * @param purchaseTimeHourGroup the purchaseTimeHourGroup to set
	 */
	public void setPurchaseTimeHourGroup(Integer purchaseTimeHourGroup) {
		this.purchaseTimeHourGroup = purchaseTimeHourGroup;
	}

	/**
	 * @return the purchaseTimeMinuteGroup
	 */
	public Integer getPurchaseTimeMinuteGroup() {
		return purchaseTimeMinuteGroup;
	}

	/**
	 * @param purchaseTimeMinuteGroup the purchaseTimeMinuteGroup to set
	 */
	public void setPurchaseTimeMinuteGroup(Integer purchaseTimeMinuteGroup) {
		this.purchaseTimeMinuteGroup = purchaseTimeMinuteGroup;
	}

	/**
	 * @return the purchaseTimeAMPMGroup
	 */
	public Integer getPurchaseTimeAMPMGroup() {
		return purchaseTimeAMPMGroup;
	}

	/**
	 * @param purchaseTimeAMPMGroup the purchaseTimeAMPMGroup to set
	 */
	public void setPurchaseTimeAMPMGroup(Integer purchaseTimeAMPMGroup) {
		this.purchaseTimeAMPMGroup = purchaseTimeAMPMGroup;
	}

	
	/**
	 * @return the purchaseDateDayMatch
	 */
	public Integer getPurchaseDateDayMatch() {
		return purchaseDateDayMatch;
	}

	/**
	 * @param purchaseDateDayMatch the purchaseDateDayMatch to set
	 */
	public void setPurchaseDateDayMatch(Integer purchaseDateDayMatch) {
		this.purchaseDateDayMatch = purchaseDateDayMatch;
	}

	/**
	 * @return the purchaseDateMonthMatch
	 */
	public Integer getPurchaseDateMonthMatch() {
		return purchaseDateMonthMatch;
	}

	/**
	 * @param purchaseDateMonthMatch the purchaseDateMonthMatch to set
	 */
	public void setPurchaseDateMonthMatch(Integer purchaseDateMonthMatch) {
		this.purchaseDateMonthMatch = purchaseDateMonthMatch;
	}

	/**
	 * @return the purchaseDateYearMatch
	 */
	public Integer getPurchaseDateYearMatch() {
		return purchaseDateYearMatch;
	}

	/**
	 * @param purchaseDateYearMatch the purchaseDateYearMatch to set
	 */
	public void setPurchaseDateYearMatch(Integer purchaseDateYearMatch) {
		this.purchaseDateYearMatch = purchaseDateYearMatch;
	}

	/**
	 * @return the purchaseTimeHourMatch
	 */
	public Integer getPurchaseTimeHourMatch() {
		return purchaseTimeHourMatch;
	}

	/**
	 * @param purchaseTimeHourMatch the purchaseTimeHourMatch to set
	 */
	public void setPurchaseTimeHourMatch(Integer purchaseTimeHourMatch) {
		this.purchaseTimeHourMatch = purchaseTimeHourMatch;
	}

	/**
	 * @return the purchaseTimeMinuteMatch
	 */
	public Integer getPurchaseTimeMinuteMatch() {
		return purchaseTimeMinuteMatch;
	}

	/**
	 * @param purchaseTimeMinuteMatch the purchaseTimeMinuteMatch to set
	 */
	public void setPurchaseTimeMinuteMatch(Integer purchaseTimeMinuteMatch) {
		this.purchaseTimeMinuteMatch = purchaseTimeMinuteMatch;
	}

	/**
	 * @return the purchaseTimeAMPMMatch
	 */
	public Integer getPurchaseTimeAMPMMatch() {
		return purchaseTimeAMPMMatch;
	}

	/**
	 * @param purchaseTimeAMPMMatch the purchaseTimeAMPMMatch to set
	 */
	public void setPurchaseTimeAMPMMatch(Integer purchaseTimeAMPMMatch) {
		this.purchaseTimeAMPMMatch = purchaseTimeAMPMMatch;
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

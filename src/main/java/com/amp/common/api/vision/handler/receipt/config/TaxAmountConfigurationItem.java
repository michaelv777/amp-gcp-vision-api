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
public class TaxAmountConfigurationItem implements Serializable
{
	private static final long serialVersionUID = 1L;

	@SerializedName("priority")
	@Expose
	public Integer priority = 0;
	
	@SerializedName("type")
	@Expose
	public String type = StringUtils.EMPTY;
	
	@SerializedName("value")
	@Expose
	public String value = StringUtils.EMPTY;
	
	@SerializedName("group")
	@Expose
	public Integer group;
    
	@SerializedName("match")
	@Expose
	public Integer match;
	
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
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the group
	 */
	public Integer getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(Integer group) {
		this.group = group;
	}

	/**
	 * @return the match
	 */
	public Integer getMatch() {
		return match;
	}

	/**
	 * @param match the match to set
	 */
	public void setMatch(Integer match) {
		this.match = match;
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
		if (!(obj instanceof SubtotalConfigurationItem)) {
			return false;
		}
		SubtotalConfigurationItem other = (SubtotalConfigurationItem) obj;
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

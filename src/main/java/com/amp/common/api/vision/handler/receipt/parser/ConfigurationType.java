/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.parser;

import org.apache.commons.lang3.StringUtils;

/**
 * @author mveksler
 *
 */
public enum ConfigurationType 
{
	JSON_REGEX ("json_regex"), 
	JSON_PATH ("json_path");
	
	private String configurationType = StringUtils.EMPTY;

	/**
	 * @return the configurationType
	 */
	public String getConfigurationType() {
		return configurationType;
	}
	
	ConfigurationType(String configurationType)
	{
		this.configurationType = configurationType;
	}
}

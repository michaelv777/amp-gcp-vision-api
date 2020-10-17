/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.parser;

import java.math.BigDecimal;
import java.util.List;

import com.amp.common.api.vision.handler.receipt.config.ConfigurationItem;
import com.google.cloud.vision.v1.TextAnnotation;
import com.jayway.jsonpath.DocumentContext;

/**
 * @author mveksler
 *
 */
public interface IReceiptDataParser 
{
	public String handleStringDataWithJsonRegex(
			TextAnnotation receiptAnnotation,
			ConfigurationItem configurationItem,
			boolean... flags);
	
	public String handleStringDataWithJsonPath(
			DocumentContext jsonContext, 
			ConfigurationItem configurationItem);
	
	public BigDecimal handleDecimalDataWithJsonPath(
			DocumentContext jsonContext, 
			ConfigurationItem configurationItem);
	
	public BigDecimal handleDecimalDataWithJsonRegex(
			TextAnnotation receiptAnnotation,
			ConfigurationItem configurationItem, 
			boolean... flags);
	
	public List<String> handleStringsListDataWithJsonRegex(
			TextAnnotation receiptAnnotation,
			ConfigurationItem configurationItem,
			boolean... flags);
}

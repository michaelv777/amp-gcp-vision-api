/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.parser;

import java.util.List;
import java.util.Set;

import com.amp.common.api.vision.dto.ReceiptItemDTO;
import com.amp.common.api.vision.handler.receipt.config.ItemsDetailsConfiguration;

/**
 * @author mveksler
 *
 */
public interface IItemsDataParser 
{
	public Set<ReceiptItemDTO> parseItemsData(
			List<String> items, 
			ItemsDetailsConfiguration configurationItem);
}

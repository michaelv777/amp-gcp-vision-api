/**
 * 
 */
package com.amp.common.api.vision.dto;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author mveksler
 *
 */
public class ReceiptItemDTOWrapper 
{
	protected Set<ReceiptItemDTO> itemsSet = 
			new LinkedHashSet<ReceiptItemDTO>();
	
	protected List<String> items = 
			new LinkedList<String>();

	/**
	 * @return the itemsSet
	 */
	public Set<ReceiptItemDTO> getItemsSet() {
		return itemsSet;
	}

	/**
	 * @param itemsSet the itemsSet to set
	 */
	public void setItemsSet(Set<ReceiptItemDTO> itemsSet) {
		this.itemsSet = itemsSet;
	}

	/**
	 * @return the items
	 */
	public List<String> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<String> items) {
		this.items = items;
	}
}

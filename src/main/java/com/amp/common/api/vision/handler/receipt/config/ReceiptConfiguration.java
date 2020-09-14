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
public class ReceiptConfiguration implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("purchaseDateTime")
	@Expose
	public DateTimeConfiguration purchaseDateTime;

	@SerializedName("itemsData")
	@Expose
	public ItemsDataConfiguration itemsData;
	
	@SerializedName("subtotal")
	@Expose
	public String subtotal = StringUtils.EMPTY;

	@SerializedName("total")
	@Expose
	public String total = StringUtils.EMPTY;
	
	@SerializedName("taxAmount")
	@Expose
	public String taxAmount = StringUtils.EMPTY;
	
	/**
	 * @return the purchaseDateTime
	 */
	public DateTimeConfiguration getPurchaseDateTime() {
		return purchaseDateTime;
	}

	/**
	 * @param purchaseDateTime the purchaseDateTime to set
	 */
	public void setPurchaseDateTime(DateTimeConfiguration purchaseDateTime) {
		this.purchaseDateTime = purchaseDateTime;
	}

	/**
	 * @return the itemsData
	 */
	public ItemsDataConfiguration getItemsData() {
		return itemsData;
	}

	/**
	 * @param itemsData the itemsData to set
	 */
	public void setItemsData(ItemsDataConfiguration itemsData) {
		this.itemsData = itemsData;
	}

	/**
	 * @return the subtotal
	 */
	public String getSubtotal() {
		return subtotal;
	}

	/**
	 * @param subtotal the subtotal to set
	 */
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}

	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @return the taxAmount
	 */
	public String getTaxAmount() {
		return taxAmount;
	}

	/**
	 * @param taxAmount the taxAmount to set
	 */
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}
}

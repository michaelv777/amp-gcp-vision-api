/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.config;

import java.io.Serializable;

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
	public SubtotalConfiguration subtotal;

	@SerializedName("total")
	@Expose
	public TotalConfiguration total;
	
	@SerializedName("taxRate")
	@Expose
	public TaxRateConfiguration taxRate;
	
	@SerializedName("taxAmount")
	@Expose
	public TaxAmountConfiguration taxAmount;
	
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
	public SubtotalConfiguration getSubtotal() {
		return subtotal;
	}

	/**
	 * @param subtotal the subtotal to set
	 */
	public void setSubtotal(SubtotalConfiguration subtotal) {
		this.subtotal = subtotal;
	}

	/**
	 * @return the total
	 */
	public TotalConfiguration getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(TotalConfiguration total) {
		this.total = total;
	}

	/**
	 * @return the taxRate
	 */
	public TaxRateConfiguration getTaxRate() {
		return taxRate;
	}

	/**
	 * @param taxRate the taxRate to set
	 */
	public void setTaxRate(TaxRateConfiguration taxRate) {
		this.taxRate = taxRate;
	}

	/**
	 * @return the taxAmount
	 */
	public TaxAmountConfiguration getTaxAmount() {
		return taxAmount;
	}

	/**
	 * @param taxAmount the taxAmount to set
	 */
	public void setTaxAmount(TaxAmountConfiguration taxAmount) {
		this.taxAmount = taxAmount;
	}
}

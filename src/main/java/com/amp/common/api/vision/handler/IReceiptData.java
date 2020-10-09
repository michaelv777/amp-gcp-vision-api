/**
 * 
 */
package com.amp.common.api.vision.handler;

import java.math.BigDecimal;
import java.time.Instant;

import com.amp.common.api.vision.handler.receipt.config.ReceiptConfiguration;
import com.google.cloud.vision.v1.TextAnnotation;
import com.jayway.jsonpath.DocumentContext;

/**
 * @author mveksler
 *
 */
public interface IReceiptData 
{
	public Instant getPurchaseDate(
			DocumentContext jsonContext,
			TextAnnotation receiptAnnotation,
			ReceiptConfiguration receiptConfig);
	
	public BigDecimal getTotal(
			DocumentContext jsonContext, 
			TextAnnotation receiptAnnotation,
			ReceiptConfiguration receiptConfig);
	
	public BigDecimal getSubtotal(
			DocumentContext jsonContext, 
			TextAnnotation receiptAnnotation,
			ReceiptConfiguration receiptConfig);
	
	public BigDecimal getTaxRate(
			DocumentContext jsonContext, 
		   	TextAnnotation receiptAnnotation,
		    ReceiptConfiguration receiptConfig);
	
	public BigDecimal getTaxAmount(
			 DocumentContext jsonContext, 
		   	 TextAnnotation receiptAnnotation,
		   	 ReceiptConfiguration receiptConfig);
}

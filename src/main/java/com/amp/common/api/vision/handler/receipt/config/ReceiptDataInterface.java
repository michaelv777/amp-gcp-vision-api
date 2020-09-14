/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.config;

import java.time.Instant;

import com.jayway.jsonpath.DocumentContext;

/**
 * @author mveksler
 *
 */
public interface ReceiptDataInterface 
{
	public Instant getPurchaseDate(
			DocumentContext jsonContext,
			ReceiptConfiguration receiptConfig);
}

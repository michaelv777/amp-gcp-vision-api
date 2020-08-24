/**
 * 
 */
package com.amp.common.api.vision.handler.receipt;

import java.time.Instant;

import com.google.gson.JsonObject;

/**
 * @author mveksler
 *
 */
public interface ReceiptDataInterface 
{
	public Instant getPurchaseDate(
			JsonObject receiptPayload, 
			ReceiptConfiguration receiptConfig);
}

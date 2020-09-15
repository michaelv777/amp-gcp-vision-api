/**
 * 
 */
package com.amp.common.api.vision.handler.receipt.config;

import java.time.Instant;

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
}

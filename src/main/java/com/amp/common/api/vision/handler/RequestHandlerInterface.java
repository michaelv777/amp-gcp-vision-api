/**
 * 
 */
package com.amp.common.api.vision.handler;

import com.amp.common.api.vision.dto.ReceiptDTO;
import com.amp.common.api.vision.jpa.ReceiptConfigurationM;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.gson.JsonObject;

/**
 * @author MVEKSLER
 *
 */
public interface RequestHandlerInterface 
{
	public boolean init();
	
	public ReceiptDTO runProcessData(
			JsonObject receiptPayload,
			TextAnnotation receiptAnnotation,
			ReceiptConfigurationM vendorConfig);
	
	public boolean releaseResources();
	
	
}

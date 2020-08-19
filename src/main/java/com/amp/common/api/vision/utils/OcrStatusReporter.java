/**
 * 
 */
package com.amp.common.api.vision.utils;

/**
 * @author mveksler
 *
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.cloud.gcp.vision.DocumentOcrResultSet;
import org.springframework.util.concurrent.ListenableFuture;

public class OcrStatusReporter 
{
	private final Map<String, OcrOperationStatus> pendingOcrOperations;

	public OcrStatusReporter() {
		this.pendingOcrOperations = new HashMap<>();
	}

	public void registerFuture(
			String documentPath, ListenableFuture<DocumentOcrResultSet> resultFuture) {

		pendingOcrOperations.put(
				documentPath, new OcrOperationStatus(documentPath, resultFuture));
	}

	public Map<String, OcrOperationStatus> getDocumentOcrStatuses() {
		return pendingOcrOperations;
	}

	public static final class OcrOperationStatus {
		final String gcsLocation;
		final ListenableFuture<DocumentOcrResultSet> ocrResultFuture;

		public OcrOperationStatus(
				String gcsLocation,
				ListenableFuture<DocumentOcrResultSet> ocrResultFuture) {
			this.gcsLocation = gcsLocation;
			this.ocrResultFuture = ocrResultFuture;
		}

		public String getGcsLocation() {
			return gcsLocation;
		}

		public boolean isDone() {
			return ocrResultFuture.isDone();
		}

		public DocumentOcrResultSet getResultSet() throws ExecutionException, InterruptedException {
			return ocrResultFuture.get();
		}
	}
}

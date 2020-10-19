/**
 * 
 */
package com.amp.common.api.vision.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.DocumentOcrTemplate;
import org.springframework.core.io.ResourceLoader;

import com.amp.common.api.vision.dto.ReceiptDTO;
import com.amp.common.api.vision.service.OcrParserService;
import com.amp.common.api.vision.utils.OcrResponseParser;
import com.amp.common.api.vision.utils.OcrStatusReporter;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobListOption;
import com.google.cloud.vision.v1.AnnotateFileResponse;
import com.google.cloud.vision.v1.AnnotateFileResponse.Builder;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.protobuf.util.JsonFormat;

/**
 * @author mveksler
 *
 */
public class VisionControllerBase
{
	private final static Logger LOG = 
			LoggerFactory.getLogger(VisionControllerBase.class);
	
	@Autowired 
	protected OcrParserService ocrParserService;
	
	@Autowired 
	protected Storage storage;
	
	@Autowired
	protected ResourceLoader resourceLoader;

	@Autowired
	protected DocumentOcrTemplate documentOcrTemplate;

	protected String ocrBucket;
	
	protected final OcrStatusReporter ocrStatusReporter = 
			new OcrStatusReporter();
	
	/**
	 * @return the storage
	 */
	public Storage getStorage() {
		return storage;
	}

	/**
	 * @param storage the storage to set
	 */
	public void setStorage(Storage storage) {
		this.storage = storage;
	}
	
	/**
	 * @return the ocrParserService
	 */
	public OcrParserService getOcrParserService() {
		return ocrParserService;
	}

	/**
	 * @param ocrParserService the ocrParserService to set
	 */
	public void setOcrParserService(OcrParserService ocrParserService) {
		this.ocrParserService = ocrParserService;
	}
	
	/**
	 * @return the resourceLoader
	 */
	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	/**
	 * @param resourceLoader the resourceLoader to set
	 */
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/**
	 * @return the documentOcrTemplate
	 */
	public DocumentOcrTemplate getDocumentOcrTemplate() {
		return documentOcrTemplate;
	}

	/**
	 * @param documentOcrTemplate the documentOcrTemplate to set
	 */
	public void setDocumentOcrTemplate(DocumentOcrTemplate documentOcrTemplate) {
		this.documentOcrTemplate = documentOcrTemplate;
	}

	/**
	 * @return the ocrBucket
	 */
	public String getOcrBucket() {
		return ocrBucket;
	}

	/**
	 * @param ocrBucket the ocrBucket to set
	 */
	public void setOcrBucket(String ocrBucket) {
		this.ocrBucket = ocrBucket;
	}

	/**
	 * @return the ocrStatusReporter
	 */
	public OcrStatusReporter getOcrStatusReporter() {
		return ocrStatusReporter;
	}

	public List<ReceiptDTO> processDocumentVisionParser(
			  String gcsSourcePath, 
			  String gcsDestinationPath) throws Exception
	{
		List<ReceiptDTO> receiptObjects = new LinkedList<ReceiptDTO>();

		try 
		{
			// ---
			
			JsonObject receiptsPayload = new JsonObject();
			JsonArray receiptsPayloadArray = new JsonArray();
			receiptsPayload.add("results", receiptsPayloadArray);

			JsonObject pathObject = new JsonObject();
			pathObject.addProperty("gcsSourcePath", gcsSourcePath);
			pathObject.addProperty("gcsDestinationPath", gcsDestinationPath);
			receiptsPayload.add("file", pathObject);
			
			Blob firstOutputFile = this.getDocumentFirstOutputBlob(gcsDestinationPath);
					
			// Get the contents of the file and convert the JSON contents to an
			// AnnotateFileResponse
			// object. If the Blob is small read all its content in one request
			// (Note: the file is a .json file)
			// Storage guide: https://cloud.google.com/storage/docs/downloading-objects
			String jsonContents = new String(firstOutputFile.getContent());
			Builder builder = AnnotateFileResponse.newBuilder();
			JsonFormat.parser().merge(jsonContents, builder);

			// Build the AnnotateFileResponse object
			AnnotateFileResponse annotateFileResponse = builder.build();

			// Parse through the object to get the actual response for the first page of the
			// input file.
			AnnotateImageResponse imageResponse = annotateFileResponse.getResponses(0);

			// Here we print the full text from the first page.
			// The response contains more information:
			// annotation/pages/blocks/paragraphs/words/symbols
			// including confidence score and bounding boxes
			JsonObject receiptPayload = new OcrResponseParser().buildResponsePayload(imageResponse);

			receiptsPayloadArray.add(receiptPayload);

			TextAnnotation receiptAnnotation = imageResponse.getFullTextAnnotation();

			ReceiptDTO receiptObject = this.getOcrParserService().
					processVisionApiResponse(receiptPayload, receiptAnnotation);

			receiptObjects.add(receiptObject);
			
			return receiptObjects;
		} 
		catch (Exception e) 
		{
			LOG.error(e.getMessage(), e);

			throw e;
		}
	}
	
	protected Blob getDocumentFirstOutputBlob(String gcsDestinationPath)
	{
		Blob firstOutputFile = null;
		
		// Get the destination location from the gcsDestinationPath
		Pattern pattern = Pattern.compile("gs://([^/]+)/(.+)");
		Matcher matcher = pattern.matcher(gcsDestinationPath);
	
		if (matcher.find())
		{
			String bucketName = matcher.group(1);
			String prefix = matcher.group(2);
			
			// Once the request has completed and the System.output has been
			// written to GCS, we can list all the System.output files.
			//Storage storage = StorageOptions.getDefaultInstance().getService();
			
			// Get the list of objects with the given prefix from the GCS bucket
			Bucket bucket = storage.get(bucketName);
			
			//@SuppressWarnings("unused")
			Page<Blob> pageListAll = bucket.list();
			for (Blob blob : pageListAll.iterateAll()) 
			{
				LOG.info(blob.getName());
			}
			
			Page<Blob> pageList = bucket.list(BlobListOption.prefix(prefix));
			
			// List objects with the given prefix.
			LOG.info("Output files:");
			for (Blob blob : pageList.iterateAll()) 
			{
				LOG.info(blob.getName());
	
				// Process the first System.output file from GCS.
				// Since we specified batch size = 2, the first response contains
				// the first two pages of the input file.
				if (firstOutputFile == null)
				{
					firstOutputFile = blob;
				}
			}
		}
		else
		{
			LOG.info("No MATCH");
		}
		
		return firstOutputFile;
	}
	
	protected boolean isGSDocument(String documentPath)
	{
		
		// Get the destination location from the gcsDestinationPath
		Pattern pattern = Pattern.compile("gs://([^/]+)/(.+)");
		Matcher matcher = pattern.matcher(documentPath);
			
		return matcher.find();
	
	}
}

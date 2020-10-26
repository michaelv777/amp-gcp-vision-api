/**
 * 
 */
package com.amp.common.api.vision.controller;

import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.storage.GoogleStorageLocation;
import org.springframework.cloud.gcp.vision.DocumentOcrResultSet;
import org.springframework.core.io.Resource;
import org.springframework.ui.ModelMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.amp.common.api.vision.application.VisionApiConstants;
import com.amp.common.api.vision.dto.ReceiptDTO;
import com.google.api.client.util.ByteStreams;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage.BucketGetOption;
import com.google.cloud.vision.v1.AsyncAnnotateFileRequest;
import com.google.cloud.vision.v1.AsyncAnnotateFileResponse;
import com.google.cloud.vision.v1.AsyncBatchAnnotateFilesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.GcsDestination;
import com.google.cloud.vision.v1.GcsSource;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.InputConfig;
import com.google.cloud.vision.v1.OperationMetadata;
import com.google.cloud.vision.v1.OutputConfig;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.protobuf.InvalidProtocolBufferException;

@RestController
public class VisionControllerDocument extends VisionControllerBase
{
	private final static Logger LOG = 
			LoggerFactory.getLogger(VisionControllerDocument.class);
	
	
	public VisionControllerDocument() 
	{
		
	}
	
	@GetMapping("/")
	public ModelAndView renderIndex(ModelMap map) 
	{
		map.put("ocrBucket", ocrBucket);
		return new ModelAndView("index", map);
	}

	@GetMapping("/status")
	public ModelAndView renderStatusPage(ModelMap map) 
	{
		map.put("ocrStatuses", ocrStatusReporter.getDocumentOcrStatuses().values());
		return new ModelAndView("status", map);
	}
	
	/**
	   * Performs document text OCR with PDF/TIFF as source files on Google Cloud Storage.
	   *
	   * @param gcsSourcePath The path to the remote file on Google Cloud Storage to detect document
	   *     text on.
	   * @param gcsDestinationPath The path to the remote file on Google Cloud Storage to store the
	   *     results on.
	   * @throws Exception on errors while closing the client.
	   */
	  @GetMapping("/detectDocumentTextGcs")
	  public List<ReceiptDTO> detectDocumentTextGcs(
			  @RequestParam("gcsSourcePath") String gcsSourcePath, 
			  @RequestParam("gcsDestinationPath") String gcsDestinationPath,
			  HttpServletRequest webRequest) throws Exception
	  {
		  ImageAnnotatorClient client = null;
			
		  List<ReceiptDTO> receiptObjects = new LinkedList<ReceiptDTO>();
		  
		  // Initialize client that will be used to send requests. This client only needs to be created
		  // once, and can be reused for multiple requests. After completing all of your requests, call
		  // the "close" method on the client to safely clean up any remaining background resources.
		  try
		  {
			  client = ImageAnnotatorClient.create();
					  
			  List<AsyncAnnotateFileRequest> requests = new ArrayList<>();
	
			  // Set the GCS source path for the remote file.
			  GcsSource gcsSource = GcsSource.newBuilder().setUri(gcsSourcePath).build();
	
			  // Create the configuration with the specified MIME (Multipurpose Internet Mail Extensions)
			  // types
		      InputConfig inputConfig =
		          InputConfig.newBuilder()
		              .setMimeType(
		                  "application/pdf") // Supported MimeTypes: "application/pdf", "image/tiff"
		              .setGcsSource(gcsSource)
		              .build();
	
		      // Set the GCS destination path for where to save the results.
		      GcsDestination gcsDestination =
		          GcsDestination.newBuilder().setUri(gcsDestinationPath).build();
	
		      // Create the configuration for the System.output with the batch size.
		      // The batch size sets how many pages should be grouped into each json System.output file.
		      OutputConfig outputConfig =
		          OutputConfig.newBuilder().setBatchSize(2).setGcsDestination(gcsDestination).build();
	
		      // Select the Feature required by the vision API
		      Feature feature = Feature.newBuilder().setType(Feature.Type.DOCUMENT_TEXT_DETECTION).build();
	
		      // Build the OCR request
		      AsyncAnnotateFileRequest request =
		          AsyncAnnotateFileRequest.newBuilder()
		              .addFeatures(feature)
		              .setInputConfig(inputConfig)
		              .setOutputConfig(outputConfig)
		              .build();
	
		      requests.add(request);
	
		      // Perform the OCR request
		      OperationFuture<AsyncBatchAnnotateFilesResponse, OperationMetadata> response =
		          client.asyncBatchAnnotateFilesAsync(requests);
	
		      LOG.info("Waiting for the operation to finish.");
	
		      // Wait for the request to finish. (The result is not used, since the API saves the result to
		      // the specified location on GCS.)
		      @SuppressWarnings("unused")
		      List<AsyncAnnotateFileResponse> result =
		          response.get(180, TimeUnit.SECONDS).getResponsesList();
	
		      //---

		      receiptObjects =  this.processDocumentVisionParser(
		    		  gcsSourcePath, gcsDestinationPath);
		  }
		  catch( Exception e )
		  {
			  LOG.error(e.getMessage(), e);
		    	
			  throw e;
		  }
		  finally
		  {
			  if ( client != null )
			  {
				  client.close();
			  }
		  }
		  
		  return receiptObjects;
	  }

	@GetMapping("/detectDocumentTextGcsByPage")
	public ModelAndView detectDocumentTextGcsByPage(
			@RequestParam("gcsDocumentUrl") String gcsDocumentUrl,
			@RequestParam("pageNumber") int pageNumber)
			throws ExecutionException, InterruptedException, InvalidProtocolBufferException {
		
		ModelMap map = new ModelMap();
		
		TextAnnotation textAnnotation =
				ocrStatusReporter.getDocumentOcrStatuses()
						.get(gcsDocumentUrl)
						.getResultSet()
						.getPage(pageNumber);

		String[] firstWordsTokens = textAnnotation.getText().split(" ", 50);

		map.put("pageNumber", pageNumber);
		map.put("gcsDocumentUrl", gcsDocumentUrl);
		map.put("text", String.join(" ", firstWordsTokens));

		return new ModelAndView("viewDocument", map);
	}
	
	@GetMapping("/detectDocumentTextGcsByURL")
	public List<ReceiptDTO> detectDocumentTextGcsByURL(
			@RequestParam("documentUrl") String documentUrl,
			HttpServletRequest webRequest) throws Exception 
	{
		
		List<ReceiptDTO> receiptObjects = new LinkedList<ReceiptDTO>();
		
		try
		{
			// Uploads the document to the GCS bucket
			String documentNamePrefix = StringUtils.EMPTY;
			if ( !isGSDocument(documentUrl) )
			{
				documentNamePrefix = VisionApiConstants.BUCKET_INPUT_FOLDER + "/";
			}
			
			Resource documentResource = resourceLoader.getResource(documentUrl);
			BlobId outputBlobId = BlobId.of(ocrBucket, documentNamePrefix + documentResource.getFilename());
			BlobInfo blobInfo = BlobInfo.newBuilder(outputBlobId).
					setContentType(getFileType(documentResource)).build();
	
			try (WriteChannel writer = storage.writer(blobInfo)) 
			{
				ByteStreams.copy(documentResource.getInputStream(), Channels.newOutputStream(writer));
				writer.close();
			}
	
			// Run OCR on the document
			GoogleStorageLocation gcsSourcePath =
					GoogleStorageLocation.forFile(outputBlobId.getBucket(), outputBlobId.getName());
			
			GoogleStorageLocation gcsDestinationPath = GoogleStorageLocation.forFolder(
					outputBlobId.getBucket(), gcsSourcePath.getBlobName().replace(
							VisionApiConstants.BUCKET_INPUT_FOLDER, 
							VisionApiConstants.BUCKET_OUTPUTT_FOLDER));
	
			ListenableFuture<DocumentOcrResultSet> result =
					documentOcrTemplate.runOcrForDocument(gcsSourcePath, gcsDestinationPath);
	
			ocrStatusReporter.registerFuture(gcsSourcePath.uriString(), result);
			ocrStatusReporter.registerFuture(gcsDestinationPath.uriString(), result);
			//---
			
			receiptObjects =  this.processDocumentVisionParser(
		    		  gcsSourcePath.uriString(), gcsDestinationPath.uriString());
		}
		catch( Exception e )
		{
			  LOG.error(e.getMessage(), e);
		    	
			  throw e;
		}
		
		return receiptObjects;
	}
	
	@Value("${application.ocr-bucket}")
	public void setOcrBucket(String ocrBucket)
	{
		try 
		{
			this.storage.get(ocrBucket, BucketGetOption.fields());
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException(
					"The bucket " + ocrBucket + " does not exist. "
							+ "Please specify a valid Google Storage bucket name "
							+ "in the resources/application.properties file. "
							+ "You can create a new bucket at: https://console.cloud.google.com/storage");
		}

		this.ocrBucket = ocrBucket;
	}
	
	private static String getFileType(Resource documentResource)
	{
		int extensionIdx = documentResource.getFilename().lastIndexOf(".");
		String fileType = documentResource.getFilename().substring(extensionIdx);

		switch (fileType) {
			case ".tif":
				return "image/tiff";
			case ".pdf":
				return "application/pdf";
			default:
				throw new IllegalArgumentException("Does not support processing file type: " + fileType);
		}
	}
}

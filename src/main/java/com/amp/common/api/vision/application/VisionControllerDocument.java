/**
 * 
 */
package com.amp.common.api.vision.application;

/**
 * @author mveksler
 *
 */

import java.io.IOException;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.storage.GoogleStorageLocation;
import org.springframework.cloud.gcp.vision.DocumentOcrResultSet;
import org.springframework.cloud.gcp.vision.DocumentOcrTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.ModelMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.amp.common.api.vision.utils.OcrStatusReporter;
import com.google.api.client.util.ByteStreams;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobListOption;
import com.google.cloud.storage.Storage.BucketGetOption;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.vision.v1.AnnotateFileResponse;
import com.google.cloud.vision.v1.AnnotateFileResponse.Builder;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.AsyncAnnotateFileRequest;
import com.google.cloud.vision.v1.AsyncAnnotateFileResponse;
import com.google.cloud.vision.v1.AsyncBatchAnnotateFilesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.GcsDestination;
import com.google.cloud.vision.v1.GcsSource;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.InputConfig;
import com.google.cloud.vision.v1.OperationMetadata;
import com.google.cloud.vision.v1.OutputConfig;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.protobuf.Descriptors;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

@RestController
public class VisionControllerDocument 
{
	private final static Logger LOG = 
			LoggerFactory.getLogger(VisionControllerDocument.class);
	
	private String ocrBucket;

	@Autowired
	private Storage storage;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private DocumentOcrTemplate documentOcrTemplate;

	private final OcrStatusReporter ocrStatusReporter;
	
	public VisionControllerDocument() 
	{
		this.ocrStatusReporter = new OcrStatusReporter();
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
	  public List<String> detectDocumentTextGcs(
			  @RequestParam("gcsSourcePath") String gcsSourcePath, 
			  @RequestParam("gcsDestinationPath") String gcsDestinationPath,
			  HttpServletRequest webRequest)
	      throws Exception
	  {
		  ImageAnnotatorClient client = null;
		  
		  List<String> resultData = new LinkedList<String>();  
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
	
		      // Once the request has completed and the System.output has been
		      // written to GCS, we can list all the System.output files.
		      Storage storage = StorageOptions.getDefaultInstance().getService();
	
		      // Get the destination location from the gcsDestinationPath
		      Pattern pattern = Pattern.compile("gs://([^/]+)/(.+)");
		      Matcher matcher = pattern.matcher(gcsDestinationPath);
	
		      if (matcher.find())
		      {
			        String bucketName = matcher.group(1);
			        String prefix = matcher.group(2);
		
			        // Get the list of objects with the given prefix from the GCS bucket
			        Bucket bucket = storage.get(bucketName);
			        com.google.api.gax.paging.Page<Blob> pageList = bucket.list(BlobListOption.prefix(prefix));
		
			        Blob firstOutputFile = null;
		
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
		
			        // Get the contents of the file and convert the JSON contents to an AnnotateFileResponse
			        // object. If the Blob is small read all its content in one request
			        // (Note: the file is a .json file)
			        // Storage guide: https://cloud.google.com/storage/docs/downloading-objects
			        String jsonContents = new String(firstOutputFile.getContent());
			        Builder builder = AnnotateFileResponse.newBuilder();
			        JsonFormat.parser().merge(jsonContents, builder);
		
			        // Build the AnnotateFileResponse object
			        AnnotateFileResponse annotateFileResponse = builder.build();
		
			        // Parse through the object to get the actual response for the first page of the input file.
			        AnnotateImageResponse annotateImageResponse = annotateFileResponse.getResponses(0);
			       
			        // Here we print the full text from the first page.
			        // The response contains more information:
			        // annotation/pages/blocks/paragraphs/words/symbols
			        // including confidence score and bounding boxes
			        if (annotateImageResponse.hasError()) 
			        {
			        	LOG.error("Error: %s%n", annotateImageResponse.getError().getMessage());
			          
			        	return resultData;
			        }
			        
			        String imageText = String.format("%s%n", annotateImageResponse.getFullTextAnnotation().getText());
			        LOG.info(imageText);
			        
			        //resultData.add(imageText);
			        for ( Map.Entry<Descriptors.FieldDescriptor, Object> cannotations : 
			        		annotateImageResponse.getFullTextAnnotation().getAllFields().entrySet() )
			        {
			        		//String record = String.format("%s : %s%n", k, v.toString());
			        		Descriptors.FieldDescriptor k = cannotations.getKey();
			        		Object v = cannotations.getValue();
			        		
			        		String kv = String.format("%s : %s%n", k, v.toString());
			        		LOG.debug(kv);
			        		
			        		String record = String.format("%s", v.toString());
				          		
				          	resultData.add(record);
			        }
			        
			        
			        /*
			        String imageText = String.format("%s", annotateImageResponse.getFullTextAnnotation().getText());
			        
			        LOG.info(imageText);
			        
			        resultData.add(imageText);
			        resultData.add(System.lineSeparator());
			        */
		      } 
		      else 
		      {
		    	  	LOG.info("No MATCH");
		      }
		      
		      return resultData;
		  }
		  catch( Exception e )
		  {
			  LOG.error(e.getMessage(), e);
			  
			  return resultData;
		  }
		  finally
		    {
		    	if ( client != null )
		    	{
		    		client.close();
		    	}
		    }
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
	
	@GetMapping("/detectDocumentTextGcsExt")
	public ModelAndView detectDocumentTextGcsExt(
			@RequestParam("documentUrl") String documentUrl,
			HttpServletRequest webRequest) throws IOException 
	{

		// Uploads the document to the GCS bucket
		Resource documentResource = resourceLoader.getResource(documentUrl);
		BlobId outputBlobId = BlobId.of(ocrBucket, documentResource.getFilename());
		BlobInfo blobInfo =
				BlobInfo.newBuilder(outputBlobId)
						.setContentType(getFileType(documentResource))
						.build();

		try (WriteChannel writer = storage.writer(blobInfo)) 
		{
			ByteStreams.copy(documentResource.getInputStream(), Channels.newOutputStream(writer));
		}

		// Run OCR on the document
		GoogleStorageLocation documentLocation =
				GoogleStorageLocation.forFile(outputBlobId.getBucket(), outputBlobId.getName());

		GoogleStorageLocation outputLocation = GoogleStorageLocation.forFolder(
				outputBlobId.getBucket(), "output/" + documentLocation.getBlobName());

		ListenableFuture<DocumentOcrResultSet> result =
				documentOcrTemplate.runOcrForDocument(documentLocation, outputLocation);

		ocrStatusReporter.registerFuture(documentLocation.uriString(), result);

		return new ModelAndView("submit_done");
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

/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amp.common.api.vision.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.amp.common.api.vision.utils.OcrResponseParser;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Block;
import com.google.cloud.vision.v1.BoundingPoly;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageSource;
import com.google.cloud.vision.v1.LocalizedObjectAnnotation;
import com.google.cloud.vision.v1.Page;
import com.google.cloud.vision.v1.Paragraph;
import com.google.cloud.vision.v1.Symbol;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.cloud.vision.v1.Vertex;
import com.google.cloud.vision.v1.Word;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors;

/**
 * Code sample demonstrating Cloud Vision usage within the context of Spring Framework using Spring
 * Cloud GCP libraries. The sample is written as a Spring Boot application to demonstrate a
 * practical application of this usage.
 */
@RestController
public class VisionControllerImage 
{
	private final static Logger LOG = 
			LoggerFactory.getLogger(VisionControllerImage.class);
	
	@Autowired private ResourceLoader resourceLoader;

	// [START spring_vision_autowire]
	@Autowired private CloudVisionTemplate cloudVisionTemplate;
	  // [END spring_vision_autowire]
	
	  /**
	   * This method downloads an image from a URL and sends its contents to the Vision API for label
	   * detection.
	   *
	   * @param imageUrl the URL of the image
	   * @param map the model map to use
	   * @return a string with the list of labels and percentage of certainty
	   */
	  @GetMapping("/detectImageTextLabels")
	  public ModelAndView detectImageTextLabels(
			  @RequestParam("imageUrl") String imageUrl) 
	  {
		  ModelMap map = new ModelMap();
		  
		  try 
		  {
			  // [START spring_vision_image_labelling]
			  AnnotateImageResponse response = this.cloudVisionTemplate
					.analyzeImage(this.resourceLoader.getResource(imageUrl), Type.LABEL_DETECTION);

			  Map<String, Float> imageLabels = response.getLabelAnnotationsList().stream()
					.collect(Collectors.toMap(EntityAnnotation::getDescription, EntityAnnotation::getScore, (u, v) -> {
						throw new IllegalStateException(String.format("Duplicate key %s", u));
					}, LinkedHashMap::new));
			  // [END spring_vision_image_labelling]

			  map.addAttribute("annotations", imageLabels);
			  map.addAttribute("imageUrl", imageUrl);

			  return new ModelAndView("result", map);
		  } 
		  catch (Exception e)
		  {
			  LOG.error(e.getMessage(), e);

			  return new ModelAndView("result", map);
		  }
	  }

	  @GetMapping("/detectImageTextWeb")
	  public String detectImageTextWeb(String imageUrl, HttpServletRequest webRequest)
	  {
		  	try 
		    { 
			    // [START spring_vision_text_extraction]
			    String textFromImage =
			        this.cloudVisionTemplate.extractTextFromImage(
			        		this.resourceLoader.getResource(imageUrl));
			    
			    return textFromImage;
			    // [END spring_vision_text_extraction]
		    }
		  	catch( Exception e )
		    {
		    	LOG.error(e.getMessage(), e);
		    	
		    	return StringUtils.EMPTY;
		    }
	  }
	  
	  /**
	   * This method downloads an image from a URL and sends its contents to the Vision API for label
	   * detection.
	   *
	   * @param imageUrl the URL of the image
	   * @param map the model map to use
	   * @return a string with the list of labels and percentage of certainty
	   */
	  @GetMapping("/detectImageTextWebExt")
	  public ModelAndView detectImageTextWebExt(String imageUrl, ModelMap map) 
	  {
		  	try 
		    {  
			    // [START spring_vision_image_labelling]
			    AnnotateImageResponse response =
			        this.cloudVisionTemplate.analyzeImage(
			            this.resourceLoader.getResource(imageUrl), Type.DOCUMENT_TEXT_DETECTION);
			
			    Map<String, Float> imageText =
			        response
			            .getLabelAnnotationsList()
			            .stream()
			            .collect(
			                Collectors.toMap(
			                    EntityAnnotation::getDescription,
			                    EntityAnnotation::getScore,
			                    (u, v) -> {
			                      throw new IllegalStateException(String.format("Duplicate key %s", u));
			                    },
			                    LinkedHashMap::new));
			    // [END spring_vision_image_labelling]
			
			    map.addAttribute("annotations", imageText);
			    map.addAttribute("imageUrl", imageUrl);
			
			    return new ModelAndView("result", map);
		    }
		  	catch( Exception e )
		    {
		    	LOG.error(e.getMessage(), e);
		    	
		    	return new ModelAndView("result", map);
		    }
	  }

	  @GetMapping("/detectImageTextLocal")
	  public List<String> detectImageTextLocal(
			  String imagePath, HttpServletRequest webRequest)
	  {
			// Initialize client that will be used to send requests. This client only needs to be created
		    // once, and can be reused for multiple requests. After completing all of your requests, call
		    // the "close" method on the client to safely clean up any remaining background resources.
		  	ImageAnnotatorClient client = null;
		  	
		  	List<String> resultData = new LinkedList<String>();
		  	
		    try 
		    {
		    	client = ImageAnnotatorClient.create();
		    	
		    	File file = new File(imagePath);
		    	String absolutePath = file.getAbsolutePath();
		    	 
		    	LOG.info(absolutePath);
		
		    	// Reads the image file into memory
		    	Path path = Paths.get(absolutePath);
		    	byte[] data = Files.readAllBytes(path);
		    	ByteString imgBytes = ByteString.copyFrom(data);
	
		    	// Builds the image annotation request
		    	List<AnnotateImageRequest> requests = new ArrayList<>();
		    	Image img = Image.newBuilder().setContent(imgBytes).build();
		    	
		    	//Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
		    	Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
		    	
		    	AnnotateImageRequest requestVision = AnnotateImageRequest.newBuilder().
		    			addFeatures(feat).setImage(img).build();
		    	
		    	requests.add(requestVision);
	
		    	// Performs label detection on the image file
		    	BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
		    	List<AnnotateImageResponse> responses = response.getResponsesList();
	
		    	for (AnnotateImageResponse res : responses) 
		    	{
			        if (res.hasError()) 
			        {
			        	LOG.error("Error: %s%n", res.getError().getMessage());
			          
			        	return resultData;
			        }
			        
			        String imageText = String.format("%s%n", res.getFullTextAnnotation().getText());
			        LOG.info(imageText);
			        
			        //resultData.add(imageText);
			        
			        //for (EntityAnnotation annotation : res.getLabelAnnotationsList()) 
			        for (EntityAnnotation annotation : res.getTextAnnotationsList())
			        {
			        	String record =	StringUtils.EMPTY;
			        	
				        //annotation.getAllFields().forEach((k, v) ->
			        	for( Map.Entry<Descriptors.FieldDescriptor, Object> cannotations : annotation.getAllFields().entrySet() )
			        	{
			        		//String record = String.format("%s : %s%n", k, v.toString());
			        		Descriptors.FieldDescriptor k = cannotations.getKey();
			        		Object v = cannotations.getValue();
			        		
			        		String kv = String.format("%s : %s%n", k, v.toString());
			        		LOG.debug(kv);
			        		
				          	record = String.format("%s", v.toString());
				          		
				          	resultData.add(record);
				          	
				          //});
			        	}
			        }
			        
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
	  
	  /**
	   * Performs document text detection on a local image file.
	   *
	   * @param imagePath The path to the local file to detect document text on.
	   * @throws Exception on errors while closing the client.
	   * @throws IOException on Input/Output errors.
	   */
	  // [START vision_fulltext_detection]
	  @GetMapping("/detectImageTextLocalExt")
	  public String detectImageTextLocalExt(
			  @RequestParam("imagePath") String imagePath, 
			  HttpServletRequest webRequest) throws IOException 
	  {
			ImageAnnotatorClient client = null;
			
			FileInputStream fis = null;
			
			//Gson gson = new GsonBuilder().setLenient().create();
			
			JsonObject resPayload = new JsonObject();
			JsonArray resPayloadArray = new JsonArray();
			resPayload.add("results", resPayloadArray);
			//List<String> resultData = new LinkedList<String>();
		    
		    // Initialize client that will be used to send requests. This client only needs to be created
		    // once, and can be reused for multiple requests. After completing all of your requests, call
		    // the "close" method on the client to safely clean up any remaining background resources.
		    try
		    {
		    	JsonObject pathObject = new JsonObject();
		    	pathObject.addProperty("path", imagePath);
		    	resPayload.add("file", pathObject);
		    	
		    	fis = new FileInputStream(imagePath);
		    	
		    	ByteString imgBytes = ByteString.readFrom(fis);
		    	
		    	Image img = Image.newBuilder().setContent(imgBytes).build();
		    	
		    	Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
		    	
		    	List<AnnotateImageRequest> requests = new ArrayList<>();
			    AnnotateImageRequest request =
			        AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			    requests.add(request);
			    
		    	client = ImageAnnotatorClient.create();
		    			
			    BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
			    List<AnnotateImageResponse> responses = response.getResponsesList();
			    client.close();
		
			    for (AnnotateImageResponse res : responses) 
			    {
			    	JsonObject resPayloadCurr = new OcrResponseParser().buildResponsePayload(res);
			    	
			    	resPayloadArray.add(resPayloadCurr);
			    	/*
					if (res.hasError()) 
					{
						LOG.error(String.format("Error: %s%n", res.getError().getMessage()));
	
						return StringUtils.EMPTY;
					}
					
					// For full list of available annotations, see http://g.co/cloud/vision/docs
					TextAnnotation annotation = res.getFullTextAnnotation();
					
					for (Page page : annotation.getPagesList()) 
					{
						JsonArray blocksDataArray = new JsonArray();
						resPayload.add("blocks", blocksDataArray);
						
						String pageText = ""; int blockIndex = 0;
						for (Block block : page.getBlocksList()) 
						{
							JsonObject blockDataPayload = new JsonObject();
							blocksDataArray.add(blockDataPayload);
							
							JsonArray blockDataArray = new JsonArray();
							blockDataPayload.add("block" + String.valueOf(++blockIndex), blockDataArray);
							
							JsonObject paragraphsDataPayload = new JsonObject();
							JsonArray paragraphsDataArray = new JsonArray();
							paragraphsDataPayload.add("paragraphs", paragraphsDataArray);
							blockDataArray.add(paragraphsDataPayload);
							
							String blockText = ""; int pIndex = 0;
							for (Paragraph para : block.getParagraphsList())
							{
								JsonObject paragraphDataPayload = new JsonObject();
								paragraphsDataArray.add(paragraphDataPayload);
								
								JsonArray paragraphDataArray = new JsonArray();
								paragraphDataPayload.add("paragraph" + String.valueOf(++pIndex), paragraphDataArray);
								
								JsonObject woldsDataPayload = new JsonObject();
								JsonArray wordsDataArray = new JsonArray();
								woldsDataPayload.add("words", wordsDataArray);
								paragraphDataArray.add(woldsDataPayload);
								
								String paraText = ""; int wordIndex = 0;
								for (Word word : para.getWordsList()) 
								{
									JsonObject wordDataPayload = new JsonObject();
									wordsDataArray.add(wordDataPayload);
									
									wordDataPayload.addProperty("confidence", word.getConfidence()); 
									
									JsonArray wordVertixesArray = new JsonArray();
									wordDataPayload.add("vertices", wordVertixesArray);
									
									BoundingPoly bPoly = word.getBoundingBox();
									
									for( Vertex nVertex : bPoly.getVerticesList())
									{
										JsonObject bPolyVertexJsonObject = new JsonObject();
										bPolyVertexJsonObject.addProperty("x", nVertex.getX());
										bPolyVertexJsonObject.addProperty("y", nVertex.getY());
										
										wordVertixesArray.add(bPolyVertexJsonObject);
									}
									
									
									String wordText = "";
									for (Symbol symbol : word.getSymbolsList())
									{
										wordText = wordText + symbol.getText();
										
										LOG.info(String.format("Symbol text: %s (confidence: %f)%n", symbol.getText(),
												symbol.getConfidence()));
									}
									
									wordDataPayload.addProperty("text", wordText);
									
									LOG.info(String.format("Word text: %s (confidence: %f)%n%n", wordText,
											word.getConfidence()));
									paraText = String.format("%s %s", paraText, wordText);
								}
								
								// Output Example using Paragraph:
								LOG.info("%nParagraph: %n" + paraText);
								LOG.info(String.format("Paragraph Confidence: %f%n", para.getConfidence()));
	
								blockText = blockText + paraText;
							}
							
							pageText = pageText + blockText;
						}
					}
	
					LOG.info("%nComplete annotation:");
					LOG.info(annotation.getText());
					*/
			    }
			    
			    return resPayload.toString();
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
		    	
		    	if ( fis != null )
		    	{
		    		fis.close();
		    	}
		    }
	  }
	  // [END vision_fulltext_detection]
	  
	  /**
	   * Performs document text detection on a remote image on Google Cloud Storage.
	   *
	   * @param gcsPath The path to the remote file on Google Cloud Storage to detect document text on.
	   * @throws Exception on errors while closing the client.
	   * @throws IOException on Input/Output errors.
	   */
	  // [START vision_fulltext_detection_gcs]
	  @GetMapping("/detectImageTextGcs")
	  public List<String> detectImageTextGcs(String gcsPath, HttpServletRequest webRequest) throws IOException 
	  {
		  /*
	    List<AnnotateImageRequest> requests = new ArrayList<>();

	    ImageSource imgSource = ImageSource.newBuilder().setGcsImageUri(gcsPath).build();
	    Image img = Image.newBuilder().setSource(imgSource).build();
	    Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
	  
	    AnnotateImageRequest request =
	        AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
	    requests.add(request);
		*/
		  ImageAnnotatorClient client = null;
		  	
		  List<String> resultData = new LinkedList<String>();
			  	
		  List<AnnotateImageRequest> requests = new ArrayList<>();
		    
		  // Initialize client that will be used to send requests. This client only needs to be created
		  // once, and can be reused for multiple requests. After completing all of your requests, call
		  // the "close" method on the client to safely clean up any remaining background resources.
		  try
		  {
			  ImageSource imgSource = ImageSource.newBuilder().setGcsImageUri(gcsPath).build();
	    	
			  Image img = Image.newBuilder().setSource(imgSource).build();
	    	
			  Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
		    
			  AnnotateImageRequest request =
		        AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			  requests.add(request);
		    
			  client = ImageAnnotatorClient.create();
	    			
			  BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
			  List<AnnotateImageResponse> responses = response.getResponsesList();
			  client.close();

			  for (AnnotateImageResponse res : responses) 
			  {
				  if (res.hasError()) 
				  {
					  LOG.error(String.format("Error: %s%n", res.getError().getMessage()));
	        	
					  return resultData;
				  }
				  // For full list of available annotations, see http://g.co/cloud/vision/docs
				  TextAnnotation annotation = res.getFullTextAnnotation();
	        
				  for (Page page : annotation.getPagesList()) 
				  {
					  String pageText = "";
					  for (Block block : page.getBlocksList()) 
					  {
						  String blockText = "";
						  for (Paragraph para : block.getParagraphsList()) 
						  {
							  String paraText = "";
							  for (Word word : para.getWordsList()) 
							  {
								  String wordText = "";
								  for (Symbol symbol : word.getSymbolsList()) 
								  {
									  wordText = wordText + symbol.getText();
	                
									  LOG.info(String.format(
											  "Symbol text: %s (confidence: %f)%n",
											  symbol.getText(), symbol.getConfidence()));
								  }
								  LOG.info(String.format(
										  "Word text: %s (confidence: %f)%n%n", wordText, word.getConfidence()));
	                
								  paraText = String.format("%s %s", paraText, wordText);
							  }
							  // Output Example using Paragraph:
							  LOG.info("%nParagraph: %n" + paraText);
							  LOG.info(String.format("Paragraph Confidence: %f%n", para.getConfidence()));
							  blockText = blockText + paraText;
						  }
						  pageText = pageText + blockText;
					  }
				  }
				  LOG.info("%nComplete annotation:");
				  LOG.info(annotation.getText());
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
	  // [END vision_fulltext_detection_gcs]
	  
	  // [START vision_localize_objects]
	  /**
	   * Detects localized objects in the specified local image.
	   *
	   * @param filePath The path to the file to perform localized object detection on.
	   * @throws Exception on errors while closing the client.
	   * @throws IOException on Input/Output errors.
	   */
	  public void detectImageLocalizedObjects(String filePath) throws IOException {
	    List<AnnotateImageRequest> requests = new ArrayList<>();

	    ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

	    Image img = Image.newBuilder().setContent(imgBytes).build();
	    AnnotateImageRequest request =
	        AnnotateImageRequest.newBuilder()
	            .addFeatures(Feature.newBuilder().setType(Type.OBJECT_LOCALIZATION))
	            .setImage(img)
	            .build();
	    requests.add(request);

	    // Initialize client that will be used to send requests. This client only needs to be created
	    // once, and can be reused for multiple requests. After completing all of your requests, call
	    // the "close" method on the client to safely clean up any remaining background resources.
	    try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
	      // Perform the request
	      BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
	      List<AnnotateImageResponse> responses = response.getResponsesList();

	      // Display the results
	      for (AnnotateImageResponse res : responses) 
	      {
	        for (LocalizedObjectAnnotation entity : res.getLocalizedObjectAnnotationsList()) 
	        {
	        	LOG.info(String.format("Object name: %s%n", entity.getName()));
	        	LOG.info(String.format("Confidence: %s%n", entity.getScore()));
	        	LOG.info(String.format("Normalized Vertices:%n"));
	        	
	        	entity
	              .getBoundingPoly()
	              .getNormalizedVerticesList()
	              .forEach(vertex -> System.out.format("- (%s, %s)%n", vertex.getX(), vertex.getY()));
	        }
	      }
	    }
	  }
	  // [END vision_localize_objects]
	  
	  // [START vision_localize_objects_gcs]
	  /**
	   * Detects localized objects in a remote image on Google Cloud Storage.
	   *
	   * @param gcsPath The path to the remote file on Google Cloud Storage to detect localized objects
	   *     on.
	   * @throws Exception on errors while closing the client.
	   * @throws IOException on Input/Output errors.
	   */
	  public void detectImageLocalizedObjectsGcs(String gcsPath) throws IOException {
	    List<AnnotateImageRequest> requests = new ArrayList<>();

	    ImageSource imgSource = ImageSource.newBuilder().setGcsImageUri(gcsPath).build();
	    Image img = Image.newBuilder().setSource(imgSource).build();

	    AnnotateImageRequest request =
	        AnnotateImageRequest.newBuilder()
	            .addFeatures(Feature.newBuilder().setType(Type.OBJECT_LOCALIZATION))
	            .setImage(img)
	            .build();
	    requests.add(request);

	    // Initialize client that will be used to send requests. This client only needs to be created
	    // once, and can be reused for multiple requests. After completing all of your requests, call
	    // the "close" method on the client to safely clean up any remaining background resources.
	    try (ImageAnnotatorClient client = ImageAnnotatorClient.create())
	    {
	      // Perform the request
	      BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
	      List<AnnotateImageResponse> responses = response.getResponsesList();
	      client.close();
	      // Display the results
	      for (AnnotateImageResponse res : responses)
	      {
	        for (LocalizedObjectAnnotation entity : res.getLocalizedObjectAnnotationsList()) 
	        {
	        	LOG.info(String.format("Object name: %s%n", entity.getName()));
	        	LOG.info(String.format("Confidence: %s%n", entity.getScore()));
	        	LOG.info(String.format("Normalized Vertices:%n"));
	          
	        	entity
	              .getBoundingPoly()
	              .getNormalizedVerticesList()
	              .forEach(vertex -> System.out.format("- (%s, %s)%n", vertex.getX(), vertex.getY()));
	        }
	      }
	    }
	  }
	  // [END vision_localize_objects_gcs]
}

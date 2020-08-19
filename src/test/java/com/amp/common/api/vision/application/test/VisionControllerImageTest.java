/*
 * Copyright 2017-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amp.common.api.vision.application.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assume.assumeThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

import com.google.cloud.vision.v1.ImageAnnotatorClient;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;

import com.google.protobuf.ByteString;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * This test sends images to the GCP Vision API and verifies the returned image
 * annotations.
 *
 * @author Michael Veksler
 */

@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@ComponentScan(basePackages = {})
@TestPropertySource(locations = { "classpath:amp-vision-api.properties"})
public class VisionControllerImageTest {

	private static final String LABEL_IMAGE_URL = "/detectImageLabels?imageUrl=classpath:static/boston-terrier.jpg";

	private static final String TEXT_IMAGE_URL = "/detectImageDocumentText?imageUrl=classpath:static/stop-sign.jpg";

	private static final String BILL_IMAGE_URL = "/detectImageDocumentText?imageUrl=classpath:static/aircanada.jpg";
	
	@Autowired
	private MockMvc mockMvc;

	@BeforeClass
	public static void prepare() {
		System.setProperty("it.vision", "true");
		
		assumeThat(
				"Vision Sample integration tests are disabled. Please use '-Dit.vision=true' "
						+ "to enable them.",
				System.getProperty("it.vision"), is("true"));
	}

	@Ignore
	@Test
	public void testExtractTextFromImage() throws Exception {
		this.mockMvc.perform(get(TEXT_IMAGE_URL))
				.andDo((response) -> {
					ModelAndView result = response.getModelAndView();
					if ( result != null )
					{
						String textFromImage = ((String) result.getModelMap().get("text")).trim();
						assertThat(textFromImage).isEqualTo("STOP");
					}
				});
	}

	@SuppressWarnings("unchecked")
	@Ignore
	@Test
	public void testClassifyImageLabels() throws Exception {
		this.mockMvc.perform(get(LABEL_IMAGE_URL))
				.andDo((response) -> {
					ModelAndView result = response.getModelAndView();
					Map<String, Float> annotations = 
							(LinkedHashMap<String, Float>) result.getModelMap().get("annotations");

					System.out.println(annotations);
					
					List<String> annotationNames = new LinkedList<String>();
					
					annotations.entrySet().stream()
							.forEach(annotation -> annotationNames.add(
									annotation.getKey()));
							
					System.out.println(annotationNames);
					
					assertThat(annotationNames).contains("Boston terrier");
				});
	}
	
	@SuppressWarnings("unchecked")
	@Ignore
	@Test
	public void testClassifyRecieptLabels() throws Exception {
		this.mockMvc.perform(get(BILL_IMAGE_URL))
				.andDo((response) -> {
					ModelAndView result = response.getModelAndView();
					Map<String, Float> annotations = 
							(LinkedHashMap<String, Float>) result.getModelMap().get("annotations");

					System.out.println(annotations);
					
					/*
					List<String> annotationNames = annotations.stream()
							.map(annotation -> annotation.getDescription().toLowerCase().trim())
							.collect(Collectors.toList());
					*/
					
					
					List<String> annotationNames = new LinkedList<String>();
					
					annotations.entrySet().stream()
							.forEach(annotation -> annotationNames.add(
									annotation.getKey()));
							
					System.out.println(annotationNames);
					
					assertThat(annotationNames).contains("Boston terrier");
				});
	}
	
	//@Ignore
	@Test
	public void testParseImageText() throws Exception {
		// Initialize client that will be used to send requests. This client only needs to be created
	    // once, and can be reused for multiple requests. After completing all of your requests, call
	    // the "close" method on the client to safely clean up any remaining background resources.
	    try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) 
	    {
	    	// The path to the image file to annotate
		    String fileName = "static/aircanada.jpg";
		      
	    	ClassLoader classLoader = getClass().getClassLoader();
	    	File file = new File(classLoader.getResource(fileName).getFile());
	    	String absolutePath = file.getAbsolutePath();
	    	 
	    	System.out.println(absolutePath);
	
	    	// Reads the image file into memory
	    	Path path = Paths.get(absolutePath);
	    	byte[] data = Files.readAllBytes(path);
	    	ByteString imgBytes = ByteString.copyFrom(data);

	    	// Builds the image annotation request
	    	List<AnnotateImageRequest> requests = new ArrayList<>();
	    	Image img = Image.newBuilder().setContent(imgBytes).build();
	    	//Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
	    	Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
	    	AnnotateImageRequest request =
	          AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
	    	requests.add(request);

	    	// Performs label detection on the image file
	    	BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
	    	List<AnnotateImageResponse> responses = response.getResponsesList();

	    	for (AnnotateImageResponse res : responses) 
	    	{
		        if (res.hasError()) 
		        {
		          System.out.format("Error: %s%n", res.getError().getMessage());
		          return;
		        }
	
		        //for (EntityAnnotation annotation : res.getLabelAnnotationsList()) 
		        for (EntityAnnotation annotation : res.getTextAnnotationsList())
		        {
		          annotation
		              .getAllFields()
		              .forEach((k, v) -> System.out.format("%s : %s%n", k, v.toString()));
		        }
	    	}
	    }
	}
}

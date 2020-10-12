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
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONObject;
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

import com.amp.common.api.vision.dto.StoreDTO;
import com.amp.common.api.vision.handler.receipt.config.ConfigurationItem;
import com.amp.common.api.vision.handler.receipt.config.DateTimeConfigurationItem;
import com.amp.common.api.vision.handler.receipt.config.ReceiptConfiguration;
import com.amp.common.api.vision.handler.receipt.config.TaxAmountConfigurationItem;
import com.amp.common.api.vision.handler.receipt.config.TaxRateConfigurationItem;
import com.amp.common.api.vision.handler.receipt.config.TotalConfigurationItem;
import com.amp.common.api.vision.handler.receipt.parser.ConfigurationType;
import com.amp.common.api.vision.utils.RegexParser;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.ByteString;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;


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
	
	@Ignore
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
	
	//@Test
	public void testParseMetadataPayloadText() throws Exception
	{
		try
		{
			String fileName = "static/homedepot_parser_metadata.json";
			
			ClassLoader classLoader = getClass().getClassLoader();
	    	File file = new File(classLoader.getResource(fileName).getFile());
	    	String absolutePath = file.getAbsolutePath();
	    	
	        String parserMetadata = new String(Files.readAllBytes(Paths.get(absolutePath)));
	        
	        Gson gson = new GsonBuilder().setLenient().create();
        	
        	ReceiptConfiguration receiptConfig = gson.fromJson(
        			parserMetadata, ReceiptConfiguration.class);
        	
        	assertTrue(receiptConfig != null);
		}
		catch(PathNotFoundException e) 
		{
			System.err.println( e.getMessage());
		}
		catch (ClassCastException e) 
		{
			  System.err.println( e.getMessage());
		} 
		catch(InvalidPathException e) 
		{
			  System.err.println( e.getMessage());
		}
		catch( Exception e)
		{
			System.err.println( e.getMessage());
		}
	}
	        
	//@Ignore
	@Test
	public void testParsePayloadText() throws Exception
	{
		try
		{
			String payloadJsonFileName = "static/homedepot_payload1.json";
			String payloadTextFileName = "static/homedepot_payload1.txt";
			String metadataFileName = "static/homedepot_parser_metadata.json";
			
			ClassLoader classLoader = getClass().getClassLoader();
			
			//---
	    	File filePayloadJson = new File(classLoader.getResource(payloadJsonFileName).getFile());
	    	String absolutePathPayloadJson = filePayloadJson.getAbsolutePath();
	        String      payloadContentJson = new String(Files.readAllBytes(Paths.get(absolutePathPayloadJson)));
	        JSONObject  receiptPayload = new JSONObject(payloadContentJson);
	        
	        DocumentContext jsonContext = JsonPath.
	        		using(Configuration.defaultConfiguration()).
	        		parse(receiptPayload.toString());
	        
	        //---
	        File filePayloadText = new File(classLoader.getResource(payloadTextFileName).getFile());
	    	String absolutePathPayloadText = filePayloadText.getAbsolutePath();
	        String payloadContentText = new String(Files.readAllBytes(Paths.get(absolutePathPayloadText)));
	        
	        //---
	        File fileMetadata = new File(classLoader.getResource(metadataFileName).getFile());
	    	String absolutePathMetadata = fileMetadata.getAbsolutePath();
	        String metadataContent = new String(Files.readAllBytes(Paths.get(absolutePathMetadata)));
	        
	        Gson gson = new GsonBuilder().setLenient().create();
	        
        	ReceiptConfiguration receiptConfig = gson.fromJson(
        			metadataContent, ReceiptConfiguration.class);
	        //---
	        this.parsePayloadDateTimeWithRegex(payloadContentText, receiptConfig);
	        
	        //---subtotal
	        this.parseSubtotalWithRegex(payloadContentText,receiptConfig);
	        /*
	        net.minidev.json.JSONArray subtotalArray = 
	        		jsonContext.read(receiptConfig.getSubtotal());//("$.blocks[8]..paragraphs[1]..words[0].text");
	        
	        if ( subtotalArray.size() >= 1 )
	        {
		        String value = (String)subtotalArray.get(0);
	        	
	        	System.out.println( value);
	        }
	        */
	        //---total
	        this.parseTotalWithRegex(payloadContentText,receiptConfig);
	        /*
	        net.minidev.json.JSONArray totalArray = 
	        		jsonContext.read(receiptConfig.getTotal());//("$.blocks[8]..paragraphs[4]..words[0].text");
	        
	        if ( totalArray.size() >= 1 )
	        {
		        String value = (String)totalArray.get(0);
	        	
	        	System.out.println( value);
	        }
	        */
	        
	        //---tax rate
	        this.parseTaxRateWithRegex(payloadContentText,receiptConfig);
	        
	        //---tax amountc
	        this.parseTaxAmountWithRegex(payloadContentText,receiptConfig);
	        
	        //---store
	        this.parseStoreWithRegex(payloadContentText, receiptConfig);
	        
	        /*
	        net.minidev.json.JSONArray taxAmountArray = 
	        		jsonContext.read(receiptConfig.getTaxAmount());//("$.blocks[8]..paragraphs[3]..words[0].text");
	        
	        if ( taxAmountArray.size() >= 1 )
	        {
		        String value = (String)taxAmountArray.get(0);
	        	
	        	System.out.println( value);
	        }
	        */
	        //---items data
	        for( String itemsDetailsPath : receiptConfig.getItemsData().getItemsDetails())
	        {
		        net.minidev.json.JSONArray itemsCodesArray = jsonContext.read(itemsDetailsPath);
		        
		        System.out.println(itemsCodesArray.toString());
	        }
		}
		catch(PathNotFoundException e) 
		{
			System.err.println( e.getMessage());
		}
		catch (ClassCastException e) 
		{
			  System.err.println( e.getMessage());
		} 
		catch(InvalidPathException e) 
		{
			  System.err.println( e.getMessage());
		}
		catch( Exception e)
		{
			System.err.println( e.getMessage());
		}
		
	}
	//---
	private void parsePayloadDateTimeWithRegex(
			String payloadContentText,
			ReceiptConfiguration receiptConfig) 
	{
		try
		{
			RegexParser regexParser = new RegexParser();
			
			//---purchaseDate
			@SuppressWarnings("unused")
			String purchaseDateFormat = "dd/MM/yy hh:mm a";
			
			DateTimeConfigurationItem configurationItem = null;
			
			for( DateTimeConfigurationItem configurationItemValue : receiptConfig.getPurchaseDateTime().getConfigurationItems() )
			{
				if ( configurationItemValue.getType().equalsIgnoreCase(ConfigurationType.JSON_REGEX.getConfigurationType()))
				{
					configurationItem = configurationItemValue;
					
					break;
				}
			}
			
			if ( configurationItem == null )
			{
				System.err.println( "configurationItem == null" );
				
				return;
			}
		
			String day = regexParser.getGroupValueByRegex(
					payloadContentText, 
					configurationItem.getPurchaseDate(), 
					configurationItem.getPurchaseDateDayMatch(), 
					configurationItem.getPurchaseDateDayGroup());
			
			int dayValue = (StringUtils.isNotBlank(day) &&  regexParser.isNumeric(day)) ? Integer.valueOf(day) : 0;
					
			String month = regexParser.getGroupValueByRegex(
					payloadContentText, 
					configurationItem.getPurchaseDate(), 
					configurationItem.getPurchaseDateMonthMatch(), 
					configurationItem.getPurchaseDateMonthGroup());
			
			int monthValue = (StringUtils.isNotBlank(month) &&  regexParser.isNumeric(month)) ? Integer.valueOf(month) : 0;
			
			String year = regexParser.getGroupValueByRegex(
					payloadContentText, 
					configurationItem.getPurchaseDate(), 
					configurationItem.getPurchaseDateYearMatch(), 
					configurationItem.getPurchaseDateYearGroup());
			
			int yearValue = (StringUtils.isNotBlank(year) &&  regexParser.isNumeric(year)) ? Integer.valueOf(year) : 0;
			
			String hour = regexParser.getGroupValueByRegex(
					payloadContentText, 
					configurationItem.getPurchaseTime(), 
					configurationItem.getPurchaseTimeHourMatch(), 
					configurationItem.getPurchaseTimeHourGroup());
			
			int hourValue = (StringUtils.isNotBlank(hour) &&  regexParser.isNumeric(hour)) ? Integer.valueOf(hour) : 0;
			
			String minute = regexParser.getGroupValueByRegex(
					payloadContentText, 
					configurationItem.getPurchaseTime(), 
					configurationItem.getPurchaseTimeMinuteMatch(), 
					configurationItem.getPurchaseTimeMinuteGroup());
			
			int minuteValue = (StringUtils.isNotBlank(minute) &&  regexParser.isNumeric(minute)) ? Integer.valueOf(minute) : 0;
			
			String ampm = regexParser.getGroupValueByRegex(
					payloadContentText, 
					configurationItem.getPurchaseTime(), 
					configurationItem.getPurchaseTimeAMPMMatch(), 
					configurationItem.getPurchaseTimeAMPMGroup());
			
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTimeZone(TimeZone.getDefault());
			//SimpleDateFormat formatter = new SimpleDateFormat(configurationItem.getPurchaseDateFormat());
			//formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
			
			if ( ampm.equalsIgnoreCase("am"))
			{
				cal.set( Calendar.AM_PM, Calendar.AM );
			}
			else
			{
				cal.set( Calendar.AM_PM, Calendar.PM );
			}
			cal.set(yearValue, monthValue, dayValue, hourValue, minuteValue, 0);
			
			Instant value = cal.toInstant();
			
			System.out.println( value);
		}
		catch( Exception e ) 
		{
			System.err.println( e.getMessage());
		}
	}
	
	@SuppressWarnings("unused")
	private void parsePayloadDateTimeWithJsonPath(
			DocumentContext jsonContext, 
			ReceiptConfiguration receiptConfig) 
	{
		//---purchaseDate
		String purchaseDateValue = StringUtils.EMPTY;
		String purchaseTimeValue = StringUtils.EMPTY;
		String purchaseAMPMValue = StringUtils.EMPTY;
		String purchaseDateTimeValue = StringUtils.EMPTY;
		String purchaseDateFormat = "dd/MM/yy hh:mm a";
		
		DateTimeConfigurationItem configurationItem = null;
		
		for( DateTimeConfigurationItem configurationItemValue : receiptConfig.getPurchaseDateTime().getConfigurationItems() )
		{
			if ( configurationItemValue.getType().equalsIgnoreCase(ConfigurationType.JSON_PATH.getConfigurationType()))
			{
				configurationItem = configurationItemValue;
			}
		}
		
		if ( configurationItem == null )
		{
			System.err.println( "configurationItem == null" );
			
			return;
		}
		
		try
		{
			
			net.minidev.json.JSONArray purchaseDate = 
				jsonContext.read(configurationItem.getPurchaseDate()); //("$.blocks[3]..paragraphs[0]..words[0].text");
			
			if ( purchaseDate.size() >= 1)
		    {
		    	purchaseDateValue = (String)purchaseDate.get(0);
		    }
		}
		catch( Exception e ){}
		
		try
		{
			net.minidev.json.JSONArray purchaseTime = 
				jsonContext.read(configurationItem.getPurchaseTime());//("$.blocks[4]..paragraphs[0]..words[0].text");
			
			if ( purchaseTime.size() >= 1)
		    {
				purchaseTimeValue = (String)purchaseTime.get(0);
		    }
		}
		catch( Exception e ) {}
		
		try
		{
			net.minidev.json.JSONArray purchaseAMPM = 
				jsonContext.read(configurationItem.getPurchaseAMPM());//("$.blocks[4]..paragraphs[0]..words[1].text");
			
			if ( purchaseAMPM.size() >= 1)
		    {
				purchaseAMPMValue = (String)purchaseAMPM.get(0);
		    }
		
		}
		catch( Exception e ){}
		
		try
		{
			if ( !purchaseDateValue.equals(StringUtils.EMPTY))
			{
				purchaseDateTimeValue += purchaseDateValue;
			}
			
			if ( !purchaseTimeValue.equals(StringUtils.EMPTY))
			{
				purchaseDateTimeValue += " ";
				purchaseDateTimeValue += purchaseTimeValue;
			}
			
			if ( !purchaseAMPMValue.equals(StringUtils.EMPTY))
			{
				purchaseDateTimeValue += " ";
				purchaseDateTimeValue += purchaseAMPMValue;
			}
			
			Date date = new SimpleDateFormat(purchaseDateFormat).parse(purchaseDateTimeValue);
		
			Instant value = date.toInstant();
			
			System.out.println( value);
		}
		catch( Exception e ) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	//---
	protected void parseTotalWithRegex(
			String payloadContentText,
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		BigDecimal value = null;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        TotalConfigurationItem configurationItem = null;
			
			for( TotalConfigurationItem configurationItemValue : receiptConfig.getTotal().getConfigurationItems() )
			{
				if ( configurationItemValue.getType().equalsIgnoreCase(ConfigurationType.JSON_REGEX.getConfigurationType()))
				{
					configurationItem = configurationItemValue;
					
					break;
				}
			}
			
			if ( configurationItem == null )
			{
				System.err.println( "configurationItem == null" );
				
				return ;
			}
	        
	        RegexParser regexParser = new RegexParser();
	        		
	        String valueStr = regexParser.getGroupValueByRegex(
	        		payloadContentText, 
					configurationItem.getValue(), 
					configurationItem.getMatch(), 
					configurationItem.getGroup());
					
			if ( NumberUtils.isCreatable(valueStr) )
    		{
    			value = new BigDecimal(valueStr);
    			
    			System.out.println(cMethodName + "::Subtotal: " + value);
    		}
		}
		catch( Exception e )
		{
			System.out.println(e.getMessage());
		}
	}
	
	//---
	protected void parseSubtotalWithRegex(
			String payloadContentText,
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		BigDecimal value = null;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        ConfigurationItem configurationItem = null;
			
			for( ConfigurationItem configurationItemValue : receiptConfig.getSubtotal().getConfigurationItems() )
			{
				if ( configurationItemValue.getType().equalsIgnoreCase(ConfigurationType.JSON_REGEX.getConfigurationType()))
				{
					configurationItem = configurationItemValue;
					
					break;
				}
			}
			
			if ( configurationItem == null )
			{
				System.err.println( "configurationItem == null" );
				
				return ;
			}
	        
	        RegexParser regexParser = new RegexParser();
	        		
	        String valueStr = regexParser.getGroupValueByRegex(
	        		payloadContentText, 
					configurationItem.getValue(), 
					configurationItem.getMatch(), 
					configurationItem.getGroup());
					
			if ( NumberUtils.isCreatable(valueStr) )
    		{
    			value = new BigDecimal(valueStr);
    			
    			System.out.println(cMethodName + "::Subtotal: " + value);
    		}
		}
		catch( Exception e )
		{
			System.out.println(e.getMessage());
		}
	}
	
	//---
	protected void parseTaxRateWithRegex(
			String payloadContentText,
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		BigDecimal value = null;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        TaxRateConfigurationItem configurationItem = null;
			
			for( TaxRateConfigurationItem configurationItemValue : receiptConfig.getTaxRate().getConfigurationItems() )
			{
				if ( configurationItemValue.getType().equalsIgnoreCase(ConfigurationType.JSON_REGEX.getConfigurationType()))
				{
					configurationItem = configurationItemValue;
					
					break;
				}
			}
			
			if ( configurationItem == null )
			{
				System.err.println( "configurationItem == null" );
				
				return ;
			}
	        
	        RegexParser regexParser = new RegexParser();
	        		
	        String valueStr = regexParser.getGroupValueByRegex(
	        		payloadContentText, 
					configurationItem.getValue(), 
					configurationItem.getMatch(), 
					configurationItem.getGroup());
					
			if ( NumberUtils.isCreatable(valueStr) )
    		{
    			value = new BigDecimal(valueStr);
    			
    			System.out.println(cMethodName + "::Subtotal: " + value);
    		}
		}
		catch( Exception e )
		{
			System.out.println(e.getMessage());
		}
	}
	
	//---
	protected void parseTaxAmountWithRegex(
			String payloadContentText,
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		BigDecimal value = null;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        TaxAmountConfigurationItem configurationItem = null;
			
			for( TaxAmountConfigurationItem configurationItemValue : receiptConfig.getTaxAmount().getConfigurationItems() )
			{
				if ( configurationItemValue.getType().equalsIgnoreCase(ConfigurationType.JSON_REGEX.getConfigurationType()))
				{
					configurationItem = configurationItemValue;
					
					break;
				}
			}
			
			if ( configurationItem == null )
			{
				System.err.println( "configurationItem == null" );
				
				return ;
			}
	        
	        RegexParser regexParser = new RegexParser();
	        		
	        String valueStr = regexParser.getGroupValueByRegex(
	        		payloadContentText, 
					configurationItem.getValue(), 
					configurationItem.getMatch(), 
					configurationItem.getGroup());
					
			if ( NumberUtils.isCreatable(valueStr) )
    		{
    			value = new BigDecimal(valueStr);
    			
    			System.out.println(cMethodName + "::Subtotal: " + value);
    		}
		}
		catch( Exception e )
		{
			System.out.println(e.getMessage());
		}
	}
	
	//---
	protected void parseStoreWithRegex(
			String payloadContentText,
			ReceiptConfiguration receiptConfig)
	{
		String cMethodName = "";
		
		StoreDTO value = new StoreDTO();
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        ConfigurationItem configurationItem = null;
			
			for( ConfigurationItem configurationItemValue : receiptConfig.getStore().getConfigurationItemsStreetAddress() )
			{
				if ( configurationItemValue.getType().equalsIgnoreCase(ConfigurationType.JSON_REGEX.getConfigurationType()))
				{
					configurationItem = configurationItemValue;
					
					break;
				}
			}
			
			if ( configurationItem == null )
			{
				System.err.println( "configurationItem == null" );
				
				return ;
			}
	        
	        RegexParser regexParser = new RegexParser();
	        		
	        String address1 = regexParser.getGroupValueByRegex(
	        		payloadContentText, 
					configurationItem.getValue(), 
					configurationItem.getMatch(), 
					configurationItem.getGroup(),
					true);
    		
			value.setAddress1(address1);
			//---
			
			System.out.println(cMethodName + "::Address1: " + value.getAddress1());
		}
		catch( Exception e )
		{
			System.out.println(e.getMessage());
		}
	}
}

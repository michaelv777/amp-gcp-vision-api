/**
 * 
 */
package com.amp.common.api.vision.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Block;
import com.google.cloud.vision.v1.BoundingPoly;
import com.google.cloud.vision.v1.Page;
import com.google.cloud.vision.v1.Paragraph;
import com.google.cloud.vision.v1.Symbol;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.cloud.vision.v1.Vertex;
import com.google.cloud.vision.v1.Word;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author mveksler
 *
 */
public class OcrResponseParser
{
	private final static Logger LOG = 
			LoggerFactory.getLogger(OcrResponseParser.class);
	
	public JsonObject buildResponsePayload(AnnotateImageResponse res)
	{
		JsonObject resPayload = new JsonObject();
		
		try
		{
			if (res.hasError()) 
			{
				LOG.error(String.format("Error: %s%n", res.getError().getMessage()));

				return resPayload;
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
			
			return resPayload;
		}
		catch( Exception e )
		{
			LOG.error(e.getMessage(), e);
			
			return resPayload;
		}
	}
}

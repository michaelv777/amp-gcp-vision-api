/**
 * 
 */
package com.amp.common.api.vision.utils;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mveksler
 *
 */
public class OcrPDFUtil
{
	private final static Logger LOG = 
			LoggerFactory.getLogger(OcrPDFUtil.class);
	
	
	public class PdfInfo {
		private String PDFWAY;    
	    private String OUTPUT_PREFIX;
	    private String PASSWORD;
	    private int START_PAGE=1;
	    private int END_PAGE=Integer.MAX_VALUE;
	    private String IMAGE_FORMAT="jpg";
	    private String COLOR="rgb";
	    private int RESOLUTION=256;
	    private int IMAGETYPE=24;
	    private String filename;
	    private String filePath="";
	    
	    public  String getPDFWAY() {
			return this.PDFWAY;
		}
		public  void setPDFWAY(String pDFWAY) {
			this.PDFWAY = pDFWAY;
		}
		public  String getOUTPUT_PREFIX() {
			return this.OUTPUT_PREFIX;
		}
		public  void setOUTPUT_PREFIX(String oUTPUT_PREFIX) {
			this.OUTPUT_PREFIX = oUTPUT_PREFIX;
		}
		public  String getPASSWORD() {
			return this.PASSWORD;
		}
		public  void setPASSWORD(String pASSWORD) {
			this.PASSWORD = pASSWORD;
		}
		public  int getSTART_PAGE() {
			return this.START_PAGE;
		}
		public  void setSTART_PAGE(int sTART_PAGE) {
			this.START_PAGE = sTART_PAGE;
		}
		public  int getEND_PAGE() {
			return this.END_PAGE;
		}
		public  void setEND_PAGE(int eND_PAGE) {
			this.END_PAGE = eND_PAGE;
		}
		public  String getIMAGE_FORMAT() {
			return this.IMAGE_FORMAT;
		}
		public  void setIMAGE_FORMAT(String iMAGE_FORMAT) {
			this.IMAGE_FORMAT = iMAGE_FORMAT;
		}
		public  String getCOLOR() {
			return this.COLOR;
		}
		public  void setCOLOR(String cOLOR) {
			this.COLOR = cOLOR;
		}
		public  int getRESOLUTION() {
			return this.RESOLUTION;
		}
		public  void setRESOLUTION(int rESOLUTION) {
			this.RESOLUTION = rESOLUTION;
		}
		public  int getIMAGETYPE() {
			return this.IMAGETYPE;
		}
		public  void setIMAGETYPE(int iMAGETYPE) {
			this.IMAGETYPE = iMAGETYPE;
		}
		public  String getFilename() {
			return this.filename;
		}
		public  void setFilename(String filename) {
			this.filename = filename;
		}
		public  String getFilePath() {
			return this.filePath;
		}
		public  void setFilePath(String filePath) {
			this.filePath = filePath;
		}
	}
	
	
	
	public boolean convertFromPDFtoImage(
			String sourceFilePath, String destFile)
	{
		try 
		{
			String sourceDir = sourceFilePath;//"C:\\Users\\venkataudaykiranp\\Downloads\\04-Request-Headers.pdf"; // Pdf files are read from this folder
            String destinationDir = destFile;//"C:\\Users\\venkataudaykiranp\\Downloads\\Converted_PdfFiles_to_Image/"; // converted images from pdf document are saved here

            File sourceFile = new File(sourceDir);
            File destinationFile = new File(destinationDir);
            if (!destinationFile.exists()) 
            {
                destinationFile.mkdir();
                System.out.println("Folder Created -> "+ destinationFile.getAbsolutePath());
            }
            
            if (sourceFile.exists()) 
            {
                System.out.println("Images copied to Folder Location: "+ destinationFile.getAbsolutePath());             
                PDDocument document = PDDocument.load(sourceFile);
                PDFRenderer pdfRenderer = new PDFRenderer(document);

                int numberOfPages = document.getNumberOfPages();
                System.out.println("Total files to be converting -> "+ numberOfPages);

                String fileName = sourceFile.getName().replace(".pdf", "");             
                String fileExtension= "png";
                /*
                 * 600 dpi give good image clarity but size of each image is 2x times of 300 dpi.
                 * Ex:  1. For 300dpi 04-Request-Headers_2.png expected size is 797 KB
                 *      2. For 600dpi 04-Request-Headers_2.png expected size is 2.42 MB
                 */
                int dpi = 300;// use less dpi for to save more space in harddisk. For professional usage you can use more than 300dpi 

                for (int i = 0; i < numberOfPages; ++i) 
                {
                    File outPutFile = new File(destinationDir + fileName +"_"+ (i+1) +"."+ fileExtension);
                    BufferedImage bImage = pdfRenderer.renderImageWithDPI(i, dpi, ImageType.RGB);
                    ImageIO.write(bImage, fileExtension, outPutFile);
                }

                document.close();
                
                LOG.info("Converted Images are saved at -> "+ destinationFile.getAbsolutePath());
            } 
            else 
            {
            	LOG.error(sourceFile.getName() +" File not exists");
            }
            
            return true;
		}
		catch( Exception e )
		{
			LOG.error(e.getMessage());
			
			return false;
		}
    } 
		
	
}

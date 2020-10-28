package com.amp.common.api.vision.application;

import java.util.HashSet;
import java.util.Set;

public class VisionApiConstants 
{
    public static final String PROPERTY_FILE_NAME1 = "amp-vision-api.properties";
    
    public static final String PROPERTY_FILE_NAME2 = "application.properties";
    
    public static final String BUCKET_INPUT_FOLDER = "input";
    
    public static final String BUCKET_OUTPUTT_FOLDER = "output";
    
    public static Set<String> HANDLERS_PACKAGERS = 
			new HashSet<String>(){
		        /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
		            add("com.amp.common.api.vision.handler.vendor");
		        }
		    }; 
}

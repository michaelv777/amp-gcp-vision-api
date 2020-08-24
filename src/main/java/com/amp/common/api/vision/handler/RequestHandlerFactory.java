/**
 * 
 */
package com.amp.common.api.vision.handler;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amp.common.api.vision.application.VisionApiConstants;
import com.amp.common.api.vision.handler.impl.RequestHandlerDefault;

/**
 * @author MVEKSLER
 *
 */
public class RequestHandlerFactory 
{
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(RequestHandlerFactory.class);
	
	public String cHandlerPackage = "";
	
	public String getcHandlerPackage() {
		return cHandlerPackage;
	}

	public void setcHandlerPackage(String cHandlerPackage) {
		this.cHandlerPackage = cHandlerPackage;
	}

	public RequestHandlerFactory()
	{
		try
		{
			this.setcHandlerPackage(
					this.getClass().getPackage().getName());
			
			
			if ( StringUtils.isEmpty(this.getcHandlerPackage()))
			{
				this.setcHandlerPackage(
						"com.amp.common.api.vision.handler.impl");
			}
			
		}
		catch( Exception e )
		{
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	//---
	public RequestHandlerInterface getSearchHandler(
			String ossObjectType) 
	{
		String cMethodName = "";
		
		RequestHandlerInterface cRequestHandler = 
				new RequestHandlerDefault();
		
		boolean isHandlerFound = false;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        //---
	        if ( ossObjectType == null )
	        {
	        	return cRequestHandler;
	        }
	         
	        //---
	        for ( String cHandlerPackage : VisionApiConstants.HANDLERS_PACKAGERS )
	        {
		        Set<Class<?>> cHandlerClasses = new Reflections(cHandlerPackage).
		        		getTypesAnnotatedWith(HandlerObjectName.class);
		        
		        for( Class<?> cHandlerClass : cHandlerClasses)
		        {
		        	HandlerObjectName cSearchHandler = 
		        			cHandlerClass.getAnnotation(HandlerObjectName.class);
		        	
		        	if ( cSearchHandler != null )
		        	{
			        	if ( cSearchHandler.objectType().equalsIgnoreCase(ossObjectType.toLowerCase()))
			        	{
			        		cRequestHandler = (RequestHandlerInterface) cHandlerClass.newInstance();
			        		
			        		LOGGER.warn(cMethodName + ":" + cRequestHandler.getClass().getName() + 
			        				" Handler is created!");
			        		
			        		isHandlerFound = true;
			        		
			        		break;
			        	}
		        	}
		        }
	        }
	        
	        if ( !isHandlerFound )
	        {
	        	cRequestHandler = new RequestHandlerDefault();
	        	
	        	LOGGER.error(cMethodName + ":Default Handler is invoked!");
	        }
	        
	        LOGGER.info(cMethodName + ":Handler:" + cRequestHandler.getClass().getName());
	        
	        return cRequestHandler;
		}
		catch( Exception e)
		{
			LOGGER.error(cMethodName + "::" + e.getMessage(), e);
			
			return new RequestHandlerDefault();
		}
	}
}

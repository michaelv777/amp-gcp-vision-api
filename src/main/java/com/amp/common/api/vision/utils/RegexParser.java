/**
 * 
 */
package com.amp.common.api.vision.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author MVEKSLER
 *
 */
public class RegexParser 
{
	private static Logger cLogger = 
			LoggerFactory.getLogger(RegexParser.class);

	//---class variables
	public RegexParser()
	{
		String  cMethodName = "";
  		
  		try
  		{
  			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
  		}
  		catch( Exception e)
  		{
  			cLogger.error(cMethodName + ":" + e.getMessage());
  		}
	}
	
	//----
  	public String getValueByRegex(String cRegex, String cValue, int cRetGroup) 
	{
		String  cMethodName = "";
  		
		String cRetVal = "";
		
  		@SuppressWarnings("unused")
		boolean cRes = true;
  		
  		try
  		{
  			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
      		
	        Pattern r = Pattern.compile(cRegex);
			
	        Matcher m = r.matcher(cValue);
			
			if ( m.matches())
			{
				if ( cRetGroup <= m.groupCount())
				{
					cRetVal = m.group(cRetGroup);
				}
			}
	        
  			return cRetVal ;
  		}
  		catch( Exception e)
  		{
  			cLogger.error(cMethodName + ":" + e.getMessage());
  			
      		return "";
  		}
	}
  	
  	//----
  	public boolean isMatchByRegex(String cRegex, String cValue) 
	{
		String  cMethodName = "";
  		
		boolean cRetVal = false;
		
  		try
  		{
  			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
      		
	        Pattern r = Pattern.compile(cRegex);
			
	        Matcher m = r.matcher(cValue);
			
			if ( m.matches())
			{
				cRetVal = true;
			}
	        
  			return cRetVal ;
  		}
  		catch( Exception e)
  		{
  			cLogger.error(cMethodName + ":" + e.getMessage());
  			
      		return false;
  		}
	}
  	
  	//----
  	public String getGroupValueByRegex(String cRegex, String cValue, int group) 
	{
		String  cMethodName = "";
  		
		@SuppressWarnings("unused")
		boolean cRetVal = false;
		
		String cGroupVal = "";
		
  		try
  		{
  			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
      		
	        Pattern r = Pattern.compile(cRegex);
			
	        Matcher m = r.matcher(cValue);
			
			if ( m.matches())
			{
				cGroupVal = m.group(group);
				
				if ( cGroupVal == null )
				{
					cGroupVal = "";
				}
			}
			
  			return cGroupVal ;
  		}
  		catch( Exception e)
  		{
  			cLogger.error(cMethodName + ":" + e.getMessage());
  			
      		return cGroupVal;
  		}
	}
  	
  	//----
  	public String getGroupValueByRegex(String cValue, String cRegex, int match, int group) 
	{
		String  cMethodName = "";
  		
		@SuppressWarnings("unused")
		boolean cRetVal = false;
		
		String cGroupVal = StringUtils.EMPTY;
		
  		try
  		{
  			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
      		
	        Pattern r = Pattern.compile(cRegex, Pattern.DOTALL);
			
	        Matcher m = r.matcher(cValue);
			
	        int matchIndex = 1;
	        
	        while (m.find()) 
	        {
	        	if ( matchIndex == match )
	        	{
					cGroupVal = m.group(group);
					
					if ( cGroupVal == null )
					{
						cGroupVal = StringUtils.EMPTY;
					}
					else
					{
						break;
					}
	        	}
				++matchIndex;
	        }
			
  			return cGroupVal ;
  		}
  		catch( Exception e)
  		{
  			cLogger.error(cMethodName + ":" + e.getMessage(), e);
  			
      		return cGroupVal;
  		}
	}
  	
  	public boolean isNumeric(String s) 
  	{
  		String  cMethodName = "";
        
  		try
  		{
  			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
  	        StackTraceElement ste = stacktrace[1];
  	        cMethodName = ste.getMethodName();
  	        
  			return s.matches("[-+]?\\d*\\.?\\d+");
  		}
  		catch( Exception e)
  		{
  			cLogger.error(cMethodName + ":" + e.getMessage());
      		
      		return false;
  		}
  	}
    //---
	
}

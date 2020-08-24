/**
 * 
 */
package com.amp.common.api.vision.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author MVEKSLER
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlerObjectName 
{
	public String objectName() default "";
	public String objectType() default "";
}

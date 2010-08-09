/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package org.jtoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>The <code>Post</code> annotation tells Jtoc that the method
 * to which it is attached should have a Post checking invocation. 
 * </p>
 * 
 * <p>A simple <code>Post</code> invocation looks like this:
 * <pre>
 * code 1:
 * public class Example {
 *    <b>&#064;Post</b> 
 *    public void method1(ParameterType1 para1, ..., ParameterTypeN paraN) {
 *       ...
 *       return;
 *       ...
 *    }
 * 
 *    <b>&#064;Post</b> 
 *    public returnType method2(ParameterType1 para1, ..., ParameterTypeN paraN) {
 *       ...
 *       return expr1;
 *       ...
 *       return exprK;
 *    }
 * }
 * </pre>
 * </p>
 * 
 * <p>Then the converted source file would contain code like this:
 * <pre>
 * code 2:
 * public class Example {
 *    public void method1(ParameterType1 para1, ..., ParameterTypeN paraN) {
 *       ...
 *       <b>{innerTest.method1PostCheck(para1, ..., paraN);return;}</b>
 *       ...
 *       <b>innerTest.method1PostCheck(para1, ..., paraN);</b>
 *    }
 *    
 *    public returnType method2(ParameterType1 para1, ..., ParameterTypeN paraN) {
 *       ...
 *       <b>return innerTest.method2PostCheck((expr1), para1, ..., paraN);</b>
 *       ...
 *       <b>return innerTest.method2PostCheck((exprK), para1, ..., paraN);</b>
 *    }
 *    <b>//which need the innerTest method return the 1st input without any change</b>
 * }
 * </pre>
 * </p>
 * 
 * <p>The <code>Post</code> annotation also supports three optional parameters as the 
 * <code>Pre</code> annotation, They have the same usage as those in <code>Pre</code> 
 * annotation, except the following differences: </p>
 * <p>[1] If the <code>testMethod</code> parameter is not specified, then the test method's name
 * would be specified as (<code>method's name + "PostCheck"</code>)</p>
 * <p>[2] If the <code>parameters</code> is not specified, then the tested method's return value
 * and all parameters would be assigned.
 * 
 * @see @Pre
 * </p>
 */
@Retention(RetentionPolicy.SOURCE)
@Target( { ElementType.METHOD, ElementType.CONSTRUCTOR })
public @interface Post {

	/**
	 * Optionally specify <code>testObject</code>, a object which is a object of
	 * the Inner TestClass; the default name for the inner test object is "innerTest".
	 */ 
	String testObject() default "innerTest";
	
	/**
	 * Optionally specify <code>testMethod</code>, the test method which would invoked
	 * when this annotation is activated; the user could choose not to assign it, then
	 * Jtoc would give it a standard methodName.
	 */
	String testMethod() default "";
	
	/**
	 * Optionally specify <code>parameters</code>, the parameters to be input to the test method;
	 * the user could choose not to assign it, then Jtoc would assign the tested method's 
	 * parameters to it.
	 */
	String parameters() default "";
}

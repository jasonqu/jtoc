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
 * <p>The <code>Pre</code> annotation tells Jtoc that the method
 * to which it is attached should have a pre checking invocation. 
 * </p>
 * 
 * <p>A simple <code>Pre</code> invocation looks like this:
 * <pre>
 * code 1:
 * public class Example {
 *    <b>&#064;Pre</b> 
 *    public void method(ParameterType1 para1, ..., ParameterTypeN paraN) {
 *       ...
 *    }
 * }
 * </pre>
 * </p>
 * 
 * <p>Then the converted source file would contain code like this:
 * <pre>
 * code 2:
 * public class Example {
 *    public void method(ParameterType1 para1, ..., ParameterTypeN paraN) {
 *       <b>innerTest.methodPreCheck(para1, ..., paraN);</b>
 *       ...
 *    }
 * }
 * </pre>
 * </p>
 * 
 * <p>The <code>Pre</code> annotation supports three optional parameters.
 * The first, <code>testObject</code> specifies the instance of Inner Test Class's name;
 * For instance, if the <code>Pre</code> annotation in code 1 is changed to 
 * <code>&#064;Pre(testObject = "test")</code>, then the converted code would be like the code
 * showed in code 3 rather than the default value "innerTest" as the code 2 shows:
 * <pre>
 * code 3:
 * public class Example {
 *    public void method(ParameterType1 para1, ..., ParameterTypeN paraN) {
 *       <b>test.methodPreCheck(para1, ..., paraN);</b>
 *       ...
 *    }
 * }
 * </pre>
 * </p>
 * 
 * <p>The second optional parameter, <code>testMethod</code> specifies the name of Test Method's name;
 * if this parameter is not specified, then the test method's name would be specified as 
 * (<code>method's name + "PreCheck"</code>); or else the parameter's value would be assigned.
 * For instance, if the <code>Pre</code> annotation in code 1 is changed to 
 * <code>&#064;Pre(testMethod = "commenPreCheck")</code>, then the converted code would be like this:
 * <pre>
 * code 4:
 * public class Example {
 *    public void method(ParameterType1 para1, ..., ParameterTypeN paraN) {
 *       <b>innerTest.commenPreCheck(para1, ..., paraN);</b>
 *       ...
 *    }
 * }
 * </pre>
 * </p>
 * 
 * <p>The third optional parameter, <code>parameters</code> specifies the parameters of Test Method's 
 * input parameters; if this parameter is not specified, then the tested method's parameters would be
 * assigned; or else this value would be assigned.
 * For instance, if the <code>Pre</code> annotation in code 1 is changed to 
 * <code>&#064;Pre(parameters = "para1, paraK")</code>, then the converted code would be like this:
 * <pre>
 * code 5:
 * public class Example {
 *    public void method(ParameterType1 para1, ..., ParameterTypeN paraN) {
 *       <b>innerTest.methodPreCheck(para1, paraK);</b>
 *       ...
 *    }
 * }
 * </pre>
 * </p>
 */
@Retention(RetentionPolicy.SOURCE)
@Target( { ElementType.METHOD })
public @interface Pre {
	
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

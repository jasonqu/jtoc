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
 * <p>The <code>InnerTest</code> annotation tells Jtoc that the class
 * to which it is attached would have the InnerTests initialized. 
 * </p>
 * 
 * <p>A simple <code>InnerTest</code> invocation looks like this:
 * <pre>
 * code 1:
 * <b>&#064;InnerTest</b>
 * public class Example {
 *       ...
 * }
 * </pre>
 * </p>
 * 
 * <p>Then the converted source file would contain code like this:
 * <pre>
 * code 2:
 * public class Example {
 *       ...
 *       <b>InnerTest innerTest = new InnerTest();</b>
 * }
 * </pre>
 * </p>
 * 
 * <p>The <code>InnerTest</code> annotation supports two optional parameters, 
 * which are "<code>classNames</code>" and "<code>objectNames</code>".
 * they respectively specify Inner Test Classes' names and objects' names;
 * For instance, if the <code>&#064;InnerTest</code> annotation in code 1 is changed to 
 * <code>&#064;InnerTest(classNames = "InnerTest1", objectNames = "test")</code>,
 * then the converted code would be like the code showed in code 3:
 * <pre>
 * code 3:
 * public class Example {
 *       ...
 *       <b>InnerTest1 test = new InnerTest1();</b>
 * }
 * </pre>
 * </p>
 * 
 * The user also could assign String Array to these two parameters,
 * which would initialize several test classes in the converted source file.
 * For instance, if the <code>&#064;InnerTest</code> annotation in code 1 is changed to 
 * <code>&#064;InnerTest(classNames = {"InnerTest1", "InnerTest2"}, objectNames = {"test1", "test2"})</code>,
 * then the converted code would be like the code showed in code 4:
 * <pre>
 * code 4:
 * public class Example {
 *       ...
 *       <b>InnerTest1 test1 = new InnerTest1();</b>
 *       <b>InnerTest2 test2 = new InnerTest2();</b>
 * }
 * </pre>
 * </p>
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface InnerTest {

	/**
	 * Optionally specify <code>classNames</code> of the Inner Test Classes; the user could
	 * choose not to assign it, then Jtoc would assign the default name "InnerTest" to it.
	 */
	String[] classNames() default {"InnerTest"};
	
	/**
	 * Optionally specify <code>classNames</code> of the Inner Test Classes; the user could
	 * choose not to assign it, then Jtoc would assign the default name "innerTest" to it.
	 */
	String[] objectNames() default {"innerTest"};
}

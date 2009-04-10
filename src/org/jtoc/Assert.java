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

/**
 * A supplement for junit's assersion set, used to check whether the expected
 * condition is satisfied, where the users could choose their own way to deal
 * with the exception. for example, the code:
 * 
 * <pre>
 * try {
 * 	int i = -1;
 * 	assertConditionSatisfied(&quot;i &gt; 0&quot;, i &gt; 0, i);
 * } catch (PreConditionException e) {
 * 	System.err.println(e.getMessage());
 * }
 * </pre>
 * 
 * would generate a error message like this : "expected condition "i >
 * 0", but actual value was "-1"" </p>These methods can be used directly:
 * <code>Assert.assertEquals(...)</code>, however, they read better if they are
 * referenced through static import:<br/>
 * 
 * <pre>
 * import static org.jtoc.Assert.*;
 *    ...
 *    assertConditionSatisfied(...);
 * </pre>
 * 
 * @see PreConditionException
 */
public class Assert {

	/**
	 * Asserts that a condition is satisfied. If it isn't it throws an
	 * {@link PreConditionException} with the given message.
	 * 
	 * @param conditionDesc
	 *            the description of the condition
	 * @param condition
	 *            condition to be checked
	 * @param actualValue
	 *            the actualValue that should be checked
	 */
	static public void assertConditionSatisfied(String conditionDesc,
			boolean condition, Object actualValue) {
		if (!condition)
			throw new PreConditionException(conditionDesc, actualValue);
	}

	public static void main(String[] args) {
		try {
			int i = -1;
			assertConditionSatisfied("i > 0", i > 0, i);
		} catch (PreConditionException e) {
			System.err.println(e.getMessage());
		}
	}
}

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
 * <p>
 * The <code>PreConditionException</code> used to check whether the expected
 * condition is satisfied.
 * 
 * @see org.jtoc.Assert
 */
public class PreConditionException extends AssertionError {

	private static final long serialVersionUID = -3227456730849371952L;

	private String condition;

	/**
	 * get the condition description
	 * 
	 * @return the condition description
	 */
	public String getCondition() {
		return condition;
	}

	private String actualValue;

	/**
	 * get the actual value of the specified parameter
	 * 
	 * @return the actual value of the specified parameter
	 */
	public String getActualValue() {
		return actualValue;
	}

	/**
	 * Constructs a PreConditionException.
	 * Other Constructors are overloading this one.
	 * 
	 * @param condition
	 *            the condition description of the expected range
	 * @param actual
	 *            the actual string value
	 */
	public PreConditionException(String condition, String actual) {
		super("");
		this.condition = condition;
		this.actualValue = actual;
	}

	public PreConditionException(String condition, int actual) {
		this(condition, "" + actual);
	}

	public PreConditionException(String condition, long actual) {
		this(condition, "" + actual);
	}

	public PreConditionException(String condition, double actual) {
		this(condition, "" + actual);
	}

	public PreConditionException(String condition, float actual) {
		this(condition, "" + actual);
	}

	public PreConditionException(String condition, char actual) {
		this(condition, "" + actual);
	}

	public PreConditionException(String condition, boolean actual) {
		this(condition, "" + actual);
	}

	public PreConditionException(String condition, Object actual) {
		this(condition, actual.toString());
	}

	/**
	 * @see org.jtoc.Assert
	 * @see Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return "expected condition \"" + this.getCondition()
				+ "\", but actual value was \"" + this.getActualValue() + "\"";
	}

}

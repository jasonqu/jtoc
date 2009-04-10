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

package org.jtoc.convertor.cpunit;

/**
 * The base Exception in org.jtoc.convertor. When catched, the user could
 * get message like the following one that would help the user find the problem :
 * <pre>
 * (FileName.java:123) Message content
 * </pre>
 * 
 * @author Goddamned Qu
 * 
 */
public class JtocException extends Exception {
	
	private static final long serialVersionUID = -3004067126281077952L;

	/**
	 * 
	 * @param message Error Message
	 * @param fileName the name of the file being parsed
	 * @param lineNumber the number of the line caused this Exception 
	 */
	public JtocException(String message, String fileName, int lineNumber) {
		super('('+fileName+':'+lineNumber+") " + message);
	}
}

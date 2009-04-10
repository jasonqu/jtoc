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
 * thrown when a Jtoc Annotation is not in the right format.
 * 
 * @see JtocException
 */
public class JtocFormatException extends JtocException {

	private static final long serialVersionUID = 4945829155509546021L;

	/**
	 * 
	 * @param annoExpr the expression of the Jtoc Annotation
	 * @param fileName fileName the name of the file being parsed
	 * @param lineNumber lineNumber the number of the line caused this Exception 
	 */
	public JtocFormatException(String annoExpr, String fileName, int lineNumber) {
		super("The Input Jtoc Annotation has wrong format : " + annoExpr, fileName, lineNumber);
	}
}

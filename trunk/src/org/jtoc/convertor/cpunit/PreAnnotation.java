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

import japa.parser.ast.expr.AnnotationExpr;

/**
 * class used to contain information of the Pre Jtoc Annotation.
 */
public class PreAnnotation extends JtocAnnotation {

	/**
	 * set the compilation unit and initial the local variables
	 * 
	 * @param ae
	 *            the input compilation unit
	 */
	public PreAnnotation(AnnotationExpr ae) {
		super(ae);
		this.setHead("@Pre");
	}

	/**
	 * to determine whether the input expression is an Instance of PreAnnotation
	 * 
	 * @param annotation
	 *            the annotation
	 * @return true if the annotation's name is "Pre"
	 */
	public static boolean isInstance(AnnotationExpr annotation) {
		return annotation != null && annotation.getName().getName().equals("Pre");
	}

	/**
	 * the method to get an PreAnnotation from an AnnotationExpr
	 * 
	 * @param ae
	 *            the AnnotationExpr need to be parsed
	 * @return the PreAnnotation contains the AnnotationExpr's information
	 * @throws JtocFormatException
	 */
	public static PreAnnotation getPreAnnotationFromAnnoExpr(AnnotationExpr ae)
			throws JtocFormatException {
		if (!isInstance(ae))
			return null;
		PreAnnotation pa = new PreAnnotation(ae);
		pa.parse();
		return pa;
	}

	/**
	 * get the java code from the information it contains.
	 * 
	 * @param methodInfo
	 * @return statements of the return code
	 */
	public String getStatement(MethodInfo methodInfo) {
		StringBuilder sb = new StringBuilder(128);
		sb.append(this.getTestObject());
		sb.append(".");
		if (this.testMethod.isEmpty())
			this.setTestMethod(methodInfo.getName() + "PreCheck");
		sb.append(this.getTestMethod());
		sb.append('(');
		if (this.parameters.isEmpty())
			this.setParameters(methodInfo.getParaList());
		sb.append(this.getParameters());
		sb.append(");");
		return sb.toString();
	}

}

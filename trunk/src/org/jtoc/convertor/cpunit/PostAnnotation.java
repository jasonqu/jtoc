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
 * class used to contain information of the Post Jtoc Annotation.
 */
public class PostAnnotation extends JtocAnnotation {

	/**
	 * set the compilation unit and initial the local variables
	 * 
	 * @param ae
	 *            the input compilation unit
	 */
	public PostAnnotation(AnnotationExpr ae) {
		super(ae);
		this.setHead("@Post");
	}

	/**
	 * to determine whether the input expression is an Instance of PostAnnotation
	 * 
	 * @param annotation
	 *            the annotation
	 * @return true if the annotation's name is "Post"
	 */
	public static boolean isInstance(AnnotationExpr annotation) {
		return annotation != null && annotation.getName().getName().equals("Post");
	}
	
	/**
	 * the method to get an PostAnnotation from an AnnotationExpr
	 * 
	 * @param ae
	 *            the AnnotationExpr need to be parsed
	 * @return the PostAnnotation contains the AnnotationExpr's information
	 * @throws JtocFormatException
	 */
	public static PostAnnotation getPostAnnotationFromAnnoExpr(AnnotationExpr ae) throws JtocFormatException{
		if(!isInstance(ae))
			return null;
		PostAnnotation pa = new PostAnnotation(ae);
		pa.parse();
		return pa;
	}

	/**
	 * whether this annotation need generate the parameters automatically
	 * 
	 * @return true if parameters need be generated automatically
	 */
	public boolean needGenerateParas(){
		return this.parameters.isEmpty();
	}

	/**
	 * get the java code from the information it contains. if parameters need
	 * not to be generated then it would get the full statement with the
	 * specified parameters; or else it would get the statements without the
	 * parameters.
	 * 
	 * @param methodInfo
	 *            whose returnType should not be void
	 * @param returnParaName
	 * @return statements of the return code
	 */
	public String getStatementDecl(MethodInfo methodInfo) {
		StringBuilder sb = new StringBuilder(128);
		sb.append(this.getTestObject());
		sb.append(".");
		if (this.testMethod.isEmpty())
			this.setTestMethod(methodInfo.getName() + "PostCheck");
		sb.append(this.getTestMethod());
		sb.append('(');
		if (!this.needGenerateParas()){
			sb.append(this.getParameters());
			sb.append(");");
		}
		return sb.toString();
	}

	/**
	 * this method is called when the returnValue is already printed, and it
	 * would print the method's input parameters to complete the statement.
	 * 
	 * @param methodInfo
	 *            whose returnType should not be void
	 * @return statements of the return code
	 */
	public String getStatementParas(MethodInfo methodInfo) {
		StringBuilder sb = new StringBuilder(128);
		if (this.needGenerateParas()){
			if(!methodInfo.getParaList().isEmpty())
				sb.append(", " + methodInfo.getParaList());
			sb.append(");");
		}
		return sb.toString();
	}
	
	/**
	 * get the java code from the information it contains.
	 * 
	 * @param methodInfo
	 *            whose returnType should be void
	 * @return statements of the return code
	 */
	public String getStatement(MethodInfo methodInfo){// throws JtocException {
//		if(methodInfo.getReturnType() != "void")
//			throw new JtocException("Wrong usage of the getStatement for Post annotation."
//					,this.getFilename(), this.unit.getBeginLine());

		StringBuilder sb = new StringBuilder(128);
		sb.append(this.getTestObject());
		sb.append(".");
		if (this.testMethod.isEmpty())
			this.setTestMethod(methodInfo.getName() + "PostCheck");
		sb.append(this.getTestMethod());
		sb.append('(');
		if (this.needGenerateParas())
			this.setParameters(methodInfo.getParaList());
		sb.append(this.getParameters());
		sb.append(");");
		return sb.toString();
	}

}
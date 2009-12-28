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

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * class used to contain information of the Post Jtoc Annotation.
 * 
 * @author Goddamned Qu
 */
public class PostAnnotation extends JtocAnnotation {

	private static Log logger = LogFactory.getLog(PostAnnotation.class);
	
	/**
	 * used for test
	 */
	public PostAnnotation() {
		this(null);
	}
	
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
	 * the method to determine whether the input annotation expr is an Instance
	 * 
	 * @param content
	 *            annotation expr
	 * @return
	 */
	public static boolean isInstance(String content) {
		return content != null
				&& (content.equals("@Post") || content.startsWith("@Post("));
	}
	
	@Override
	protected boolean isInstanceLocal(String content) {
		return PostAnnotation.isInstance(content);
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
		if(!isInstance(ae.toString()))
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
		else
			sb.append(this.getParameters());
		sb.append(");");
		return sb.toString();
	}

	/**
	 * test class
	 */
	class MethodVisitor extends VoidVisitorAdapter<Object> {
		public MethodVisitor(){}
		
		@Override
		public void visit(MethodDeclaration n, Object arg) {
			List<AnnotationExpr> list = n.getAnnotations();
			if (list == null) // the method has no annotation.
				return;
			
			try {
				for (AnnotationExpr ae : list) {
					if(PostAnnotation.isInstance(ae.toString())){
						PostAnnotation i = new PostAnnotation(ae);
						i.parse();
						System.out.println(i.toString());
					}
				}
			} catch (JtocFormatException e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	/**
	 * test method
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// creates an input stream for the file to be parsed
		FileInputStream in = new FileInputStream(
				"./test/org/jtoc/convertor/cpunit/PreAnnoTest.java");

		CompilationUnit cu;
		try {
			// parse the file
			cu = japa.parser.JavaParser.parse(in);
		} finally {
			in.close();
		}

		PostAnnotation pa = new PostAnnotation();
		// visit and print the methods names
		JtocNode.setFilename("PreAnnoTest.java");
		MethodVisitor visitor = pa.new MethodVisitor();
		visitor.visit(cu, null);
	}
}
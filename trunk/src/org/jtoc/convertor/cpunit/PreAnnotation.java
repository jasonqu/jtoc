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
 * class used to contain information of the Pre Jtoc Annotation.
 * 
 * @author Goddamned Qu
 */
public class PreAnnotation extends JtocAnnotation {

	private static Log logger = LogFactory.getLog(PreAnnotation.class);

	/**
	 * used for test
	 */
	public PreAnnotation() {
		this(null);
	}

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
	 * the method to determine whether the input annotation expr is an Instance
	 * 
	 * @param content
	 *            annotation expr
	 * @return
	 */
	public static boolean isInstance(String content) {
		return content != null
				&& (content.equals("@Pre") || content.startsWith("@Pre("));
	}

	@Override
	protected boolean isInstanceLocal(String content) {
		return PreAnnotation.isInstance(content);
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
		if (!isInstance(ae.toString()))
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

	/**
	 * test class
	 */
	class MethodVisitor extends VoidVisitorAdapter<Object> {
		public MethodVisitor() {
		}

		@Override
		public void visit(MethodDeclaration n, Object arg) {
			List<AnnotationExpr> list = n.getAnnotations();
			if (list == null) // the method has no annotation.
				return;

			try {
				for (AnnotationExpr ae : list) {
					if (PreAnnotation.isInstance(ae.toString())) {
						PreAnnotation i = new PreAnnotation(ae);
						i.parse();
						logger.info(i.toString());
					}
				}
			} catch (JtocFormatException e) {
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * test method
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// creates an input stream for the file to be parsed
		FileInputStream in = new FileInputStream(
				"D:/Project/Jtoc/Project/Jtoc/test/org/jtoc/convertor/cpunit/PreAnnoTest.java");

		CompilationUnit cu;
		try {
			// parse the file
			cu = japa.parser.JavaParser.parse(in);
		} finally {
			in.close();
		}

		PreAnnotation pa = new PreAnnotation();
		// visit and print the methods names
		JtocNode.setFilename("PreAnnoTest.java");
		MethodVisitor visitor = pa.new MethodVisitor();
		visitor.visit(cu, null);
	}
}

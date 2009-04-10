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
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Class for information of the InnerTestAnnotation elements.
 * 
 * @author Goddamned Qu
 */
public class InnerTestAnnotation extends JtocNode<AnnotationExpr> {

	private static Logger logger = Logger.getLogger(InnerTestAnnotation.class.getName());

	/**
	 * used for test
	 */
	public InnerTestAnnotation() {
		this(null);
	}
	
	/**
	 * set the compilation unit and initial the local variables
	 * 
	 * @param ae
	 *            the input compilation unit
	 */
	public InnerTestAnnotation(AnnotationExpr ae) {
		super(ae);
		this.init();
	}
	
	/**
	 * initialize the local variables to their default values
	 */
	protected void init() {
		classNames = new String[]{"InnerTest"};
		objectNames = new String[]{"innerTest"};
	}
	
	private String[] classNames;
	private String[] objectNames;
	
	/**
	 * get the names of the test Classes, which should be declared as a
	 * InnerClass
	 * 
	 * @return the names of the test Classes
	 */
	public String[] getClassNames() {
		return classNames;
	}

	/**
	 * get the names of the test Objects, which should be referred by the @Pre
	 * and @Post Annotations declared in the class
	 * 
	 * @return the names of the test Objects
	 */
	public String[] getObjectNames() {
		return objectNames;
	}

	/**
	 * the method to determine whether the input annotation expr is an Instance
	 * of InnerTestAnnotation
	 * 
	 * @param content
	 *            annotation expr
	 * @return true if it is an Instance of InnerTestAnnotation
	 */
	public static boolean isInstance(String content) {
		return content != null
				&& (content.equals("@InnerTest") || content.startsWith("@InnerTest("));
	}
	
	protected boolean isInstanceLocal(String content) {
		return InnerTestAnnotation.isInstance(content);
	}
	
	/**
	 * parse a given annotation expression and set the local variables with the
	 * specified values.
	 * 
	 * @throws JtocFormatException
	 */
	@SuppressWarnings("static-access")
	public void parse() throws JtocFormatException {
		String content = this.unit.toString();
		
		if (!this.isInstanceLocal(content))
			return;
		
		logger.debug("Begin parse : "+content);
		this.init();
		
		if (content.equals("@InnerTest"))
			return;	
		
		logger.debug(this.unit.getClass().toString());
		if(unit instanceof NormalAnnotationExpr){
			NormalAnnotationExpr nu = (NormalAnnotationExpr)unit;
			List<MemberValuePair> list = nu.getPairs();
			if (list == null)
				return;
			
			for(MemberValuePair mp : list){
				if(mp.getName().equals("classNames")){
					this.classNames = this.getStringArrayFromValue(mp);
				} else if (mp.getName().equals("objectNames")) {
					this.objectNames = this.getStringArrayFromValue(mp);
				} else {
					throw new JtocFormatException(content, this.getFilename(),
							this.unit.getBeginLine());
				}
			}
			
			if(this.classNames.length != this.objectNames.length)
				throw new JtocFormatException(content, this.getFilename(),
						this.unit.getBeginLine());
		}
	}
	
	/**
	 * the method to get an InnerTestAnnotation from an AnnotationExpr
	 * 
	 * @param ae
	 *            the AnnotationExpr need to be parsed
	 * @return the InnerTestAnnotation contains the AnnotationExpr's information
	 * @throws JtocFormatException
	 */
	public static InnerTestAnnotation getInnerTestAnnotationFromAnnoExpr(
			AnnotationExpr ae) throws JtocFormatException {
		if(!isInstance(ae.toString()))
			return null;
		InnerTestAnnotation pa = new InnerTestAnnotation(ae);
		pa.parse();
		return pa;
	}
	
	/**
	 * get the java code from the information it contains.
	 * 
	 * @return statements of the return code
	 */
	public String[] getStatements(){
		if(classNames.length != objectNames.length)
			return null;
		
		String[] states = new String[classNames.length];
		for(int i = 0; i< states.length; i++){
			states[i] = classNames[i] + ' '+objectNames[i]+
			" = new "+classNames[i]+"();";
		}
		return states;
	}
	
	/**
	 * for test
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder(128);
		sb.append("classNames : ");
		for(String s : classNames){
			sb.append('(');
			sb.append(s);
			sb.append(')');
		}
		
		sb.append(" objectNames : ");
		for(String s : objectNames){
			sb.append('(');
			sb.append(s);
			sb.append(')');
		}
		return sb.toString();
	}

	/**
	 * test class
	 */
	class ClassDeclarationVisitor extends VoidVisitorAdapter<Object> {
		public ClassDeclarationVisitor(){}
		
		@Override
		public void visit(ClassOrInterfaceDeclaration n, Object arg) {
			List<AnnotationExpr> list = n.getAnnotations();
			if (list == null) // the method has no annotation.
				return;
			
			try {
				for (AnnotationExpr ae : list) {
					if(InnerTestAnnotation.isInstance(ae.toString())){
						InnerTestAnnotation i = new InnerTestAnnotation(ae);
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
				"D:/Project/Jtoc/Project/Jtoc/test/org/jtoc/convertor/cpunit/InnerTestAnnoTest.java");

		CompilationUnit cu;
		try {
			// parse the file
			cu = japa.parser.JavaParser.parse(in);
		} finally {
			in.close();
		}

		InnerTestAnnotation ita = new InnerTestAnnotation();
		// visit and print the methods names
		JtocNode.setFilename("InnerTestAnnoTest.java");
		ClassDeclarationVisitor visitor = ita.new ClassDeclarationVisitor();
		visitor.visit(cu, null);
	}
}

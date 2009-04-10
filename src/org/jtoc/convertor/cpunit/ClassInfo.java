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

import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.AnnotationExpr;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Abstract class for information of the ClassOrInterfaceDeclaration elements.
 * 
 * @author Goddamned Qu
 */
public class ClassInfo extends JtocNode<ClassOrInterfaceDeclaration> {

	private static Logger logger = Logger.getLogger(ClassInfo.class.getName());

	/**
	 * set the compilation unit and initial the local variables
	 * 
	 * @param n
	 *            the input compilation unit
	 */
	public ClassInfo(ClassOrInterfaceDeclaration n){
		super(n);
	}
	
	private InnerTestAnnotation ita = null;

	/**
	 * get the InnerTestAnnotation of this class
	 * 
	 * @return the InnerTestAnnotation of this class
	 */
	public InnerTestAnnotation getInnerTestAnnotation() {
		return ita;
	}
	
	/**
	 * get the name of the class
	 * 
	 * @return the name of the class
	 */
	public String getName(){
		return this.unit.getName();
	}
	
	private ArrayList<MethodInfo> methodInfos = new ArrayList<MethodInfo>();
	
	/**
	 * get the MethodInfos declared in this class, used in validating the
	 * constraints
	 * 
	 * @return the MethodInfos declared in this class
	 */
	private ArrayList<MethodInfo> getMethodInfos() {
		return methodInfos;
	}
	
	private ArrayList<JtocNode> nodes = new ArrayList<JtocNode>();
	
	/**
	 * get the InnerClasses and methods which are annotated by JtocAnnotation in
	 * this class, used to process the JtocNodes sequentially
	 * 
	 * @return the InnerClasses and methods which are annotated by
	 *         JtocAnnotation in this class
	 */
	public ArrayList<JtocNode> getNodes() {
		return nodes;
	}

	// used in validating the constraints
	private ArrayList<String> classNames = new ArrayList<String>();
	
	/**
	 * the method to get an ClassInfo from an ClassOrInterfaceDeclaration
	 * 
	 * @param md
	 *            the ClassOrInterfaceDeclaration need to be parsed
	 * @return the ClassInfo contains the ClassOrInterfaceDeclaration's
	 *         information
	 * @throws JtocException
	 */
	public static ClassInfo getClassInfoFromClassDecl(
			ClassOrInterfaceDeclaration cd) throws JtocException {
		ClassInfo ci = new ClassInfo(cd);
		ci.parse();
		return ci;
	}

	/**
	 * parse a given ClassOrInterfaceDeclaration and set the local variables
	 * with the specified values.
	 * 
	 * @throws JtocFormatException
	 */
	public void parse() throws JtocException {
		logger.debug("parse class : " + this.unit.getName());
		
		this.ita = this.getInnerTestAnnotation(this.unit.getAnnotations());
		
		List<BodyDeclaration> list = this.unit.getMembers();
		if(list == null)
			return;
		
		for(BodyDeclaration bd : list){
			logger.debug(bd.getClass().toString() +" "+ bd.getBeginLine());
			
			if(bd instanceof ClassOrInterfaceDeclaration){
				this.nodes.add(ClassInfo.getClassInfoFromClassDecl(
						(ClassOrInterfaceDeclaration) bd));
				this.classNames.add(((ClassOrInterfaceDeclaration) bd)
						.getName());
			}
			else if (bd instanceof MethodDeclaration) {
				MethodInfo mi = MethodInfo
						.getMethodInfoFromMethodDecl((MethodDeclaration) bd);
				if (mi.getPreAnno() == null && mi.getPostAnno() == null)
					continue;
				
				this.nodes.add(mi);
				this.methodInfos.add(mi);
			}
		}
		
		this.validate();
	}

	/**
	 * used to validate the constraints of the jtoc annotations
	 * 
	 * @throws JtocException
	 *             if violate any constraint
	 */
	private void validate() throws JtocException {
		if(ita == null && !this.getMethodInfos().isEmpty())
			throw new JtocException("The class has no InnerTest Annotation",
					this.getFilename(),
					this.getMethodInfos().get(0).getPreviousNode().getBeginLine());
		
		if (ita == null)
			return;

		logger.debug("InnerClassNames " + this.classNames);
		for(String className : this.ita.getClassNames()){
			if(!this.classNames.contains(className))
				throw new JtocException("The class has no InnerClass called "
						+ className, this.getFilename(), this.ita.getBeginLine());
		}
	}

	/**
	 * process the Annotations of this class and get the InnerTestAnnotation if
	 * this class has one
	 * 
	 * @param list
	 *            the annotations of this class
	 * @return the InnerTestAnnotation of this class
	 * @throws JtocFormatException
	 */
	private InnerTestAnnotation getInnerTestAnnotation(List<AnnotationExpr> list) throws JtocFormatException{
		logger.debug(list);
		if (list == null)
			return null;
		
		for(AnnotationExpr ae : list)
			if(InnerTestAnnotation.isInstance(ae.toString()))
				return InnerTestAnnotation.getInnerTestAnnotationFromAnnoExpr(ae);
		return null;
	}
}

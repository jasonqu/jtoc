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
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NormalAnnotationExpr;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Abstract class for information of the JtocAnnotation elements.
 * 
 * @author Goddamned Qu
 */
public abstract class JtocAnnotation extends JtocNode<AnnotationExpr> {

	private static Log logger = LogFactory.getLog(JtocAnnotation.class);

	/**
	 * set the compilation unit and initial the local variables
	 * 
	 * @param ae
	 *            the input compilation unit
	 */
	public JtocAnnotation(AnnotationExpr ae) {
		super(ae);
		this.init();
	}
	
	/**
	 * initialize the local variables to their default values
	 */
	protected void init() {
		testObject = "innerTest";
		testMethod = "";
		parameters = "";
	}
	
	protected String testObject;
	protected String testMethod;
	protected String parameters;

	/**
	 * get the name of the test object. For detailed info, please refer to the
	 * javadoc of the @Pre and @Post
	 * 
	 * @return the name of the test object
	 */
	public final String getTestObject() {
		return testObject;
	}

	/**
	 * set the name of the test object. For detailed info, please refer to the
	 * javadoc of the @Pre and @Post
	 * 
	 * @param testObject
	 *            the name of the test object
	 */
	public final void setTestObject(String testObject) {
		if (!testObject.isEmpty())
			this.testObject = testObject;
	}

	/**
	 * get the name of the test method. For detailed info, please refer to the
	 * javadoc of the @Pre and @Post
	 * 
	 * @return the name of the test method
	 */
	public final String getTestMethod() {
		return testMethod;
	}

	/**
	 * set the name of the test method. For detailed info, please refer to the
	 * javadoc of the @Pre and @Post
	 * 
	 * @param testMethod
	 *            the name of the test method
	 */
	public final void setTestMethod(String testMethod) {
		this.testMethod = testMethod;
	}

	/**
	 * get the parameters' String of the test method. For detailed info, please
	 * refer to the javadoc of the @Pre and @Post
	 * 
	 * @return the parameters' String of the test method
	 */
	public final String getParameters() {
		return parameters;
	}

	/**
	 * set the parameters' String of the test method. For detailed info, please
	 * refer to the javadoc of the @Pre and @Post
	 * 
	 * @param parameters
	 *            the parameters' String of the test method
	 */
	public final void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	/** used to pull up the parse method */
	protected String head = "";

	/**
	 * get the String represents the Marker format of the Annotation
	 * 
	 * @return should only be "@Pre" or "@Post"
	 */
	protected String getHead() {
		return head;
	}

	/**
	 * set the String represents the Marker format of the Annotation
	 * 
	 * @param head should only be "@Pre" or "@Post"
	 */
	protected void setHead(String head) {
		this.head = head;
	}

	/**
	 * parse a given annotation expression and set the local variables with the
	 * specified values.
	 * p.a. must be sure that the compile unit is with the right type.
	 * 
	 * @throws JtocFormatException
	 */
	@SuppressWarnings("static-access")
	public void parse() throws JtocFormatException {
		// XXX need refactor
		String content = this.unit.toString();
		
		logger.debug("Begin parse : "+content);
		this.init();
		
		// default value
		if (content.equals(this.getHead()))
			return;
		
		logger.debug(this.unit.getClass().toString());
		if(unit instanceof NormalAnnotationExpr){
			List<MemberValuePair> list = ((NormalAnnotationExpr)unit).getPairs();
			if (list == null)// have to be kept for "@Post()" or "@Pre()"
				return;
			
			for(MemberValuePair mp : list){
				if(mp.getName().equals("testObject")){
					this.setTestObject(this.getStringFromValue(mp));
				} else if (mp.getName().equals("testMethod")) {
					this.setTestMethod(this.getStringFromValue(mp));
				} else if (mp.getName().equals("parameters")) {
					this.setParameters(this.getStringFromValue(mp));
				} else {
					throw new JtocFormatException(content, this.getFilename(),
							this.unit.getBeginLine());
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.jtoc.convertor.cpunit.JtocNode#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(128);
		sb.append(this.getHead());
		sb.append("(testObject = \"");
		sb.append(this.getTestObject());
		sb.append("\", testMethod = \"");
		sb.append(this.getTestMethod());
		sb.append("\", parameters = \"");
		sb.append(this.getParameters());
		sb.append("\")");
		return sb.toString();
	}

}

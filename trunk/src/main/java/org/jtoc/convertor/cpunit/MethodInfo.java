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

import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.expr.AnnotationExpr;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * class used to contain information of the Methods.
 */
public class MethodInfo extends JtocNode<MethodDeclaration> {

	private static Log logger = LogFactory.getLog(InnerTestAnnotation.class);

	/**
	 * set the compilation unit and initial the local variables
	 * 
	 * @param md
	 *            the input compilation unit
	 */
	public MethodInfo(MethodDeclaration md) {
		super(md);
	}

	/**
	 * Get the method's name
	 * 
	 * @return the method's name
	 */
	public String getName() {
		return this.unit.getName();
	}

	/**
	 * Get the method's return type
	 * 
	 * @return the method's return type
	 */
	public String getReturnType() {
		return this.unit.getType().toString();
	}

	/**
	 * Get the method body's begin line
	 * 
	 * @return the method body's begin line
	 */
	public int getBodyBeginLine() {
		return this.unit.getBody().getBeginLine();
	}

	/**
	 * Get the method body's end line
	 * 
	 * @return the method body's end line
	 */
	public int getBodyEndLine() {
		return this.unit.getBody().getEndLine();
	}

	/**
	 * Check whether the the method's body only occupies one line of the code
	 * 
	 * @return true if the method's body only occupies one line of the code
	 */
	public boolean hasOneLineBody() {
		return this.getBodyBeginLine() == this.getBodyEndLine();
	}

	/**
	 * the method to get the parameters' list of the specified method
	 * 
	 * @return the String represents the parameters' list.
	 */
	public String getParaList() {
		List<Parameter> paras = this.unit.getParameters();
		if (paras == null || paras.isEmpty())
			return "";

		StringBuilder sb = new StringBuilder();
		for (Parameter para : paras) {
			sb.append(para.getId().toString());
			sb.append(", ");
		}
		return sb.delete(sb.length() - 2, sb.length()).toString();
	}

	private PreAnnotation pre = null;

	/**
	 * Get the PreAnnotation of this method.
	 * 
	 * @return the PreAnnotation of this method
	 */
	public PreAnnotation getPreAnno() {
		return pre;
	}

	/**
	 * Set the PreAnnotation of this method.
	 * 
	 * @param pre
	 *            the PreAnnotation of this method
	 */
	public void setPreAnno(PreAnnotation pre) {
		this.pre = pre;
	}

	private PostAnnotation post = null;

	/**
	 * Get the PostAnnotation of this method.
	 * 
	 * @return the PostAnnotation of this method
	 */
	public PostAnnotation getPostAnno() {
		return post;
	}

	/**
	 * Set the PostAnnotation of this method.
	 * 
	 * @param post
	 *            the PostAnnotation of this method
	 */
	public void setPostAnno(PostAnnotation post) {
		this.post = post;
	}

	/**
	 * Get this method's the JtocAnnotation whose begin line number is smaller.
	 * 
	 * @return the JtocAnnotation whose begin line number is smaller. Null if
	 *         it's not existed
	 */
	public JtocAnnotation getPreviousNode() {
		if (pre == null)
			return post;
		else if (post == null)
			return pre;
		else
			return (pre.getBeginLine() < post.getBeginLine()) ? pre : post;
	}

	/**
	 * Get this method's the JtocAnnotation whose begin line number is larger.
	 * 
	 * @return the JtocAnnotation whose begin line number is larger. Null if
	 *         it's not existed
	 */
	public JtocAnnotation getFollowingNode() {
		if (pre == null || post == null)
			return null;
		return (pre.getBeginLine() < post.getBeginLine()) ? post : pre;
	}

	/**
	 * the method to get an MethodInfo from an MethodDeclaration
	 * 
	 * @param md
	 *            the MethodDeclaration need to be parsed
	 * @return the MethodInfo contains the MethodDeclaration's information
	 * @throws JtocFormatException
	 */
	public static MethodInfo getMethodInfoFromMethodDecl(MethodDeclaration md)
			throws JtocFormatException {
		MethodInfo mi = new MethodInfo(md);
		mi.parse();
		return mi;
	}

	/**
	 * parse a given MethodDeclaration expression and set the local variables
	 * with the specified values.
	 * 
	 * @throws JtocFormatException
	 */
	public void parse() throws JtocFormatException {
		List<AnnotationExpr> list = this.unit.getAnnotations();
		if (list == null) // the method has no annotation.
			return;

		logger.debug("parse method : " + this.unit.getName());
		for (AnnotationExpr ae : list) {
			if (PreAnnotation.isInstance(ae)) {
				this.setPreAnno(PreAnnotation.getPreAnnotationFromAnnoExpr(ae));
			} else if (PostAnnotation.isInstance(ae)) {
				this.setPostAnno(PostAnnotation
						.getPostAnnotationFromAnnoExpr(ae));
			} else {
				continue;
			}
		}
	}
}

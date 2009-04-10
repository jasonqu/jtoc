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

import japa.parser.ast.Node;
import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.StringLiteralExpr;

/**
 * Abstract class used to contain information of the Jtoc elements.
 * 
 * @author Goddamned Qu
 */
public abstract class JtocNode<C extends Node> {

	// All nodes refer to the same file in every conversion
	protected static String filename = "";

	/**
	 * Get the parsed file's name that All nodes referring to
	 * 
	 * @return the filename used in locating the Exception
	 */
	public final static String getFilename() {
		return filename;
	}

	/**
	 * Set the parsed file's name that All nodes referring to
	 * 
	 * @param filename
	 *            the name of the file being parsed
	 */
	public final static void setFilename(String filename) {
		JtocNode.filename = filename;
	}

	// the compile unit this JtocNode contains
	C unit = null;

	public JtocNode(C c) {
		this.unit = c;
	}

	/**
	 * Get the begin line of this node.
	 * 
	 * @return the begin line of this node
	 */
	public final int getBeginLine() {
		return unit.getBeginLine();
	}

	/**
	 * Get the end line of this node.
	 * 
	 * @return the end line of this node
	 */
	public final int getEndLine() {
		return unit.getEndLine();
	}

	/**
	 * Get the String array value of the param pair
	 * 
	 * @param mp
	 * @return value of the param pair; null if it is not a String Array
	 */
	protected final String[] getStringArrayFromValue(MemberValuePair mp) {
		if (mp.getValue() instanceof StringLiteralExpr) {
			return new String[] { ((StringLiteralExpr) mp.getValue())
					.getValue() };
		} else if (mp.getValue() instanceof ArrayInitializerExpr) {
			StringLiteralExpr[] t = (StringLiteralExpr[]) ((ArrayInitializerExpr) mp
					.getValue()).getValues().toArray(new StringLiteralExpr[0]);
			String[] result = new String[t.length];
			for (int i = 0; i < t.length; i++) {
				result[i] = t[i].getValue();
			}
			return result;
		}
		return null;
	}

	/**
	 * Get the String value of the param pair
	 * 
	 * @param mp
	 * @return value of the param pair; null if it is not a String
	 */
	protected final String getStringFromValue(MemberValuePair mp) {
		if (mp.getValue() instanceof StringLiteralExpr)
			return ((StringLiteralExpr) mp.getValue()).getValue();
		return null;
	}

	/**
	 * Used for test.
	 * 
	 * @return the String representation of this node
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(32);
		sb.append(" BeginLine:");
		sb.append(this.getBeginLine());
		sb.append(" EndLine:");
		sb.append(this.getEndLine());
		return sb.toString();
	}

}

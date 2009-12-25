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

package org.jtoc.convertor;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 
import org.jtoc.convertor.cpunit.JtocNode;

/**
 * Abstract class used to contain commen codes of the convertors.
 * 
 * @author Goddamned Qu
 */
public abstract class UnitConvertor<C extends JtocNode> {
	
	private static Log logger = LogFactory.getLog(UnitConvertor.class);

	// the number and the content of the line being parsed
	protected static int lineNum = 0;
	protected static String line = "";
	
	// the JtocNode this Convertor represents
	protected C unit = null;

	/**
	 * set the JtocNode this Convertor represents
	 * <p>
	 * <b>Notice : do not call the init method since it initializes the static
	 * variables</b>
	 * </p>
	 * 
	 * @param c
	 *            the input JtocNode
	 */
	public UnitConvertor(C c) {
		this.unit = c;
	}

	/**
	 * initialize the static variables to their default values
	 */
	public static void init() {
		line = "";
		lineNum = 0;
	}

	/**
	 * encapsulation of the get next line action since when a new line is
	 * obtained, the number of the line has to be added by 1
	 * 
	 * @param in
	 *            the input file Scanner
	 * @return the content of the line obtained
	 */
	protected String getNextLine(Scanner in) {
		lineNum++;
		return in.nextLine();
	}

	/**
	 * remove the specified lines of code
	 * 
	 * @param in
	 *            the input file Scanner
	 * @param endLineNum
	 *            till which number the obtained line would be removed
	 */
	protected void removeLinesTo(Scanner in, int endLineNum) {
		while (lineNum < endLineNum)
			line = this.getNextLine(in);
	}

	/**
	 * print the specified lines of code
	 * 
	 * @param in
	 *            the input file Scanner
	 * @param out
	 *            the output PrintStream
	 * @param endLineNum
	 *            till which number the obtained line would be printed
	 */
	protected void printLinesTo(Scanner in, PrintWriter out, int endLineNum) {
		while (lineNum < endLineNum) {
			line = this.getNextLine(in);
			out.println(line);
		}
	}

	// the protected local variables used in ClassConvertor and
	// SingleFileConvertor
	// the next line number should be the next node's begin line minus 1
	protected int nextLine = -1;
	// the JtocNodes this Converter contains
	protected Iterator<JtocNode> iter = null;
	// the next node to be processed
	protected JtocNode node = null;

	/**
	 * used to iterate the JtocNodes this Converter contains and set to the next
	 * node it's going to process. If there there's no more node to be
	 * processed, then the local variable would stay the same.
	 */
	protected void setEnvParas() {
		if(iter.hasNext()){
			node = iter.next();
			nextLine = this.node.getBeginLine() - 1;
			logger.debug("nextLine changed to " + nextLine);
		}
	}
}

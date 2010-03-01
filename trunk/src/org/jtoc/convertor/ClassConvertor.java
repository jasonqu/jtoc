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
import java.util.Scanner;
import java.util.regex.Matcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jtoc.convertor.cpunit.ClassInfo;
import org.jtoc.convertor.cpunit.MethodInfo;
import org.jtoc.convertor.utils.RegPatternFactory;

/**
 * class used to convert classes.
 * 
 * @author Goddamned Qu
 */
public class ClassConvertor extends UnitConvertor<ClassInfo> {

	private static Log logger = LogFactory.getLog(ClassConvertor.class.getName());

	/**
	 * set the JtocNode this Convertor represents
	 * 
	 * @param ci
	 *            the input ClassInfo
	 */
    public ClassConvertor(ClassInfo ci) {
    	super(ci);
    	iter = ci.getNodes().iterator();
    }
    
	/**
	 * the method to process the Jtoc annotated ClassInfo java codes into test codes.
	 * 
	 * @param in
	 *            the input file Scanner
	 * @param out
	 *            the output PrintStream
	 */
	public void process(Scanner in, PrintWriter out) {
		this.removeJtocAnnos(in, out);
		this.setEnvParas();
		
		while (lineNum < this.unit.getEndLine() - 1) {
			if (lineNum == nextLine) {
				if(node instanceof ClassInfo){
					ConvertorFactory.getClassConvertor((ClassInfo) node)
							.process(in, out);
				}
				else if(node instanceof MethodInfo){
					ConvertorFactory.getMethodConvertor((MethodInfo) node)
							.process(in, out);
				}
				else{
					// should never be here
					out.println("should not be here" + lineNum + line);
				}
				
				this.setEnvParas();
			}
			else {
				line = this.getNextLine(in);
				//out.println("CC "+lineNum+line);
				out.println(line);
			}
		}
		
		this.addStatementsToLastLine(in, out);
	}
	
	/**
	 * called by method "process", in order to remove the InnerTestAnnotation
	 * 
	 * @param in
	 *            the input file Scanner
	 * @param out
	 *            the output PrintStream
	 */
	private void removeJtocAnnos(Scanner in, PrintWriter out){
		if(this.unit.getInnerTestAnnotation() == null)
			return;
		
		this.printLinesTo(in, out, this.unit.getInnerTestAnnotation().getBeginLine()-1);
		logger.debug("before remove the 1st anno, lineNum : " + lineNum);
		this.removeLinesTo(in, this.unit.getInnerTestAnnotation().getEndLine());
	}
	
	/**
	 * called by method "process", used to add the test objects' statements
	 * to the last line of the class declaration
	 * 
	 * @param in
	 *            the input file Scanner
	 * @param out
	 *            the output PrintStream
	 */
	private void addStatementsToLastLine(Scanner in, PrintWriter out) {
		line = this.getNextLine(in);

		if(this.unit.getInnerTestAnnotation() == null){
			out.println(line);
			return;
		}
		
		Matcher matcher = RegPatternFactory.instance().getPattern("}").matcher(
				line);
		if (matcher.matches()) {
			this.printStatements(out, matcher.group(1) + '\t');
			out.println(line);
		} else {
			// which requires "}" must be the last non-white char in this line
			matcher = RegPatternFactory.instance().getPattern("Code}").matcher(
					line);
			if (matcher.matches()) {
				out.println(matcher.group(1));
				this.printStatements(out, matcher.group(2) + '\t');
				out.println(matcher.group(2).substring(0,
						matcher.group(2).length() - 1) + '}');
			}
			else{
				//out.println("Post : " + line);
				out.println(line);
			}
		}
	}
	
	/**
	 * print the specified lines of code
	 * 
	 * @param out
	 *            the output PrintStream
	 * @param endLineNum
	 *            till which number the obtained line would be printed
	 */
	protected void printStatements(PrintWriter out, String indent) {
		out.println();
		for (String s : this.unit.getInnerTestAnnotation().getStatements())
			out.println(indent + s);
	}
}

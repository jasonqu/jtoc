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
import org.jtoc.convertor.cpunit.MethodInfo;
import org.jtoc.convertor.utils.RegPatternFactory;

/**
 * class used to convert methods.
 */
public class MethodConvertor extends UnitConvertor<MethodInfo> {

	private static Log logger = LogFactory.getLog(MethodConvertor.class);

	/**
	 * set the JtocNode this Convertor represents
	 * 
	 * @param mi
	 *            the input MethodInfo
	 */
    public MethodConvertor(MethodInfo mi) {
    	super(mi);
    }

	/**
	 * the method to process the Jtoc annotated MethodInfo java codes into test codes.
	 * 
	 * @param in
	 *            the input file Scanner
	 * @param out
	 *            the output PrintStream
	 */
	public void process(Scanner in, PrintWriter out) {
		this.removeJtocAnnos(in, out);
		this.processBody(in, out);
	}
	
	/**
	 * the method to process the MethodBody java codes into test codes.
	 * 
	 * @param in
	 *            the input file Scanner
	 * @param out
	 *            the output PrintStream
	 */
	private void processBody(Scanner in, PrintWriter out) {
		this.printLinesTo(in, out, this.unit.getBodyBeginLine()-1);
		
		// so far, the One Line Body method is not going to be processed
		// on account of readability
		if (this.unit.hasOneLineBody())
			return;
		this.addPreCheck(in, out);
		this.addPostCheck(in, out);
	}

	/**
	 * the method to add the postCheck test method into the file. It calls
	 * different methods in respect to different return types.
	 * 
	 * @param in
	 *            the input file Scanner
	 * @param out
	 *            the output PrintStream
	 */
	private void addPostCheck(Scanner in, PrintWriter out){
		if(this.unit.getPostAnno() == null)
			return;
		
		logger.debug(this.unit.getName() +" with return type : "+this.unit.getReturnType());
		if(this.unit.getReturnType().equals("void"))
			this.addPostCheckVoid(in, out);
		else
			this.addPostCheckWithReturnType(in, out);
	}
	
	/**
	 * this method to add the postCheck test method for the method has return
	 * types other than void.
	 * 
	 * @param in
	 *            the input file Scanner
	 * @param out
	 *            the output PrintStream
	 */
	private void addPostCheckWithReturnType(Scanner in, PrintWriter out){
		while (lineNum < this.unit.getEndLine()){
			line = this.getNextLine(in);
			
			int index = line.indexOf("return");
			if(index != -1){
				logger.debug("deal with 'return' statement at " + lineNum);
				
				Matcher matcher = RegPatternFactory.instance()
						.getPattern("returnValue").matcher(line);
				if (matcher.find()){
					int localIndent = this.getIndent(line);
					
					/**
					 * this requires in the last line of the return expression,
					 * ';' must be the last non-white char in this line. It also
					 * requires the test method returns the return value that
					 * has been inputed.
					 * 
					 */
					// remove the return value expression since we don't need
					// generate parameters
					if(!this.unit.getPostAnno().needGenerateParas()){
						out.println(line.substring(0, matcher.start()+7)+' '
								+this.unit.getPostAnno().getStatementDecl(this.unit));
						
						while (true) {
							if (localIndent == 0
									&& RegPatternFactory.instance().getPattern(
											"end;").matcher(line).find()) {
								break;
							}
							line = this.getNextLine(in);
							localIndent += this.getIndent(line);
						}
					} else { // we need to include the return value
						out.print(line.substring(0, matcher.start() + 7)
								+ ' ' + this.unit.getPostAnno().getStatementDecl(this.unit)+'(');
						
						if(matcher.end() != line.length())
							line = line.substring(matcher.end()-1, line.length());
						
						logger.debug("line after sub : "+line);
						while (true) {
							if (localIndent == 0) {
								matcher = RegPatternFactory.instance()
										.getPattern("end;").matcher(line);
								if (matcher.find()) {
									logger.debug("Paras : "+this.unit.getPostAnno()
													.getStatementParas(this.unit));
									out.println(line.substring(0, matcher
											.start())
											+ ')'+this.unit.getPostAnno()
													.getStatementParas(this.unit));
									break;
								}
							}

							out.println(line);
							line = this.getNextLine(in);
							localIndent += this.getIndent(line);
						}
					}
				}
				else{
					//out.println("Post : " + line);
					out.println(line);
				}
			}
			else {
				// out.println("Post : " + line);
				out.println(line);
			}
		}
	}
	
	/**
	 * used in method "addPostCheckWithReturnType" to determine whether the
	 * return value's expression's braces were finished, which requires that in
	 * the 1st line of the expression there is no braces before the "return"
	 * keyword, and in the last line of the expression the ending brace '}' must
	 * be the last non-white char in this line before the ending semicolon ';'
	 */
	private int getIndent(String content){
		int ldent = 0;
		for(int i = 0; i < content.length(); i++){
			if('{' == content.charAt(i))
				ldent ++;
			else if('}' == content.charAt(i))
				ldent --;
		}
		return ldent;
	}
	
	/**
	 * this method to add the postCheck test method for the method has void
	 * return type.
	 * 
	 * @param in
	 *            the input file Scanner
	 * @param out
	 *            the output PrintStream
	 */
	private void addPostCheckVoid(Scanner in, PrintWriter out){
		while (lineNum < this.unit.getEndLine()-1){
			line = this.getNextLine(in);
			
			int index = line.indexOf("return");
			if (index != -1) {
				logger.debug("deal with 'return' statement : ");

				Matcher matcher = RegPatternFactory.instance().getPattern(
						"return;").matcher(line);
				if (matcher.matches()) {
					out.println(matcher.group(1) + '{'
							+ this.unit.getPostAnno().getStatement(this.unit)
							+ " return;}");
				} else {
					// which requires "return;" must be the last non-white
					// charSequence in this line
					matcher = RegPatternFactory.instance().getPattern(
							"retWithPreCode").matcher(line);
					if (matcher.find()) {
						out.println(matcher.group(1));
						out.println(matcher.group(2) + '{'
								+ this.unit.getPostAnno().getStatement(
										this.unit) + " return;}");
					} else {
						out.println(line);
					}
				}
			} else {
				//out.println("Post : " + line);
				out.println(line);
			}
		}
		
		this.processLastLineVoid(in, out);
	}

	/**
	 * called by mtthod "addPostCheckVoid", used to process the last line of the
	 * void method
	 * 
	 * @param in
	 *            the input file Scanner
	 * @param out
	 *            the output PrintStream
	 */
	private void processLastLineVoid(Scanner in, PrintWriter out) {
		line = this.getNextLine(in);
		Matcher matcher = RegPatternFactory.instance().getPattern("}").matcher(
				line);
		if (matcher.matches()) {
			out.println(matcher.group(1) + '\t'
					+ this.unit.getPostAnno().getStatement(this.unit));
			out.println(line);
		} else {
			// which requires "}" must be the last non-white char in this line
			matcher = RegPatternFactory.instance().getPattern("Code}").matcher(
					line);
			if (matcher.matches()) {
				out.println(matcher.group(1));
				out.println(matcher.group(2)
						+ this.unit.getPostAnno().getStatement(this.unit));
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
	 * the method is used to remove the JtocAnnotations and add the preCheck
	 * test method into the file.
	 * 
	 * @param in
	 *            the input file Scanner
	 * @param out
	 *            the output PrintStream
	 */
	private void addPreCheck(Scanner in, PrintWriter out){
		if(this.unit.getPreAnno() == null)
			return;
		
		line = this.getNextLine(in);
		
		Matcher matcher = RegPatternFactory.instance().getPattern("{").matcher(
				line);
		if (matcher.matches()) {
			out.println(line);
			out.println(matcher.group(1) + '\t'
					+ this.unit.getPreAnno().getStatement(this.unit));
		}else{
			matcher = RegPatternFactory.instance().getPattern("Code{").matcher(
					line);
			if(matcher.find()){
				out.println(matcher.group(0));
				out.print(matcher.group(1) + '\t'
						+ this.unit.getPreAnno().getStatement(this.unit));
				
				if(matcher.end() != line.length())
					out.print(line.substring(matcher.end(), line.length()));
				out.println();
			} else {
				// should not be here
				out.println("should not be here" + lineNum + line);
			}
		}
	}

	/**
	 * called by method "addPreCheck", in order to remove the JtocAnnotations
	 * 
	 * @param in
	 *            the input file Scanner
	 * @param out
	 *            the output PrintStream
	 */
	private void removeJtocAnnos(Scanner in, PrintWriter out){
		this.printLinesTo(in, out, this.unit.getPreviousNode().getBeginLine()-1);
		logger.debug("before remove the 1st anno, lineNum : " + lineNum);
		this.removeLinesTo(in, this.unit.getPreviousNode().getEndLine());

		if (this.unit.getFollowingNode() == null)
			return;

		this.printLinesTo(in, out, this.unit.getFollowingNode().getBeginLine()-1);
		logger.debug("before remove the 2nd anno, lineNum : "+ lineNum);
		this.removeLinesTo(in, this.unit.getFollowingNode().getEndLine());
	}
}

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

import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jtoc.convertor.cpunit.ClassInfo;

/**
 * class used to convert a file.
 * 
 * @author Goddamned Qu
 */
public class SingleFileConvertor extends UnitConvertor<ClassInfo>  {

	private static Log logger = LogFactory.getLog(SingleFileConvertor.class);

	public SingleFileConvertor() {
		super(null);
	}
	
	/**
	 * convert the jtoc annotated input file into the test code in the output
	 * file
	 * 
	 * @param inputFileName
	 *            the full name of the input file
	 * @param outputFileName
	 *            the full name of the output file
	 * @throws Exception 
	 */
	public void convert(String inputFileName, String outputFileName) throws Exception {
		this.convert(new File(inputFileName), new File(outputFileName));
	}

	/**
	 * convert the jtoc annotated input file into the test code in the output
	 * file
	 * 
	 * @param inputFile
	 *            the input file
	 * @param outputFile
	 *            the output file
	 * @throws Exception 
	 */
	public void convert(File inputFile, File outputFile) throws Exception {
		logger.info("convert form file " + inputFile.getName() + " to "
				+ outputFile.getAbsolutePath());
		
		this.init();

		try {
			Scanner in = new Scanner(new FileReader(inputFile));
			//PrintWriter out = new PrintWriter(System.out, true);
			PrintWriter out = new PrintWriter(new FileOutputStream(outputFile), true);
			
			CompilationUnit cu = null;
			cu = japa.parser.JavaParser
					.parse(new FileInputStream(inputFile));
			
			ClassDeclarationVisitor visitor = new ClassDeclarationVisitor(
					inputFile);
			visitor.visit(cu, null);
			if(visitor.hasException())
				throw new Exception("the convertor has parsing exceptions");
			
			iter = visitor.getClassInfos().iterator();
			this.setEnvParas();
			
			while (in.hasNextLine()) {
				if(lineNum == nextLine){
					logger.debug("process class " + ((ClassInfo)node).getName());
					ConvertorFactory.getClassConvertor((ClassInfo)node).process(in, out);
					this.setEnvParas();
				} else {
					line = getNextLine(in);
					//out.println("FI "+lineNum + line);
					out.println(line);
				}
			}
			
			in.close();
			out.close();
		} catch (Exception e) {
			logger.error("Exception occurs, converting stops : ", e);
			throw e;
		}finally{
//			out.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
		File root = new File(".");
		System.out.println(root.getCanonicalPath());
		File origin = new File(root.getCanonicalPath()+"/files/original/src/jtoc/test/Person.java");
		File generated = new File(root.getCanonicalPath()+"/files/generated/src/jtoc/test/Person.java");

		SingleFileConvertor sc = new SingleFileConvertor();
		sc.convert(origin, generated);
//		sc.convert(
//				"D:/Project/Jtoc/Project_SVN/JtocInput/src/junit/samples/money/Money.java",
//				"D:/Project/Jtoc/Project_SVN/JtocOutput/src/junit/samples/money/Money.java");

//		sc.convert(
//				"D:/Project/Jtoc/Project/JtocInput/src/jtoc/test/Person.java",
//				"D:/Project/Jtoc/Project/JtocOutput/src/jtoc/test/Person.java");

//		sc.convert(
//				"./test/org/jtoc/convertor/cpunit/InnerTestAnnoTest.java",
//				"D:/Project/Jtoc/Project/JtocOutput/src/jtoc/test/Person.java");

//		File dir = new File("D:/Project/Jtoc/Project/Jtoc/test/org/jtoc/convertor/classConvertor/");
//		for(File file : dir.listFiles())
//		{
//			sc.init();
//			sc.convert(file, file);
//		}
	}


}

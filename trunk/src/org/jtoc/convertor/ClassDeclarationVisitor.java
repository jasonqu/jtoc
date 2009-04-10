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
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.jtoc.convertor.cpunit.ClassInfo;
import org.jtoc.convertor.cpunit.JtocException;
import org.jtoc.convertor.cpunit.JtocNode;

public class ClassDeclarationVisitor extends VoidVisitorAdapter<Object> {

	//private static Logger logger = Logger.getLogger(ClassDeclarationVisitor.class.getName());

	private ArrayList<JtocNode> classInfos = new ArrayList<JtocNode>();

	/**
	 * constructor with File
	 * 
	 * @param file
	 *            the file that is going to be parsed
	 */
	public ClassDeclarationVisitor(File file) {
		this(file.getName());
	}
	
	/**
	 * constructor with the name of the file
	 * 
	 * @param filename
	 *            the name of the file pa. NOT FULL NAME
	 */
	public ClassDeclarationVisitor(String filename) {
		JtocNode.setFilename(filename);
	}

	/**
	 * get the classes that the code file contained
	 * 
	 * @return the ArrayList of the classes that the code file contained
	 */
	public ArrayList<JtocNode> getClassInfos() {
		return classInfos;
	}
	
	private boolean exceptionFlag = false;

	/**
	 * get whether the parse procedure throws some Exception
	 * 
	 * @return true if Exception throw in the parse procedure
	 */
	public boolean hasException() {
		return exceptionFlag;
	}

	/**
	 * traverse each ClassOrInterfaceDeclaration, parse them and added them in
	 * the ArrayList to info that the classes this code file contained.
	 */
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		try {
			ClassInfo ci = new ClassInfo(n);
			this.classInfos.add(ci);
			ci.parse();
		} catch (JtocException e) {
			System.err.println(e.getMessage());
			this.exceptionFlag = true;
		}
	}

	/**
	 * test method
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// creates an input stream for the file to be parsed
		FileInputStream in = new FileInputStream(
				"D:/Project/Jtoc/Project/JtocInput/src/jtoc/test/Person.java");

		CompilationUnit cu;
		try {
			// parse the file
			cu = japa.parser.JavaParser.parse(in);
		} finally {
			in.close();
		}

		// visit and print the methods names
		ClassDeclarationVisitor visitor = new ClassDeclarationVisitor("Person.java");
		visitor.visit(cu, null);
	}

}

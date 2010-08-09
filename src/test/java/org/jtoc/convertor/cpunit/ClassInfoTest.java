package org.jtoc.convertor.cpunit;

import static org.junit.Assert.*;
import japa.parser.ast.CompilationUnit;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ClassInfoTest {

	/** ArrayList that contains the ClassInfos */
	static ArrayList<ClassInfo> classInfos = new ArrayList<ClassInfo>();
	/** Arraylist for the ClassInfo parse exception messages */
	static ArrayList<String> classErrMessages = new ArrayList<String>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// creates an input stream for the file to be parsed
		FileInputStream in = new FileInputStream(
				"src/test/files/test/ClassInfoValidationTest.java");

		CompilationUnit cu;
		try { // parse the file
			cu = japa.parser.JavaParser.parse(in);
		} finally {
			in.close();
		}

		// visit and print the methods names
		JtocNode.setFilename("ClassInfoValidationTest.java");
		ClassDeclarationVisitorForTest visitor = new ClassDeclarationVisitorForTest();
		visitor.visit(cu, null);
		
		classInfos = visitor.classInfos;
		classErrMessages = visitor.classErrMessages;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * not quite sure how to test this
	 */
	@Test
	public void testParse() {
		ArrayList<Integer> rightones = new ArrayList<Integer>();
		for (int i : new int[] { 0, 1, 5, 6, 7, 12, 13 })
			rightones.add(i);
		assertEquals(classInfos.size(), rightones.size()
				+ classErrMessages.size());
	}

	/**
	 * test method
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// creates an input stream for the file to be parsed
		FileInputStream in = new FileInputStream(
		"./files/test/ClassInfoValidationTest.java");

		CompilationUnit cu;
		try { // parse the file
			cu = japa.parser.JavaParser.parse(in);
		} finally {
			in.close();
		}

		// visit and print the methods names
		JtocNode.setFilename("ClassInfoValidationTest.java");
		ClassDeclarationVisitorForTest visitor = new ClassDeclarationVisitorForTest();
		visitor.visit(cu, null);
		
		System.out.println("The index array for testIsInstance :");
		for(Integer i : visitor.classIndexSet)
			System.out.print(i+" ");
		System.out.println();

		System.out.println("The ClassInfos :");
		for(ClassInfo ci : visitor.classInfos)
			System.out.println(ci);
		System.out.println();

		System.out.println("The ClassInfo parse error messages :");
		for (String s : visitor.classErrMessages)
			System.out.println(s);
		System.out.println();
	}
}

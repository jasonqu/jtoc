/**
 * 
 */
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

public class MethodInfoTest {
	
	/** Arraylist for the MethodInfo */
	static ArrayList<MethodInfo> methodlist = new ArrayList<MethodInfo>();

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// creates an input stream for the file to be parsed
		FileInputStream in = new FileInputStream(
				"src/test/files/test/PrePostAnnoTest.java");

		CompilationUnit cu;
		try { // parse the file
			cu = japa.parser.JavaParser.parse(in);
		} finally {
			in.close();
		}

		// visit and print the methods names
		JtocNode.setFilename("PrePostAnnoTest.java");
		MethodVisitor visitor = new MethodVisitor();
		visitor.visit(cu, null);
		
		methodlist = visitor.methodlist;
		assertEquals(11, methodlist.size());
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
	 * Test method for {@link org.jtoc.convertor.cpunit.MethodInfo#getName()}.
	 */
	@Test
	public void testGetName() {
		String[] names = new String[] { "method0", "method1", "method2",
				"method3", "method4", "method5", "method6", "method7",
				"method8", "method9", "method10" };
		for (int i = 0; i < methodlist.size(); i++)
			assertEquals(names[i], methodlist.get(i).getName());
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.MethodInfo#getReturnType()}.
	 */
	@Test
	public void testGetReturnType() {
		String[] returntypes = new String[] { "void", "void", "int", "int", "Object",
				"Object", "Object", "double", "double", "Integer", "Integer",
				"Integer" };
		for (int i = 0; i < methodlist.size(); i++)
			assertEquals(returntypes[i], methodlist.get(i).getReturnType());
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.MethodInfo#hasOneLineBody()}.
	 */
	@Test
	public void testHasOneLineBody() {
		assertTrue(methodlist.get(0).hasOneLineBody());
		for (int i = 1; i < methodlist.size(); i++)
			assertFalse(methodlist.get(i).hasOneLineBody());
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.MethodInfo#getParaList()}.
	 */
	@Test
	public void testGetParaList() {
		String[] paras = new String[] { "", "", "", "para1", "para1", "para1",
				"para1, paraK, paraN", "para1, paraK, paraN",
				"para1, paraK, paraN", "para1, paraK, paraN",
				"para1, paraK, paraN", "para1, paraK, paraN" };
		for (int i = 0; i < methodlist.size(); i++)
			assertEquals(paras[i], methodlist.get(i).getParaList());
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.MethodInfo#getPreAnno()}.
	 */
	@Test
	public void testGetPreAnno() {
		assertNull(methodlist.get(0).getPreAnno());
		assertNotNull(methodlist.get(1).getPreAnno());
		assertNotNull(methodlist.get(4).getPreAnno());
		assertNull(methodlist.get(5).getPreAnno());
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.MethodInfo#getPostAnno()}.
	 */
	@Test
	public void testGetPostAnno() {
		assertNull(methodlist.get(0).getPostAnno());
		assertNotNull(methodlist.get(1).getPostAnno());
		assertNull(methodlist.get(4).getPostAnno());
		assertNotNull(methodlist.get(5).getPostAnno());
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.MethodInfo#getPreviousNode()}.
	 */
	@Test
	public void testGetPreviousNode() {
		assertNull(methodlist.get(0).getPreviousNode());
		assertEquals(PreAnnotation.class, methodlist.get(1).getPreviousNode()
				.getClass());
		assertEquals(PostAnnotation.class, methodlist.get(2).getPreviousNode()
				.getClass());
		assertEquals(PostAnnotation.class, methodlist.get(3).getPreviousNode()
				.getClass());
		assertEquals(PreAnnotation.class, methodlist.get(4).getPreviousNode()
				.getClass());
		assertEquals(PostAnnotation.class, methodlist.get(5).getPreviousNode()
				.getClass());
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.MethodInfo#getFollowingNode()}.
	 */
	@Test
	public void testGetFollowingNode() {
		assertNull(methodlist.get(0).getFollowingNode());
		assertEquals(PostAnnotation.class, methodlist.get(1).getFollowingNode()
				.getClass());
		assertEquals(PreAnnotation.class, methodlist.get(2).getFollowingNode()
				.getClass());
		assertEquals(PreAnnotation.class, methodlist.get(3).getFollowingNode()
				.getClass());
		assertNull(methodlist.get(4).getFollowingNode());
		assertNull(methodlist.get(5).getFollowingNode());
	}

	/**
	 * test method
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// creates an input stream for the file to be parsed
		FileInputStream in = new FileInputStream(
				"./files/test/PrePostAnnoTest.java");

		CompilationUnit cu;
		try { // parse the file
			cu = japa.parser.JavaParser.parse(in);
		} finally {
			in.close();
		}

		// visit and print the methods names
		JtocNode.setFilename("PrePostAnnoTest.java");
		MethodVisitor visitor = new MethodVisitor();
		visitor.visit(cu, null);
		
		System.out.println("The method's parameters :");
		for (MethodInfo mi : visitor.methodlist)
			System.out.println(mi.getName() + " : " + mi.getParaList());
		System.out.println();
	}
}

/**
 * 
 */
package org.jtoc.convertor.cpunit;

import static org.junit.Assert.*;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.expr.AnnotationExpr;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.junit.*;

/**
 * @author Goddamned Qu
 *
 */
public class PostAnnotationTest {

	/**	Arraylist for the AnnotationExpr */
	static ArrayList<AnnotationExpr> annolist = new ArrayList<AnnotationExpr>();

	/** ArrayList for the PreAnnotation indexes */
	static ArrayList<Integer> postindexes = new ArrayList<Integer>();
	
	/** the lineNumber where wrong format Annotation begins */
	int wrongFormatLine = 18;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
		
		annolist = visitor.annolist;

		int[] indexes = new int[] { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19 };
		for (int i : indexes)
			postindexes.add(i);
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
	 * Test method for {@link org.jtoc.convertor.cpunit.PostAnnotation#PostAnnotation(japa.parser.ast.expr.AnnotationExpr)}.
	 */
	@Test
	public void testPostAnnotationAnnotationExpr() {
		PostAnnotation pa = new PostAnnotation(null);
		assertEquals("@Post", pa.getHead());
		assertNull(pa.unit);
		assertEquals("innerTest", pa.getTestObject());
		assertEquals("", pa.getTestMethod()); 
		assertEquals("", pa.getParameters()); 
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.PostAnnotation#isInstance(java.lang.String)}.
	 */
	@Test
	public void testIsInstance() {
		for (int i = 0; i < annolist.size(); i++)
			if (PostAnnotation.isInstance(annolist.get(i)))
				assertTrue("the bug index is " + i, postindexes.contains(i));
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.PostAnnotation#getPostAnnotationFromAnnoExpr(japa.parser.ast.expr.AnnotationExpr)}.
	 * @throws JtocException 
	 */
	@Test
	public void testGetPostAnnotationFromAnnoExpr() throws JtocException {
		for (int i = 0; i < annolist.size(); i++) {
			if (i > wrongFormatLine)
				continue;
			if (postindexes.contains(i))
				assertNotNull("the bug index is " + i, PostAnnotation
						.getPostAnnotationFromAnnoExpr(annolist.get(i)));
			else
				assertNull("the bug index is " + i, PostAnnotation
						.getPostAnnotationFromAnnoExpr(annolist.get(i)));
		}
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.PostAnnotation#needGenerateParas()}.
	 */
	@Test
	public void testNeedGenerateParas() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.PostAnnotation#getStatementDecl(org.jtoc.convertor.cpunit.MethodInfo)}.
	 */
	@Test
	public void testGetStatementDecl() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.PostAnnotation#getStatementParas(org.jtoc.convertor.cpunit.MethodInfo)}.
	 */
	@Test
	public void testGetStatementParas() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.PostAnnotation#getStatement(org.jtoc.convertor.cpunit.MethodInfo)}.
	 */
	@Test
	public void testGetStatement() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocAnnotation#init()}.
	 * @throws JtocException 
	 */
	@Test
	public void testInit() throws JtocException {
		for (int i = 0; i < annolist.size(); i++) {
			if (i > wrongFormatLine)
				continue;
			if (PostAnnotation.isInstance(annolist.get(i))){
				PostAnnotation pa = PostAnnotation
						.getPostAnnotationFromAnnoExpr(annolist.get(i));
				pa.init();
				assertEquals("the bug index is " + i, "innerTest", pa.getTestObject());
				assertEquals("the bug index is " + i, "", pa.getTestMethod()); 
				assertEquals("the bug index is " + i, "", pa.getParameters()); 
			}					
		}
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocAnnotation#getHead()}.
	 * @throws JtocException 
	 */
	@Test
	public void testGetHead() throws JtocException {
		for (int i = 0; i < annolist.size(); i++) {
			if (i > wrongFormatLine)
				continue;
			if (PostAnnotation.isInstance(annolist.get(i)))
				assertEquals("the bug index is " + i, "@Post", PostAnnotation
						.getPostAnnotationFromAnnoExpr(annolist.get(i))
						.getHead());
		}
	}
	
	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocAnnotation#parse()}.
	 */
	@Test
	public void testParse() {
		for (int i = 0; i < annolist.size(); i++) {
			if (PostAnnotation.isInstance(annolist.get(i))){
				try {
					PostAnnotation.getPostAnnotationFromAnnoExpr(annolist.get(i));
					if (i > wrongFormatLine)
						fail("This annotation should throw a JtocFormatException"
								+ annolist.get(i));
				} catch (JtocFormatException e) {
					assertTrue("", i > wrongFormatLine);
					assertTrue("", e.getMessage().startsWith(
							"(PrePostAnnoTest.java:"));
				}
			}					
		}
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocNode#getStringArrayFromValue(japa.parser.ast.expr.MemberValuePair)}.
	 */
	@Test
	public void testGetStringArrayFromValue() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocNode#getStringFromValue(japa.parser.ast.expr.MemberValuePair)}.
	 */
	@Test
	public void testGetStringFromValue() {
		fail("Not yet implemented");
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
		try {
			// parse the file
			cu = japa.parser.JavaParser.parse(in);
		} finally {
			in.close();
		}

		// visit and print the methods names
		JtocNode.setFilename("PrePostAnnoTest.java");
		MethodVisitor visitor = new MethodVisitor();
		visitor.visit(cu, null);
		
		System.out.println("The index array for testIsInstance :");
		for(Integer i : visitor.postAnnoSet)
			System.out.print(i+" ");
		System.out.println();
	}
}

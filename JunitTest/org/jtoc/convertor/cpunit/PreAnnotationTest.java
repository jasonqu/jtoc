/**
 * 
 */
package org.jtoc.convertor.cpunit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.expr.AnnotationExpr;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * @author Goddamned Qu
 *
 */
public class PreAnnotationTest {

	/**	Arraylist for the AnnotationExpr */
	static ArrayList<AnnotationExpr> annolist = new ArrayList<AnnotationExpr>();
	
	// XXX might be useless
	/**	Arraylist for the PreAnnotation */
	static ArrayList<PreAnnotation> prelist = new ArrayList<PreAnnotation>();
	/**	Arraylist for the PreAnnotation parse exception messages*/
	static ArrayList<String> prelistmessages = new ArrayList<String>();
	
	/** ArrayList for the PreAnnotation indexes */
	static ArrayList<Integer> preindexes = new ArrayList<Integer>();
	
	/** the lineNumber where wrong format Annotation begins */
	int wrongFormatLine = 17;
	
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
		prelist = visitor.prelist;
		prelistmessages = visitor.prelistmessages;
		
		int[] indexes = new int[] { 0, 2, 4, 6, 8, 10, 12, 14, 16, 18 };
		for (int i : indexes)
			preindexes.add(i);
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
	 * Test method for {@link org.jtoc.convertor.cpunit.PreAnnotation#PreAnnotation(japa.parser.ast.expr.AnnotationExpr)}.
	 */
	@Test
	public void testPreAnnotationAnnotationExpr() {
		PreAnnotation pa = new PreAnnotation(null);
		assertEquals("@Pre", pa.getHead());
		assertNull(pa.unit);
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.PreAnnotation#isInstance(java.lang.String)}.
	 */
	@Test
	public void testIsInstance() {
		for (int i = 0; i < annolist.size(); i++)
			if (PreAnnotation.isInstance(annolist.get(i)))
				assertTrue("the bug index is " + i, preindexes.contains(i));
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.PreAnnotation#getPreAnnotationFromAnnoExpr(japa.parser.ast.expr.AnnotationExpr)}.
	 * @throws JtocException 
	 */
	@Test
	public void testGetPreAnnotationFromAnnoExpr() throws JtocException {
		for (int i = 0; i < annolist.size(); i++) {
			if (i > wrongFormatLine)
				continue;
			if (preindexes.contains(i))
				assertNotNull("the bug index is " + i, PreAnnotation
						.getPreAnnotationFromAnnoExpr(annolist.get(i)));
			else
				assertNull("the bug index is " + i, PreAnnotation
						.getPreAnnotationFromAnnoExpr(annolist.get(i)));
		}
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.PreAnnotation#getStatement(org.jtoc.convertor.cpunit.MethodInfo)}.
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
			if (PreAnnotation.isInstance(annolist.get(i))){
				PreAnnotation pa = PreAnnotation
						.getPreAnnotationFromAnnoExpr(annolist.get(i));
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
			if (PreAnnotation.isInstance(annolist.get(i)))
				assertEquals("the bug index is " + i, "@Pre", PreAnnotation
						.getPreAnnotationFromAnnoExpr(annolist.get(i))
						.getHead());
		}
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocAnnotation#parse()}.
	 * For the JtocFormatException occurrence
	 */
	@Test
	public void testParse() {
		for (int i = 0; i < annolist.size(); i++) {
			if (PreAnnotation.isInstance(annolist.get(i))) {
				try {
					PreAnnotation.getPreAnnotationFromAnnoExpr(annolist.get(i));
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
	public static void main(String[] args) throws Exception {
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
		
		System.out.println("The index array for testIsInstance :");
		for(Integer i : visitor.preAnnoSet)
			System.out.print(i+" ");
		System.out.println();
	}

}

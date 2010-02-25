/**
 * 
 */
package org.jtoc.convertor.cpunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.expr.AnnotationExpr;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.junit.*;

/**
 * @author QGD
 *
 */
public class InnerTestAnnotationTest {
	
	/** Arraylist for the AnnotationExpr */
	static ArrayList<AnnotationExpr> annolist = new ArrayList<AnnotationExpr>();

	/** ArrayList for the PreAnnotation indexes */
	static ArrayList<Integer> innerindexes = new ArrayList<Integer>();

	/** the lineNumber where wrong format Annotation begins */
	int wrongFormatLine = 5;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// creates an input stream for the file to be parsed
		FileInputStream in = new FileInputStream(
		"./files/test/InnerTestAnnoTest.java");

		CompilationUnit cu;
		try { // parse the file
			cu = japa.parser.JavaParser.parse(in);
		} finally {
			in.close();
		}

		// visit and print the methods names
		JtocNode.setFilename("InnerTestAnnoTest.java");
		ClassDeclarationVisitor visitor = new ClassDeclarationVisitor();
		visitor.visit(cu, null);
		
		annolist = visitor.annolist;
		
		int[] indexes = new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };
		for (int i : indexes)
			innerindexes.add(i);
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
	 * Test method for {@link org.jtoc.convertor.cpunit.InnerTestAnnotation#InnerTestAnnotation(japa.parser.ast.expr.AnnotationExpr)}.
	 */
	@Test
	public void testInnerTestAnnotationAnnotationExpr() {
		InnerTestAnnotation ita = new InnerTestAnnotation(null);
		assertNull(ita.unit);
		assertEquals(1, ita.getClassNames().length);
		assertEquals("InnerTest", ita.getClassNames()[0]);
		assertEquals(1, ita.getObjectNames().length);
		assertEquals("innerTest", ita.getObjectNames()[0]);
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.InnerTestAnnotation#init()}.
	 * @throws JtocException 
	 */
	@Test
	public void testInit() throws JtocException {
		for (int i = 0; i < annolist.size(); i++) {
			if (i > wrongFormatLine)
				continue;
			if (InnerTestAnnotation.isInstance(annolist.get(i))){
				InnerTestAnnotation ita = InnerTestAnnotation
						.getInnerTestAnnotationFromAnnoExpr(annolist.get(i));
				ita.init();
				assertEquals("the bug index is " + i, 1, ita.getClassNames().length);
				assertEquals("the bug index is " + i, "InnerTest", ita.getClassNames()[0]);
				assertEquals("the bug index is " + i, 1, ita.getObjectNames().length);
				assertEquals("the bug index is " + i, "innerTest", ita.getObjectNames()[0]);
			}					
		}
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.InnerTestAnnotation#isInstance(java.lang.String)}.
	 */
	@Test
	public void testIsInstance() {
		for (int i = 0; i < annolist.size(); i++)
			if (InnerTestAnnotation.isInstance(annolist.get(i)))
				assertTrue("the bug index is " + i, innerindexes.contains(i));
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.InnerTestAnnotation#parse()}.
	 */
	@Test
	public void testParse() {
		for (int i = 0; i < annolist.size(); i++) {
			if (InnerTestAnnotation.isInstance(annolist.get(i))) {
				try {
					InnerTestAnnotation
							.getInnerTestAnnotationFromAnnoExpr(annolist.get(i));
					if (i > wrongFormatLine)
						fail("This annotation should throw a JtocFormatException"
								+ annolist.get(i));
				} catch (JtocFormatException e) {
					assertTrue("", i > wrongFormatLine);
					assertTrue("", e.getMessage().startsWith(
							"(InnerTestAnnoTest.java:"));
				}
			}
		}
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.InnerTestAnnotation#getInnerTestAnnotationFromAnnoExpr(japa.parser.ast.expr.AnnotationExpr)}.
	 * @throws JtocException 
	 */
	@Test
	public void testGetInnerTestAnnotationFromAnnoExpr() throws JtocException {
		for (int i = 0; i < annolist.size(); i++) {
			if (i > wrongFormatLine)
				continue;
			if (innerindexes.contains(i))
				assertNotNull("the bug index is " + i, InnerTestAnnotation
						.getInnerTestAnnotationFromAnnoExpr(annolist.get(i)));
			else
				assertNull("the bug index is " + i, InnerTestAnnotation
						.getInnerTestAnnotationFromAnnoExpr(annolist.get(i)));
		}
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.InnerTestAnnotation#getStatements()}.
	 */
	@Test
	public void testGetStatements() {
		fail("Not yet implemented");
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
		"./files/test/InnerTestAnnoTest.java");

		CompilationUnit cu;
		try { // parse the file
			cu = japa.parser.JavaParser.parse(in);
		} finally {
			in.close();
		}

		// visit and print the methods names
		JtocNode.setFilename("InnerTestAnnoTest.java");
		ClassDeclarationVisitor visitor = new ClassDeclarationVisitor();
		visitor.visit(cu, null);
		
		System.out.println("The index array for testIsInstance :");
		for(Integer i : visitor.innerAnnoSet)
			System.out.print(i+" ");
		System.out.println();
	}
}

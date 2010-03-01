/**
 * 
 */
package org.jtoc.convertor.cpunit;

import static org.junit.Assert.*;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NormalAnnotationExpr;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

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
	
	/** the index used in testGetStringFromValue */
	int fullparaindex = 7;
	
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
		ClassDeclarationVisitorForTest visitor = new ClassDeclarationVisitorForTest();
		visitor.visit(cu, null);
		
		annolist = visitor.annolist;
		
		int[] indexes = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
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
	 * @throws JtocFormatException 
	 */
	@Test
	public void testGetStatements() throws JtocFormatException {
		String[][] states = new String[][] {
				new String[] { "InnerTest innerTest = new InnerTest();" },
				new String[] { "InnerTest innerTest = new InnerTest();" },
				new String[] { "InnerTest1 innerTest = new InnerTest1();" },
				new String[] { "InnerTest test1 = new InnerTest();" },
				new String[] { "InnerTest1 test1 = new InnerTest1();" },
				new String[] { "InnerTest1 test1 = new InnerTest1();",
						"InnerTest2 test2 = new InnerTest2();" } };
		for (int i = 0, j = 0; i < annolist.size(); i++) {
			if (i > wrongFormatLine)
				return;
			if (innerindexes.contains(i)) {
				InnerTestAnnotation ita = InnerTestAnnotation
						.getInnerTestAnnotationFromAnnoExpr(annolist.get(i));
				assertArrayEquals(states[j], ita.getStatements());
				j++;
			}
		}
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocNode#getStringArrayFromValue(japa.parser.ast.expr.MemberValuePair)}.
	 * @throws JtocFormatException 
	 */
	@Test
	public void testGetStringArrayFromValue() throws JtocFormatException {
		List<MemberValuePair> mps = ((NormalAnnotationExpr) annolist
				.get(fullparaindex)).getPairs();
		assertEquals(3, mps.size());
		String[] t = JtocNode.getStringArrayFromValue(mps.get(0));
		assertEquals(2, t.length);
		assertEquals("InnerTest1", t[0]);
		assertEquals("InnerTest2", t[1]);

		t = JtocNode.getStringArrayFromValue(mps.get(1));
		assertEquals(1, t.length);
		assertEquals("test2", t[0]);

		try {
			JtocNode.getStringArrayFromValue(mps.get(2));
			fail("This line should not be reached.");
		} catch (JtocFormatException e) {
			assertTrue("", e.getMessage().startsWith("(InnerTestAnnoTest.java:"));
		}
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
		ClassDeclarationVisitorForTest visitor = new ClassDeclarationVisitorForTest();
		visitor.visit(cu, null);

		System.out.println("The index array for testIsInstance :");
		for(Integer i : visitor.innerAnnoSet)
			System.out.print(i+" ");
		System.out.println();

		System.out.println();
		System.out.println("The code generated :");
		for (InnerTestAnnotation i : visitor.innerAnnoList)
			for (String s : i.getStatements())
				System.out.println(s);
		System.out.println();
	}
}

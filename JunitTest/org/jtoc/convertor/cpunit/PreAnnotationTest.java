/**
 * 
 */
package org.jtoc.convertor.cpunit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NormalAnnotationExpr;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * @author Goddamned Qu
 *
 */
public class PreAnnotationTest {

	/**	Arraylist for the AnnotationExpr */
	static ArrayList<AnnotationExpr> annolist = new ArrayList<AnnotationExpr>();
	
	/**	Arraylist for the PreAnnotation */
	static ArrayList<PreAnnotation> prelist = new ArrayList<PreAnnotation>();

	/** ArrayList for the PreAnnotation indexes */
	static ArrayList<Integer> preindexes = new ArrayList<Integer>();
	
	/** the index where wrong format Annotation begins */
	int wrongFormatLine = 17;
	
	/** the index used in testGetStringFromValue */
	int fullparaindex = 16;
	int arrayindex = 18;
	
	/** Arraylist for the MethodInfo */
	static ArrayList<MethodInfo> methodlist = new ArrayList<MethodInfo>();
	
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
		assertEquals(9, prelist.size());
		methodlist = visitor.methodlist;
		
		int[] indexes = new int[] { 0, 3, 5, 6, 8, 10, 12, 14, 16, 18 };
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
		String[] states = new String[]{
				"innerTest.method1PreCheck();",
				"innerTest.method2PreCheck();",
				"test.method3PreCheck(para1);",
				"innerTest.commenPreCheck(para1);",
				"innerTest.method6PreCheck(para1, paraK);",
				"test.commenPreCheck(para1, paraK, paraN);",
				"test.method8PreCheck(para1, paraK);",
				"innerTest.commenPreCheck(para1, paraK);",
				"test.commenPreCheck(para1, paraK);"
		};
		
		int id = 0;
		for (int i = 0; i < 4; i++)
			assertEquals(states[id++], prelist.get(i).getStatement(
					methodlist.get(i + 1)));
		for (int i = 4; i < prelist.size(); i++)
			assertEquals(states[id++], prelist.get(i).getStatement(
					methodlist.get(i + 2)));
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
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocNode#getStringFromValue(japa.parser.ast.expr.MemberValuePair)}.
	 * @throws JtocFormatException 
	 */
	@Test
	public void testGetStringFromValue() throws JtocFormatException {
		List<MemberValuePair> mp = ((NormalAnnotationExpr) annolist
				.get(fullparaindex)).getPairs();
		assertEquals(3, mp.size());
		assertEquals("test", JtocNode.getStringFromValue(mp.get(0)));
		assertEquals("commenPreCheck", JtocNode.getStringFromValue(mp.get(1)));
		assertEquals("para1, paraK", JtocNode.getStringFromValue(mp.get(2)));

		try {
			JtocNode.getStringFromValue(((NormalAnnotationExpr) annolist
					.get(arrayindex)).getPairs().get(0));
			fail("This line should not be reached.");
		} catch (JtocFormatException e) {
			assertTrue("", e.getMessage().startsWith("(PrePostAnnoTest.java:"));
		}
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
		
		System.out.println("The statements testGetStatement :");
		for (int i = 0; i < 4; i++)
			System.out.println(visitor.prelist.get(i).getStatement(
					visitor.methodlist.get(i + 1)));
		for (int i = 4; i < visitor.prelist.size(); i++)
			System.out.println(visitor.prelist.get(i).getStatement(
					visitor.methodlist.get(i + 2)));
		System.out.println();
	}

}

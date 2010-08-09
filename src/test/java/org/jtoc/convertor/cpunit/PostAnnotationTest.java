package org.jtoc.convertor.cpunit;

import static org.junit.Assert.*;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.expr.AnnotationExpr;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.junit.*;

public class PostAnnotationTest {

	/**	Arraylist for the AnnotationExpr */
	static ArrayList<AnnotationExpr> annolist = new ArrayList<AnnotationExpr>();
	
	/** Arraylist for the PostAnnotation */
	static ArrayList<PostAnnotation> postlist = new ArrayList<PostAnnotation>();

	/** ArrayList for the PreAnnotation indexes */
	static ArrayList<Integer> postindexes = new ArrayList<Integer>();
	
	/** the lineNumber where wrong format Annotation begins */
	int wrongFormatLine = 18;
	
	/** Arraylist for the MethodInfo */
	static ArrayList<MethodInfo> methodlist = new ArrayList<MethodInfo>();
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// creates an input stream for the file to be parsed
		FileInputStream in = new FileInputStream(
				"src/test//files/test/PrePostAnnoTest.java");

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
		postlist = visitor.postlist;
		methodlist = visitor.methodlist;

		int[] indexes = new int[] { 1, 2, 4, 7, 9, 11, 13, 15, 17, 19 };
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
		for (int i = 0; i < postlist.size(); i++)
			assertEquals("error at " + i, postlist.get(i).getParameters().isEmpty(),
					postlist.get(i).needGenerateParas());
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.PostAnnotation#getStatementDecl(org.jtoc.convertor.cpunit.MethodInfo)}.
	 * @throws Exception 
	 */
	@Test
	public void testGetStatementDeclAndGetStatementParas() throws Exception {
		// must parse again because objects in Annotation array are changed
		setUpBeforeClass();
		
		String[] statedecls = new String[]{
				"innerTest.method1PostCheck(",
				"innerTest.method2PostCheck(",
				"test.method3PostCheck(",
				"innerTest.commenPostCheck(",
				"innerTest.method6PostCheck(para1, paraK);",
				"test.commenPostCheck(",
				"test.method8PostCheck(para1, paraK);",
				"innerTest.commenPostCheck(para1, paraK);",
				"test.commenPostCheck(para1, paraK);"
		};
		
		String[] stateparas = new String[] { ");", ");", ", para1);",
				", para1);", "", ", para1, paraK, paraN);", "", "", "", };
		
		int id = 0;
		for (int i = 0; i < 3; i++){
			assertEquals(statedecls[id], postlist.get(i).getStatementDecl(
					methodlist.get(i + 1)));
			assertEquals(stateparas[id], postlist.get(i).getStatementParas(
					methodlist.get(i + 1)));
			id++;
		}
		for (int i = 3; i < postlist.size(); i++){
			assertEquals(statedecls[id], postlist.get(i).getStatementDecl(
					methodlist.get(i + 2)));
			assertEquals(stateparas[id], postlist.get(i).getStatementParas(
					methodlist.get(i + 1)));
			id++;
		}
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.PostAnnotation#getStatement(org.jtoc.convertor.cpunit.MethodInfo)}.
	 * Pay attention that "the methodInfo's returnType should be void" isn't programmed as a constraint.
	 */
	@Test
	public void testGetStatement() {
		String[] states = new String[]{
				"innerTest.method1PostCheck();",
				"innerTest.method2PostCheck();",
				"test.method3PostCheck(para1);",
				"innerTest.commenPostCheck(para1);",
				"innerTest.method6PostCheck(para1, paraK);",
				"test.commenPostCheck(para1, paraK, paraN);",
				"test.method8PostCheck(para1, paraK);",
				"innerTest.commenPostCheck(para1, paraK);",
				"test.commenPostCheck(para1, paraK);"
		};
		
		int id = 0;
		for (int i = 0; i < 3; i++)
			assertEquals(states[id++], postlist.get(i).getStatement(
					methodlist.get(i + 1)));
		for (int i = 3; i < postlist.size(); i++)
			assertEquals(states[id++], postlist.get(i).getStatement(
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
		
		System.out.println("The needGenerateParas for testNeedGenerateParas :");
		for(PostAnnotation pa : visitor.postlist){
			System.out.println(pa);
			System.out.println(pa.needGenerateParas());
		}
		
		System.out.println("The statements testGetStatement :");
		for (int i = 0; i < 3; i++)
			System.out.println(visitor.postlist.get(i).getStatement(
					visitor.methodlist.get(i + 1)));
		for (int i = 3; i < visitor.postlist.size(); i++)
			System.out.println(visitor.postlist.get(i).getStatement(
					visitor.methodlist.get(i + 2)));
		System.out.println();

		// must parse again because objects in Annotation array are changed
		setUpBeforeClass();
		System.out.println("The statements getStatementDecl :");
		for (int i = 0; i < 3; i++)
			System.out.println(postlist.get(i).getStatementDecl(
					methodlist.get(i + 1)));
		for (int i = 3; i < postlist.size(); i++)
			System.out.println(postlist.get(i).getStatementDecl(
					methodlist.get(i + 2)));
		System.out.println();
		
		System.out.println("The statements getStatementParas :");
		for (int i = 0; i < 3; i++)
			System.out.println(postlist.get(i).getStatementParas(
					methodlist.get(i + 1)));
		for (int i = 3; i < postlist.size(); i++)
			System.out.println(postlist.get(i).getStatementParas(
					methodlist.get(i + 2)));
		System.out.println();
	}
}

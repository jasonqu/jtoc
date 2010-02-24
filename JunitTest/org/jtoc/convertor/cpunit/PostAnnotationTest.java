/**
 * 
 */
package org.jtoc.convertor.cpunit;

import static org.junit.Assert.fail;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.*;

/**
 * @author Goddamned Qu
 *
 */
public class PostAnnotationTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	 * Test method for {@link org.jtoc.convertor.cpunit.PostAnnotation#isInstanceLocal(java.lang.String)}.
	 */
	@Test
	public void testIsInstanceLocal() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.PostAnnotation#PostAnnotation()}.
	 */
	@Test
	public void testPostAnnotation() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.PostAnnotation#PostAnnotation(japa.parser.ast.expr.AnnotationExpr)}.
	 */
	@Test
	public void testPostAnnotationAnnotationExpr() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.PostAnnotation#isInstance(java.lang.String)}.
	 */
	@Test
	public void testIsInstance() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.PostAnnotation#getPostAnnotationFromAnnoExpr(japa.parser.ast.expr.AnnotationExpr)}.
	 */
	@Test
	public void testGetPostAnnotationFromAnnoExpr() {
		fail("Not yet implemented");
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
	 * Test method for {@link org.jtoc.convertor.cpunit.PostAnnotation#main(java.lang.String[])}.
	 */
	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocAnnotation#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocAnnotation#JtocAnnotation()}.
	 */
	@Test
	public void testJtocAnnotation() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocAnnotation#JtocAnnotation(japa.parser.ast.expr.AnnotationExpr)}.
	 */
	@Test
	public void testJtocAnnotationAnnotationExpr() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocAnnotation#init()}.
	 */
	@Test
	public void testInit() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocAnnotation#getTestObject()}.
	 */
	@Test
	public void testGetTestObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocAnnotation#setTestObject(java.lang.String)}.
	 */
	@Test
	public void testSetTestObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocAnnotation#getTestMethod()}.
	 */
	@Test
	public void testGetTestMethod() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocAnnotation#setTestMethod(java.lang.String)}.
	 */
	@Test
	public void testSetTestMethod() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocAnnotation#getParameters()}.
	 */
	@Test
	public void testGetParameters() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocAnnotation#setParameters(java.lang.String)}.
	 */
	@Test
	public void testSetParameters() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocAnnotation#getHead()}.
	 */
	@Test
	public void testGetHead() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocAnnotation#setHead(java.lang.String)}.
	 */
	@Test
	public void testSetHead() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.jtoc.convertor.cpunit.JtocAnnotation#parse()}.
	 */
	@Test
	public void testParse() {
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

	private static Log logger = LogFactory.getLog(PostAnnotationTest.class);

	/**
	 * test class
	 */
	class MethodVisitor extends VoidVisitorAdapter<Object> {
		public MethodVisitor(){}
		
		@Override
		public void visit(MethodDeclaration n, Object arg) {
			List<AnnotationExpr> list = n.getAnnotations();
			if (list == null) // the method has no annotation.
				return;
			
			try {
				for (AnnotationExpr ae : list) {
					if(PostAnnotation.isInstance(ae)){
						PostAnnotation i = new PostAnnotation(ae);
						i.parse();
						System.out.println(i.toString());
					}
				}
			} catch (JtocFormatException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * XXX add a test file
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

		PostAnnotationTest pa = new PostAnnotationTest();
		// visit and print the methods names
		JtocNode.setFilename("PrePostAnnoTest.java");
		MethodVisitor visitor = pa.new MethodVisitor();
		visitor.visit(cu, null);
	}
}

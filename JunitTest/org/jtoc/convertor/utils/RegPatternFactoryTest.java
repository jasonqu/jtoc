/**
 * 
 */
package org.jtoc.convertor.utils;

import static org.junit.Assert.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author QGD
 *
 */
public class RegPatternFactoryTest{
	
	static RegPatternFactory factory;
	Matcher matcher;

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
		factory = RegPatternFactory.Instance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.jtoc.convertor.utils.RegPatternFactory#getPattern(java.lang.String)}.
	 * 
	 * Used in the @Pre Annotation addition
	 * 1. a line of only "{" after the declaration of the method.
	 * should reserve the indent space where the Pre statement is added.
	 * 
	 * 2. part of the method declaration in front of the "{".
	 * should reserve the indent space where the Pre statement is added.
	 */
	@Test
	public void testGetPatternPre() {
		matcher = factory.getPattern("{").matcher(" \t{ ");
		assertEquals(true, matcher.matches());
		assertEquals(" \t{ ", matcher.group(0));
		assertEquals(" \t", matcher.group(1));
		
		String line = " \tException { ";
		matcher = factory.getPattern("Code{").matcher(line);
		assertEquals(true, matcher.find());
		assertEquals(" \tException {", matcher.group(0));
		assertEquals(" \t", matcher.group(1));
		assertEquals("{ ", line.substring(matcher.end()-1));
		
		line = " 	Exception {";
		matcher = factory.getPattern("Code{").matcher(line);
		assertEquals(true, matcher.find());
		assertEquals(line, matcher.group(0));
		assertEquals(" \t", matcher.group(1));
		assertEquals("{", line.substring(matcher.end()-1));
		
		line = " 	Exception{ int x";
		matcher = factory.getPattern("Code{").matcher(line);
		assertEquals(true, matcher.find());
		assertEquals(" \tException{", matcher.group(0));
		assertEquals(" \t", matcher.group(1));
		assertEquals("{ int x", line.substring(matcher.end()-1));
	}
	
	@Test
	public void testGetPatternPost() {
		matcher = factory.getPattern("}").matcher("  }    ");
		assertEquals(true, matcher.matches());
		assertEquals("  ;", matcher.group(1)+';');
		
		matcher = factory.getPattern("Code}").matcher("  \thaha }    ");
		assertEquals(true, matcher.matches());
		assertEquals("  \thaha ;", matcher.group(1) + ';');
		// don't know what the purpose is
//		System.out.println(matcher.group(2).substring(0,
//				matcher.group(2).length()-1) + ';');
		
		matcher = factory.getPattern("return;").matcher("    	return;    \n");
		assertEquals(true, matcher.matches());
		
		Pattern pattern = factory.getPattern("retWithPreCode");
		
		matcher = pattern.matcher("   if(true) \treturn;    ");
		assertEquals(true, matcher.find());
		assertEquals("   if(true) \t;", matcher.group(1) + ';');
		assertEquals("   ;", matcher.group(2) + ';');

		matcher = pattern.matcher("   if(true){}return ;");
		assertEquals(true, matcher.find());
		assertEquals(" ", "   if(true){}return ;".substring(0, matcher.start()+1));
		
		matcher = pattern.matcher("   asdreturn ;");
		assertEquals(false, matcher.find());
		
		pattern = factory.getPattern("returnValue");
		String line = "if(false) ;return(null);";
		
		matcher = pattern.matcher("if(false) ;return(null);");
		assertEquals(true, matcher.find());
		assertEquals("if(false) ;", line.substring(0, matcher.start()+1));
		assertEquals("if(false) ;return", line.substring(0, matcher.start()+7));
		assertEquals("24 : 18", line.length()+" : "+matcher.end());
		assertEquals("(null);", line.substring(matcher.end()-1, line.length()));
		
		line = "if(false) ;return";
		matcher = pattern.matcher(line);
		assertEquals(true, matcher.find());
		assertEquals("if(false) ;", line.substring(0, matcher.start()+1));
		assertEquals("if(false) ;return", line.substring(0, matcher.start()+7));
		assertEquals("17 : 17", line.length()+" : "+matcher.end());
		assertEquals("n", line.substring(matcher.end()-1, line.length()));
		
		matcher = factory.getPattern("end;").matcher("end; ");
		assertEquals(true, matcher.find());
		assertEquals("end", "end; ".substring(0, matcher.start()));
	}

}

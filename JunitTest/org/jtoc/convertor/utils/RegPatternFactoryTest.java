/**
 * 
 */
package org.jtoc.convertor.utils;

import static org.junit.Assert.*;

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
public class RegPatternFactoryTest {
	
	static RegPatternFactory factory;
	Matcher matcher;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		factory = RegPatternFactory.Instance();
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
		
	}

}

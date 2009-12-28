package org.jtoc;

import org.jtoc.convertor.FileTester;
import org.jtoc.convertor.utils.RegPatternFactoryTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.jtoc");
		//$JUnit-BEGIN$
		suite.addTestSuite(ConditionExceptionTest.class);
		suite.addTestSuite(FileTester.class);
		suite.addTestSuite(RegPatternFactoryTest.class);
		//$JUnit-END$
		return suite;
	}

}

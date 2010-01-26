package org.jtoc;

import org.jtoc.convertor.FileTester;
import org.jtoc.convertor.utils.RegPatternFactoryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({    
	ConditionExceptionTest.class, 
	FileTester.class,
	RegPatternFactoryTest.class
 })
public class AllTests {
}

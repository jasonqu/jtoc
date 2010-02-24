package org.jtoc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({    
	org.jtoc.ConditionExceptionTest.class, 
	//org.jtoc.convertor.FileTester.class,
	org.jtoc.convertor.cpunit.PreAnnotationTest.class,
	org.jtoc.convertor.utils.RegPatternFactoryTest.class
 })
public class AllTests {
}

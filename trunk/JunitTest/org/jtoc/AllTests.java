package org.jtoc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	org.jtoc.convertor.FileTester.class,
	org.jtoc.convertor.cpunit.ClassInfoTest.class,
	org.jtoc.convertor.cpunit.InnerTestAnnotationTest.class,
	org.jtoc.convertor.cpunit.MethodInfoTest.class,
	org.jtoc.convertor.cpunit.PostAnnotationTest.class,
	org.jtoc.convertor.cpunit.PreAnnotationTest.class,
	org.jtoc.convertor.utils.RegPatternFactoryTest.class
 })
public class AllTests {
}

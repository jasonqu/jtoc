package org.jtoc.convertor;

import java.io.File;
import java.io.IOException;

import org.jtoc.convertor.utils.FileComparator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileTester {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFileCompare() throws Exception {
		File root = new File(".");
		System.out.println(root.getCanonicalPath());

		File expectedDir = new File(root.getCanonicalPath()+"/files/expected");
		File generatedDir = new File(root.getCanonicalPath()+"/files/generated");
		FileComparator.compare(expectedDir, generatedDir);
	}
}

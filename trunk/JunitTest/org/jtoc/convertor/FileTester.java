package org.jtoc.convertor;

import java.io.File;

import junit.framework.TestCase;

import org.jtoc.convertor.utils.FileComparator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileTester{
	
	File expectedDir;
	File generatedDir;
	File originalDir;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		File root = new File(".");
		System.out.println(root.getCanonicalPath());
		expectedDir = new File(root.getCanonicalPath()+"/files/expected");
		generatedDir = new File(root.getCanonicalPath()+"/files/generated");
		originalDir = new File(root.getCanonicalPath()+"/files/original");
		
		FileComparator.recurDelete(expectedDir);
		expectedDir.mkdirs();
	}

	@After
	public void tearDown() throws Exception {
		FileComparator.recurDelete(expectedDir);
	}

	@Test
	public void testFileCompare() throws Exception {
		ProjectConvertor.convertProject(originalDir, generatedDir, true);
		FileComparator.compare(expectedDir, generatedDir);
	}
}

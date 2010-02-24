package org.jtoc.convertor;

import java.io.File;

import junit.framework.TestCase;

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
		
		FileComparator.recurDelete(generatedDir);
		generatedDir.mkdirs();
	}

	@After
	public void tearDown() throws Exception {
		// FileComparator.recurDelete(generatedDir);
	}

	@Test
	public void testFileCompare() throws Exception {
		ProjectConvertor.convertProject(originalDir, generatedDir, true);
		//FileComparator.compare(expectedDir, generatedDir);
		FileComparator.compare(generatedDir, expectedDir);
		FileComparator.recurDelete(generatedDir);
	}
}

package org.jtoc.convertor;

import java.io.File;

import org.junit.*;

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
		expectedDir = new File("src/test/files/expected");
		generatedDir = new File("src/test/files/generated");
		originalDir = new File("src/test/files/original");
		
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
		FileComparator.compare(expectedDir, generatedDir);
		FileComparator.compare(generatedDir, expectedDir);
		FileComparator.recurDelete(generatedDir);
	}
}

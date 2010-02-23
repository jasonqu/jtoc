package org.jtoc.convertor;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The File Comparator used in unit test to compare whether the 2 files are the same.
 */
public class FileComparator {

	private static Log logger = LogFactory.getLog(FileComparator.class);

	/**
	 * Method to compare two files or directories
	 * @param expectedFile full absolute path of the expected File or Directory
	 * @param actualFile full absolute path of the actual File or Directory
	 * @throws IOException
	 */
	public static void compare(String expectedFile, String actualFile)
			throws IOException {
		compare(new File(expectedFile), new File(actualFile));
	}

	/**
	 * Method to compare two files or directories
	 * @param expectedFile the expected File or Directory
	 * @param actualFile the actual File or Directory
	 * @throws IOException
	 */
	public static void compare(File expectedFile, File actualFile)
			throws IOException {
		logger.debug("Comparing file " + expectedFile + " : " + actualFile);
		if (expectedFile.isDirectory() && actualFile.isDirectory()){
			if(ProjectConvertor.shouldExclude(expectedFile.getName()))
				return;
			for (File file : expectedFile.listFiles())
				FileComparator.compare(expectedFile.getCanonicalPath() + '/'
						+ file.getName(), actualFile.getCanonicalPath() + '/'
						+ file.getName());
		}
		else
			compareFile(expectedFile, actualFile);
	}

	/**
	 * Method to compare two files
	 * @param expectedFileName full absolute path of the expected File
	 * @param actualFileName full absolute path of the actual File
	 * @throws IOException
	 */
	public static void compareFile(String expectedFileName,
			String actualFileName) throws IOException {
		compareFile(new File(expectedFileName), new File(actualFileName));
	}

	/**
	 * Method to compare two files
	 * @param expectedFileName the expected File
	 * @param actualFileName the actual File
	 * @throws IOException
	 */
	public static void compareFile(File expectedFile, File actualFile)
			throws IOException {
		if(ProjectConvertor.shouldExclude(expectedFile.getName()))
			return;
		
		BufferedReader expected = new BufferedReader(new FileReader(expectedFile));
		BufferedReader actual = new BufferedReader(new FileReader(actualFile));

		try {
			String expectedStr, actualStr;
			int linenum = 0;
			while ((expectedStr = expected.readLine()) != null) {
				actualStr = actual.readLine();
				assertEquals("Difference found at Line " + linenum + " in File \"" 
						+ expectedFile.getAbsolutePath() + "\"",
						expectedStr, actualStr);
				linenum++;
			}
		} catch (IOException e) {
			throw e;
		} finally {
			expected.close();
			actual.close();
		}
	}
	
	/**
	 * @param file
	 */
	public static void recurDelete(File file) {
		if (file.isDirectory())
			for(File inner : file.listFiles())
				if(inner.isFile())
					inner.delete();
				else
					recurDelete(inner);
		file.delete();
	}
}

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jtoc.convertor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * class used to convert a project.
 * 
 * @author Goddamned Qu
 */
public class ProjectConvertor {
	
	private static Log logger = LogFactory.getLog(ProjectConvertor.class);

	// inspired by org.apache.tools.ant.DirectoryScanner
	private static final String[] defaultExcludes = new String[] {
		"CVS", ".cvsignore", // CVS
		"SCCS", // SCCS
		"vssver.scc", // Visual SourceSafe
		".svn", // Subversion
		".DS_Store" // Mac
	};
	
	static boolean shouldExclude(String filename) {
		for (String exclude : defaultExcludes)
			if (exclude.equalsIgnoreCase(filename))
				return true;
		return false;
	}
	
	private static void convert(File inputDir, File outputDir,
			boolean forceRewrite) throws Exception {
		if(shouldExclude(inputDir.getName()))
			return;
		
		if (!inputDir.exists())
			throw new Exception(
					"The specified input Directory is not existed : "
							+ inputDir.getAbsolutePath());

		if (!outputDir.exists()){
			logger.debug("Making directory "+outputDir.getAbsolutePath());
			outputDir.mkdir();
		}

		for (File origin : inputDir.listFiles()) {
			File output = new File(outputDir.getAbsolutePath() + '/'
					+ origin.getName());
			if (origin.isDirectory()){
				ProjectConvertor.convert(origin, output, forceRewrite);
			}
			else if (origin.getName().toLowerCase().endsWith(".java")) {
				if ((origin.lastModified() > output.lastModified())
						|| forceRewrite)
					ConvertorFactory.getSingleFileConvertor().convert(origin,
							output);
				// else do nothing
			} else {
				if ((origin.lastModified() > output.lastModified())
						|| forceRewrite)
					copyfile(origin, output);
				// else do nothing
			}
		}
	}
	
	public static void convertProject(File inputDir, File outputDir) throws Exception {
		ProjectConvertor.convertProject(inputDir, outputDir, false);
	}
	
	public static void convertProject(File inputDir, File outputDir,
			boolean forceRewrite) throws Exception {
		logger.info("Begin Jtoc Project Convertion:");
		logger.info("Input Directory: "+inputDir.getAbsolutePath());
		logger.info("Output Directory: "+outputDir.getAbsolutePath());
		logger.info("Should rewrite all files? "+forceRewrite);
		ProjectConvertor.convert(inputDir, outputDir, forceRewrite);
		logger.info("Jtoc Project Convertion Finished");
	}

	/**
	 * directly copy the file to the dest directory
	 * @param srcFile the source file
	 * @param destFile the destination file
	 * @throws IOException
	 */
	private static void copyfile(File srcFile, File destFile)
			throws IOException {
//		if(shouldExclude(srcFile.getName()))
//			return;
		
		logger.debug("copying form file " + srcFile.getName() + " to "
				+ destFile.getAbsolutePath());
		
		InputStream in = new BufferedInputStream(new FileInputStream(srcFile));
		OutputStream out = new BufferedOutputStream(new FileOutputStream(
				destFile));

		byte[] buf = new byte[4096];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		try {
			if (args == null || args.length == 0) {
				usage();
				return;
			}

			File inputDir = null;
			File outputDir = null;
			boolean rewrite = false;

			for (int i = 0; args != null && i < args.length; i++) {
				if (args[i].equals("-s")) {
					inputDir = new File(args[++i]);
				}
				else if (args[i].equals("-d")) {
					outputDir = new File(args[++i]);
				}
				else if (args[i].equals("-r")) {
					rewrite = true;
				} else {
					throw new Exception("The specified parameter was wrong: "+args[i]);
				}
			}

			ProjectConvertor.convertProject(inputDir, outputDir, rewrite);
			
		} catch (Exception e) {
			logger.error("Exception catched in convert process:", e);
		}
	}

	private static void usage() {
		System.out.println("Usage: java -jar jtoc.jar [parameters]");
		System.out.println("where parameters include:");
		System.out.println("       -s <Source Project Directory>");
		System.out.println("       -d <Destination Project Directory>");
		System.out.println("       -r : Rewrite all java files if specified");
		System.out.println("            or only rewrite the modified files");
		System.out.println("For Example£º");
		System.out.println("       java -jar jtoc.jar -s . -d ../testProject/");
		System.out.println(" Or");
		System.out.println("       java -jar jtoc.jar -s \"D:\\Origin\" -d \"D:\\Test\" -r");
	}

}

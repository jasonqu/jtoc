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

/**
 * class used to convert a project.
 * 
 * @author Goddamned Qu
 */
public class ProjectConvertor {
	
	public static void convert(File inputDir, File outputDir) throws Exception {
		if (!inputDir.exists())
			throw new Exception(
					"The specified input Directory is not existed : "
							+ inputDir.getAbsolutePath());
		
		if (!outputDir.exists())
			outputDir.mkdir();

		for (File f : inputDir.listFiles()) {
			File output = new File(outputDir.getAbsolutePath() + '/'
					+ f.getName());
			if (f.isDirectory())
				ProjectConvertor.convert(f, output);
			else if (f.getName().toLowerCase().endsWith(".java")) {
				ConvertorFactory.getSingleFileConvertor().convert(f, output);
			}else{
				copyfile(f, output);
			}
		}
	}
	
	private static void copyfile(File srcFile, File destFile) throws IOException {
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
		File inputDir = new File("E:/Project/Jtoc/Projects/JtocInput/");
		File outputDir = new File("E:/Project/Jtoc/Projects/JtocOutput/");
		
		if (args.length == 2) {
			inputDir = new File(args[0]);
			outputDir = new File(args[1]);
		}
		
		ProjectConvertor.convert(inputDir, outputDir);
	}

}

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

package org.jtoc.convertor.utils;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * class used to get the Regular Expression Patterns, implemented by Singleton.
 * 
 * @author Goddamned Qu
 */
public class RegPatternFactory {
	
	private static RegPatternFactory rpf = null;
	
	private RegPatternFactory(){
		this.init();
	}
	
	/**
	 * get the single instance of this factory
	 * 
	 * @return the single instance of this factory
	 */
	public static RegPatternFactory instance(){
		if(rpf == null)
			rpf = new RegPatternFactory();
		return rpf;
	}
	
	private HashMap<String, Pattern> map = new HashMap<String, Pattern>();
	
	/**
	 * initialize the patterns
	 */
	private void init(){
		// used in pre add
		map.put("{", Pattern.compile("^(\\s*)\\{\\s*$") );
		map.put("Code{", Pattern.compile("^(\\s*).*?\\{") );
		
		// used in post add
		map.put("}", Pattern.compile("^(\\s*)}\\s*$") );
		map.put("Code}", Pattern.compile("^((\\s*).*)}\\s*$") );
		map.put("return;", Pattern.compile("^(\\s*)return\\s*;\\s*$") );
		map.put("retWithPreCode", Pattern.compile("((\\s*).*\\W)return\\s*;\\s*$") );
		
		map.put("returnValue", Pattern.compile("\\Wreturn\\W?"));
		map.put("end;", Pattern.compile(";\\s*$") );
	}
	
	/**
	 * the available pattern list :
	 * <code>"{", "Code{", "}", "Code}", "return;", "retWithPreCode", "returnValue", "end;"</code>
	 * 
	 * @param name the String represents the pattern
	 * @return the specified pattern
	 */
	public Pattern getPattern(String name) {
		return map.get(name);
	}
}

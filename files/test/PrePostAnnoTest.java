package org.jtoc.convertor.cpunit;

import org.jtoc.Post;
import org.jtoc.Pre;

public class PrePostAnnoTest {
	
	public void method() {
	}

	@Pre
	@Post
	public void method0() {
	}
	
	@Pre()
	@Post()
	public void method1() {
	}

	@Pre(testObject = "innerTest")
	@Post(testObject = "innerTest")
	public void method0(String name) {
	}

	@Pre(testMethod = "commenPreCheck")
	@Post(testMethod = "commenPostCheck")
	public void method0(String name) {
	}
	
	@Pre(parameters = "para1, paraK")
	@Post(parameters = "para1, paraK")
	public void method0(String name) {
	}
	
	@Pre(testObject = "innerTest", testMethod = "commenPreCheck")
	@Post(testObject = "innerTest", testMethod = "commenPostCheck")
	public void method0(String name) {
	}
	
	@Pre(testObject = "innerTest", parameters = "para1, paraK")
	@Post(testObject = "innerTest", parameters = "para1, paraK")
	public void method0(String name) {
	}
	
	@Pre(testMethod = "commenPreCheck", parameters = "para1, paraK")
	@Post(testMethod = "commenPostCheck", parameters = "para1, paraK")
	public void method0(String name) {
	}
	
	@Pre(testObject = "innerTest", testMethod = "commenPreCheck", parameters = "para1, paraK")
	@Post(testObject = "innerTest", testMethod = "commenPostCheck", parameters = "para1, paraK")
	public void method0(String name) {
	}
	
	// the annotation below would cause JtocFormatException
	@Pre(testObject1 = "innerTest", testMethod = "commenPreCheck", parameters = "para1, paraK")
	@Post(testObject1 = "innerTest", testMethod = "commenPostCheck", parameters = "para1, paraK")
	public void method0(String name) {
	}
}

package org.jtoc.convertor.cpunit;

public class PrePostAnnoTest {
	
	public void method0() { }

	@Pre
	@Post
	public void method1() {
	}
	
	@Post() @Pre()
	public int method2() {
	}

	@Post(testObject = "test")
	@Pre(testObject = "test")
	public int method3(String para1) {
	}

	@Pre(testMethod = "commenPreCheck")
	public Object method4(String para1) {
	}
	
	@Post(testMethod = "commenPostCheck")
	public Object method5(String para1) {
	}
	
	@Pre(parameters = "para1, paraK")
	@Post(parameters = "para1, paraK")
	public Object method6(String para1, int paraK, double paraN) {
	}
	
	@Pre(testObject = "test", testMethod = "commenPreCheck")
	@Post(testObject = "test", testMethod = "commenPostCheck")
	public double method7(String para1, int paraK, double paraN) {
	}
	
	@Pre(testObject = "test", parameters = "para1, paraK")
	@Post(testObject = "test", parameters = "para1, paraK")
	public double method8(String para1, int paraK, double paraN) {
	}
	
	@Pre(testMethod = "commenPreCheck", parameters = "para1, paraK")
	@Post(testMethod = "commenPostCheck", parameters = "para1, paraK")
	public Integer method9(String para1, int paraK, double paraN) {
	}
	
	@Pre(testObject = "test", testMethod = "commenPreCheck", parameters = "para1, paraK")
	@Post(testObject = "test", testMethod = "commenPostCheck", parameters = "para1, paraK")
	public Integer method10(String para1, int paraK, double paraN) {
	}
	
	// the annotation below would cause JtocFormatException
	@Pre(testObject = {"test"}, testMethod = "commenPreCheck", parameters = "para1, paraK")
	@Post(testObject1 = "test", testMethod = "commenPostCheck", parameters = "para1, paraK")
	public Integer method11(String para1, int paraK, double paraN) {
	}
}

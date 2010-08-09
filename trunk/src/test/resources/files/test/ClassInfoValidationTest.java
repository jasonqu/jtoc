// dumb
public class ClassInfoValidationTest {
}

class ClassDumbWithOneMethodButNotAnnoed {
	public int add(int i1, int i2){
		return i1+ i2;
	}
}

@InnerTest
class ClassWithInnerTestAnnoButNoMethodAnnoed {
	public int add(int i1, int i2){
		return i1+ i2;
	}
}

class ClassNotAnnoedWithAnnoedMethod {
	@Pre @Post
	public int add(int i1, int i2){
		return i1+ i2;
	}
}

@InnerTest
class ClassAnnoedButNoInnerClass {
	@Pre @Post
	public int add(int i1, int i2){
		return i1+ i2;
	}
}

@InnerTest
class ClassAnnoedAndRightInnerClassWithoutMethodsAnnoed {
	public int add(int i1, int i2){
		return i1+ i2;
	}
	
	class InnerTest{}
}

@InnerTest
class ClassAnnoedAndRightInnerClass1 {
	@Post
	public int add(int i1, int i2){
		return i1+ i2;
	}
	
	class InnerTest{}
}

@InnerTest
class ClassAnnoedAndRightInnerClass2 {
	@Pre
	public int add(int i1, int i2){
		return i1+ i2;
	}
	
	class InnerTest{}
	class InnerTest1{}
	class InnerTest2{}
}

@InnerTest
class ClassAnnoedButWrongInnerClass {
	@Pre @Post
	public int add(int i1, int i2){
		return i1+ i2;
	}
	
	class InnerTest1{}
	class InnerTest2{}
}

@InnerTest
class NestedInnerClassTest1 {
	@Pre
	public int add(int i1, int i2){
		return i1+ i2;
	}
	
	@InnerTest
	class InnerTest{}
}

@InnerTest
class NestedInnerClassTest2 {
	@Post
	public int add(int i1, int i2){
		return i1+ i2;
	}
	
	class InnerTest{
		@Pre @Post
		public int add(int i1, int i2){
			return i1+ i2;
		}
	}
}

@InnerTest
class NestedInnerClassTest3 {
	@Post
	public int add(int i1, int i2){
		return i1+ i2;
	}
	
	@InnerTest
	class InnerTest{
		public int add(int i1, int i2){
			return i1+ i2;
		}
	}
}

@InnerTest
class NestedInnerClassTest4 {
	@Post
	public int add(int i1, int i2){
		return i1+ i2;
	}
	
	@InnerTest
	class InnerTest{
		@Pre @Post
		public int add(int i1, int i2){
			return i1+ i2;
		}
	}
}

@InnerTest
class NestedInnerClassTest5 {
	@Post
	public int add(int i1, int i2){
		return i1+ i2;
	}
	
	@InnerTest
	class InnerTest{
		@Pre @Post
		public int add(int i1, int i2){
			return i1+ i2;
		}
		
		class InnerTest{}
	}
}

@InnerTest
class NestedInnerClassTest6 {
	@Post
	public int add(int i1, int i2){
		return i1+ i2;
	}
	
	@InnerTest
	class InnerTest{
		@Pre @Post
		public int add(int i1, int i2){
			return i1+ i2;
		}
		
		class InnerTest{}
		class InnerTest1{}
	}
}

@InnerTest
class NestedInnerClassTest7 {
	@Post
	public int add(int i1, int i2){
		return i1+ i2;
	}
	
	@InnerTest
	class InnerTest{
		@Pre @Post
		public int add(int i1, int i2){
			return i1+ i2;
		}
		
		class InnerTest1{}
		class InnerTest2{}
	}
}
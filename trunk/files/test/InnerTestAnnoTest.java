public class InnerTestAnnoTest {
}

@InnerTest
class InnerTestAnnoWithNoParas {
}

@InnerTest()
class InnerTestAnnoWithNoParaButBrackets {
}

@InnerTest(classNames = "InnerTest1")
class InnerTestAnnoWith1SinglePara {
}

@InnerTest(objectNames = "test1")
class InnerTestAnnoWith1SinglePara {
}

@InnerTest(classNames = "InnerTest1", objectNames = "test1")
class InnerTestAnnoWith2SingleParas {
}

@InnerTest(classNames = { "InnerTest1", "InnerTest2" }, 
		objectNames = { "test1", "test2" })
class InnerTestAnnoWith2MultiParas {
}

// the annotation below would cause JtocFormatException
@InnerTest(classNames = { "InnerTest1", "InnerTest2" }, 
		objectNames = { "test2" })
class InnerTestAnnoWith2MultiParasWithDifferentNums {
}

@InnerTest(classNames = { "InnerTest1", "InnerTest2" })
class InnerTestAnnoWith1MultiParas {
}
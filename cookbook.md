# Jtoc Cookbook #
<p>Guodong Qu</p>


&lt;hr WIDTH="100%"&gt;


<br>
Notice: when you read this article, the author suppose that you had<br>
already been familiar with JUnit, if not, please check<br>
<a href='http://www.junit.org'>here</a> first.<br>
<h2>Introduction</h2>
You may have already heard about the concept of<br>
<a href='http://en.wikipedia.org/wiki/Design_by_contract'>"Programming by contract"</a>,<br>
which uses runtime assertions to prescribe the behavior of programs.<br>
Although Java provide the keyword 'assert' to write runtime assertions, Programmers<br>
found it insufficient and developed over 10 kinds of libraries for<br>
"Programming by contract" such as Contract4J, jContractor,<br>
Java Modeling Language (JML) etc [check<br>
<a href='http://en.wikipedia.org/wiki/Design_by_contract#Languages_with_third-party_support'>here</a>
for a complete list].<br>
<br>
<p>Jtoc is also an application of this kind; it focuses on<br>
<font color='green'>Completely</font> test the contracts<br>
<font color='green'>Without</font> changing the original design of the class,<br>
while the mentioned projects have to put some constraints on the original code<br>
or hard to completely test the original code's behavior; with this feature,<br>
it's possible to put test case auto-generation into practical usage.<br>
Jtoc supports Unit testing, where the written JUnit tests could be used in the<br>
test-project Jtoc generated without any change.<br>
<br>
<p>The design of the Jtoc is easy to understand, Jtoc provides 3 annotations<br>
and use Java's Innerclass as the implementation method of the contracts.<br>
Programmer could use these annotations to annotated his code, and implement<br>
the pre and post assertions in the inner test classes. When need test,<br>
just use Jtoc Code Convertor to compile the project to a new test project and use the<br>
JUnit test tools to run the tests. the process is depicted in Figure 1:<br>
<br>
<a href='http://lh3.ggpht.com/_OATorOD3pb8/Sd6zLlY-q_I/AAAAAAAAAE8/2_Kf7bKG_CI/jtoc_design.JPG'>http://lh3.ggpht.com/_OATorOD3pb8/Sd6zLlY-q_I/AAAAAAAAAE8/2_Kf7bKG_CI/jtoc_design.JPG</a>
<p><font size='-1'>Figure 1 : Jtoc design structure</font>

<h2>2. Jtoc Annotations</h2>
<p>Jtoc provides 3 annotations, which are 'Pre', 'Post' and 'InnerTest'. Their retention<br>
policy are all 'SOURCE', so the user should't worry the problem of runtime resource occupation.<br>
Here is the introduction of these annotations:<br>
<h3><a href='http://lh5.ggpht.com/_OATorOD3pb8/Sd6zLkAgDsI/AAAAAAAAAFE/BVgpi-8Eodg/at.JPG'>http://lh5.ggpht.com/_OATorOD3pb8/Sd6zLkAgDsI/AAAAAAAAAFE/BVgpi-8Eodg/at.JPG</a>org.jtoc.Pre</h3>
<p>Pre annotation's target is "method", it tells Jtoc that the method to which<br>
it is attached should have a pre checking invocation. A simple Pre invocation<br>
looks like this:<br>
</p>

<pre><code>code 1:<br>
public class Example {<br>
   @Pre<br>
   public void method(ParameterType1 para1, ..., ParameterTypeN paraN) {<br>
      ...<br>
   }<br>
}<br>
</code></pre>
</p>

<p>Then the converted source file would contain code like this in code 2:<br>
<pre><code>code 2:<br>
public class Example {<br>
   public void method(ParameterType1 para1, ..., ParameterTypeN paraN) {<br>
      innerTest.methodPreCheck(para1, ..., paraN);<br>
      ...<br>
   }<br>
}<br>
</code></pre>
</p>

<p>The Pre annotation supports three optional parameters, namely testObject, testMethod and parameters.<br>
Their types are the same -- "String"; they are used to store the inner test object's name,<br>
the test method's name and the test method's input parameters respectively. If they are not specified,<br>
then their default value "innerTest", "method's name"+"PreCheck" and "method's parameters" would be assigned.<br>
For instance, if the "Pre" annotation in code 1 is changed to<br>
"@Pre(testObject = "test", testMethod = "commenTest", parameters = "para1, paraK")",<br>
then the converted code would be like the code showed in code 3 rather than the default values depicted in code 2:<br>
<pre><code>code 3:<br>
public class Example {<br>
   public void method(ParameterType1 para1, ..., ParameterTypeN paraN) {<br>
      test.commenTest(para1, paraK);<br>
      ...<br>
   }<br>
}<br>
</code></pre>
</p>

<h3><a href='http://lh5.ggpht.com/_OATorOD3pb8/Sd6zLkAgDsI/AAAAAAAAAFE/BVgpi-8Eodg/at.JPG'>http://lh5.ggpht.com/_OATorOD3pb8/Sd6zLkAgDsI/AAAAAAAAAFE/BVgpi-8Eodg/at.JPG</a>org.jtoc.Post</h3>
<p>Post annotation's target is "method", it tells Jtoc that the method to which<br>
it is attached should have a post checking invocation. A simple Post invocation<br>
looks like this:<br>
</p>
<pre><code>code 4:<br>
public class Example {<br>
   @Post<br>
   public void method1(ParameterType1 para1, ..., ParameterTypeN paraN) {<br>
      ...<br>
      return;<br>
      ...<br>
   }<br>
<br>
   @Post <br>
   public returnType method2(ParameterType1 para1, ..., ParameterTypeN paraN) {<br>
      ...<br>
      return expr1;<br>
      ...<br>
      return exprK;<br>
   }<br>
}<br>
</code></pre>
</p>

<p>Then the converted source file would contain code like this in code 5:<br>
<pre><code>code 5:<br>
public class Example {<br>
   public void method1(ParameterType1 para1, ..., ParameterTypeN paraN) {<br>
      ...<br>
      {innerTest.method1PostCheck(para1, ..., paraN);return;}<br>
      ...<br>
      innerTest.method1PostCheck(para1, ..., paraN);<br>
   }<br>
   <br>
   public returnType method2(ParameterType1 para1, ..., ParameterTypeN paraN) {<br>
      ...<br>
      return innerTest.method2PostCheck((expr1), para1, ..., paraN);<br>
      ...<br>
      return innerTest.method2PostCheck((exprK), para1, ..., paraN);<br>
   }<br>
  //which need the innerTest method return the 1st input without any change<br>
}<br>
</code></pre>
</p>

<p>The Post annotation also supports the three optional parameters mentioned in the Pre annotation's<br>
introduction, and these optional parameters are basiclly the same as those above, Except:<br>
<ol><li>testMethod's default value is "method's name"+"PostCheck"<br>
</li><li>parameters's default value if "method's return value"+"method's parameters" if the return type is not "void"; or else the "method's parameters" would be assigned as the default value.</li></ol>

<h3><a href='http://lh5.ggpht.com/_OATorOD3pb8/Sd6zLkAgDsI/AAAAAAAAAFE/BVgpi-8Eodg/at.JPG'>http://lh5.ggpht.com/_OATorOD3pb8/Sd6zLkAgDsI/AAAAAAAAAFE/BVgpi-8Eodg/at.JPG</a>org.jtoc.InnerTest</h3>
<p>InnerTest annotation's target is "type", it tells Jtoc that the class to which<br>
<blockquote>it is attached would have the InnerTest classes initialized. A simple InnerTest Example<br>
looks like this:<br>
<pre><code>code 6:<br>
@InnerTest<br>
public class Example {<br>
      ...<br>
}<br>
</code></pre>
</p></blockquote>

<p>Then the converted source file would contain code like this in code 7:<br>
<pre><code>code 7:<br>
public class Example {<br>
      ...<br>
      InnerTest innerTest = new InnerTest();<br>
}<br>
</code></pre>
</p>

<p>The InnerTest annotation supports two optional parameters, "classNames" and "objectNames".<br>
they respectively specify Inner Test Classes' names and objects' names whose default values<br>
are "InnerTest" and "innerTest". For instance, if the InnerTest annotation in code 6 is changed to<br>
"@InnerTest(classNames = {"InnerTest1", "InnerTest2"}, objectNames = {"test1", "test2"})",<br>
then the converted code would be like the code showed in code 8:<br>
<pre><code>code 8:<br>
public class Example {<br>
      ...<br>
      InnerTest1 test1 = new InnerTest1();<br>
      InnerTest2 test2 = new InnerTest2();<br>
}<br>
</code></pre>
</p>
<p>
The design rules of the inner test class are:<br>
<ol><li>In the inner test class, the following fields should be used for assertions readonly: any field in the tested class and any input parameter.<br>
</li><li>For the method with return type that is not "void", the return result of this method must be inputed to the post check method as the 1st parameter, and the post check method should return it without any change.</li></ol>

If the two rules above are not conformed with, the classes in the test project Jtoc generated<br>
may not function the same as the original project.<br>
</p>

<h2>3. A Example</h2>
<p>Allow me use the Money example in JUnit to show the usage of Jtoc. Assume the content<br>
of the "Money.java" is:<br>
<pre><code>Money.java<br>
import static org.junit.Assert.*;<br>
import org.jtoc.*;<br>
@InnerTest<br>
public class Money{<br>
	private final int fAmount;<br>
	private final String fCurrency;<br>
	public Money(int amount, String currency) {<br>
		fAmount= amount;<br>
		fCurrency= currency;<br>
	}<br>
	public int amount() {<br>
		return fAmount;<br>
	}<br>
	public String currency() {<br>
		return fCurrency;<br>
	}<br>
	@Post<br>
	public Money multiply(int factor) {<br>
		return new Money(amount()*factor, currency());<br>
	}<br>
	@Post<br>
	public Money negate() {<br>
		return new Money(-amount(), currency());<br>
	}<br>
	@Pre(testObject = "InnerTestStatic")<br>
	@Post(testObject = "InnerTestStatic")<br>
	public static Money getMoney(int amount, String currency){<br>
		return new Money(amount, currency);<br>
	}<br>
<br>
	private class InnerTest {<br>
		public Money multiplyPostCheck(Money result, int factor){<br>
			assertEquals(currency(), result.currency());<br>
			assertEquals(amount()*factor, result.amount());<br>
			return result;<br>
		}<br>
		<br>
		public Money negatePostCheck(Money result){<br>
			return multiplyPostCheck(result, -1);<br>
		}<br>
	}<br>
	<br>
	static class InnerTestStatic{<br>
		public static void getMoneyPreCheck(int amount, String currency) {<br>
			assertNotNull(currency);<br>
		}<br>
	<br>
		public static Money getMoneyPostCheck(Money result, int amount,	String currency) {<br>
			assertEquals(currency, result.currency());<br>
			assertEquals(amount, result.amount());<br>
			return result;<br>
		}<br>
	}<br>
}<br>
</code></pre>
<p>As the code showed above, the innerclasses - InnerTest and InnerTestStatic, have<br>
implemented the contracts; the method to be tested are add with some Jtoc annotations.<br>
the converted code would be like the code showed in below<a href='with.md'>some repeated code omitted</a>:<br>
<pre><code>import static org.junit.Assert.*;<br>
import org.jtoc.*;<br>
public class Money{<br>
	private final int fAmount;<br>
	private final String fCurrency;<br>
	public Money(int amount, String currency) {<br>
		fAmount= amount;<br>
		fCurrency= currency;<br>
	}<br>
	public int amount() {<br>
		return fAmount;<br>
	}<br>
	public String currency() {<br>
		return fCurrency;<br>
	}<br>
	public Money multiply(int factor) {<br>
		return innerTest.multiplyPostCheck(( new Money(amount()*factor, currency())), factor);<br>
	}<br>
	public Money negate() {<br>
		return innerTest.negatePostCheck(( new Money(-amount(), currency())));<br>
	}<br>
	public static Money getMoney(int amount, String currency){<br>
		InnerTestStatic.getMoneyPreCheck(amount, currency);<br>
		return InnerTestStatic.getMoneyPostCheck(( new Money(amount, currency)), amount, currency);<br>
	}<br>
<br>
	private class InnerTest { ... }<br>
	static class InnerTestStatic { ... }<br>
<br>
	InnerTest innerTest = new InnerTest();<br>
}<br>
</code></pre>
<p>As the converted code showed above, the method to be tested successfully called<br>
corresponding test methods; notice that the "testObject" of the post annotation in<br>
"getMoney" was set to "InnerTestStatic", and the static test methods had been successfully<br>
called. By the way, the "multiplyPostCheck" test method had been called by "negatePostCheck"<br>
which could show that it is easier to maintain test code because of the reuse of test code.<br>
<br>
<h2>4. Rules and Restrictions</h2>
Stephen Hawking once said this joke for his book "Space and Time Warps":<br>
"Someone told me that each equation I included in the book would halve the sales."<br>
While for the relation of library and programmers, it might not be a joke but a rule:<br>
"each restriction the library included halve the potential users."<br>
However, for the code's readability and convenience in Jtoc's implementation[mostly the latter<br>
reason :)], I have to list the following rules and restrictions, hope their number<br>
would reduce in latter releases:<br>
<p>
The design rules of the inner test class have already been introduced; however, I would<br>
repeat them since they are important: the test methods must never change any data in<br>
original classes; the return value of the test method has to be used as the 1st parameter in the<br>
post-check method and returned without any change.<br>
</p>
</p>
<p>Besides the two rules, there still are four restrictions which would cause<br>
converting problems if violated:<br>
</p>
<h4>(1). the line with Jtoc annotation should not have other code. Because the generated<br>
code would delete these lines.</h4>
<pre><code>@InnerTest @AnotherAnno<br>
public class Example{<br>
	@Post @Override<br>
	public void method1(int factor) {<br>
		...<br>
	}@pre @post<br>
	public int method2() {<br>
		...<br>
	}<br>
	@post public void method3() {<br>
		...<br>
	}<br>
}<br>
</code></pre>
The right format is:<br>
<pre><code>@InnerTest<br>
@AnotherAnno<br>
public class Example{<br>
	@Post<br>
	@Override<br>
	public void method1(int factor) {<br>
		...<br>
	}<br>
	@pre @post<br>
	public int method2() {<br>
		...<br>
	}<br>
	@post<br>
	public void method3() {<br>
		...<br>
	}<br>
}<br>
</code></pre>
<h4>(2). For the void method with Post annotation, the return point: last '}' of the<br>
method body and the "return;" statement must be the last non-blank characters in the line.<br>
"non-blank characters" means " \t\r\n\v\f\" in regular expression.<br>
</h4>
<pre><code>@InnerTest<br>
public class Example{<br>
	@Post<br>
	public void method1(int factor) {<br>
		...<br>
	}// End of method1<br>
	@pre @post<br>
	public void method2() {<br>
		if(b == null)<br>
			return; else<br>
		...<br>
		if(shouldReturn){<br>
			return;}<br>
		else{<br>
			...<br>
		}<br>
	}private int method3(){<br>
		...<br>
	}<br>
}<br>
</code></pre>
The right format is:<br>
<pre><code>@InnerTest<br>
public class Example{<br>
	@Post<br>
	public void method1(int factor) {<br>
		...<br>
	}<br>
	@pre @post<br>
	public void method2() {<br>
		if(b == null)<br>
			return;<br>
		else<br>
		...<br>
		if(shouldReturn){<br>
			return;<br>
		}<br>
		else{<br>
			...<br>
		}<br>
	}<br>
	private int method3(){<br>
		...<br>
	}<br>
}<br>
</code></pre>
<h4>(3). For the non-void method with Post annotation, the ';' of the return statement<br>
must be the last non-blank characters in the line, in which the statement could in multi-lines.</h4>
<pre><code>@InnerTest<br>
public class Example{<br>
	@post<br>
	public ActionListener method1() {<br>
		if(shouldReturnListener)<br>
		return new ActionListener(){<br>
			...<br>
			}; else<br>
			return null;<br>
	}<br>
	@pre @post<br>
	public int method2(int a, int b) {<br>
		if(b &lt; 0) return a-b;<br>
		else return a+b;}<br>
}<br>
</code></pre>
The right format is:<br>
<pre><code>@InnerTest<br>
public class Example{<br>
	@post<br>
	public ActionListener method1() {<br>
		if(shouldReturnListener)<br>
		return new ActionListener(){<br>
			...<br>
			};<br>
		else<br>
			return null;<br>
	}<br>
	@pre @post<br>
	public int method2(int a, int b) {<br>
		if(b &lt; 0) return a-b;<br>
		else return a+b;<br>
	}<br>
}<br>
</code></pre>
<h4>(4). Do not annotate method with only one line, which means the '{' and '}'<br>
of the method body are in the same line.</h4>
<pre><code>@InnerTest<br>
public class Example{<br>
	@pre<br>
	public void setNumber(int number) <br>
	{ this.number = number; }<br>
	@pre @post<br>
	public int getNumber() <br>
	{ return number; }<br>
}<br>
</code></pre>
The right format is:<br>
<pre><code>@InnerTest<br>
public class Example{<br>
	@pre<br>
	public void setNumber(int number){<br>
		this.number = number; }<br>
	@pre @post<br>
	public int getNumber()<br>
	{<br>
		return number;<br>
	}<br>
}<br>
</code></pre>
<p>In fact, you would find these restrictions are simple, and comply with the<br>
Java's readability specification, so maybe you should get used to these:-)<br>
<h2>5. Conclusions</h2>
<p>Hope you enjoy your Jtoc journey, good luck!<br>
<br>
<br>
<hr WIDTH="100%"><br>
<br>

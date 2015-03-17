# Jtoc QuickStart #

<p>Guodong Qu</p>



&lt;hr WIDTH="100%"&gt;


<br>
<br>
<p>Soppose we have a java project called <code>jtoc-quick-sample</code>, it has a class named <code>Divider</code> only to provide a method which return the result of 2 input parameters' division. Here we used integer as the parameter's type oddly, only to show the <code>DivideByZero</code> Exception.</p>

<pre><code>package jtoc.test;<br>
<br>
public class Divider {<br>
	public int divide(int x, int y){<br>
		return x/y;<br>
	}<br>
}<br>
</code></pre>

<p>Usually we could write a junit test, like this</p>

<pre><code>package jtoc.test;<br>
<br>
import static org.junit.Assert.*;<br>
import org.junit.Test;<br>
<br>
public class DividerTest {<br>
	@Test<br>
	public void testDivide() {<br>
		assertEquals("", 2, new Divider().divide(4, 2));<br>
	}<br>
}<br>
</code></pre>

<p>
Now consider to introduce the contract to constrain the input and verify the output, we should have that the divisor could not be 0, and the result should be exactly the same as the division's output.</p>

<p>
There many available contract <a href='http://en.wikipedia.org/wiki/Design_by_contract#Languages_with_third-party_support'>tools</a> for java, however, I would like to show you the advantages of jtoc. To use jtoc, you need download the binary distribution from <a href='http://code.google.com/p/jtoc/downloads/list'>here</a>, extract it in the project's 'lib' directory and add the jtoc.jar into the classpath.</p>

<p>
Now we could introduce the contract with jtoc, and use the junit as the assertion API, the code would be like this:</p>

<pre><code>package jtoc.test;<br>
<br>
import static org.junit.Assert.*;<br>
import org.jtoc.*;<br>
<br>
@InnerTest<br>
public class Divider {<br>
	@Pre @Post<br>
	public int divide(int x, int y){<br>
		return x/y;<br>
	}<br>
	<br>
	@SuppressWarnings("unused")<br>
	private class InnerTest {<br>
		public void dividePreCheck(int x, int y){<br>
			assertNotSame("", 0, y);<br>
		}<br>
		<br>
		public int dividePostCheck(int result, int x, int y){<br>
			assertEquals("", result, x/y);<br>
			return result;<br>
		}<br>
	}<br>
}<br>
</code></pre>

<p>
Now we need to use jtoc to convert this project into a test project where the contracts could actually work. We need to write a ant build file like this (now the invocation methods of jtoc is limited to ant and console, but <a href='http://code.google.com/p/jtoc-eclipse-plugin/'>the eclipse plugin</a> is under development): <br>
<br>
Unknown end tag for </P><br>
<br>
<br>
<br>
<pre><code>&lt;?xml version="1.0" ?&gt;<br>
&lt;project name="AntExample" default="jtoc"&gt;<br>
	&lt;path id="jtoc.jar"&gt;<br>
		&lt;pathelement location="./lib/jtoc-0.1.2.jar" /&gt;<br>
	&lt;/path&gt;<br>
<br>
	&lt;target name="jtoc"&gt;<br>
		&lt;taskdef name="jtoc" classname="org.jtoc.ant.ConvertTask"&gt;<br>
			&lt;classpath refid="jtoc.jar" /&gt;<br>
		&lt;/taskdef&gt;<br>
		&lt;jtoc srcDir="." destDir="../jtoc-quick-sample2/" rewrite="false"/&gt;<br>
	&lt;/target&gt;<br>
&lt;/project&gt;<br>
</code></pre>

<p>
Run the ant target, then we have a project named <code>jtoc-quick-sample2</code>, where the <code>Divider</code>'s source file is converted to be be like this:</p>

<pre><code>package jtoc.test;<br>
<br>
import static org.junit.Assert.*;<br>
import org.jtoc.*;<br>
<br>
public class Divider {<br>
	public int divide(int x, int y){<br>
		innerTest.dividePreCheck(x, y);<br>
		return innerTest.dividePostCheck(( x/y), x, y);<br>
	}<br>
	<br>
	@SuppressWarnings("unused")<br>
	private class InnerTest {<br>
		public void dividePreCheck(int x, int y){<br>
			assertNotSame("", 0, y);<br>
		}<br>
		<br>
		public int dividePostCheck(int result, int x, int y){<br>
			assertEquals("", result, x/y);<br>
			return result;<br>
		}<br>
	}<br>
<br>
	InnerTest innerTest = new InnerTest();<br>
}<br>
</code></pre>

<p>
Now, we can see that, without learning new grammar, the contracts are added to the class Divider. Now try the unit test, it works as well; so here is one advantage of jtoc: if you write the contracts right, it will not break you original unit tests.</p>

<p>
P.S Some may wondered why we generated the source files rather than directly generated the class files? The reason is that the programmers need an easy way to trace the failed test, and it would be better if there are source file to refer to; however, if the programmers do not want to see the generated source files, it is not hard to write an ant target to generate the class files directly, like the jtoc-test target in the <a href='http://jtoc.googlecode.com/files/jtoc-quick-sample.zip'>sample project</a>'s ant build file. </p>

<p>
Now we could add tests for <code>Divider</code> without considering the expected output, because the constraint we added by contracts would help us to restrain the software's behavior, which is another advantage of jtoc. Here is an example, notice we wisely avoid the 0 in the dividers because we know that not assign the divider to zero is the contract to apply in order to use this class</p>

<pre><code>	@Test<br>
	public void testDivideWithJtoc() {<br>
		int[] divideds = new int[]{ 0, 4, 10 , -30, Integer.MAX_VALUE};<br>
		int[] dividers = new int[]{ 2, 10 , -30, Integer.MAX_VALUE};<br>
		for(int divided : divideds)<br>
			for(int divider : dividers)<br>
				new Divider().divide(divided, divider);<br>
	}<br>
</code></pre>

<p>
Without much extra work, we run many cases without specifically assignning the result. Now the original <code>testDivide</code> seemed to be useless and not proper to be kept in the test class, but how would we know that the results is exactly the same we expected.</p>

<p>
To assure this, one could keep the old unit tests, which as showed above. Well we also could choose write the test case in the contracts like the one showed in below</p>

<pre><code>		public int dividePostCheck(int result, int x, int y){<br>
			// the original 'testDivide' test case<br>
			if(4 == x &amp;&amp; 2 == y) assertEquals("", 2, result);<br>
<br>
			assertEquals("", result, x/y);<br>
			return result;<br>
		}<br>
</code></pre>

<p>
Notice not to write the test case like this "<code>if(4 == x &amp;&amp; 2 == y) assertEquals("", 2, new Divider().divide(4, 2), 0.001);</code>", which will be run as endless loop.</p>

<p>
Now as you can see, with the code written in java, we could easily locate the test contracts and write them no matter how complex they are.</p>

<p>
Finally I would like to introduce one of the most important reason for using jtoc: the contracts also work with higher invocation.</p>

<p>
Suppose we have another class called <code>ArrayDivider</code> which use <code>Divider</code> as its component. As inferred by its name, the class provide a method to divide 2 int array with the same length, and return the result. As you can see in the code, it has its Pre condition checker to make sure the inputed array are of the same length and the Post condition checker to make sure the result is right.</p>

<pre><code>package jtoc.test;<br>
<br>
import static org.junit.Assert.*;<br>
import org.jtoc.*;<br>
<br>
@InnerTest<br>
public class ArrayDivider {<br>
	@Pre @Post<br>
	public int[] divide(int[] xarray, int[] yarray){<br>
		int[] result = new int[xarray.length];<br>
		for(int i = 0; i &lt; xarray.length; i++ )<br>
			result[i] = new Divider().divide(xarray[i], yarray[i]);<br>
		return result;<br>
	}<br>
	<br>
	@SuppressWarnings("unused")<br>
	private class InnerTest {<br>
		public void dividePreCheck(int[] xarray, int[] yarray){<br>
			assertEquals("", xarray.length, yarray.length);<br>
		}<br>
		<br>
		public int[] dividePostCheck(final int[] result, int[] xarray, int[] yarray){<br>
			for(int i = 0; i &lt; xarray.length; i++ )<br>
				assertEquals("", result[i], xarray[i]/yarray[i]);<br>
			return result;<br>
		}<br>
	}<br>
}<br>
</code></pre>

<p>
We can write a test like this</p>

<pre><code>package jtoc.test;<br>
<br>
import org.junit.Test;<br>
<br>
public class ArrayDividerTest {<br>
	@Test<br>
	public void testDivide() {<br>
		int[] divideds = new int[] { 0, 10, -30, Integer.MAX_VALUE };<br>
		int[] dividers = new int[] { 0, 10, -30, Integer.MAX_VALUE };<br>
		new ArrayDivider().divide(divideds, dividers);<br>
	}<br>
}<br>
</code></pre>

<p>
After we converted the project and run it... oops, an exception occured. The <code>Divider</code>'s contract is broken, and this error is easier to trace with the source code of the test project. </p>

<p>
The complete sample project could be found <a href='http://jtoc.googlecode.com/files/jtoc-quick-sample.zip'>here</a> with a sample ant build file.</p>

<p>
This is the Quick Start over the jtoc, to know more and to customize yoour own contracts, please read jtoc's <a href='http://code.google.com/p/jtoc/wiki/cookbook'>cookbook</a>. </p>
<br>
<br>
<hr WIDTH="100%"><br>
<br>

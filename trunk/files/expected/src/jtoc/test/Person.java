package jtoc.test;

import static org.junit.Assert.*;

import org.jtoc.InnerTest;
import org.jtoc.Post;
import org.jtoc.Pre;

public class Person {
	private String name;
	private int weight;

	// inner class initialized
	private PersonChecker innerTester = new PersonChecker();

	@Post(testObject="asd")
	public Person(String n) {
		//innerTester.PersonPreCheck(n);
		name = n;
		weight = 0;
		//innerTester.PersonPostCheck(n);
	}
	
	public void test(){}

	@Deprecated
	@SuppressWarnings(value = { "" })
	public void addKgs(int kgs){
		inner.addKgsPreCheck(kgs);
		//innerTester.addKgsPreCheck(kgs);
		weight += kgs;
		//innerTester.addKgsPostCheck(kgs);
		innerTest.addKgsPostCheck();
	}

	public int getWeight() {
		innerTest.getWeightPreCheck();
		innerTester.memberChecking();
		int returnValue = weight;
		innerTester.getWeightPostCheck(returnValue);
		return weight;
	}

	/* ... */

	private class PersonChecker {
		public void memberChecking() {
			assertTrue(name != null && name.length() > 0 && weight >= 0);
		}

		public void PersonPreCheck(String n) {
			assertTrue(name == null && weight == 0);
			assertTrue(n != null && n.length() > 0); // parameters checking
		}

		public void PersonPostCheck(String n) {
			assertEquals(name, n);
			assertEquals(weight, 0);
		}

		int oldWeight;

		public void addKgsPreCheck(int kgs) {
			memberChecking();
			assertTrue(kgs >= 0); // parameters checking
			oldWeight = weight;
		}

		public void addKgsPostCheck(int kgs) {
			memberChecking();
			assertEquals(weight, oldWeight + kgs);
		}

		public void getWeightPostCheck(int returnValue) {
			assertEquals(weight, returnValue);
		}
	}

	PersonChecker innerTest = new PersonChecker();
}

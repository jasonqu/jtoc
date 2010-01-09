package junit.samples.money;

import org.junit.Test;
import junit.framework.TestCase;

public class JtocMoneyTestAuto extends TestCase {
	@Test(timeout = 2000)
	public void testSimpleMultiply() {
		Money m = new Money(12, "CHF");
		int k = 0;
		for (int i = 1; k < 30; i *= 2, k++){
			System.out.print(k + " i : "+i);
			System.out.println(" Money : "+m.multiply(i).toString());
		}
		System.out.println(k);
	}
}

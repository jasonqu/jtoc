package jtoc.test;

import static org.junit.Assert.*;

import org.jtoc.*;

@InnerTest
public class Money{
	private final int fAmount;
	private final String fCurrency;
	public Money(int amount, String currency) {
		fAmount= amount;
		fCurrency= currency;
	}
	@Post
	public Money multiply(int factor) {
		return new Money(fAmount*factor, fCurrency);
	}
	@Post
	public Money negate() {
		return new Money(-fAmount, fCurrency);
	}
	@Pre(testObject = "InnerTestStatic")
	@Post(testObject = "InnerTestStatic")
	public static Money getMoney(int amount, String currency){
		return new Money(amount, currency);
	}

	@SuppressWarnings("unused")
	private class InnerTest {
		public Money multiplyPostCheck(Money result, int factor){
			assertEquals(fCurrency, result.fCurrency);
			assertEquals(fAmount*factor, result.fAmount);
			return result;
		}
		public Money negatePostCheck(Money result){
			return multiplyPostCheck(result, -1);
		}
	}
	static class InnerTestStatic{
		public static void getMoneyPreCheck(int amount, String currency) {
			assertNotNull(currency);
		}
		public static Money getMoneyPostCheck(Money result, int amount, String currency) {
			assertEquals(currency, result.fCurrency);
			assertEquals(amount, result.fAmount);
			return result;
		}
	}
}
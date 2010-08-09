package junit.samples.money;

import static org.junit.Assert.*;

import org.jtoc.InnerTest;
import org.jtoc.Post;
import org.jtoc.Pre;

/**
 * A simple Money.
 *
 */
public class Money implements IMoney {

	private final int fAmount;
	private final String fCurrency;

	/**
	 * Constructs a money from the given amount and currency.
	 */
	public Money(int amount, String currency) {
		fAmount= amount;
		fCurrency= currency;
	}
	/**
	 * Adds a money to this money. Forwards the request to the addMoney helper.
	 */
	public IMoney add(IMoney m) {
		innerTest.testNull(m);
		return m.addMoney(this);
	}
	public IMoney addMoney(Money m) {
		innerTest.testNull(m);
		if (m.currency().equals(currency()) )
			return innerTest.addMoneyPostCheck(( new Money(amount()+m.amount(), currency())), m);
		return innerTest.addMoneyPostCheck(( MoneyBag.create(this, m)), m);
	}
	public IMoney addMoneyBag(MoneyBag s) {
		innerTest.testNull(s);
		return s.addMoney(this);
	}
	public int amount() {
		return fAmount;
	}
	public String currency() {
		return fCurrency;
	}
	@Override
	//@Pre(testMethod = "testNull")
	public boolean equals(Object anObject) {
		if (isZero()) 
			if (anObject instanceof IMoney)
				return innerTest.equalsPostCheck(( ((IMoney)anObject).isZero()), anObject);
		if (anObject instanceof Money) {
			Money aMoney= (Money)anObject;
			return innerTest.equalsPostCheck(( aMoney.currency().equals(currency())
							 && amount() == aMoney.amount()), anObject);
		}
		return innerTest.equalsPostCheck(( false), anObject);
	}
	@Override
	public int hashCode() {
		if (fAmount == 0)
			return 0;
		return fCurrency.hashCode()+fAmount;
	}
	public boolean isZero() {
		return amount() == 0;
	}
	public IMoney multiply(int factor) {
		innerTest.multiplyPreCheck(factor);
		return innerTest.multiplyPostCheck(( new Money(amount()*factor, currency())), factor);
	}
	public IMoney negate() {
		return innerTest.negatePostCheck(( new Money(-amount(), currency())));
	}
	//@Post
	public IMoney subtract(IMoney m) {
		innerTest.testNull(m);
		return add(m.negate());
	}
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("["+amount()+" "+currency()+"]");
		return innerTest.toStringPostCheck(( buffer.toString()));
	}
	public /*this makes no sense*/ void appendTo(MoneyBag m) {
		m.appendMoney(this);
	}
	public static Money getMoney(int amount, String currency){
		return InnerTestStatic.getMoneyPostCheck(( new Money(amount, currency)), amount, currency);
	}
	
	@SuppressWarnings("unused")
	private class InnerTest {
		public void testNull(final Object obj){
			assertNotNull(obj);
		}
		
		public IMoney addMoneyPostCheck(final IMoney result, final Money m){
			if(m.currency().equals(currency()))
				assertEquals(new Money(amount()+m.amount(), currency()), result);
			else
				assertEquals(MoneyBag.create(Money.this, m), result);
			return result;
		}
		
		public boolean equalsPostCheck(final boolean result, Object obj) {
			if (isZero() && obj instanceof IMoney)
				assertEquals(((IMoney) obj).isZero(), result);
			else if (obj instanceof Money) {
				Money aMoney = (Money) obj;
				assertEquals(aMoney.currency().equals(currency())
						&& amount() == aMoney.amount(), result);
			} else
				assertEquals(false, result);
			return result;
		}
		
		int oldAmountValForMultiply = 0;
		public void multiplyPreCheck(int factor){
			oldAmountValForMultiply = amount();
		}
		
		public IMoney multiplyPostCheck(Money result, int factor){
			assertEquals(currency(), result.currency());
			assertEquals(amount()*factor, result.amount());
			assertTrue((factor > 0 && result.amount() >= oldAmountValForMultiply)
					|| (factor < 0 && result.amount() <= oldAmountValForMultiply)
					|| (factor == 0 && result.amount() == 0));
			return result;
		}
		
		public IMoney negatePostCheck(Money result){
			return multiplyPostCheck(result, -1);
		}
		
		public String toStringPostCheck(String result){
			assertEquals("["+amount()+" "+currency()+"]", result);
			return result;
		}
	}
	
	static class InnerTestStatic{
		public static Money getMoneyPostCheck(Money result, int amount,
				String currency) {
			assertEquals(currency, result.currency());
			assertEquals(amount, result.amount());
			return result;
		}
	}

	InnerTest innerTest = new InnerTest();
}

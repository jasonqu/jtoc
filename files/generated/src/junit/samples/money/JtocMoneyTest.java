package junit.samples.money;

import junit.framework.TestCase;

public class JtocMoneyTest extends TestCase {
	private int[] ints = new int[]{-1,0,1,10,-10,Integer.MAX_VALUE,Integer.MIN_VALUE};
	private Money[] moneys = new Money[]{new Money(12, "CHF"),new Money(14, "CHF"),
			new Money( 7, "USD"),new Money(21, "USD"),null};
	private IMoney[] imoneys = new IMoney[]{MoneyBag.create(moneys[0], moneys[2]),
			MoneyBag.create(moneys[1], moneys[3]),
			null};
	private int testcase = 0;
	@Override
	protected void setUp() {
		testcase = 0;
	}
	@Override
	protected void tearDown() {
		System.out.println(testcase);
	}
	public void testIsZero() {
		for(IMoney mon : moneys){
			if(mon==null) continue;
			assertTrue(mon.subtract(mon).isZero());
			testcase++;
		}
		for(IMoney mon : imoneys){
			if(mon==null) continue;
			assertTrue(mon.subtract(mon).isZero());
			testcase++;
		}
	}
	public void testMixedSimpleAdd() {
		for(IMoney mon1 : moneys)
			for(IMoney mon2 : imoneys)
			{
				if(mon1==null) continue;
				mon1.add(mon2);
				if(mon2==null) continue;
				mon2.add(mon1);
				testcase+=2;
			}
	}
	public void testEquals() {
		for(IMoney mon1 : moneys)
			for(IMoney mon2 : imoneys)
			{
				if(mon1==null) continue;
				mon1.equals(mon1);
				mon1.equals(mon2);
				if(mon2==null) continue;
				mon2.equals(mon2);
				mon2.equals(mon1);
				testcase+=4;
			}
	}
	public void testSimpleMultiply() {
		for (IMoney mon1 : moneys)
			for (int k : ints) {
				if(mon1==null) continue;
				mon1.multiply(k);
				testcase ++;
			}
		for (IMoney mon2 : imoneys)
			for (int k : ints) {
				if(mon2==null) continue;
				mon2.multiply(k);
				testcase ++;
			}
	}
}

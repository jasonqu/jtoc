package org.jtoc;

import junit.framework.TestCase;

import org.jtoc.Assert;
import org.jtoc.PreConditionException;
import org.junit.Test;

/**
 * @author GoddamnedQu
 *
 */
public class ConditionExceptionTest extends TestCase {

	/**
	 * Test method for {@link org.jtoc.PreConditionException#getMessage()}.
	 */
	@Test
	public void testGetMessage() {
		PreConditionException pce = new PreConditionException("i > 0", -1);
		assertEquals("expected condition \"i > 0\", but actual value was \"-1\""
				, pce.getMessage());
		assertEquals("i > 0", pce.getCondition());
		assertEquals("-1", pce.getActualValue());
	}
	
	/**
	 * Test method for {@link org.jtoc.Assert#assertConditionSatisfied()}.
	 */
	@Test
	public void testAssertConditionSatisfied() {
		try {
			int i = -1;
			Assert.assertConditionSatisfied("i > 0", i > 0, i);
		} catch (PreConditionException pce) {
			assertEquals("expected condition \"i > 0\", but actual value was \"-1\""
					, pce.getMessage());
			assertEquals("i > 0", pce.getCondition());
			assertEquals("-1", pce.getActualValue());
		}
	}

}

package cs414.midterm.yqiu;

import junit.framework.TestCase;

public class RationalTest extends TestCase {

	public void testSimpleMultiply() {
		Rational rat1 = new Rational(1, 2);
		Rational rat2 = new Rational(3, 5);
		Rational result1 = rat1.multiply(rat2);
		Rational result2 = rat2.multiply(rat1);
		assertEquals(result1,result2);
		assertTrue(result1.equals(new Rational(3,10)));
	}
	
	public void testMultiplyNeedReduce(){
		Rational rat1 = new Rational(8,9);
		Rational rat2 = new Rational(3,4);
		Rational result1 = rat1.multiply(rat2);
		Rational result2 = rat2.multiply(rat1);
		assertEquals(result1, result2);
		assertTrue(result1.equals(new Rational(2,3)));
	}
	
	public void testMultiplyZero(){
		Rational rat1 = new Rational(2,3);
		Rational rat2 = new Rational(0,2);
		Rational result1 = rat1.multiply(rat2);
		Rational result2 = rat2.multiply(rat1);
		assertEquals(result1, result2);
		assertTrue(result1.equals(new Rational(0,6)));
		assertTrue(result1.equals(new Rational(0,5)));
	}

	public void testMultplyNegative(){
		Rational rat1 = new Rational(-4, 9);
		Rational rat2 = new Rational(2, 5);
		Rational result1 = rat1.multiply(rat2);
		Rational result2 = rat2.multiply(rat1);
		assertEquals(result1, result2);
		assertTrue(result1.equals(new Rational(-8, 45)));
		assertTrue(result1.equals(new Rational(8,-45)));
	}
	
	public void testSimpleReciprocal() {
		Rational rat = new Rational(2,3);
		Rational result = rat.reciprocal();
		assertEquals(result, new Rational(3,2));
	}
	
	public void testReciprocalZero(){
		Rational rat = new Rational(0,2);
		try{
			Rational result = rat.reciprocal();
			assertTrue(!result.equals(new Rational(2,0)));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}

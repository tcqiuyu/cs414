package cs414.a1.yqiu;

import junit.framework.TestCase;

import org.junit.Test;

public class QualificationTest extends TestCase {

	@Test
	public void testDescription() {
		Qualification testQual = new Qualification("test qual");
		assertEquals("test qual", testQual.description());
	}

	public void testToString() {
		Qualification testQual = new Qualification("test");
		assertEquals("test", testQual.toString());
	}

}

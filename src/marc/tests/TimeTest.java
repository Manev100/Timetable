package marc.tests;

import static org.junit.Assert.*;
import marc.ItemStuff.Time;

import org.junit.Test;

public class TimeTest {

	@Test
	public void isEarlierTest() {
		Time testTime1 = new Time("1000");
		Time testTime2 = new Time("1000");
		Time testTime3 = new Time("1001");
		Time testTime4 = new Time("0959");
		Time testTime5 = new Time("0000");
		Time testTime6 = new Time("2359");
		assertFalse("", testTime1.isEarlier(testTime2));
		assertTrue("", testTime1.isEarlier(testTime3));
		assertFalse("", testTime3.isEarlier(testTime1));
		assertTrue("", testTime4.isEarlier(testTime1));
		assertTrue("", testTime5.isEarlier(testTime1));
		assertTrue("", testTime1.isEarlier(testTime6));
		
	}
	@Test
	public void isLaterTest() {
		Time testTime1 = new Time("1000");
		Time testTime2 = new Time("1000");
		Time testTime3 = new Time("1001");
		Time testTime4 = new Time("0959");
		Time testTime5 = new Time("0000");
		Time testTime6 = new Time("2359");
		assertFalse("", testTime1.isLater(testTime2));
		assertFalse("", testTime1.isLater(testTime3));
		assertTrue("", testTime3.isLater(testTime1));
		assertFalse("", testTime4.isLater(testTime1));
		assertFalse("", testTime5.isLater(testTime1));
		assertFalse("", testTime1.isLater(testTime6));
		
	}
	
	@Test
	public void tickTest() {
		Time testTime1 = new Time("0000");
		for(int i = 0; i< (24*60);i++){
			testTime1.tick();
			//System.out.println(testTime1.getTime());
		}
		
	}
	

}

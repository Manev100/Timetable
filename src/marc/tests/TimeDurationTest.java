package marc.tests;

import static org.junit.Assert.*;
import marc.ItemStuff.TimeDuration;

import org.junit.Test;

public class TimeDurationTest {

	
	@Test
	public void overlappingTest(){
		TimeDuration testDuration1 = new TimeDuration("0000","2359");
		TimeDuration testDuration2 = new TimeDuration("0800","0900");
		TimeDuration testDuration3 = new TimeDuration("0000","2359");
		TimeDuration testDuration4 = new TimeDuration("0900","1000");
		TimeDuration testDuration5 = new TimeDuration("1000","1100");
		TimeDuration testDuration6 = new TimeDuration("0800","1000");
		TimeDuration testDuration7 = new TimeDuration("0800","0900");
		assertTrue("",testDuration1.overlaps(testDuration2));
		assertTrue("",testDuration1.overlaps(testDuration3));
		assertTrue("",testDuration3.overlaps(testDuration1));
		assertTrue("",testDuration1.overlaps(testDuration4));
		assertTrue("",testDuration1.overlaps(testDuration5));
		assertTrue("",testDuration1.overlaps(testDuration6));
		assertTrue("",testDuration6.overlaps(testDuration1));

	}
	@Test
	public void overlappingDirectionTest(){
		TimeDuration testDuration1 = new TimeDuration("0700","0800");
		TimeDuration testDuration2 = new TimeDuration("0800","0900");
		TimeDuration testDuration3 = new TimeDuration("0000","2359");
		TimeDuration testDuration4 = new TimeDuration("0900","1000");
		TimeDuration testDuration5 = new TimeDuration("1000","1100");
		TimeDuration testDuration6 = new TimeDuration("0800","1000");
		TimeDuration testDuration7 = new TimeDuration("0730","0900");
		TimeDuration testDuration8 = new TimeDuration("0600","0730");
		TimeDuration testDuration9 = new TimeDuration("0700","1000");
		TimeDuration testDuration10 = new TimeDuration("0600","0800");
		TimeDuration testDuration11 = new TimeDuration("0800","0900");
		TimeDuration testDuration12 = new TimeDuration("0800","1000");

		assertTrue("",testDuration1.getOverlappingDirection(testDuration2) == TimeDuration.NOT_CONNECTED_PERIOD);
		assertTrue("",testDuration1.getOverlappingDirection(testDuration3) == TimeDuration.OUTER_PERIOD);
		assertTrue("",testDuration3.getOverlappingDirection(testDuration1) == TimeDuration.INNER_PERIOD);
		assertTrue("",testDuration1.getOverlappingDirection(testDuration4) == TimeDuration.NOT_CONNECTED_PERIOD);
		assertTrue("",testDuration1.getOverlappingDirection(testDuration5) == TimeDuration.NOT_CONNECTED_PERIOD);
		assertTrue("",testDuration1.getOverlappingDirection(testDuration6) == TimeDuration.NOT_CONNECTED_PERIOD);
		assertTrue("",testDuration6.getOverlappingDirection(testDuration1) == TimeDuration.NOT_CONNECTED_PERIOD);
		assertTrue("",testDuration1.getOverlappingDirection(testDuration7) == TimeDuration.OVERLAPPING_PERIOD_LATER);
		assertTrue("",testDuration7.getOverlappingDirection(testDuration1) == TimeDuration.OVERLAPPING_PERIOD_EARLIER);
		assertTrue("",testDuration1.getOverlappingDirection(testDuration8) == TimeDuration.OVERLAPPING_PERIOD_EARLIER);
		assertTrue("",testDuration1.getOverlappingDirection(testDuration9) == TimeDuration.OUTER_PERIOD);
		assertTrue("",testDuration1.getOverlappingDirection(testDuration10) == TimeDuration.OUTER_PERIOD);
		assertTrue("",testDuration12.getOverlappingDirection(testDuration11) == TimeDuration.INNER_PERIOD);
	}
	
	

}

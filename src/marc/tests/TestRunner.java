package marc.tests;


import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import marc.ItemStuff.Time;
import marc.ItemStuff.TimeDuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class TestRunner  {
  private TimeDuration period1;
  private TimeDuration period2;
  private Time time1;
  private Time time2;
  
  public TestRunner(TimeDuration testParameter1,TimeDuration testParameter2, Time t1, Time t2) {
    this.period1 = testParameter1;
    this.period2 = testParameter2;
    this.time1 = t1;
    this.time2 = t2;
    
  }

  // Creates the test data
  @Parameters
  public static Collection<Object[]> data() {
	  	Time testTime1 = new Time("1000");
		Time testTime2 = new Time("1000");
		Time testTime3 = new Time("1001");
		Time testTime4 = new Time("0959");
		Time testTime5 = new Time("0000");
		Time testTime6 = new Time("2359");
	  	TimeDuration testDuration1 = new TimeDuration("0700","0800");
		TimeDuration testDuration2 = new TimeDuration("0800","0900");
		TimeDuration testDuration3 = new TimeDuration("0000","2359");
		TimeDuration testDuration4 = new TimeDuration("0900","1000");
		TimeDuration testDuration5 = new TimeDuration("0800","1100");
		TimeDuration testDuration6 = new TimeDuration("0500","0700");
		TimeDuration testDuration7 = new TimeDuration("0700","0800");
		Object[][] data = new Object[][] { { testDuration1, testDuration2, testTime1, testTime2 }, 
				{ testDuration2, testDuration1,testTime1, testTime6  }, 
				{ testDuration1, testDuration3,testTime1, testTime1  },
				{ testDuration1, testDuration4,testTime1, testTime3  },
				{ testDuration1, testDuration5,testTime1, testTime4  },
				{ testDuration1, testDuration6,testTime1, testTime5  }, 
				{ testDuration1, testDuration7,testTime1, testTime4  }
		};
		return Arrays.asList(data);
  }



  @Test
  public void testOverlappingMethod() {
    assertTrue("Result", period1.overlaps(period2));

  }
  @Test
  public void testIsEarlierMethod() {
    assertTrue("Result", time1.isEarlier(time2));

  }
  @Test
  public void testIsLaterMethod() {
    assertTrue("Result", time1.isLater(time2));
  }
  @Test
  public void testOverlappingDirectionMethod() {
    assertEquals("Result " + period1.getOverlappingDirection(period2), period1.getOverlappingDirection(period2), 4);
  }
  
}
 
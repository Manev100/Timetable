package marc.ItemStuff;

public class TimeDuration implements Comparable<TimeDuration>{
	private Time from;
	private Time to;
	public static final int OUTER_PERIOD = 0;
	public static final int INNER_PERIOD = 1;
	public static final int OVERLAPPING_PERIOD_EARLIER = 2;
	public static final int OVERLAPPING_PERIOD_LATER = 3;
	public static final int NOT_CONNECTED_PERIOD = 4;
	public static final int SAME_PERIOD = 5;
	
	public TimeDuration(Time f, Time t) {
		this.from = f;
		this.to = t;
	}
	
	public TimeDuration(String f, String t){
		this.from = new Time(f);
		this.to = new Time(t);
	}
	public Time getFromTime(){
		return from;
	}
	
	public Time getToTime(){
		return to;
	}
	
	@Override
	public String toString(){
		String str = "from " + from.getFormattedTime() + " to " + to.getFormattedTime();
		return str;
	}
	
	public String getFormattedTimeDuration(){
		return from.getFormattedTime() + " - " + to.getFormattedTime();
	}
	
	public boolean equals(TimeDuration period){
		if(period == null){
			return false;
		}else{
			return from.equals(period.getFromTime()) && to.equals(period.getToTime());
		}
	}
	
	public boolean overlaps(TimeDuration period){
		int overlappingDirection = getOverlappingDirection(period);
		return !(overlappingDirection == NOT_CONNECTED_PERIOD);
	}
	
	// relation of this to period 0800-0900 and 0900-1000 are not connected
	/*
	 * possible constallation:
	 * relation to this
	 * otherfrom	l		l		l		i		i		r
	 * otherto		l		i		r		i		r		r
	 * 				not	 overl.el	out		in	overl la	not
	 * 
	 */
	public int getOverlappingDirection(TimeDuration period){
		Time otherFrom = period.getFromTime();
		Time otherTo = period.getToTime();
		if(otherTo.equals(from )|| otherFrom.equals(to)){
			return NOT_CONNECTED_PERIOD;
		}else if(equals(period)){
			return SAME_PERIOD;
		}else if(otherFrom.isEarlierOrEqual(from) && otherTo.isLaterOrEqual(to)){
			return OUTER_PERIOD;
		}else if(contains(otherFrom) && contains(otherTo)){
			return INNER_PERIOD;
		}else if(otherFrom.isEarlierOrEqual(from) && otherTo.isEarlierOrEqual(from)){
			return NOT_CONNECTED_PERIOD;
		}else if(otherFrom.isEarlierOrEqual(from) && contains(otherTo)){
			return OVERLAPPING_PERIOD_EARLIER;
		}else if(contains(otherFrom) && otherTo.isLaterOrEqual(to)){
			return OVERLAPPING_PERIOD_LATER;
		}else if(otherFrom.isLaterOrEqual(to) && otherTo.isLaterOrEqual(to)){
			return NOT_CONNECTED_PERIOD;
		}else{
			throw new IllegalArgumentException("Something went wrong in method getOverlappingDirection in class TimeDuration!");
		}
		
	}
	
	public boolean contains(Time time){
		return time.isLaterOrEqual(from) && time.isEarlierOrEqual(to);
	}
	
	public TimeDuration cut(Time time){
		if(contains(time)){
			TimeDuration newTimeDuration =  new TimeDuration(from,time);
			from = new Time(time.getTime());
			
			return newTimeDuration;
		}else{
			throw new IllegalArgumentException("TimeDuration can only be cut at a point it contains!");
		}
	}
	
	// messed up. works only for not connected periods. Used to sort the times list
	@Override
	public int compareTo(TimeDuration other) {
		int left = this.from.compareTo(other.getFromTime());
		int right = this.to.compareTo(other.getToTime());
		if(left+right>= 1){
			return 1;
		} else if(left+right == 0){
			return 0;
		}else{
			return -1;
		}
		
	}
}

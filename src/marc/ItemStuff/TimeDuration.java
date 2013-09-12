package marc.ItemStuff;

public class TimeDuration {
	private Time from;
	private Time to;
	public static final int OUTER_PERIOD = 0;
	public static final int INNER_PERIOD = 1;
	public static final int OVERLAPPING_PERIOD_EARLIER = 2;
	public static final int OVERLAPPING_PERIOD_LATER = 3;
	
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
		return from.equals(period.getFromTime()) && to.equals(period.getToTime());
	}
	
	public boolean overlaps(TimeDuration period){
		Time otherFrom = period.getFromTime();
		Time otherTo = period.getToTime();
		return (otherFrom.isLater(from) && otherFrom.isEarlier(to)) || (otherTo.isLater(from) && otherTo.isEarlier(to));
	}
	
	public int getOverlappingDirection(TimeDuration period){
		Time otherFrom = period.getFromTime();
		Time otherTo = period.getToTime();
		boolean leftLimit = (otherFrom.isLater(from) && otherFrom.isEarlier(to));
		boolean rightLimit = (otherTo.isLater(from) && otherTo.isEarlier(to));
		if(leftLimit && rightLimit){
			return INNER_PERIOD;
		}else if(leftLimit && !rightLimit){
			return OVERLAPPING_PERIOD_LATER;
		}else if(!leftLimit && rightLimit){
			return OVERLAPPING_PERIOD_EARLIER;
		}else if(!leftLimit && !rightLimit){
			return OUTER_PERIOD;
		}
		return 0;
	}
}

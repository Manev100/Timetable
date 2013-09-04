package marc.ItemStuff;

public class TimeDuration {
	private Time from;
	private Time to;
	
	
	public TimeDuration(Time f, Time t) {
		this.from = f;
		this.to = t;
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
}

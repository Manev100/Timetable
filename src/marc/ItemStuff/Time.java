package marc.ItemStuff;

public class Time {
	private String time;
	private int timeInt;
	
	public Time(String t) {
		
		validateTime(t);
		this.time = t;
	}

	private void validateTime(String t) {
		try{
			timeInt = Integer.parseInt(t);
		}catch(NumberFormatException e){
			throw new IllegalArgumentException("Input("+ t + ") Time-String must be a valid time 0000-2359");
		}
		
		if(Integer.parseInt(t.substring(2,4)) >= 60){
			throw new IllegalArgumentException("Input("+ t + ") Time must be a valid time, one hour lasts 60 seconds!");
		}
		if((Integer.parseInt(t.substring(0,1)) == 2 &&  Integer.parseInt(t.substring(1,2)) > 3) || Integer.parseInt(t.substring(0,1)) >2){
			throw new IllegalArgumentException("Input("+ t + ") must be a valid time, 23 is the last hour!");
		}
	}
	
	public int getTimeInInt(){
		return timeInt;
	}
	public String getTime(){
		return time;
	}
	
	public String getFormattedTime(){
		return time.substring(0, 2) + ":" + time.substring(2, 4);
	}
}

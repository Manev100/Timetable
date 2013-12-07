package marc.ItemStuff;

public class Time implements Comparable<Time>{
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
	public boolean equals(Time time2){
		return (timeInt == time2.getTimeInInt());
	}
	
	public boolean isEarlier(Time time2){
		return timeInt < time2.getTimeInInt();
	}
	
	public boolean isLater(Time time2){
		return timeInt > time2.getTimeInInt();
	}
	
	public boolean isEarlierOrEqual(Time time2){
		return timeInt <= time2.getTimeInInt();
	}
	
	public boolean isLaterOrEqual(Time time2){
		return timeInt >= time2.getTimeInInt();
	}
	
	public void tick(){
		char hour1 = time.charAt(0);
		char hour2 = time.charAt(1);
		char min1 = time.charAt(2);
		char min2 = time.charAt(3);
		if(time.equals("2359")){
			time = "0000";
			validateTime(time);
		}else if(time.endsWith("59")){
			timeInt += 41;
			time = intToTimeString(timeInt);
		}else{
			timeInt += 1;
			time = intToTimeString(timeInt);
		}
	}
	
	private String intToTimeString(int i){
		if(i < 10){
			return "000" + i;
		}else if(i < 100){
			return "00" + i;
		}else if(i < 1000){
			return "0" + i;
		}else{
			return "" + i;
		}
	}

	public int compareTo(Time other) {
		if(this.isEarlier(other)){
			return -1;
		}else if(this.equals(other)){
			return 0;
		} else{
			return 1;
		}
	}
}

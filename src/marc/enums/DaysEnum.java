package marc.enums;

public enum DaysEnum {
	monday("Monday",1), tuesday( "Tuesday",2), wednesday("Wednesday",3),
	thursday("Thursday",4),friday("Friday",5), saturday("Saturday",6), sunday("Sunday",7);
	
	private final String name;
	private final int number;
	
	private DaysEnum(String s, int n){
		this.name = s;
		this.number = n;
	}
	public String getName(){
		return name;	
	}
	public int getNumber(){
		return number;
	}
}

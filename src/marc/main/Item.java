package marc.main;

import java.util.LinkedList;

import marc.enums.DaysEnum;

public class Item {
	
	private String name;
	private String location;
	private Time from;
	private Time to;
	private String day;
	private final int ATTRIBUTECOUNT = 5;
	private LinkedList<TimeDuration> times;
	
	
	public Item(String name, String location, String day, TimeDuration... time) {
		this.name = name;
		this.location = location;
		this.day = day;
		times = new LinkedList<TimeDuration>();
		for(TimeDuration t: time){
			times.add(t);
		}
		
	}
	
	public Item(String name, String location, Time from, Time to, String day) {
		this(name, location, day, new TimeDuration(from,to));
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Time getFrom() {
		return from;
	}

	public void setFrom(Time from) {
		this.from = from;
	}

	public Time getTo() {
		return to;
	}

	public void setTo(Time to) {
		this.to = to;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
	public LinkedList<TimeDuration> getTimes(){
			return times;
	}
	
	public String[] getAttributesAsArray(){
		String[] content = new String[ATTRIBUTECOUNT];
		content[0] = name;
		content[1] = from.getFormattedTime();
		content[2] = to.getFormattedTime();
		content[3] = day;
		content[4] = location;
		return content;
	}
	
}

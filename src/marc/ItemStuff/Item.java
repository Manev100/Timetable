package marc.ItemStuff;

import java.util.LinkedList;

import marc.enums.DaysEnum;

public class Item {
	
	private String name;
	private String location;
	private Time from;
	private Time to;
	private String day;
	private final int ATTRIBUTECOUNT = 5;
	private LinkedList<ItemDate> dates;
	
	
	public Item(String name, ItemDate... dates) {
		this.name = name;
		this.dates = new LinkedList<ItemDate>();
		for(ItemDate t: dates){
			this.dates.add(t);
		}	
	}
	
	
	public Item(String name, String location, Time from, Time to, String day) {
		this(name,  new ItemDate(new TimeDuration(from,to),day,location));
		this.from = from;
		this.to = to;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	
	public LinkedList<ItemDate> getDates(){
			return dates;
	}
	
	public ItemDate[] getTimesAsArray(){
		return (ItemDate[])dates.toArray();
	}
	
	public ItemDate getTimeDurationAt(int index){
		return dates.get(index);
	}
	
	//doesnt work anymore :(
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

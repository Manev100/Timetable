package marc.main;

import java.awt.List;
import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.Observable;

import javax.swing.ListModel;

import marc.ItemStuff.Item;
import marc.ItemStuff.ItemDate;
import marc.ItemStuff.Time;
import marc.ItemStuff.TimeDuration;
import marc.enums.Days;
import marc.enums.DaysEnum;

public class TimetableModel extends Observable{
	private int columnCount;
	private int rowCount;
	private LinkedList<Item> items;
	private LinkedList<TimeDuration> times;
	private static final String[] LIST_COLUMN_NAMES = {"Name", "from","to","Day", "Location"};
	private static final String[] DEFAULT_TIME_DURATIONS = {"08:00 - 09:00", "09:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00", "12:00 - 13:00" ,"13:00 - 14:00","14:00 - 15:00","15:00 - 16:00"};
	private String[] columnNames = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday","Friday", "Saturday", "Sunday"};
	private String[] tableTimes;

	private static final String FIRST_LIST_ENTRY = "New Period";
	public static final int ADD_TIMES_MODE_CUT_EXISTING_PERIODS = 1;
	public static final int ADD_TIMES_MODE_CUT_NEW_PERIOD = 0;
	private int cutMode;
	
	
	public TimetableModel(){
		refreshColumnCount();
		refreshRowCount();
		items = new LinkedList<Item>();
		times = getDefaultTimes();
		cutMode = ADD_TIMES_MODE_CUT_EXISTING_PERIODS;
		
		tableTimes = DEFAULT_TIME_DURATIONS;
		
		//test
		items.add(new Item("Item 1", "here", new Time("0800"), new Time("0900"), "Monday"));
		items.add(new Item("Item 2", "here", new Time("0800"), new Time("0900"), "Tuesday"));
		items.add(new Item("Item 3", "here", new Time("0800"), new Time("0900"), "Saturday"));
		items.add(new Item("Item 4", "here", new Time("0800"), new Time("0900"), "Sunday"));
		items.add(new Item("Item 5", "here", new Time("0800"), new Time("0900"), "Thursday"));
		items.add(new Item("Item 6", "here", new Time("0900"), new Time("1000"), "Monday"));
		items.add(new Item("Item 7", "here", new Time("0900"), new Time("1000"), "Tuesday"));
		items.add(new Item("Item 8", "here", new Time("0900"), new Time("1000"), "Saturday"));
		items.add(new Item("Item 9", "here", new Time("0900"), new Time("1000"), "Sunday"));
		items.add(new Item("Item 10", "here", new Time("0900"), new Time("1000"), "Thursday"));
		items.add(new Item("Item 11", "here", new Time("1300"), new Time("1400"), "Monday"));
		items.add(new Item("Item 12", "here", new Time("1300"), new Time("1400"), "Tuesday"));
		items.add(new Item("Item 13", "here", new Time("1300"), new Time("1400"), "Saturday"));
		items.add(new Item("Item 14", "here", new Time("1300"), new Time("1400"), "Sunday"));
		items.add(new Item("Item 15", "here", new Time("1300"), new Time("1400"), "Thursday"));
	}
	
	

	private LinkedList<TimeDuration> getDefaultTimes() {
		LinkedList<TimeDuration> timesList = new LinkedList<TimeDuration>();
		timesList.add(new TimeDuration("0800","0900"));
		timesList.add(new TimeDuration("0900","1000"));
		timesList.add(new TimeDuration("1000","1100"));
		timesList.add(new TimeDuration("1100","1200"));
		timesList.add(new TimeDuration("1200","1300"));
		timesList.add(new TimeDuration("1300","1400"));
		timesList.add(new TimeDuration("1400","1500"));
		timesList.add(new TimeDuration("1500","1600"));
		return timesList;
	}



	public LinkedList<String> getActiveColumnNames(){
		LinkedList<String>  colNames = new LinkedList<String>();
		colNames.add(columnNames[0]);
		EnumSet<DaysEnum> days = EnumSet.allOf(DaysEnum.class);
		for(DaysEnum day:days){
			if(Days.isActive(day)){
				colNames.add(day.getName());
			}
		}
		return colNames;
	}
	
	public LinkedList<String> getActiveDays(){
		LinkedList<String>  activeDays = getActiveColumnNames();
		activeDays.remove(0);
		return activeDays;
	}
	
	public String[][] getContent(){
		String[][] content = new String[rowCount][columnCount];
		return content;
	}
	
	public String[][] getItemsAsArray(){
		if(items.size() == 0){
			return new String[][]{{" "},{" "},{" "},{" "},{" "}};
		}
		String[][] content = new String[items.size()][LIST_COLUMN_NAMES.length];
		int i = 0;
		for(Item it: items){
			content[i++] = it.getAttributesAsArray();
		}
		return content;
	}
	
	
	
	public void setDayActive(DaysEnum day,boolean b){
		Days.setActive(day, b);
		refreshColumnCount();
	}
	
	
	public int getColumnCount(){
		return columnCount;
	}
	
	public int getRowCount() {
		return rowCount;
	}
	public void addItem(Item item){
		items.add(item);
		setChanged();
		notifyObservers(items);
	}



	public LinkedList<Item> getItemsList() {
		return items;
	}
	
	public int getItemCount(){
		return items.size();
	}

	public void deleteItem(Item item) {
		items.remove(item);
		setChanged();
		notifyObservers(items);
	}

	public void deleteItems(int[] ItemsToDelete) {
		for(int i:ItemsToDelete){
			items.remove(i);
		}
		setChanged();
		notifyObservers(items);
	}
	public void deleteItems(Item[] ItemsToDelete) {
		for(Item i:ItemsToDelete){
			items.remove(i);
		}	
		setChanged();
		notifyObservers(items);
	}
	public Item getItem(String name){
		Item item = null;
		for(Item i: items){
			if(i.getName().equals(name)){
				item = i;
			}
		}
		return item;
	}
	
	public boolean isNameTaken(String name){
		for(Item i: items){
			if(i.getName().equals(name)){
				return true;
			}
		}
		return false;
	}

	public String[] getItemsNames() {
		String[] itemNames = new String[items.size()];
		int i = 0; 
		for(Item item:items){
			itemNames[i] = item.getName();
			i++;
		}
		return itemNames;
	}



	public String[][] getTableContent() {
		String[][] content = new String[rowCount][columnCount];
		for(int i = 0; i< rowCount;i++){
			content[i][0] = times.get(i).getFormattedTimeDuration();
		}
		for(Item item: items){
			ItemDate date = item.getFocusDate();
		    //do smth
		}
		/*LinkedList<Point>[] itemPositions = (LinkedList<Point>[]) new LinkedList[items.size()];
		for(Item item: items){
			Point[] positions = getPossibleTablePosition(item);
			itemPositions.add(new LinkedList<Point>(positions));
		
		}
			Point position = getPerfectPosition(positions);
			if(content[position.x][position.y] == null){
				content[position.x][position.y] = item.getName();
			}else{
				// fire collision algorithm
			}
		}*/
		return content;
	}


	public String[] getTimesAsString(){
		String[] timesString = new String[times.size()];
		int index = 0;
		for(TimeDuration td: times){
			timesString[index++] = td.getFormattedTimeDuration();
		}
		return timesString;
	}
	
	public String[] getTimesListString(){
		LinkedList<String> timeStrings = new LinkedList<String>();
		timeStrings.add(FIRST_LIST_ENTRY);
		timeStrings.addAll(Arrays.asList(getTimesAsString()));
		return timeStrings.toArray(new String[0]);
	}
	
	public LinkedList<TimeDuration> getTimes(){
		return times;
	}
	
	public TimeDuration getTime(String string) {
		for(TimeDuration period: times){
			if(period.getFormattedTimeDuration().equals(string)){
				return period;
			}
		}
		throw new IllegalArgumentException("'string' must be in the Format 'XXXX - XXXX' and an existing timeduration!");
	}
	
	private void refreshColumnCount(){
		columnCount = 1+(Days.isActive(DaysEnum.sunday) ? 1:0) + ((Days.isActive(DaysEnum.saturday)) ? 1:0) + 
				(Days.isActive(DaysEnum.friday) ? 1:0) +(Days.isActive(DaysEnum.thursday) ? 1:0)+ (Days.isActive(DaysEnum.wednesday) ? 1:0)+
				(Days.isActive(DaysEnum.tuesday) ? 1:0) + (Days.isActive(DaysEnum.monday) ? 1:0);
	}
	
	
	// NOT FINAL, should be connected to Times-Array
	private void refreshRowCount() {
		rowCount = 8;
		
	}
	
	private Point[] getPossibleTablePosition(Item item) {
		Point[] positions = new Point[item.getDates().size()];
		int i = 0;
		for(ItemDate date: item.getDates()){
			// Time??? row??? 
			positions[i++] = new Point(1,Days.getDaysEnum(date.getDay()).getNumber()+1);
		}
		return positions;
	}



	public void deleteTime(TimeDuration timeToEdit) {
		if(timeToEdit != null){
			times.remove(timeToEdit);
			setChanged();
			notifyObservers(times);
		}
		
	}



	public void addTime(TimeDuration newPeriod) {
		// algorithm to sort it in
		LinkedList<TimeDuration> overlappingPeriods = new LinkedList<TimeDuration>();
		for(TimeDuration tiDu: times){
			if(tiDu.overlaps(newPeriod)){
				// doesnt work if from and to times are equal! what to do?
				overlappingPeriods.add(tiDu);
				System.out.println(tiDu);
			}
		}
		if(overlappingPeriods.size() == 0){
			times.add(newPeriod);
		}else{
			if(cutMode == ADD_TIMES_MODE_CUT_EXISTING_PERIODS){
				LinkedList<TimeDuration> periodsToDestroy = new LinkedList<TimeDuration>();
				for(TimeDuration tiDu: overlappingPeriods){
					int overlappingDirection = newPeriod.getOverlappingDirection(tiDu);
					switch(overlappingDirection){
						case TimeDuration.INNER_PERIOD:
							times.remove(tiDu);
							break;
						case TimeDuration.OUTER_PERIOD:
							//break tidu 2x
							times.add(tiDu.cut(newPeriod.getFromTime()));
							tiDu.cut(newPeriod.getToTime());
							break;
						case TimeDuration.OVERLAPPING_PERIOD_EARLIER:
							//break tidu left
							times.add(tiDu.cut(newPeriod.getFromTime()));
							times.remove(tiDu);
							break;
						case TimeDuration.OVERLAPPING_PERIOD_LATER:
							//break tidu right
							tiDu.cut(newPeriod.getToTime());
							break;
						case TimeDuration.SAME_PERIOD:
							times.remove(tiDu);
							break;
					}
					
					
				}
			}else if(cutMode == ADD_TIMES_MODE_CUT_NEW_PERIOD){
				for(TimeDuration tiDu: overlappingPeriods){
					int overlappingDirection = newPeriod.getOverlappingDirection(tiDu);
					switch(overlappingDirection){
						case TimeDuration.INNER_PERIOD:
							times.add(newPeriod.cut(tiDu.getFromTime()));
							//newPeriod.cut(tiDu.getFromTime());
							break;
						case TimeDuration.OUTER_PERIOD:
							newPeriod = null;
							break;
						case TimeDuration.OVERLAPPING_PERIOD_EARLIER:
							newPeriod.cut(tiDu.getToTime());
							break;
						case TimeDuration.OVERLAPPING_PERIOD_LATER:
							newPeriod = newPeriod.cut(tiDu.getFromTime());
							break;
						case TimeDuration.SAME_PERIOD:
							times.remove(tiDu);
							break;
					}
					
				}
			}else{
				throw new IllegalStateException("Choose a valid cut-Mode to run method `addTime`");
			}

			if(newPeriod != null){
				times.add(newPeriod);
			}
			cutNullTimeDurations();
			Collections.sort(times);
			setChanged();
			notifyObservers(times);
		}	
	}

	private void cutNullTimeDurations(){
		LinkedList<TimeDuration> timesToDelete = new LinkedList<TimeDuration>();
		for(TimeDuration period: times){
			if(period.getFromTime().equals(period.getToTime())){
				timesToDelete.add(period);
			}
		}
		times.removeAll(timesToDelete);
	}
	
	public boolean isColliding(TimeDuration newPeriod) {
		LinkedList<TimeDuration> overlappingPeriods = new LinkedList<TimeDuration>();
		for(TimeDuration tiDu: times){
			if(tiDu.overlaps(newPeriod)){
				overlappingPeriods.add(tiDu);
				System.out.println(tiDu + "asdasd");
			}
		}
		if(overlappingPeriods.size() == 0){
			return false;
		}else{
			return true;
		}
	}
	
	public void setCutMode(int mode){
		cutMode = mode;
	}
	
	// debug method
	public void printTimes(){
		System.out.println(times.toString());
	}

}

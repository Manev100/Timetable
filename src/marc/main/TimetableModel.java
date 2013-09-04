package marc.main;

import java.awt.Point;
import java.util.EnumSet;
import java.util.LinkedList;

import javax.swing.ListModel;

import marc.ItemStuff.Item;
import marc.ItemStuff.ItemDate;
import marc.ItemStuff.Time;
import marc.enums.Days;
import marc.enums.DaysEnum;

public class TimetableModel {
	private int columnCount;
	private int rowCount;
	private LinkedList<Item> items;
	private static final String[] LIST_COLUMN_NAMES = {"Name", "from","to","Day", "Location"};
	private static final String[] DEFAULT_TIME_DURATIONS = {"08:00 - 09:00", "09:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00", "12:00 - 13:00" ,"13:00 - 14:00","14:00 - 15:00","15:00 - 16:00"};
	private String[] columnNames = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday","Friday", "Saturday", "Sunday"};
	
	public TimetableModel(){
		refreshColumnCount();
		refreshRowCount();
		items = new LinkedList<Item>();
		
		//test
		items.add(new Item("Item 1", "here", new Time("1111"), new Time("2222"), "Monday"));
		items.add(new Item("Item 2", "here", new Time("1111"), new Time("2222"), "Tuesday"));
		items.add(new Item("Item 3", "here", new Time("1111"), new Time("2222"), "Saturday"));
		items.add(new Item("Item 4", "here", new Time("1111"), new Time("2222"), "Sunday"));
		items.add(new Item("Item 5", "here", new Time("1111"), new Time("2222"), "Thursday"));
		items.add(new Item("Item 6", "here", new Time("0000"), new Time("0050"), "Monday"));
		items.add(new Item("Item 7", "here", new Time("0000"), new Time("0050"), "Tuesday"));
		items.add(new Item("Item 8", "here", new Time("0000"), new Time("0050"), "Saturday"));
		items.add(new Item("Item 9", "here", new Time("0000"), new Time("0050"), "Sunday"));
		items.add(new Item("Item 10", "here", new Time("0000"), new Time("0050"), "Thursday"));
		items.add(new Item("Item 11", "here", new Time("0051"), new Time("1110"), "Monday"));
		items.add(new Item("Item 12", "here", new Time("0051"), new Time("1110"), "Tuesday"));
		items.add(new Item("Item 13", "here", new Time("0051"), new Time("1110"), "Saturday"));
		items.add(new Item("Item 14", "here", new Time("0051"), new Time("1110"), "Sunday"));
		items.add(new Item("Item 15", "here", new Time("0051"), new Time("1110"), "Thursday"));
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
	
	private void refreshColumnCount(){
		columnCount = 1+(Days.isActive(DaysEnum.sunday) ? 1:0) + ((Days.isActive(DaysEnum.saturday)) ? 1:0) + 
				(Days.isActive(DaysEnum.friday) ? 1:0) +(Days.isActive(DaysEnum.thursday) ? 1:0)+ (Days.isActive(DaysEnum.wednesday) ? 1:0)+
				(Days.isActive(DaysEnum.tuesday) ? 1:0) + (Days.isActive(DaysEnum.monday) ? 1:0);
	}
	
	
	// NOT FINAL, should be connected to Times-Array
	private void refreshRowCount() {
		rowCount = 8;
		
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
	}



	public LinkedList<Item> getItemsList() {
		return items;
	}
	
	public int getItemCount(){
		return items.size();
	}

	public void deleteItem(Item item) {
		items.remove(item);
	}

	public void deleteItems(int[] ItemsToDelete) {
		for(int i:ItemsToDelete){
			items.remove(i);
		}	
	}
	public void deleteItems(Item[] ItemsToDelete) {
		for(Item i:ItemsToDelete){
			items.remove(i);
		}	
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
			content[i][0] = DEFAULT_TIME_DURATIONS[i];
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



	private Point[] getPossibleTablePosition(Item item) {
		Point[] positions = new Point[item.getDates().size()];
		int i = 0;
		for(ItemDate date: item.getDates()){
			// Time??? row??? 
			positions[i++] = new Point(1,Days.getDaysEnum(date.getDay()).getNumber()+1);
		}
		return positions;
	}

}

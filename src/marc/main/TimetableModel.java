package marc.main;

import java.util.EnumSet;
import java.util.LinkedList;

import marc.enums.Days;
import marc.enums.DaysEnum;

public class TimetableModel {
	private int columnCount;
	private int rowCount;
	private LinkedList<Item> items;
	private static final String[] LIST_COLUMN_NAMES = {"Name", "from","to","Day", "Location"};
	private String[] columnNames = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday","Friday", "Saturday", "Sunday"};
	
	public TimetableModel(){
		refreshColumnCount();
		refreshRowCount();
		items = new LinkedList<Item>();
		
		//test
		items.add(new Item("Item 1", "Monday", new Time("2222"), new Time("1111"), "here"));
		items.add(new Item("Item 2", "Monday", new Time("2222"), new Time("1111"), "here"));
		items.add(new Item("Item 3", "Monday", new Time("2222"), new Time("1111"), "here"));
		items.add(new Item("Item 4", "Monday", new Time("2222"), new Time("1111"), "here"));
		items.add(new Item("Item 5", "Monday", new Time("2222"), new Time("1111"), "here"));
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



	public void deleteItems(int[] ItemsToDelete) {
		for(int i:ItemsToDelete){
			items.remove(i);
		}	
	}
}

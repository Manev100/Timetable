package marc.main;

import java.util.EnumSet;
import java.util.LinkedList;

import marc.enums.Days;
import marc.enums.DaysEnum;

public class TimetableModel {
	private int columnCount;
	private int rowCount;


	private String[] columnNames = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday","Friday", "Saturday", "Sunday"};
	
	public TimetableModel(){
		refreshColumnCount();
		refreshRowCount();
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
}

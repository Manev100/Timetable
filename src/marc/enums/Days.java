package marc.enums;

public class Days {
	
	private static boolean[] active = {true,true,true,true,true,true,true};
	
	public static void setActive(DaysEnum day,boolean act){
		active[day.getNumber()-1] = act;
	}
	
	public static boolean isActive(DaysEnum day){
		return active[day.getNumber()-1];
	}
	
	public static DaysEnum getDaysEnum(String day){
		DaysEnum[] days = DaysEnum.values();
		for(DaysEnum dayEnum: days){
			if(day.equals(dayEnum.getName())){
				return dayEnum;
			}
		}
		throw new IllegalArgumentException("Input must be a valid (capitalized) weekday!");
		
	}
	
}

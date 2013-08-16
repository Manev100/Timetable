package marc.ItemStuff;

public class ItemDate {
	private TimeDuration time;
	private String day;
	private String location;
	
	public ItemDate(TimeDuration time, String day, String location) {
		this.time = time;
		this.location = location;
		this.day = day;
	}

	public TimeDuration getTime() {
		return time;
	}

	public void setTime(TimeDuration time) {
		this.time = time;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}

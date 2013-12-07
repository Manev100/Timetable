package marc.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import marc.ItemStuff.ItemDate;
import marc.ItemStuff.Time;
import marc.ItemStuff.TimeDuration;
import marc.enums.Days;

public class DatePanel extends JPanel {


	private TimetableModel model;
	private JComboBox<String> dayBox;
	private TimeField toField;
	private TimeField fromField;
	private JTextField locationField;
	private JComboBox<TimeDuration> timeBox;
	
	public DatePanel(String title, TimetableModel m) {
		super(new BorderLayout());
		
		this.model = m;
		
		TitledBorder tit = BorderFactory.createTitledBorder(title);
		setBorder(tit);
		
		JPanel dayPanel = createDayPanel();
		JPanel timePanel = createTimePanel();
		JPanel locationPanel = createLocationPanel();
			
		add(dayPanel, BorderLayout.NORTH);
		add(timePanel, BorderLayout.CENTER);
		add(locationPanel, BorderLayout.SOUTH);
		
	}
	
	private JPanel createLocationPanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Location");
		locationField = new JTextField(20);
		
		panel.add(label, BorderLayout.WEST);
		panel.add(locationField, BorderLayout.CENTER);
		return panel;
	}

	private JPanel createTimePanel() {
		JPanel panel = new JPanel(new FlowLayout());
		
		timeBox = new JComboBox<TimeDuration>(model.getTimes().toArray(new TimeDuration[0]));
		
		panel.add(timeBox);
		/*
		toField = new TimeField();
		fromField = new TimeField();
		
		panel.add(new JLabel("from: "),FlowLayout.LEFT);
		panel.add(fromField, FlowLayout.CENTER);
		panel.add(toField, FlowLayout.RIGHT);
		panel.add(new JLabel("to: "),FlowLayout.RIGHT);
		*/
		
		return panel;
	}

	
	private JPanel createDayPanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Day: ");
		dayBox = new JComboBox<String>(model.getActiveDays().toArray(new String[0]));
		
		panel.add(label, BorderLayout.WEST);
		panel.add(dayBox, BorderLayout.EAST);
		
		return panel;
	}

	
	
	
	public void clear(){
		toField.reset();
		fromField.reset();
		locationField.setText("");
		dayBox.setSelectedIndex(0);
		
	}
	
	public boolean verifyValues(){
		/*
		boolean viable = true;
		int fromTime = fromField.timeInInteger();
		int toTime = toField.timeInInteger();
		if(toTime == -1 || fromTime == -1 || fromTime >= toTime){
			viable = false;
		}
		return viable;
		*/
		return true;
	}
	
	
	
	public ItemDate getTimeDate(){
		/*
		 * if(verifyValues()){
			TimeDuration time = new TimeDuration(new Time(fromField.getTime()), new Time(toField.getTime()));
			ItemDate date = new ItemDate(time, (String) dayBox.getSelectedItem(), locationField.getText());
			return date;
		}else{
			return null;
		}
		*/
		return new ItemDate((TimeDuration)timeBox.getSelectedItem(), (String) dayBox.getSelectedItem(),locationField.getText());
	}
	
	public JComboBox<?> getDayBox() {
		return dayBox;
	}

	public TimeField getToField() {
		return toField;
	}

	public TimeField getFromField() {
		return fromField;
	}

	public JTextField getLocationField() {
		return locationField;
	}
	
	public void fill(ItemDate date){
		/*
		fromField.setTime(date.getTime().getFromTime());
		toField.setTime(date.getTime().getToTime());
		*/
		timeBox.setSelectedItem(model.getTime(date.getTime().getFormattedTimeDuration()));
		locationField.setText(date.getLocation());
		dayBox.setSelectedIndex(Days.getDaysEnum(date.getDay()).getNumber()-1);
	}
	
	
}

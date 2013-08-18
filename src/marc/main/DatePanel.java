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

public class DatePanel extends JPanel {


	private TimetableModel model;
	private JComboBox<?> dayBox;
	private TimeField toField;
	private TimeField fromField;
	private JTextField locationField;
	
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
		toField = new TimeField();
		fromField = new TimeField();
		
		panel.add(new JLabel("from: "),FlowLayout.LEFT);
		panel.add(fromField, FlowLayout.CENTER);
		panel.add(toField, FlowLayout.RIGHT);
		panel.add(new JLabel("to: "),FlowLayout.RIGHT);
		
		
		return panel;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JPanel createDayPanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Day: ");
		
		
		dayBox = new JComboBox(model.getActiveDays().toArray());
		
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
	
	public boolean verifyValues(){
		boolean viable = true;
		int fromTime = fromField.timeInInteger();
		int toTime = toField.timeInInteger();
		if(toTime == -1 || fromTime == -1 || fromTime >= toTime){
			viable = false;
		}
		return viable;

	}
}

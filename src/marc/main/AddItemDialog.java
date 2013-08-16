package marc.main;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import marc.ItemStuff.Item;
import marc.ItemStuff.ItemDate;
import marc.ItemStuff.Time;
import marc.ItemStuff.TimeDuration;
import marc.enums.DaysEnum;

public class AddItemDialog extends JDialog {
	private TimetableModel model;
	private JFrame parent;
	
	private JLabel infoLabel;
	private JTextField nameField;
	private JComboBox<?> dayBox;
	private TimeField toField;
	private TimeField fromField;
	private JTextField locationField;
	private JButton addButton;
	private JButton cancelButton;
	private Item item;
	private JButton addDateButton;
	private JPanel datesPanel;
	private LinkedList<TimeField> fromTimes;
	private LinkedList<TimeField> toTimes;
	private LinkedList<JTextField> locationFields;
	private LinkedList<JComboBox> dayFields;
	private int numberOfDates;
	
	
	
	public AddItemDialog(JFrame aFrame, TimetableModel m){
		super(aFrame,true);
		this.parent = aFrame;
		
		numberOfDates = 1;
		fromTimes = new LinkedList<TimeField>();
		toTimes = new LinkedList<TimeField>();
		locationFields = new LinkedList<JTextField>();
		dayFields = new LinkedList<JComboBox>();
		
		this.item = null;
		this.model = m;
		JPanel panel = new JPanel(new BorderLayout()); 
		
		panel.add(createInputMask(), BorderLayout.NORTH);
		panel.add(createButtonPanel(), BorderLayout.CENTER);
		panel.add(createInfoField(), BorderLayout.SOUTH);
		
		setContentPane(panel);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		// what to do on close
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                clearAndHide();
        }
    });
	}

	private JLabel createInfoField() {
		
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		infoLabel = new JLabel("Fill out and click Add to add Item",JLabel.CENTER);
		infoLabel.setBorder(loweredetched);
		return infoLabel;
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		addButton = new JButton("Add");
		addButton.setFocusable(false);
		cancelButton = new JButton("Cancel");
		cancelButton.setFocusable(false);
		addDateButton = new JButton("More Dates?");
		addDateButton.setFocusable(false);
		
		
		
		//Add-Button pressed
		addButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(inputValidated()){
					ItemDate[] dates = new ItemDate[locationFields.size()];
					for(int i = 0; i<locationFields.size(); i++){
						TimeDuration time = new TimeDuration(new Time(fromTimes.get(i).getTime()), new Time(toTimes.get(i).getTime()));
						String day = dayFields.get(i).getName();
						String location = locationFields.get(i).getText();
						ItemDate date = new ItemDate(time, day, location);
						dates[i] = date;
					}
					item = new Item(nameField.getText(),dates);
					clearAndHide();
				}else{
					infoLabel.setText("Failure. Please enter valid Values to continue!");
				}
				
			}

		});
		

		addDateButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				numberOfDates++;
				datesPanel.add(createDatePanel());
				setLocationRelativeTo(parent);
				pack();
			}

		});
		
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				item = null;
				clearAndHide();
			}

		});
		
		panel.add(addDateButton, BorderLayout.CENTER);
		panel.add(addButton, BorderLayout.WEST);
		panel.add(cancelButton, BorderLayout.EAST);
		
		return panel;
	}

	private JPanel createInputMask() {
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel namePanel = createNamePanel();
		datesPanel = new JPanel(new GridLayout(0, 1));
		
		datesPanel.add(createDatePanel());
		
		panel.add(namePanel, BorderLayout.NORTH);
		panel.add(datesPanel, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel createDatePanel(){
		JPanel panel = new JPanel(new BorderLayout());
		
		TitledBorder title = BorderFactory.createTitledBorder("Date " + numberOfDates);
		panel.setBorder(title);
		
		JPanel dayPanel = createDayPanel();
		JPanel timePanel = createTimePanel();
		JPanel locationPanel = createLocationPanel();
			
		panel.add(dayPanel, BorderLayout.NORTH);
		panel.add(timePanel, BorderLayout.CENTER);
		panel.add(locationPanel, BorderLayout.SOUTH);
		
		return panel;
	}

	private JPanel createLocationPanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Location");
		locationField = new JTextField(20);
		locationFields.add(locationField);
		
		panel.add(label, BorderLayout.WEST);
		panel.add(locationField, BorderLayout.CENTER);
		return panel;
	}

	private JPanel createTimePanel() {
		JPanel panel = new JPanel(new FlowLayout());
		toField = new TimeField();
		fromField = new TimeField();
		
		
		fromTimes.add(fromField);
		toTimes.add(toField);
		
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
		dayFields.add(dayBox);
		
		panel.add(label, BorderLayout.WEST);
		panel.add(dayBox, BorderLayout.EAST);
		
		return panel;
	}

	private JPanel createNamePanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Name");
		nameField = new JTextField(20);
		
		panel.add(label, BorderLayout.WEST);
		panel.add(nameField, BorderLayout.CENTER);
		return panel;
	}

	public Item getResult() {
		return item;
	}
	
	private void clearAndHide() {
		nameField.setText(null);
		
        datesPanel.removeAll();
        datesPanel.add(createDatePanel());
        
        infoLabel.setText("Fill out and click Add to add Item");
        
        setVisible(false);
    }
	
	private boolean inputValidated() {
		boolean viable = true;
		for(int i = 0; i<toTimes.size();i++){
			int fromTime = fromTimes.get(i).timeInInteger();
			int toTime = toTimes.get(i).timeInInteger();
			if(toTime == -1 || fromTime == -1 || fromTime >= toTime){
				viable = false;
			}
		}
		return viable;
		
	}
}

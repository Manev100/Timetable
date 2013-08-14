package marc.main;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import marc.enums.DaysEnum;

public class AddItemDialog extends JDialog {
	private TimetableModel model;
	
	private JLabel infoLabel;
	private JTextField nameField;
	private JComboBox<?> dayBox;
	private TimeField toField;
	private TimeField fromField;
	private JTextField locationField;
	private JButton addButton;
	private JButton cancelButton;
	private Item item; 
	
	public AddItemDialog(JFrame aFrame, TimetableModel m){
		super(aFrame,true);
		
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
		cancelButton = new JButton("Cancel");
		
		//Add-Button pressed
		addButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(inputValidated()){
					item = new Item(nameField.getText(),locationField.getText() ,new Time(fromField.getTime()),new Time(toField.getTime()),(String) dayBox.getSelectedItem());
					clearAndHide();
				}else{
					infoLabel.setText("Failure. Please enter valid Values to continue!");
				}
				
			}

		});
		
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				item = null;
				clearAndHide();
			}

		});
		
		panel.add(addButton, BorderLayout.WEST);
		panel.add(cancelButton, BorderLayout.EAST);
		
		return panel;
	}

	private JPanel createInputMask() {
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel namePanel = createNamePanel();
		JPanel dayPanel = createDayPanel();
		JPanel timePanel = createTimePanel();
		JPanel locationPanel = createLocationPanel();
		
		JPanel top = new JPanel(new BorderLayout());
		
		top.add(namePanel, BorderLayout.NORTH);
		top.add(dayPanel, BorderLayout.SOUTH);
		
		panel.add(top, BorderLayout.NORTH);
		panel.add(timePanel, BorderLayout.CENTER);
		panel.add(locationPanel, BorderLayout.SOUTH);
		
		return panel;
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
		locationField.setText(null);
		nameField.setText(null);
        toField.reset();
        fromField.reset();
        infoLabel.setText("Fill out and click Add to add Item");
        dayBox.setSelectedIndex(0);
        addButton.setFocusPainted(false);
        cancelButton.setFocusPainted(false);
        
        setVisible(false);
    }
	
	private boolean inputValidated() {
		int fromTime = fromField.timeInInteger();
		int toTime = toField.timeInInteger();
		if(toTime != -1 && fromTime != -1 && fromTime < toTime){
			return true;
		}
		else{
			return false;
		}
		
	}
}

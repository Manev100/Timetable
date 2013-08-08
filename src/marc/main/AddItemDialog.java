package marc.main;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class AddItemDialog extends JPanel {
	private TimetableModel model;
	
	private JLabel infoLabel;
	private JTextField nameField;
	private JComboBox<?> dayBox;
	private TimeField toField;
	private TimeField fromField;
	private JTextField locationField;
	private JButton addButton;
	private JButton cancelButton;
	
	
	public AddItemDialog(JFrame aFrame, String aWord, TimetableModel m){
		super(new BorderLayout());
		
		this.model = m;
		
		
		add(createInputMask(), BorderLayout.NORTH);
		add(createButtonPanel(), BorderLayout.CENTER);
		add(createInfoField(), BorderLayout.SOUTH);
		
		
	}

	private JLabel createInfoField() {
		
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		infoLabel = new JLabel("Click Add to add Item",JLabel.CENTER);
		infoLabel.setBorder(loweredetched);
		return infoLabel;
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		addButton = new JButton("Add");
		cancelButton = new JButton("Cancel");
		
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
	
	public static void main(String[] args){
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
            	JFrame frame = new JFrame("Test");
            	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
            	AddItemDialog cont = new AddItemDialog(frame, "a", new TimetableModel());
            	
		
            	frame.setContentPane(cont);
            	frame.pack();
            	frame.setVisible(true);
            }
        });
	}
}

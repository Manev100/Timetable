package marc.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import marc.ItemStuff.Item;

public class TimeEditPanel extends JPanel {
private JLabel infoLabel;
	
	private JTextField nameField;
	
	private JButton addButton;
	private JButton cancelButton;
	private JButton deleteDateButton;
	private JButton addDateButton;
	private Item item;
	private JPanel datesPanel;
	private LinkedList<DatePanel> datePanelsList;
	private int numberOfDates;
	private JDialog parent;
	private TimetableModel model;
	
	public TimeEditPanel(TimetableModel m, JDialog parent) {
		super(new BorderLayout());
		
		this.parent = parent;
		this.model = m;
		
		numberOfDates = 1;
		
		datePanelsList = new LinkedList<DatePanel>();
		datePanelsList.add(new DatePanel("Date 1", model));
		
		add(createInputMask(), BorderLayout.NORTH);
		add(createButtonPanel(), BorderLayout.CENTER);
		add(createInfoField(), BorderLayout.SOUTH);
		
	}

	private JPanel createInfoField() {
		JPanel panel = new JPanel(new BorderLayout());
		return panel;
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		return panel;
	}

	private JPanel createInputMask() {
		JPanel panel = new JPanel(new BorderLayout());
		return panel;
	}
}

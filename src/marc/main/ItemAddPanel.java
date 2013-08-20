package marc.main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import marc.ItemStuff.Item;
import marc.ItemStuff.ItemDate;

public class ItemAddPanel extends JPanel{
	
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
	
	public ItemAddPanel(TimetableModel m, JDialog parent) {
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
	
	
	private JLabel createInfoField() {
		
		
		infoLabel = new JLabel("Fill out and click Add to add Item",JLabel.CENTER);
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		infoLabel.setBorder(loweredetched);
		return infoLabel;
	}

	


	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel,BoxLayout.X_AXIS);
		panel.setLayout(layout);
		
		addButton = new JButton("Add");
		addButton.setFocusable(false);
		cancelButton = new JButton("Cancel");
		cancelButton.setFocusable(false);
		addDateButton = new JButton("More Dates?");
		addDateButton.setFocusable(false);
		deleteDateButton = new JButton("Less Dates?");
		deleteDateButton.setFocusable(false);
		
		addDateButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {

				numberOfDates++;
				DatePanel newDatePanel = new DatePanel("Date " + numberOfDates, model);
				datesPanel.add(newDatePanel);
				datePanelsList.add(newDatePanel);
				parent.setLocationRelativeTo(parent.getParent());
				parent.pack();
			}

		});
		
		deleteDateButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(numberOfDates>1){
					datesPanel.remove(datePanelsList.getLast());
					datePanelsList.removeLast();
					numberOfDates--;
					parent.pack();
				}else{
					infoLabel.setText("Item must have atleast one Date!");
				}
			}

		});
		
		panel.add(addButton);
		panel.add(addDateButton);
		panel.add(deleteDateButton);
		panel.add(cancelButton);
		
		
		return panel;
	}

	private JPanel createInputMask() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel namePanel = createNamePanel();
		
		datesPanel = new JPanel(new GridLayout(0,1));
		datesPanel.add(datePanelsList.get(0));
		
		panel.add(namePanel, BorderLayout.NORTH);
		panel.add(datesPanel, BorderLayout.SOUTH);
		
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
	
	
	public void addDate(){
		
	}
	
	public void deleteDate(){
		
	}
	
	public JTextField getNameField() {
		return nameField;
	}
	
	public JPanel getDatesPanel() {
		return datesPanel;
	}

	public LinkedList<DatePanel> getDatePanelsList() {
		return datePanelsList;
	}
	
	public JButton getAddButton() {
		return addButton;
	}


	public JButton getCancelButton() {
		return cancelButton;
	}

	
	public JButton getAddDateButton() {
		return addDateButton;
	}
	
	public JButton getDeleteDateButton() {
		return deleteDateButton;
	}

	public JLabel getInfoLabel() {
		return infoLabel;
	}


	public void clear() {
		nameField.setText(null);
        datesPanel.removeAll();
        datePanelsList.clear();
       
        numberOfDates = 1;
        DatePanel newDatePanel = new DatePanel("Date " + numberOfDates, model);
		datesPanel.add(newDatePanel);
		datePanelsList.add(newDatePanel);
		
        infoLabel.setText("Fill out and click Add to add Item");
		
	}
	
	public void fill(Item item){
		nameField.setText(item.getName());
		LinkedList<ItemDate> dates = item.getDates();
		
		int datePosition = 1;
		for(ItemDate date: dates){
			if(datePosition > numberOfDates){
				numberOfDates++;
				datePanelsList.add(new DatePanel("" + numberOfDates, model));
			}
			
			datePanelsList.get(datePosition-1).fill(date);
			
		}
		
	}
	
	public boolean inputValidated() {
		String name = nameField.getText();
		boolean viable = !name.equals("") && !model.isNameTaken(name);

		for(DatePanel panel: datePanelsList){
			if(!panel.verifyValues()){
				viable = false;
			}
		}
		return viable;
		
	}
}

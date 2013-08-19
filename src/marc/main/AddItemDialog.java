package marc.main;


import java.awt.BorderLayout;
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
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.BoxLayout;


import marc.ItemStuff.Item;
import marc.ItemStuff.ItemDate;


public class AddItemDialog extends JDialog {
	private TimetableModel model;
	private JFrame parent;
	
	private JLabel infoLabel;
	private JTextField nameField;
	
	private JButton addButton;
	private JButton cancelButton;
	private JButton deleteDateButton;
	private JButton addDateButton;
	private Item item;
	private JPanel datesPanel;
	
	
	private LinkedList<DatePanel> datePanels;
	private int numberOfDates;
	
	
	public AddItemDialog(JFrame aFrame, TimetableModel m){
		super(aFrame,true);
		this.parent = aFrame;
		
		this.item = null;
		this.model = m;
		
		
		numberOfDates = 1;
		datePanels = new LinkedList<DatePanel>();
		datePanels.add(new DatePanel("Date 1", model));
		
		
		
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
		
		
		//Add-Button pressed
		addButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(inputValidated()){
					LinkedList<ItemDate> dates = new LinkedList<ItemDate>();
					for(DatePanel panel : datePanels){
						dates.add(panel.getTimeDate());
					}
					item = new Item(nameField.getText(),dates);
					System.out.println(item.toString());
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
				DatePanel newDatePanel = new DatePanel("Date " + numberOfDates, model);
				datesPanel.add(newDatePanel);
				datePanels.add(newDatePanel);
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
		
		deleteDateButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(numberOfDates>1){
					datesPanel.remove(datePanels.getLast());
					datePanels.removeLast();
					numberOfDates--;
					pack();
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
		datesPanel.add(datePanels.get(0));
		
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

	

	public Item getResult() {
		return item;
	}
	
	private void clearAndHide() {
		nameField.setText(null);
        datesPanel.removeAll();
        datePanels.clear();
       
        numberOfDates = 1;
        DatePanel newDatePanel = new DatePanel("Date " + numberOfDates, model);
		datesPanel.add(newDatePanel);
		datePanels.add(newDatePanel);
		
		
        
        
        infoLabel.setText("Fill out and click Add to add Item");
        
        setVisible(false);
 
    }
	
	private boolean inputValidated() {
		boolean viable = !nameField.getText().equals("");

		for(DatePanel panel: datePanels){
			if(!panel.verifyValues()){
				viable = false;
			}
		}
		return viable;
		
	}
	
	
	public void fill(Item item){
		nameField.setText(item.getName());
		LinkedList<ItemDate> dates = item.getDates();
		
		int datePosition = 1;
		for(ItemDate date: dates){
			if(datePosition > numberOfDates){
				numberOfDates++;
				datePanels.add(new DatePanel("" + numberOfDates, model));
			}
			
			datePanels.get(--datePosition).fill(date);
			
		}
		
	}
	
	public JButton getAddButton() {
		return addButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JButton getDeleteDateButton() {
		return deleteDateButton;
	}

	public JButton getAddDateButton() {
		return addDateButton;
	}
}

package marc.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import marc.ItemStuff.Item;
import marc.ItemStuff.ItemDate;


public class EditItemDialog extends JDialog{
	private TimetableModel model;
	private JLabel info;
	private JList<String> itemsList;
	private JPanel selectionPanel;
	private JPanel editPanel;
	private JPanel mainPanel;
	private CardLayout cl;
	private final int MAX_ITEMS_DISPLAYED = 4;
	
	
	private JTextField nameField;
	
	private JButton backButton;
	private JButton addDateButton;
	private JButton deleteDateButton;
	private JButton editButton;
	
	private JLabel infoLabel;
	
	
	public EditItemDialog(JFrame aFrame, TimetableModel m) {
		super(aFrame,true);
		this.model = m;
		
		cl = new CardLayout();
		mainPanel = new JPanel(cl);
		
		selectionPanel = buildSelectionPanel();
		
		AddItemDialog helpItemDialog = new AddItemDialog(null, m);
		
		editPanel = (JPanel) helpItemDialog.getContentPane();
		//editPanel.get
		
		
		
		mainPanel.add(selectionPanel);
		mainPanel.add(editPanel);
		
		cl.first(mainPanel);
		
		setContentPane(mainPanel);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		// what to do on close
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
            	//deletedItems = null;
                clearAndHide();
            }
		});
		
	}
	
	
	
	private JPanel buildEditPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.add(createInputMask(), BorderLayout.NORTH);
		panel.add(createButtonPanel(), BorderLayout.CENTER);
		panel.add(createInfoField(), BorderLayout.SOUTH);
		
		
		return panel;
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
		
		editButton = new JButton("Edit");
		editButton.setFocusable(false);
		backButton = new JButton("Cancel");
		backButton.setFocusable(false);
		addDateButton = new JButton("More Dates?");
		addDateButton.setFocusable(false);
		deleteDateButton = new JButton("Less Dates?");
		deleteDateButton.setFocusable(false);
		
		
		//Add-Button pressed
		editButton.addActionListener(new ActionListener(){
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
		
		backButton.addActionListener(new ActionListener(){
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



	private JPanel buildSelectionPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		info = new JLabel("Delete oder edit Items");
		
		panel.add(buildItemPanel(), BorderLayout.NORTH);
		panel.add(buildButtons(),BorderLayout.CENTER);
		panel.add(info  , BorderLayout.SOUTH);
		
		return panel;
	}



	private JPanel buildItemPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		itemsList = new JList<String>(model.getItemsNames());
		if(model.getItemCount()<= MAX_ITEMS_DISPLAYED){
			itemsList.setVisibleRowCount(model.getItemCount());
		}else{
			itemsList.setVisibleRowCount(MAX_ITEMS_DISPLAYED);
		}
		
		JScrollPane scroll = new JScrollPane(itemsList);
		panel.add(scroll);
		
		return panel;
	}



	private JPanel buildButtons() {
		JPanel panel = new JPanel(new BorderLayout());
		JButton deleteButton = new JButton("Delete");
		deleteButton.setFocusable(false);
		deleteButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// delete marked items
				
			}
		});
		
		JButton continueButton = new JButton("Continue ->");
		continueButton.setFocusable(false);
		continueButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.last(mainPanel);
			}
		});
		
		panel.add(deleteButton, BorderLayout.WEST);
		panel.add(continueButton, BorderLayout.EAST);
		
		return panel;
	}
	
	public void refresh(){
		
		itemsList.setListData(model.getItemsNames());
		if(model.getItemCount()<= MAX_ITEMS_DISPLAYED){
			itemsList.setVisibleRowCount(model.getItemCount());
		}else{
			itemsList.setVisibleRowCount(MAX_ITEMS_DISPLAYED);
		}
	}
	
	public int[] getResult(){
		return null;
	}
	
	private void clearAndHide() {
        
		cl.first(mainPanel);
        setVisible(false);
    }
	
	
}

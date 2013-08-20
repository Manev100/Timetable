package marc.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

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
	private ItemAddPanel editPanel;
	private JPanel mainPanel;
	private CardLayout cl;
	private final int MAX_ITEMS_DISPLAYED = 4;
	private Item itemToEdit;
	
	private JTextField nameField;
	
	private JButton backButton;
	private JButton addDateButton;
	private JButton deleteDateButton;
	private JButton editButton;
	private Item selectedItem;
	
	private JLabel infoLabel;
	
	
	public EditItemDialog(JFrame aFrame, TimetableModel m) {
		super(aFrame,true);
		this.model = m;
		
		cl = new CardLayout();
		mainPanel = new JPanel(cl);
		
		selectionPanel = buildSelectionPanel();
		editPanel = new ItemAddPanel(model,this);
		
		setupEditButtons();
		
		
		mainPanel.add(selectionPanel);
		mainPanel.add(editPanel);
		
		cl.first(mainPanel);
		
		setContentPane(mainPanel);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		pack();
		// what to do on close
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
            	//deletedItems = null;
                clearAndHide();
            }
		});
		
	}
	
	
	
	private void setupEditButtons() {
		editPanel.getAddButton().setText("Edit");
		editPanel.getAddButton().setName("Edit");
		editPanel.getAddButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.deleteItem(itemToEdit);
				if(editPanel.inputValidated()){
					itemToEdit.setName(editPanel.getNameField().getText());
					itemToEdit.getDates().clear();
					LinkedList<ItemDate> dates = new LinkedList<ItemDate>();
					for(DatePanel panel : editPanel.getDatePanelsList()){
						dates.add(panel.getTimeDate());
					}
					
					itemToEdit.setDates(dates);
				
					System.out.println(itemToEdit.toString());
					model.addItem(itemToEdit);
					refresh();
					editPanel.clear();
					pack();
					cl.first(mainPanel);
				}else{
					model.addItem(itemToEdit);
					editPanel.getInfoLabel().setText("Failure. Please enter valid Values to continue!");
				}
			}
		});
		
		editPanel.getCancelButton().setText("Back");
		editPanel.getCancelButton().setName("Back");
		editPanel.getCancelButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editPanel.clear();
				pack();
				cl.first(mainPanel);
				
			}
		});
	}

	private JPanel buildSelectionPanel() {
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel,BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		
		info = new JLabel("Delete oder edit Items!", JLabel.CENTER);
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		info.setBorder(loweredetched);
		info.setHorizontalTextPosition(JLabel.CENTER);	
		
		panel.add(buildItemPanel());
		panel.add(buildButtons());
		panel.add(buildInfoPanel());
		
		return panel;
	}



	private Component buildInfoPanel() {
		JPanel panel = new JPanel();
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		panel.setBorder(loweredetched);
		
		info = new JLabel("Delete oder edit Items!", JLabel.CENTER);
		info.setHorizontalTextPosition(JLabel.CENTER);	
		
		panel.add(info);
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
		panel.setMaximumSize(new Dimension(2000,50));
		JButton deleteButton = new JButton("Delete");
		deleteButton.setFocusable(false);
		deleteButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				List<String> selectedItems = itemsList.getSelectedValuesList();
				if(selectedItems.size() != 0){
					LinkedList<Item> itemsList = model.getItemsList();
					LinkedList<Item> itemsToDelete = new LinkedList<Item>();
					for(String itemName: selectedItems){
						for(Item item: itemsList){
							if(item.getName().equals(itemName)){
								itemsToDelete.add(item);
							}
						}
					}
					model.deleteItems(itemsToDelete.toArray(new Item[0]));
					info.setText("Delete oder edit Items!");
					refresh();
				}else{
					info.setText("Please choose atleast one item to delete!");
				}
			}
		});
		
		JButton continueButton = new JButton("Continue ->");
		continueButton.setFocusable(false);
		continueButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<String> selectedItems = itemsList.getSelectedValuesList();
				if(!(selectedItems.size()==1)){
					info.setText("Please choose one item to continue!");
				}else{
					info.setText("Delete oder edit Items!");
					itemToEdit = model.getItem(selectedItems.get(0));
					editPanel.fill(itemToEdit);
					cl.last(mainPanel);
				}
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

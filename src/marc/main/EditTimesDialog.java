package marc.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import marc.ItemStuff.Item;
import marc.ItemStuff.ItemDate;
import marc.ItemStuff.Time;
import marc.ItemStuff.TimeDuration;

public class EditTimesDialog extends JDialog {
	private TimetableModel model;
	private JLabel info;
	private JList<String> itemsList;
	private JPanel selectionPanel;
	private JPanel editPanel;
	private JPanel mainPanel;
	private CardLayout cl;
	private final int MAX_ITEMS_DISPLAYED = 8;
	private TimeDuration timeToEdit;
	private static final String FIRST_LIST_ENTRY = "New Period";
	
	private JTextField nameField;
	
	private TimeField toField;
	private TimeField fromField;
	private JButton backButton;
	private JButton addDateButton;
	private JButton deleteDateButton;
	private JButton editButton;
	private Item selectedItem;
	
	private JLabel infoLabel;
	
	private static final int SELECTION_PANEL_SIZE_X = 250;
	private static final int SELECTION_PANEL_SIZE_Y = 232;
	private static final int EDIT_PANEL_SIZE_X = 250;
	private static final int EDIT_PANEL_SIZE_Y = 100;
	
	public EditTimesDialog(JFrame aFrame, TimetableModel m) {
		super(aFrame,true);
		this.model = m;
		
		setTitle("Edit Times");
		
		cl = new CardLayout();
		mainPanel = new JPanel(cl);
		
		selectionPanel = buildSelectionPanel();
		editPanel = buildTimeEditPanel();
		
		
		mainPanel.add(selectionPanel);
		mainPanel.add(editPanel);
		
		showSelectionPanel();
		
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
	
	private void showSelectionPanel(){
		setPreferredSize(new Dimension(SELECTION_PANEL_SIZE_X,SELECTION_PANEL_SIZE_Y));
		cl.first(mainPanel);
	}
	
	private void showEditPanel(){
		setPreferredSize(new Dimension(EDIT_PANEL_SIZE_X,EDIT_PANEL_SIZE_Y));
		cl.last(mainPanel);
	}
	
	
	private JPanel buildTimeEditPanel() {
		JPanel inputPanel = new JPanel();
		
		inputPanel.setLayout(new FlowLayout());

		
		toField = new TimeField();

		fromField = new TimeField();
		
		inputPanel.add(new JLabel("from: "),FlowLayout.LEFT);
		inputPanel.add(fromField, FlowLayout.CENTER);
		inputPanel.add(toField, FlowLayout.RIGHT);
		inputPanel.add(new JLabel("to: "),FlowLayout.RIGHT);
		
		
		
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.setMaximumSize(new Dimension(2000, 100));
		
		JButton editButton = new JButton("edit");
		editButton.setFocusable(false);
		editButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					Time fromTime = new Time(fromField.getTime());
					Time toTime = new Time(toField.getTime()); 
					if(fromTime.getTimeInInt() < toTime.getTimeInInt()){
						TimeDuration newPeriod = new TimeDuration(fromTime, toTime);
						if(!newPeriod.equals(timeToEdit)){
							model.deleteTime(timeToEdit);
							model.addTime(newPeriod);
						}
						refresh();
						showSelectionPanel();
						pack();
					}else{
						//error
					}
				}catch(IllegalArgumentException ex){
					// error
				}
				
			}
		});
		
		JButton backButton = new JButton("back");
		backButton.setFocusable(false);
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				toField.reset();
				fromField.reset();
				showSelectionPanel();
				pack();
			}
		});
		buttonPanel.add(editButton, BorderLayout.WEST);
		buttonPanel.add(backButton, BorderLayout.EAST);
		
		
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel,BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		panel.add(inputPanel);
		panel.add(buttonPanel);
		
		return panel;
	}
	
	
	private JPanel buildSelectionPanel() {
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel,BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		
		
		panel.add(buildItemPanel());
		panel.add(buildButtons());
		panel.add(buildInfoPanel());
		
		return panel;
	}



	private Component buildInfoPanel() {
		JPanel panel = new JPanel();
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		panel.setBorder(loweredetched);
		
		info = new JLabel("Delete or edit Time Periods!", JLabel.CENTER);
		info.setHorizontalTextPosition(JLabel.CENTER);	
		
		panel.add(info);
		return panel;
	}



	private JPanel buildItemPanel() {
		JPanel panel = new JPanel(new BorderLayout());
	
		itemsList = new JList<String>(model.getTimesListString());
		if(model.getRowCount()<= MAX_ITEMS_DISPLAYED){
			itemsList.setVisibleRowCount(model.getRowCount());
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
				if(selectedItems.size() != 0 && !(selectedItems.size() == 1 && selectedItems.get(0).equals(FIRST_LIST_ENTRY))){
					LinkedList<TimeDuration> timesList = model.getTimes();
					for(String period: selectedItems){
						TimeDuration periodToDelete = null; 
						for(TimeDuration time: timesList){
							if(time.getFormattedTimeDuration().equals(period)){
								periodToDelete = time;
							}
						}
						timesList.remove(periodToDelete);
					}
					info.setText("Delete or edit Time Periods!");
					refresh();
				}else{
					info.setText("Please choose atleast one time to delete!");
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
					info.setText("Please choose one time to continue!");
				}else{
					info.setText("Delete or edit Time Periods!");
					if(!selectedItems.get(0).equals(FIRST_LIST_ENTRY)){
						timeToEdit = model.getTime(selectedItems.get(0));
						toField.setTime(timeToEdit.getToTime());
						fromField.setTime(timeToEdit.getFromTime());
					}else{
						toField.reset();
						fromField.reset();
					}
					//editPanel.fill(itemToEdit);
					showEditPanel();
					pack();
				}
			}
		});
		
		panel.add(deleteButton, BorderLayout.WEST);
		panel.add(continueButton, BorderLayout.EAST);
		
		return panel;
	}
	
	public void refresh(){
		
		itemsList.setListData(model.getTimesListString());
		if(model.getRowCount()<= MAX_ITEMS_DISPLAYED){
			itemsList.setVisibleRowCount(model.getRowCount());
		}else{
			itemsList.setVisibleRowCount(MAX_ITEMS_DISPLAYED);
		}
	}
	
	public int[] getResult(){
		return null;
	}
	
	private void clearAndHide() {
        
		
        setVisible(false);
        showSelectionPanel();
    }
	
}

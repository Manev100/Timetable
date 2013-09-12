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
	private ItemAddPanel mainPanel;
	
	private LinkedList<DatePanel> datePanelsList;
	private int numberOfDates;
	
	
	public AddItemDialog(JFrame aFrame, TimetableModel m){
		super(aFrame,true);
		this.parent = aFrame;
		
		setTitle("Add Item");
		
		this.item = null;
		this.model = m;
		
		
		numberOfDates = 1;
		
		mainPanel = new ItemAddPanel(model, this);
		
		
		infoLabel = mainPanel.getInfoLabel();
		nameField = mainPanel.getNameField();
		datePanelsList = mainPanel.getDatePanelsList();
		addButton = mainPanel.getAddButton();
		cancelButton = mainPanel.getCancelButton();
		setActionListeners();
		
		setContentPane(mainPanel);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		// what to do on close
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                clearAndHide();
            }
		});
	}
	
	private void setActionListeners() {
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				item = null;
				clearAndHide();
			}

		});
		
		
		
		addButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(mainPanel.inputValidated()){
					LinkedList<ItemDate> dates = new LinkedList<ItemDate>();
					for(DatePanel panel : datePanelsList){
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
	}


	public Item getResult() {
		return item;
	}
	
	private void clearAndHide() {
		mainPanel.clear();
        setVisible(false);
 
    }
}

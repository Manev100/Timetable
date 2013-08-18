package marc.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;


public class EditItemDialog extends JDialog{
	private TimetableModel model;
	private JLabel info;
	private JList<String> itemsList;
	private JPanel selectionPanel;
	private JPanel editPanel;
	private JPanel mainPanel;
	private CardLayout cl;
	private final int MAX_ITEMS_DISPLAYED = 4;
	
	
	public EditItemDialog(JFrame aFrame, TimetableModel m) {
		super(aFrame,true);
		this.model = m;
		
		cl = new CardLayout();
		mainPanel = new JPanel(cl);
		
		selectionPanel = buildSelectionPanel();
		editPanel = buildEditPanel();
				
		mainPanel.add(new DatePanel("1", model));
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
	
	private void showSelection(){
		cl.first(mainPanel);
	}
	
	private void showEdit(){
		cl.last(mainPanel);
	}
	
	
	private JPanel buildEditPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
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
				showEdit();
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
        
        setVisible(false);
    }
}

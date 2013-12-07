package marc.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableModel;

import marc.ItemStuff.Item;
import marc.enums.DaysEnum;

public class TimetableView implements Observer{
	private JFrame frame;
	private final Dimension startSize = new Dimension(800,600);
	private TimetableModel model;
	private JTable table;
	private JMenuBar topBar;
	private AddItemDialog addItemDialog;
	private EditItemDialog editItemDialog;
	private EditTimesDialog editTimesDialog;
	
	
	public TimetableView(TimetableModel m) {
		this.frame = new JFrame("Timetable");
		setup();
		this.model = m;
		build();
		addItemDialog = new AddItemDialog(frame, m);
		addItemDialog.pack();
		
		editItemDialog = new EditItemDialog(frame, m);
		editItemDialog.pack();
		
		editTimesDialog = new EditTimesDialog(frame, m);
		editTimesDialog.pack(); 
		
		setTableContent(model.getTableContent());
		
	}
	
	public void setTableContent(String[][] content){
		TableModel tableModel = table.getModel();
		for(int row = 0; row<table.getRowCount();row++){
			for(int column = 0; column < table.getColumnCount(); column++ ){
				tableModel.setValueAt(content[row][column], row, column);
			}
		}
	}
	
	private void build() {
		JSplitPane p = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
		
		JScrollPane scrollPane = new JScrollPane(createTable(),ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		p.setBottomComponent(scrollPane);
		p.setTopComponent(createTopArea());
		
		
		p.setDividerSize(0);
		p.setEnabled(true);
		
		frame.add(p, BorderLayout.CENTER);
	}




	private JTable createTable() {
		LinkedList<String> columnNames = model.getActiveColumnNames();
		
		table = new JTable(new NotEditableTableModel(model.getContent(), columnNames.toArray()));
		
		
		table.setRowMargin(0);
		table.getColumnModel().setColumnMargin(0);
		//table.setRowHeight(77);
		table.setRowSelectionAllowed(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setBackground(Color.CYAN);
		table.getTableHeader().setReorderingAllowed(false);

		
		return table;
	}



	private JMenuBar createTopArea() {
		topBar = new JMenuBar();
		JMenuItem saveItem = new JMenuItem("Save");
		JMenuItem loadItem = new JMenuItem("Load");

		JMenu fileMenu = new JMenu("File");
		fileMenu.add(saveItem);
		fileMenu.add(loadItem);
		topBar.add(fileMenu);
		
		JMenuItem addPlayer = new JMenuItem("Add Item" );
		addPlayer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				addItemDialog.pack();
				addItemDialog.setLocationRelativeTo(frame);
				addItemDialog.setVisible(true);
				Item newItem = addItemDialog.getResult();
				if(newItem != null){
					model.addItem(newItem);
				}
			}
		});
		
		JMenuItem deletePlayer = new JMenuItem("Edit Item");
		deletePlayer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				editTimesDialog.pack();
				editItemDialog.setLocationRelativeTo(frame);
				editItemDialog.setVisible(true);
			}
		});
		
		JMenuItem editTimes = new JMenuItem("Edit Times");
		editTimes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				editTimesDialog.pack();
				editTimesDialog.setLocationRelativeTo(frame);
				editTimesDialog.setVisible(true);
			}
		});
		
		JSeparator sep = new JSeparator();
		JMenuItem editBracket = new JMenuItem("Preferences");
		JMenu edit = new JMenu("Edit");
		edit.add(addPlayer);
		edit.add(deletePlayer);
		edit.add(editTimes);
		edit.add(sep);
		edit.add(editBracket);
		
		topBar.add(edit);
		topBar.setVisible(true);
		return topBar;
	}



	private void setup() {
		frame.setSize(startSize);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setMinimumSize(new Dimension(400,200));
		frame.setExtendedState(JFrame.NORMAL);
		
	}
	
	public void show(){
		frame.setVisible(true);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 == model.getItemsList()){
			setTableContent(model.getTableContent());
			frame.pack();
		}else if(arg1 == model.getTimes()){
			setTableContent(model.getTableContent());
			frame.pack();
			
		}
		
	}
}

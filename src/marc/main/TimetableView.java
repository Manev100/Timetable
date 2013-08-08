package marc.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import marc.enums.DaysEnum;

public class TimetableView {
	private JFrame frame;
	private final Dimension startSize = new Dimension(800,600);
	private TimetableModel model;
	private JTable table;
	private JMenuBar topBar;
	
	public TimetableView(TimetableModel m) {
		this.frame = new JFrame("Timetable");
		setup();
		this.model = m;
		build();
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
		
		table = new JTable(model.getContent(),columnNames.toArray());
		
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
		
		JMenuItem addPlayer = new JMenuItem("Add Item");
		JMenuItem deletePlayer = new JMenuItem("Delete Item");
		JSeparator sep = new JSeparator();
		JMenuItem editBracket = new JMenuItem("Preferences");
		JMenu edit = new JMenu("Edit");
		edit.add(addPlayer);
		edit.add(deletePlayer);
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
}

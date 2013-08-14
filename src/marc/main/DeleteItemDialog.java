package marc.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

public class DeleteItemDialog extends JDialog {
	private TimetableModel model;
	private static final String[] LIST_COLUMN_NAMES = {"Name", "from","to","Day", "Location"};
	private JTable table;
	private final int TABLE_ROW_HEIGHT = 20;
	private final int DIALOG_WIDTH = 400;
	private final int MAX_VISIBLE_ROW_COUNT = 10;
	
	public DeleteItemDialog(JFrame aFrame, TimetableModel m) {
		super(aFrame,true);
		
		this.model = m;
		JPanel panel = new JPanel(new BorderLayout()); 
		
		panel.add(buildList(),BorderLayout.NORTH);
		panel.add(buildButtons(), BorderLayout.SOUTH);
		
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
	
	

	private JScrollPane buildList() {
		
		table = new JTable(new NotEditableTableModel(model.getItemsAsArray(), LIST_COLUMN_NAMES));
		
		table.setRowMargin(0);
		table.getColumnModel().setColumnMargin(0);
		table.setRowHeight(TABLE_ROW_HEIGHT);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setFocusable(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setBackground(Color.CYAN);
		table.getTableHeader().setReorderingAllowed(false);
		if(model.getItemCount() <= MAX_VISIBLE_ROW_COUNT){
			table.setPreferredScrollableViewportSize(new Dimension(DIALOG_WIDTH ,model.getItemCount()*TABLE_ROW_HEIGHT));
		} else{
			table.setPreferredScrollableViewportSize(new Dimension(DIALOG_WIDTH ,MAX_VISIBLE_ROW_COUNT*TABLE_ROW_HEIGHT));
		}
		JScrollPane scrollPane = new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		return scrollPane;
	}
	
	private JPanel buildButtons() {
		JPanel panel = new JPanel(new BorderLayout());
		JButton deleteButton = new JButton("Delete");
		JButton cancelButton = new JButton("Cancel");
		
		panel.add(deleteButton, BorderLayout.WEST);
		panel.add(cancelButton, BorderLayout.EAST);
		
		return panel;
	}

	private void refresh(){		
		boolean state = this.isVisible();
		setVisible(false);
		if(model.getItemCount() <= MAX_VISIBLE_ROW_COUNT){
			table.setPreferredScrollableViewportSize(new Dimension(DIALOG_WIDTH ,model.getItemCount()*TABLE_ROW_HEIGHT));
		} else{
			table.setPreferredScrollableViewportSize(new Dimension(DIALOG_WIDTH ,MAX_VISIBLE_ROW_COUNT*TABLE_ROW_HEIGHT));
		}
		table.setModel(new NotEditableTableModel(model.getItemsAsArray(), LIST_COLUMN_NAMES));
		setVisible(state);
	}
	
	private void clearAndHide() {
        
        setVisible(false);
    }

}

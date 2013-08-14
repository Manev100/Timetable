package marc.main;

import javax.swing.table.DefaultTableModel;

public class NotEditableTableModel extends DefaultTableModel {

	   public NotEditableTableModel(Object[][] tableData, Object[] colNames) {
	      super(tableData, colNames);
	   }

	   public boolean isCellEditable(int row, int column) {
	      return false;
	   }
	}

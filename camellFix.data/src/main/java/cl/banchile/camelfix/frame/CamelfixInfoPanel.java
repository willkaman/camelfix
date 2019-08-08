package cl.banchile.camelfix.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cl.banchile.camelfix.impl.session.CamelfixSession;

public class CamelfixInfoPanel extends JPanel {

	private static final long serialVersionUID = 1051174950410007802L;
	
	private JTable infoTable=null;
	private DefaultTableModel infoTableModel=null;
	Object[][]rowData = null;
	Object columnNames[] = { "name","value"};
	private JScrollPane sp=null;
	
	public CamelfixInfoPanel(CamelfixSession camelfixSession) {
		
		rowData = createRowData(camelfixSession);
		infoTableModel=new DefaultTableModel(rowData, columnNames);
		infoTable=new JTable(infoTableModel);

		infoTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		infoTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		infoTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		infoTable.setEnabled(Boolean.FALSE);
		//infoTable.setMaximumSize(new Dimension(160, 100));
		
		sp=new JScrollPane(infoTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(200, 100));
		
		this.add(sp,BorderLayout.CENTER);
	}
	
	private Object[][] createRowData(CamelfixSession camelfixSession){
		Object rowData[][] = { {"Fix ver.",camelfixSession.getSessison().getBeginString()},
				   {"Sender ID",camelfixSession.getSessison().getSenderCompID()},
				   {"Target ID",camelfixSession.getSessison().getTargetCompID()}
				 };
		return rowData;
	}
	
	/*public void updateRowData(CamelfixSession camelfixSession) {
		DefaultTableModel model=(DefaultTableModel) infoTable.getModel();
		model.setDataVector(createRowData(camelfixSession), columnNames);
		model.fireTableDataChanged();
	}*/
	
}

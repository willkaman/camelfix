package cl.banchile.camelfix.frame;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cl.banchile.camelfix.impl.session.CamelfixSession;
import quickfix.Message;

public class CamelfixMessagesPanel extends JPanel {

	private static final long serialVersionUID = 1051174950410007802L;
	
	private JTable infoTable=null;
	private DefaultTableModel infoTableModel=null;
	Object[][]rowData = null;
	Object columnNames[] = {"message"};
	private JScrollPane sp=null;
	
	public CamelfixMessagesPanel(CamelfixSession session) {
		this.setLayout(new GridLayout(1, 1));
		infoTableModel=new DefaultTableModel(columnNames,1);
		
		infoTable=new JTable(infoTableModel) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};

		infoTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		//infoTable.getColumnModel().getColumn(0).setPreferredWidth(200);
		infoTable.setEnabled(true);
		infoTable.setRowSelectionAllowed(true);
		
		
		infoTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
		        JTable table = (JTable)evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	System.out.println("doble");
		        	Object msgObject=(Message)table.getValueAt(table.getSelectedRow(),table.getSelectedColumn());
		        	if(msgObject instanceof Message) {
		        		new CamelfixMsgDetailFrame(session.getDataDictionary(),(Message)msgObject).setVisible(true);
		        	}
		        }
			}
		});
		
		sp=new JScrollPane(infoTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//sp.setPreferredSize(new Dimension(200, 100));
		
		this.add(sp);
	}
	
	private Object[][] createRowData(Object[] messsagesArray){
		Object rowData[][] = new Object[messsagesArray.length][1];
		
		for(int i=0;i<messsagesArray.length;i++) {
			rowData[i][0]=messsagesArray[i];
		}
		
		return rowData;
	}
	
	public void updateRowData(Object[] messsagesArray) {
		DefaultTableModel model=(DefaultTableModel) infoTable.getModel();
		model.setDataVector(createRowData(messsagesArray), columnNames);
		model.fireTableDataChanged();
	}
	
	public List<Message> getMessageList(){
		List<Message> messages=new ArrayList<Message>();
		for(int i=0;i<infoTableModel.getRowCount();i++) {
			messages.add((Message)infoTableModel.getValueAt(i, 0));
		}
		return messages;
	}
	
}

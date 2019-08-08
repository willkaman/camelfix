package cl.banchile.camelfix.frame;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import quickfix.DataDictionary;
import quickfix.Field;
import quickfix.FieldMap;
import quickfix.FieldNotFound;
import quickfix.Message;

public class CamelfixMsgDetailFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel=null;
	private JScrollPane scrollPanel=null;
	
	public CamelfixMsgDetailFrame(DataDictionary dd, Message message) {
		super();
		mainPanel=new JPanel();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//this.setPreferredSize(new Dimension(200,300));
		
		String[]tableHeader={"tag","value"};
		JTable table=null;
        
        try {
			table=new JTable (getTagValueMatrix(dd, message),tableHeader);
		} catch (FieldNotFound e1) {
			table=new JTable(0,2);
		}
        
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        
        scrollPanel=new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(scrollPanel,BorderLayout.CENTER);
		
		this.add(mainPanel);
		this.pack();
		this.setLocationRelativeTo(null); //center screen
		
	}
	
	private String[][] getTagValueMatrix(DataDictionary dd, FieldMap fieldMap) throws FieldNotFound {
		String[][] out=null;
		List<String[]> list=new ArrayList<String[]>();
		
		Iterator fieldIterator = fieldMap.iterator();
		while (fieldIterator.hasNext()) {
		    Field field = (Field) fieldIterator.next();
		    String value = fieldMap.getString(field.getTag());
		    
		    String[]arr= {dd.getFieldName(field.getTag()) + "("+field.getTag()+")",value};
		    
		    list.add(arr);
		}
		
		out=new String[list.size()][2];
		
		for(int i=0;i<list.size();i++) {
			out[i]=list.get(i);
		}
		
		return out;
	}

}

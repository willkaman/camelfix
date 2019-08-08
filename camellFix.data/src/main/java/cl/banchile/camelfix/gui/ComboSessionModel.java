package cl.banchile.camelfix.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.MutableComboBoxModel;
import javax.swing.event.ListDataListener;

public class ComboSessionModel<CamelfixSession> implements MutableComboBoxModel<CamelfixSession> {
	List<CamelfixSession> l=new ArrayList<CamelfixSession>();
	Object selected=null;
	
	@Override
	public int getSize() {
		return l==null?0:l.size();
	}

	@Override
	public CamelfixSession getElementAt(int index) {
		return l!=null?l.get(index):null;
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selected=anItem;
	}

	@Override
	public Object getSelectedItem() {
		return selected;
	}

	@Override
	public void addElement(CamelfixSession item) {
		l.add(item);
	}

	@Override
	public void removeElement(Object obj) {
		l.remove(obj);
	}

	@Override
	public void insertElementAt(CamelfixSession item, int index) {
		l.add(index, item);
	}

	@Override
	public void removeElementAt(int index) {
		l.remove(index);
	}

}

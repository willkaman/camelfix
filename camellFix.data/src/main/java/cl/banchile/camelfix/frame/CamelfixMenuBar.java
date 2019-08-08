package cl.banchile.camelfix.frame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class CamelfixMenuBar extends JMenuBar {

	private static final long serialVersionUID = -5908001896986231647L;
	
	private JMenu menu=null;
	
	public CamelfixMenuBar() {
		super();
		
		JMenuItem menuItem=null;
		menu=new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_ALT);
		
		menuItem=new JMenuItem("new acceptor session");
		menu.add(menuItem);
		//setActionListener(menuItem);
		
		menuItem=new JMenuItem("new initiator session");
		menu.add(menuItem);
		
		menu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Menu:"+e.getSource().getClass().getName());
				
			}
			
		});
		
		this.add(menu);
	}
	
	/*private void setActionListener(JMenuItem menuItem) {
		menuItem.add
	}*/

}

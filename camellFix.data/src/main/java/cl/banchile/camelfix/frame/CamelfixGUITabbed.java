package cl.banchile.camelfix.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cl.banchile.camelfix.impl.session.CamelfixSession;

public class CamelfixGUITabbed extends JPanel implements ActionListener, PropertyChangeListener, ListSelectionListener{
	
	private static final long serialVersionUID = -2457110712706744104L;
	
	JFrame mainFrame=null;
	CamelfixMenuBar menuBar=null;
	JTabbedPane jtp=null;
	
	public CamelfixGUITabbed(){
		mainFrame=new JFrame();
		jtp = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		menuBar=new CamelfixMenuBar();
	}
	
	public void addTabbedSession(CamelfixSession sess) {
		CamelfixSessionPanel sessionPane=new CamelfixSessionPanel(sess,mainFrame);
		jtp.addTab(sessionPane.session.toString(), sessionPane);
		//jtp.setComponentAt(0, sessionFrame);
	}

	public void buildAndDisplayGui() {
		mainFrame.add(jtp);
		mainFrame.setJMenuBar(menuBar);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	private static final class ShowDialog implements ActionListener {
		ShowDialog(JFrame aFrame) {
			fFrame = aFrame;
		}

		@Override
		public void actionPerformed(ActionEvent aEvent) {
			JOptionPane.showMessageDialog(fFrame, "This is a dialog");
		}

		private JFrame fFrame;
	}

	/*@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("Propiedad cambia: "+evt.getPropertyName()+":"+evt.getNewValue());
		if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            //taskOutput.append(String.format("Completed %d%% of task.\n", task.getProgress()));
        } else if ("sessionStatus".equalsIgnoreCase(evt.getPropertyName())) {
        	frame.setTitle(String.valueOf(evt.getNewValue()));
        	label.setText(session.toString());
        	flushLog();
        } else if ("messageQueue".equalsIgnoreCase(evt.getPropertyName())) {
        	System.out.println("flushLog...");
        	flushLog();
        }
	}*/

	@Override
	public void valueChanged(ListSelectionEvent e) {
		/*if(!e.getValueIsAdjusting()){
			try {
				htmlMessage.setText(messageToHTML(dd,area2.getSelectedValue()));
			} catch (FieldNotFound e1) {
				e1.printStackTrace();
			}
		}*/
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

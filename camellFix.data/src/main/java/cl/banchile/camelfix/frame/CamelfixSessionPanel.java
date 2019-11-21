package cl.banchile.camelfix.frame;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;

import cl.banchile.camelfix.impl.session.CamelfixSession;
import cl.banchile.camelfix.main.CamelfixMain;
import quickfix.DataDictionary;
import quickfix.Message;

public class CamelfixSessionPanel extends JPanel implements ActionListener, PropertyChangeListener{
	
	private static final long serialVersionUID = -2457110712706744104L;
	
	JFrame frame=null;
	JButton parseButton = null;
	JButton sentMessagesButton = null;
	ParseTask parseTask=null;
	SentTask sentTask=null;
	JTextArea area=null;
	JList<Object> logList=null;
	JScrollPane logListScrollPanel=null;
	JSpinner spinner =null;
	JProgressBar progressBar = null;
	CamelfixSession session=null;
	JLabel sessionStatusLabel=null;
	DataDictionary dd=null;
	CamelfixInfoPanel infoPanel=null;
	CamelfixMessagesPanel messagesPanel=null;
	
	public CamelfixSessionPanel(CamelfixSession sess,JFrame parentFrame){
		try {
			//dd=new DataDictionary("C:\\javadev\\workspace\\2015\\camellFix\\svn\\camellFix.data\\src\\main\\resources\\FIX44_QF_BCS.xml");
			//dd=new DataDictionary("resources/FIX44_QF_BCS.xml");
			dd=sess.getDataDictionary();
			session=sess;
			session.addPropertyChangeListener(this);
			frame=parentFrame;
			
			buildContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*public void buildAndDisplayGui() {
		frame = new JFrame("Camelfix 0.1 alpha");
		buildContent(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}*/

	private void buildContent() {
		SpinnerModel model = new SpinnerNumberModel(1, 1, 1000, 1);
		JPanel superPanel = this;
		superPanel.setLayout(new BorderLayout());
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		area = new JTextArea();// JTextArea(10, 40);
		spinner = new JSpinner(model);
		spinner.setEditor(new JSpinner.NumberEditor(spinner));
		JScrollPane sp = new JScrollPane(area);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		parseButton = new JButton("Parse");
		sentMessagesButton = new JButton("Sent Messages");
		messagesPanel=new CamelfixMessagesPanel(session);
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 10;
		c.weighty = 2;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridx = 0;
		c.gridy = 0;
		centerPanel.add(sp,c);
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(2,2,0,0);
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.gridx = 0;
		c.gridy = 1;
		centerPanel.add(spinner,c);
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.gridx = 1;
		c.gridy = 1;
		centerPanel.add(parseButton,c);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 10;
		c.weighty = 2;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridx = 0;
		c.gridy = 2;
		centerPanel.add(messagesPanel,c);
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(2,2,0,0);
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.gridx = 0;
		c.gridy = 3;
		centerPanel.add(sentMessagesButton,c);
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(2,2,0,0);
		c.ipady = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.gridx = 1;
		c.gridy = 3;
		centerPanel.add(progressBar,c);
		
		
		
		superPanel.add(centerPanel, BorderLayout.CENTER);
		
		sessionStatusLabel=new JLabel("Session status:"+session.getSessionStatus());
		superPanel.add(sessionStatusLabel,BorderLayout.NORTH);

		//pruebame
		DefaultListModel<Object> loglistModel=new  DefaultListModel<Object>();
		logList=new JList<Object>(loglistModel);
		
        
		logListScrollPanel = new JScrollPane(logList);
		logListScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		logListScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel panelSouth = new JPanel();
		panelSouth.setLayout(new GridBagLayout());
		//panelSouth.add(sp2);
		c.fill = GridBagConstraints.BOTH;
		//c.ipady = 40;
		c.weightx = 1;
		c.weighty = 5;
		//c.gridwidth = 1;
		//c.gridx = 0;
		c.gridy = 0;
		panelSouth.add(logListScrollPanel, c);
		
		superPanel.add(panelSouth, BorderLayout.SOUTH);
		//superPanel.add(rightPanel, BorderLayout.EAST);

		//this.add(superPanel);

		parseButton.addActionListener(this);
		parseButton.setActionCommand("parse");
		sentMessagesButton.addActionListener(this);
		sentMessagesButton.setActionCommand("sent");
		
		//logList.addListSelectionListener(this);
		logList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	Object msgObject=(Message)list.getSelectedValue();
		        	if(msgObject instanceof Message) {
		        		new CamelfixMsgDetailFrame(dd,(Message)msgObject).setVisible(true);
		        	}
		        }
			}
		});
    	
		infoPanel=new CamelfixInfoPanel(session);
		superPanel.add(infoPanel, BorderLayout.WEST);
		
		//flush the log queue in the session
		flushLog();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("Propiedad cambia: "+evt.getPropertyName()+":"+evt.getNewValue());
		if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            //taskOutput.append(String.format("Completed %d%% of task.\n", task.getProgress()));
        } else if ("sessionStatus".equalsIgnoreCase(evt.getPropertyName())) {
        	sessionStatusLabel.setText("Session status:"+String.valueOf(evt.getNewValue()));
        	flushLog();
        } else if ("messageQueue".equalsIgnoreCase(evt.getPropertyName())) {
        	System.out.println("flushLog...");
        	flushLog();
        }
	}
	
	private synchronized void flushLog(){
		Message msg=null;
		DefaultListModel<Object> model=(DefaultListModel<Object>) logList.getModel();
		msg=session.pollMessageQueue();
		
		if(logList!=null && model!=null) {
			//voids the entire queue into the logListModel (visual compo)
	    	while(msg!=null){
	    		System.out.println("Model size:"+model.getSize());
	    		try {
	    			if(model.getSize() >= 1000){
	    				model.remove(0);
	    			}
	    			model.addElement(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
	    		msg=session.pollMessageQueue();
	    	}
		}
		logList.ensureIndexIsVisible(logList.getModel().getSize());
		System.out.println("scrollbar value: "+logListScrollPanel.getVerticalScrollBar().getValue());
		int currentValue=logListScrollPanel.getVerticalScrollBar().getModel().getValue();
		int maximumValue=logListScrollPanel.getVerticalScrollBar().getModel().getMaximum();
		int extentValue=logListScrollPanel.getVerticalScrollBar().getModel().getExtent();
		
		logListScrollPanel.getVerticalScrollBar().setValue(maximumValue - extentValue);
		logList.ensureIndexIsVisible(logList.getModel().getSize());
		logListScrollPanel.updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if(session==null){
			JOptionPane.showMessageDialog(this.frame, "No hay sesiones conectadas");
			return;
		}
		
		switch(command) {
			case "parse" :{
				parseButton.setEnabled(false);
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				
				parseTask = new ParseTask();
				//no se requiere activar la barra de progreso para el comando "parse"
			    //parseTask.addPropertyChangeListener(this);
			    parseTask.execute();
				break;
			}
			case "sent":{
				sentMessagesButton.setEnabled(false);
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				
				sentTask = new SentTask();
			    sentTask.addPropertyChangeListener(this);
			    sentTask.execute();
				break;
			}
		}
		
		System.out.println("Evento"+e.getSource().getClass().toGenericString());
	}
	
	private synchronized void pushLogList(Object logObject) {
		((DefaultListModel<Object>)logList.getModel()).addElement(logObject);
	}
	
	/*Internal classes*/
	class ParseTask extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
        	String[] lines = area.getText().split("\\n");
    		Integer repeticiones = ((SpinnerNumberModel) spinner.getModel()).getNumber().intValue();
    		List<Message> messages=new ArrayList<Message>();
    		Double progress;
    		setProgress(0);
			for (double i = 0; i < repeticiones; i++) {
				for (String chorizo : lines) {
					try{
						messages.add(CamelfixMain.store.getApplication().loadChorizo(session, chorizo));
						messagesPanel.updateRowData(messages.toArray());
					}
					catch(Exception ex) {
						pushLogList(ex);
						ex.printStackTrace();
					}
				}
				//avanzar el progreso
				progress=Math.ceil(((i+1)/repeticiones*100));
				
				System.out.println("progreso: "+progress);
				setProgress(progress.intValue());
			}
			
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            parseButton.setEnabled(true);
            setCursor(null); //turn off the wait cursor
            //taskOutput.append("Done!\n");
        }
    }
	
	class SentTask extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
    		List<Message> messages=messagesPanel.getMessageList();
    		int repeticiones=messages.size();
    		Double progress;
    		setProgress(0);
			for (int i = 0; i < repeticiones; i++) {
				try {
					CamelfixMain.store.getApplication().sendMessage(messages.get(i), session);
				} catch (Exception e) {
					pushLogList(e);
				}
				//avanzar el progreso
				progress=Math.ceil(((i+1)/repeticiones*100));
				setProgress(progress.intValue());
			}
			
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            sentMessagesButton.setEnabled(true);
            setCursor(null);
            
            JOptionPane.showMessageDialog(frame,"Messages sent");
            progressBar.setValue(0);
        }
    }
}

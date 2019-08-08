package cl.banchile.camelfix.impl.session;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import quickfix.DataDictionary;
import quickfix.Message;
import quickfix.SessionID;
import quickfix.SessionSettings;
import cl.banchile.camelfix.utils.CamelfixStore;

public class CamelfixSession implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String sessionStatus=null;
	private SessionID sessison=null;
	private Queue<Message> messageQueue=null;
	private DataDictionary dataDictionary=null;

	private PropertyChangeSupport pcs =new PropertyChangeSupport(this);
	
	public CamelfixSession(){
		messageQueue=new LinkedList<Message>();
	}
	
	public String getSessionStatus() {
		return sessionStatus;
	}
	public void setSessionStatus(String sessionStatus) {
		pcs.firePropertyChange("sessionStatus",
                this.sessionStatus, sessionStatus);
		this.sessionStatus = sessionStatus;
	}
	public SessionID getSessison() {
		return sessison;
	}
	public void setSessison(SessionID sessison) {
		this.sessison = sessison;
		String beginString=null;
		String senderCompID=null;
		String targetCompID=null;
		//manejo de archivo de log
		try {
			beginString=CamelfixStore.getInstance().getSessionSettings().getString(sessison,SessionSettings.BEGINSTRING);
			senderCompID=CamelfixStore.getInstance().getSessionSettings().getString(sessison,SessionSettings.SENDERCOMPID);
			targetCompID=CamelfixStore.getInstance().getSessionSettings().getString(sessison,SessionSettings.TARGETCOMPID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//pruebame
		/*StringBuffer fileContent=null;
		try
	    {
			int len = (int) sessionLog.length();
			FileInputStream fis = new FileInputStream(sessionLog);
			byte buf[] = new byte[len];
			fis.read(buf);
			fis.close();
			fileContent = new StringBuffer(new String(buf, 0, len));
			System.out.println(fileContent);
	    }
	    	catch (FileNotFoundException e)
	    {
	    	System.out.println("FileNotFoundException" + e);
	    }
			catch (IOException e)
	    {
			System.out.println("IOException" + e);
	    }*/
		//fin pruebame
		
	}
	
	public DataDictionary getDataDictionary() {
		return dataDictionary;
	}

	public void setDataDictionary(DataDictionary dataDictionary) {
		this.dataDictionary = dataDictionary;
	}

	public synchronized Message pollMessageQueue(){
		return messageQueue.poll();
	}
	
	public synchronized void addMessageQueue(Message element){
		messageQueue.add(element);
		pcs.firePropertyChange("messageQueue", null, messageQueue);
	}
	
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
    
    public String toString(){
    	return sessison.toString();
    }
}

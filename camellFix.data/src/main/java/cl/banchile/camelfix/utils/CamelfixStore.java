package cl.banchile.camelfix.utils;

import java.util.HashMap;
import java.util.Map;

import quickfix.ConfigError;
import quickfix.DataDictionary;
import quickfix.Dictionary;
import quickfix.FieldConvertError;
import quickfix.SessionID;
import quickfix.SessionSettings;
import cl.banchile.camelfix.impl.session.CamelfixSession;
import cl.banchile.camelfix.service.CamelfixApplication;

public class CamelfixStore{
	private static CamelfixStore instance=null;
	private CamelfixApplication application=null;
	private SessionSettings sessionSettings=null;
	private Map<String,CamelfixSession> camelfixSessionMap=new HashMap<String, CamelfixSession>();

	private CamelfixStore(){
		
	}
	
	public static CamelfixStore getInstance(){
		if (instance==null)
			instance=new CamelfixStore();
		return instance;
	}
	
	public CamelfixApplication getApplication() {
		return application;
	}

	public synchronized void setApplication(CamelfixApplication application) {
		this.application = application;
	}
	
	public SessionSettings getSessionSettings() {
		return sessionSettings;
	}

	public void setSessionSettings(SessionSettings sessionSettings) {
		this.sessionSettings = sessionSettings;
	}
	
	public Dictionary getDictionary(SessionID sess){
		try {
			return sessionSettings.get(sess);
		} catch (ConfigError e) {
			e.printStackTrace();
			return null;
		}
	}
    
    public void addCamelfixSessionToMap(SessionID session){
    	if(camelfixSessionMap.get(session.toString())==null){
    		CamelfixSession cs=new CamelfixSession();
    		cs.setSessison(session);
    		try {
				cs.setDataDictionary(new DataDictionary(sessionSettings.getString(session, "DataDictionary")));
			} catch (Exception e) {
				e.printStackTrace();
				cs.setDataDictionary(null);
			}
    		camelfixSessionMap.put(session.toString(), cs);
    	}
    }
    
    public CamelfixSession getCamelfixSessionBySessionID(SessionID session){
    	return camelfixSessionMap.get(session.toString());
    }
}

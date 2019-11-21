package cl.banchile.camelfix.service;

import cl.banchile.camelfix.impl.session.CamelfixSession;
import quickfix.Application;
import quickfix.Message;

public interface CamelfixApplication extends Application {
	public Message loadChorizo(CamelfixSession session, String chorizo) throws Exception;
	public Message parseChorizo(CamelfixSession session, String chorizo) throws Exception;
	public void sendMessage(Message msg,CamelfixSession camelfixSession) throws Exception;
}

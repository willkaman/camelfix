package cl.banchile.camelfix.main;

import java.io.FileInputStream;

import javax.management.ObjectName;

import org.quickfixj.jmx.JmxExporter;

import cl.banchile.camelfix.frame.CamelfixGUITabbed;
import cl.banchile.camelfix.impl.CamelfixApplicationImpl;
import cl.banchile.camelfix.utils.CamelfixStore;
import quickfix.Acceptor;
import quickfix.DefaultMessageFactory;
import quickfix.DefaultSessionFactory;
import quickfix.FileLogFactory;
import quickfix.FileStoreFactory;
import quickfix.Initiator;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.Session;
import quickfix.SessionFactory;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.ThreadedSocketAcceptor;
import quickfix.ThreadedSocketInitiator;

public class CamelfixMain {

	public static CamelfixStore store = null;

	public static void main(String args[]) throws Exception {
		store = CamelfixStore.getInstance();
		if (args.length != 1)
			return;
		String fileName = args[0];

		SessionSettings settings = new SessionSettings(new FileInputStream(
				fileName));
		store.setSessionSettings(settings);
		
		store.setApplication(new CamelfixApplicationImpl());

		MessageStoreFactory storeFactory = new FileStoreFactory(settings);
		LogFactory logFactory = new FileLogFactory(settings);
		MessageFactory messageFactory = new DefaultMessageFactory(); 
		
		Acceptor acceptor = new ThreadedSocketAcceptor(store.getApplication(), storeFactory,
				settings, logFactory, messageFactory);
		
		Initiator initiator=new ThreadedSocketInitiator(store.getApplication(), storeFactory,
				settings, logFactory, messageFactory);
		
		try{
			JmxExporter jmxExporter = new JmxExporter();
			ObjectName connectorObjectNameAcc = jmxExporter.register(acceptor);
			acceptor.start();
			System.out.println("[main] Acceptor iniciado");
		}
		catch(Exception e){
			System.out.println("[WARNING main] No hay acceptors");
			e.printStackTrace();
		}
		finally{
			try{
				JmxExporter jmxExporter = new JmxExporter();
				ObjectName connectorObjectNameIni = jmxExporter.register(initiator);
				initiator.start();
				System.out.println("[main] Initiator iniciado");
			}
			catch(Exception e){
				System.out.println("[WARNING main] No hay initiators");
				e.printStackTrace();
			}
		}
		
		CamelfixGUITabbed gui=new CamelfixGUITabbed();
		//obteniendo las sesiones y creando ventanas por cada una de ellas
		for(SessionID session:acceptor.getSessions()){
			//(new CamelfixGUI(store.getCamelfixSessionBySessionID(session))).buildAndDisplayGui();
			gui.addTabbedSession(store.getCamelfixSessionBySessionID(session));
		}
		for(SessionID session:initiator.getSessions()){
			//(new CamelfixGUI(store.getCamelfixSessionBySessionID(session))).buildAndDisplayGui();
			gui.addTabbedSession(store.getCamelfixSessionBySessionID(session));
		}
		
//		SessionID sessionId=new SessionID("FIX.4.4", "SOYPORTFOLIO", "SOYOMS");
//		SessionFactory sf=new DefaultSessionFactory(store.getApplication(), storeFactory, logFactory);
//		SessionSettings sst=new SessionSettings();
//		//SESSION
//		settings.setString(sessionId,"TargetCompID", "SOYOMS");
//		settings.setString(sessionId,"SenderCompID", "SOYPORTFOLIO");
//		settings.setString(sessionId,"ConnectionType", "initiator");
//		settings.setString(sessionId,"DataDictionary", "resources\\FIX44_OMS.xml");
//		settings.setLong(sessionId,"SocketConnectPort", 7000);
//		settings.setString(sessionId,"SocketConnectHost", "127.0.0.1");
//		settings.setString(sessionId,"SocketConnectProtocol", "TCP");
//		
//		Initiator dynInitiator=new ThreadedSocketInitiator(store.getApplication(), storeFactory,
//				settings, logFactory, messageFactory);
//		dynInitiator.start();
//		
//		gui.addTabbedSession(store.getCamelfixSessionBySessionID(sessionId));
		
		
		gui.buildAndDisplayGui();
		
		/*javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });*/

		// acceptor.stop();
	}

}

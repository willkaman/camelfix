package cl.banchile.camelfix.impl;

import java.util.Arrays;

import cl.banchile.camelfix.impl.session.CamelfixSession;
import cl.banchile.camelfix.service.CamelfixApplication;
import cl.banchile.camelfix.utils.CamelfixStore;
import quickfix.DataDictionary;
import quickfix.DataDictionary.GroupInfo;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.Group;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.MessageUtils;
import quickfix.RejectLogon;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.UnsupportedMessageType;
import quickfix.fix44.MessageFactory;


public class CamelfixApplicationImpl implements CamelfixApplication {
	CamelfixStore store=CamelfixStore.getInstance();
	
	@Override
	public void fromAdmin(Message arg0, SessionID arg1) throws FieldNotFound,
			IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		//TODO BORRAME
		store.getCamelfixSessionBySessionID(arg1).addMessageQueue(arg0);
	}

	@Override
	public void fromApp(Message arg0, SessionID arg1) throws FieldNotFound,
			IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		store.getCamelfixSessionBySessionID(arg1).addMessageQueue(arg0);
	}

	@Override
	public void onCreate(SessionID arg0) {
		store.addCamelfixSessionToMap(arg0);
		store.getCamelfixSessionBySessionID(arg0).setSessionStatus("created");
		System.out.println("onCreate");
	}

	@Override
	public void onLogon(SessionID arg0) {
		store.getCamelfixSessionBySessionID(arg0).setSessionStatus("logged");
		System.out.println("onLogon");
	}

	@Override
	public void onLogout(SessionID arg0) {
		store.getCamelfixSessionBySessionID(arg0).setSessionStatus("not logged");
		System.out.println("onLogout");
	}

	@Override
	public void toAdmin(Message arg0, SessionID arg1) {
		////Administration messages (Heartbeat)
	}

	@Override
	public void toApp(Message arg0, SessionID arg1) throws DoNotSend {
		System.out.println("toApp");
		store.getCamelfixSessionBySessionID(arg1).addMessageQueue(arg0);
	}

	public synchronized Message loadChorizo(CamelfixSession session, String line){
		Message m=null;
		try {
			SessionID sessionId=session.getSessison();
			MessageFactory mf = new MessageFactory();
			DataDictionary dd = session.getDataDictionary();
			if(dd!=null){
				//NewOrderSingle nos=new NewOrderSingle();
				//nos.fromString(line, dd, true);
				m=MessageUtils.parse(mf,dd, line);
				System.out.println("mensaje generado");
				//Session.sendToTarget(m,sessionId);
			}
			else{
				throw new Exception("DataDictionary no definido para la session");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}
	
	public synchronized Message parseChorizo(CamelfixSession session, String line) throws Exception{
		MessageFactory mf = new MessageFactory();
		Message msg=null;
		DataDictionary dd = session.getDataDictionary();
		String[]tagsValues=null;
		String[]pair=null;
		int tag=0;
		String msgType=null;
		Group group=null;
		GroupInfo groupInfo=null;
		if(dd!=null){
			tagsValues=line.split("\\u0001");
			msgType=getTagValue(tagsValues,35);
			msg=mf.create(dd.getVersion(), msgType);
			
			for(String tagValue:tagsValues) {
				pair=tagValue.split("=",2);
				tag=Integer.parseInt(pair[0]);
				if(!(tag==8 || tag==9) && !dd.isTrailerField(tag)) {
					if(dd.isHeaderField(tag)) {
						msg.getHeader().setString(tag, pair[1]);
					}
					else {
						if(dd.getGroup(msgType, tag)!=null) {
							groupInfo=dd.getGroup(msgType, tag);
							group=new Group(tag,groupInfo.getDelimiterField());
						}
						else {
							if(groupInfo!=null) {
								if(belongsToGroup(groupInfo.getDataDictionary().getOrderedFields(),tag)) {
									group.setString(tag, pair[1]);
								}
								else {
									msg.addGroup(group);
									group=null;
									groupInfo=null;
									msg.setString(tag, pair[1]);
								}
							}
							else {
								msg.setString(tag, pair[1]); 
							}
						}
					}
				}
			}
			System.out.println("mensaje generado");
			
			//validte message agains data dictionary, including the headers and body
			//dd.validate(msg, false);
			new Message(msg.toString(), dd, true);
			
			//Session.sendToTarget(msg,sessionId);
		}
		else{
			throw new Exception("DataDictionary no definido para la session");
		}
		return msg;
	}
	
	public void sendMessage(Message msg,CamelfixSession camelfixSession) throws Exception {
		try {
			Session.sendToTarget(msg,camelfixSession.getSessison());
		} catch (SessionNotFound e) {
			throw new Exception("Sesion no definida");
		}
	}
	
	private String getTagValue(String[]tagsValues,int tag) {
		String[]pair=null;
		for(String tagValue:tagsValues) {
			pair=tagValue.split("=",2);
			if(Integer.parseInt(pair[0])==tag) {
				return pair[1];
			}
		}
		return null;
	}
	
	private Boolean belongsToGroup(int[]group, final int tag) {
		return Arrays.stream(group).anyMatch(i -> i == tag);
	}
	
}

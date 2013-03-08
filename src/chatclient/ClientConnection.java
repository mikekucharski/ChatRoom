/**
 * @author Mike Kucharski
 * Fall 2013
 */

package chatclient;

import java.io.IOException;

import chatconnection.ChatConnection;

import messages.Message;
import messages.MsgDisconApproved;
import messages.MsgDisconDenied;
import messages.MsgDisconRequest;
import messages.MsgTextRequest;
import messages.MsgUpdateChat;
import messages.MsgUpdateUserList;
import messages.MsgUsernameRequest;
import messages.MsgUsernameWelcome;

public class ClientConnection extends ChatConnection{
	
	private ClientState cState;
	
	public ClientConnection()
	{
		cState = null;
	}
	
	ClientConnection(ClientState cs)
	{
		super();
		cState = cs;
	}
	
	public synchronized void senduserName(String s)
	{
		MsgUsernameRequest un = new MsgUsernameRequest(s);
		try {
			objectOut.writeObject(un);
			objectOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized  void sendMessageText(String text)
	{
		MsgTextRequest msg = new MsgTextRequest(text);
		try {
			objectOut.writeObject(msg);
			objectOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void sendDisconnect()
	{
		MsgDisconRequest msg = new MsgDisconRequest();
		try {
			objectOut.writeObject(msg);
			objectOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(cState.connected)
		{
			try {
				Message m = (Message) objectIn.readObject();
				if(m instanceof MsgUsernameWelcome)
				{
					MsgUsernameWelcome welcome = (MsgUsernameWelcome) m;
					cState.processWelcomeMsg(welcome.getMessage());
				}
				else if(m instanceof MsgDisconDenied)
				{
					MsgDisconDenied failed = (MsgDisconDenied) m;
					cState.processUserDeniedMsg(failed.getMessage());
				}
				else if(m instanceof MsgUpdateChat)
				{
					MsgUpdateChat msg = (MsgUpdateChat) m;
					cState.processMessageText(msg.getText());
				}
				else if(m instanceof MsgUpdateUserList)
				{
					MsgUpdateUserList msg = (MsgUpdateUserList) m;
					cState.processMessageUserList(msg.getList());
				}
				else if(m instanceof MsgDisconApproved)
				{
					MsgDisconApproved msg = (MsgDisconApproved) m;
					cState.processGoodbyeMsg(msg.getMessage());
				}
				else
				{
					System.out.println("This message is not an instance of any subclass!");
				}
				
			} catch (IOException e) {
				break;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		try {
			objectIn.close();
			objectOut.close();
			clientSocket.close();
		} catch (IOException e) {
		}
		
	}
}

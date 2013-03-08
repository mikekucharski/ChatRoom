/**
 * @author Mike Kucharski
 * Fall 2013
 */

package chatserver;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

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

public class ServerConnection extends ChatConnection {
	
	private ServerState sState;
	
	public ServerConnection(Socket skt)
	{
		super(skt);
	}
	
	public void setServerState(ServerState s)
	{
		sState = s;
	}
	
	public synchronized void sendUserConn(String username)
	{
		MsgUsernameWelcome welcome = new MsgUsernameWelcome(username);
		try {
			objectOut.writeObject(welcome);
			objectOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void sendUpdateChat(String user, String text)
	{
		MsgUpdateChat msg = new MsgUpdateChat();
		msg.setChatMessage("[" + msg.getMessageTime() + "] " + user + ": " + text);
		try {
			objectOut.writeObject(msg);
			objectOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void sendDisconMessage(String username)
	{
		MsgDisconApproved goodbye = new MsgDisconApproved(username);
		try {
			objectOut.writeObject(goodbye);
			objectOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void sendDisconFailed(String username)
	{
		MsgDisconDenied denied = new MsgDisconDenied(username);
		try {
			objectOut.writeObject(denied);
			objectOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void sendUserList(ArrayList<String> users)
	{
		MsgUpdateUserList msg = new MsgUpdateUserList(users);
		try {
			objectOut.writeObject(msg);
			objectOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(serverConnected)
		{
			try {
				
				Message m = (Message) objectIn.readObject();
				if(m instanceof MsgUsernameRequest)
				{
					MsgUsernameRequest sm = (MsgUsernameRequest) m;
					sState.processUsername(this, sm.getUsername());
				}
				else if(m instanceof MsgTextRequest){
					MsgTextRequest msg = (MsgTextRequest) m;
					sState.processTextMessage(this, msg.getText());
				}
				else if(m instanceof MsgDisconRequest){
					sState.processDisconnectMessage(this);
				}
				else
				{
					System.out.println("This message is not an instant of any subclass!");
				}
			} catch (SocketException e){
				//catches when the window is "xed out"
				//treats this like a normal disconnect to the server
				sState.processDisconnectMessage(this);
				break;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}

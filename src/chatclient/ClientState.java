/**
 * @author Mike Kucharski
 * Fall 2013
 */

package chatclient;

import java.util.ArrayList;

public class ClientState {
	
	private ClientConnection connection;
	private ClientGUI clientGUI;
	private String username;
	protected boolean connected;
	
	public ClientState(ClientGUI GUI)
	{
		connection = null;
		clientGUI = GUI;
		username = null;
		connected = false;
	}
	
	public void connectAction(String name)
	{
		username = name;
		connection = new ClientConnection(this);
		connection.clientConnect();
		connected = true;
		connection.start();
		
		connection.senduserName(username);
	}
	
	public void sendAction(String text)
	{
		connection.sendMessageText(text);
	}
	
	public void disconnectAction()
	{
		connection.sendDisconnect();
		connected = false;
	}
	
	public void processWelcomeMsg(String msg)
	{
		clientGUI.addTextToChat(msg);
	}
	
	public void processUserDeniedMsg(String msg)
	{
		connected = false;
		clientGUI.addTextToChat(msg);
		clientGUI.resetComponents();
	}
	
	public void processMessageText(String text)
	{
		clientGUI.addTextToChat(text);
	}

	public void processMessageUserList(ArrayList<String> list) 
	{
		clientGUI.clearOnlineUsers();
		for(int i = 0; i < list.size(); i++)
			clientGUI.addUserToList(list.get(i));
	}
	
	public boolean isConnected()
	{
		return connected;
	}

	public void processGoodbyeMsg(String message) {
		clientGUI.addTextToChat(message);
	}
	
}

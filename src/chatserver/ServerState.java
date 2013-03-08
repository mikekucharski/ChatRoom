/**
 * @author Mike Kucharski
 * Fall 2013
 */

package chatserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ServerState {
	
	private HashMap<ServerConnection, String> users;
	
	public ServerState()
	{
		users = new HashMap<ServerConnection, String>();
	}
	
	public void processUsername(ServerConnection sConn, String username)
	{	
		
		if(users.containsValue(username))
		{
			sConn.sendDisconFailed(username);
			sConn.setServerConnected(false);
			return;
		}
		users.put(sConn, username);
		
		ArrayList<String> usernameList = new ArrayList<String>();
		Iterator<ServerConnection> iter2 = users.keySet().iterator();
		while(iter2.hasNext()){
			usernameList.add(users.get(iter2.next()));
		}
		
		Iterator<ServerConnection> iterKey = users.keySet().iterator();
		
		while(iterKey.hasNext())
		{
			ServerConnection temp = iterKey.next();
			temp.sendUserConn(username);
			temp.sendUserList(usernameList);
		}
	}
	
	public void processTextMessage(ServerConnection sConn, String text)
	{
		String user = users.get(sConn);
		
		Iterator<ServerConnection> iter = users.keySet().iterator();
		while(iter.hasNext())
		{
			iter.next().sendUpdateChat(user, text);
		}
	}
	
	public void processDisconnectMessage(ServerConnection sConn)
	{
		String user = users.get(sConn);
		users.remove(sConn);
		
		ArrayList<String> usernameList = new ArrayList<String>();
		Iterator<ServerConnection> iter2 = users.keySet().iterator();
		while(iter2.hasNext()){
			usernameList.add(users.get(iter2.next()));
		}
		
		Iterator<ServerConnection> iter = users.keySet().iterator();
		while(iter.hasNext())
		{
			ServerConnection temp = iter.next();
			temp.sendDisconMessage(user);
			temp.sendUserList(usernameList);
		}
		sConn.setServerConnected(false);
	}
}

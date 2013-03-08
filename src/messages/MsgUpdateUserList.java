/**
 * @author Mike Kucharski
 * Fall 2013
 */

package messages;

import java.util.ArrayList;

public class MsgUpdateUserList extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> usernames;
	
	public MsgUpdateUserList()
	{
		usernames = null;
	}
	
	public MsgUpdateUserList(ArrayList<String> users)
	{
		usernames = users;
	}
	
	public ArrayList<String> getList()
	{
		return usernames;
	}
}

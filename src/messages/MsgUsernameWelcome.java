/**
 * @author Mike Kucharski
 * Fall 2013
 */

package messages;

public class MsgUsernameWelcome extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String welcome;
	
	
	public MsgUsernameWelcome(String username)
	{
		welcome = "[" + getMessageTime() + "]     *** " + username + " has joined the chat room ***";
	}
	
	public String getMessage()
	{
		return welcome;	
	}
}

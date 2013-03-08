/**
 * @author Mike Kucharski
 * Fall 2013
 */

package messages;

public class MsgDisconApproved extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String goodbye;
	
	public MsgDisconApproved(String username)
	{
		goodbye = "[" + getMessageTime() + "]     *** " + username + " has left the chat room ***";
	}
	
	public String getMessage()
	{
		return goodbye;	
	}
}

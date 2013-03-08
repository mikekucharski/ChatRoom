/**
 * @author Mike Kucharski
 * Fall 2013
 */

package messages;

public class MsgUpdateChat extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private String username;
	
	public MsgUpdateChat()
	{
		message = null;
	}
	
	public MsgUpdateChat(String user, String text)
	{
		username = user;
		message = text;
	}
	
	public void setChatMessage(String msg)
	{
		message = msg;
	}
	
	public String getText()
	{
		return message;	
	}
	
	public String getUser()
	{
		return username;	
	}
}
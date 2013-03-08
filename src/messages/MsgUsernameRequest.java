/**
 * @author Mike Kucharski
 * Fall 2013
 */

package messages;

public class MsgUsernameRequest extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username; 
	
	public MsgUsernameRequest(String s)
	{
		username = s;
	}
	
	public String getUsername()
	{
		return username;	
	}
}
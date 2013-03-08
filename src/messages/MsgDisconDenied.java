/**
 * @author Mike Kucharski
 * Fall 2013
 */

package messages;

public class MsgDisconDenied extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String denied;
	
	
	public MsgDisconDenied(String username)
	{
		denied = " *** " + username + " is already being used! Try another username. ***";
	}
	
	public String getMessage()
	{
		return denied;	
	}
}

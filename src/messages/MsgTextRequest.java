/**
 * @author Mike Kucharski
 * Fall 2013
 */

package messages;

public class MsgTextRequest extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public MsgTextRequest()
	{
		message = null;
	}
	
	public MsgTextRequest(String text)
	{
		message = text;
	}
	
	public void setMessageText(String msg)
	{
		message = msg;
	}
	
	public String getText()
	{
		return message;	
	}
}

/**
 * @author Mike Kucharski
 * Fall 2013
 */

package chatclient;

import java.io.IOException;

public class ChatClient {

	 public static void main(String[] args) throws IOException {
		 
		 ClientGUI gui = new ClientGUI();
		 ClientState cs = new ClientState(gui);
		
		 gui.setClientState(cs);
		 gui.setVisible(true);
		
	 }
}

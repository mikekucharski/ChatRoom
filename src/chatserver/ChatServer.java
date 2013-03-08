/**
 * @author Mike Kucharski
 * Fall 2013
 */

package chatserver;

import java.io.*;
import java.net.*;

public class ChatServer {
	public static void main(String args[]) throws IOException {
		
		ServerState state = null;
		ServerConnection sConn = null;
		ServerSocket svrSkt = null;
		
		try {
			svrSkt = new ServerSocket(2222);
			state = new ServerState();
		} catch (IOException e) {
			System.out.println("problem with connecting server to a port");
			e.printStackTrace();
			System.exit(1);
		}
		
		Socket skt = null;
		while(true){
			try{
				skt = svrSkt.accept();
				//create and update serverstate
				sConn = new ServerConnection(skt);
				sConn.setServerState(state);
		
				sConn.start();
				
			} catch (IOException e) {
				System.out.println("problem with accepting client!");
				e.printStackTrace();
				break;
				//System.out.println("server closed!");
				//System.exit(1);
			}
		}
			
		//skt.close();
		svrSkt.close();

	}
}

/**
 * @author Mike Kucharski
 * Fall 2013
 */

package chatconnection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatConnection extends Thread{

	protected Socket clientSocket;
    protected ObjectOutputStream objectOut;
    protected ObjectInputStream objectIn;
	//boolean connected;
	protected boolean serverConnected;
	private final String serverName = "cosmos.moonbaseone.net";
	// used for chat room over local machine
    //private final String localhost = "127.0.0.1";
    
    protected ChatConnection()
    {
    	clientSocket = null;
        objectIn = null;
        objectOut = null;
        serverConnected = false;
    }
    
    protected ChatConnection(Socket s)
    {
    	serverConnected = true;
    	clientSocket = s;
    	try {
			objectOut = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
			objectOut.flush();
			objectIn = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
    }
    
    public Boolean isServerConnected()	{return serverConnected;}
    public void setServerConnected(Boolean con) {serverConnected = con;}
    
    //the way the client uses this class
    public synchronized  void clientConnect()
    {
    	try {
			clientSocket = new Socket(serverName, 2222);
			
			objectOut = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
			objectOut.flush();
			objectIn = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
    }
}

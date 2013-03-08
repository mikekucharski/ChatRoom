/**
 * @author Mike Kucharski
 * Fall 2013
 */

package chatclient;

import javax.swing.*;  
import java.awt.*;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ClientGUI extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ClientState currentClientState;
	
	private JPanel pnlMain;
	private JLabel lblOnlineUsers, lblUsername;
	private JTextField tfUsername;
	private JTextArea taChat, taOnlineUsers, taMessage;
	private JButton btnConnect, btnDisconnect, btnSend;
	private JScrollPane scrollMessage, scrollUserList, scrollChat;
	
	public ClientGUI()
	{
		initUI();
	}

	private void initUI() {
		pnlMain = new JPanel();
		pnlMain.setLayout(null);
		pnlMain.setBackground(new Color(47, 79, 79));
		
		getContentPane().add(pnlMain);
		
		lblUsername = new JLabel("Username:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUsername.setBounds(43, 24, 79, 22);
		pnlMain.add(lblUsername);
		
		taChat = new JTextArea();
		taChat.setMargin(new Insets(8, 8, 8, 8));
		taChat.setEditable(false);
		taChat.setLineWrap(true);
		taChat.setWrapStyleWord(true);
		taChat.setCaretPosition(taChat.getDocument().getLength());
		
		scrollChat = new JScrollPane(taChat);
		scrollChat.setBounds(30, 60, 393, 260);
		scrollChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlMain.add(scrollChat);
		
		tfUsername = new JTextField();
		tfUsername.setBounds(132, 26, 145, 22);
		tfUsername.setMargin(new Insets(2, 2, 2, 2));
		tfUsername.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if(!currentClientState.isConnected())
	        		connectAction();
	        	taMessage.requestFocusInWindow();
	        }
	    });
		pnlMain.add(tfUsername);
		tfUsername.setColumns(10);
		
		btnConnect = new JButton("Connect");
		btnConnect.setBounds(289, 26, 98, 23);
		btnConnect.addActionListener(this);
		pnlMain.add(btnConnect);
		
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(433, 361, 115, 23);
		btnDisconnect.setEnabled(false);
		btnDisconnect.addActionListener(this);
		pnlMain.add(btnDisconnect);
		
		taOnlineUsers = new JTextArea();
		taOnlineUsers.setMargin(new Insets(5, 5, 5, 5));
		taOnlineUsers.setEditable(false);
		scrollUserList = new JScrollPane(taOnlineUsers);
		scrollUserList.setBounds(433, 60, 115, 260);
		scrollUserList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlMain.add(scrollUserList);
		
		lblOnlineUsers = new JLabel("Online Users");
		lblOnlineUsers.setForeground(Color.WHITE);
		lblOnlineUsers.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblOnlineUsers.setBounds(443, 34, 98, 14);
		pnlMain.add(lblOnlineUsers);
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(this);
		btnSend.setBounds(433, 331, 115, 23);
		pnlMain.add(btnSend);
		
		
		taMessage = new JTextArea();
		//taMessage.setBounds(29, 329, 355, 40);
		taMessage.setMargin(new Insets(5,5,5,5));
		taMessage.setLineWrap(true);
		taMessage.setWrapStyleWord(true);
		taMessage.addKeyListener(new KeyListener(){
			@SuppressWarnings("static-access")
			@Override
			public void keyReleased(KeyEvent event) {
				if(event.getKeyCode() == event.VK_ENTER && currentClientState.isConnected())
					sendAction();
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
		}
		);
		scrollMessage = new JScrollPane(taMessage);
		scrollMessage.setBounds(30, 331, 393, 53);
		scrollMessage.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlMain.add(scrollMessage);
		//pnlMain.add(taMessage);
		
        setTitle("Chat Room");
        setSize(578, 436);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void addTextToChat(String text)
	{
		taChat.append(text+"\n");
		taChat.setCaretPosition(taChat.getDocument().getLength());
	}
	
	public void clearOnlineUsers()
	{
		taOnlineUsers.setText("");
	}
	
	public void addUserToList(String name)
	{
		taOnlineUsers.append(name+"\n");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton temp = (JButton) e.getSource();
		if(temp.getText() == "Connect")
		{
			if(!currentClientState.isConnected())
				connectAction();
		}
		else if(temp.getText() == "Send")
		{
			if(currentClientState.isConnected())
				sendAction();
		}
		else if(temp.getText() == "Disconnect")
		{
			resetComponents();
			addTextToChat("*** You have disconnected from the server ***");
			currentClientState.disconnectAction();
		}
	}
	
	public void setClientState(ClientState cs)
	{
		currentClientState = cs;
	}
	
	public void sendAction()
	{
		if(!taMessage.getText().trim().isEmpty())
			currentClientState.sendAction(taMessage.getText().trim());
		taMessage.setText("");
	}
	
	public void connectAction()
	{
		if(tfUsername.getText().trim().isEmpty())
		{
			return;
		}
		btnConnect.setEnabled(false);
		btnDisconnect.setEnabled(true);
		tfUsername.setEditable(false);

		String username = tfUsername.getText();
		if(tfUsername.getText().length() > 14)
			username = tfUsername.getText().substring(0, 14);
		
		currentClientState.connectAction(username.trim());
	}

	public void resetComponents() {
		btnConnect.setEnabled(true);
		btnDisconnect.setEnabled(false);
		tfUsername.setEditable(true);
		tfUsername.setText("");
		tfUsername.requestFocusInWindow();
	}
}

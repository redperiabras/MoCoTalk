package CLIENT;
import java.net.*;
import java.io.*;

import javax.swing.JOptionPane;

import PROCESSES.BeepEmitter;
import PROCESSES.ChatMessage;
import PROCESSES.SpeechProcess;


public class Client  {

	// for I/O
	private ObjectInputStream sInput;		// to read from the socket
	private ObjectOutputStream sOutput;		// to write on the socket
	private Socket socket;

	private Main mc;
	private SpeechProcess talk = new SpeechProcess();

	private String server, username;
	private int port;

	Client(String server, int port, String username, Main mc) {
		this.server = server;
		this.port = port;
		this.username = username;
		this.mc = mc;
	}
	
	/*
	 * To start the dialog
	 */
	public boolean start() {
		// try to connect to the server
		try {
			socket = new Socket(server, port);
		} 
		// if it failed not much I can so
		catch(Exception ec) {
			new Thread() {
				public void run() {
					talk.say("Sorry but I cannot find the server on port number "+port);
					
				}
			}.start();
			mc.setAlert("Cannot find server", "Server", JOptionPane.ERROR_MESSAGE);
			mc.connectionFailed();
			return false;
		}
	
		/* Creating both Data Stream */
		try{
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			mc.setAlert("Exception creating new Input/output Streams: " + eIO,"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}

		new ListenFromServer().start();
		// Send our username to the server this is the only message that we
		// will send as a String. All other messages will be ChatMessage objects
		try{
			sOutput.writeObject(username);
		}
		catch (IOException eIO) {
			new Thread() {
				public void run() {
					talk.say("Connection Failed");
				}
			}.start();
			mc.setAlert("Connection Failed","Connection",JOptionPane.ERROR_MESSAGE);
			disconnect();
			return false;
		}
		// success we inform the caller that it worked
		return true;
	}

	void sendMessage(ChatMessage msg) {
		try {
			sOutput.writeObject(msg);
			if(ChatMessage.MESSAGE==msg.getType())
				mc.setNotif("Broadcasting Message");
			else
				mc.setNotif("Successfully disconnected");
		}
		catch(IOException e) {
			mc.setAlert("Exception writing to server: " + e,"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/*
	 * When something goes wrong
	 * Close the Input/Output streams and disconnect not much to do in the catch clause
	 */
	private void disconnect() {
		try { 
			if(sInput != null) sInput.close();
		}
		catch(Exception e) {} // not much else I can do
		try {
			if(sOutput != null) sOutput.close();
		}
		catch(Exception e) {} // not much else I can do
        try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {} // not much else I can do
		mc.connectionFailed();
			
	}
	
	class ListenFromServer extends Thread {
		public void run(){
			while(true) {
				try {
					BeepEmitter beep = new BeepEmitter();
					String msg[] = ((String) sInput.readObject()).split(": ");
					
					
					if(!msg[0].equals(username)) { //Transmit with sound
						mc.setSender("FROM: ",msg[0]);
						mc.initReceive();
						
						for(char x: msg[1].toCharArray()) {
							mc.receiveMessage(x);
							switch(x) {
								case '·':{
									beep.playBeep();
									new Thread() {
										public void run() {
											try {
												Thread.sleep(10);
												beep.stopBeep();
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									}.start();
								}
								case '-':{
									beep.playBeep();
									new Thread() {
										public void run() {
											try {
												Thread.sleep(150);
												beep.stopBeep();
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											
										}
									}.start();
								}
								case ' ':{
									try {
										Thread.sleep(200);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								case '/':{
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								
							}
						}
					}
					else { // transmit without sound
						mc.setSender("Broadcasting Message","");
						mc.initReceive();
						for(char x: msg[1].toCharArray()) {
							mc.receiveMessage(x);
							switch(x) {
								case '·':{
									try {
										Thread.sleep(10);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								case '-':{
									try {
										Thread.sleep(150);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								case ' ':{
									try {
										Thread.sleep(200);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								case '/':{
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}
						if(mc.getSender().getText().equalsIgnoreCase("BROADCASTING MESSAGE"))
							mc.setSender("","");
					}
				}
				catch(IOException e) {
					mc.connectionFailed();
					break;
				}
				// can't happen with a String object but need the catch anyhow
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
}

package GUI;

public interface WritableGUI {
	
	void write(String s, String u);
	void setNotif(String notif);
	void setAlert(String alert);
	void connectionClosed();
}

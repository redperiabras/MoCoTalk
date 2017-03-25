package PROCESSES;

import java.io.File;
import java.net.URISyntaxException;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import CLIENT.Main;

public class SpeechProcess {

//	private String name;
	public Voice systemVoice;
	public static void main(String args[]){
		SpeechProcess talk = new SpeechProcess();
		talk.say("Hey Red!");
		talk.dispose();
	}
		public SpeechProcess() {
			String location_mbrola = "";
			
			try {
				File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
				location_mbrola = jarFile.getParentFile().getPath();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
	
			VoiceManager voiceManager = VoiceManager.getInstance();
			System.setProperty("mbrola.base",location_mbrola+"\\mbrola");
			System.out.println(System.getProperty("mbrola.base"));
		  
			systemVoice = voiceManager.getVoice("mbrola_us1");
	
				try {
					systemVoice.allocate();
				}catch(NullPointerException e) {
					new Thread(){
						public void run(){
							say("Sue not found! Zeus will take over");
						}
					}.start();
					systemVoice = voiceManager.getVoice("kevin16");
					systemVoice.allocate();
				}
		}
	
	public void say(String sayThis) {
		try {
			systemVoice.speak(sayThis);
		}catch(NullPointerException e) {
		}
	}
	
	public void dispose() {
		try {
			systemVoice.deallocate();
		}catch(NullPointerException e) {
		}
	}
}
